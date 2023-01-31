package com.surveasy.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amplitude.api.Amplitude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.list.UserSurveyItem
import com.surveasy.surveasy.login.CurrentUser
import com.surveasy.surveasy.userRoom.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class MainRepository : MainRepositoryInterface {
    companion object {
        val instance = MainRepository()
    }

    private val db = Firebase.firestore
    val tt = MutableLiveData<Dto>()
    //fetchCurrentUser
    val rCurrentUser = MutableLiveData<CurrentUser>()

    override suspend fun fetchCurrentUser(uid: String) {
        val docRef = db.collection("panelData").document(uid.toString())
        val userSurveyList = ArrayList<UserSurveyItem>()

        docRef.collection("UserSurveyList").get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    var item : UserSurveyItem = UserSurveyItem(
                        Integer.parseInt(document["id"].toString()),
                        Integer.parseInt(document["lastIDChecked"].toString()),
                        document["title"] as String?,
                        Integer.parseInt(document["panelReward"].toString()) as Int?,
                        document["responseDate"] as String?,
                        document["isSent"] as Boolean?,
                        null
                    )
                    userSurveyList.add(item)

                }
            }

        docRef.get().addOnCompleteListener{ snapshot ->
            val currentUser : CurrentUser = CurrentUser(
                snapshot.result["uid"].toString(),
                snapshot.result["fcmToken"].toString(),
                snapshot.result["name"].toString(),
                snapshot.result["email"].toString(),
                snapshot.result["phoneNumber"].toString(),
                snapshot.result["gender"].toString(),
                snapshot.result["birthDate"].toString(),
                snapshot.result["accountType"].toString(),
                snapshot.result["accountNumber"].toString(),
                snapshot.result["accountOwner"].toString(),
                snapshot.result["inflowPath"].toString(),
                snapshot.result["didFirstSurvey"] as Boolean?,
                snapshot.result["autoLogin"] as Boolean?,
                Integer.parseInt(snapshot.result["reward_current"].toString()),
                Integer.parseInt(snapshot.result["reward_total"].toString()),
                snapshot.result["marketingAgree"] as Boolean?,
                userSurveyList
            )
            rCurrentUser.postValue(currentUser)

        }

    }

    /*
    private fun fetchCurrentUser(uid: String) :CurrentUser {
        val docRef = db.collection("panelData").document(uid.toString())
        val userSurveyList = ArrayList<UserSurveyItem>()


        docRef.collection("UserSurveyList").get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    var item : UserSurveyItem = UserSurveyItem(
                        Integer.parseInt(document["id"].toString()),
                        Integer.parseInt(document["lastIDChecked"].toString()),
                        document["title"] as String?,
                        Integer.parseInt(document["panelReward"].toString()) as Int?,
                        document["responseDate"] as String?,
                        document["isSent"] as Boolean?,
                        null
                    )
                    userSurveyList.add(item)

                }
            }


        docRef.get().addOnCompleteListener { snapshot ->
            if(snapshot != null) {

                // Local Room DB에 current user의 User 객체 저장하기
                val uidNum = userDB.userDao().getNumUid(snapshot.result["uid"].toString())

                // [case 1] 해당 uid 가진 튜플 없는 경우 (INSERT user info)
                if(uidNum == 0) {
                    userDB.userDao().deleteAll()
                    val user : User = User(
                        snapshot.result["uid"].toString(),
                        Integer.parseInt(snapshot.result["birthDate"].toString().substring(0, 4)),
                        snapshot.result["gender"].toString(),
                        snapshot.result["fcmToken"].toString(),
                        snapshot.result["autoLogin"] as Boolean,

                    )
                    userDB.userDao().insert(user)
                }

                // [case 2] 이미 동일한 uid의 튜플이 저장된 경우 (UPDATE fcm token)
                else if(uidNum == 1) {
                    userDB.userDao().updateFcm(snapshot.result["uid"].toString(),snapshot.result["fcmToken"].toString())
                }


                // userModel에 유저 정보 저장
                val currentUser : CurrentUser = CurrentUser(
                    snapshot.result["uid"].toString(),
                    snapshot.result["fcmToken"].toString(),
                    snapshot.result["name"].toString(),
                    snapshot.result["email"].toString(),
                    snapshot.result["phoneNumber"].toString(),
                    snapshot.result["gender"].toString(),
                    snapshot.result["birthDate"].toString(),
                    snapshot.result["accountType"].toString(),
                    snapshot.result["accountNumber"].toString(),
                    snapshot.result["accountOwner"].toString(),
                    snapshot.result["inflowPath"].toString(),
                    snapshot.result["didFirstSurvey"] as Boolean?,
                    snapshot.result["autoLogin"] as Boolean?,
                    Integer.parseInt(snapshot.result["reward_current"].toString()),
                    Integer.parseInt(snapshot.result["reward_total"].toString()),
                    snapshot.result["marketingAgree"] as Boolean?,
                    userSurveyList
                )
                userModel.currentUser = currentUser


                // [Amplitude] user properties (name, reward_total)
                val client = Amplitude.getInstance()
                val userProperties = JSONObject()
                try {
                    userProperties.put("name", userModel.currentUser!!.name)
                        .put("reward_total", userModel.currentUser!!.rewardTotal)
                        .put("birthYear", userModel.currentUser!!.birthDate!!.substring(0, 4))
                        .put("gender", userModel.currentUser!!.gender)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    System.err.println("Invalid JSON")
                }
                client.setUserProperties(userProperties)


            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "fail $exception")
        }
        return userModel.currentUser
    }
     */

    override suspend fun test1() {
        CoroutineScope(Dispatchers.Main).launch {
            db.collection("panelData").document("lmhi8mpzo0YZGpx0NTRYkIdxAlE2")
                .get().addOnCompleteListener { snapshot->
                    tt.postValue(Dto(snapshot.result["name"].toString()))
                    Log.d(TAG, "test1: 레포 지나감")
                }
        }
    }

    }
