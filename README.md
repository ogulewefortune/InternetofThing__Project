# InternetofThing__Project

<img width="596" alt="Screenshot 2023-12-05 at 8 21 14 PM" src="https://github.com/ogulewefortune/InternetofThing__Project/assets/74354924/798d9df1-487c-44b2-aad6-870dfb5229b4">

Contributors HungDuong01, Ban

# Introduction
Step into our Smart Home IoT Project! We're dedicated to crafting an intuitive, user-friendly smart home system empowered by IoT tech. Seamlessly merging smart devices and sensors, our aim is to automate home functions and establish a centralized control accessible through a mobile or web application.

# Features
Automated Lighting: Control lights based on occupancy or time, ensuring convenience and energy efficiency throughout your home.

Temperature Control: Enjoy personalized comfort and efficient heating/cooling with our smart thermostat integration.

Security Monitoring: Keep your home secure with integrated cameras, smart locks, and real-time alert notifications for added peace of mind.

Water Management: Optimize water usage by seamlessly controlling irrigation systems, ensuring efficient and effective landscaping.

# Usage
To set up and run the application, follow these steps:

Configure the project build path by adding the following libraries:
javafx.base.jar
javafx.controls.jar
javafx.fxml.jar
javafx.graphics.jar
javafx.media.jar
javafx.swing.jar
javax.web.jar
javafx-swt.jar
mysql-connector-j-8.2.0.jar (used for MySql connection)
Determine your network's IP address and port number.
Open the IoTServer.java file in the computer terminal and execute it using the commands: "javac IoTServer.java" followed by "java IoTServer.java".
On the client side, access the GUI.java file to modify the Host's IP address and port number.
After updating the Host's IP and port number, ensure that the run configuration of the GUI.java file includes the following VM arguments: "--module-path /'your javafx libraries path' --add-modules javafx.controls,javafx.fxml,javafx.media".
For the "Log in" and "Sign Up" functions, set up a small MySQL database on your computer. Refer to this link for guidance on creating a MySQL database from the terminal: "https://blog.devart.com/mysql-command-line-client.html".
In the MySQL section, create two tables with the following attributes:
7.1. CREATE TABLE User( FirstName VARCHAR(100), LastName VARCHAR(100), Email VARCHAR(50), Password VARCHAR(50) );
7.2. CREATE TABLE Admin( FirstName VARCHAR(100), LastName VARCHAR(100), Email VARCHAR(50), Password VARCHAR(50) );
