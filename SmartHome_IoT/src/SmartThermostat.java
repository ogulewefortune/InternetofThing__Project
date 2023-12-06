/* ------------------------------------------------------------------------------------
 * SmartThermostat.java
 * 
 * Copyright (c) 2023 Venos Tech. All rights reserved
 * 
 * Related Documents: 
 *    Specification Document 
 *    Design Document
 * 
 * File created by Duong Chan Hung on 11/08/2023
 * 
 * ------------------------------------------------------------------------------------
 */

public class SmartThermostat extends SmartDevice {
    // Constant variable for optimum temperature
    private final Integer OPT_TEMP = 20;
    private Integer temperature;

    public SmartThermostat(Integer deviceID, boolean status) {
	super(deviceID, status);
	this.temperature = OPT_TEMP;
    }

// --- SETTER AND GETTER FUNCTIONS FOR CHANGING THE THERMOSTAT'S TEMPERATURE ---
    public void setTemperature(Integer updateTemperature) {
	temperature = updateTemperature;
    }

    public Integer getTemperature() {
	return this.temperature;
    }
// --- END ---

// --- SETTER AND GETTER FUNCTIONS FOR CHANGING THE LIGHT'S COLOR ---
    public void increase(Integer update) {
	temperature = temperature + update;
    }

    public void decrease(Integer update) {
	temperature = temperature - update;
    }
// --- END --- 

    @Override
    public String alertMessage() {
	// TODO Auto-generated method stub
	return "Please note that the current temperature settings in your house is not ideal for maintaining optimal health conditions.";

    }

}
