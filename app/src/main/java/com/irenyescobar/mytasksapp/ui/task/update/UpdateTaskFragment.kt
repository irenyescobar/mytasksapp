package com.irenyescobar.mytasksapp.ui.task.update


import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import com.irenyescobar.mytasksapp.R
import com.irenyescobar.mytasksapp.ui.base.fragments.BaseSaveTaskFragment
import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelFactory
import com.irenyescobar.mytasksapp.ui.helpers.TaskViewModelType
import com.irenyescobar.mytasksapp.ui.interfaces.UpdateTaskInterface
import com.irenyescobar.mytasksapp.ui.listeners.DateSelectedListener
import com.irenyescobar.mytasksapp.ui.listeners.UpdateTaskListener
import com.irenyescobar.mytasksapp.utils.customApp
import com.irenyescobar.mytasksapp.utils.setOnClickDatePicker
import com.irenyescobar.mytasksapp.utils.toDateTextFormatted
import java.util.*


class UpdateTaskFragment(val taskId: Long)
    : BaseSaveTaskFragment(TaskViewModelType.UPDATE_TASK),
    DateSelectedListener{

    private lateinit var editTextName: EditText
    private lateinit var editTextToDate: EditText

    private lateinit var viewModel: UpdateTaskViewModel
    private var updateTaskListener: UpdateTaskListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView = inflater.inflate(R.layout.task_fragment, container, false)
        setupView(rootView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
    }

    fun setupViewModel(){

        viewModel = ViewModelProviders.of(this,
            TaskViewModelFactory(
                customApp,
                taskRepository,
                this,
                viewModelType,
                taskId
            )
        ).get(UpdateTaskViewModel::class.java)

        updateTaskListener = viewModel

        viewModel.task.observe(this, androidx.lifecycle.Observer { data ->
            data?.let { it ->
                editTextName.setText(it.name)
                it.toDate?.apply {
                    editTextToDate.setText(toDateTextFormatted())
                }
            }
        })
    }

    fun setupView(view:View){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        editTextName = view.findViewById(R.id.editTextName)
        editTextName.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                textNameChanged()
                return@OnKeyListener true
            }
            false
        })

        editTextToDate = view.findViewById(R.id.editTextToDate)
        editTextToDate.setOnClickDatePicker(requireContext(),year,month,day,this)
    }

    override fun onDateSelected(date: Date) {
        updateTaskListener?.apply {
            enableView(false)
            viewInteractions?.showProgress()
            onChangeTaskToDate(date)
        }
    }

    fun textNameChanged(){
        updateTaskListener?.apply {
            viewInteractions?.showProgress()
            enableView(false)
            onChangeTaskName(editTextName.text.toString())
        }
    }

    fun enableView(enable: Boolean){
        editTextName.isEnabled = enable
        editTextToDate.isEnabled = enable
    }

    override fun onSaveTaskSuccess() {
        super.onSaveTaskSuccess()
        enableView(true)
    }

    override fun onSaveTaskError(message: String) {
        super.onSaveTaskError(message)
        enableView(true)
    }

    companion object {
        fun newInstance(taskId:Long) = UpdateTaskFragment(taskId)
    }
}
