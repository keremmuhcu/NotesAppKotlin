package com.keremmuhcu.notesappkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchBar
import com.keremmuhcu.notesappkotlin.R
import com.keremmuhcu.notesappkotlin.models.Note
import kotlin.random.Random

class NoteAdapter(private val context : Context, val listener : NotesClickListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true

        holder.note.text = currentNote.note

        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))

        holder.notes_layout.setOnClickListener {

            listener.onItemClicked(notesList[holder.adapterPosition])

        }

        holder.notes_layout.setOnLongClickListener {

            listener.onLongItemClicked(notesList[holder.adapterPosition],holder.notes_layout)
            true
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateList(newList : List<Note>) {

        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)

        notifyDataSetChanged()
    }

    fun filterList(search : String) {

        notesList.clear()

        for (item in fullList) {

            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true) {
                notesList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    fun randomColor() : Int {

        val listOfColors = ArrayList<Int>()
        listOfColors.add(R.color.NoteColor1)
        listOfColors.add(R.color.NoteColor2)
        listOfColors.add(R.color.NoteColor3)
        listOfColors.add(R.color.NoteColor4)
        listOfColors.add(R.color.NoteColor5)
        listOfColors.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(listOfColors.size)

        return listOfColors[randomIndex]
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val note = itemView.findViewById<TextView>(R.id.tvNote)
        val date = itemView.findViewById<TextView>(R.id.tvDate)
    }

    interface NotesClickListener {
        fun onItemClicked(note : Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}