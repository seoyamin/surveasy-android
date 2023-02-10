package com.surveasy.surveasy.my.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyNoticeViewModelFactory (private val repository: MyNoticeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MyNoticeRepository::class.java).newInstance(repository)

    }
}