/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

/**
 *
 * @author RC_Student_Lab
 */
public class ChatApp {

    public static void main(String[] args) {
        Login register = new Login();
        register.registerUser();  // Register user

        System.out.println("\n--- Now try to log in ---");
        register.returnLoginStatus();     // Attempt login
    }
    
}