package com.masim.emissionAgents;

import com.masim.core.SourceTYpe;
import com.masim.utils.CBox;
import com.masim.utils.Location;
import com.masim.utils.Pollutant;

public class AreaSourceAgnt extends EmissionUnit {

	public void setup(){
		
		addBehaviour(new SetConfigSourceAgentBehavior(this));
	
		
	}
}
