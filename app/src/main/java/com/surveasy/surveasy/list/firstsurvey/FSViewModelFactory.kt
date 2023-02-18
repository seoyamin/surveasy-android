package com.surveasy.surveasy.list.firstsurvey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FSViewModelFactory(private val repository: FirstSurveyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(FirstSurveyRepository::class.java).newInstance(repository)
    }
}