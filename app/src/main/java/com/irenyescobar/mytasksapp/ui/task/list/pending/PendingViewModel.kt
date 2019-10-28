package com.irenyescobar.mytasksapp.ui.task.list.pending

import android.app.Application
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.ui.base.viewmodels.BaseListTaskViewModel
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener

class PendingViewModel(application: Application,repository: TaskRepository,saveTaskListener:  SaveTaskListener?):
    BaseListTaskViewModel(application, repository,saveTaskListener){

    init {
        repository.setSaveTaskErrorListener(this)
        repository.setSaveTaskCompletedListener(this)
        data = repository.getPendings()
    }
}