package com.surveasy.surveasy.my.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyInfoViewModelFactory (private val repository: MyInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MyInfoRepository::class.java).newInstance(repository)

    }
}