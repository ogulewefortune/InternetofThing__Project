/* ------------------------------------------------------------------------------------
 * GUI.java
 * 
 * Copyright (c) 2023 Venos Tech. All rights reserved
 * 
 * Related Documents: 
 *    Specification Document 
 *    Design Document
 * 
 * File created by Said Hassan
 * 
 * Associated file: 
 * ------------------------------------------------------------------------------------
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {

	FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomePage.fxml"));
	Parent root = loader.load();

	GUIController controller = loader.getController();
	loader.setController(controller);
	SmartHomeClient client = new SmartHomeClient("10.100.89.103", 2090, controller);
	client.connectToServer();
	controller.setClient(client);

	// attach scene graph to scene
	Scene scene = new Scene(root);

	// show title on window
	stage.setTitle("Smart Home Automation System");

	// attach scene to stage
	stage.setScene(scene);

	// display stage
	stage.show();

    }

    public static void main(String[] args) {
	// launch contact app object and start method
	launch(args);

    }

}
