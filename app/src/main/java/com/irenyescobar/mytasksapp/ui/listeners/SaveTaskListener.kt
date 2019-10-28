package com.irenyescobar.mytasksapp.ui.listeners

interface SaveTaskListener{
    fun onSaveTaskSuccess()
    fun onSaveTaskError(message:String)
}