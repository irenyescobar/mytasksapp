package com.irenyescobar.mytasksapp.ui.base.fragments

import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelType
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener

abstract class BaseSaveTaskFragment(@TaskViewModelType viewModelType: Int): BaseTaskFragment(viewModelType), SaveTaskListener {

    override fun onSaveTaskSuccess() {
        viewInteractions?.hideProgress()
    }

    override fun onSaveTaskError(message: String) {
        viewInteractions?.run {
            hideProgress()
            showWarning(message)
        }
    }
}