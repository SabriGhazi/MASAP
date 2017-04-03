package com.masim.meteoAgents;

import com.masim.core.MeasureAgent;
import com.masim.core.ParamInfo;

import jade.core.Agent;

public class Hu extends MeasureAgent {

	
	
	public void setup(){
		ParamInfo pi=new ParamInfo();
		pi.setName("Hu");
		pi.setUnit("µ gramme/m^3");
		
		setMeasuredParam(pi);
		this.addBehaviour(new SendParamsBehavior(this));
	}
	
}
