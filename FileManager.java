import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    
    // Method to save notes to a file
    public static void saveNotestoFile(List<Note> notes, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Note note : notes) {
                writer.write(note.getTitle() + ":" + note.getContent());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load notes from a file
    public static List<Note> loadNotesFromFile(String filename) {
        List<Note> notes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String title = parts[0];
                    String content = parts[1];
                    notes.add(new Note(title, content));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes;
    }
}
