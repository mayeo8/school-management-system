package com.tribeglobal;

public class users {
    private String name, password, result, role;
    private int grade;

    public users(String name, String password, String result, int grade) {
        this.name = name;
        this.password = password;
        this.result = result;
        this.grade = grade;
    }

    public users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

}