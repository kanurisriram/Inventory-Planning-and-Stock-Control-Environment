import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ExpensePanel extends JPanel {

    DefaultTableModel model;

    JTextField desc = new JTextField(10);
    JTextField amount = new JTextField(10);

    public ExpensePanel() {

        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        JButton add = new JButton("Add Expense");
        JButton undo = new JButton("Undo");

        top.add(new JLabel("Description"));
        top.add(desc);
        top.add(new JLabel("Amount"));
        top.add(amount);
        top.add(add);
        top.add(undo);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[] { "Description", "Amount" }, 0);

        JTable table = new JTable(model);

        add(new JScrollPane(table));

        add.addActionListener(e -> addExpense());
        undo.addActionListener(e -> undoExpense());
    }

    void addExpense() {

        String[] data = { desc.getText(), amount.getText() };

        DataStore.expenses.push(data);

        model.addRow(data);
    }

    void undoExpense() {

        if (!DataStore.expenses.isEmpty()) {

            DataStore.expenses.pop();
            model.removeRow(model.getRowCount() - 1);
        }
    }
}