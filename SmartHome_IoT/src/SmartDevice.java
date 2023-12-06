
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
 *                   SmartLight.java (subclass)
 *                   SmartThermostat.java (subclass)
 *                   SmartLock.java (subclass)
 *                   SmartSecurityCamera (subclass)
 *                   SmartWaterSystem (subclass)
 * ------------------------------------------------------------------------------------
 */
public abstract class SmartDevice {
    private Integer deviceID;
    private Boolean status;

    // Constructor
    public SmartDevice(Integer deviceID, boolean status) {
	this.deviceID = deviceID;
	this.status = status;
    }

// --- SETTER AND GETTER FOR THE DEVICE ID AND DEVICE STATUS ---     	
    public void setDeviceID(Integer deviceID) {
	this.deviceID = deviceID;
    }

    public Integer deviceID() {
	return deviceID;
    }

    public void setDeviceStatus(boolean status) {
	this.status = status;
    }

    public Boolean getDeviceStatus() {
	return status;
    }
// --- END ---

    // Abstract method to be implemented by subclasses for device-specific
    // functionality
    public abstract String alertMessage();

}
