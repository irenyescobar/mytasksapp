package com.irenyescobar.mytasksapp.ui.base.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelType
import com.irenyescobar.mytasksapp.ui.interfaces.ViewInteractionInterface
import com.irenyescobar.mytasksapp.utils.customApp

abstract class BaseTaskFragment(@TaskViewModelType val viewModelType: Int): Fragment() {

    val taskRepository: TaskRepository by lazy { customApp.component.taskRepository() }
    var viewInteractions: ViewInteractionInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ViewInteractionInterface) {
            viewInteractions = context
        } else {
            throw RuntimeException("$context must implement ViewInteractionInterface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewInteractions = null
    }
}