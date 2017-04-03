package com.masim.core;

import com.masim.emissionAgents.EmissionUnit;
import com.masim.utils.Station;

public class MeasureAgent extends GAgent {

	ParamInfo MeasuredParam;
	Station stationName;
	public ParamInfo getMeasuredParam() {
		return MeasuredParam;
	}
	public void setMeasuredParam(ParamInfo measuredParam) {
		MeasuredParam = measuredParam;
	}
	public Station getStationName() {
		return stationName;
	}
	public void setStationName(Station stationName) {
		this.stationName = stationName;
	}
	
	
}


