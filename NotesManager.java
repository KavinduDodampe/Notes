import java.util.ArrayList;
import java.util.List;

public class NotesManager
{
    private List<Note> notes;

    public NotesManager()
    {
        notes = new ArrayList<>();
    }

    public void addNote(Note note)
    {
        notes.add(note);
    }

    public void deleteNote(Note note)
    {
        notes.remove(note);
    }

    public List<Note> getAllNotes()
    {
        return notes;
    }

    public void saveNotes(String filename) 
    {
        FileManager.saveNotestoFile(notes, filename);
    }

    public void loadNotes(String filename) 
    {
        notes = FileManager.loadNotesFromFile(filename);
    }
}