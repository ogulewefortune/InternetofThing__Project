
/* ------------------------------------------------------------------------------------

* SmartHomeClient.java

* Copyright (c) 2023 Venos Tech. All rights reserved

* Related Documents:
* Specification Document
* Design Document


* File created by Said Hassan

* Associated file:

* ------------------------------------------------------------------------------------

*/

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.lloseng.ocsf.client.AbstractClient;

import javafx.application.Platform;

/*

LIGHT, LOCK, THERMOSTAT, CAMERA, WATER_SYSTEM, TEMPERATURE_INCREMENT, TEMPERATURE_DECREMENT,

LIGHT_STATUS, LIGHT_BRIGHTNESS_UPDATE, LIGHT_COLOR_UPDATE, WATER_USAGE, WATER_LIMIT, USER_INFO, LOCK_STATUS,

CAMERA_ANGLE

*/

public class SmartHomeClient extends AbstractClient {
    // create instance of GUIController class
    private GUIController controller;
    private int temp;
    private String pass;
    private String waterTime;
    private String waterLimit;

    // setter and getter method to handle messages from server
    public void setTempData(int temp) {
	this.temp = temp;
    }//

    public int getTempData() {

	return this.temp;
    }

    public void setPass(String pass) {
	this.pass = pass;
    }

    public String getPass() {
	return this.pass;
    }

    public void setWaterTime(String waterTime) {
	this.waterTime = waterTime;
    }

    public String getWaterTime() {
	return this.waterTime;
    }

    public void setWaterLimit(String waterLimit) {
	this.waterLimit = waterLimit;
    }

    public String getWaterLimit() {
	return this.waterLimit;
    }

    // Constructor with the parameters including the controller class
    public SmartHomeClient(String host, int port, GUIController controller) {

	super(host, port);
	this.controller = controller;
    }

    public void connectToServer() {
	try {
	    // OCSF method inherited from AbstractClient Class to open connection to a
	    // server
	    openConnection();
	    System.out.println("Connected to server!");
	} catch (IOException e) {
	    e.printStackTrace();
	    System.err.println("Error - Cannot connect to server!");
	}
    }

    /* -------------------- USER SIGN PAGE ------------------------- */

    // Send Email and Password after User has Signed Up to Server
    public void userSignUpToServer(String user) {
	try {
	    sendToServer(user);
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /* -------------------- ADMIN SIGN PAGE ------------------------- */

    // Send Email and Password after User has Signed Up to Server.
    public void adminSignUpToServer(String admin) {
	try {
	    sendToServer(admin);
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /* -------------------- MAIN MENU PAGE ------------------------- */

    // Send Msg to Server When Main Menu Thermostat Button Pressed
    public void thermostatMainMenuToServer() {
	try {
	    sendToServer("thermoData");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Send Msg to Server When Main Menu Smart Light Button Pressed
    public void lightMainMenuToServer() {
	try {
	    sendToServer("lightData");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Send Msg to Server When Main Menu Water System Button Pressed
    public void waterMainMenuToServer() {
	try {
	    sendToServer("waterData");
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    // Send Msg to Server When Main Menu Smart Lock Button Pressed
    public void lockMainMenuToServer() {
	try {
	    sendToServer("lockData");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Send Msg to Server When Main Menu Security Camera Button Pressed
    public void cameraMainMenuToServer() {
	try {
	    sendToServer("cameraData");
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /* --------------------- SMART THERMOSTAT PAGE --------------------- */

    public void temperatureIncrementToServer() {
	// if client is connected to server do the following actions
	try {
	    // Send message to decrease to the server
	    sendToServer("thermoIncrease");
	    // Display message on the console after decrementing the temperature
	    System.out.println("Sent Temperature Increase Message To Server");
	} catch (IOException e) {
	    // Error handle message
	    e.printStackTrace();
	    System.err.println("Error sending temperature decrease");
	}
    }

    public void temperatureDecrementToServer() throws IOException {
	try {
	    // Send message to decrease to the server
	    sendToServer("thermoDecrease");
	    // Display message on the console after decrementing the temperature
	    System.out.println("Sent Temperature Decrease Message To Server");
	} catch (IOException e) {
	    // Error handle message
	    e.printStackTrace();
	    System.err.println("Error sending temperature decrease");
	}

    }

    public void thermostatOnToServer() {
	try {
	    sendToServer("thermoON");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void thermostatOffToServer() {
	try {
	    sendToServer("thermoOFF");
	} catch (IOException e) {
	    e.printStackTrace();

	}

    }

    /* ----------------------- SMART LIGHT PAGE ----------------------- */

    public void lightStatus() {
	// Status of light
    }

    public void lightBrightnessIncreaseToServer() {
	try {
	    // Send message to increase the brightness to the server
	    sendToServer("lightBrightnessIncrease");
	    // Display message on the console after sending message
	    System.out.println("Sent brightness increase of request to server");
	} catch (IOException e) {
	    // Error handle message
	    e.printStackTrace();
	    System.err.println("Error sending brightness increase");
	}
    }

    public void lightBrightnessDecreaseToServer() {
	try {
	    // Send message to Decrease the brightness to the server
	    sendToServer("lightBrightnessDecrease");
	    // Display message on the console after sending message
	    System.out.println("Sent brightness decrease of request to server");
	} catch (IOException e) {
	    // Error handle message
	    e.printStackTrace();
	    System.err.println("Error sending brightness decrease");
	}
    }

    public void lightColorChangeToServer(String color) {
	try {
	    // Send message to increase the brightness to the server
	    sendToServer(color);
	    // Display message on the console after sending message
	    System.out.println("Sent color change request to server");
	} catch (IOException e) {
	    // Error handle message
	    e.printStackTrace();
	    System.err.println("Error sending color change");
	}
    }

    // Send message to Server to Turn OFF
    public void smartLightOnToServer() {
	try {
	    sendToServer("lightON");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Send message to Server to Turn ON
    public void smartLightOffToServer() {
	try {
	    sendToServer("lightOFF");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /* --------------------------- SMART LOCK PAGE ----------------------- */

    // Send Lock Door To Server
    public void lockDoorMsgToServer() {
	try {
	    sendToServer("lockON,");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Send Unlock Door with entered Password to Server
    public void unlockDoorMsgToServer(String enteredPassword) {
	try {
	    sendToServer("lockOFF," + enteredPassword);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Send the set password To Server
    public void sendSetPasswordMsgToServer(String pass) {
	setPass(pass);
	try {
	    sendToServer("lockpass," + pass);
	    System.out.println("User Password: " + pass + " sent to Server");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /* --------------------------- SMART WATER PAGE ------------------------ */

    // Send the time to set Duration of water to server.
    public void waterTimeMsgToServer(String water) {
	setWaterTime(water);
	try {
	    sendToServer(getWaterTime());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Send the Water Limit to Server.
    public void waterLimitMsgToServer(String waterLimit) {
	setWaterLimit(waterLimit);

	try {
	    sendToServer(getWaterLimit());
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /* -------------------- SMART SECURITY CAMERA PAGE ----------- */

    // Send the camera angle to server.
    public void cameraAngleMsgToServer(int angle) {
	try {
	    sendToServer("Camera," + angle);
	    System.out.println("Sent the camera angle: " + angle + " to server.");
	} catch (IOException e) {

	    e.printStackTrace();
	}

    }

    // Send a message to server to open the footage.
    public void cameraFootageMsgToServer(String footage) {
	try {
	    sendToServer(footage);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /* ---------------------- HANDLE MESSAGES FROM SERVER ----------------------- */

    @Override

    protected void handleMessageFromServer(Object msg) {

	String message = (String) msg;
	String[] parts = message.split(":");

	if (parts.length >= 2) {
	    String device = (parts[0]);
	    String data = parts[1];

	    // *** Handling Received Data for Smart Thermostat From Server *** //

	    if (device.equals("Thermostat")) {
		// Light is OFF
		if (data.equals("false")) {
		    Platform.runLater(() -> controller.setAlertMessageThermostat("Thermostat: OFF"));
		    Platform.runLater(() -> controller.setThermoVisible(false));
		}
		// Light is ON
		else if (data.equals("true")) {
		    Platform.runLater(() -> controller.setAlertMessageThermostat("Thermostat: ON"));
		    Platform.runLater(() -> controller.setThermoVisible(true));
		}
		// Light is not in range of 15 to 26 degrees
		else if (data.contains("Please")) {
		    Platform.runLater(() -> controller.setAlertMessageThermostat(data));
		}
		// Temperature data is sent from server. Example: 25
		else {

		    Platform.runLater(() -> controller.setTextTemperature(data));
		    Platform.runLater(() -> controller.setAlertMessageThermostat(""));
		    System.out.println("Temperature Received From Server: " + data);
		}

	    }

	    // *** Handling Received Data for Smart Light From Server *** //

	    if (device.equals("Light")) {
		if (data.contains("0x")) {
		    Platform.runLater(() -> controller.displayColor(data));

		}

		else if (data.contains("false")) {
		    Platform.runLater(() -> controller.setNotificationLight("Light: OFF"));
		    Platform.runLater(() -> controller.setLightVisible(false));
		}

		else if (data.contains("true")) {
		    Platform.runLater(() -> controller.setNotificationLight("Light: ON"));
		    Platform.runLater(() -> controller.setLightVisible(true));
		}

		else {

		    Platform.runLater(() -> controller.setTextLightBrightness(data));
		    Platform.runLater(() -> controller.setNotificationLight(""));
		}
	    }

	    // *** Handling Received Data for Smart Lock From Server *** //

	    if (device.equals("Lock")) {

		if (data.equals("true")) {
		    Platform.runLater(() -> controller.setLockHistoryArea("Door has been LOCKED by User"));

		}

		else if (data.equals("false")) {
		    Platform.runLater(() -> controller.setLockHistoryArea("Door has been UNLOCKED by User"));

		}

		else if (data.contains("New password")) {
		    Platform.runLater(() -> controller.setLockHistoryArea("Password has been set"));
		}

		else if (data.contains("Intruder alert")) {
		    Platform.runLater(() -> controller.setLockHistoryArea(data));
		}
	    }

	    // *** Handling Received Data for Smart Water System From Server *** //

	    if (device.equals("Water")) {
		if (data.contains("Completed")) {
		    Platform.runLater(() -> controller.displayWaterHistory(""));
		    Platform.runLater(() -> controller.displayWaterHistory(data));
		    Platform.runLater(() -> controller.setWaterVisible(false));

		}

		else if (data.contains("New water")) {
		    Platform.runLater(() -> controller.displayWaterHistory(""));
		    Platform.runLater(() -> controller.displayWaterHistory(data));

		}

	    }

	    // Handling Received Data for Smart Security Camera From Server
	    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	    if (device.equals("cameraAngle")) {

		int newValueAngle = Integer.parseInt(data);
		System.out.println("Angle: " + newValueAngle);

		// Introduce a 1-second delay before executing the code block
		executorService.schedule(() -> {
		    Platform.runLater(() -> controller.shouldUpdateSlider(true));
		    Platform.runLater(() -> controller.setCameraAngle(newValueAngle));
		    Platform.runLater(() -> controller.shouldUpdateSlider(false));
		}, 1, TimeUnit.SECONDS);

	    }

	    if (device.equals("cameraFootage")) {
		Platform.runLater(() -> controller.showFootage());

	    }

	}
    }
}