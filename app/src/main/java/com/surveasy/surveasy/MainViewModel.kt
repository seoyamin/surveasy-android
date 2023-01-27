package com.surveasy.surveasy

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.login.CurrentUser
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _repositoriesFetchCurrentUser = MutableLiveData<ArrayList<CurrentUser>>()
    val repositories1 : MutableLiveData<ArrayList<CurrentUser>>
        get() = _repositoriesFetchCurrentUser
    private val _test = MutableLiveData<String>()
    val test1 : LiveData<String>
        get() = _test

    init {
        Log.d(TAG, ": MainViewMode init")
    }
    lateinit var currentUserModel : ArrayList<CurrentUser>

    fun test(){
        _test.value = repository.test()
        Log.d(TAG, "test: ${_test.value}")
    }

    suspend fun fetchCurrentUser(uid: String){
        viewModelScope.launch {
            _repositoriesFetchCurrentUser.postValue(repository.fetchCurrentUser(uid))
            Log.d(TAG, "fetchCurrentUser: post")
        }
    }
}