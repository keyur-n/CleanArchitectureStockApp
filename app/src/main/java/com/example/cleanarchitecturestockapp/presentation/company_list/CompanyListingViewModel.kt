package com.example.cleanarchitecturestockapp.presentation.company_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.cleanarchitecturestockapp.domain.repository.StockRepository
import com.example.cleanarchitecturestockapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    val stockRepository: StockRepository
) : ViewModel() {
    var state by mutableStateOf(CompanyListingState())
    private var searchJob : Job?=null
    init {
        getCompanyListing()
    }
    fun onEvent(event: CompanyListingEvent) {
        when (event) {
            is CompanyListingEvent.OnSearchQueryChange -> {
                state=state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob=viewModelScope.launch {
                    delay(500L)
                    getCompanyListing()
                }

            }
            CompanyListingEvent.Refresh -> {
                getCompanyListing(fetchFromRemote = true)
            }
        }
    }

    private fun getCompanyListing(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            stockRepository.getCompanyListing(fetchFromRemote, query).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        result.data?.let { listing ->
                            state = state.copy(
                                listCompany = listing
                            )
                        }
                    }
                }
            }
        }
    }

}