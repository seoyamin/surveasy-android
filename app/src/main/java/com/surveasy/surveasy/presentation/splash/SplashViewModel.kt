package com.surveasy.surveasy.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.app.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _events = MutableSharedFlow<SplashUiEvent>()
    val events: SharedFlow<SplashUiEvent> = _events.asSharedFlow()

    fun chooseNextPage() {
        viewModelScope.launch {
            dataStoreManager.getTutorial().collect { tutorial ->
                if (tutorial == true) {
                    _events.emit(SplashUiEvent.NavigateToMain)
                } else {
                    _events.emit(SplashUiEvent.NavigateToTutorial)
                }
            }
        }
//        viewModelScope.launch {
//            dataStoreManager.getAutoLogin().collect { autoLogin ->
//                dataStoreManager.getAccessToken().collect { accessToken ->
//                    if (autoLogin == true && accessToken != "") {
//                        _events.emit(SplashUiEvent.NavigateToMain)
//                    } else {
//                        _events.emit(SplashUiEvent.NavigateToIntro)
//                    }
//                }
//            }
//        }
    }
}

sealed class SplashUiEvent {
    data object NavigateToTutorial : SplashUiEvent()
    data object NavigateToMain : SplashUiEvent()
    data object NavigateToIntro : SplashUiEvent()
}