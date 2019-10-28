package com.irenyescobar.mytasksapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irenyescobar.mytasksapp.R
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.ui.listeners.CheckedChangeListener
import com.irenyescobar.mytasksapp.ui.listeners.SelectedListener
import com.irenyescobar.mytasksapp.utils.toDateTextFormatted

class TaskListAdapter (
    private val context: Context,
    private val selectedListener: SelectedListener<Task>,
    private val checkedChangeListener: CheckedChangeListener<Task>
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data = emptyList<Task>()

    internal fun setData(items: List<Task>) {
        this.data = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = data[position]
        holder.run {

            itemView.tag = current

            itemView.setOnClickListener{
                val item = itemView.tag as Task
                selectedListener.onSelected(item)
            }

            checkBox.isChecked = current.completed
            checkBox.tag = current
            checkBox.setOnClickListener{
                val item = it.tag as Task
                item.completed = !item.completed
                checkedChangeListener.onCheckedChange(item)
            }
            /*checkBox.setOnCheckedChangeListener { view, isChecked ->

            }*/
            textViewName.text = current.name

            textViewToDate.text = context.getString(R.string.task_toDate_null_message)
            
            current.toDate?.let { 
                textViewToDate.text = it.toDateTextFormatted()
            }       
        }
    }

    override fun getItemCount() = data.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewToDate: TextView = itemView.findViewById(R.id.textViewToDate)
    }
}