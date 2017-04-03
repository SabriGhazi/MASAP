package com.masim.meteoAgents;

import com.masim.core.MeasureAgent;
import com.masim.core.ParamInfo;

import jade.core.Agent;

public class Temp extends MeasureAgent {

	String AgentName="Temp";
	public void setup(){
		ParamInfo pi=new ParamInfo();
		pi.setName("Temp");
		pi.setUnit("µ gramme/m^3");
		
		setMeasuredParam(pi);
		this.addBehaviour(new SendParamsBehavior(this));
	}
}
