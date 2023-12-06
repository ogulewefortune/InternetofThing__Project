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
 *                   Account (parent class)
 * ------------------------------------------------------------------------------------
 */
public class Admin extends Account {

    public Admin(String firstName, String lastName, String email, String password) {
	super(firstName, lastName, email, password);
    }

}
