package com.masim.utils;

public class Pollutant {

	String Name;
	String ScientificName;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getScientificName() {
		return ScientificName;
	}
	public void setScientificName(String scientificName) {
		ScientificName = scientificName;
	}
	
	/**
	 * @param name
	 * @param scientificName
	 */
	public Pollutant(String name, String scientificName) {
		super();
		Name = name;
		ScientificName = scientificName;
	}
	
	
	
}
