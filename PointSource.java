package com.masim.emissionAgents;

import com.masim.core.SourceTYpe;
import com.masim.gameTheory.PrisonnerDelimmaUtl;
import com.masim.gameTheory.VoteStrategyUtl;
import com.masim.utils.CBox;
import com.masim.utils.Location;
import com.masim.utils.Pollutant;
import com.masim.utils.SimulationFramework;

public class PointSource extends EmissionUnit {

	public void setup() {
		// setEnabledO2ACommunication(true, 2);
		switch (SimulationFramework.currentStrategy) {
		// no behaviour is needed, because there is no cooperation between
		// agents.
		case GREEDY:
			addBehaviour(new GreedyStrategy(this,
					SimulationFramework.SIMSPEED));
			break;
		case NAIVE:
			addBehaviour(new ModeratePollutionNAIVE(this,
					SimulationFramework.SIMSPEED));
			break;
		case CENTRALIZED:
			addBehaviour(new ReceiveASKforERBehaviour(this,
					SimulationFramework.SIMSPEED));
			break;
		case NEGOTIATION: {
			//if((SimulationFramework.sourcesInfo.get(this.getLocalName())).uncontrolled==true) return;
			PrisonnerDelimmaUtl.getInstance().putInGame(this);
			addBehaviour(new ModeratePollutionGameTheoryPD(this,
					SimulationFramework.SIMSPEED));
			break;
		}
		case Voting :{
			VoteStrategyUtl.getInstance().putInGame(this);
			addBehaviour(new ModeratePollutionVoting(this,
					SimulationFramework.SIMSPEED));
		}
			break;
		case Qlearning: {
			
			break;
		}
		default:
			break;
		}
	}
}
