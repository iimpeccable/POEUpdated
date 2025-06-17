package poeupdated;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        JButton analyticsButton = new JButton("Message Analytics");
        analyticsButton.addActionListener(e -> openAnalytics());
        buttonPanel.add(analyticsButton);

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
private void openAnalytics() {
    MessageAnalytics analytics = new MessageAnalytics();

    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns
    String[] buttonLabels = {
        "View Senders & Recipients",
        "Find Longest Message",
        "Search by Message ID",
        "Full Message Report",
        "Archived Messages",
        "Delete Message by Hash",
        "Search by Recipient Number",
        "Cancel"
    };

    ArrayList<JButton> buttons = new ArrayList<>();
    for (String label : buttonLabels) {
        JButton btn = new JButton(label);
        buttons.add(btn);
        panel.add(btn);
    }

   
    // Add button logic
    buttons.get(0).addActionListener(e -> analytics.displayAllSendersAndRecipients());
    buttons.get(1).addActionListener(e -> analytics.findLongestMessage());
    buttons.get(2).addActionListener(e -> {
        String id = JOptionPane.showInputDialog(this, "Enter Message ID:");
        Message result = analytics.searchByMessageID(id);
        if (result != null) {
            JOptionPane.showMessageDialog(this, result.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Message not found.");
        }
    });
    buttons.get(3).addActionListener(e -> analytics.showFullMessageReport());
    buttons.get(4).addActionListener(e -> analytics.displayStoredMessagesFromJson());
    buttons.get(5).addActionListener(e -> {
        String hash = JOptionPane.showInputDialog(this, "Enter Message Hash to delete:");
        if (hash != null && !hash.trim().isEmpty()) {
            messageLogic.deleteMessageByHash(hash.trim());
            new JSONMessageStorage().deleteMessageByHash(hash.trim());
        }
    });
    buttons.get(6).addActionListener(e -> {
        String recipient = JOptionPane.showInputDialog(this, "Enter recipient's number:");
        if (recipient != null && !recipient.trim().isEmpty()) {
            analytics.searchMessagesByRecipient(recipient.trim());
        }
    });
    buttons.get(7).addActionListener(e -> {}); // Cancel button – closes the panel
     // Show the custom panel
    JOptionPane.showMessageDialog(this, panel, "Message Analytics", JOptionPane.PLAIN_MESSAGE);

}


    private void exitApplication() {
        JOptionPane.showMessageDialog(this, "Goodbye!");
        System.exit(0);
    }
}
