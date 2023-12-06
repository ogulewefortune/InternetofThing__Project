
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
 * Associated file: IoTServer.java (one to one) 
 *                  SmartDevice.java (many to one) 
 * ------------------------------------------------------------------------------------
 */
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IoTController {
    // List to hold accounts
    private List<Account> accounts;

    // List to hold smart devices
    private List<SmartDevice> devices;
    private SmartDevice thermo; // device ID: 0
    private SmartDevice light; // device ID: 1
    private SmartDevice lock; // device ID: 2
    private SmartDevice water; // device ID: 3
    private SmartDevice camera; // device ID: 4

    private Account admin;
    private Account user;

    public IoTController() {
	// --- INITIALIZE SMART DEVICES ---
	// Create instance of a list of type SmartDevice
	devices = new ArrayList<SmartDevice>();
	// Create instances of smart devices
	thermo = new SmartThermostat(0, true);
	light = new SmartLight(1, true);
	lock = new SmartLock(2, true);
	water = new SmartWaterSystem(3, true);
	camera = new SmartSecurityCamera(4, true);

	// Add to the List<SmartDevice>
	devices.add(thermo.deviceID(), thermo);
	devices.add(light.deviceID(), light);
	devices.add(lock.deviceID(), lock);
	devices.add(water.deviceID(), water);
	devices.add(camera.deviceID(), camera);
	// --- END ---

	// --- INITIALIZE ACCOUNTS ---
	accounts = new ArrayList<Account>();
	// --- END ---

    }

    // --- FUNCTIONS TO UPDATE AND RETURN THE STATUS OF DEVICES ---

    public void setDeviceStatus(String device, boolean status) {
	// Switch case based on the name of the chosen device passed from the IoTServer
	switch (device) {
	case "thermo":
	    devices.get(0).setDeviceStatus(status);
	    break;
	case "light":
	    devices.get(1).setDeviceStatus(status);
	    break;
	case "lock":
	    devices.get(2).setDeviceStatus(status);
	    break;
	case "water":
	    devices.get(3).setDeviceStatus(status);
	    break;
	case "camera":
	    devices.get(4).setDeviceStatus(status);
	    break;
	default:
	    break;
	}
    }

    public Boolean getDeviceStatus(int device) {
	Boolean tempStatus = null;
	// Get the status of the device based on the device ID
	for (int i = 0; i < devices.size(); i++) {
	    if (devices.get(i).deviceID() == device) {
		tempStatus = devices.get(i).getDeviceStatus();
	    }
	}
	return tempStatus;
    }

    // --- END ---

    // --- FUNCTION TO RETURN SMARTDEVICE ALERT MESSAGE ---

    public String getDeviceAlertMessage(int device) {
	String tempStr = null;
	// Get the alert message of the device based on the device ID
	for (int i = 0; i < devices.size(); i++) {
	    if (devices.get(i).deviceID() == device) {
		tempStr = devices.get(i).alertMessage();
	    }
	}
	return tempStr;
    }

    // --- END ---

    // --- CALL FUNCTIONS FROM THE SMART THERMOSTAT CLASS ---

    // The function receives an action string and an increment value of the
    // temperature (in this case is 1)
    public void updateTemperature(String msg, Integer temperature) {
	if ("thermoIncrease".equals(msg)) {
	    ((SmartThermostat) devices.get(0)).increase(temperature);
	}
	if ("thermoDecrease".equals(msg)) {
	    ((SmartThermostat) devices.get(0)).decrease(temperature);
	}
    }

    public Integer getUpdateTemp() {
	return ((SmartThermostat) devices.get(0)).getTemperature();
    }

    // --- END ---

    // --- CALL FUNCTIONS FROM THE SMART LIGHT CLASS ---

    public void setLightColor(String color) {
	((SmartLight) devices.get(1)).setColor(color);
    }

    public String getLightColor() {
	return ((SmartLight) devices.get(1)).getColor();
    }

    // The function receives an action string and an increment value of the
    // brightness (in this case is 10)
    public void updateBrightness(String msg, Integer brightness) {
	if ("lightBrightnessIncrease".equals(msg)) {
	    ((SmartLight) devices.get(1)).increaseBrightness(brightness);
	}
	if ("lightBrightnessDecrease".equals(msg)) {
	    ((SmartLight) devices.get(1)).decreaseBrightness(brightness);
	}
    }

    public Integer getUpdateBrightness() {
	return ((SmartLight) devices.get(1)).getBrightness();
    }

    // --- END ---

    // --- CALL FUNCTIONS FROM THE SMART LOCK CLASS

    public void setLockPassword(String newPassword) {
	((SmartLock) devices.get(2)).setLockPassword(newPassword);
    }

    public String getLockPassword() {
	return ((SmartLock) devices.get(2)).getLockPassword();
    }

    // --- END ---

    // --- CALL FUNCTIONS FROM THE SMART WATER SYSTEM CLASS ---

    public void setWaterLimit(Integer waterLimit) {
	((SmartWaterSystem) devices.get(3)).setWaterLimit(waterLimit);
    }

    public Integer getWaterLimit() {
	return ((SmartWaterSystem) devices.get(3)).getWaterLimit();
    }

    // The function receives a time of type String, and it will convert from String
    // to LocalTime data type
    public void setWaterTimer(String timeStr) {
	DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
	LocalTime tempTimer = LocalTime.parse(timeStr, format);
	((SmartWaterSystem) devices.get(3)).setTimer(tempTimer);
    }

    public LocalTime getWaterTimer() {
	return ((SmartWaterSystem) devices.get(3)).getTimer();
    }
    // --- END ---

    // --- CALL FUNCTIONS FROM THE SMART SECURITY CAMERA CLASS ---

    public void setCameraAngle(Integer angle) {
	((SmartSecurityCamera) devices.get(4)).setAngle(angle);
    }

    public Integer getCameraAngle() {
	return ((SmartSecurityCamera) devices.get(4)).getAngle();
    }

    public String getVideoResource() {
	// GET MP4 FILE AND SEND IT TO CLIENT
	String cameraResource = ((SmartSecurityCamera) devices.get(4)).getVideoResource();
	return cameraResource;
    }

    // --- END ---

    // --- CALL FUNCTION FROM THE ACCOUNT CLASS ---

    public String setUserInformation(String fName, String lName, String email, String password) {
	user = new User(fName, lName, email, password);
	accounts.add(user);
	return accounts.toString();
    }

    public String setAdminInformation(String fName, String lName, String email, String password) {
	admin = new Admin(fName, lName, email, password);
	accounts.add(admin);
	return accounts.toString();
    }
    // --- END ---

}
