package com.irenyescobar.mytasksapp.ui.interfaces

import com.irenyescobar.mytasksapp.data.room.entities.Task

interface OpenTaskInterface {
    fun openTask(task: Task)
}