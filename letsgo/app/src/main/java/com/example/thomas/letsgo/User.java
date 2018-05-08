package com.example.thomas.letsgo;

public class User {
    private String Username;
    private String Email;
    private String Password;


    public User() {
    }

    public User(String username,String email,String password) {
        this.Username = username;
        this.Email= email;
        this.Password= password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}



