/* ------------------------------------------------------------------------------------
 * SmartDevice.java
 * 
 * Copyright (c) 2023 Venos Tech. All rights reserved
 * 
 * Related Documents: 
 *    Specification Document 
 *    Design Document
 * 
 * File created by Duong Chan Hung on 11/08/2023
 * 
 * Associated Files: IoTController.java (one to many)
 *                   Admin.java (child class)
 *                   User.java (child class) 
 * ------------------------------------------------------------------------------------
 */
public abstract class Account {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Account(String firstName, String lastName, String email, String password) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.password = password;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getFirstName() {
	return this.firstName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getLastName() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getEmail() {
	return this.email;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getPassword() {
	return this.password;
    }

    // print function for account information
    @Override
    public String toString() {
	return "\n{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\''
		+ '}';
    }

}
