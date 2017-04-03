package com.masim.gameTheory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.masim.emissionAgents.EmissionUnit;
import com.masim.utils.SimulationFramework;

public class VoteStrategyUtl {
	
	public HashMap<String, EmissionUnit> eAgentList;
	public ArrayList<String> names;
	public static VoteStrategyUtl currentVote=null;
	public static int cpt = 0;
	public static int PAVLOVIAN = 6;
	public static boolean started=false;	
	public static boolean showHead=true;
	
	public double currentPM10Level;
	public double currentCoxLevel;
	public double currentNOXLevel;
	public double currentSOXlevel;
	public double currentO3Level;
	public double currentAQ;
	
	public static VoteStrategyUtl getInstance() {
		if (currentVote == null) {
			currentVote = new VoteStrategyUtl();
			currentVote.eAgentList = new HashMap<String, EmissionUnit>();
			currentVote.names = new ArrayList<String>(500);
			// ADDsampleSources();
		}
		return currentVote;
	}
	//
	public static Random r = new Random();
	//
	public static void putInGame(EmissionUnit eAgent) {
		if (!currentVote.eAgentList.containsKey(eAgent.getLocalName())) {
			currentVote.eAgentList.put(eAgent.getLocalName(), eAgent);
			currentVote.names.add(eAgent.getLocalName());
			eAgent.choice = r.nextInt(2);
			eAgent.lastChoice = r.nextInt(2);
			eAgent.personnality = PAVLOVIAN;
			eAgent.initPavlovian(cpt);
			cpt++;
		}
	}
	
	public static void StartGame() {

		if (SimulationFramework.sourcesInfo.size() == currentVote.eAgentList
				.size()) {
			currentVote.started = true;
		}
	}// fin
	//
	//Poll is over compute the results
	public static void MakeVote() {
		double participation=0;
		int indc = 0;
		double displayRforD = 0;
		Iterator itFlag = currentVote.eAgentList.entrySet().iterator();
		boolean dones = true;
		while (itFlag.hasNext()) {
			Map.Entry pairs = (Map.Entry) itFlag.next();
			dones = dones && ((EmissionUnit) pairs.getValue()).done;
		}//fin while
		if (dones) {
			
			//every one has made a choice what is the results. 
			//compute it here.
		}//fin
	}// fin
}