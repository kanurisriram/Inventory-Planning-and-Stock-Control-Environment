import java.util.*;

public class DataStore {

    public static LinkedList<String[]> customers = new LinkedList<>();
    public static LinkedList<String[]> products = new LinkedList<>();

    public static Queue<String[]> sales = new LinkedList<>();
    public static Queue<String[]> purchases = new LinkedList<>();

    public static Stack<String[]> expenses = new Stack<>();

    // invoiceId -> {customer, amount, date, gst, total}
    public static HashMap<String, String[]> invoices = new HashMap<>();

    // Auto invoice numbering
    private static int invoiceCounter = 1001;

    // GST rate (editable)
    public static double GST_RATE = 0.18; // 18%

    public static String nextInvoiceId() {
        return "INV-" + (invoiceCounter++);
    }

    // Demo data
    static {
        customers.add(new String[] { "Ramesh Corp", "ramesh@example.com" });
        customers.add(new String[] { "Sriram", "sriram@gmail.com" });

        products.add(new String[] { "Laptop", "50000" });
        products.add(new String[] { "Mouse", "500" });

        sales.add(new String[] { "Ramesh Corp", "50000" });
        purchases.add(new String[] { "ABC Traders", "30000" });

        expenses.push(new String[] { "Office Rent", "15000" });
    }
}