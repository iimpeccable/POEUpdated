package poeupdated;

public class Message {
    private String messageID;
    private String content;
    private int messageNum;
    private String status;

    public Message(String messageID, String content, int messageNum) {
        this.messageID = messageID;
        this.content = content;
        this.messageNum = messageNum;
        this.status = "Sent";
    }

    /// Generates Message Hash based on ID, Number, First & Last Words
    public String generateMessageHash() {
        String[] words = content.trim().split("\\s+"); 
        String firstWord = words[0].toUpperCase(); 
        String lastWord = words[words.length - 1].toUpperCase(); 

        return messageID.substring(0, 2) + ":" + messageNum +  firstWord + lastWord;
    }

    public String getMessageID() {
        return messageID;
    }
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getStatus() {
        return status;
    }
     @Override
    public String toString() {
        return "Message ID: " + messageID + "\nContent: " + content + "\nMessage Number: " + messageNum + "\nStatus: " + status;
    }

    public String getContent() {
        return content;
    }

    public int getMessageNum() {
        return messageNum;
    }
}
