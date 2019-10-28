package com.irenyescobar.mytasksapp.ui.interfaces


interface ViewInteractionInterface {
    fun showMessageError(errorMessage:String)
    fun showWarning(message:String)
    fun showProgress()
    fun hideProgress()
}