package com.berker.sherlockofitunes.data.remote

import com.berker.sherlockofitunes.data.remote.dto.ContentResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesContentApi {

    @GET("search")
    suspend fun getContentsBySearch(
        @Query("term", encoded = true) term: String,
        @Query("media") mediaType: String,
        /**
         * Can't find it in documentation, so i googled it ^^
         * https://stackoverflow.com/a/46707491
         */
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): ContentResultDTO

    companion object {
        const val BASE_URL = "https://itunes.apple.com"
    }
}