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
    private val _repositoriesFetchBannerImg = MutableLiveData<ArrayList<String>>()
    val repositories2 : MutableLiveData<ArrayList<String>>
        get() = _repositoriesFetchBannerImg

    private val db = Firebase.firestore

    init {
        Log.d(TAG, ": MainViewMode init")
    }

    suspend fun fetchCurrentUser(uid : String){
        viewModelScope.launch {
            repository.fetchCurrentUser(uid, _repositoriesFetchCurrentUser)

        }
    }

    suspend fun fetchBannerImg(){
        viewModelScope.launch {
            repository.fetchBannerImg(_repositoriesFetchBannerImg)
            Log.d(TAG, "fetchCurrentUser: 뷰모델")
        }
    }


}