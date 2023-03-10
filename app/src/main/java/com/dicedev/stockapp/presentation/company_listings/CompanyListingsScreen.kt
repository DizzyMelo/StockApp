package com.dicedev.stockapp.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dicedev.stockapp.presentation.company_listings.components.CompanyItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CompanyListingsScreen(
    navController: NavController,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.value.isRefreshing)

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = state.value.searchQuery,
            onValueChange = { query ->
                viewModel.onEvent(event = CompanyListingsEvent.OnSearchQueryChange(query))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Search") },
            maxLines = 1,
            singleLine = true
        )

        if (state.value.isLoading) {
            CircularProgressIndicator()
            return@Column
        }

        if (state.value.errorMessage.isNotBlank()) {
            Text(text = state.value.errorMessage)
            return@Column
        }

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(event = CompanyListingsEvent.Refresh) }) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.value.companies.size) { index ->
                    CompanyItem(
                        company = state.value.companies[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // TODO: Navigate to detail screen
                            })

                    if (index < state.value.companies.size) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}