package com.surveasy.surveasy.retrofit

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ServiceViewModel(private val repository: Repository) : ViewModel() {
    private val _postRepository = MutableLiveData<PostResponse>()
    val repositories1 : MutableLiveData<PostResponse>
        get() = _postRepository


    fun postTest(body : PostTestDto){
        viewModelScope.launch {
            repository.postTest(body).let { response ->
                if(response.isSuccessful) _postRepository.postValue(response.body())
            }
        }
    }
}