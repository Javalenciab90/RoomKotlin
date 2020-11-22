package com.java90.roomrxkotlin.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.java90.roomrxkotlin.R
import com.java90.roomrxkotlin.databinding.ItemCardBinding
import com.java90.roomrxkotlin.model.Note
import com.java90.roomrxkotlin.utils.NoteDiffCallback

// RecyclerView.Adapter<**>()
// **RecyclerView.ViewHolder -> let it like this. (General)
// To handle different ViewHolders

class NoteAdapter(private val notes: ArrayList<Note>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var checkedRadioButton: CompoundButton? = null
    private lateinit var itemSelected: (Note) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCardBinding>(inflater, R.layout.item_card, parent, false)
        return ItemViewHolder(binding)
    }

    fun setNotes(newNotes: List<Note>) {
        //Check if data batch is different from current
        val diffCallback = NoteDiffCallback(notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        notes.clear()
        notes.addAll(newNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Define what to do depend on which type of ViewHolder, can use if,else to choose.
        holder as ItemViewHolder
        val note = notes[position]
        holder.bin(note)
    }

    // This is a type of ViewHolder
    inner class ItemViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bin(item: Note) {
            with(binding) {
                note = item

                textViewNumber.text = item.noteId.toString()
                textViewTitle.text = item.title

                radioButtonSelect.setOnCheckedChangeListener(checkedChangeListener)
                if (radioButtonSelect.isChecked) checkedRadioButton = radioButtonSelect
                radioButtonSelect.setOnClickListener { if(::itemSelected.isInitialized) itemSelected.invoke(item) }
            }
        }
    }

    fun setOnItemSelected( fn: (Note) -> Unit) {
        itemSelected = fn
    }

    private val checkedChangeListener = CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
        checkedRadioButton?.apply { setChecked(!isChecked)}
        checkedRadioButton = compoundButton.apply {
            setChecked(isChecked)
        }
    }
}