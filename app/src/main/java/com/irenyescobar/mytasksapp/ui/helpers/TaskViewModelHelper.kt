package com.irenyescobar.mytasksapp.ui.helpers

import android.app.Application
import androidx.annotation.IntDef
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.ui.base.viewmodels.BaseTaskViewModel
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener
import com.irenyescobar.mytasksapp.ui.task.add.AddTaskViewModel
import com.irenyescobar.mytasksapp.ui.task.list.completed.CompletedViewModel
import com.irenyescobar.mytasksapp.ui.task.list.pending.PendingViewModel
import com.irenyescobar.mytasksapp.ui.task.list.today.TodayViewModel
import com.irenyescobar.mytasksapp.ui.task.update.UpdateTaskViewModel


@IntDef(
    TaskViewModelType.TODAY,
    TaskViewModelType.PENDING,
    TaskViewModelType.COMPLETED,
    TaskViewModelType.UPDATE_TASK
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class TaskViewModelType {
    companion object {
        const val TODAY = 0
        const val PENDING = 1
        const val COMPLETED = 2
        const val UPDATE_TASK = 3
        const val ADD_TASK = 4
    }
}

class TaskViewModelFactory(private val application: Application,
                           private val repository: TaskRepository,
                           private val saveTaskListener: SaveTaskListener?,
                           @TaskViewModelType private val type: Int,
                           private val taskId: Long?
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        var viewModel: BaseTaskViewModel = TodayViewModel(application, repository,saveTaskListener)

        when(type){
            TaskViewModelType.TODAY -> viewModel = TodayViewModel(application, repository,saveTaskListener)
            TaskViewModelType.PENDING -> viewModel = PendingViewModel(application, repository,saveTaskListener)
            TaskViewModelType.COMPLETED -> viewModel = CompletedViewModel(application, repository,saveTaskListener)
            TaskViewModelType.UPDATE_TASK -> viewModel = UpdateTaskViewModel(application, repository,saveTaskListener,taskId!!)
            TaskViewModelType.ADD_TASK -> viewModel = AddTaskViewModel(application, repository,saveTaskListener)
        }
        return viewModel as T
    }
}