import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        GradientPaint gp = new GradientPaint(
                0, 0, new Color(60, 70, 150),
                0, getHeight(), new Color(150, 90, 200));

        g2.setPaint(gp);

        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}