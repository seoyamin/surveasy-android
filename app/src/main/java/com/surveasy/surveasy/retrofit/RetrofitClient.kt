package com.surveasy.surveasy.retrofit

import android.content.ContentValues
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit client 선언
object RetrofitClient {
    private var retrofitClient : Retrofit? = null

    fun getClient(baseUrl : String) : Retrofit?{
        Log.d(ContentValues.TAG, "getClient: called")

        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(ContentValues.TAG, "log: ******message $message")
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        if(retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}