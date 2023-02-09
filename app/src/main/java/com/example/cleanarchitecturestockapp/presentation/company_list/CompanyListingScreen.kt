package com.example.cleanarchitecturestockapp.presentation.company_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleanarchitecturestockapp.presentation.company_info.CompanyInfoScreen
import com.example.cleanarchitecturestockapp.presentation.destinations.CompanyInfoScreenDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun CompanyListingScreen(
    navController: DestinationsNavigator,
    viewModel: CompanyListingViewModel = hiltViewModel(),

    ) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)

    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(event = CompanyListingEvent.OnSearchQueryChange(it))

            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.onEvent(CompanyListingEvent.Refresh)
        }) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.listCompany.size) { index ->
                    val company=state.listCompany[index]
                    CompanyItem(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(CompanyInfoScreenDestination(symbol = company.symbol))
                            }
                            .padding(16.dp)
                    )
                    if (index < state.listCompany.size) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                }
            }

        }
    }

}