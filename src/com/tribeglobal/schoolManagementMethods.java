package com.tribeglobal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class schoolManagementMethods implements  managementInterface{
Connection connect;
String query;
PreparedStatement statement;
int selected;
Scanner sc = new Scanner(System.in);

    @Override
    public void user(users user) {
        connect = DBConnection.createDBConnection();
        query = "INSERT into school_management (name, password, role) values(?,?,?)";

        try {
            statement = connect.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, "student");
            int row = statement.executeUpdate();
            if (row > 0){
                System.out.println("user created successfully");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void login(String name, String password) {
        connect = DBConnection.createDBConnection();
        query  = "select name, password, role from school_management where name=?";
        String roleQuery  = "select role from school_management where name=?";
        try {
            statement = connect.prepareStatement(query);
            PreparedStatement roleStatement = connect.prepareStatement(roleQuery);
            statement.setString(1, name);
            roleStatement.setString(1, name);
            ResultSet result = statement.executeQuery();

//  using the if statement to make sure that the else statement runs if nothing is returned instead of the while loop that never runs id nothing is returned
            if (result.next()){
                if (name.equalsIgnoreCase(result.getString("name")) && password.equalsIgnoreCase(result.getString("password"))){
                    if (result.getString("role").equalsIgnoreCase("Admin")){
                        System.out.println("Welcome Admin");
                        admin(name);
                    }else if (result.getString("role").equalsIgnoreCase("professor")){
                        System.out.println("Welcome Professor " + name);
                        professor(name);
                    }else if (result.getString("role").equalsIgnoreCase("Student")){
                        System.out.println("Welcome " + name);
                        System.out.println("if you are a professor please send an email to management to give you professor privileges");
                        System.out.println("1. Check your Grade");
                        selected = sc.nextInt();
                        if (selected == 1){
                            student(name);
                        }
                    }
                }
                if (!password.equalsIgnoreCase(result.getString("password"))){
                    System.out.println("Password Incorrect");
                }
            }else System.out.println("user does not exist");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void student(String name) {
        connect = DBConnection.createDBConnection();
        query = "select grade, result from school_management where name=?";
        try {
            statement = connect.prepareStatement(query);
            statement.setString(1,name);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                if (result.getString("result") == null){
                    System.out.println("Your grade is not yet ready for viewing");
                }else System.out.println("Your Grade Is "+result.getString("grade") + " You " + result.getString("result").toUpperCase());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void professor(String name) {
        connect = DBConnection.createDBConnection();
        query = "select name, grade from school_management where name =?";
        String updateGradeQuery = "update school_management set grade=? where name=?";
        String updateResultQuery = "update school_management set result=? where name=?";

        System.out.println("""
                what will you like to do?
                1. Grade Students
                2. View All Student Grade
                3. Update Student Grade
                4. View A Student Record""");
        selected = sc.nextInt();
        switch (selected) {
            case 1, 3 -> {
                System.out.println("Please enter student name for grading");
                name = sc.next();
                try {
                    statement = connect.prepareStatement(query);
                    statement.setString(1, name);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        System.out.println("You Will Be Grading " + result.getString("name").toUpperCase());
                        System.out.println("Please Enter Corresponding Grade: ");
                        double grade = sc.nextDouble();
                        statement = connect.prepareStatement(updateGradeQuery);
                        statement.setDouble(1, grade);
                        statement.setString(2, name);
                        int row = statement.executeUpdate();
                        statement = connect.prepareStatement(updateResultQuery);
                        if (grade >= 3.5) {
                            statement.setString(1, "PASS");
                        } else {
                            statement.setString(1, "FAIL");
                        }
                        if (row > 0) {
                            System.out.println("Student Graded Successfully");
                        }

                    } else System.out.println("Student Not Found");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                query = "select name, grade, result from school_management where role=?";
                try {
                    statement = connect.prepareStatement(query);
                    statement.setString(1, "student");
                    ResultSet result = statement.executeQuery();
                    while (result.next()){
                        System.out.println(" | "+result.getString("name") + " | " + " | " + result.getDouble("grade") +
                                " | " + " | " + result.getString("result") + " | ");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            case 4 -> {
                query = "select name, grade, result from school_management where name=?";
                System.out.println("Enter Students Name");
                String stdName = sc.next();
                try {
                    statement = connect.prepareStatement(query);
                    statement.setString(1, stdName);
                    ResultSet result = statement.executeQuery();
                    if (result.next()){
                        System.out.println(" | "+result.getString("name") + " | " + " | " + result.getDouble("grade") +
                                " | " + " | " + result.getString("result") + " | ");
                    }else System.out.println("No Record On This Student (Does Not Exist)");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            default -> System.out.println("Please Enter A Valid Input");
        }
    }

    @Override
    public void admin(String name) {
        connect = DBConnection.createDBConnection();
        query = "select name from school_management where name=?";
        String updateRoleQuery = "update school_management set role=? where name=?";
        System.out.println("""
                What Will You Like To Do Today
                1. Assign User Role""");
        selected = sc.nextInt();
        if (selected == 1){
            System.out.println("Enter User Name");
            String userName = sc.next();
            try {
                statement = connect.prepareStatement(query);
                statement.setString(1, userName);
                ResultSet result = statement.executeQuery();
                if (result.next()){
                    System.out.println("Specify Role:");
                    String role = sc.next();
                    if (role.equalsIgnoreCase(Roles.ADMIN.name()) || role.equalsIgnoreCase(Roles.PROFESSOR.name()) || role.equalsIgnoreCase(Roles.STUDENT.name())){
                        statement = connect.prepareStatement(updateRoleQuery);
                        statement.setString(1, role);
                        statement.setString(2, userName);
                        int row = statement.executeUpdate();
                        if (row > 0){
                            System.out.println("User Role Updated Successfully");
                        }
                    }else System.out.println("No Such Roles");

                }else System.out.println("No User Found");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
