package com.tribeglobal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        schoolManagementMethods dao = new schoolManagementMethods();
        String name, password;
        int selected;
        users user;
        while (true){
            System.out.println("Welcome To Novena School Management System");
            System.out.println("""
                1. Sign up
                2. login
                3. Exit
                """);
            selected = sc.nextInt();
            switch (selected){
                case 1 -> {
                    System.out.println("Enter Name");
                    name = sc.next();
                    System.out.println("Enter Password");
                    password = sc.next();
                    user = new users();
                    user.setName(name);
                    user.setPassword(password);
                    dao.user(user);
                    System.out.println("Your Registration Was Successful");
                }
                case 2 -> {
                    System.out.println("Enter Name");
                    name = sc.next();
                    System.out.println("Enter Password");
                    password = sc.next();
                    dao.login(name,password);
                }
                default -> System.exit(0);
            }
        }
    }
}
