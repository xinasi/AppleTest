package com.cyut.fruit;

public class User {
    public String email, password;

    public User(){

    }

    public User(String email) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
