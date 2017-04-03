package com.masim.utils;

import java.util.ArrayList;

/*
 * A class that regroup the vector values and its name, unit and min max values.
 * Used to load data from the CSV files.
 */
public class DataItem {

	ArrayList<Double> data;
	String ItemName;
	String ItemUnit;
	Double ItemMaxVal;
	Double ItemMinVal;
	
	public DataItem(){
		data= new ArrayList<Double>();
	}
	
	public synchronized ArrayList<Double> getData() {
		
		return this.data;
	}
	public void setData(ArrayList<Double> data) {
		this.data = data;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public String getItemUnit() {
		return ItemUnit;
	}
	public void setItemUnit(String itemUnit) {
		ItemUnit = itemUnit;
	}
	public Double getItemMaxVal() {
		return ItemMaxVal;
	}
	public void setItemMaxVal(Double itemMaxVal) {
		ItemMaxVal = itemMaxVal;
	}
	public Double getItemMinVal() {
		return ItemMinVal;
	}
	public void setItemMinVal(Double itemMinVal) {
		ItemMinVal = itemMinVal;
	}
	
	public void addData(double d){
		data.add(d);
	}

	public String toString(){
		return (getItemName()+"\t "+getData().size()+
				 "\t "+getItemMaxVal()+
				 "\t "+getItemMinVal()+
				 "\t "+getItemUnit()+ "\n");
	}
}
