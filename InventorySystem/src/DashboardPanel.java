import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {

        setLayout(new GridLayout(2, 2, 20, 20));

        add(card("Customers : " + DataStore.customers.size()));
        add(card("Products : " + DataStore.products.size()));
        add(card("Sales : " + DataStore.sales.size()));
        add(card("Invoices : " + DataStore.invoices.size()));
    }

    JPanel card(String text) {

        JPanel panel = new JPanel();

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(label);

        return panel;
    }
}