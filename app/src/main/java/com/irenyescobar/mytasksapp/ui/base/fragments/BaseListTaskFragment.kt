package com.irenyescobar.mytasksapp.ui.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irenyescobar.mytasksapp.R
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.ui.base.viewmodels.BaseListTaskViewModel
import com.irenyescobar.mytasksapp.ui.interfaces.OpenTaskInterface
import com.irenyescobar.mytasksapp.ui.adapters.TaskListAdapter
import com.irenyescobar.mytasksapp.ui.interfaces.WallpaperSettingsInterface
import com.irenyescobar.mytasksapp.ui.listeners.CheckedChangeListener
import com.irenyescobar.mytasksapp.ui.listeners.SelectedListener
import com.irenyescobar.mytasksapp.ui.interfaces.ViewInteractionInterface
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener
import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelFactory
import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelType
import com.irenyescobar.mytasksapp.ui.task.list.today.TodayViewModel
import com.irenyescobar.mytasksapp.utils.customApp

abstract class BaseListTaskFragment(@TaskViewModelType viewModelType: Int): BaseSaveTaskFragment(viewModelType),
    SelectedListener<Task>,
    CheckedChangeListener<Task>{

    private lateinit var viewModel: BaseListTaskViewModel
    private lateinit var adapter: TaskListAdapter
    private lateinit var recyclerView: RecyclerView

    private var openTaskFunction: OpenTaskInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)
        recyclerView = root.findViewById(R.id.recyclerview)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OpenTaskInterface) {
            openTaskFunction = context
        } else {
            throw RuntimeException("$context must implement OpenTaskInterface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        openTaskFunction = null
    }

    fun setup(){
        adapter = TaskListAdapter(context!!,this,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        viewModel = ViewModelProviders.of(this,
            TaskViewModelFactory(
                customApp,
                taskRepository,
                this, viewModelType,
                null
            )
        ).get(TodayViewModel::class.java)

        viewModel.data.observe(this, Observer { data ->
            data?.let { adapter.setData(it) }
        })
    }

    fun enableView(enable: Boolean){
        recyclerView.isEnabled = enable
    }

    override fun onSelected(item: Task) {
        openTaskFunction?.openTask(item)
    }

    override fun onCheckedChange(item: Task) {
        viewInteractions?.showProgress()
        enableView(false)
        viewModel.save(item)
    }

    override fun onSaveTaskSuccess() {
        super.onSaveTaskSuccess()
        enableView(true)
    }

    override fun onSaveTaskError(message: String) {
        super.onSaveTaskError(message)
        enableView(true)
    }
}