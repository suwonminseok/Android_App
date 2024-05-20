package com.intervoid.parent_app_ver2;

import java.util.Map;

public class users {

    public String userID;
    public String userAdress;
    public String userGender;
    public String userName;
    public String userSSID;
    public String userNumber;
    public Map<String, Object> data;

    public users(){}

    public String getID() { return userID;}
    public void setID(String userID) { this.userID = userID;}

    public String getAddress() { return userAdress;}
    public void setAddress(String userAdress) { this.userAdress = userAdress;}

    public String getGender() { return userGender;}
    public void setGender(String userGender) { this.userGender = userGender;}

    public String getName() { return userName;}
    public void setName(String userName) { this.userName = userName;}

    public String getSSID() { return userSSID;}
    public void setSSID(String userSSID) { this.userSSID = userSSID;}

    public String getNumber() {return  userNumber;}
    public void setUserNumber(String userNumber) {this.userNumber = userNumber;}

    public Map<String, Object> getData() {return data;}
    public void setData(Map<String, Object> data) {this.data = data;}
}
