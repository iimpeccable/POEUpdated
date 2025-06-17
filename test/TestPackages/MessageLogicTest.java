package TestPackages;

import poeupdated.MessageLogic;

public class MessageLogicTest {
    public static void main(String[] args){
      runTests();  
    }
    static void runTests(){
        testValidRecipient();
        testInvalidRecipient();
        System.out.println("All recipient validation tests passed!");
    }
    static void testValidRecipient(){
        MessageLogic logic = new MessageLogic();
        assert logic.validateRecipient("+27718693002") : "Valid recipient faied";
    }
    static void testInvalidRecipient(){
        MessageLogic logic = new MessageLogic();
        assert !logic.validateRecipient("08575975889") : "Invalid Recipient";
    }
}
