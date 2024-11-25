package com.test.applydigital.ui.news

import android.app.Activity
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.test.applydigital.MainViewModel


@ExperimentalMaterial3Api
@Composable
fun BrowserView(viewModel: MainViewModel) {
    val url: String by viewModel.browserUrl.observeAsState(initial = "")
    var isViewingWebPage by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    LaunchedEffect(url) {
        if (url.isNotEmpty()) {
            isViewingWebPage = true
            webView.loadUrl(url)
        }
    }

    Column {
        TopAppBar(
            title = { Text("Back") },
            navigationIcon = {
                IconButton(onClick = {
                    if (isViewingWebPage) {
                        if (webView.canGoBack()) {
                            webView.goBack()
                        } else {
                            isViewingWebPage = false
                            (context as? Activity)?.onBackPressed()
                        }
                    } else {
                        (context as? Activity)?.onBackPressed()
                    }
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (isViewingWebPage) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    webView.apply {
                        webViewClient = WebViewClient()
                        loadUrl(url)
                    }
                }
            )
        }
    }
}