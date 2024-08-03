package pdsa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextArea;
import javax.swing.JComponent;

public class LineNumberComponent extends JComponent {
    private static final long serialVersionUID = 1L;
    private final JTextArea textArea;

    public LineNumberComponent(JTextArea textArea) {
        this.textArea = textArea;
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                repaint();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                repaint();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                repaint();
            }
        });

        textArea.addCaretListener(e -> repaint());
        setPreferredSize(new Dimension(30, textArea.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics fontMetrics = textArea.getFontMetrics(textArea.getFont());
        int fontHeight = fontMetrics.getHeight();
        int currentLine = 0;
        int startOffset = textArea.viewToModel2D(textArea.getVisibleRect().getLocation());

        while (currentLine <= textArea.getLineCount()) {
            try {
                int endOffset = textArea.getLineEndOffset(currentLine);
                if (startOffset < endOffset) {
                    int lineNumberY = textArea.modelToView2D(startOffset).getBounds().y;
                    g2d.drawString(String.valueOf(currentLine + 1), 0, lineNumberY + fontHeight - 3);
                }
                startOffset = endOffset;
                currentLine++;
            } catch (Exception e) {
                break;
            }
        }

        g2d.setColor(Color.GRAY);
        int y = 0;
        while (y < getHeight()) {
            g2d.drawLine(getWidth() - 1, y, getWidth() - 1, y + fontHeight);
            y += fontHeight;
        }
    }
}
