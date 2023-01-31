package com.surveasy.surveasy

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.login.CurrentUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.log

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _repositoriesFetchCurrentUser = MutableLiveData<CurrentUser>()
    val repositories1 : MutableLiveData<CurrentUser>
        get() = _repositoriesFetchCurrentUser
    private val _test = MutableLiveData<Dto>()
    val test1 : LiveData<Dto>
        get() = _test
    private val _test1 = MutableLiveData<Flow<Dto>>()
    val test11 : LiveData<Flow<Dto>>
        get() = _test1

    private val db = Firebase.firestore

    init {
        Log.d(TAG, ": MainViewMode init")
    }
    var currentUserModel = ArrayList<CurrentUser>()

    suspend fun fetchCurrentUser(uid : String){
        viewModelScope.launch {
            repository.fetchCurrentUser(uid).let {
                delay(1500)
                _repositoriesFetchCurrentUser.postValue(repository.rCurrentUser.value)
                Log.d(TAG, "fetchCurrentUser: 뷰모델")
            }
        }
    }

    suspend fun test(){
        viewModelScope.launch {
            repository.test1().let {
                delay(1500)
                _test.postValue(repository.tt.value)
                Log.d(TAG, "test: 뷰모델 지나감")
            }

        }
    }


}