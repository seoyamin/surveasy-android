package com.surveasy.surveasy.list.firstsurvey

interface FirstSurveyRepositoryInterface {
    suspend fun updateDidFS(uid : String)
    suspend fun setUserSurveyList(uid : String)
    suspend fun updateReward(uid : String)
    suspend fun addFSCollection(uid : String, data : FSCollectionModel)
}