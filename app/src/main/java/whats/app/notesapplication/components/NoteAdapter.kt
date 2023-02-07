package whats.app.notesapplication.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import whats.app.notesapplication.R
import whats.app.notesapplication.entity.Note

class NoteAdapter(val context: Context,
                  val onNoteDeleteClickListener: OnNoteDeleteClickListener,
                  val onNoteUpdateClickListener: OnNoteUpdateClickListener):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_items,parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.noteTitleTv.text = allNotes.get(position).noteTitle
        holder.noteDescriptionTv.text = allNotes.get(position).noteDescription

        holder.deleteIv.setOnClickListener {
            onNoteDeleteClickListener.onDeleteNoteClick(allNotes.get(position))
        }

        holder.itemView.setOnClickListener{
            onNoteUpdateClickListener.onUpdateNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    //View Holder class

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val noteTitleTv = itemView.findViewById<TextView>(R.id.noteTitle)
        val noteDescriptionTv = itemView.findViewById<TextView>(R.id.noteDescription)
        val deleteIv = itemView.findViewById<ImageView>(R.id.deleteButton)
    }
}

interface OnNoteDeleteClickListener{
    fun onDeleteNoteClick(note: Note)
}

interface OnNoteUpdateClickListener{
    fun onUpdateNoteClick(note: Note)
}