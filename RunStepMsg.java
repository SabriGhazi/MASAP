package com.masim.core;

import java.io.Serializable;

public class  RunStepMsg implements Serializable{

	Double t;

	public Double getT() {
		return t;
	}

	public void setT(Double t) {
		this.t = t;
	}

	/**
	 * @param t
	 */
	public RunStepMsg(double t) {
		
		this.t = new Double(t);
	}
	
}
