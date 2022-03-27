package com.example.modul6_exam.network

import com.example.modul6_exam.model.Card
import com.example.modul6_exam.model.Responses
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("cards")
    fun getAllCards(): Call<ArrayList<Responses>>
    @POST("cards")
    fun createCard(@Body card: Card): Call<Responses>

}