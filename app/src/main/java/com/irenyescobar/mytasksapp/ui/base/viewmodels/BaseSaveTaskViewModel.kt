package com.irenyescobar.mytasksapp.ui.base.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.repositories.listeners.SaveTaskCompletedListener
import com.irenyescobar.mytasksapp.repositories.listeners.SaveTaskErrorListener
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener
import kotlinx.coroutines.launch

abstract class BaseSaveTaskViewModel (application: Application,
                                      repository: TaskRepository,
                                      private val saveTaskListener: SaveTaskListener?):
    BaseTaskViewModel(application,repository),
    SaveTaskErrorListener,
    SaveTaskCompletedListener
{

    init {
        settings()
    }

    fun settings(){
        repository.setSaveTaskErrorListener(this)
        repository.setSaveTaskCompletedListener(this)
    }

    fun save(task: Task){
        viewModelScope.launch {
            repository.save(task)
        }
    }

    override fun onSaveTaskError(errorMessage: String) {
        saveTaskListener?.onSaveTaskError(errorMessage)
    }

    override fun onSaveTaskCompleted(success: Boolean) {
        if(success) {
            saveTaskListener?.onSaveTaskSuccess()
        }
    }
}