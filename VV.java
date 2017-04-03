package com.masim.meteoAgents;

import com.masim.core.MeasureAgent;
import com.masim.core.ParamInfo;

import jade.core.Agent;

public class VV extends MeasureAgent {

	public void setup(){
		ParamInfo pi=new ParamInfo();
		pi.setName("WS");
		pi.setUnit("m/s");
		setMeasuredParam(pi);
		this.addBehaviour(new SendParamsBehavior(this));
	}
}
