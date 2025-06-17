package poeupdated;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MessageAnalytics {
    private ArrayList<Message> sentMessages;

    public MessageAnalytics() {
        JSONMessageStorage storage = new JSONMessageStorage();
        this.sentMessages = storage.loadMessagesAsObjects(); // Always load from file
    }

    public void displayAllSendersAndRecipients() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found.");
            return;
        }

        StringBuilder sb = new StringBuilder("Messages Overview:\n");
        for (Message msg : sentMessages) {
            sb.append("ID: ").append(msg.getMessageID())
              .append(" | To: ").append(msg.getRecipient())
              .append(" | Msg #").append(msg.getMessageNum())
              .append(" | Status: ").append(msg.getStatus()).append("\n");
        }
        showTextArea(sb.toString(), "Senders & Recipients");
    }

    public void findLongestMessage() {
        Message longest = null;
        for (Message msg : sentMessages) {
            if (longest == null || msg.getContent().length() > longest.getContent().length()) {
                longest = msg;
            }
        }

        if (longest != null) {
            showTextArea("Longest Message:\n\n" + formatMessage(longest), "Longest Message");
        } else {
            JOptionPane.showMessageDialog(null, "No messages to analyze.");
        }
    }

    public Message searchByMessageID(String id) {
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equalsIgnoreCase(id)) {
                return msg;
            }
        }
        return null;
    }

    public void showFullMessageReport() {
        StringBuilder report = new StringBuilder("Full Message Report:\n\n");
        for (Message msg : sentMessages) {
            report.append(formatMessage(msg)).append("\n----------------------\n");
        }
        showTextArea(report.toString(), "All Messages");
    }

    public void displayStoredMessagesFromJson() {
        JSONMessageStorage storage = new JSONMessageStorage();
        ArrayList<Message> stored = storage.loadMessagesAsObjects();

        if (stored.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No archived messages found.");
            return;
        }

        StringBuilder sb = new StringBuilder("Archived Messages:\n\n");
        for (Message msg : stored) {
            sb.append(formatMessage(msg)).append("\n----------------------\n");
        }

        showTextArea(sb.toString(), "Archived Messages");
    }

    private String formatMessage(Message msg) {
        return "ID: " + msg.getMessageID() +
               "\nTo: " + msg.getRecipient() +
               "\nMessage: " + msg.getContent() +
                "\nHash: " + msg.generateMessageHash() +
               "\nStatus: " + msg.getStatus();
    }
    
    public void searchMessagesByRecipient(String recipient) {
        ArrayList<Message> matches = new ArrayList<>();
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equalsIgnoreCase(recipient)) {
                matches.add(msg);
            }
        }

        if (matches.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found for recipient: " + recipient);
            return;
        }

        StringBuilder sb = new StringBuilder("Messages sent to: " + recipient + "\n\n");
            for (Message m : matches) {
            sb.append("ID: ").append(m.getMessageID())
                             .append("\nHash: ").append(m.generateMessageHash())
                             .append("\nMessage: ").append(m.getContent())
                             .append("\nStatus: ").append(m.getStatus())
                             .append("\n--------------------\n");
            }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scroll, "Search Results", JOptionPane.INFORMATION_MESSAGE);
}

    private void showTextArea(String content, String title) {
        JTextArea area = new JTextArea(content);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scroll, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
