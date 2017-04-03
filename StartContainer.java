package com.masim.core;

import com.masim.emissionAgents.SourceConfigInfo;
import com.masim.ui.Console;
import com.masim.utils.Location;
import com.masim.utils.Pollutant;
import com.masim.utils.SimulationFramework;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * The main class, creating UI agents.
 * and starting the rest of the core agent (Timer and environment related agent.) 
 * @author sabri ghazi ghazi_sabri@yahoo.fr
 *
 */
public class StartContainer {

	public static AgentContainer currentAgentContainer=null;
	
	public static void createAgentContainer(){
		if(currentAgentContainer==null){
			jade.core.Runtime rt= jade.core.Runtime.instance();
			
			Profile p= new ProfileImpl();
			p.setParameter(Profile.MAIN_HOST, "user-PC");
			p.setParameter(Profile.MAIN_PORT, "5050");
			p.setParameter("gui", "ture");
			currentAgentContainer= rt.createMainContainer(p);
		}
	}
	
public static boolean createAgent(String name, String type) throws Exception{
		
		AgentController agnC1=currentAgentContainer.createNewAgent(name, type, null);
		SimulationFramework.listOfAgentName.add(name);
		Console.frmLogMSG(agnC1.getName()+" Agent is Created");
		agnC1.start();
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			createAgentContainer();
			
			SimulationFramework.initFramework();
			
			createAgent("UIAgent", "com.masim.core.UIAgent");
			
			createAgent("Temp", "com.masim.meteoAgents.Temp");
			
			createAgent("VV", "com.masim.meteoAgents.VV");

			createAgent("Hu", "com.masim.meteoAgents.Hu");

			createAgent("PM10", "com.masim.predictionAgents.PM10");
			
			createAgent("Timer", "com.masim.core.TimerAgent");
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
