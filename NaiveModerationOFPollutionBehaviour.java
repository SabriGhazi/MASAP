package com.masim.emissionAgents;

import com.masim.ui.Console;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class NaiveModerationOFPollutionBehaviour extends OneShotBehaviour {
	Agent _agent;

	public NaiveModerationOFPollutionBehaviour(Agent agnt) {
		_agent = agnt;
	}

	@Override
	public void action() {
		double currentValOfPM10 = SimulationFramework.getCurrentEnv().sim_PM10
				.getData().get(SimulationFramework.getCurrentEnv().T - 1);
		double currentValOfSOX = SimulationFramework.getCurrentEnv().sim_SOx
				.getData().get(SimulationFramework.getCurrentEnv().T - 1);
		double currentValOfNOX = SimulationFramework.getCurrentEnv().sim_NOx
				.getData().get(SimulationFramework.getCurrentEnv().T - 1);
		double currentValOfO3 = SimulationFramework.getCurrentEnv().sim_O3
				.getData().get(SimulationFramework.getCurrentEnv().T - 1);
		double currentValOfCOX = SimulationFramework.getCurrentEnv().sim_COx
				.getData().get(SimulationFramework.getCurrentEnv().T - 1);

		switch (((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
				.getLocalName())).getP()) {
		case PM10:
			modertePM10(currentValOfPM10);
			break;
		case SOx:
			moderteSOX(currentValOfSOX);
			//moderteO3(currentValOfO3);
			break;
		case NOx:
			moderteNOX(currentValOfNOX);
			moderteO3(currentValOfO3);
			break;
		case COx:
			moderteCOX(currentValOfCOX);
			break;
		
		default:
			break;
		}

		// Console.logMSG(
		// _agent.getName()+" -- NEW emission Rate"+
		// ((SourceConfigInfo)SimulationFramework.sourcesInfo.get(_agent.getLocalName())).getEmisionRate());
	}// end of method.

	void modertePM10(double val) {
	/*	if (val > (SimulationFramework.maxValPM10 * SimulationFramework.SusPendfactor))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(true);
		else
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(false);*/

		if (
				//(val < (SimulationFramework.maxValPM10 * SimulationFramework.SusPendfactor)
				//		)
			//	&&
				(val > SimulationFramework.maxValPM10))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).reduceEmission();
		else if (val < (SimulationFramework.maxValPM10 / 3))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).resumeEmission();
	}

	void moderteSOX(double val) {
	/*	if (val > (SimulationFramework.maxValSOx * SimulationFramework.SusPendfactor))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(true);
		else
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(false);
*/
		if (
				//(val < (SimulationFramework.maxValSOx * SimulationFramework.SusPendfactor))
				//&&
				(val > SimulationFramework.maxValSOx))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).reduceEmission();
		else if (val < (SimulationFramework.maxValSOx / 3))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).resumeEmission();
	}

	void moderteNOX(double val) {
	/*	if (val > (SimulationFramework.maxValNOx * SimulationFramework.SusPendfactor))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(true);
		else
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(false);
*/
		if (
				//(val < (SimulationFramework.maxValNOx * SimulationFramework.SusPendfactor))
				//&&
				(val > SimulationFramework.maxValNOx))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).reduceEmission();
		else if (val < (SimulationFramework.maxValNOx / 3))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).resumeEmission();
		
		
	}

	void moderteCOX(double val) {
	/*	if (val > (SimulationFramework.maxValCOx * SimulationFramework.SusPendfactor))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(true);
		else
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(false);
*/
		if (
				//(val < (SimulationFramework.maxValCOx * SimulationFramework.SusPendfactor))
				//&&
				(val > SimulationFramework.maxValCOx))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).reduceEmission();
		else if (val < (SimulationFramework.maxValCOx / 3))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).resumeEmission();
	}

	void moderteO3(double val) {
	/*	if (val > (SimulationFramework.maxValO3 * SimulationFramework.SusPendfactor))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(true);
		else
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).setSuspended(false);
*/
		if (
				///(val < (SimulationFramework.maxValO3 * SimulationFramework.SusPendfactor))
				//&& 
				(val > SimulationFramework.maxValO3))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).reduceEmission();
		else if (val < (SimulationFramework.maxValO3 / 3))
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent
					.getLocalName())).resumeEmission();
	}
}
