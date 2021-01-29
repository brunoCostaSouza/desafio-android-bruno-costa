package com.marvel_api_bruno_costa.repositories.api

import com.marvel_api_bruno_costa.util.Utils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiComics {

    @GET("characters")
    fun getCharacters(
        @Query("ts") ts: String = Utils.ApiKeys.TS,
        @Query("apikey") apikey: String = Utils.ApiKeys.PUBLIC_KEY,
        @Query("hash") hash: String = Utils.ApiKeys.HASH,
        @Query("limit") limit: Int = Utils.ApiKeys.LIMIT,
        @Query("offset") offset: Int = Utils.ApiKeys.DEFAULT_OFFSET
    ): Call<ResponseComics>

    @GET("characters/{idCharacter}")
    fun getCharacter(
        @Path("idCharacter") idCharacter: String,
        @Query("ts") ts: String = Utils.ApiKeys.TS,
        @Query("apikey") apikey: String = Utils.ApiKeys.PUBLIC_KEY,
        @Query("hash") hash: String = Utils.ApiKeys.HASH
    ): Call<ResponseComics>

    @GET("characters/{idCharacter}/comics")
    fun getComics(
        @Path("idCharacter") idCharacter: String,
        @Query("ts") ts: String = Utils.ApiKeys.TS,
        @Query("apikey") apikey: String = Utils.ApiKeys.PUBLIC_KEY,
        @Query("hash") hash: String = Utils.ApiKeys.HASH
    ): Call<ResponseComics>

    companion object {
        fun create(): ApiComics {
            val retrofit = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .build()
            return retrofit.create(ApiComics::class.java)
        }
    }
}