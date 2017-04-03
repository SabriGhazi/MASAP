package com.masim.utils;

public class Location {

	long x;
	long y;
	long level;
	
	public Location(long longitude, long altitude) {
		super();
		this.x = longitude;
		this.y = altitude;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	
	
	
	
}
