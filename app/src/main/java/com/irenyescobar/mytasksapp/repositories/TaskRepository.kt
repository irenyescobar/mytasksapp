package com.irenyescobar.mytasksapp.repositories

import androidx.lifecycle.LiveData
import com.irenyescobar.mytasksapp.data.api.retrofit.MyTasksApi
import com.irenyescobar.mytasksapp.data.room.dao.TaskDao
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.repositories.exceptions.SaveTaskRemoteError
import com.irenyescobar.mytasksapp.repositories.listeners.ImportDataCompletedListener
import com.irenyescobar.mytasksapp.repositories.listeners.ImportDataErrorListener
import com.irenyescobar.mytasksapp.repositories.listeners.SaveTaskCompletedListener
import com.irenyescobar.mytasksapp.repositories.listeners.SaveTaskErrorListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class TaskRepository(
    private val api:MyTasksApi,
    private val taskDao: TaskDao) {

    private var importDataCompletedListener:ImportDataCompletedListener? = null
    private var importDataErrorListener:ImportDataErrorListener? = null
    private var saveTaskErrorListener:SaveTaskErrorListener? = null
    private var saveTaskCompletedListener:SaveTaskCompletedListener? = null

    private var today:LiveData<List<Task>> = taskDao.getAll(getCurrentDate())
    private var completeds :LiveData<List<Task>> = taskDao.getAll(true)
    private var pendings:LiveData<List<Task>> = taskDao.getAll(false)

    fun getPendings():LiveData<List<Task>>{
        return pendings
    }

    fun getCompleteds():LiveData<List<Task>>{
        return completeds
    }

    fun getToday():LiveData<List<Task>>{
        return today
    }

    fun getById(id:Long):LiveData<Task>{
        return taskDao.getById(id)
    }

    suspend fun save(task:Task){
         if(task.id == 0L){
             save(task,true)
         }else{
             save(task,false)
         }
    }

    suspend fun save(task:Task, isNew :Boolean){
        withContext(Dispatchers.IO){

            try {
                taskDao.save(task)
                apiSave(task,isNew)
                saveCompleted(true)
            }catch (error:SaveTaskRemoteError){
                error.message?.apply {
                    saveTaskError(this)
                }
            }
            catch (ex:Exception){
                saveCompleted(false)
                saveTaskError("Ocorreu um erro ao salvar os dados na base local")
            }
        }
    }

    suspend fun apiSave(task:Task, isNew :Boolean){
        try {
            if(isNew){
                api.insert(task)
            }else{
                api.update(task.id,task)
            }
        }catch (ex:Exception){
            throw SaveTaskRemoteError(ex)
        }
    }


    suspend fun importData(){
        withContext(Dispatchers.IO){
            try {
                val list: List<Task> = api.get()
                list.forEach {
                    taskDao.insert(it)
                    importDataCompleted(true)
                }
            }catch (ex:Exception){
                importDataCompleted(false)
                importDataError(ex)
            }
        }
    }

    private fun getCurrentDate(): Long{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time.time
    }

    private suspend fun importDataCompleted(success:Boolean){
        importDataCompletedListener?.let {
            withContext(Dispatchers.Main) {
                it.onImportDataCompleted(success)
            }
        }
    }

    private suspend fun importDataError(error: java.lang.Exception){
        importDataErrorListener?.let {
            withContext(Dispatchers.Main) {
                it.onImportDataError(error)
            }
        }
    }

    private suspend fun saveTaskError(errorMessage: String){
        saveTaskErrorListener?.let {
            withContext(Dispatchers.Main) {
                it.onSaveTaskError(errorMessage)
            }
        }
    }

    private suspend fun saveCompleted(success:Boolean){
        saveTaskCompletedListener?.let {
            withContext(Dispatchers.Main) {
                it.onSaveTaskCompleted(success)
            }
        }
    }

    fun setImportDataErrorListener(listener: ImportDataErrorListener){
        importDataErrorListener = listener
    }

    fun setImportDataCompletedListener(listener: ImportDataCompletedListener){
        importDataCompletedListener = listener
    }

    fun setSaveTaskErrorListener(listener: SaveTaskErrorListener){
        saveTaskErrorListener = listener
    }

    fun setSaveTaskCompletedListener(listener: SaveTaskCompletedListener){
        saveTaskCompletedListener = listener
    }
}