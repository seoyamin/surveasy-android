package com.surveasy.surveasy.my.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyInfoViewModel(private val repository: MyInfoRepository) : ViewModel() {
    //live data
    private val _repositoriesFetchInfoData = MutableLiveData<MyInfoModel>()
    val repositories1 : MutableLiveData<MyInfoModel>
        get() = _repositoriesFetchInfoData
    private val _repositoriesUpdateInfoData = MutableLiveData<MyInfoModel>()
    val repositories2 : MutableLiveData<MyInfoModel>
        get() = _repositoriesUpdateInfoData


    //acc viewModel
    var myInfoModel = ArrayList<MyInfoModel>()

    suspend fun fetchUserInfo(uid : String){
        repository.fetchUserInfo(_repositoriesFetchInfoData, uid)
    }

    suspend fun updateUserInfo(uid: String, data : InfoEditModel){
        repository.updateUserInfo(_repositoriesUpdateInfoData, uid, data)
    }

}