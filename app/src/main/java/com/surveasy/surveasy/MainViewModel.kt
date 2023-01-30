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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _repositoriesFetchCurrentUser = MutableLiveData<ArrayList<CurrentUser>>()
    val repositories1 : MutableLiveData<ArrayList<CurrentUser>>
        get() = _repositoriesFetchCurrentUser
    private val _test = MutableLiveData<String>()
    val test1 : LiveData<String>
        get() = _test
    private val _test1 = MutableLiveData<Flow<Dto>>()
    val test11 : LiveData<Flow<Dto>>
        get() = _test1

    private val db = Firebase.firestore

    init {
        Log.d(TAG, ": MainViewMode init")
    }
    lateinit var currentUserModel : ArrayList<CurrentUser>

    fun test(){
        viewModelScope.launch {
            _test.value = repository.tt.toString()
        }
    }


}