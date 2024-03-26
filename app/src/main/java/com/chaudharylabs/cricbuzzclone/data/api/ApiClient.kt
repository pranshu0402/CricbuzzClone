package com.chaudharylabs.cricbuzzclone.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null
    private var BASE_URL: String = "https://cricbuzz-cricket.p.rapidapi.com/matches/v1/"

    val retrofitInstance: APIInterface
        get() {
            if (retrofit == null) {
                val okHttpClient = OkHttpClient().newBuilder().build()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(APIInterface::class.java)
        }
}