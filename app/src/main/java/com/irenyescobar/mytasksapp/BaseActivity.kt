package com.irenyescobar.mytasksapp

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.irenyescobar.mytasksapp.ui.interfaces.ViewInteractionInterface
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity(), ViewInteractionInterface {

    override fun showWarning(message: String) {
        Snackbar.make(container, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun showMessageError(errorMessage: String) {
        showDialogMessage(errorMessage)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }


    fun showDialogMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage(message)
            .setPositiveButton( "Ok")
            { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}