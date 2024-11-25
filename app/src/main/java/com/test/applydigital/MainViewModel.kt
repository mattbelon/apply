package com.test.applydigital

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.applydigital.data.models.news.Hit
import com.test.applydigital.repository.news.NewsRepository
import com.test.applydigital.ui.news.NewsUiState
import com.test.applydigital.ui.news.ParserExceptions
import com.test.applydigital.ui.news.ServerExceptions
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _removedHits = mutableSetOf<String>()
    private val removedHits: Set<String> get() = _removedHits

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _hits = MutableLiveData<List<Hit?>>(emptyList())
    val hits: LiveData<List<Hit?>> = _hits

    private val _apiState = MutableLiveData<NewsUiState>()
    val apiState: LiveData<NewsUiState> = _apiState

    private val _isBrowserMode = MutableLiveData<Boolean>(false)
    val isBrowserMode: LiveData<Boolean> = _isBrowserMode

    private val _browserUrl = MutableLiveData<String>("")
    val browserUrl: LiveData<String> = _browserUrl

    private val newsRepository: NewsRepository = NewsRepository()


    fun loadUrl(url: String) {
        url.let {
            _isBrowserMode.value = true
            _browserUrl.value = url
        }
    }

    fun deactivateBrowserMode() {
        _isBrowserMode.value = false
    }

    fun loadNews() {
        _isRefreshing.value = true

        viewModelScope.launch {
            try {
                val response = newsRepository.getNews()
                if (response.hits.isNotEmpty()) {
                    Log.d("Response", response.hits.toString())

                    val filteredHits =
                        response.hits.filter { it.objectID !in removedHits && it.storyTitle?.isNotEmpty() == true }

                    _hits.postValue(filteredHits)
                } else {
                    _apiState.value = NewsUiState.Error(ServerExceptions())
                }
            } catch (e: IOException) {
                _apiState.value = NewsUiState.Error(ParserExceptions())
                Log.d("TESTING", e.toString())
            } catch (e: HttpException) {
                _apiState.value = NewsUiState.Error(ServerExceptions())
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun removeHit(hit: Hit) {
        _hits.value = _hits.value?.filterNot { it?.objectID == hit.objectID }
        _removedHits.add(hit.objectID)
    }

}

