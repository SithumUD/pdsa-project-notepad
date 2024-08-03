package pdsa;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.util.ArrayList;

public class Function_View {
    GUI gui;
    ArrayList<Integer> lineStartOffsets = new ArrayList<>();

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
            highlighter.addHighlight(startOffset, endOffset, new DefaultHighlighter.DefaultHighlightPainter(java.awt.Color.YELLOW));
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
}
