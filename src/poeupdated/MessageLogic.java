package poeupdated;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.ArrayList;

public class MessageLogic {
    
    private int maxMessages;
    private int currentMessageCount;
    private ArrayList<Message> sentMessages;
    private int totalMessagesSent = 0;


    public MessageLogic() {
        this.currentMessageCount = 0;
        this.sentMessages = new ArrayList<>();
    }

    /// Generate a random 10-digit message ID
    private String generateMessageID() {
        SecureRandom random = new SecureRandom();
        long id = 1000000000L + (long) (random.nextDouble() * 9000000000L); 
        return String.valueOf(id); 
    }

    /// Start message sending process
    public void startMessaging() {
        try {
            String input = JOptionPane.showInputDialog(null, "Enter number of messages you want to send:");
            if (input == null || input.trim().isEmpty()) return;

            maxMessages = Integer.parseInt(input);
            currentMessageCount = 0;

            while (currentMessageCount < maxMessages) {
                
                sendMessage();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number! Please enter a valid message count.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean validateRecipient(String number) {
    if (number == null || !number.startsWith("+")) {
        return false;  
    }

    String numericPart = number.substring(1); // Remove "+"
    
    if (!numericPart.matches("\\d+") || numericPart.length() >= 12) {
        return false; 
    }

    return true; 
}


    /// Handles individual message entry with Message ID assignment
    private void sendMessage() {
        JSONMessageStorage jsonStorage = new JSONMessageStorage();
    String recipient = JOptionPane.showInputDialog(null, "Enter recipient's phone number (with country code):");
    if (!validateRecipient(recipient)) {
        JOptionPane.showMessageDialog(null, "Invalid number format! Please enter a valid international number.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String message = JOptionPane.showInputDialog(null, "Enter message " + (currentMessageCount + 1) + ":");
    if (message == null || message.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Message cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (message.length() > 250) {
        JOptionPane.showMessageDialog(null, "Send message less than 250 characters.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Ask user what they want to do with the message
    String[] options = {"Send", "Discard", "Save for Later"};
    int choice = JOptionPane.showOptionDialog(
        null, "Choose an action for this message:", "Message Options",
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
        null, options, options[0]
    );

    if (choice == 1) {  /// Discard Message option
        JOptionPane.showMessageDialog(null, "Message Discarded.");
       SwingUtilities.invokeLater(() -> new MenuScreen("username"));
        return;
    } else if (choice == 2) {  /// Save for Later option
        saveMessageForLater(message);
        JOptionPane.showMessageDialog(null, "Message saved for later!");
        SwingUtilities.invokeLater(() -> new MenuScreen("username"));
        return;
    }

    /// If user chooses to send
    String messageID = generateMessageID();
    Message newMessage = new Message(messageID, message, totalMessagesSent + 1);
    String messageHash = newMessage.generateMessageHash();
    newMessage.setStatus("Sent");
    sentMessages.add(newMessage);
    jsonStorage.saveMessageToJson(recipient, message);
   
    currentMessageCount++;

    JOptionPane.showMessageDialog(null, "Message Sent! ID: " + messageID +  "\n" + "Message Hash: " + messageHash + "\n" + "Recipient: " + recipient + "\n" + "Message: " + message + "\n" + "Message Status: " + newMessage.getStatus() +"\n" + "Total Messages Sent: " + currentMessageCount);
    totalMessagesSent++;
    SwingUtilities.invokeLater(() -> new MenuScreen("username"));
    
}

/// Method to store messages for later sending
private ArrayList<String> savedMessages = new ArrayList<>();

/// Updates read receipts
public void markMessageAsRead(int messageIndex) {
    if (messageIndex >= 0 && messageIndex < sentMessages.size()) {
        sentMessages.get(messageIndex).setStatus("Read");
        JOptionPane.showMessageDialog(null, "Message ID: " + sentMessages.get(messageIndex).getMessageID() + " marked as Read!");
    } else {
        JOptionPane.showMessageDialog(null, "Invalid message selection.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void saveMessageForLater(String message) {
    savedMessages.add(message);
}

    /// Retrieves and displays sent messages with IDs
    public void showRecentlySentMessages() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Coming Soon!" + " Please Wait", "Recently Sent Messages", JOptionPane.INFORMATION_MESSAGE);
        }
      
    }
}
