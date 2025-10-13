/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_Lab
 */
public class ChatApp {

    public static void main(String[] args) {
        Login register = new Login();
        register.registerUser();  // Register user

        // login
        boolean loggedIn = register.loginUser();

        // only if login successful
        if (loggedIn) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

            Message msgSystem = new Message();
            boolean running = true;

            while (running) {
                String choice = JOptionPane.showInputDialog(
                        "Choose an option:\n1) Send Message\n2) Show Messages\n3) Quit");

                if (choice == null || choice.equals("3")) {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    running = false;
                } else if (choice.equals("1")) {
                    msgSystem.sentMessage();  // ✅ updated name
                } else if (choice.equals("2")) {
                    msgSystem.printMessages(); // ✅ static, but fine to call via instance
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
                }
            }
        } else {
            System.out.println("Exiting program. Please register or log in again.");
        }
    }
    
}