package com.wallet.yukoni.models;

public class SignUpRequest {

    private String name = "";
    private String email = "";
    private String password = "";
    // private Integer roleId;


    public SignUpRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        //this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   /* public Integer getId() {
        return roleId;
    }

    public void setId(Integer id) {
        this.roleId = id;
    }*/
}
