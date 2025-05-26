package poeupdated;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class User extends JFrame {
    private Process userProcess;

    private RoundedPanel loginSection;
    private RoundedPanel registerSection;
    private Timer fadeTimer;

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        if (DEV_MODE) {
            new MenuScreen("DevUser");
        } else {
            new User(); 
        }
    });
}

    private static final boolean DEV_MODE = false;

    public User() {
        super("IIE Secrect App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        userProcess = new Process();

        setLayout(new BorderLayout());

        /// Header panel 
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(212, 237, 218)); 
        JLabel titleLabel = new JLabel("Welcome to The IIE Secret App");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 26));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.setBackground(new Color(248, 249, 250));
        
        loginSection = createLoginSection();
        registerSection = createRegisterSection();
        loginSection.setLocation(-400, loginSection.getY()); 
        registerSection.setLocation(400, registerSection.getY());

        mainPanel.add(loginSection);
        mainPanel.add(registerSection);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);

        animateTitle(titleLabel);
        slidePanels(loginSection, registerSection);
    }
    ///Title motions
    private void animateTitle(JLabel label) {
        Timer titleFadeTimer = new Timer(50, new ActionListener() {
            int alpha = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (alpha < 255) {
                    alpha += 5;
                    label.setForeground(new Color(0, 0, 0, alpha));
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        titleFadeTimer.start();
    }
    ///Panel motions
    private void slidePanels(JPanel login, JPanel register) {
        Timer slideTimer = new Timer(50, new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step < 40) { 
                    login.setLocation(login.getX() + 10, login.getY());
                    register.setLocation(register.getX() - 10, register.getY());
                    step++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        slideTimer.start();
    }
    ///Log in panel creation
    private RoundedPanel createLoginSection() {
        RoundedPanel panel = new RoundedPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(new Color(212, 237, 218)); 
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel loginLabel = new JLabel("Log In");
        loginLabel.setFont(new Font("Roboto", Font.BOLD, 22));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        JTextField loginUsernameField = new JTextField(15);
        JPasswordField loginPasswordField = new JPasswordField(15);

        CustomStyledButton loginButton = new CustomStyledButton("Log In", new Color(0, 128, 128), new Color(0, 180, 180));
        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText().trim();
            String password = new String(loginPasswordField.getPassword()).trim();
            
            Process.Result result = userProcess.loginUser(username, password);
            JOptionPane.showMessageDialog(this, result.getMessage(),
                    result.isSuccess() ? "Success" : "Error",
                    result.isSuccess() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            
            if (result.isSuccess()) {
                SwingUtilities.invokeLater(() -> new MenuScreen(username));
                
            }
        });
        ///Positioning of labels
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        panel.add(loginLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(loginUsernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(loginPasswordField, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(loginButton, gbc);

        return panel;
    }
    ///Registration panel creation
    private RoundedPanel createRegisterSection() {
        RoundedPanel panel = new RoundedPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(248, 249, 250)); 
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel createAccountLabel = new JLabel("Register");
        createAccountLabel.setFont(new Font("Roboto", Font.BOLD, 24));

        JLabel nameLabel = new JLabel("Username:");
        JLabel areaCodeLabel = new JLabel("Area Code:");
        JLabel phoneLabel = new JLabel("Phone Number:");
        JLabel passwordLabel = new JLabel("Password:");

        JTextField registerNameField = new JTextField(15);
        JTextField registerPhoneAreaCodeField = new JTextField(5);
        JTextField registerPhoneNumberField = new JTextField(10);
        JPasswordField registerPasswordField = new JPasswordField(15);

        CustomStyledButton registerButton = new CustomStyledButton("Register", new Color(0, 128, 128), new Color(0, 180, 180));
        registerButton.addActionListener(e -> {
            String username = registerNameField.getText().trim();
            String phone = registerPhoneAreaCodeField.getText() + registerPhoneNumberField.getText().trim();
            String password = new String(registerPasswordField.getPassword()).trim();

            Process.Result result = userProcess.registerUser(username, phone, password);
            JOptionPane.showMessageDialog(this, result.getMessage(),
                    result.isSuccess() ? "Success" : "Error",
                    result.isSuccess() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        });
        ///Positioning for labels
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createAccountLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(registerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(areaCodeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(registerPhoneAreaCodeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(registerPhoneNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(registerPasswordField, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(registerButton, gbc);

        return panel;
    }
}
