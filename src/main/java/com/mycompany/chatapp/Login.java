/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author RC_Student_Lab
 */
import java.util.Scanner;

public class Login {

    private String password;
    private String userName;
    private String phoneNumber;
    private boolean isRegistered = false;

    Scanner scanner = new Scanner(System.in);

    public boolean checkUserName(String userName) {
        return userName.length() <= 5 && userName.contains("_");
    }

    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\+27\\d{9}");
    }
    
    public String registerUser(){
        System.out.print("Enter username: ");
        String inputUserName = scanner.nextLine();

        if (!checkUserName(inputUserName)) {
            System.out.println("""
                               Username is not correctly formatted.
                               Username must be less than 5 characters long and contain an underscore.""");
            return null;
        }

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        if (!checkPasswordComplexity(inputPassword)) {
            System.out.println("""
                               Password is not correctly formatted.
                               Password must be at least 8 characters long,
                               contain a capital letter, a number, and a special character.""");
            return null;
        }

        System.out.print("Enter phone number (e.g. +27831234567): ");
        String inputPhone = scanner.nextLine();

        if (!checkCellPhoneNumber(inputPhone)) {
            System.out.println("""
                               Cell phone number is not correctly formatted.
                               It must start with +27 and be followed by 9 digits.""");
            return null;
        }
        this.userName = inputUserName;
        this.password = inputPassword;
        this.phoneNumber = inputPhone;
        this.isRegistered = true;

        System.out.println("Registered successfully.");
        return null;
    }
    public boolean loginUser(){

        if (!isRegistered) {
            System.out.println("No user registered yet.");
            return false;
        }

        System.out.print("Enter username: ");
        String inputUserName = scanner.nextLine();

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        return this.userName.equals(inputUserName) && this.password.equals(inputPassword);
    }

    public String returnLoginStatus(){
        if (loginUser()){
            System.out.print("Login successful");
            return "Login successful";
        } else {
            System.out.print("Username or password incorrect");
            return "Username or password incorrect";
        }
    }


    
}
