/* ------------------------------------------------------------------------------------
 * IoTServer.java
 * 
 * Copyright (c) 2023 Venos Tech. All rights reserved
 * 
 * Related Documents: 
 *    Specification Document 
 *    Design Document
 * 
 * File created by Duong Chan Hung on 11/08/2023
 * 
 * Associated Files: IoTController.java (one to one)
 * ------------------------------------------------------------------------------------
 */

/* Strings used to compare with the message received from the clients:
 * 1. thermoData, thermoIncrease, thermoDecrease, thermoON, thermoOFF.
 * 2. lightData, lightBrightnessIncrease, lightBrightnessDecrease, hex - decimal value.
 * 3. lockData, lockON, lockOFF, lock
*/

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class IoTServer extends AbstractServer {

    // A reference to the IoTController class
    private IoTController serverController;

    private List<ConnectionToClient> client;

    public IoTServer(int port) {
	super(port);
	this.client = new ArrayList<ConnectionToClient>();
	this.serverController = new IoTController();

    }
    // --- FUNCTION TO DETERMINE THE DELAY OF TIME ENTERED BY USER ---

    public long calculateDelay(LocalTime targetTime) {
	// Get the current time
	LocalDateTime now = LocalDateTime.now();
	LocalDateTime targetDateTime = now.with(targetTime);

	// If targetDateTime has already passed, add a day's duration
	if (targetDateTime.isBefore(now)) {
	    targetDateTime = targetDateTime.plusDays(1);
	}
	// Compute the duration between current time with the target time
	Duration duration = Duration.between(now, targetDateTime);
	return duration.toMillis();
    }

    // --- CALCULATE DELAY FUNCTION ENDS HERE ---

    // --- TIMER FUNCTION TO PERFORM A GIVEN TASK ---

    public void startTimer(LocalTime targetTime) {
	// Calculate the duration
	long delay = calculateDelay(targetTime);

	// Create a timer
	Timer timer = new Timer();
	// Create a task using the computed duration
	TimerTask task = new TimerTask() {
	    @Override
	    public void run() {
		System.out.println("\nTimer task executed at " + LocalTime.now());
		// Send the alert message back to all clients
		sendToAllClients("Water:" + serverController.getDeviceAlertMessage(3));
	    }
	};

	timer.schedule(task, delay);
    }

    // --- TIMER FUNCTION ENDS HERE ---

    // --- HANDLE MESSAGE FROM CLIENT ---

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
	// TODO Auto-generated method stub
	String receivedMsg = (String) msg;
	String updateTempStr, updateThermoStatusStr, updateBrightnessStr, updateLightStatusStr, updateLightColorStr,
		updateLockStatusStr, updateLockPass, updateWaterLimit, updateWaterTimer, updateCameraAngle,
		cameraFootage;

	// Displays the messages from all clients
	System.out.println("\nRequest received from client: " + client + "\nMessage content: " + receivedMsg);

	// --- PERFORM THERMOSTAT USE CASES BASED ON THE RECEIVED MESSAGE ---

	// 1. ONLY ALLOW CLIENT TO INCREASE TEMPERATURE IF THE CURRENT BRIGHTNESS IS
	// LESS THAN 35
	// 2. ONLY ALLOW CLIENT TO DECREASE TEMPERATURE IF THE CURRENT BRIGHTNESS IS
	// GREATER THAN 15
	if (("thermoIncrease".equals(receivedMsg) && serverController.getDeviceStatus(0) == true
		&& serverController.getUpdateTemp() < 35)
		|| ("thermoDecrease".equals(receivedMsg) && serverController.getDeviceStatus(0) == true
			&& (serverController.getUpdateTemp() > 15))) {
	    // ACTIONS TO INCREASE TEMPERATURE
	    // Call the updateTemperature in IoTController
	    serverController.updateTemperature(receivedMsg, 1);
	    // Convert Integer to String
	    updateTempStr = serverController.getUpdateTemp().toString();

	    try {
		// If the temperature below 15 or above 26 => send back alert message
		if (serverController.getUpdateTemp() <= 17 || serverController.getUpdateTemp() >= 30) {
		    // SEND BACK THE UPDATED TEMPERATURE
		    sendToAllClients("Thermostat:" + updateTempStr);
		    sendToAllClients("Thermostat:" + serverController.getDeviceAlertMessage(0));
		} else
		    sendToAllClients("Thermostat:" + updateTempStr);
		System.out.println("Thermo updated temperature: " + updateTempStr);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

	// If the request is to turn ON the thermostat
	if ("thermoON".equals(receivedMsg)) {
	    // ACTIONS TO TURN ON THERMOSTAT
	    // Call the setDeviceStatus in IoTController, pass the name of
	    // device(thermostat) and true(ON)
	    serverController.setDeviceStatus("thermo", true);
	    try {
		// SEND BACK THE LIGHT STATUS
		// Convert Boolean value to String
		updateThermoStatusStr = serverController.getDeviceStatus(0).toString();
		// Send the updated status to all clients
		sendToAllClients("Thermostat:" + updateThermoStatusStr);
		// Display the updated status
		System.out.println("Thermo status: " + serverController.getDeviceStatus(0));
	    } catch (Exception e) {

		e.printStackTrace();
	    }
	}

	// If the request is to turn OFF the thermostat
	if ("thermoOFF".equals(receivedMsg)) {
	    // ACTIONS TO TURNING OFF THERMOSTAT
	    // Call the setDeviceStatus in IoTController, pass the name of
	    // device(thermostat) and true(OFF)
	    serverController.setDeviceStatus("thermo", false);
	    try {
		// SEND BACK THE LIGHT STATUS
		// Convert Boolean value to String
		updateThermoStatusStr = serverController.getDeviceStatus(0).toString();
		// Send the updated status to all clients
		sendToAllClients("Thermostat:" + updateThermoStatusStr);
		// Display the updated status
		System.out.println("Thermo status: " + serverController.getDeviceStatus(0));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	// If the clients press on the thermostat menu
	if ("thermoData".equals(receivedMsg)) {
	    try {
		// RETURN DATA FROM THE SERVER TO CLIENTS
		updateThermoStatusStr = serverController.getDeviceStatus(0).toString();
		updateTempStr = serverController.getUpdateTemp().toString();
		// Send to all clients the thermostat data when they enter the thermostat menu
		sendToAllClients("Thermostat:" + updateTempStr);
		sendToAllClients("Thermostat:" + updateThermoStatusStr);
		// Display the thermostat data on server terminal
		System.out.println("Thermostat current temperature: " + updateTempStr);
		System.out.println("Thermostat current status: " + updateThermoStatusStr);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	// --- END ---

	// --- PERFORM LIGHT USE CASES BASED ON THE RECEIVED MESSAGE ---

	// 1. ONLY ALLOW CLIENT TO INCREASE BRIGHTNESS IF THE CURRENT BRIGHTNESS IS LESS
	// THAN 100
	// 2. ONLY ALLOW CLIENT TO DECREASE BRIGHTNESS IF THE CURRENT BRIGHTNESS IS
	// GREATER THAN 0
	if (("lightBrightnessIncrease".equals(receivedMsg) && serverController.getDeviceStatus(1) == true
		&& serverController.getUpdateBrightness() < 100)
		|| ("lightBrightnessDecrease".equals(receivedMsg) && serverController.getDeviceStatus(1) == true
			&& serverController.getUpdateBrightness() > 0)) {
	    // ACTIONS TO SET THE LIGHT BRIGHTNESS
	    // Call the updateBrightness in IoTController class
	    serverController.updateBrightness(receivedMsg, 10);

	    try {
		// SEND BACK THE UPDATED BRIGHTNESS
		// Convert Integer to String
		updateBrightnessStr = serverController.getUpdateBrightness().toString();
		// Send the updated brightness value to all connected clients
		sendToAllClients("Light:" + updateBrightnessStr);
		// Display the updated value on the server terminal
		System.out.println("Light updated brightness: " + serverController.getUpdateBrightness());

	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

	// IF THE MESSAGE IS THE VALUE OF COLOR
	if (receivedMsg.contains("0x")) {
	    // ACTIONS TO CHANGE LIGHT COLOR
	    // Call the setLightColor in the IoTController
	    serverController.setLightColor(receivedMsg);
	    try {
		updateLightColorStr = serverController.getLightColor();
		// Send the updated color value to all connected clients
		sendToAllClients("Light:" + updateLightColorStr);
		// Display the updated value on the server terminal
		System.out.println("Light updated color: " + serverController.getLightColor());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	if ("lightON".equals(receivedMsg)) {
	    // ACTIONS FOR TURNING ON LIGHT
	    // Call the setDeviceStatus in IoTController, pass the name of device(light) and
	    // true(ON)
	    serverController.setDeviceStatus("light", true);
	    try {
		// SEND BACK THE LIGHT STATUS
		// Convert Boolean value to String
		updateLightStatusStr = serverController.getDeviceStatus(1).toString();
		// Send the updated status value to all connected clients
		sendToAllClients("Light:" + updateLightStatusStr);
		// Display the updated value on the server terminal
		System.out.println("Light updated status: " + serverController.getDeviceStatus(1));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	if ("lightOFF".equals(receivedMsg)) {
	    // ACTIONS FOR TURNING OFF LIGHT
	    // Call the setDeviceStatus in IoTController, pass the name of device(light) and
	    // false(OFF)
	    serverController.setDeviceStatus("light", false);
	    try {
		// SEND BACK THE LIGHT STATUS
		// Convert Boolean value to String
		updateLightStatusStr = serverController.getDeviceStatus(1).toString();
		// Send the updated status value to all connected clients
		sendToAllClients("Light:" + updateLightStatusStr);
		// Display the updated value on the server terminal
		System.out.println("Light updated status: " + serverController.getDeviceStatus(1));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	// If the clients press on the light menu
	if ("lightData".equals(receivedMsg)) {
	    try {
		// RETURN DATA FROM THE SERVER TO CLIENTS
		updateBrightnessStr = serverController.getUpdateBrightness().toString();
		updateLightStatusStr = serverController.getDeviceStatus(1).toString();
		updateLightColorStr = serverController.getLightColor();
		// Send to all clients the light data when they enter the light menu
		sendToAllClients("Light:" + updateLightStatusStr);
		sendToAllClients("Light:" + updateLightColorStr);
		sendToAllClients("Light:" + updateBrightnessStr);
		// Display the sending data
		System.out.println("Light current status: " + updateLightStatusStr);
		System.out.println("Light current color: " + updateLightColorStr);
		System.out.println("Light current brightness: " + updateBrightnessStr);

	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	// --- END ---

	// --- PERFORM LOCK USE CASES BASED ON THE RECEIVED MESSAGE ---

	// If the clients press on the lock menu
	if (receivedMsg.startsWith("lockData")) {
	    // RETURN DATA OF THE LOCK TO CLIENTS
	    // Convert Boolean value to String
	    updateLockStatusStr = serverController.getDeviceStatus(2).toString();
	    updateLockPass = serverController.getLockPassword();

	    try {
		// Send to all clients the lock data when they enter the lock menu
		sendToAllClients(updateLockStatusStr);
		sendToAllClients(updateLockPass);
		// Display the lock data on server terminal
		System.out.println("Lock current status: " + updateLockStatusStr);
		System.out.println("Lock current password: " + updateLockPass);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}

	// IF THE MESSAGE CONTAINS A COMMAND AND A DATA
	if (receivedMsg.startsWith("lockpass")) {
	    // Split the received message into 2 parts
	    String[] part = receivedMsg.split(",");
	    String command = (part[0]);
	    String data = part[1];
	    try {
		// SEND BACK THE LOCK STATUS
		// Call the setLockPassword in IoTController to set new password
		serverController.setLockPassword(data);
		// Send to all clients a notification that a new password has been set
		sendToAllClients("Lock:New password");
		// Display the updated password on server terminal
		System.out.println("Lock updated password: " + serverController.getLockPassword());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	if (receivedMsg.startsWith("lockON")) {
	    // Call the setDeviceStatus in IoTController, pass the name of device(lock) and
	    // true(ON)
	    serverController.setDeviceStatus("lock", true);
	    try {
		// SEND BACK THE LOCK STATUS
		// Convert Boolean value to String
		updateLockStatusStr = serverController.getDeviceStatus(2).toString();
		// Send the updated status to all clients
		sendToAllClients("Lock:" + updateLockStatusStr);
		// Display the updated status on server terminal
		System.out.println("Lock updated status: Locked");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	// IF THE MESSAGE CONTAINS A COMMAND WITH A DATA
	if (receivedMsg.startsWith("lockOFF")) {
	    String[] part = receivedMsg.split(",");
	    String command = (part[0]);
	    String data = part[1];
	    // Check if the entered password matches with the password on the server
	    if (data.equals(serverController.getLockPassword())) {
		// Call the setDeviceStatus in IoTController, pass the name of device(lock) and
		// false(OFF)
		serverController.setDeviceStatus("lock", false);
		try {
		    // SEND BACK THE LOCK STATUS
		    // Convert Boolean value to String
		    updateLockStatusStr = serverController.getDeviceStatus(2).toString();
		    // Send the updated status to all clients
		    sendToAllClients("Lock:" + updateLockStatusStr);
		    // Display the updated status on server terminal
		    System.out.println("Lock updated status: Unlocked");
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    } else {
		sendToAllClients("Lock:" + serverController.getDeviceAlertMessage(2));
	    }
	}

	// --- END ---

	// --- PERFORM WATER SYSTEM USE CASES BASED ON THE RECEIVED MESSAGE ---

	if (receivedMsg.startsWith("Water")) {
	    String[] part = receivedMsg.split(",");
	    String data = part[1];
	    // Call the setWaterTimer in IoTController to set water schedule time
	    serverController.setWaterTimer(data);
	    // Start the timer using the time sent from client
	    startTimer(serverController.getWaterTimer());

	}

	if (receivedMsg.startsWith("waterLimit")) {
	    String[] part = receivedMsg.split(",");
	    String data = part[1];
	    // Convert String value to Integer
	    Integer limit = Integer.parseInt(data);
	    // Call the setWaterLimit in IoTController to set water limit
	    serverController.setWaterLimit(limit);
	    // Convert Integer value to String
	    updateWaterLimit = serverController.getWaterLimit().toString();

	    try {
		// Send back a notification to all clients to indicate water limit has been set
		sendToAllClients("Water:New water limit has been set " + updateWaterLimit + " ml");
		// Display updated water limit
		System.out.println("Water updated limit: " + updateWaterLimit);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}

	// --- END ---

	// --- PERFORM CAMERA USE CASES BASED ON THE RECEIVED MESSAGE ---

	// Send back camera data when user presses on camera menu
	if (receivedMsg.equals("cameraData")) {
	    // Convert Integer to String
	    updateCameraAngle = serverController.getCameraAngle().toString();
	    try {
		// Send to all clients the updated camera angle
		sendToAllClients("cameraAngle:" + updateCameraAngle);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	if (receivedMsg.startsWith("Camera")) {
	    String[] part = receivedMsg.split(",");
	    String data = part[1];
	    Integer angle = Integer.parseInt(data);
	    // Call the setCameraAngle in IoTServer to update the camera angle
	    serverController.setCameraAngle(angle);
	    updateCameraAngle = serverController.getCameraAngle().toString();
	    try {
		// Send to all clients the updated camera angle
		sendToAllClients("cameraAngle:" + updateCameraAngle);
		// Display the updated camera angle on server terminal
		System.out.println("Camera updated angle: " + updateCameraAngle);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	if (receivedMsg.startsWith("Footage")) {
	    try {
		cameraFootage = serverController.getVideoResource();
		// Send back the footage to all clients

		client.sendToClient("cameraFootage:" + cameraFootage);
		// Display a message indicates that the video resource has been sent
		System.out.println("Sent back the video footage");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	// --- END ---

	// --- HANDLE USER INFORMATION ---

	// If the message begins with "User"
	if (receivedMsg.startsWith("User")) {
	    String[] parts = receivedMsg.split(",");
	    if (parts.length == 5) {
		String type = parts[0].trim();
		String fName = parts[1].trim();
		String lName = parts[2].trim();
		String email = parts[3].trim();
		String password = parts[4].trim();
		// Call the setUserInformation in IoTServer to create a new user object
		String userInfo = serverController.setUserInformation(fName, lName, email, password);
		// Display the newly added user
		System.out.println("User Information: " + userInfo);
	    }

	}

	// If the message begins with "Admin"
	if (receivedMsg.startsWith("Admin")) {
	    String[] parts = receivedMsg.split(",");
	    if (parts.length == 5) {
		String type = parts[0].trim();
		String fName = parts[1].trim();
		String lName = parts[2].trim();
		String email = parts[3].trim();
		String password = parts[4].trim();
		// Call the setAdminInformation in IoTServer to create a new admin object
		String userInfo = serverController.setAdminInformation(fName, lName, email, password);
		// Display the newly added admin
		System.out.println("Admin Information: " + userInfo);
	    }

	}

	// --- END ---

    }

    // --- HANDLE MESSAGE FROM CLIENT FUNCTION ENDS HERE ---

    // --- FUNCTIONS TO DISPLAY CONNECTED CLIENTS AND DISCONNECTED CLIENTS ---

    @Override
    protected void clientConnected(ConnectionToClient client) {
	// DISPLAY THE CONNECTED CLIENT

	System.out.println("\nClient connected: " + client.getInetAddress());

    }

    @Override
    synchronized protected void clientDisconnected(ConnectionToClient client) {
	// DISPLAY THE DISCONNECTED CLIENT
	System.out.println("\nClient disconnected: " + client);
    }

    // --- FUNCTIONS END HERE ---

    // --- MAIN FUNCTION TO RUN THE SERVER ---

    public static void main(String[] args) {
	int port = 2090;
	IoTServer server = new IoTServer(port);
	try {
	    server.listen();
	    System.out.println("Server is listening");
	} catch (Exception e) {
	    System.out.println("ERROR - Could not listen for clients! ");
	}

    }

    // -- END ---

}
