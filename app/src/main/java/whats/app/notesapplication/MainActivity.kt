package whats.app.notesapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import whats.app.notesapplication.components.NoteAdapter
import whats.app.notesapplication.components.OnNoteDeleteClickListener
import whats.app.notesapplication.components.OnNoteUpdateClickListener
import whats.app.notesapplication.entity.Note
import whats.app.notesapplication.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity(), OnNoteUpdateClickListener, OnNoteDeleteClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit var fabButton: FloatingActionButton
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.noteRecyclerView)
        fabButton = findViewById(R.id.floatingButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = NoteAdapter(this,this,this)
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        fabButton.setOnClickListener{
            val intent = Intent(this@MainActivity, ActivityAddEdit::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onDeleteNoteClick(note: Note) {
        viewModel.deleteNote(note)
        Snackbar.make(findViewById(R.id.mainActivity),"${note.noteTitle} deleted",Snackbar.LENGTH_SHORT).show()
    }

    override fun onUpdateNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, ActivityAddEdit::class.java)
        intent.putExtra("NOTE_TYPE","EDIT")
        intent.putExtra("NOTE_TITLE",note.noteTitle)
        intent.putExtra("NOTE_DESCRIPTION",note.noteDescription)
        intent.putExtra("NOTE_ID",note.id)
        startActivity(intent)
        this.finish()
    }
}