package com.surveasy.surveasy.my.info

import androidx.lifecycle.MutableLiveData
import com.surveasy.surveasy.login.CurrentUser

interface MyInfoRepositoryInterface {
    suspend fun fetchUserInfo(model : MutableLiveData<MyInfoModel>, uid : String)
    suspend fun updateUserInfo(mode : MutableLiveData<MyInfoModel>, uid : String, data : InfoEditModel)

}