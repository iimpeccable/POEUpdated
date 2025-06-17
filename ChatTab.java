package poeupdated;

import javax.swing.*;
import java.awt.*;

public class ChatTab extends JFrame {
    private JTextArea messageArea;
    private JTextField messageInput;
    private JButton sendButton;

    public ChatTab(String username) {
        setTitle("QuickChat - " + username);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Customizing the background
        GradientPanel backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        // Message area with styling
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Roboto", Font.PLAIN, 16));
        messageArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(40, 40, 50));
        messageInput = new JTextField();
        messageInput.setFont(new Font("Roboto", Font.PLAIN, 16));
        messageInput.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        sendButton = new CustomStyledButton("Send", new Color(0, 128, 128), new Color(0, 180, 180));
        sendButton.addActionListener(e -> sendMessage());

        inputPanel.add(messageInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        backgroundPanel.add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sendMessage() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty()) {
            messageArea.append("You: " + message + "\n");
            messageInput.setText("");
        }
    }
}
