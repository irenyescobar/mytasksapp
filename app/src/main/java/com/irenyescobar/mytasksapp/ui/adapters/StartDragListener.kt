package com.irenyescobar.mytasksapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView

interface StartDragListener {
    fun requestDrag(viewHolder: RecyclerView.ViewHolder)
}