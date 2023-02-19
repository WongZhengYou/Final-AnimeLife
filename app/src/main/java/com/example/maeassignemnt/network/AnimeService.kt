package com.example.maeassignemnt.network

import com.example.maeassignemnt.data.AnimeList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.jikan.moe/v4/top/anime

// filter
//https://api.jikan.moe/v4/top/anime?filter=airing
//https://api.jikan.moe/v4/top/anime?filter=upcoming
//https://api.jikan.moe/v4/top/anime?filter=bypopularity
//https://api.jikan.moe/v4/top/anime?filter=favorite

// type
//https://api.jikan.moe/v4/top/anime?type=tv
//https://api.jikan.moe/v4/top/anime?type=ova
//https://api.jikan.moe/v4/top/anime?type=movie
//https://api.jikan.moe/v4/top/anime?type=special

//page
//https://api.jikan.moe/v4/top/anime?page={id}

//limit
////https://api.jikan.moe/v4/top/anime?limit={id}

const val baseURL = "https://api.jikan.moe"
interface AnimeInterface {

    @GET("/v4/top/anime")
    fun getTopAnimeAll(@Query("page") page: Int?, @Query("limit")limit: Int?): Call<AnimeList>
    //https://api.jikan.moe/v4/top/anime?page=1&limit=100

    @GET("/v4/top/anime")
    fun getTopAnimeByType(@Query("page") page: Int?, @Query("limit")limit: Int?, @Query("type")type: String?): Call<AnimeList>

    @GET("/v4/top/anime")
    fun getTopAnimeByFilter(@Query("page") page: Int?, @Query("limit")limit: Int?,@Query("filter")filter: String?): Call<AnimeList>

}

object AnimeService{
    val animeInstance: AnimeInterface
    init{
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
        animeInstance = retrofit.create(AnimeInterface::class.java)
    }
}