package com.masim.gameTheory;

import com.masim.core.CooperationStrategy;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class RunVoteBhvr extends TickerBehaviour {
	public RunVoteBhvr(Agent a, long period) {
		super(a, period);
		_agent=a;
		// TODO Auto-generated constructor stub
	}
	Agent _agent;
	@Override
	protected void onTick() {
	if(SimulationFramework.currentStrategy== CooperationStrategy.Voting){
		//VoteStrategyUtl.StartGame();
		System.out.println("*-Votting-*");
	}
	}
}
