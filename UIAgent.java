package com.masim.core;

public class UIAgent extends GAgent {

	public void setup(){
		
		this.addBehaviour(new StartUIBehaviour());
	}
	
}
