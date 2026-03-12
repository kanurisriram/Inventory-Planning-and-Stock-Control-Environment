import javax.swing.*;
import java.awt.*;

public class InventoryApp extends JFrame {

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    public InventoryApp() {

        setTitle("Inventory Planning System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        SidebarPanel sidebar = new SidebarPanel();

        mainPanel.add(new DashboardPanel(), "dashboard");
        mainPanel.add(new CustomerPanel(), "customers");
        mainPanel.add(new ProductPanel(), "products");
        mainPanel.add(new SalesPanel(), "sales");
        mainPanel.add(new ExpensePanel(), "expenses");
        mainPanel.add(new PurchasePanel(), "purchases");
        mainPanel.add(new InvoicePanel(), "invoices");
        mainPanel.add(new ProfilePanel(), "profile");

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        sidebar.dashboard.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        sidebar.customers.addActionListener(e -> cardLayout.show(mainPanel, "customers"));
        sidebar.products.addActionListener(e -> cardLayout.show(mainPanel, "products"));
        sidebar.sales.addActionListener(e -> cardLayout.show(mainPanel, "sales"));
        sidebar.expenses.addActionListener(e -> cardLayout.show(mainPanel, "expenses"));
        sidebar.purchases.addActionListener(e -> cardLayout.show(mainPanel, "purchases"));
        sidebar.invoices.addActionListener(e -> cardLayout.show(mainPanel, "invoices"));
        sidebar.profile.addActionListener(e -> cardLayout.show(mainPanel, "profile"));
    }
}