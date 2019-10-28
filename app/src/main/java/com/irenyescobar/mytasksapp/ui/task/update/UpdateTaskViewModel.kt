package com.irenyescobar.mytasksapp.ui.task.update

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener
import com.irenyescobar.mytasksapp.ui.base.viewmodels.BaseSaveTaskViewModel
import com.irenyescobar.mytasksapp.ui.interfaces.UpdateTaskInterface
import com.irenyescobar.mytasksapp.ui.listeners.UpdateTaskListener
import com.irenyescobar.mytasksapp.utils.copy
import java.util.*

class UpdateTaskViewModel(application: Application,
                          repository: TaskRepository,
                          saveTaskListener: SaveTaskListener?, taskId: Long):
    BaseSaveTaskViewModel(application,repository,saveTaskListener),
    UpdateTaskInterface,
    UpdateTaskListener
{
    init {
        settings()
    }

    val task: LiveData<Task> = repository.getById(taskId)

    override fun onChangeTaskName(name: String) {
        task.value?.apply {
            val t = copy()
            t.name = name
            updateTask(t)
        }
    }

    override fun onChangeTaskToDate(toDate: Date?) {
        task.value?.apply {
            val t = copy()
            t.toDate = toDate
            updateTask(t)
        }
    }

    override fun updateTask(task:Task) {
        save(task)
    }
}
