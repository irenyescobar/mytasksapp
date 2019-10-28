package com.irenyescobar.mytasksapp.ui.task.add

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.ui.base.viewmodels.BaseSaveTaskViewModel
import com.irenyescobar.mytasksapp.ui.interfaces.AddTaskInterface
import com.irenyescobar.mytasksapp.ui.listeners.AddTaskListener
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener

class AddTaskViewModel (application: Application,
                        repository: TaskRepository,
                        saveTaskListener: SaveTaskListener?):
    BaseSaveTaskViewModel(application,repository,saveTaskListener),
    AddTaskInterface,
    AddTaskListener
{
    private val _text = MutableLiveData<String>().apply { value = "" }
    val text: LiveData<String> = _text

    override fun onChangeText(text: String) {
        _text.apply {
            value = text
        }
    }

    override fun addTask() {
        text.value?.apply {
            val task = Task()
            task.name = this
            save(task)
        }
    }
}