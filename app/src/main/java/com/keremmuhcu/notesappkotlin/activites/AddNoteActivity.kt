package com.keremmuhcu.notesappkotlin.activites

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.keremmuhcu.notesappkotlin.R
import com.keremmuhcu.notesappkotlin.databinding.ActivityAddNoteBinding
import com.keremmuhcu.notesappkotlin.models.Note
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {

            old_note = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(old_note.title)
            binding.etNote.setText(old_note.note)
            isUpdate = true
        }catch (e : Exception) {
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {

            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdate) {
                    note = Note(
                        old_note.id, title, note_desc, formatter.format(Date())
                    )

                } else {
                    note = Note(
                        null, title, note_desc, formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this@AddNoteActivity, "Please Enter Some Data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }


    }


    override fun onBackPressed() {
        val alert = AlertDialog.Builder(this@AddNoteActivity)

        alert.setTitle("There may be unsaved changes.")
        alert.setMessage("Are You Sure?")

        alert.setPositiveButton("Yes") {dialog, which ->
            finish()
        }

        alert.setNegativeButton("No") {dialog, which ->

        }

        alert.show()
    }


}