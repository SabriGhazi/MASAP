package com.masim.gameTheory;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class StartGameBehavior extends OneShotBehaviour {

	
	
	
	@Override
	public void action() {
	
		PrisonnerDelimmaUtl.getInstance().StartGame();
		
	}

}
