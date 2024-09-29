package exemple.ui.ex4_components;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Radu Miron
 * @version 1
 */
public class PasswordFieldExample extends JFrame {
    public static final Map<String, String> accounts = new HashMap<>();

    public PasswordFieldExample() {
        //setup the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setBounds(200, 200, 300, 200);

        //create and add components
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 100, 20);
        this.add(userLabel);

        JTextField userInputField = new JTextField();
        userInputField.setBounds(160, 20, 100, 20);
        this.add(userInputField);

        JLabel pwdLabel = new JLabel("Password:");
        pwdLabel.setBounds(20, 60, 100, 20);
        this.add(pwdLabel);

        JPasswordField pwdInputField = new JPasswordField();
        pwdInputField.setBounds(160, 60, 100, 20);
        this.add(pwdInputField);

        JButton button = new JButton("Login");
        button.setBounds(20, 100, 240, 20);
        this.add(button);

        button.addActionListener(e -> {
            String username = userInputField.getText();
            String pwd = pwdInputField.getText();

            if (Objects.equals(accounts.get(username), pwd)) {
                new LabelTextFieldTextAreaExample();
                PasswordFieldExample.this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(PasswordFieldExample.this,
                        "Username and password don't match any existing account!",
                        "Bad credentials",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        //initialize accounts
        initAccounts();

        //display
        this.setVisible(true);
    }

    private void initAccounts() {
        accounts.put("user1", "password1");
        accounts.put("user2", "password2");
        accounts.put("user3", "password3");
    }

    public static void main(String[] args) {
        new PasswordFieldExample();
    }
}
