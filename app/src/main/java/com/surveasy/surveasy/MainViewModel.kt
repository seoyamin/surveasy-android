package com.surveasy.surveasy

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.login.CurrentUser
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _repositoriesFetchCurrentUser = MutableLiveData<CurrentUser>()
    val repositories1 : MutableLiveData<CurrentUser>
        get() = _repositoriesFetchCurrentUser

    init {
        Log.d(TAG, ": MainViewMode init")
    }
    lateinit var currentUserModel : CurrentUser

    suspend fun fetchCurrentUser(uid: String){
        viewModelScope.launch {
            _repositoriesFetchCurrentUser.postValue(repository.fetchCurrentUser(uid))
            Log.d(TAG, "fetchCurrentUser: post")
        }
    }
}