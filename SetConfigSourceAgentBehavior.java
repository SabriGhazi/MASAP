package com.masim.emissionAgents;

import com.masim.core.CooperationStrategy;
import com.masim.gameTheory.PrisonnerDelimmaUtl;
import com.masim.gameTheory.VoteStrategyUtl;
import com.masim.ui.Console;
import com.masim.utils.Environment;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/**
 * 
 * @author sabri ghazi
 *
 */
public class SetConfigSourceAgentBehavior extends OneShotBehaviour {
	EmissionUnit _agent;
	
	public SetConfigSourceAgentBehavior(Agent pagnt){
		_agent=(EmissionUnit)pagnt;
	}
	
	@Override
	public void action() {
		SourceConfigInfo obj=(SourceConfigInfo)_agent.getO2AObject();
		_agent.emisionRate=obj.emisionRate;
		_agent.production= obj.production;
		_agent.geoLocation= obj.geoLocation;
		_agent.isLocatedOnCbox= obj.isLocatedOnCbox;
		//_agent.p=obj.p;
		Console.frmLogMSG("Current Strategy is "+SimulationFramework.currentStrategy);
		if(SimulationFramework.currentStrategy==CooperationStrategy.NAIVE)
		_agent.addBehaviour( new ModeratePollutionNAIVE(_agent, 60));
		else if(SimulationFramework.currentStrategy==CooperationStrategy.NEGOTIATION){
			PrisonnerDelimmaUtl.getInstance().putInGame(_agent);
			_agent.addBehaviour(new ModeratePollutionGameTheoryPD(_agent,60));
		}
		else if(SimulationFramework.currentStrategy==CooperationStrategy.Voting){
			VoteStrategyUtl.putInGame(_agent);
			_agent.addBehaviour(new ModeratePollutionVoting(_agent,60));
		}
	}
}