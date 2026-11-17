package com.mycompany.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Creates Scanner for user input
    static Scanner input = new Scanner(System.in);

    // Stores the user's entered details
    static String username;
    static String password;
    static String cellPhone;

    // Stores the successfully registered details
    static String registeredUsername;
    static String registeredPassword;
    static String registeredCellPhone;


    // ===================== PART 2 ADDITIONS =====================

    // Simple class to hold the details of one message
    static class Message {
        String messageId;
        int messageNumber;
        String recipient;
        String text;
        String messageHash;
    }

    // ArrayList that stores all the messages that were sent
    static ArrayList<Message> sentMessages = new ArrayList<>();

    // Counts the messages across the whole application
    static int totalMessages = 0;


    // ===================== PART 3 ADDITIONS =====================

    // Parallel ArrayLists: the same index in every list belongs to the same message
    static ArrayList<String> allMessageIds = new ArrayList<>();
    static ArrayList<String> allRecipients = new ArrayList<>();
    static ArrayList<String> allTexts = new ArrayList<>();
    static ArrayList<String> allHashes = new ArrayList<>();


    // Checks if the username is valid
    public static boolean checkUserName(String username) {

        // Username must have exactly 5 characters
        // and must contain an underscore
        if (username.length() == 5 && username.contains("_")) {
            return true;
        } else {
            return false;
        }
    }


    // Checks if the password is valid
    public static boolean checkPasswordComplexity(String password) {

        // Password must have at least 8 characters
        // and contain uppercase, lowercase, number and special character
        if (password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*")) {

            return true;

        } else {
            return false;
        }
    }


    // Checks if the cellphone number is valid
    public static boolean checkCellPhoneNumber(String number) {

        // Number must start with +27
        // followed by exactly 9 digits
        if (number.matches("^\\+27[0-9]{9}$")) {
            return true;
        } else {
            return false;
        }
    }


    // Handles the registration process
    public static void registerUser() {

        // Infinite loop for username
        while (true) {

            // Ask for username
            System.out.print("Enter your username: ");
            username = input.nextLine();

            // Check username
            boolean usernameCorrect = checkUserName(username);

            // Check if username is correct
            if (usernameCorrect) {
                break;
            } else {
                System.out.println("Username is incorrectly formatted. Please try again.");
            }
        }


        // Infinite loop for password
        while (true) {

            // Ask for password
            System.out.print("Enter your password: ");
            password = input.nextLine();

            // Check password
            boolean passwordCorrect = checkPasswordComplexity(password);

            // Check if password is correct
            if (passwordCorrect) {
                break;
            } else {
                System.out.println("Password is incorrectly formatted. Please try again.");
            }
        }


        // Infinite loop for cellphone number
        while (true) {

            // Ask for cellphone number
            System.out.print("Enter your cellphone number (+27): ");
            cellPhone = input.nextLine();

            // Check cellphone number
            boolean phoneCorrect = checkCellPhoneNumber(cellPhone);

            // Check if cellphone number is correct
            if (phoneCorrect) {
                break;
            } else {
                System.out.println("Cellphone number is incorrectly formatted. Please try again.");
            }
        }


        // Save the valid username
        registeredUsername = username;

        // Save the valid password
        registeredPassword = password;

        // Save the valid cellphone number
        registeredCellPhone = cellPhone;

        // Display success message
        System.out.println("User registered successfully.");
    }


    // Checks the login details
    public static boolean loginUser(String username, String password) {

        // Compare entered details with registered details
        if (username.equals(registeredUsername)
                && password.equals(registeredPassword)) {

            return true;

        } else {
            return false;
        }
    }


    // Returns the login status message
    public static String returnLoginStatus(boolean loginSuccessful) {

        // Check if login was successful
        if (loginSuccessful) {

            return "Welcome, it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }


    // ===================== PART 2 METHODS =====================

    // Creates a random 10 digit message ID
    public static String createMessageId() {

        // Random number between 1000000000 and 9999999999
        long number = 1000000000L + (long) (Math.random() * 9000000000L);

        return String.valueOf(number);
    }


    // Creates the message hash using string manipulation
    // Format: first two digits of ID : message number : FIRSTWORDLASTWORD
    public static String createMessageHash(String messageId, int messageNumber, String text) {

        // First two digits of the message ID (substring)
        String firstTwo = messageId.substring(0, 2);

        // Split the message into words
        String[] words = text.trim().split(" ");

        // First and last word of the message
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // Join everything together in capital letters
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }


    // Displays the details of one message
    public static void displayMessage(Message m) {

        System.out.println("-----------------------------");
        System.out.println("Message ID: " + m.messageId);
        System.out.println("Message Number: " + m.messageNumber);
        System.out.println("Message Hash: " + m.messageHash);
        System.out.println("Recipient: " + m.recipient);
        System.out.println("Message: " + m.text);
        System.out.println("-----------------------------");
    }


    // Saves all messages to a JSON file
    public static void saveMessagesToJson() {

        // Build the JSON text
        String json = "[\n";

        int count = 0;

        // For-each loop through every stored message
        for (Message m : sentMessages) {

            count++;

            json += "  {\n";
            json += "    \"messageId\": \"" + m.messageId + "\",\n";
            json += "    \"messageNumber\": " + m.messageNumber + ",\n";
            json += "    \"messageHash\": \"" + m.messageHash + "\",\n";
            json += "    \"recipient\": \"" + m.recipient + "\",\n";
            json += "    \"message\": \"" + m.text.replace("\\", "\\\\").replace("\"", "\\\"") + "\"\n";
            json += "  }";

            // Add a comma after every message except the last one
            if (count < sentMessages.size()) {
                json += ",";
            }

            json += "\n";
        }

        json += "]";

        // Write the JSON text to a file
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write(json);
            System.out.println("Messages saved to messages.json");
        } catch (IOException e) {
            System.out.println("Could not save the messages to the JSON file.");
        }
    }


    // Lets the user enter the assigned number of messages
    public static void addMessages() {

        // Ask how many messages the user wants to enter
        System.out.print("How many messages do you want to enter? ");

        int numberOfMessages;

        try {
            numberOfMessages = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        // For loop runs once for each message
        for (int i = 1; i <= numberOfMessages; i++) {

            Message m = new Message();

            // Message number is created using the loop counter
            totalMessages++;
            m.messageNumber = totalMessages;

            System.out.println("Message " + i + " of " + numberOfMessages);

            // Ask for the recipient (must be a valid +27 number)
            while (true) {
                System.out.print("Enter the recipient cellphone number (+27): ");
                m.recipient = input.nextLine();

                if (checkCellPhoneNumber(m.recipient)) {
                    break;
                } else {
                    System.out.println("Cellphone number is incorrectly formatted. Please try again.");
                }
            }

            // Ask for the message text (cannot be empty)
            while (true) {
                System.out.print("Enter your message: ");
                m.text = input.nextLine();

                if (!m.text.trim().isEmpty()) {
                    break;
                } else {
                    System.out.println("The message cannot be empty. Please try again.");
                }
            }

            // Create the ID and hash using string manipulation
            m.messageId = createMessageId();
            m.messageHash = createMessageHash(m.messageId, m.messageNumber, m.text);

            // Store the message in the ArrayList
            sentMessages.add(m);

            // Display the message details
            displayMessage(m);
        }

        // Save all the messages to the JSON file
        saveMessagesToJson();
    }


    // ===================== PART 3 METHODS =====================

    // Fills the parallel ArrayLists from the sent messages
    // (the same index in each list is the same message)
    public static void populateArrays() {

        // Clear the old data first so nothing is duplicated
        allMessageIds.clear();
        allRecipients.clear();
        allTexts.clear();
        allHashes.clear();

        // For-each loop adds each message's details to the matching list
        for (Message m : sentMessages) {
            allMessageIds.add(m.messageId);
            allRecipients.add(m.recipient);
            allTexts.add(m.text);
            allHashes.add(m.messageHash);
        }
    }


    // Finds the position of the longest message (returns -1 if there are no messages)
    public static int findLongestMessageIndex() {

        populateArrays();

        int longestIndex = -1;
        int currentIndex = 0;

        // For-each loop through every message text
        for (String text : allTexts) {

            // The first message, or any message longer than the longest so far
            if (longestIndex == -1 || text.length() > allTexts.get(longestIndex).length()) {
                longestIndex = currentIndex;
            }

            currentIndex++;
        }

        return longestIndex;
    }


    // Displays the details of the longest message
    public static void displayLongestMessage() {

        int index = findLongestMessageIndex();

        if (index == -1) {
            System.out.println("No messages have been sent yet.");
        } else {
            System.out.println("Longest message:");
            System.out.println("Message ID: " + allMessageIds.get(index));
            System.out.println("Message Hash: " + allHashes.get(index));
            System.out.println("Recipient: " + allRecipients.get(index));
            System.out.println("Message: " + allTexts.get(index));
        }
    }


    // Returns all the messages that were sent to one recipient
    public static ArrayList<String> searchByRecipient(String recipient) {

        populateArrays();

        ArrayList<String> found = new ArrayList<>();

        int currentIndex = 0;

        // For-each loop through the recipients list
        for (String r : allRecipients) {

            // If the recipient matches, take the message at the same index
            if (r.equals(recipient)) {
                found.add(allTexts.get(currentIndex));
            }

            currentIndex++;
        }

        return found;
    }


    // Asks for a recipient and displays the messages sent to them
    public static void displayMessagesForRecipient() {

        System.out.print("Enter the recipient cellphone number (+27): ");
        String recipient = input.nextLine();

        ArrayList<String> found = searchByRecipient(recipient);

        if (found.isEmpty()) {
            System.out.println("No messages were sent to " + recipient);
        } else {
            System.out.println("Messages sent to " + recipient + ":");

            // For-each loop displays each message found
            for (String text : found) {
                System.out.println("- " + text);
            }
        }
    }


    // Deletes a message using its message hash (returns true if it was deleted)
    public static boolean deleteMessageByHash(String hash) {

        Message messageToDelete = null;

        // For-each loop searches for the message with the matching hash
        for (Message m : sentMessages) {
            if (m.messageHash.equalsIgnoreCase(hash)) {
                messageToDelete = m;
            }
        }

        // Nothing was found
        if (messageToDelete == null) {
            return false;
        }

        // Remove it AFTER the loop (removing inside a for-each loop causes an error)
        sentMessages.remove(messageToDelete);

        // Update the parallel lists and the JSON file
        populateArrays();
        saveMessagesToJson();

        return true;
    }


    // Asks for a hash and deletes that message
    public static void deleteMessage() {

        System.out.print("Enter the message hash to delete: ");
        String hash = input.nextLine();

        if (deleteMessageByHash(hash)) {
            System.out.println("Message \"" + hash + "\" successfully deleted.");
        } else {
            System.out.println("No message with that hash was found.");
        }
    }


    // Reads the value out of one JSON line, e.g.  "recipient": "+27123456789",
    public static String getJsonValue(String line) {

        // Everything after the first colon
        String value = line.substring(line.indexOf(":") + 1).trim();

        // Remove the comma at the end
        if (value.endsWith(",")) {
            value = value.substring(0, value.length() - 1);
        }

        // Remove the quotation marks around text values
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        // Undo the escaping that was done when the file was saved
        return value.replace("\\\"", "\"").replace("\\\\", "\\");
    }


    // Reads messages.json line by line and returns the messages in an ArrayList
    public static ArrayList<Message> readJsonIntoArray() {

        ArrayList<Message> messagesFromFile = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {

            Message current = null;
            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.startsWith("{")) {
                    current = new Message();
                } else if (line.startsWith("\"messageId\"")) {
                    current.messageId = getJsonValue(line);
                } else if (line.startsWith("\"messageNumber\"")) {
                    current.messageNumber = Integer.parseInt(getJsonValue(line));
                } else if (line.startsWith("\"messageHash\"")) {
                    current.messageHash = getJsonValue(line);
                } else if (line.startsWith("\"recipient\"")) {
                    current.recipient = getJsonValue(line);
                } else if (line.startsWith("\"message\"")) {
                    current.text = getJsonValue(line);
                } else if (line.startsWith("}")) {
                    // End of one message, add it to the list
                    messagesFromFile.add(current);
                }
            }

        } catch (IOException e) {
            System.out.println("Could not read messages.json. Send some messages first.");
        }

        return messagesFromFile;
    }


    // Displays the messages that were read from the JSON file
    public static void displayMessagesFromJson() {

        ArrayList<Message> messagesFromFile = readJsonIntoArray();

        if (messagesFromFile.isEmpty()) {
            System.out.println("There are no messages in the JSON file.");
        } else {
            // For-each loop displays every message read from the file
            for (Message m : messagesFromFile) {
                displayMessage(m);
            }
        }
    }


    // Displays a report of all the sent messages
    public static void displayMessageReport() {

        if (sentMessages.isEmpty()) {
            System.out.println("No messages have been sent yet.");
            return;
        }

        System.out.println("========== MESSAGE REPORT ==========");

        // For-each loop prints the details of every message
        for (Message m : sentMessages) {
            System.out.println("Message Hash: " + m.messageHash);
            System.out.println("Recipient: " + m.recipient);
            System.out.println("Message: " + m.text);
            System.out.println("------------------------------------");
        }

        System.out.println("Total messages sent: " + sentMessages.size());
    }


    // Shows the welcome message and the menu, runs until the user quits
    public static void runMenu() {

        System.out.println("Welcome to QuickChat.");

        int choice = 0;

        // While loop keeps the application running until the user enters 3
        while (choice != 3) {

            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            // Part 3 menu options
            System.out.println("4) Display the longest message");
            System.out.println("5) Search messages by recipient");
            System.out.println("6) Delete a message using its hash");
            System.out.println("7) Read messages from the JSON file");
            System.out.println("8) Display message report");
            System.out.print("Enter option (1-8): ");

            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            if (choice == 1) {

                addMessages();

            } else if (choice == 2) {

                if (sentMessages.isEmpty()) {
                    System.out.println("No messages have been sent yet.");
                } else {
                    // For-each loop displays every message
                    for (Message m : sentMessages) {
                        displayMessage(m);
                    }
                }

            } else if (choice == 3) {

                System.out.println("Goodbye.");

            } else if (choice == 4) {

                displayLongestMessage();

            } else if (choice == 5) {

                displayMessagesForRecipient();

            } else if (choice == 6) {

                deleteMessage();

            } else if (choice == 7) {

                displayMessagesFromJson();

            } else if (choice == 8) {

                displayMessageReport();

            } else {

                System.out.println("Invalid option. Please enter a number from 1 to 8.");
            }
        }
    }


    // Main method
    public static void main(String[] args) {

        // Call registration method
        registerUser();


        // Ask user to login
        System.out.print("Enter your username to login: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter your password to login: ");
        String loginPassword = input.nextLine();


        // Call login method
        boolean loginSuccessful = loginUser(loginUsername, loginPassword);

        // Call login status method
        System.out.println(returnLoginStatus(loginSuccessful));


        // Part 2: only start the menu if the login worked
        if (loginSuccessful) {
            runMenu();
        }
    }
}
