package com.irenyescobar.mytasksapp.data.api.retrofit

import com.irenyescobar.mytasksapp.data.room.entities.Task
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Body
import retrofit2.http.Path

interface MyTasksApi {
    @GET("tasks")
    suspend fun get():List<Task>

    @POST("tasks")
    suspend fun insert(@Body task:Task)

    @PUT("tasks/{id}")
    suspend fun update(@Path("id") id: Long,@Body task:Task)
}