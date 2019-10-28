package com.irenyescobar.mytasksapp.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.irenyescobar.mytasksapp.data.room.entities.Task

@Dao
abstract class TaskDao {

    @Query("SELECT * from Task ORDER BY `order` ASC")
    abstract fun getAll(): LiveData<List<Task>>

    @Query("SELECT * from Task WHERE completed = 0 AND toDate = :today ORDER BY `order` ASC")
    abstract fun getAll(today: Long): LiveData<List<Task>>

    @Query("SELECT * from Task WHERE completed = :completed ORDER BY `order` ASC")
    abstract fun getAll(completed:Boolean): LiveData<List<Task>>

    @Query("SELECT * from Task WHERE id =:id")
    abstract fun getById(id:Long): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Task)

    @Query("SELECT id from Task ORDER BY id DESC LIMIT 1")
    abstract suspend fun getLastId(): Long?

    @Query("SELECT `order` from Task ORDER BY `order` DESC LIMIT 1")
    abstract suspend fun getLastOrder(): Int?

    @Transaction
    open suspend fun save(task: Task):Task{
        if(task.id == 0L){

            var id = 1L
            val lastId = getLastId()
            lastId?.let {
                id = it +1
            }
            task.id = id
        }
        if (task.order == 0) {

            var order = 1
            val lastOrder = getLastOrder()
            lastOrder?.let {
                order = lastOrder +1
            }
            task.order = order
        }
        insert(task)
        return task
    }

}