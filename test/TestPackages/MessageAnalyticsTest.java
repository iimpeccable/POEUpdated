package testpackages;

import poeupdated.Message;
import poeupdated.MessageAnalytics;

import java.util.ArrayList;

public class MessageAnalyticsTest {
    public static void main(String[] args) {
        runTests();
    }

    static void runTests() {
        testFindLongestMessage();
        testSearchByMessageIDFound();
        testSearchByMessageIDNotFound();
        System.out.println("MessageAnalytics tests passed!");
    }

    static void testFindLongestMessage() {
        MessageAnalytics analytics = new MessageAnalytics();
        ArrayList<Message> testMessages = new ArrayList<>();

        testMessages.add(new Message("1", "Short one", 1, "+27123456789"));
        testMessages.add(new Message("2", "This is a message that is much longer than the others", 2, "+27123456789"));
        testMessages.add(new Message("3", "Tiny", 3, "+27123456789"));

        analytics.displayAllSendersAndRecipients();
        
    }

    static void testSearchByMessageIDFound() {
        MessageAnalytics analytics = new MessageAnalytics();
        ArrayList<Message> testMessages = new ArrayList<>();

        testMessages.add(new Message("ABC123", "Hello", 1, "+27123456789"));
        testMessages.add(new Message("XYZ999", "Test message", 2, "+27876543210"));

        analytics.displayAllSendersAndRecipients();
        Message result = analytics.searchByMessageID("XYZ999");

        assert result != null : " Expected message not found.";
        assert result.getMessageID().equals("XYZ999") : " Wrong message returned.";
    }

    static void testSearchByMessageIDNotFound() {
        MessageAnalytics analytics = new MessageAnalytics();
        analytics.displayStoredMessagesFromJson();

        Message result = analytics.searchByMessageID("DOES_NOT_EXIST");

        assert result == null : " Nonexistent ID returned a result.";
    }
    Message msg1 = new Message("TEST1", "Did you get the cake?", 1, "+27834557896");

    Message msg2 = new Message("TEST2", "Where are you? You are late! I have asked you to be on time.", 2, "+27838884567");

    Message msg3 = new Message("TEST3", "Yohoooo, I am at your gate.", 3, "+27834484567");

}
