package poeupdated;

import java.awt.Dimension;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class JSONMessageStorage {
    private static final String FILE_PATH = "messages.json";

    public void saveMessageToJson(String messageID, String username, String message, String status) {
        try {
            File file = new File(FILE_PATH);
            StringBuilder jsonBuilder = new StringBuilder();

            jsonBuilder.append("[");

            // Load existing content
            if (file.exists() && file.length() > 0) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder existingContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    existingContent.append(line);
                }
                reader.close();

                String content = existingContent.toString().trim();
                if (content.endsWith("]")) {
                    content = content.substring(1, content.length() - 1).trim();
                    if (!content.isBlank()) {
                        jsonBuilder.append(content).append(",");
                    }
                }
            }

            // Append new message
            jsonBuilder.append("{\"id\":\"").append(messageID)
                       .append("\",\"recipient\":\"").append(username)
                       .append("\",\"message\":\"").append(message)
                       .append("\",\"status\":\"").append(status)
                       .append("\"}");

            jsonBuilder.append("]");

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(jsonBuilder.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Message> loadMessagesAsObjects() {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return messages;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            String[] entries = jsonContent.toString().replace("[", "").replace("]", "").split("(?<=\\}),");
            int counter = 1;
            for (String entry : entries) {
                String id = extractJsonValue(entry, "id");
                String username = extractJsonValue(entry, "username");
                String msg = extractJsonValue(entry, "message");
                String status = extractJsonValue(entry, "status");
                String recipient = extractJsonValue(entry, "recipient");


                if (id == null || id.isBlank()) {
                    id = "ARCHIVE" + counter;
                }

                Message m = new Message(id, msg, counter, recipient);
                m.setStatus(status);
                messages.add(m);
                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int start = json.indexOf(searchKey);
        if (start == -1) return "";
        start += searchKey.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    public void displayStoredMessagesFromJson() {
        ArrayList<Message> stored = loadMessagesAsObjects();
        if (stored.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found in archive.");
            return;
        }

        StringBuilder sb = new StringBuilder("Stored Messages:\n");
        for (Message msg : stored) {
            sb.append("ID: ").append(msg.getMessageID())
              .append("\nFrom: ").append(msg.getContent())
              .append("\nStatus: ").append(msg.getStatus())
              .append("\n----------------------\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Archived Messages", JOptionPane.INFORMATION_MESSAGE);
    }
    public boolean deleteMessageByHash(String hashToDelete) {
    ArrayList<Message> messages = loadMessagesAsObjects();
    boolean found = false;

    for (int i = 0; i < messages.size(); i++) {
        if (messages.get(i).generateMessageHash().equalsIgnoreCase(hashToDelete)) {
            messages.remove(i);
            found = true;
            break;
        }
    }

    if (found) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("[");
            for (int i = 0; i < messages.size(); i++) {
                Message msg = messages.get(i);
                String jsonEntry = String.format(
                    "{\"id\":\"%s\",\"username\":\"%s\",\"message\":\"%s\",\"status\":\"%s\",\"recipient\":\"%s\"}",
                    msg.getMessageID(), msg.getRecipient(), msg.getContent(), msg.getStatus(), msg.getRecipient()
                );
                writer.write(jsonEntry);
                if (i < messages.size() - 1) writer.write(",");
            }
            writer.write("]");
            JOptionPane.showMessageDialog(null, "Message with hash " + hashToDelete + " deleted from archive.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(null, "No message with that hash found in JSON.");
    }

    return false;
}

}
