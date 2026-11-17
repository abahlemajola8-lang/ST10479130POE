package com.mycompany.main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTest {

    // Adds a test message straight into the ArrayList
    private void addTestMessage(int number, String recipient, String text) {
        Main.Message m = new Main.Message();
        m.messageNumber = number;
        m.recipient = recipient;
        m.text = text;
        m.messageId = "1234567890";
        m.messageHash = Main.createMessageHash(m.messageId, number, text);
        Main.sentMessages.add(m);
    }

    // Start every test with an empty list
    @BeforeEach
    public void setUp() {
        Main.sentMessages.clear();
    }

    @Test
    public void testUsernameCorrect() {
        assertTrue(Main.checkUserName("ab_cd"));
    }

    @Test
    public void testUsernameIncorrect() {
        assertFalse(Main.checkUserName("abcdef"));
    }

    @Test
    public void testPasswordCorrect() {
        assertTrue(Main.checkPasswordComplexity("Passw0rd!"));
    }

    @Test
    public void testPasswordIncorrect() {
        assertFalse(Main.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneCorrect() {
        assertTrue(Main.checkCellPhoneNumber("+27831234567"));
    }

    @Test
    public void testCellPhoneIncorrect() {
        assertFalse(Main.checkCellPhoneNumber("0831234567"));
    }

    @Test
    public void testMessageHash() {
        assertEquals("12:1:HITONIGHT", Main.createMessageHash("1234567890", 1, "Hi tonight"));
    }

    @Test
    public void testArraysPopulatedCorrectly() {
        addTestMessage(1, "+27831234567", "Hello there");
        addTestMessage(2, "+27839999999", "Another message");
        Main.populateArrays();

        assertEquals(2, Main.allTexts.size());
        assertEquals("+27839999999", Main.allRecipients.get(1));
        assertEquals("Another message", Main.allTexts.get(1));
    }

    @Test
    public void testLongestMessage() {
        addTestMessage(1, "+27831234567", "Hi");
        addTestMessage(2, "+27839999999", "This is the longest message");
        addTestMessage(3, "+27831234567", "Short one");

        int index = Main.findLongestMessageIndex();
        assertEquals("This is the longest message", Main.allTexts.get(index));
    }

    @Test
    public void testLongestMessageWhenEmpty() {
        assertEquals(-1, Main.findLongestMessageIndex());
    }

    @Test
    public void testSearchByRecipient() {
        addTestMessage(1, "+27831234567", "First");
        addTestMessage(2, "+27839999999", "Second");
        addTestMessage(3, "+27831234567", "Third");

        ArrayList<String> found = Main.searchByRecipient("+27831234567");
        assertEquals(2, found.size());
        assertTrue(found.contains("First"));
        assertTrue(found.contains("Third"));
    }

    @Test
    public void testSearchByRecipientNotFound() {
        addTestMessage(1, "+27831234567", "First");
        assertTrue(Main.searchByRecipient("+27800000000").isEmpty());
    }

    @Test
    public void testDeleteMessageByHash() {
        addTestMessage(1, "+27831234567", "Hello there");
        String hash = Main.sentMessages.get(0).messageHash;

        assertTrue(Main.deleteMessageByHash(hash));
        assertEquals(0, Main.sentMessages.size());
    }

    @Test
    public void testDeleteMessageByHashNotFound() {
        addTestMessage(1, "+27831234567", "Hello there");
        assertFalse(Main.deleteMessageByHash("XX:9:NOTFOUND"));
        assertEquals(1, Main.sentMessages.size());
    }

    @Test
    public void testReadJsonIntoArray() {
        addTestMessage(1, "+27831234567", "Hello there");
        Main.saveMessagesToJson();

        ArrayList<Main.Message> fromFile = Main.readJsonIntoArray();
        assertEquals(1, fromFile.size());
        assertEquals("Hello there", fromFile.get(0).text);
        assertEquals("+27831234567", fromFile.get(0).recipient);
    }
}
