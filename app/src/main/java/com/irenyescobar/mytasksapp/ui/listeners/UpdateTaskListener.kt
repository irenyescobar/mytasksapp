package com.irenyescobar.mytasksapp.ui.listeners

import java.util.*

interface UpdateTaskListener {
    fun onChangeTaskName(name:String)
    fun onChangeTaskToDate(toDate: Date?)
}