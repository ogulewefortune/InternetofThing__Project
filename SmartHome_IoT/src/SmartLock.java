/* ------------------------------------------------------------------------------------
 * IoTController.java


 * 
 * Copyright (c) 2023 Venos Tech. All rights reserved
 * 
 * Related Documents: 
 *    Specification Document 
 *    Design Document
 * 
 * File created by Duong Chan Hung on 11/08/2023
 * 
 * Associated file: 
 * ------------------------------------------------------------------------------------
 */
public class SmartLock extends SmartDevice {

    private String lockPassword;

    public SmartLock(Integer deviceID, boolean status) {
	super(deviceID, status);
	// TODO Auto-generated constructor stub
	lockPassword = null;
    }

    public void setLockPassword(String lockPassword) {
	this.lockPassword = lockPassword;
    }

    public String getLockPassword() {
	return this.lockPassword;
    }

    @Override
    public String alertMessage() {
	// TODO Auto-generated method stub
	return "Intruder alert !!!";
    }

}
