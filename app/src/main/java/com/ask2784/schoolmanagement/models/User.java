package com.ask2784.schoolmanagement.models;

public class User {
    private String uId;
    private String userType;
    private String name;
    
    public User(){
    }
    
    public User(String uId, String userType, String name) {
        this.uId = uId;
        this.userType = userType;
        this.name = name;
    }
    
    public String getUId() {
        return this.uId;
    }
    public void setUId(String uId) {
        this.uId = uId;
    }
    public String getUserType() {
        return this.userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
