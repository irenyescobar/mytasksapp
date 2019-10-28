package com.ireny.randon.frasle.warrantyreport.di.modules

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irenyescobar.mytasksapp.BuildConfig
import com.irenyescobar.mytasksapp.MyTasksApp
import com.irenyescobar.mytasksapp.data.api.retrofit.MyTasksApi
import com.irenyescobar.mytasksapp.data.room.MyTasksAppRoomDatabase
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(val application: MyTasksApp) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = application


    @Provides
    @Singleton
    fun provideRoomDatabase(context:Context): MyTasksAppRoomDatabase {
        val build = Room.databaseBuilder(
            context,
            MyTasksAppRoomDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
        return build
    }

    val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BuildConfig.API_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideMyTasksApi(): MyTasksApi{
        return provideRetrofit().create(MyTasksApi::class.java)
    }

    @Provides
    fun provideTaskRepository(api: MyTasksApi, database: MyTasksAppRoomDatabase): TaskRepository{
        return TaskRepository(api,database.taskDao())
    }




}