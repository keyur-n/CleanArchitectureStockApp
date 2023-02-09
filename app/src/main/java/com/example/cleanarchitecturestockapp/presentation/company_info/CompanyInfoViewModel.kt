package com.example.cleanarchitecturestockapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturestockapp.domain.repository.StockRepository
import com.example.cleanarchitecturestockapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository,

    ) : ViewModel() {
    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }
            when (val result = companyInfoResult.await()) {
                is Resource.Error -> {
                    state=state.copy(
                        company = null,
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Success -> {
                    state = state.copy(
                        company = result.data,
                        error = null,
                        isLoading = false
                    )
                }
                else -> Unit
            }
            when (val result = intradayInfoResult.await()) {
                is Resource.Error -> {
                    state=state.copy(
                        stockInfos = emptyList(),
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Success -> {
                    state = state.copy(
                        stockInfos = result.data?: emptyList(),
                        error = null,
                        isLoading = false
                    )
                }
                else -> Unit
            }
        }
    }
}