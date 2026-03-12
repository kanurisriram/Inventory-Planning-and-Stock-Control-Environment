import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductPanel extends JPanel {

    DefaultTableModel model;

    JTextField product = new JTextField(10);
    JTextField price = new JTextField(10);
    JTextField search = new JTextField(10);

    public ProductPanel() {

        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        JButton add = new JButton("Add Product");
        JButton find = new JButton("Search");

        top.add(new JLabel("Product"));
        top.add(product);

        top.add(new JLabel("Price"));
        top.add(price);

        top.add(add);

        top.add(new JLabel("Search"));
        top.add(search);
        top.add(find);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[] { "Product", "Price" }, 0);

        JTable table = new JTable(model);

        add(new JScrollPane(table));

        loadDemo();

        add.addActionListener(e -> addProduct());
        find.addActionListener(e -> binarySearch());
    }

    void loadDemo() {

        for (String[] p : DataStore.products)
            model.addRow(p);
    }

    void addProduct() {

        String[] data = { product.getText(), price.getText() };

        DataStore.products.add(data);

        model.addRow(data);

        product.setText("");
        price.setText("");
    }

    void binarySearch() {

        String key = search.getText();

        int left = 0;
        int right = DataStore.products.size() - 1;

        while (left <= right) {

            int mid = (left + right) / 2;

            String[] p = DataStore.products.get(mid);

            int cmp = p[0].compareToIgnoreCase(key);

            if (cmp == 0) {

                JOptionPane.showMessageDialog(this, "Found " + p[0]);
                return;
            }

            if (cmp < 0)
                left = mid + 1;
            else
                right = mid - 1;
        }

        JOptionPane.showMessageDialog(this, "Product Not Found");
    }
}