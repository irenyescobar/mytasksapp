package com.irenyescobar.mytasksapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.annotation.Nullable
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.repositories.listeners.ImportDataErrorListener
import com.irenyescobar.mytasksapp.utils.customApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DataImportService: Service(), CoroutineScope, ImportDataErrorListener {

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + coroutineJob

    val component by lazy { customApp.component }
    val taskRepository: TaskRepository by lazy { component.taskRepository() }

    override fun onCreate() {
        super.onCreate()
        taskRepository.setImportDataErrorListener(this)
    }

    override fun onStartCommand(@Nullable intent: Intent?, flags: Int, startId: Int): Int {
        launch {
            taskRepository.importData()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        coroutineJob.cancel()
    }

    override fun onImportDataError(error: Exception) {
        reportErrorOccurrence("Não foi possível importar os dados.")
    }

    private fun reportErrorOccurrence(message:String){
        val intent = Intent(DATA_IMPORT_ERROR_INTENT)
        intent.putExtra(DATA_IMPORT_ERROR_MESSAGE, message)
        sendBroadcast(intent)
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object{
        const val DATA_IMPORT_ERROR_INTENT = "DATA_IMPORT_ERROR_INTENT"
        const val DATA_IMPORT_ERROR_MESSAGE = "DATA_IMPORT_ERROR_MESSAGE"
    }
}
