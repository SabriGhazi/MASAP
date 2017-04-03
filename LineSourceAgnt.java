package com.masim.emissionAgents;

import com.masim.core.SourceTYpe;
import com.masim.utils.CBox;
import com.masim.utils.Location;
import com.masim.utils.Pollutant;
import jade.core.*;

public class LineSourceAgnt extends EmissionUnit {


	
	public LineSourceAgnt() {
		
		// TODO Auto-generated constructor stub
	}

	public void setup(){
		addBehaviour(new ModeratePollutionNAIVE(this, 600));
	}
	
	

}
