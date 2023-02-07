package whats.app.notesapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import whats.app.notesapplication.entity.Note
import whats.app.notesapplication.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class ActivityAddEdit : AppCompatActivity() {

    lateinit var editNoteTitle: EditText
    lateinit var editNoteDesc: EditText
    lateinit var addUpdateBtn: Button
    lateinit var viewModel: NoteViewModel
    var noteId = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        editNoteTitle = findViewById(R.id.editNoteTitle)
        editNoteDesc = findViewById(R.id.editNoteDescription)
        addUpdateBtn = findViewById(R.id.saveNote)

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("NOTE_TYPE")
        if (noteType.equals("EDIT")){
            val noteTitle = intent.getStringExtra("NOTE_TITLE")
            val noteDesc = intent.getStringExtra("NOTE_DESCRIPTION")
            noteId = intent.getIntExtra("NOTE_ID",0)

            addUpdateBtn.text = "Update Note"
            editNoteTitle.setText(noteTitle)
            editNoteDesc.setText(noteDesc)
        }
        else{
            addUpdateBtn.text = "Save Note"
        }

        addUpdateBtn.setOnClickListener{
            val noteTitle = editNoteTitle.text.toString()
            val noteDesc = editNoteDesc.text.toString()

            if (noteType.equals("EDIT")){
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val format = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentTime: String = format.format(Date())
                    val updateNote  = Note(noteTitle,noteDesc,currentTime)
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Snackbar.make(findViewById(R.id.addUpdateActivity),"Note Updated successfully",Snackbar.LENGTH_SHORT).show()
                }
            }
            else{
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val format = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentTime: String = format.format(Date())
                    viewModel.addNote(Note(noteTitle,noteDesc,currentTime))
                    Snackbar.make(findViewById(R.id.addUpdateActivity),"Note Added successfully",Snackbar.LENGTH_SHORT).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }
    }
}