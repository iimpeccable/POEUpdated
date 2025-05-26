package poeupdated;

import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JFrame {

    private String loggedInUsername;
    private MessageLogic messageLogic; // Message handling class

    public MenuScreen(String username) {
        this.loggedInUsername = username;
        this.messageLogic = new MessageLogic(); // Initialize MessageLogic

        setTitle("QuickChat - Menu");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /// Header
        JLabel menuLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        add(menuLabel, BorderLayout.NORTH);

        /// Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton sendMessageButton = new JButton("Send Message");
        JButton showRecentMessagesButton = new JButton("Show Recently Sent Messages");
        JButton quitButton = new JButton("Quit");

        sendMessageButton.addActionListener(e -> messageLogic.startMessaging());
        sendMessageButton.addActionListener(e -> new ChatTab(loggedInUsername));
        showRecentMessagesButton.addActionListener(e -> messageLogic.showRecentlySentMessages());
        quitButton.addActionListener(e -> exitApplication());

        buttonPanel.add(sendMessageButton);
        buttonPanel.add(showRecentMessagesButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void exitApplication() {
        JOptionPane.showMessageDialog(this, "Goodbye!");
        System.exit(0);
    }
}
