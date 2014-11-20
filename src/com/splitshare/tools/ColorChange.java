package com.splitshare.tools;


public class ColorChange {
	
	public int hexColor;
	public int red;
	public int green;
	public int blue;
	
	public ColorChange(int hexColor) {
		this.hexColor = hexColor;
		this.red = (hexColor >> 16) & 0xFF;
		this.green = (hexColor >> 8) & 0xFF;
		this.blue = (hexColor >> 0) & 0xFF;
	}
	
	public ColorChange(int red, int green, int blue) {
		this.hexColor = Integer.parseInt(String.format("%02x%02x%02x", red, green, blue));
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public int getHexColor() {
		return this.hexColor;
	}
	
	public int getRed() {
		return this.red;
	}
	
	public int getGreen() {
		return this.green;
	}
	
	public int getBlue() {
		return this.blue;
	}
}
