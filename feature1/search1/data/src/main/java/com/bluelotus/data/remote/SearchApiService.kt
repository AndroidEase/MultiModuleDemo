package com.bluelotus.data.remote

import com.bluelotus.data.model.ReceipeDetailResponse
import com.bluelotus.data.model.ReceipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("api/json/v1/1/search.php")
    suspend fun getReceipes(
        @Query("s") s: String
    ) : Response<ReceipeResponse>

    @GET("api/json/v1/1/lookup.php")
    suspend fun getRecipeDetails(
        @Query("i") s: String
    ) : Response<ReceipeDetailResponse>
}