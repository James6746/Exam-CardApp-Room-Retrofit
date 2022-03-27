package com.example.modul6_exam.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient{
    companion object {

        val TAG: String = ApiClient::class.java.simpleName.toString()
        const val IS_TESTER: Boolean = true
        const val SERVER_DEVELOPMENT = "https://6221a440afd560ea69b5a904.mockapi.io/"
        const val SERVER_PRODUCTION = "https://6221a440afd560ea69b5a904.mockapi.io/"

        private fun server():String{
            if (IS_TESTER){
                return SERVER_DEVELOPMENT
            }
            return SERVER_PRODUCTION
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(server())
                .build()
        }

        val apiService = getRetrofit().create(ApiService::class.java)
    }
}