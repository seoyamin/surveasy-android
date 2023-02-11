package com.surveasy.surveasy.my.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyHistoryViewModelFactory (private val repository: MyHistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MyHistoryRepository::class.java).newInstance(repository)

    }
}