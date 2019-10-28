package com.irenyescobar.mytasksapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Task(@PrimaryKey var id:Long = 0,
           var name: String = "",
           var completed:Boolean = false,
           var order:Int = 0,
           var toDate: Date? = null)
