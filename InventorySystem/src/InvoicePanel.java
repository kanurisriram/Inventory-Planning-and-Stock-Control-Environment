import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

public class InvoicePanel extends JPanel {

    DefaultTableModel model;

    JTextField id = new JTextField(10);
    JTextField customer = new JTextField(10);
    JTextField amount = new JTextField(10);

    JLabel dateLabel = new JLabel();

    public InvoicePanel() {

        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        JButton create = new JButton("Create Invoice");
        JButton pdf = new JButton("Download PDF");
        JButton excel = new JButton("Export Excel");

        dateLabel.setText(currentDate());

        top.add(new JLabel("Invoice ID"));
        top.add(id);

        top.add(new JLabel("Customer"));
        top.add(customer);

        top.add(new JLabel("Amount"));
        top.add(amount);

        top.add(new JLabel("Date"));
        top.add(dateLabel);

        top.add(create);
        top.add(pdf);
        top.add(excel);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[] { "Invoice", "Customer", "Amount", "GST", "Total", "Date" }, 0);

        JTable table = new JTable(model);

        add(new JScrollPane(table));

        loadDemo();

        create.addActionListener(e -> createInvoice());
        pdf.addActionListener(e -> downloadPDF());
        excel.addActionListener(e -> exportExcel());
    }

    String currentDate() {

        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    void loadDemo() {

        for (String key : DataStore.invoices.keySet()) {

            String[] data = DataStore.invoices.get(key);

            model.addRow(new Object[] {
                    key,
                    data[0],
                    data[1],
                    data[3],
                    data[4],
                    data[2]
            });
        }
    }

    void createInvoice() {

        String invoiceId = id.getText();
        String cust = customer.getText();

        double amt = Double.parseDouble(amount.getText());

        double gst = amt * DataStore.GST_RATE;
        double total = amt + gst;

        String date = currentDate();

        DataStore.invoices.put(invoiceId,
                new String[] {
                        cust,
                        String.valueOf(amt),
                        date,
                        String.valueOf(gst),
                        String.valueOf(total)
                });

        model.addRow(new Object[] {
                invoiceId,
                cust,
                amt,
                gst,
                total,
                date
        });

        id.setText("");
        customer.setText("");
        amount.setText("");
        dateLabel.setText(currentDate());
    }

    void downloadPDF() {

        try {

            Document doc = new Document();

            PdfWriter.getInstance(doc, new FileOutputStream("Invoice.pdf"));

            doc.open();

            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.HELVETICA,
                    22,
                    com.itextpdf.text.Font.BOLD);

            Paragraph title = new Paragraph("INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            doc.add(title);

            doc.add(new Paragraph("Company: Inventory System"));
            doc.add(new Paragraph("Address: Hyderabad, India"));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);

            table.addCell("Invoice");
            table.addCell("Customer");
            table.addCell("Amount");
            table.addCell("GST");
            table.addCell("Total");
            table.addCell("Date");

            for (int i = 0; i < model.getRowCount(); i++) {

                for (int j = 0; j < 6; j++) {

                    table.addCell(
                            model.getValueAt(i, j).toString());
                }
            }

            doc.add(table);

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Thank you for your business!"));

            doc.close();

            JOptionPane.showMessageDialog(this, "PDF Invoice Created");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    void exportExcel() {

        try {

            FileWriter writer = new FileWriter("Invoices_Report.csv");

            writer.append("Invoice ID,Customer,Amount,GST,Total,Date\n");

            for (String key : DataStore.invoices.keySet()) {

                String[] data = DataStore.invoices.get(key);

                writer.append(
                        key + "," +
                                data[0] + "," +
                                data[1] + "," +
                                data[3] + "," +
                                data[4] + "," +
                                data[2] + "\n");
            }

            writer.close();

            JOptionPane.showMessageDialog(this,
                    "Excel file exported: Invoices_Report.csv");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}