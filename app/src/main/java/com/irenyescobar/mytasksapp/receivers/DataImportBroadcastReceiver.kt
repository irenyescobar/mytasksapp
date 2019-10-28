package com.irenyescobar.mytasksapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.irenyescobar.mytasksapp.services.DataImportService

class DataImportBroadcastReceiver(val listener: DataImportErrorListener):BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent) {
        val action = intent.action
        if (action == DataImportService.DATA_IMPORT_ERROR_INTENT) {
            val message = intent.getStringExtra(DataImportService.DATA_IMPORT_ERROR_MESSAGE)
            message?.let {
                listener.onDataImportError(it)
            }
        }
    }

    interface DataImportErrorListener{
        fun onDataImportError(errorMessage:String)
    }
}