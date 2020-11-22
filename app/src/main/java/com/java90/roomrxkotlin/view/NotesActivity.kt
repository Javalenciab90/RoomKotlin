package com.java90.roomrxkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.java90.roomrxkotlin.R
import com.java90.roomrxkotlin.databinding.ActivityMainBinding
import com.java90.roomrxkotlin.model.Note
import com.java90.roomrxkotlin.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesActivity : AppCompatActivity() {

    private val viewModel: NoteViewModel by viewModels()

    private lateinit var noteAdapter: NoteAdapter
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel

        binding.saveButton.isEnabled = false
        binding.saveButton.isClickable = false

        setUpRecyclerView(binding)
        setObservers(binding)

        binding.saveButton.setOnClickListener {
            viewModel.addNote(saveData(binding))
        }

        noteAdapter.setOnItemSelected {
            note = it
            binding.saveButton.isEnabled = true
            binding.saveButton.isClickable = true
        }

        binding.goToDetail.setOnClickListener { view ->
            Snackbar.make(view, note!!.description, Snackbar.LENGTH_LONG ).show()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteNote(note!!)
        }
    }

    private fun setUpRecyclerView(binding: ActivityMainBinding) {
        noteAdapter = NoteAdapter(arrayListOf())
        binding.recyclerNotes.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(this@NotesActivity)
        }
    }

    private fun saveData(binding: ActivityMainBinding) : Note {
        val id = binding.editTextID.text.toString().toInt()
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextDescription.text.toString()
        return Note(id, title, description)
    }

    private fun setObservers(binding: ActivityMainBinding) {
        viewModel.listNotes.observe(this, Observer {
            val adapter = binding.recyclerNotes.adapter as NoteAdapter
                adapter.setNotes(it)
        })
    }
}