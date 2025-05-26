package poeupdated;

import java.io.*;

public class JSONMessageStorage {
    private static final String FILE_PATH = "messages.json";

   public void saveMessageToJson(String username, String message) {
    try {
        File file = new File("messages.json");
        StringBuilder jsonBuilder = new StringBuilder();

        // Load existing messages if file exists
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();
        }

        // Append new message
        jsonBuilder.append("{")
                   .append("\"username\":\"").append(username).append("\",")
                   .append("\"message\":\"").append(message).append("\"")
                   .append("}\n");

        // Write back to file
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(jsonBuilder.toString());
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    
}

}
