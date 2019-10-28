package com.irenyescobar.mytasksapp.repositories.listeners

interface SaveTaskErrorListener{
    fun onSaveTaskError(errorMessage: String)
}