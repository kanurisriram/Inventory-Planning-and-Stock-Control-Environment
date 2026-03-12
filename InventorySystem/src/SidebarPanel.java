import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends GradientPanel {

    JButton dashboard = new JButton("Dashboard");
    JButton customers = new JButton("Customers");
    JButton products = new JButton("Products");
    JButton sales = new JButton("Sales");
    JButton expenses = new JButton("Expenses");
    JButton purchases = new JButton("Purchases");
    JButton invoices = new JButton("Invoices");
    JButton profile = new JButton("Profile");

    public SidebarPanel() {

        setLayout(new GridLayout(10, 1, 10, 10));
        setPreferredSize(new Dimension(200, 700));

        JLabel title = new JLabel("WELCOME ADMIN");
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        add(title);
        add(menuButton(dashboard));
        add(menuButton(customers));
        add(menuButton(products));
        add(menuButton(sales));
        add(menuButton(expenses));
        add(menuButton(purchases));
        add(menuButton(invoices));
        add(menuButton(profile));
    }

    JButton menuButton(JButton btn) {

        btn.setBackground(new Color(120, 70, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        return btn;
    }
}