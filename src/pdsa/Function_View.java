package pdsa;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Function_View {
    GUI gui;
    ArrayList<Integer> lineStartOffsets = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> wordPositions = new HashMap<>();

    public Function_View(GUI gui) {
        this.gui = gui;
    }

    public void findLine() {
        JTextArea textArea = gui.textArea;

        String input = JOptionPane.showInputDialog("Enter line number:");
        if (input != null) {
            try {
                int lineNumber = Integer.parseInt(input.trim());
                if (lineNumber < 1 || lineNumber > lineStartOffsets.size()) {
                    JOptionPane.showMessageDialog(null, "Invalid line number.");
                    return;
                }
                int startOffset = lineStartOffsets.get(lineNumber - 1);
                int endOffset;
                if (lineNumber < lineStartOffsets.size()) {
                    endOffset = lineStartOffsets.get(lineNumber);
                } else {
                    endOffset = textArea.getDocument().getLength();
                }
                highlightLine(startOffset, endOffset);
                textArea.setCaretPosition(startOffset);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        }
    }

    private void highlightLine(int startOffset, int endOffset) {
        JTextArea textArea = gui.textArea;

        Highlighter highlighter = textArea.getHighlighter();
        highlighter.removeAllHighlights();
        try {
            highlighter.addHighlight(startOffset, endOffset, new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void clearHighlight() {
        JTextArea textArea = gui.textArea;
        Highlighter highlighter = textArea.getHighlighter();
        highlighter.removeAllHighlights();
    }

    public void updateLineStartOffsets() {
        JTextArea textArea = gui.textArea;

        lineStartOffsets.clear();
        String text = textArea.getText();
        int lineStartOffset = 0;
        while (lineStartOffset < text.length()) {
            lineStartOffsets.add(lineStartOffset);
            int lineEndOffset = text.indexOf('\n', lineStartOffset);
            if (lineEndOffset == -1) {
                break;
            }
            lineStartOffset = lineEndOffset + 1;
        }
    }
    
    public void buildWordPositions() {
        JTextArea textArea = gui.textArea;
        String text = textArea.getText();
        wordPositions.clear();
        String[] words = text.split("\\W+"); 

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int index = text.indexOf(word);
            while (index >= 0) {
                wordPositions.computeIfAbsent(word, k -> new ArrayList<>()).add(index);
                index = text.indexOf(word, index + word.length());
            }
        }
    }

    public void findWord() {
        JFrame frame = new JFrame("Find Word");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JTextField wordField = new JTextField();
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        frame.add(new JLabel("Enter the word to find:"));
        frame.add(wordField);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wordToFind = wordField.getText();
                buildWordPositions(); // Build the word positions map
                highlightWord(wordToFind);
                frame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private void highlightWord(String word) {
        JTextArea textArea = gui.textArea;
        Highlighter highlighter = textArea.getHighlighter();
        highlighter.removeAllHighlights(); // Remove existing highlights

        ArrayList<Integer> positions = wordPositions.get(word);
        if (positions != null) {
            for (int startOffset : positions) {
                try {
                    int endOffset = startOffset + word.length();
                    highlighter.addHighlight(startOffset, endOffset, new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Word not found.");
        }
    }
}
