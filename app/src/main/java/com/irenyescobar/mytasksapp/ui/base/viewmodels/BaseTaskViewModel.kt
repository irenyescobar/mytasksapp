package com.irenyescobar.mytasksapp.ui.base.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.irenyescobar.mytasksapp.repositories.TaskRepository

abstract class BaseTaskViewModel (application: Application,
                                  val repository: TaskRepository): AndroidViewModel(application)