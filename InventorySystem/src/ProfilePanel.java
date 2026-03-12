import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {

    public ProfilePanel() {

        setLayout(new GridLayout(6, 2, 20, 20));

        add(new JLabel("Business Name"));
        add(new JTextField());

        add(new JLabel("Email"));
        add(new JTextField());

        add(new JLabel("Phone"));
        add(new JTextField());

        add(new JLabel("Address"));
        add(new JTextField());

        add(new JLabel("GST Number"));
        add(new JTextField());

        JButton save = new JButton("Save");

        add(new JLabel());
        add(save);
    }
}