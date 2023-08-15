package com.surveasy.surveasy.retrofit

import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {
//    @GET("test/{word}")
//    //@Headers("Content-type: application/json")
//    suspend fun getTest(@Path("word") word : String) : Response<>

    @POST("survey/service")
    suspend fun postTest(@Body body : PostTestDto) : Response<PostResponse>

}