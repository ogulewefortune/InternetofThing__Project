/* ------------------------------------------------------------------------------------
 * GUIController.java

 *
 * Copyright (c) 2023 Venos Tech. All rights reserved
 *  Description:
 * This Java code is part of a software application developed by Fortune Ogulewe, Said Hassan
 * for Venos Tech. It includes functionality for IOT device system
 *
 * Related Documents:
 *    Specification Document
 *    Design Document
 *
 * Disclaimer:
 * This code is provided as-is, without any warranty or support. Use it at your
 * own risk. The author and Venos Tech shall not be liable for any damages or
 * issues arising from the use of this code.
 *
 *
 * File created by Said Hassan on 11/08/2023
 *
 * * Associated file:
 * ------------------
 *
 * Contributor: Fortune

 * ------------------------------------------------------------------------------
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUIController {

    // Client and GUI Controller Association
    private SmartHomeClient client;
    private int count;
    private String startTime;
    private String endTime;

    // Setter and Getters for client
    public void setClient(SmartHomeClient client) {
	this.client = client;
    }

    public boolean shouldUpdateSlider(boolean b) {
	return b;
    }

    public void setStartTime(String stime) {
	this.startTime = stime;

    }

    public void setEndTime(String etime) {
	this.endTime = etime;
    }

    public String getStartTime() {
	return startTime;
    }

    public String getEndTime() {
	return endTime;
    }

    public void initialize() {
	try {
	    // Initialize objects for Admin Room
	    setupListviewDevicelistview();

	    // Slider function used in SDCamera.fxml
	    Set<Integer> allowedAngles = new HashSet<>(Arrays.asList(0, 45, 90, 135, 180, 225, 270, 315, 360));

	    if (sliderAngleCamera != null) {
		sliderAngleCamera.valueProperty().addListener((observable, oldValue, newValue) -> {
		    if (shouldUpdateSlider(true)) {
			int currentValue = newValue.intValue();

			if (allowedAngles.contains(currentValue)) {
			    // Delay sending the value by 1 second
			    client.cameraAngleMsgToServer(currentValue);
			}
		    } //
		});
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /* --------------------------- WELCOME PAGE----------------------- */

    @FXML
    private Pane welcomePane;

    // Welcome Page Button to Switch to Next Page.
    @FXML
    void conitinueButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("userAdminPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene welcomePageScene = new Scene(root);
	    Stage stage = (Stage) welcomePane.getScene().getWindow();
	    stage.setScene(welcomePageScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }//

    /*----------------------- USER/ADMIN PAGE--------------------------- */

    @FXML
    private GridPane userAdminPane;

    // Admin Button to switch to Admin Login
    @FXML
    void adminButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Adminlogin.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) userAdminPane.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // User Button to switch to User Login
    @FXML
    void userButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("userLoginPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) userAdminPane.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*--------------------- USER LOGIN PAGE --------------------*/

    @FXML
    private TextField emailTextField;

    @FXML
    private GridPane loginPane;

    @FXML
    private PasswordField passwordTextField;

    // Login Button to Switch to Main Menu
    @FXML
    void loginButtonPressed(ActionEvent event) {
	try {

	    String email = emailTextField.getText();
	    String password = passwordTextField.getText();

	    Database db = new Database();

	    boolean success = db.authenticateUser(email, password);

	    if (success) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		Scene mainMenuScene = new Scene(root);
		Stage stage = (Stage) loginPane.getScene().getWindow();
		stage.setScene(mainMenuScene);

	    } else {
		System.out.println("User has not signed up.");

		// Display border red if incorrect.
		passwordTextField.setStyle("-fx-border-color: red;");

		// prompt text
		passwordTextField.setPromptText("Incorrect Password");
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Sign Up Button to Switch to Sign Up Page
    @FXML
    void signupButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserSignUp.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) loginPane.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Back Button to back to User/Admin Page on the User Login Page
    @FXML
    void backButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("userAdminPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) loginPane.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*--------------------- USER SIGNUP PAGE --------------------*/

    @FXML
    private TextField userEmailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField userPasswordTextField;

    @FXML
    private TextField reEnterPasswordTetxtField;

    @FXML
    private GridPane signUpPane;

    // Sign up Button pressed to Switch Scene
    @FXML
    void signUpButtonPressed(ActionEvent event) {

	// Enter User Sign Up text fields and save it into the DataBase.
	try {

	    String firstName = firstNameTextField.getText();
	    String lastName = lastNameTextField.getText();
	    String email = userEmailTextField.getText();
	    String password = userPasswordTextField.getText();

	    Database db = new Database();

	    boolean success = db.signUpUser(firstName, lastName, email, password);

	    if (success) {
		System.out.println("User Sign Up Successful");

		// Send User Sign Up To Server
		client.userSignUpToServer("User," + firstName + "," + lastName + "," + email + "," + password);

	    } else {

		System.out.println("Sign Up failed. User might exist");
	    }

	    FXMLLoader loader = new FXMLLoader(getClass().getResource("userLoginPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene SignupScene = new Scene(root);
	    Stage stage = (Stage) signUpPane.getScene().getWindow();
	    stage.setScene(SignupScene);

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    // Login Button pressed to Switch Scene
    @FXML
    void userLoginButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("userLoginPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene SignupScene = new Scene(root);
	    Stage stage = (Stage) signUpPane.getScene().getWindow();
	    stage.setScene(SignupScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*------------------- ADMIN lOGIN PAGE ------------------*/

    @FXML
    private TextField AdminEmailTextField;

    @FXML
    private GridPane AdminloginGridpane;

    @FXML
    private PasswordField AdminpasswordTextField;

    // Login Button to go to Admin Room
    @FXML
    void Loginbuttonpressed(ActionEvent event) {
	try {
	    String email = AdminEmailTextField.getText();
	    String password = AdminpasswordTextField.getText();

	    Database db = new Database();

	    boolean success = db.authenticateAdmin(email, password);

	    if (success) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Adminrooom.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		Scene AdminloginScene = new Scene(root);
		Stage stage = (Stage) AdminloginGridpane.getScene().getWindow();
		stage.setScene(AdminloginScene);
	    } else {
		System.out.println("Admin has not signed up.");

		// Display border red if incorrect.
		AdminpasswordTextField.setStyle("-fx-border-color: red;");

		// prompt text
		AdminpasswordTextField.setPromptText("Incorrect Password");
	    }

	} catch (IOException e) {

	    e.printStackTrace();
	}
    }

    @FXML
    void Registerbuttonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRegister.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene AdminloginScene = new Scene(root);
	    Stage stage = (Stage) AdminloginGridpane.getScene().getWindow();
	    stage.setScene(AdminloginScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @FXML
    void backbuttonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("userAdminPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene AdminloginScene = new Scene(root);
	    Stage stage = (Stage) AdminloginGridpane.getScene().getWindow();
	    stage.setScene(AdminloginScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*------------------- ADMIN REGISTER PAGE -------------------*/
    @FXML
    private TextField adminFirstNameTextField;

    @FXML
    private TextField adminLastNameTextField;

    @FXML
    private TextField adminEmailTextField;

    @FXML
    private TextField adminPasswordTextField;

    @FXML
    private GridPane adminRegisterPane;

    @FXML
    private TextField adminRePasswordTextField;

    @FXML
    void adminRegisterButtonBressed(ActionEvent event) {

	// Enter Admin Sign Up text fields and save it into the DataBase
	try {
	    String firstName = adminFirstNameTextField.getText();
	    String lastName = adminLastNameTextField.getText();
	    String email = adminEmailTextField.getText();
	    String password = adminPasswordTextField.getText();

	    Database db = new Database();

	    boolean success = db.signUpAdmin(firstName, lastName, email, password);

	    if (success) {
		System.out.println("Admin Sign up Successful");
		// Send User Sign Up To Server
		client.adminSignUpToServer("Admin," + firstName + "," + lastName + "," + email + "," + password);

	    } else {

		System.out.println("Sign Up failed. User might exist");
	    }
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Adminlogin.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene AdminregisterScene = new Scene(root);
	    Stage stage = (Stage) adminRegisterPane.getScene().getWindow();
	    stage.setScene(AdminregisterScene);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @FXML
    void adminLoginButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Adminlogin.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene AdminregisterScene = new Scene(root);
	    Stage stage = (Stage) adminRegisterPane.getScene().getWindow();
	    stage.setScene(AdminregisterScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*---------------- MAIN MENU ---------------------*/

    @FXML
    private Button ThermoButton;

    @FXML
    private Button cameraButton;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private Button lightButton;

    @FXML
    private Button lockButton;

    @FXML
    private Button waterButton;

    @FXML
    private Pane mainMenupane;

    private double originalOpacity1 = 1.0;
    private double originalSize1 = 1.0;
    private double originalOpacity2 = 1.0;
    private double originalSize2 = 1.0;
    private double originalOpacity3 = 1.0;
    private double originalSize3 = 1.0;

    @FXML
    void logoutMainMenuPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("userAdminpage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene FirstpageScene = new Scene(root);
	    Stage stage = (Stage) waterButton.getScene().getWindow();
	    stage.setScene(FirstpageScene);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @FXML
    void mainMenuDropDown(ActionEvent event) {

    }

    // Smart Camera Button to go to Camera Page
    @FXML
    void smartCameraButtonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("SDcamera.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene cameraScene = new Scene(root);
	    Stage stage = (Stage) mainMenupane.getScene().getWindow();
	    stage.setScene(cameraScene);

	    // SEND MSG TO SERVER TO RETRIEVE CAMERA DATA
	    client.cameraMainMenuToServer();
	} catch (IOException e) {
	    e.printStackTrace();

	}

    }

    @FXML
    void image1entered(MouseEvent event) {
	ImageView imageView = (ImageView) event.getSource();
	originalOpacity1 = imageView.getOpacity();
	originalSize1 = imageView.getScaleX();
	adjustImageProperties(imageView, 1.0, originalSize1 + 0.05);
    }

    @FXML
    void image1exited(MouseEvent event) {
	ImageView imageView = (ImageView) event.getSource();
	adjustImageProperties(imageView, 0.6, originalSize1);
    }

    @FXML
    void image2entered(MouseEvent event) {
	ImageView imageView = (ImageView) event.getSource();
	originalOpacity2 = imageView.getOpacity();
	originalSize2 = imageView.getScaleX();
	adjustImageProperties(imageView, 1.0, originalSize2 + 0.05);
    }

    @FXML
    void image2exited(MouseEvent event) {
	ImageView imageView = (ImageView) event.getSource();
	adjustImageProperties(imageView, 0.6, originalSize2);
    }

    @FXML
    void image3entered(MouseEvent event) {
	ImageView imageView = (ImageView) event.getSource();
	originalOpacity3 = imageView.getOpacity();
	originalSize3 = imageView.getScaleX();
	adjustImageProperties(imageView, 1.0, originalSize3 + 0.05);
    }

    @FXML
    void image3exited(MouseEvent event) {
	ImageView imageView = (ImageView) event.getSource();
	adjustImageProperties(imageView, 0.6, originalSize3);
    }

    private void adjustImageProperties(ImageView imageView, double opacity, double size) {
	imageView.setOpacity(opacity);
	imageView.setScaleX(size);
	imageView.setScaleY(size);
    }

    // Smart Light Button to go Light Page
    @FXML
    void smartLightButtonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("SDSmartLight.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene lightScene = new Scene(root);
	    Stage stage = (Stage) mainMenupane.getScene().getWindow();
	    stage.setScene(lightScene);

	    // SEND MSG TO SERVER TO RETRIEVE LIGHT DATA
	    client.lightMainMenuToServer();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Smart Lock Button to go Lock Page
    @FXML
    void smartLockButtonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("STLock.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene lockScene = new Scene(root);
	    Stage stage = (Stage) mainMenupane.getScene().getWindow();
	    stage.setScene(lockScene);

	    // SEND MSG TO SERVER TO RETRIEVE LOCK DATA
	    client.lockMainMenuToServer();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // about us
    @FXML
    void userAboutButtonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutUs.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene lockScene = new Scene(root);
	    Stage stage = (Stage) mainMenupane.getScene().getWindow();
	    stage.setScene(lockScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    // Smart Thermostat Button to go Thermostat Page
    @FXML
    void smartThermostatButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("SDThermostat.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene thermostatScene = new Scene(root);
	    Stage stage = (Stage) mainMenupane.getScene().getWindow();
	    stage.setScene(thermostatScene);

	    // SEND MSG TO SERVER TO RETRIEVE THERMOSTAT DATA
	    client.thermostatMainMenuToServer();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Smart Water System Button to go Water Page
    @FXML
    void waterSystemButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("IrrigationSystem.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene waterScene = new Scene(root);
	    Stage stage = (Stage) mainMenupane.getScene().getWindow();
	    stage.setScene(waterScene);

	    // SEND MSG TO SERVER TO RETRIEVE WATER DATA
	    client.waterMainMenuToServer();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Buttons Changing Color when mouse is dragged over it.
    @FXML
    void thermCursorEntered(MouseEvent event) {
	ThermoButton.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, black, purple); "
		+ "-fx-text-fill: white; -fx-border-color: black;");

    }

    @FXML
    void thermCursorExited(MouseEvent event) {
	ThermoButton.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-border-color: black;");

    }

    @FXML
    void waterCursorEntered(MouseEvent event) {
	waterButton.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, black, purple); "
		+ "-fx-text-fill: white; -fx-border-color: black;");

    }

    @FXML
    void waterCursorExited(MouseEvent event) {
	waterButton.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-border-color: black;");

    }

    @FXML
    void cameraCursorEntered(MouseEvent event) {
	cameraButton.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, black, purple); "
		+ "-fx-text-fill: white; -fx-border-color: black;");

    }

    @FXML
    void cameraCursorExited(MouseEvent event) {
	cameraButton.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-border-color: black;");

    }

    @FXML
    void lightCursorEntered(MouseEvent event) {
	lightButton.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, black, purple); "
		+ "-fx-text-fill: white; -fx-border-color: black;");

    }

    @FXML
    void lightCursorExited(MouseEvent event) {
	lightButton.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-border-color: black;");

    }

    @FXML
    void lockCursorEntered(MouseEvent event) {
	lockButton.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, black, purple); "
		+ "-fx-text-fill: white; -fx-border-color: black;");

    }

    @FXML
    void lockCursorExited(MouseEvent event) {
	lockButton.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-border-color: black;");

    }

    /* -------------------- ABOUT US PAGE --------------------- */

    @FXML
    private Pane aboutUsPane;

    @FXML
    void Aboutbackbuttonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) aboutUsPane.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /*---------------- ADMIN ROOM MENU ---------------------*/

    @FXML
    private GridPane Adminmainroompane;

    @FXML
    private ListView<String> Devicelistview;

    @FXML
    private TextField DevicenameTextField;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label useridlabel;

    private static int newDeviceCount = 5;

    private void setupListviewDevicelistview() {
	if (Devicelistview != null) {
	    ObservableList<String> Deviceinfo = FXCollections.observableArrayList(
		    // USER PRESSES ON THE LOGIN BUTTON => SEND A MSG TO SERVER TO REQUEST SMART
		    // DEVICES NAME
		    "Smart Camera", "Smart Thermostat", "Smart Lock", "Smart Light", "Water System"

	    );

	    Devicelistview.setItems(Deviceinfo);
	}
    }

    @FXML
    void AddnewDevicebuttonpressed(ActionEvent event) {
	if (DevicenameTextField != null && Devicelistview != null) {

	    String newDeviceName = DevicenameTextField.getText().trim();

	    if (!newDeviceName.isEmpty()) {

		// add the new device name to teh list

		Devicelistview.getItems().add(newDeviceName);

		// clear the text field for the next input
		DevicenameTextField.clear();

		// increment device count (if needed)

		newDeviceCount++;

		// optionally, you can do something with the count, like displaying it
		System.out.println("Total Devies: " + newDeviceCount);

	    }
	}

    }

    @FXML
    void Logoutbuttonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("userAdminpage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene AdminmainroomScene = new Scene(root);
	    Stage stage = (Stage) Adminmainroompane.getScene().getWindow();
	    stage.setScene(AdminmainroomScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @FXML
    void DeleteDevicebuttonpressed(ActionEvent event) {
	if (Devicelistview != null) {

	    // get the selected item(s) in the list
	    ObservableList<String> selectedDevices;
	    selectedDevices = Devicelistview.getSelectionModel().getSelectedItems();

	    // Remove the selected item(s) from the list
	    if (selectedDevices != null && !selectedDevices.isEmpty()) {
		Devicelistview.getItems().removeAll(selectedDevices);

		// Optionally, you can perform additional actions after deletion
		// For example, update counts or perform other operations
		System.out.println("Deleted " + selectedDevices.size() + " device(s).");
	    }
	}

    }

    @FXML
    void returnhomegifclicked(MouseEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene AdminmainroomScene = new Scene(root);
	    Stage stage = (Stage) Adminmainroompane.getScene().getWindow();
	    stage.setScene(AdminmainroomScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @FXML
    void searchbuttonpressed(ActionEvent event) {
	if (Devicelistview != null && searchTextField != null) {
	    String searchQuery = searchTextField.getText().trim();

	    ObservableList<String> originalDeviceList = Devicelistview.getItems();
	    boolean deviceFound = false;

	    // Check if the device (regardless of case) is in the list
	    for (String device : originalDeviceList) {
		if (device.equalsIgnoreCase(searchQuery)) {
		    deviceFound = true;
		    // Highlight the found device in the list view
		    Devicelistview.getSelectionModel().select(device);
		    break;
		}
	    }

	    // Change border color and set text based on search result
	    if (deviceFound) {
		// Set border color to green and set text to "Device found."
		searchTextField.setStyle("-fx-border-color: green;");
		searchTextField.clear();
		searchTextField.setPromptText("Device found.");
	    } else {
		// Set border color to red and set text to "Device not found."
		searchTextField.setStyle("-fx-border-color: red;");
		searchTextField.clear();
		searchTextField.setPromptText("Device not found.");
	    }
	}

    }

    @FXML
    void AdminAboutButtonpressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutUs.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene AdminmainroomScene = new Scene(root);
	    Stage stage = (Stage) Adminmainroompane.getScene().getWindow();
	    stage.setScene(AdminmainroomScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /* ---------------- SMART THERMOSTAT PAGE------------------ */

    @FXML
    private TextArea historyMessageBox;

    @FXML
    private Label temperatureLabel;

    // Decrease Button and send msg to server
    @FXML
    void decreaseTemperatureButton(ActionEvent event) throws IOException {
	if (client != null) {
	    // Use method in client class to send the data
	    client.temperatureDecrementToServer();
	} else {
	    System.err.println("Error - SmartHomeClient not set in the controller");
	}
    }

    @FXML
    void increaseTemperatureButton(ActionEvent event) {
	if (client != null) {
	    // Use method in client class to send the data
	    client.temperatureIncrementToServer();

	} else {
	    System.err.println("Error - SmartHomeClient not set in the controller");
	}
    }

    // Turn OFF button pressed then call method in client
    @FXML
    void offButtonPressed(ActionEvent event) {
	// Set Text OFF
//	temperatureLabel.setVisible(false);
	client.thermostatOffToServer();
    }

    // Turn ON button pressed then call method in client
    @FXML
    void onButtonPressed(ActionEvent event) {
//	temperatureLabel.setVisible(true);
	client.thermostatOnToServer();

    }

    public void setThermoVisible(Boolean status) {
	try {
	    temperatureLabel.setVisible(status);

	} catch (Exception e) {

	}

    }

    // Return Back to Main Menu by Scene Switching.
    @FXML
    void returnButtonPressed(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) historyMessageBox.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Method to set temperature label after increment/decrement
    public void setTextTemperature(String msg) {
	try {
	    temperatureLabel.setText(msg);
	} catch (Exception e) {
	    System.out.println("Server sent updated data to all client");
	}
    }

    // Set Text of Alert Msg sent from Server
    public void setAlertMessageThermostat(String msg) {
	try {
	    historyMessageBox.setText(msg);
	} catch (Exception e) {
	    System.out.println("Something wong history box");
	}
    }

    /* ---------------------- SMART LIGHT PAGE ------------------------- */

    @FXML
    private Label brightnessLabel;

    @FXML
    private TextArea colorNotification;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private MenuButton fromScheduleTime;

    @FXML
    private MenuButton toScheduleTime;

    @FXML
    private Button increaseBrightnessButton;

    @FXML
    private Button decreaseBrightnessButton;

    @FXML
    private TextArea notiLightTextArea;

    // Shapes in the SDSmartLight Page
    @FXML
    private Polygon fortuneArt1, fortuneArt2, fortuneArt3, fortuneArt4, fortuneArt5, fortuneArt6, fortuneArt7,
	    fortuneArt8, fortuneArt9, fortuneArt10;

    // Increase Brightness and send message to server
    @FXML
    void increaseBrightnessButton(ActionEvent event) {
	client.lightBrightnessIncreaseToServer();
    }

    // Decrease Brightness and send message to server
    @FXML
    void decreaseBrightnessButton(ActionEvent event) {
	client.lightBrightnessDecreaseToServer();

    }

    // Color Picker on Smart Light Page Chosen
    @FXML
    void colorPickerValueChanged(ActionEvent event) {
	String colorVal = String.valueOf(colorPicker.getValue());

	// Create an array of the Polygon shapes
	Polygon[] polygons = { fortuneArt1, fortuneArt2, fortuneArt3, fortuneArt4, fortuneArt5, fortuneArt6,
		fortuneArt7, fortuneArt8, fortuneArt9, fortuneArt10 };

	// Duration for the transition
	Duration duration = Duration.seconds(1.5);

	// Calculate the duration for each polygon
	double durationPerPolygon = duration.toMillis() / polygons.length;
	double currentDuration = 0;

	// Apply FillTransition to each polygon with a delay
	for (Polygon polygon : polygons) {
	    FillTransition ft = new FillTransition(duration, polygon);
	    ft.setFromValue((Color) polygon.getFill());
	    ft.setToValue(colorPicker.getValue());
	    ft.setDelay(Duration.millis(currentDuration));
	    ft.play();
	    currentDuration += durationPerPolygon;
	}
	System.out.println(colorVal);
	client.lightColorChangeToServer(colorVal);
    }

    // OFF Button on Smart Light Page sends Msg to Server
    @FXML
    void offSmartLightButton(ActionEvent event) {

	client.smartLightOffToServer();
    }

    public void setLightVisible(Boolean status) {
	try {
	    brightnessLabel.setVisible(status);
	} catch (Exception e) {

	}
    }

    // ON Button on Smart Light page sends Msg to Server
    @FXML
    void onSmartLightButton(ActionEvent event) {
	client.smartLightOnToServer();
    }

    // Return Button back to Main Menu in the Smart Light Page
    @FXML
    void returnSmartLightButton(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) colorPicker.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void setNotificationLight(String msg) {
	try {
	    notiLightTextArea.setText(msg);
	} catch (Exception e) {

	}
    }

    // Display Color on Polygons
    public void displayColor(String color1) {
	try {
	    Color color = Color.web(color1);
	    colorPicker.setValue(color);

	    fortuneArt1.setFill(color);
	    fortuneArt2.setFill(color);
	    fortuneArt3.setFill(color);
	    fortuneArt4.setFill(color);
	    fortuneArt5.setFill(color);
	    fortuneArt6.setFill(color);
	    fortuneArt7.setFill(color);
	    fortuneArt8.setFill(color);
	    fortuneArt9.setFill(color);
	    fortuneArt10.setFill(color);
	} catch (Exception e) {
	    System.out.println("Server sent updated data to all client");
	}
    }

    @FXML
    void saveScheduleButton(ActionEvent event) {

    }

    // Color Change When Mouse Enters Buttons
    @FXML
    void increaseButtonEntered(MouseEvent event) {
	increaseBrightnessButton
		.setStyle("-fx-text-fill: white; -fx-background-color: black; -fx-border-color: black;");

    }

    @FXML
    void increaseButtonExited(MouseEvent event) {
	increaseBrightnessButton
		.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-border-color: black;");
    }

    @FXML
    void decreaseButtonEntered(MouseEvent event) {
	decreaseBrightnessButton
		.setStyle("-fx-text-fill: white; -fx-background-color: black; -fx-border-color: black;");

    }

    @FXML
    void decreaseButtonExited(MouseEvent event) {
	decreaseBrightnessButton
		.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-border-color: black;");

    }

    public void setTextLightBrightness(String msg) {
	try {
	    brightnessLabel.setText(msg);
	} catch (Exception e) {

	}
    }

    public void setAlertMessagesetTextLightBrightness(String msg) {
	try {
	    colorNotification.setText(msg);
	} catch (Exception e) {

	}

    }

    /* ----------- SMART SECURITY CAMERA PAGE---------------- */

    @FXML
    private TextField fromHourCamera;

    @FXML
    private TextField fromMinuteCamera;

    @FXML
    private TextArea notificationCameraArea;

    @FXML
    private Pane securityCameraPane;

    @FXML
    private Slider sliderAngleCamera;

    @FXML
    private TextField toHourCamera;

    @FXML
    private TextField toMinuteCamera;

    // Setting the character limit for time.
    @FXML
    void fromHourCameraKey(KeyEvent event) {
	int maxLength = 2; // Set the character limit
	if (fromHourCamera.getText().length() >= maxLength) {
	    if (event.getCharacter().isEmpty()) {
		fromHourCamera.setEditable(true); // Allow deletion if Backspace/Delete is pressed
	    } else {
		fromHourCamera.setEditable(false); // Disable further input if limit is reached
	    }
	} else {
	    fromHourCamera.setEditable(true); // Allow input if characters can be added
	}
    }

    // Setting the character limit for time.
    @FXML
    void fromMinuteCameraKey(KeyEvent event) {
	int maxLength = 2; // Set the character limit
	if (fromMinuteCamera.getText().length() >= maxLength) {
	    if (event.getCharacter().isEmpty()) {
		fromMinuteCamera.setEditable(true); // Allow deletion if Backspace/Delete is pressed
	    } else {
		fromMinuteCamera.setEditable(false); // Disable further input if limit is reached
	    }
	} else {
	    fromMinuteCamera.setEditable(true); // Allow input if characters can be added
	}
    }

    // Setting the character limit for time.
    @FXML
    void toHourCameraKey(KeyEvent event) {
	int maxLength = 2; // Set the character limit
	if (toHourCamera.getText().length() >= maxLength) {
	    if (event.getCharacter().isEmpty()) {
		toHourCamera.setEditable(true); // Allow deletion if Backspace/Delete is pressed
	    } else {
		toHourCamera.setEditable(false); // Disable further input if limit is reached
	    }
	} else {
	    toHourCamera.setEditable(true); // Allow input if characters can be added
	}
    }

    // Setting the character limit for time.
    @FXML
    void toMinuteCameraKey(KeyEvent event) {
	int maxLength = 2; // Set the character limit
	if (toMinuteCamera.getText().length() >= maxLength) {
	    if (event.getCharacter().isEmpty()) {
		toMinuteCamera.setEditable(true); // Allow deletion if Backspace/Delete is pressed
	    } else {
		toMinuteCamera.setEditable(false); // Disable further input if limit is reached
	    }
	} else {
	    toMinuteCamera.setEditable(true); // Allow input if characters can be added
	}
    }

    @FXML
    void returnCameraButton(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) securityCameraPane.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @FXML
    void showFootageButtonPressed(ActionEvent event) {

	String fromHour = fromHourCamera.getText();
	String fromMinute = fromMinuteCamera.getText();
	String toHour = toHourCamera.getText();
	String toMinute = toMinuteCamera.getText();

	setStartTime(fromHour + ":" + fromMinute);
	setEndTime(toHour + ":" + toMinute);

	notificationCameraArea.setText("Showing footage from: " + getStartTime() + " to: " + getEndTime());

	client.cameraFootageMsgToServer("Footage");
    }

    public void setCameraAngle(int angle) {
	sliderAngleCamera.setValue(angle);
    }

    /* ---------------------- CameraView PAGE ------------------------- */

    @FXML
    private TextField endCameraViewField;

    @FXML
    private MediaView mediaView;

    @FXML
    private TextField startCameraViewField;

    @FXML
    void backCameraViewButton(ActionEvent event) {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("SDcamera.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) mediaView.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public void showFootage() {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Cameraview.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) securityCameraPane.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	    // Display start time and end time from Camera.
	    startCameraViewField.setText(startTime);
	    endCameraViewField.setText(endTime);

	} catch (IOException e) {
	    e.printStackTrace();
	}

	// Path to the show the video
	String videoFilePath = "src/fortunesVideo.mp4";

	File videoFile = new File(videoFilePath);
	if (!videoFile.exists()) {
	    System.out.println("Video file does not exist: " + videoFilePath);
	    return;
	}

	Media media = new Media(videoFile.toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(media);
	mediaView.setMediaPlayer(mediaPlayer);

	mediaPlayer.setAutoPlay(true);
    }

    /* ---------------------- SMART WATER SYSTEM PAGE ------------------------- */

    @FXML
    private TextArea waterUsageHistoryArea;
    @FXML
    private TextField hourTextField;
    @FXML
    private TextField minuteTextField;
    @FXML
    private TextField waterLimitTextField;
    @FXML
    private ImageView waterMovement;

    // Setting Characters typed to a limit of Two for Hours
    @FXML
    void hourKeyTyped(KeyEvent event) {
	int maxLength = 2; // Set the character limit

	if (hourTextField.getText().length() >= maxLength) {
	    if (event.getCharacter().isEmpty()) {
		hourTextField.setEditable(true); // Allow deletion if Backspace/Delete is pressed
	    } else {
		hourTextField.setEditable(false); // Disable further input if limit is reached
	    }
	} else {
	    hourTextField.setEditable(true); // Allow input if characters can be added
	}
    }

    @FXML
    void minuteKeyTyped(KeyEvent event) {
	int maxLength = 2; // Set the character limit

	if (minuteTextField.getText().length() >= maxLength) {
	    if (event.getCharacter().isEmpty()) {
		minuteTextField.setEditable(true); // Allow deletion if Backspace/Delete is pressed
	    } else {
		minuteTextField.setEditable(false); // Disable further input if limit is reached
	    }
	} else {
	    minuteTextField.setEditable(true); // Allow input if characters can be added
	}
    }

    @FXML
    void waterLimitKeyTyped(KeyEvent event) {
	int maxLength = 5; // Set the character limit

	if (waterLimitTextField.getText().length() >= maxLength) {
	    if (event.getCharacter().isEmpty()) {
		waterLimitTextField.setEditable(true); // Allow deletion if Backspace/Delete is pressed
	    } else {
		waterLimitTextField.setEditable(false); // Disable further input if limit is reached
	    }
	} else {
	    waterLimitTextField.setEditable(true); // Allow input if characters can be added
	}
    }

    // Return to Main Menu Page from Water System Page
    @FXML
    void returnWaterSystemButton(ActionEvent event) {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) waterUsageHistoryArea.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Save Time Duration for Water System and Send to Server
    @FXML
    void saveTimeWaterSystemButton(ActionEvent event) {
	String hour = hourTextField.getText();
	String minute = minuteTextField.getText();

	String time = "Water," + hour + ":" + minute;
	System.out.println("Sent Water Duration time till -  " + hour + ':' + minute);
	waterUsageHistoryArea.setText("Water has been set till: " + hour + ":" + minute);
	setWaterVisible(true);
	client.waterTimeMsgToServer(time);

    }

    // Save Water Limit for Water System and Send to Server
    @FXML
    void saveWaterLimitButton(ActionEvent event) {
	String waterLimit = waterLimitTextField.getText();
	System.out.println("Sent Water Limit of: " + waterLimit + " To Server");
	client.waterLimitMsgToServer("waterLimit," + waterLimit);

    }

    public void displayWaterHistory(String msg) {
	try {
	    waterUsageHistoryArea.setText(msg);
	} catch (Exception e) {

	}

    }

    // EDITED FROM HERE
    private ImageView waterMovement1;

    public void WaterAnimator(ImageView waterMovement) {
	this.waterMovement = waterMovement;
    }

    public void setWaterVisible(boolean b) {
	if (b) {
	    fadeInFromLeft();
	} else {
	    fadeOutToRight();
	}
    }

    private void fadeInFromLeft() {
	waterMovement.setOpacity(0); // Start with zero opacity

	TranslateTransition transition = new TranslateTransition(Duration.seconds(1), waterMovement);
	transition.setFromX(-waterMovement.getBoundsInParent().getWidth()); // Start off-screen left
	transition.setToX(0); // Move to the visible area
	transition.setInterpolator(Interpolator.EASE_BOTH);

	FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), waterMovement);
	fadeTransition.setFromValue(0); // Start with zero opacity
	fadeTransition.setToValue(1); // Fully visible
	fadeTransition.setInterpolator(Interpolator.EASE_BOTH);

	ParallelTransition parallelTransition = new ParallelTransition(transition, fadeTransition);
	parallelTransition.play();
    }

    private void fadeOutToRight() {
	TranslateTransition transition = new TranslateTransition(Duration.seconds(1), waterMovement);
	transition.setFromX(0); // Start from visible area
	transition.setToX(waterMovement.getBoundsInParent().getWidth()); // Move off-screen right
	transition.setInterpolator(Interpolator.EASE_BOTH);

	FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), waterMovement);
	fadeTransition.setFromValue(1); // Fully visible
	fadeTransition.setToValue(0); // Fade out to zero opacity
	fadeTransition.setInterpolator(Interpolator.EASE_BOTH);

	ParallelTransition parallelTransition = new ParallelTransition(transition, fadeTransition);
	parallelTransition.play();
    }

    // TO HERE

    /* ---------------------- SMART LOCK PAGE ------------------------ */

    @FXML
    private TextArea smartLockHistoryArea;

    @FXML
    private TextField enterPasswordTextField;

    // Send Msg to Server to Switch to Set Password Scene
    @FXML
    void setNewPasswordButton(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("setPassword.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) enterPasswordTextField.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    // Send Msg to Server to Lock Door on Smart Lock Page
    @FXML
    void lockDoorButton(ActionEvent event) {
	client.lockDoorMsgToServer();
    }

    // Send Msg to Server to Unlock Door on Smart Lock Page
    @FXML
    void unlockDoorButton(ActionEvent event) {
	try {
	    client.unlockDoorMsgToServer(enterPasswordTextField.getText());
	} catch (Exception e) {

	}
    }

    @FXML
    void returnLockButton(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) smartLockHistoryArea.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Display Notification of Lock in Text Area
    public void setLockHistoryArea(String msg) {
	if (msg.contains("Intruder alert")) {

	    enterPasswordTextField.clear();
	    enterPasswordTextField.setStyle("-fx-border-color: red;");
	    enterPasswordTextField.setPromptText("Wrong Password");
	    enterPasswordTextField.clear();
	    count++;
	    // USER ENTER WRONG PASSWORD MORE THAN 3 TIMES
	    // DISPLAY THE ALERT MESSAGE THAT IS SENT FROM SERVER
	    if (count > 3) {
		try {
		    smartLockHistoryArea.setText(msg);
		} catch (Exception e) {

		}
	    }

	} else if (msg.equals("Door has been UNLOCKED by User")) {
	    try {
		smartLockHistoryArea.setText(msg);
		enterPasswordTextField.setStyle("-fx-border-color: green;");
		enterPasswordTextField.setPromptText("Correct Password");
		enterPasswordTextField.clear();
		count = 0;
	    } catch (Exception e) {

	    }
	} else if (msg.equals("Door has been LOCKED by User")) {
	    smartLockHistoryArea.setText(msg);

	}
    }

    /* -------------------- SET LOCK PAGE --------------------- */

    @FXML
    private TextField setPasswordTextField;

    @FXML
    void backSetPasswordButton(ActionEvent event) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("STLock.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) setPasswordTextField.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // Get set password and send the String to Server //
    @FXML
    void enterSetPasswordButton(ActionEvent event) {
	String password = setPasswordTextField.getText();
	client.sendSetPasswordMsgToServer(password);
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("STLock.fxml"));
	    loader.setController(this);
	    Parent root = loader.load();
	    Scene mainMenuScene = new Scene(root);
	    Stage stage = (Stage) setPasswordTextField.getScene().getWindow();
	    stage.setScene(mainMenuScene);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}