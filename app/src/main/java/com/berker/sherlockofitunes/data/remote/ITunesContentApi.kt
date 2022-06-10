package com.berker.sherlockofitunes.data.remote

import com.berker.sherlockofitunes.data.remote.dto.ContentResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesContentApi {

    @GET("search")
    suspend fun getContentsBySearch(
        @Query("term", encoded = true) term: String,
        @Query("country") country: String = "US",
        @Query("media") mediaType: String = "all",
        @Query("entity") entity: String = "all",
       // @Query("attribute") attribute: String = "all",
        @Query("limit") limit: Int = 50,
        @Query("lang") lang: String = "en_us",
        ): ContentResultDTO

    companion object {
        const val BASE_URL = "https://itunes.apple.com"
    }
}