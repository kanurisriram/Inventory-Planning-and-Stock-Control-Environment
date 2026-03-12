import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class LoginSystem extends JFrame {

    HashMap<String, String> users = new HashMap<>();

    JTextField username = new JTextField(15);
    JPasswordField password = new JPasswordField(15);

    public LoginSystem() {

        users.put("admin", "1234");

        setTitle("Inventory Login");
        setSize(350, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Username"));
        add(username);

        add(new JLabel("Password"));
        add(password);

        JButton login = new JButton("Login");

        add(new JLabel());
        add(login);

        login.addActionListener(e -> checkLogin());
    }

    void checkLogin() {

        String user = username.getText();
        String pass = new String(password.getPassword());

        if (users.containsKey(user) && users.get(user).equals(pass)) {

            new InventoryApp().setVisible(true);
            dispose();

        } else {

            JOptionPane.showMessageDialog(this, "Invalid Login");
        }
    }

    public static void main(String[] args) {

        new LoginSystem().setVisible(true);
    }
}