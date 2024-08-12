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
        
        if (!currentText.equals(newText)) {
            undoStack.push(currentText);
            currentText = newText;
            
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
