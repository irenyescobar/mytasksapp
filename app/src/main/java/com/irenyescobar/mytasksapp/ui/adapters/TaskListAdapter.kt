package com.irenyescobar.mytasksapp.ui.adapters

import android.content.Context
import android.graphics.Color
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
import java.util.Collections.swap
import android.view.MotionEvent

class TaskListAdapter (
    private val context: Context,
    private val selectedListener: SelectedListener<Task>,
    private val checkedChangeListener: CheckedChangeListener<Task>,
    private val startDragListener: StartDragListener
) :
    RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>(),
    ItemMoveCallback.ItemTouchHelperContract {

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

            rowView.tag = current

            rowView.setOnTouchListener(View.OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    startDragListener.requestDrag(holder)
                }
                false
            })

            rowView.setOnClickListener{
                val item = it.tag as Task
                selectedListener.onSelected(item)
            }

            checkBox.isChecked = current.completed
            checkBox.tag = current
            checkBox.setOnClickListener{
                val item = it.tag as Task
                item.completed = !item.completed
                checkedChangeListener.onCheckedChange(item)
            }
            textViewName.text = current.name

            textViewToDate.text = context.getString(R.string.task_toDate_null_message)
            
            current.toDate?.let { 
                textViewToDate.text = it.toDateTextFormatted()
            }       
        }
    }

    override fun getItemCount() = data.size

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(myViewHolder: TaskViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(myViewHolder: TaskViewHolder) {
        myViewHolder.itemView.setBackgroundColor(Color.WHITE)
    }

    inner class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)  {
        val rowView: View = itemView
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewToDate: TextView = itemView.findViewById(R.id.textViewToDate)
    }
}