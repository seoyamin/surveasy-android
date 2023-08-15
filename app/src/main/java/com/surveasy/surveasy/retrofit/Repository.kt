package com.surveasy.surveasy.retrofit

import retrofit2.Response

class Repository {
    private val iRetrofit : RetrofitInterface = RetrofitClient.getClient(API.BASE_URL)!!.create(RetrofitInterface::class.java)

    suspend fun postTest(body : PostTestDto) : Response<PostResponse> {
        return iRetrofit.postTest(body)
    }

}