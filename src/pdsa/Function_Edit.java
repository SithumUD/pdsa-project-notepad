package pdsa;

import java.util.Stack;
import javax.swing.JTextArea;

public class Function_Edit {
    GUI gui;
    private String currentText = "";
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();

    public Function_Edit(GUI gui) {
        this.gui = gui;
    }

    public void setText(String newText) {
        // Only push to undo stack if there's a change
        if (!currentText.equals(newText)) {
            undoStack.push(currentText);
            currentText = newText;
            //redoStack.clear(); // Clear redo stack on new operation
        }
    }

    public String undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentText);
            currentText = undoStack.pop();
        }
        return currentText;
    }

    public String redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentText);
            currentText = redoStack.pop();
        }
        return currentText;
    }

    public String getText() {
        return currentText;
    }
}
