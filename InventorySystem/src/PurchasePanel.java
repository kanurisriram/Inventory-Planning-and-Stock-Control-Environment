import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PurchasePanel extends JPanel {

    DefaultTableModel model;

    JTextField supplier = new JTextField(10);
    JTextField amount = new JTextField(10);

    public PurchasePanel() {

        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        JButton add = new JButton("Add Purchase");

        top.add(new JLabel("Supplier"));
        top.add(supplier);
        top.add(new JLabel("Amount"));
        top.add(amount);
        top.add(add);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[] { "Supplier", "Amount" }, 0);

        JTable table = new JTable(model);

        add(new JScrollPane(table));

        add.addActionListener(e -> addPurchase());
    }

    void addPurchase() {

        String[] data = { supplier.getText(), amount.getText() };

        DataStore.purchases.add(data);

        model.addRow(data);

        supplier.setText("");
        amount.setText("");
    }
}