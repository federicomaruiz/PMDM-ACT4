package com.utad.pmdmu5.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.pmdmu5.data.network.GamesApi
import com.utad.pmdmu5.data.network.model.GamesModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class HomeUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val response: List<GamesModelItem>? = null
)

class HomeViewModel : ViewModel() {

    private var _gamesResponse: MutableLiveData<HomeUIState> = MutableLiveData(HomeUIState())
    val gamesResponse: LiveData<HomeUIState> get() = _gamesResponse

    fun getAllGames() {
        _gamesResponse.postValue(
            HomeUIState(isLoading = true)
        )
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            val response = GamesApi.service.baseCall()
            if (response.isSuccessful) {
                _gamesResponse.postValue(
                    HomeUIState(isLoading = false, response = response.body())
                )
            } else {
                _gamesResponse.postValue(
                    HomeUIState(isLoading = false, isError = false)
                )
            }
        }
    }
}

