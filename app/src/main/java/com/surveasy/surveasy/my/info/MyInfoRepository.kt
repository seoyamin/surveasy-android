package com.surveasy.surveasy.my.info

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.list.UserSurveyItem
import com.surveasy.surveasy.login.CurrentUser

class MyInfoRepository : MyInfoRepositoryInterface {

    companion object{
        val instance = MyInfoRepository()
    }

    private val db = Firebase.firestore

    override suspend fun fetchUserInfo(model: MutableLiveData<MyInfoModel>, uid: String) {
        val docRef = db.collection("panelData").document(uid)
        var engSurvey = false

        docRef.collection("FirstSurvey").document(uid).get()
            .addOnCompleteListener { snapshot ->
                engSurvey  = snapshot.result["EngSurvey"] as Boolean
            }

        docRef.get().addOnCompleteListener{ snapshot ->
            val userInfo = MyInfoModel(
                snapshot.result["name"].toString(),
                snapshot.result["birthDate"].toString(),
                snapshot.result["gender"].toString(),
                snapshot.result["email"].toString(),
                snapshot.result["phoneNumber"].toString(),
                snapshot.result["accountType"].toString(),
                snapshot.result["accountNumber"].toString(),
                engSurvey
            )
            model.postValue(userInfo)

        }

    }

    override suspend fun updateUserInfo(
        mode: MutableLiveData<MyInfoModel>,
        uid: String,
        data: InfoEditModel
    ) {
        val docRef = db.collection("panelData").document(uid)

        docRef.update(
            "phoneNumber", data.phoneNumber.toString(),
            "accountType", data.accountType.toString(),
            "accountNumber", data.accountNumber.toString())
            .addOnSuccessListener {
                Log.d(TAG, "##@@@###### info update1 SUCCESS")
            }

        docRef.collection("FirstSurvey").document(uid)
            .update("EngSurvey", data.EngSurvey as Boolean)
            .addOnSuccessListener {
                Log.d(TAG, "##@@@###### info update2 SUCCESS")
            }
    }

    /*
    val docRef = db.collection("panelData").document(Firebase.auth.currentUser!!.uid)

        val phoneNumberEdit = findViewById<EditText>(R.id.MyViewInfo_InfoItem_PhoneNumberEdit)
        val accountNumberEdit = findViewById<EditText>(R.id.MyViewInfo_InfoItem_AccountNumberEdit)

        if(phoneNumberEdit.text.toString().trim().isNotEmpty()) {
            infoDataModel.infoData.phoneNumber = phoneNumberEdit.text.toString()
        }
        if(accountNumberEdit.text.toString().trim().isNotEmpty()) {
            infoDataModel.infoData.accountNumber = accountNumberEdit.text.toString()
        }

        docRef.update(
            "phoneNumber", infoDataModel.infoData.phoneNumber,
            "accountType", infoDataModel.infoData.accountType,
            "accountNumber", infoDataModel.infoData.accountNumber)
            .addOnSuccessListener {
                Log.d(TAG, "##@@@###### info update1 SUCCESS")
            }

        docRef.collection("FirstSurvey").document(Firebase.auth.currentUser!!.uid)
            .update("EngSurvey", infoDataModel.infoData.EngSurvey)
            .addOnSuccessListener {
                Log.d(TAG, "##@@@###### info update2 SUCCESS")
            }
     */
    }
