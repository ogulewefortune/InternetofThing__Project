/* ------------------------------------------------------------------------------------
 * SmartLight.java
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
public class SmartLight extends SmartDevice {
    private String color;
    private Integer brightness;

    public SmartLight(Integer deviceID, boolean status) {
	super(deviceID, status);
	// TODO Auto-generated constructor stub
	this.color = "0xffffff";
	this.brightness = 50;
    }

// --- SETTER AND GETTER FUNCTIONS FOR CHANGING THE LIGHT'S COLOR ---
    public void setColor(String color) {
	this.color = color;
    }

    public String getColor() {
	return this.color;
    }
// --- END ---

// --- SETTER AND GETTER FUNCTIONS FOR CHANGING THE LIGHT'S BRIGHTNESS --- 
    public void increaseBrightness(Integer brightness) {
	this.brightness += brightness;
    }

    public void decreaseBrightness(Integer brightness) {
	this.brightness -= brightness;
    }

    public Integer getBrightness() {
	return this.brightness;
    }
// --- END ---

    @Override
    public String alertMessage() {
	// TODO Auto-generated method stub
	return "";

    }

}
