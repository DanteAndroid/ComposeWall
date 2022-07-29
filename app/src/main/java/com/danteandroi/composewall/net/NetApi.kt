package com.danteandroi.composewall.net

import com.danteandroi.composewall.utils.PC_USER_AGENT
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * @author Dante
 * 2019-08-23
 */
interface WallHaven {

    companion object {
        const val WALL_HAVEN_RATIOS = "9x16,1x1,10x16,3x2,9x18,4x3,5x4"
        const val WALL_HAVEN_PORTRAIT_RATIOS = "9x16,1x1,10x16,9x18"

        const val AT_LEAST_RESOLUTION = "800x1000"
        const val BETTER_RESOLUTION = "1080x1920"

        const val SORT_RANDOM = "random"
        const val SORT_RELEVANCE = "relevance"
    }

    @Headers("User-Agent: $PC_USER_AGENT")
    @GET("search")
    suspend fun getWalls(
        @Query("q") type: String,
        @Query("ratios") ratios: String,
        @Query("atleast") atLeast: String,
        @Query("page") page: Int? = null,
        @Query("sorting") sort: String
    ): ResponseBody
}

interface Yande {

    @Headers("User-Agent: $PC_USER_AGENT")
    @GET("/post")
    suspend fun getYande(@Query("page") page: Int): ResponseBody

}
