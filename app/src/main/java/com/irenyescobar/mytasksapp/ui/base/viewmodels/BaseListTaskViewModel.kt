package com.irenyescobar.mytasksapp.ui.base.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener

abstract class BaseListTaskViewModel (application: Application,
                                      repository: TaskRepository,
                                      saveTaskListener: SaveTaskListener?):
    BaseSaveTaskViewModel(application,repository,saveTaskListener)
{
    lateinit var data: LiveData<List<Task>>
}
