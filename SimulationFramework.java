package com.masim.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.core.*;

import com.SimpleBDI.core.SimpleBDIEngine;
import com.masim.core.CooperationStrategy;
import com.masim.core.MachineLearningUtls;
import com.masim.core.SourceTYpe;
import com.masim.core.StartContainer;

import com.masim.emissionAgents.EmissionUnit;
import com.masim.emissionAgents.SourceConfigInfo;
import com.masim.gameTheory.PrisonnerDelimmaUtl;
import com.masim.ui.Console;

/**
 * This class is a singleton, giving access to the simulation parameters
 * 
 * @author Sabri Ghazi
 * 
 */
public class SimulationFramework {

	public static boolean usePredifinedScenario = true;
	public static int selectedScenario = 0;
	public static int useGausianModel = 1;
	public static boolean cumulatifPenalty = true;;

	public static int first = 0;
	public static final long SIMSPEED = 5;
	public static int numberOfSource = 160;

	public static int nbrUncontrolledSRCSPM10=0;
	public static int nbrUncontrolledSRCSSOX=0;
	public static int nbrUncontrolledSRCSCOX=0;
	public static int nbrUncontrolledSRCSNOX=0;
	
	public static int nbrPM10SRCS = 80;
	public static int nbrSOXSRCS = 80;
	public static int nbrCOXSRCS = 80;
	public static int nbrNOXSRCS = 80;
	
	public static int nbrControlledSRCS;
	
	
	public static boolean withPenalities = true;;

	public static int AirQualityIndexGoal = 2;
	
	public static double maxValPM10 = 50;
	public static double maxValSOx = 350;
	public static double maxValCOx = 10;
	public static double maxValNOx = 200;
	public static double maxValO3 = 120;
	
	public static double SusPendfactor = 2.0;
	public static double EmessionRate = 1000;

	static Environment env;
	static SimProcessState currentSimProcessState;
	public static CooperationStrategy currentStrategy = CooperationStrategy.GREEDY;
	public static Random r = new Random();

	public static HashMap<String, SourceConfigInfo> sourcesInfo = new HashMap<String, SourceConfigInfo>();
	public static ArrayList<String> listOfAgentName = new ArrayList<String>();

	// Initialise the Framwork.
	public synchronized static void initFramework() {

		env = new Environment();
		// Create the BDI Engine.
		//SimpleBDIEngine.init();
		Console.frmLogMSG("Environment is created...");
	}

	public synchronized static void loadEnvironmentData() {
		if (env != null)
			env.loadData();
	}

	public static Environment getCurrentEnv() {

		return env;
	}

	/**
	 * 
	 * @return the current state of the simulation see @see SimProcessState
	 */
	public static SimProcessState getCurrentSimProcessState() {
		if (SimulationFramework.getCurrentEnv().T == SimulationFramework
				.getCurrentEnv().max_T) {
			currentSimProcessState = SimProcessState.Stoped;
		}
		return currentSimProcessState;
	}

	public static void setCurrentSimProcessState(SimProcessState sps) {

		currentSimProcessState = sps;
	}

	/**
	 * 
	 * @param sci
	 * @throws Exception
	 */
	public static void AddSource(SourceConfigInfo sci) throws Exception {
		SimulationFramework.sourcesInfo.put(sci.getSourceName(), sci);
		// Creating the agent which will control it.
		StartContainer.createAgent(sci.getSourceName(),
				"com.masim.emissionAgents.PointSource");
	}
	
	public static void addUncontrolledSource(int nbr, PollutantType pType, double er){
		double i = 1;
		int x, y;
		while (i <= nbr) {

			SourceConfigInfo sci = new SourceConfigInfo();
			sci.setSourceName("US" + i + pType);
			sci.setUncontrolled(true);

			sci.setEmisionRate(er );
			sci.setSuspended(false);
			sci.setMaxEmissionRate(er);
			sci.setMinEmissionRate((er/2) / 25);
			x = r.nextInt(600);
			y = r.nextInt(500);

			sci.setP(pType);
			Location l = new Location(x, y);
			sci.setGeoLocation(l);
			sci.setIsLocatedOnCbox(null);
			sci.setType(SourceTYpe.Point);
			try {
				SimulationFramework.AddSource(sci);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			i++;
		}
		
	}

	public static void StartSimulation() {
		SimulationFramework.setCurrentSimProcessState(SimProcessState.Started);
		if (SimulationFramework.usePredifinedScenario) {
			nbrControlledSRCS=nbrCOXSRCS+nbrNOXSRCS+nbrPM10SRCS+nbrSOXSRCS;
			ADDsampleSources(nbrCOXSRCS, PollutantType.COx, EmessionRate,false);
			ADDsampleSources(nbrNOXSRCS, PollutantType.NOx, EmessionRate / 2,false);
			ADDsampleSources(nbrPM10SRCS, PollutantType.PM10, EmessionRate,false);
			ADDsampleSources(nbrSOXSRCS, PollutantType.SOx, EmessionRate,false);
			
			//addUncontrolledSource(nbrUncontrolledSRCSPM10, PollutantType.PM10, 10000);
			//addUncontrolledSource(nbrUncontrolledSRCSCOX, PollutantType.COx, 4000);
			//addUncontrolledSource(nbrUncontrolledSRCSNOX, PollutantType.NOx, 4000);
			//addUncontrolledSource(nbrUncontrolledSRCSSOX, PollutantType.SOx, 4000);
		}
		setMaxPollutionLevelByAirquaityIndex();

		Console.frmLogMSG(" Number of controlled sources :"+nbrControlledSRCS);
		Console.frmLogMSG(" Number of uncontrolled sources:"+nbrUncontrolledSRCSPM10);
		
		Console.frmLogMSG(" Number Of PM10 sources :"
				+ SimulationFramework.nbrPM10SRCS);
		Console.frmLogMSG(" Number Of COX sources :"
				+ SimulationFramework.nbrCOXSRCS);
		Console.frmLogMSG(" Number Of SOX sources :"
				+ SimulationFramework.nbrSOXSRCS);
		Console.frmLogMSG(" Number Of NOX sources :"
				+ SimulationFramework.nbrNOXSRCS);
		Console.frmLogMSG(" Simulation Time :"
				+ SimulationFramework.getCurrentEnv().getMax_T());
		Console.frmLogMSG("Penaly is " + SimulationFramework.withPenalities);
		Console.frmLogMSG("Cumulative Penaly is " + SimulationFramework.cumulatifPenalty);
		Console.frmLogMSG("MAX Emission Rate is: "
				+ SimulationFramework.EmessionRate);
		Console.frmLogMSG("Use Gaussian Dispersion Model : "
				+ SimulationFramework.useGausianModel);
		Console.frmLogMSG("Global Goal is Air Quality index =: "
				+ SimulationFramework.AirQualityIndexGoal);
		Console.frmLogMSG("<START_LOCAL_SIM> SENT");
	}

	/**
	 * Stop the negotiation, the simulation is ended.
	 */
	public static void StopNegociationbetWeenEmissionSources() {
		if (SimulationFramework.currentStrategy == CooperationStrategy.NEGOTIATION)
			PrisonnerDelimmaUtl.StopGame();
		// else (SimulationFramework.currentStrategy==CooperationStrategy.NAIVE)
	}

	/**
	 * Automatically add sample source.
	 */
	public static void ADDsampleSources(int nbr, PollutantType pType, double er, boolean uncontrolled) {

		double i = 1;
		int x, y;
		while (i <= nbr) {

			SourceConfigInfo sci = new SourceConfigInfo();
			sci.setSourceName("S" + i + pType);
			sci.setUncontrolled(false);

			sci.setEmisionRate(er / 2);
			sci.setSuspended(false);
			sci.setMaxEmissionRate(er);
			sci.setMinEmissionRate((er/2) / 25);
			x = r.nextInt(600);
			y = r.nextInt(500);

			sci.setP(pType);
			Location l = new Location(x, y);
			sci.setGeoLocation(l);
			sci.setIsLocatedOnCbox(null);
			sci.setType(SourceTYpe.Point);
			try {
				SimulationFramework.AddSource(sci);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			i++;
		}
	}

	/**
	 * Kill all agents controlling a sources of all pollutant type.
	 */
	public static void KillSourceAgent() {
		KillSourceAgents(nbrCOXSRCS, PollutantType.COx);
		KillSourceAgents(nbrNOXSRCS, PollutantType.NOx);
		KillSourceAgents(nbrPM10SRCS, PollutantType.PM10);
		KillSourceAgents(nbrSOXSRCS, PollutantType.SOx);
	}

	/**
	 * Kill sources controllers agents.
	 */
	public static void KillSourceAgents(int nbr, PollutantType pType) {
		double i = 1;
		while (i <= nbr) {
			try {
				StartContainer.currentAgentContainer.getAgent("S" + i + pType)
						.kill();
			} catch (StaleProxyException e) {
				// TODO Auto-generated catch block
				Console.frmLogMSG(e.getMessage());
				e.printStackTrace();
			} catch (ControllerException e) {
				// TODO Auto-generated catch block
				Console.frmLogMSG(e.getMessage());
				e.printStackTrace();
			}
			i++;
		}
	}

	public static void KillGameManagerAgent() {
		try {
			StartContainer.currentAgentContainer.getAgent("GameManager").kill();
		} catch (Exception e) {
			Console.frmLogMSG(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void setMaxPollutionLevelByAirquaityIndex() {

		maxValPM10 = MachineLearningUtls.PM10[AirQualityIndexGoal - 1][1];
		maxValSOx = MachineLearningUtls.SO2VAL[AirQualityIndexGoal - 1][1];
		maxValCOx = 10;
		maxValNOx = MachineLearningUtls.NO2VAL[AirQualityIndexGoal - 1][1];
		maxValO3 = MachineLearningUtls.O3[AirQualityIndexGoal - 1][1];
		;
	}

	public static void setScenario() {
		switch (SimulationFramework.selectedScenario) {
		case 0: {
			numberOfSource = 40;
			nbrPM10SRCS = 10;
			nbrSOXSRCS = 10;
			nbrCOXSRCS = 10;
			nbrNOXSRCS = 10;
			break;
		}
		case 1: {
			numberOfSource = 80;
			nbrPM10SRCS = 20;
			nbrSOXSRCS = 20;
			nbrCOXSRCS = 20;
			nbrNOXSRCS = 20;
			break;
		}
		case 2: {
			numberOfSource = 160;
			nbrPM10SRCS = 40;
			nbrSOXSRCS = 40;
			nbrCOXSRCS = 40;
			nbrNOXSRCS = 40;
			break;
		}
		case 3: {
			numberOfSource = 240;
			nbrPM10SRCS = 60;
			nbrSOXSRCS = 60;
			nbrCOXSRCS = 60;
			nbrNOXSRCS = 60;
			break;
		}
		case 4: {
			numberOfSource = 400;
			nbrPM10SRCS = 100;
			nbrSOXSRCS = 100;
			nbrCOXSRCS = 100;
			nbrNOXSRCS = 100;
			break;
		}
		case 5: {
			numberOfSource = 600;
			nbrPM10SRCS = 150;
			nbrSOXSRCS = 150;
			nbrCOXSRCS = 150;
			nbrNOXSRCS = 150;
			break;
		}	
		case 6: {
			numberOfSource = 1000;
			nbrPM10SRCS = 250;
			nbrSOXSRCS = 250;
			nbrCOXSRCS = 250;
			nbrNOXSRCS = 250;
			break;
		}
		case 7: {
			numberOfSource = 2000;
			nbrPM10SRCS = 500;
			nbrSOXSRCS = 500;
			nbrCOXSRCS = 500;
			nbrNOXSRCS = 500;
			break;
		}
		default:
			break;
		}
	}

	public static void save() {
		try {
			String currentDir = System.getProperty("user.dir");
			java.util.Date d = new Date();

			String fileName = currentDir + "\\results-Simulation-Session-N"
					+ SimulationFramework.currentStrategy + "PenaltyIS"
					+ SimulationFramework.withPenalities
					+ System.currentTimeMillis() + ".csv";

			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.println("------------------------------------------");
			writer.println(" Simulation results.");
			writer.println(" Number Of PM10 sources :"
					+ SimulationFramework.nbrPM10SRCS);
			writer.println(" Number Of COX sources :"
					+ SimulationFramework.nbrCOXSRCS);
			writer.println(" Number Of SOX sources :"
					+ SimulationFramework.nbrSOXSRCS);
			writer.println(" Number Of NOX sources :"
					+ SimulationFramework.nbrNOXSRCS);
			writer.println(" Simulation Time :"
					+ SimulationFramework.getCurrentEnv().getMax_T());
			writer.print("Penaly is " + SimulationFramework.withPenalities);
			writer.print("MAX Emission Rate is: "
					+ SimulationFramework.EmessionRate);
			writer.print("Use Gaussian Dispersion Model : "
					+ SimulationFramework.useGausianModel);
			writer.print("Global Goal is Air Quality index =: "
					+ SimulationFramework.AirQualityIndexGoal);
			writer.println("------------------------------------------");
			writer.println(Console.current.txtLog.toString());
			writer.close();
			Console.frmLogMSG("Results are saved in " + fileName);
		}

		catch (IOException exp) {
			Console.frmLogMSG(exp.getMessage());
			System.out.print(exp.getMessage());
		}

	}

}