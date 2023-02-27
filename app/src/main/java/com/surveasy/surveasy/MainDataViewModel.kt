package com.surveasy.surveasy

import androidx.lifecycle.ViewModel
import com.surveasy.surveasy.login.CurrentUser

class MainDataViewModel : ViewModel() {
    var currentUserModel = ArrayList<CurrentUser>()

}