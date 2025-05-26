package poeupdated;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomStyledButton extends JButton {
    private Color baseColor;
    private Color hoverColor;

    public CustomStyledButton(String text, Color baseColor, Color hoverColor) {
        super(text);
        this.baseColor = baseColor;
        this.hoverColor = hoverColor;

        setFont(new Font("Roboto", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setBackground(baseColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(baseColor);
            }
        });
    }
}
