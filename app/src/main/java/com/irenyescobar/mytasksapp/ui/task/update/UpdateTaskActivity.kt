package com.irenyescobar.mytasksapp.ui.task.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.irenyescobar.mytasksapp.BaseActivity
import com.irenyescobar.mytasksapp.R
import com.irenyescobar.mytasksapp.data.room.entities.Task

class UpdateTaskActivity : BaseActivity() {

    private var taskId:Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        setContentView(R.layout.task_activity)

        taskId = intent.getLongExtra(TASK_ID,-1)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UpdateTaskFragment.newInstance(taskId))
                .commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object{
        const val TASK_ID = "TASK_ID"
        const val TASK_NAME = "TASK_NAME"
        @JvmStatic
        fun newInstance(context: Context, task: Task) =
            Intent(context, UpdateTaskActivity::class.java).apply {
                putExtra(TASK_ID,task.id)
                putExtra(TASK_NAME,task.name)
            }
    }

}
