package com.intervoid.parent_app_ver2;

public class Get_Alarm_data {

    private String name;
    private boolean isSwitch;

    public Get_Alarm_data(String name, boolean isSwitch) {
        this.name = name;
        this.isSwitch = isSwitch;
    }

    public String getChildname() {
        return name;
    }

    public void setChildname(String name) {
        this.name = name;
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setIsSwitch(boolean isSwitch){
        this.isSwitch = isSwitch;
    }
}
