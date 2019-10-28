package com.ireny.randon.frasle.warrantyreport.di.components

import com.ireny.randon.frasle.warrantyreport.di.modules.ApplicationModule
import com.irenyescobar.mytasksapp.MyTasksApp
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: MyTasksApp)
    fun taskRepository(): TaskRepository
}