package com.oceanview.oceanviewresort.model;

public class Staff {

    private int     staffID;
    private String  fullName;
    private String  userName;
    private String  staffPassword;
    private String  role;         // ADMIN | RECEPTIONIST
    private boolean isActive;

    
    public Staff() {}

    public Staff(String fullName, String userName, String staffPassword, String role) {
        this.fullName      = fullName;
        this.userName      = userName;
        this.staffPassword = staffPassword;
        this.role          = role;
        this.isActive      = true;
    }

    
    public int getStaffID() { return staffID; }
    public void setStaffID(int staffID) { this.staffID = staffID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getStaffPassword() { return staffPassword; }
    public void setStaffPassword(String staffPassword) { this.staffPassword = staffPassword; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    @Override
    public String toString() {
        return "Staff{" +
               "staffID=" + staffID +
               ", fullName='" + fullName + '\'' +
               ", userName='" + userName + '\'' +
               ", role='" + role + '\'' +
               ", isActive=" + isActive +
               '}';
    }
}