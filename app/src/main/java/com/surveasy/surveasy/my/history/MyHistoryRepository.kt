package com.surveasy.surveasy.my.history

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.list.UserSurveyModel

class MyHistoryRepository : MyHistoryRepositoryInterface {

    companion object {
        val instance = MyHistoryRepository()
    }

    val db = Firebase.firestore

    //viewModel 에 정산 여부에 따라 나눠서 저장
    override suspend fun fetchHistoryList(
        finModel: MutableLiveData<ArrayList<UserSurveyModel>>,
        waitModel: MutableLiveData<ArrayList<UserSurveyModel>>,
        uid: String
    ) {
        var finList = ArrayList<UserSurveyModel>()
        var waitList = ArrayList<UserSurveyModel>()
        db.collection("panelData").document(uid)
            .collection("UserSurveyList").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val item = UserSurveyModel(
                        Integer.parseInt(document["id"].toString()),
                        Integer.parseInt(document["lastIDChecked"].toString()),
                        document["title"] as String?,
                        Integer.parseInt(document["panelReward"].toString()),
                        document["responseDate"] as String?,
                        document["isSent"] as Boolean,
                        document["filePath"] as String?
                    )

                    if (document["isSent"] as Boolean) {
                        finList.add(item)
                    } else {
                        waitList.add(item)
                    }
                }
                finModel.postValue(finList)
                waitModel.postValue(waitList)

            }
    }
}
    /*
    //viewModel 에 정산 여부에 따라 나눠서 저장
        db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
            .collection("UserSurveyList").get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val item: UserSurveyItem = UserSurveyItem(
                        Integer.parseInt(document["id"].toString()),
                        Integer.parseInt(document["lastIDChecked"].toString()),
                        document["title"] as String?,
                        Integer.parseInt(document["panelReward"].toString()),
                        document["responseDate"] as String?,
                        document["isSent"] as Boolean,
                        document["filePath"] as String?
                    )

                    if(document["isSent"] as Boolean){
                        finList.add(item)
                    }else{
                        waitList.add(item)
                    }
                }
                waitModel.waitSurvey.addAll(waitList)
                finModel.finSurvey.addAll(finList)


            }
     */
