
/* ------------------------------------------------------------------------------------
 * SmartWaterSystem.java
 * 
 * Copyright (c) 2023 Venos Tech. All rights reserved
 * 
 * Related Documents: 
 *    Specification Document 
 *    Design Document
 * 
 * File created by Duong Chan Hung on 11/08/2023
 * 
 * Associated file
 * ------------------------------------------------------------------------------------
 */
import java.time.LocalTime;

public class SmartWaterSystem extends SmartDevice {
    private Integer limit;
    private LocalTime timer;

    public SmartWaterSystem(Integer deviceID, boolean status) {
	super(deviceID, status);
	// TODO Auto-generated constructor stub
    }

    public void setTimer(LocalTime timer) {
	this.timer = timer;
    }

    public LocalTime getTimer() {
	return this.timer;
    }

    public void setWaterLimit(Integer limit) {
	this.limit = limit;
    }

    public Integer getWaterLimit() {
	return this.limit;
    }

    @Override
    public String alertMessage() {
	// TODO Auto-generated method stub
	return "Completed task";
    }

}
