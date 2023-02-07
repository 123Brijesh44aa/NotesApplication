package whats.app.notesapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import whats.app.notesapplication.entity.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>


}