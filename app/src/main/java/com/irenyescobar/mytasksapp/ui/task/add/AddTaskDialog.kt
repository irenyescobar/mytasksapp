package com.irenyescobar.mytasksapp.ui.task.add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.irenyescobar.mytasksapp.R
import com.irenyescobar.mytasksapp.repositories.TaskRepository
import com.irenyescobar.mytasksapp.ui.interfaces.AddTaskInterface
import com.irenyescobar.mytasksapp.ui.listeners.AddTaskListener
import com.irenyescobar.mytasksapp.ui.listeners.SaveTaskListener
import com.irenyescobar.mytasksapp.ui.interfaces.ViewInteractionInterface
import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelFactory
import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelType
import com.irenyescobar.mytasksapp.utils.customApp

class AddTaskDialog(private val viewInteractions: ViewInteractionInterface)
    :DialogFragment(),
    SaveTaskListener {

    val taskRepository: TaskRepository by lazy { customApp.component.taskRepository() }

    private var addTaskListener: AddTaskListener? = null
    private var addTaskFunction: AddTaskInterface? = null

    lateinit var viewModel: AddTaskViewModel
    var textView: TextView? = null
    var positiveButton: Button? = null
    var negativeButton: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View = LayoutInflater.from(context).inflate(R.layout.add_task_dialog, null)
        textView = view.findViewById(R.id.textNewTask)
        positiveButton = view.findViewById(R.id.positiveButton)
        negativeButton = view.findViewById(R.id.negativeButton)

        textView?.doAfterTextChanged {
            addTaskListener?.onChangeText(it.toString())
        }

        positiveButton?.setOnClickListener {
            add()
        }

        negativeButton?.setOnClickListener {
            dismiss()
        }

        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle(getString(R.string.title_add_task))
        alert.setView(view)
        return alert.create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }

    fun setup(){
        viewModel = ViewModelProviders.of(this,
            TaskViewModelFactory(
                customApp,
                taskRepository,
                this,
                TaskViewModelType.ADD_TASK,
                null
            )
        ).get(AddTaskViewModel::class.java)

        viewModel.text.observe(this, Observer { data ->
            data?.let {
               positiveButton?.isEnabled = it.isNotEmpty()
            }
        })

        addTaskListener = viewModel
        addTaskFunction = viewModel
    }

    fun add(){
        dialog?.hide()
        viewInteractions.showProgress()
        addTaskFunction?.addTask()
    }

    override fun onSaveTaskSuccess() {
        viewInteractions.hideProgress()
        dismiss()
    }

    override fun onSaveTaskError(message: String) {
        viewInteractions.run {
            hideProgress()
            showWarning(message)
        }
        dismiss()
    }
}