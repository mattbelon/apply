package com.test.applydigital.ui.news

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.test.applydigital.MainViewModel
import com.test.applydigital.R
import com.test.applydigital.data.models.news.Hit
import com.test.applydigital.utils.isNetworkAvailable
import com.test.applydigital.utils.toTimeAgo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(mainViewModel: MainViewModel) {
    val browserMode: Boolean by mainViewModel.isBrowserMode.observeAsState(initial = false)
    val apiState : NewsUiState by mainViewModel.apiState.observeAsState(initial = NewsUiState.Loading)
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (!browserMode) {
                LazyColumnView(viewModel = mainViewModel)
            } else {
                BrowserView(mainViewModel)
            }

            BackHandler(
                enabled = browserMode
            ) {
                mainViewModel.deactivateBrowserMode()
            }
        }
        when (apiState){
            is NewsUiState.Error -> {
                when    ((apiState as NewsUiState.Error).exception){
                    is ServerExceptions -> {
                        Toast.makeText(context, stringResource(id = R.string.server_error), Toast.LENGTH_LONG ).show()
                    }
                    is ParserExceptions ->{
                        Toast.makeText(context, stringResource(id = R.string.parse_error), Toast.LENGTH_LONG ).show()
                    }
                    is NetworkExceptions ->{
                        Toast.makeText(context, stringResource(id = R.string.no_internet), Toast.LENGTH_LONG ).show()
                    }
                }
            }
            NewsUiState.Loading -> Unit
            is NewsUiState.Success -> Unit
        }

    }
}


@Composable
fun LazyColumnView(viewModel: MainViewModel) {
    val isRefreshing by viewModel.isRefreshing.observeAsState(false)
    val hits: List<Hit?> by viewModel.hits.observeAsState(initial = emptyList())
    val context = LocalContext.current
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            if(context.isNetworkAvailable()) {
                viewModel.loadNews()
            } else {
                Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show()
            }
        }
    ) {
        LazyColumn {
            items(hits.filterNotNull(), key = { it.objectID }) { currentHit ->
                NewsItems(
                    hit = currentHit,
                    onRemove = viewModel::removeHit,
                    onItemClick = {
                        currentHit.storyUrl?.let {
                            viewModel.loadUrl(it)
                        }
                    }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadNews()
    }
}


@Composable
fun NewsItems(
    hit: Hit,
    modifier: Modifier = Modifier,
    onRemove: (Hit) -> Unit,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current
    val currentItem by rememberUpdatedState(hit)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {}
                SwipeToDismissBoxValue.EndToStart -> {
                    onRemove(currentItem)
                    Toast.makeText(context, R.string.deleted_item, Toast.LENGTH_SHORT).show()
                }

                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },

        positionalThreshold = { it * .25f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        backgroundContent = { DismissBackground(dismissState) },
        content = {
            NewsItemCard(hit.storyTitle!!, hit.author, hit.createdAt, onItemClick)
        })
}

@Composable
fun NewsItemCard(storyTitle: String, author: String, createdAt: String, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {

        ListItem(
            modifier = Modifier.clip(MaterialTheme.shapes.small),
            headlineContent = {
                Text(
                    storyTitle,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Text(
                    author + " " + createdAt.toTimeAgo(),
                    style = MaterialTheme.typography.bodySmall
                )
            },

            )
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}

@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color(0xFFFFFFFF)
        SwipeToDismissBoxValue.EndToStart -> Color(0xFFFF1744)
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete)
        )
        Spacer(modifier = Modifier)
        Text(
            text = stringResource(id = R.string.delete),
            color = Color.White
        )
    }
}