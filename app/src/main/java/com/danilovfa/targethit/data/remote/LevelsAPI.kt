package com.danilovfa.targethit.data.remote

import com.danilovfa.targethit.data.remote.response.LevelDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LevelsAPI {
    @GET("levels/{id}.json")
    suspend fun getLevel(
        @Path("id") id: Int
    ) : Response<LevelDto>

    @GET("levels/count.json")
    suspend fun getAmountOfLevels() : Response<Int>
}