package com.java90.roomrxkotlin.utils

import androidx.recyclerview.widget.DiffUtil
import com.java90.roomrxkotlin.model.Note

class NoteDiffCallback(
        private val oldList: List<Note>,
        private val newList: List<Note>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
}