import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SalesPanel extends JPanel {

    DefaultTableModel model;

    JTextField customer = new JTextField(10);
    JTextField amount = new JTextField(10);

    public SalesPanel() {

        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        JButton add = new JButton("Add Sale");

        top.add(new JLabel("Customer"));
        top.add(customer);
        top.add(new JLabel("Amount"));
        top.add(amount);
        top.add(add);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[] { "Customer", "Amount" }, 0);

        JTable table = new JTable(model);

        add(new JScrollPane(table));

        add.addActionListener(e -> addSale());
    }

    void addSale() {

        String[] data = { customer.getText(), amount.getText() };

        DataStore.sales.add(data);

        model.addRow(data);

        customer.setText("");
        amount.setText("");
    }
}