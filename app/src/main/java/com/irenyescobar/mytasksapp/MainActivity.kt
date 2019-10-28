package com.irenyescobar.mytasksapp

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.receivers.DataImportBroadcastReceiver
import com.irenyescobar.mytasksapp.services.DataImportService
import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelType
import com.irenyescobar.mytasksapp.ui.interfaces.OpenTaskInterface
import com.irenyescobar.mytasksapp.ui.interfaces.WallpaperSettingsInterface
import com.irenyescobar.mytasksapp.ui.task.add.AddTaskDialog
import com.irenyescobar.mytasksapp.ui.task.update.UpdateTaskActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : BaseActivity(),
    DataImportBroadcastReceiver.DataImportErrorListener,
    WallpaperSettingsInterface,
    OpenTaskInterface
{
    private val dataImportBroadcastReceiver:DataImportBroadcastReceiver by lazy { DataImportBroadcastReceiver(this) }
    private val addTaskDialog: AddTaskDialog = AddTaskDialog(this)
    private var wallpaperAnimation: WallpaperAnimation = WallpaperAnimation(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_today, R.id.navigation_pending, R.id.navigation_completed
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fab.setOnClickListener{
            showAddTaskDialog()
        }

        startImportDataService()

        wallpaperAnimation.start()

    }


    override fun onDataImportError(errorMessage: String) {
        showDialogMessage(errorMessage)
    }

    override fun openTask(task: Task) {
        startActivity(UpdateTaskActivity.newInstance(this,task))
    }

    private fun startImportDataService(){
        registerReceiver(dataImportBroadcastReceiver, IntentFilter(DataImportService.DATA_IMPORT_ERROR_INTENT))
        startService(Intent(this, DataImportService::class.java))
    }

    private fun showAddTaskDialog(){
        addTaskDialog.show(supportFragmentManager,"")
    }

    override fun setWallpaper(resourceId: Int) {
        try {
            container.background = resources.getDrawable(resourceId,null)
        }catch (error:Exception){
            showWarning(error.localizedMessage)
        }

    }
}
