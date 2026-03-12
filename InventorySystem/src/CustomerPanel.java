import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;

public class CustomerPanel extends JPanel {

    DefaultTableModel model;
    JTextField name = new JTextField(12);
    JTextField email = new JTextField(12);

    public CustomerPanel() {

        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        JButton add = new JButton("Add Customer");
        JButton export = new JButton("Export Excel");

        top.add(new JLabel("Name"));
        top.add(name);

        top.add(new JLabel("Email"));
        top.add(email);

        top.add(add);
        top.add(export);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[] { "Name", "Email" }, 0);

        JTable table = new JTable(model);

        add(new JScrollPane(table));

        loadDemo();

        add.addActionListener(e -> addCustomer());
        export.addActionListener(e -> exportExcel());
    }

    void loadDemo() {

        for (String[] c : DataStore.customers)
            model.addRow(c);
    }

    void addCustomer() {

        String[] data = { name.getText(), email.getText() };

        DataStore.customers.add(data);

        model.addRow(data);

        name.setText("");
        email.setText("");
    }

    void exportExcel() {

        try {

            FileWriter writer = new FileWriter("customers.csv");

            writer.append("Name,Email\n");

            for (String[] c : DataStore.customers) {

                writer.append(c[0] + "," + c[1] + "\n");
            }

            writer.close();

            JOptionPane.showMessageDialog(this, "Excel Exported");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}