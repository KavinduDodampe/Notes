import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class NotesApp {

    public static void main(String[] args) {
        // Set Nimbus Look and Feel for a modern appearance
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Create the main frame
        JFrame frame = new JFrame("Notes Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create the main panel with a vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Notes Panel
        JPanel notesPanel = new JPanel();
        notesPanel.setBorder(BorderFactory.createTitledBorder("Notes"));
        notesPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for fine-grained control

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Title:");
        notesPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField(20); // Fixed preferred size
        titleField.setMaximumSize(new Dimension(200, 25)); // Prevents the text field from expanding
        notesPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;

        JLabel contentLabel = new JLabel("Content:");
        notesPanel.add(contentLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;

        JTextArea noteArea = new JTextArea(10, 40);  // Text area for the note content
        notesPanel.add(new JScrollPane(noteArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        JButton addNoteButton = new JButton("Add Note");
        notesPanel.add(addNoteButton, gbc);

        gbc.gridx = 1;
        JButton viewNotesButton = new JButton("View Notes");
        notesPanel.add(viewNotesButton, gbc);

        mainPanel.add(notesPanel);

        // Wrap the mainPanel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Add the scroll pane to the frame
        frame.add(scrollPane);

        // Initialize Notes Manager
        NotesManager notesManager = new NotesManager();

        // Load notes at startup
        notesManager.loadNotes("Notes.txt");

        // Add Note Button Action
        addNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String content = noteArea.getText();
                if (!title.isEmpty() && !content.isEmpty()) {
                    notesManager.addNote(new Note(title, content));
                    titleField.setText("");  // Clear the title field
                    noteArea.setText("");  // Clear the text area
                    JOptionPane.showMessageDialog(frame, "Note added!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Both title and content must be filled!");
                }
            }
        });

        // View Notes Button Action
        viewNotesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Note> notes = notesManager.getAllNotes();
                if (notes.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No notes to display.");
                    return;
                }
                StringBuilder allNotes = new StringBuilder();
                for (Note note : notes) {
                    allNotes.append("Title: ").append(note.getTitle()).append("\n")
                            .append("Content: ").append(note.getContent()).append("\n\n");
                }
                JOptionPane.showMessageDialog(frame, allNotes.toString());
            }
        });

        // Add a window listener to save notes on close
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                notesManager.saveNotes("Notes.txt");
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}
