package com.irenyescobar.mytasksapp.ui.interfaces

import com.irenyescobar.mytasksapp.data.room.entities.Task

interface UpdateTaskInterface {
    fun updateTask(task:Task)
}