package com.tribeglobal;

public interface managementInterface {
    /**
     * register users of all kinds
     * */
    void user(users user);
    /**
     * deals with the verification password and stuff
     * */
    void login(String name, String password);
    /**
     *   assigning the functionalities of the student role just be able to check result
     *   tells the student if the result is ready foe viewing or not*/
    void student(String name);
    /**
     *   specifies the role of the teacher or professor to be able to grade students by getting their name and assigning their grades
     *   and also be able to update it if there is a mistake with the grading*/
    void professor(String name);
    /**
     * assigns role to all users by default all registered users are students the professor has to notify the admin of a change of role
     * delete and update roles
     * */
    void admin(String name);
}
