package whats.app.notesapplication.viewmodel

import android.app.Application
import android.app.Dialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import whats.app.notesapplication.db.NoteDatabase
import whats.app.notesapplication.db.NoteRepo
import whats.app.notesapplication.entity.Note

class NoteViewModel(application: Application): AndroidViewModel(application) {
    val allNotes: LiveData<List<Note>>
    val noteRepo: NoteRepo

    init {
        val dao = NoteDatabase.getInstance(application).getNotesDao()
        noteRepo = NoteRepo(dao)
        allNotes = noteRepo.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        noteRepo.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        noteRepo.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        noteRepo.insert(note)
    }
}