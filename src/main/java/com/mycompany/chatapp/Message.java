/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author Tumelo
 */
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Message {
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalMessages = 0;
    private static int idCounter = 1; // Auto ID generator
    private static ArrayList<Message> sentMessages = new ArrayList<>();

    // Boolean: checkMessageID() — still needed for testing
    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10 && !id.trim().isEmpty();
    }

    //  Int: checkRecipientCell()
    public int checkRecipientCell(String cell) {
        if (cell != null && cell.startsWith("+27") && cell.length() == 12) {
            return 1;
        }
        return 0;
    }

    // String: createMessageHash()
    public String createMessageHash(String id, String text) {
        if (id == null || id.length() < 2 || text == null || text.trim().isEmpty()) {
            return "";
        }
        String firstTwo = id.substring(0, 2);
        String[] words = text.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return firstTwo + ":" + firstWord + lastWord;
    }

    // Auto-generate Message ID
    private String generateMessageID() {
        String id = String.format("MSG%03d", idCounter++);
        if (!checkMessageID(id)) {
            id = "MSG" + System.currentTimeMillis(); // fallback if something goes wrong
        }
        return id;
    }

    // String: sentMessage()
    public String sentMessage() {
        // Auto-generate ID here
        messageID = generateMessageID();

        // Ask for recipient
        String rec = JOptionPane.showInputDialog("Enter recipient number (+27 and 9 digits, e.g. +27831234567):");
        if (rec == null || checkRecipientCell(rec) == 0) {
            JOptionPane.showMessageDialog(null, "Invalid recipient number! Must start with +27 and be 12 characters long.");
            return "Invalid Recipient";
        }

        // Ask for message text
        String msg = JOptionPane.showInputDialog("Enter message text (max 250 chars):");
        if (msg == null || msg.length() > 250) {
            JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
            return "Invalid Message";
        }

        recipient = rec;
        messageText = msg;
        messageHash = createMessageHash(messageID, msg);

        // User chooses what to do
        String[] options = {"Send", "Disregard", "Store in JSON"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose what to do with this message:\n\nMessage ID: " + messageID,
                "QuickChat",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            sentMessages.add(this);
            totalMessages++;
            JOptionPane.showMessageDialog(null, "Message sent!\nMessage ID: " + messageID);
            return "Message Sent";
        } else if (choice == 1) {
            JOptionPane.showMessageDialog(null, "Message disregarded.");
            return "Disregarded";
        } else if (choice == 2) {
            storeMessage();
            return "Stored";
        }

        return "No Action";
    }

    // String: printMessages()
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent yet.");
            return "No Messages";
        }

        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n\n");
        }

        sb.append("Total messages sent: ").append(totalMessages);
        JOptionPane.showMessageDialog(null, sb.toString());
        return sb.toString();
    }

    // Int: returnTotalMessages()
    public int returnTotalMessages() {
        return totalMessages;
    }

    // Custom: storeMessage()
    public void storeMessage() {
        StringBuilder json = new StringBuilder();
        json.append("[\n");

        for (int i = 0; i < sentMessages.size(); i++) {
            Message m = sentMessages.get(i);
            json.append("  {\n");
            json.append("    \"MessageID\": \"").append(escapeJson(m.messageID)).append("\",\n");
            json.append("    \"Recipient\": \"").append(escapeJson(m.recipient)).append("\",\n");
            json.append("    \"Message\": \"").append(escapeJson(m.messageText)).append("\",\n");
            json.append("    \"MessageHash\": \"").append(escapeJson(m.messageHash)).append("\"\n");
            json.append("  }");
            if (i < sentMessages.size() - 1) json.append(",");
            json.append("\n");
        }

        json.append("]");

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(json.toString());
            file.flush();
            JOptionPane.showMessageDialog(null, "Messages saved to JSON file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages.");
        }
    }

    // Escape text for JSON
    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    // Helper for testing
    public static void clearMessages() {
        sentMessages.clear();
        totalMessages = 0;
        idCounter = 1;
    }
}