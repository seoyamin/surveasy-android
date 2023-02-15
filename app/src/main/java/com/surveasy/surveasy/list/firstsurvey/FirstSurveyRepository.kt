package com.surveasy.surveasy.list.firstsurvey

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class FirstSurveyRepository : FirstSurveyRepositoryInterface {
    companion object{
        val instance = FirstSurveyRepository()
    }

    private val db = Firebase.firestore

    override suspend fun updateDidFS(uid: String) {
        db.collection("panelData").document(uid)
            .update("didFirstSurvey", true)
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 1. didFirstSurvey field updated!") }
    }

    override suspend fun setUserSurveyList(uid: String) {
        // set Firestore 'userSurveyList"
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR).toString()
        var month = (c.get(Calendar.MONTH) + 1).toString()
        var day = c.get(Calendar.DAY_OF_MONTH).toString()
        if(month.toInt() < 10) month = "0$month"
        if(day.toInt() < 10) day = "0$day"

        val date = year + "-" + month + "-" + day
        val firstSurvey = hashMapOf(
            "id" to "0",
            "lastIDChecked" to "0",
            "isSent" to false,
            "responseDate" to date,
            "panelReward" to 200,
            "title" to "패널 기본 정보 조사"
        )

        db.collection("panelData").document(uid)
            .collection("UserSurveyList").document("0")
            .set(firstSurvey)
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 2. UserSurveyLIst updated!") }


    }

    override suspend fun updateReward(uid: String) {
        // update Firestore reward
        // fs 무조건 200, 200 아닌지?
        db.collection("panelData").document(uid)
            .update(
                "reward_current", 200,
                "reward_total", 200
            )
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 3. First Survey Reward updated!") }
        Log.d(TAG, "*****")


    }

    override suspend fun addFSCollection(uid: String, data : FSCollectionModel) {
        val firstSurvey = hashMapOf(
            "job" to data.job,
            "major" to data.major,
            "university" to data.university,
            "EngSurvey" to data.EngSurvey,
            "military" to data.military,
            "city" to data.city,
            "district" to data.district,
            "married" to data.married,
            "pet" to data.pet,
            "family" to data.family,
            "housingType" to data.housingType
        )
        // add "FirstSurvey" collection to panelData
        db.collection("panelData").document(uid)
            .collection("FirstSurvey").document(uid)
            .set(firstSurvey).addOnSuccessListener { Log.d(TAG, "FFFFFFFFFFFFFF First Survey uploaded!") }
    }
    }
    /*
    private fun firestore() {

        // update Firestore 'didFirstSurvey (false -> true)"
        db.collection("panelData").document(userModel.currentUser.uid!!)
            .update("didFirstSurvey", true)
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 1. didFirstSurvey field updated!") }
            Log.d(TAG, "*****")


        // set Firestore 'userSurveyList"
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR).toString()
        var month = (c.get(Calendar.MONTH) + 1).toString()
        var day = c.get(Calendar.DAY_OF_MONTH).toString()
        if(month.toInt() < 10) month = "0$month"
        if(day.toInt() < 10) day = "0$day"

        val date = year + "-" + month + "-" + day
        val firstSurvey = hashMapOf(
            "id" to "0",
            "lastIDChecked" to "0",
            "isSent" to false,
            "responseDate" to date,
            "panelReward" to 200,
            "title" to "패널 기본 정보 조사"
        )

        db.collection("panelData").document(userModel.currentUser.uid!!)
            .collection("UserSurveyList").document("0")
            .set(firstSurvey)
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 2. UserSurveyLIst updated!") }



        // update Firestore reward
        db.collection("panelData").document(userModel.currentUser.uid!!)
            .update(
                "reward_current", userModel.currentUser.rewardCurrent!! + 200,
                "reward_total", userModel.currentUser.rewardTotal!! + 200
            )
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 3. First Survey Reward updated!") }
        Log.d(TAG, "*****")



        // add "FirstSurvey" collection to panelData
        val FirstSurvey = hashMapOf(
            "job" to firstSurveyModel.firstSurvey.job,
            "major" to firstSurveyModel.firstSurvey.major,
            "university" to firstSurveyModel.firstSurvey.university,
            "EngSurvey" to firstSurveyModel.firstSurvey.EngSurvey,
            "military" to firstSurveyModel.firstSurvey.military,
            "city" to firstSurveyModel.firstSurvey.city,
            "district" to firstSurveyModel.firstSurvey.district,
            "married" to firstSurveyModel.firstSurvey.married,
            "pet" to firstSurveyModel.firstSurvey.pet,
            "family" to firstSurveyModel.firstSurvey.family,
            "housingType" to firstSurveyModel.firstSurvey.housingType
        )
        db.collection("panelData").document(userModel.currentUser.uid!!)
            .collection("FirstSurvey").document(userModel.currentUser.uid!!)
            .set(FirstSurvey).addOnSuccessListener { Log.d(TAG, "FFFFFFFFFFFFFF First Survey uploaded!") }
    }
     */
}