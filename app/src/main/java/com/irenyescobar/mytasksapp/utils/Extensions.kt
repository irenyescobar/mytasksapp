@file:Suppress("DEPRECATION")

package com.irenyescobar.mytasksapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.DatePickerDialog
import android.app.Service
import android.content.Context
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.irenyescobar.mytasksapp.MyTasksApp
import com.irenyescobar.mytasksapp.data.room.entities.Task
import com.irenyescobar.mytasksapp.ui.listeners.DateSelectedListener
import java.text.SimpleDateFormat
import java.util.*




val LOCALE_BRAZIL = Locale("pt", "BR")

val Activity.customApp: MyTasksApp
    get() = castApp(application)

val Service.customApp: MyTasksApp
    get() = castApp(application)

fun castApp(app:Application): MyTasksApp = app as MyTasksApp

val Fragment.customApp: MyTasksApp
    get() = castApp(activity!!.application)


@SuppressLint("SetTextI18n")
fun EditText.setOnClickDatePicker(context:Context,
                                  year:Int,
                                  month:Int,
                                  day:Int,
                                  dateSelectedListener: DateSelectedListener) {
    this.setOnClickListener {
        val datePicker = DatePickerDialog(context,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
               dateSelectedListener.onDateSelected(getDate(year,monthOfYear,dayOfMonth))
            }, year, month, day
        )
        datePicker.show()
    }
}

fun Date.toDateTextFormatted():String{
    val formatter = SimpleDateFormat("dd/MM/yyyy",LOCALE_BRAZIL)
    return formatter.format(this)
}


fun String.toDate():Date?{
    val formatter = SimpleDateFormat("dd/MM/yyyy",LOCALE_BRAZIL)
    return formatter.parse(this)
}

fun Task.copy(): Task {
    return Task(id,name,completed,order,toDate)
}

fun getDate(year: Int, month: Int, day: Int): Date {
    val cal = Calendar.getInstance()
    cal.set(Calendar.YEAR, year)
    cal.set(Calendar.MONTH, month)
    cal.set(Calendar.DAY_OF_MONTH, day)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.time
}