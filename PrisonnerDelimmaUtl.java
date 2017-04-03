package com.masim.gameTheory;

import jade.util.RWLock;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.junit.internal.runners.model.EachTestNotifier;

import com.masim.core.SourceTYpe;
import com.masim.emissionAgents.EmissionUnit;
import com.masim.emissionAgents.SourceConfigInfo;
import com.masim.ui.Console;
import com.masim.utils.Location;
import com.masim.utils.Pollutant;
import com.masim.utils.PollutantType;
import com.masim.utils.SimulationFramework;

public class PrisonnerDelimmaUtl {

	public HashMap<String, EmissionUnit> eAgentList;
	public ArrayList<String> names;
	public HashMap<String, Integer> choices;
	public HashMap<String, Integer> flags;
	public static PrisonnerDelimmaUtl currentPD = null;
	public static boolean started = false;
	public boolean Nperson = true;

	public double quotionC = 0.50;

	public double quotionCPM10 = 0.50;
	public double quotionCSOx = 0.50;
	public double quotionCCOx = 0.50;
	public double quotionCNOx = 0.50;

	public double currentPM10Level;
	public double currentSOXlevel;
	public double currentNOXLevel;
	public double currentCoxLevel;
	public double currentO3Level;
	public double currentAQ;
	
	
	public static int cpt = 0;

	public static double b = 2;
	public static double c = 1;

	public static int TIT_FOR_TAT = 2;
	public static int RANDOM = 4;
	public static int UNIFORMIST = 5;
	public static int ALLWAYS_DEFEACT = 1;
	public static int ALLWAYS_COOPERATE = 0;
	public static int ALTERNATE = 3;
	public static int PAVLOVIAN = 6;
	public static int votter=7;
	
	
	public static double totalPayOffOfALLAgent;

	public static double totalPayOffOfALLAgentPM10;
	public static double totalPayOffOfALLAgentSOX;
	public static double totalPayOffOfALLAgentCOX;
	public static double totalPayOffOfALLAgentNOX;
	
	public static boolean showHead=true;

	/**
	 * get current instance of the game.
	 * 
	 * @return PrisonnerDelimmaUtl
	 */
	public static PrisonnerDelimmaUtl getInstance() {
		if (currentPD == null) {
			currentPD = new PrisonnerDelimmaUtl();
			currentPD.eAgentList = new HashMap<String, EmissionUnit>();
			currentPD.names = new ArrayList<String>(500);
			currentPD.flags = new HashMap<String, Integer>();
			currentPD.choices = new HashMap<String, Integer>();

			// ADDsampleSources();
		}
		return currentPD;
	}

	public static void putInGame(EmissionUnit eAgent) {
		if (!currentPD.eAgentList.containsKey(eAgent.getLocalName())) {
			if((SimulationFramework.sourcesInfo.get(eAgent.getLocalName())).isUncontrolled() ==true) return;
			currentPD.eAgentList.put(eAgent.getLocalName(), eAgent);
			currentPD.names.add(eAgent.getLocalName());
			currentPD.flags.put(eAgent.getLocalName(), 0);

			eAgent.choice = r.nextInt(2);
			eAgent.lastChoice = r.nextInt(2);
			eAgent.personnality = PAVLOVIAN;
			eAgent.initPavlovian(cpt);

			currentPD.choices.put(eAgent.getLocalName(), eAgent.choice);
			cpt++;
		}
	}

	public static Random r = new Random();

	/**
	 * This function run a step of the game.
	 * 
	 * @param eAgent
	 */
	public static void StartGame() {

		if (SimulationFramework.nbrControlledSRCS == currentPD.eAgentList.size()) {
			//Iterator it = currentPD.eAgentList.entrySet().iterator();

			/*
			 * while (it.hasNext()) { Map.Entry pairs = (Map.Entry) it.next();
			 * EmissionUnit em = (EmissionUnit) pairs.getValue();
			 * 
			 * // if(currentNofC==0) {em.choice=1;em.personnality=4;} // else
			 * {em.choice=0; currentNofC--;
			 * em.personnality=PrisonnerDelimmaUtl.r.nextInt(5); }
			 * 
			 * boolean ok = em.affected; int x = 0; String offenderName; while
			 * (!ok) { x = r.nextInt(currentPD.eAgentList.size()); offenderName
			 * = currentPD.names.get(x); if
			 * (!currentPD.names.get(x).equalsIgnoreCase( em.getLocalName()) &&
			 * (!currentPD.eAgentList.get(offenderName).affected)) ok = true; }
			 * if (em.affected == false) { em.offenderAgent =
			 * currentPD.names.get(x); EmissionUnit em2 = (EmissionUnit)
			 * currentPD.eAgentList .get(currentPD.names.get(x));
			 * em2.offenderAgent = em.getLocalName(); em2.affected = true;
			 * em.affected = true; currentPD.eAgentList.put(em2.getLocalName(),
			 * em2); } }
			 */
			currentPD.started = true;
		}
	}// fin

	public static double C_x(double x, int n, int choice) {

		if (choice == 0)// it does cooperate
			return (2 * x / n) - 1;
		else
			// it doesn't cooperate
			return (2 * x / n) - 0.5;
	}

	private static void ReportProgress() {

		if(PrisonnerDelimmaUtl.showHead){
			PrisonnerDelimmaUtl.showHead=false;
			Console.logMSG("SimStep"+ ";"
					//+ "rewardForC" + ";" 
					//+ "rewardForD" + ";"
					+ "quotionC" + ";" 
					+ "quotionCPM10" + ";" 
					+ "quotionCCOx"+";"
					+ "quotionCSOx"+";"
					+ "quotionCNOx"+";"
					+ "PM10 Level"+ ";" 
					+ "Cox Level" + ";"
					+ "NOX Level" + ";"
					+ "SOX level"+";"
					+"O3 level"+";"
					+"Air Quality");
		}
		if(PrisonnerDelimmaUtl.started){

			Console.logTab(new String[] {
					SimulationFramework.getCurrentEnv().T +"" ,
					PrisonnerDelimmaUtl.currentPD.quotionC+"",
					PrisonnerDelimmaUtl.currentPD.quotionCPM10 +"" ,
					PrisonnerDelimmaUtl.currentPD.quotionCCOx+"",
					PrisonnerDelimmaUtl.currentPD.quotionCSOx+"",
					PrisonnerDelimmaUtl.currentPD.quotionCNOx+"",
					PrisonnerDelimmaUtl.currentPD.currentPM10Level+ "", 
					PrisonnerDelimmaUtl.currentPD.currentCoxLevel + "",
					PrisonnerDelimmaUtl.currentPD.currentNOXLevel + "",
					PrisonnerDelimmaUtl.currentPD.currentSOXlevel+"",
					PrisonnerDelimmaUtl.currentPD.currentO3Level+"",
					PrisonnerDelimmaUtl.currentPD.currentAQ+""
			});

			Console.logMSG(SimulationFramework.getCurrentEnv().T + ";"
					//+ rewardForC + ";" + rewardForD + ";"
					+PrisonnerDelimmaUtl.currentPD.quotionC+";"
					+ PrisonnerDelimmaUtl.currentPD.quotionCPM10 + ";" 
					+ PrisonnerDelimmaUtl.currentPD.quotionCCOx+";"
					+ PrisonnerDelimmaUtl.currentPD.quotionCSOx+";"
					+ PrisonnerDelimmaUtl.currentPD.quotionCNOx+";"
					+ PrisonnerDelimmaUtl.currentPD.currentPM10Level+ ";" 
					+ PrisonnerDelimmaUtl.currentPD.currentCoxLevel + ";"
					+ PrisonnerDelimmaUtl.currentPD.currentNOXLevel + ";"
					+ PrisonnerDelimmaUtl.currentPD.currentSOXlevel+";"
					+ PrisonnerDelimmaUtl.currentPD.currentO3Level+";"+
					PrisonnerDelimmaUtl.currentPD.currentAQ);

		}

	}
	
	public static void ComputeRewards() {
		double participation;
		int indc = 0;
		double displayRforD = 0;
		Iterator itFlag = currentPD.eAgentList.entrySet().iterator();
		boolean dones = true;
		while (itFlag.hasNext()) {
			Map.Entry pairs = (Map.Entry) itFlag.next();
			dones = dones && ((EmissionUnit) pairs.getValue()).done;
		}
		if (true)
		{
			int t= SimulationFramework.getCurrentEnv().T;
			//ReportProgress();
			if(t<SimulationFramework.getCurrentEnv().getMax_T()){
				SimulationFramework.getCurrentEnv().T=t+1;
			}
			Iterator it = currentPD.eAgentList.entrySet().iterator();

			if (currentPD.Nperson) {
				double numberOfCooperators = 0;
				double nmberOfDefeactors = 0;

				double numberOfCooperatorsPM10 = 0;
				double nmberOfDefeactorsPM10 = 0;

				double numberOfCooperatorsSOX = 0;
				double nmberOfDefeactorsSOX = 0;

				double numberOfCooperatorsNOX = 0;
				double nmberOfDefeactorsNOX = 0;

				double numberOfCooperatorsCOX = 0;
				double nmberOfDefeactorsCOX = 0;

				PollutantType emPtype;
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry) it.next();
					EmissionUnit em = (EmissionUnit) pairs.getValue();
					emPtype = SimulationFramework.sourcesInfo.get(
							em.getLocalName()).getP();
					if (em.lastChoices[0] == 0) {
						numberOfCooperators++;
						if (emPtype == PollutantType.PM10)
							numberOfCooperatorsPM10++;
						if (emPtype == PollutantType.SOx)
							numberOfCooperatorsSOX++;
						if (emPtype == PollutantType.NOx)
							numberOfCooperatorsNOX++;
						if (emPtype == PollutantType.COx)
							numberOfCooperatorsCOX++;
					} else {
						nmberOfDefeactors++;
						if (emPtype == PollutantType.PM10)
							nmberOfDefeactorsPM10++;
						if (emPtype == PollutantType.SOx)
							nmberOfDefeactorsSOX++;
						if (emPtype == PollutantType.NOx)
							nmberOfDefeactorsNOX++;
						if (emPtype == PollutantType.COx)
							nmberOfDefeactorsCOX++;
					}
					totalPayOffOfALLAgent = totalPayOffOfALLAgent + em.rewards;

					totalPayOffOfALLAgentPM10 = totalPayOffOfALLAgentPM10
							+ em.rewards;
					totalPayOffOfALLAgentSOX = totalPayOffOfALLAgentSOX
							+ em.rewards;
					totalPayOffOfALLAgentCOX = totalPayOffOfALLAgentCOX
							+ em.rewards;
					totalPayOffOfALLAgentNOX = totalPayOffOfALLAgentNOX
							+ em.rewards;

				}
				Iterator it2 = currentPD.eAgentList.entrySet().iterator();

				double rewardForC = C_x(numberOfCooperators,
						currentPD.eAgentList.size(), 0);

				double rewardForD = C_x(numberOfCooperators,
						currentPD.eAgentList.size(), 1);
/*
				double rewardForCPM10 = C_x(numberOfCooperatorsPM10,
						SimulationFramework.nbrPM10SRCS, 0);

				double rewardForDPM10 = C_x(numberOfCooperatorsPM10,
						SimulationFramework.nbrPM10SRCS, 1);

				double rewardForCSOx = C_x(numberOfCooperatorsSOX,
						SimulationFramework.nbrSOXSRCS, 0);

				double rewardForDSOx = C_x(numberOfCooperatorsSOX,
						SimulationFramework.nbrSOXSRCS, 1);

				double rewardForCCOx = C_x(numberOfCooperatorsCOX,
						SimulationFramework.nbrCOXSRCS, 0);

				double rewardForDCOx = C_x(numberOfCooperatorsCOX,
						SimulationFramework.nbrCOXSRCS, 1);

				double rewardForCNOx = C_x(numberOfCooperatorsNOX,
						SimulationFramework.nbrNOXSRCS, 0);

				double rewardForDNOx = C_x(numberOfCooperatorsNOX,
						SimulationFramework.nbrNOXSRCS, 1);*/

				// Console.logMSG(rewardForC+" ;"+rewardForD);

				currentPD.quotionC = numberOfCooperators
						/ currentPD.eAgentList.size();

				currentPD.quotionCPM10 = numberOfCooperatorsPM10
						/ SimulationFramework.nbrPM10SRCS;

				currentPD.quotionCSOx = numberOfCooperatorsSOX
						/ SimulationFramework.nbrSOXSRCS;

				currentPD.quotionCCOx = numberOfCooperatorsCOX
						/ SimulationFramework.nbrCOXSRCS;

				currentPD.quotionCNOx = numberOfCooperatorsNOX
						/ SimulationFramework.nbrNOXSRCS;

				if (SimulationFramework.getCurrentEnv().sim_PM10.getData()
						.size() > 1)
					currentPD.currentPM10Level = SimulationFramework
							.getCurrentEnv().sim_PM10.getData().get(
							SimulationFramework.getCurrentEnv().T - 1);

				currentPD.currentSOXlevel = SimulationFramework.getCurrentEnv().sim_SOx
						.getData().get(
								SimulationFramework.getCurrentEnv().T - 1);

				currentPD.currentCoxLevel = SimulationFramework.getCurrentEnv().sim_COx
						.getData().get(
								SimulationFramework.getCurrentEnv().T - 1);

				currentPD.currentNOXLevel = SimulationFramework.getCurrentEnv().sim_NOx
						.getData().get(
								SimulationFramework.getCurrentEnv().T - 1);
				
				currentPD.currentO3Level= SimulationFramework.getCurrentEnv().sim_O3
				.getData().get(
						SimulationFramework.getCurrentEnv().T - 1);
				
				currentPD.currentAQ=SimulationFramework.getCurrentEnv().AirQuality_V.getData().get(SimulationFramework.getCurrentEnv().T - 1);

				while (it2.hasNext()) {
					Map.Entry pairs = (Map.Entry) it2.next();
					EmissionUnit em = (EmissionUnit) pairs.getValue();
					emPtype = SimulationFramework.sourcesInfo.get(
							em.getLocalName()).getP();
					if (em.lastChoices[0] == 0) {
						/*switch (emPtype) {
						case PM10:
							em.rewards = rewardForCPM10;
							break;
						case COx:
							em.rewards = rewardForCCOx;
							break;
						case SOx:
							em.rewards = rewardForCSOx;
							break;
						case NOx:
							em.rewards = rewardForCNOx;
							break;
						default:
							break;
						}*/
						
						em.rewards = rewardForC;
						// em.participation = 0;
					} else {

						switch (emPtype) {
						case PM10: {
							if (currentPD.currentPM10Level > SimulationFramework.maxValPM10) {
								participation = (SimulationFramework.sourcesInfo
										.get(em.getLocalName())
										.getEmisionRate() / (currentPD.currentPM10Level - SimulationFramework.maxValPM10));
								em.participation = participation;
								em.updateEcoImpact();
								em.rewards = (SimulationFramework.withPenalities)?rewardForD - (em.ecoImpact):rewardForD;
							} else
								em.rewards = rewardForD;
							break;
						}
						case COx: {
							if (currentPD.currentCoxLevel > SimulationFramework.maxValCOx) {
								participation = (SimulationFramework.sourcesInfo
										.get(em.getLocalName())
										.getEmisionRate() / (currentPD.currentCoxLevel - SimulationFramework.maxValCOx));
								em.participation = participation;
								em.updateEcoImpact();
								em.rewards = (SimulationFramework.withPenalities)?rewardForD - (em.ecoImpact):rewardForD;
							} else
								em.rewards = rewardForD;
							if (currentPD.currentO3Level > SimulationFramework.maxValNOx)
								em.rewards = (SimulationFramework.withPenalities)?rewardForD - (em.ecoImpact):rewardForD;
							break;
						}
						case SOx: {
							if (currentPD.currentSOXlevel > SimulationFramework.maxValSOx) {
								participation = (SimulationFramework.sourcesInfo
										.get(em.getLocalName())
										.getEmisionRate() / (currentPD.currentSOXlevel - SimulationFramework.maxValSOx));
								em.participation = participation;
								em.updateEcoImpact();
								em.rewards = (SimulationFramework.withPenalities)?rewardForD - (em.ecoImpact):rewardForD;
							} else
								em.rewards = rewardForD;
							// em.rewards=rewardForDSOx;
							break;
						}
						case NOx: {
							if (currentPD.currentNOXLevel > SimulationFramework.maxValNOx) {
								participation = (SimulationFramework.sourcesInfo
										.get(em.getLocalName())
										.getEmisionRate() / (currentPD.currentNOXLevel - SimulationFramework.maxValNOx));
								em.participation = participation;
								em.updateEcoImpact();
								em.rewards = (SimulationFramework.withPenalities)?rewardForD - (em.ecoImpact):rewardForD;
							} else
								em.rewards = rewardForD;
							// em.rewards=rewardForDNOx;
							if (currentPD.currentO3Level > SimulationFramework.maxValNOx)
								em.rewards = (SimulationFramework.withPenalities)?rewardForD - (em.ecoImpact):rewardForD;
							break;
						}
					
						default:
							break;
						}
						// displayRforD = em.rewards;
						/*
						 * Console.logMSG(participation + " " +
						 * (SimulationFramework.sourcesInfo.get(
						 * em.getLocalName()).getEmisionRate() + " " +
						 * (currentPD.currentPM10Level - 50))+" "+
						 * em.ecoImpact);
						 */
						/*
						 * Console.logMSG(" RC :" + rewardForC + " RD:" +
						 * rewardForD+ " Par:"+ em.participation + " EI " +
						 * em.ecoImpact + " er " +
						 * SimulationFramework.sourcesInfo.get(
						 * em.getLocalName()).getEmisionRate());
						 */
					}
					// em.participation = 0;

					for (indc = 0; indc < em.k - 1; indc++) {
						em.mci[indc + 1] = em.mci[indc];
						em.pf[indc + 1] = em.pf[indc];
					}
					em.mci[0] = em.rewards;
					switch (emPtype) {
					case PM10:
						em.pf[0] = (totalPayOffOfALLAgentPM10 - em.rewards)
								/ (SimulationFramework.nbrPM10SRCS);
						break;
					case SOx:
						em.pf[0] = (totalPayOffOfALLAgentSOX - em.rewards)
								/ (SimulationFramework.nbrSOXSRCS);
						break;
					case COx:
						em.pf[0] = (totalPayOffOfALLAgentCOX - em.rewards)
								/ (SimulationFramework.nbrCOXSRCS);
						break;
					case NOx:
						em.pf[0] = (totalPayOffOfALLAgentNOX - em.rewards)
								/ (SimulationFramework.nbrNOXSRCS);
						break;
					default:
						break;
					}
				}
			/*	if(showHead){
					showHead=false;
					Console.logMSG("SimStep"+ ";"
							+ "rewardForC" + ";" 
							+ "rewardForD" + ";"
							+ "quotionC" + ";" 
							+ "quotionCPM10" + ";" 
							+ "quotionCCOx"+";"
							+ "quotionCSOx"+";"
							+ "quotionCNOx"+";"
							+ "PM10 Level"+ ";" 
							+ "Cox Level" + ";"
							+ "NOX Level" + ";"
							+ "SOX level"+";"
							+"O3 level"+";"
							+"Air Quality");
				}
				Console.logMSG(SimulationFramework.getCurrentEnv().T + ";"
						+ rewardForC + ";" + rewardForD + ";"
						+currentPD.quotionC+";"
						+ currentPD.quotionCPM10 + ";" 
						+ currentPD.quotionCCOx+";"
						+ currentPD.quotionCSOx+";"
						+ currentPD.quotionCNOx+";"
						+ currentPD.currentPM10Level+ ";" 
						+ currentPD.currentCoxLevel + ";"
						+ currentPD.currentNOXLevel + ";"
						+ currentPD.currentSOXlevel+";"
						+currentPD.currentO3Level+";"+
						currentPD.currentAQ);*/
				// SimulationFramework.getCurrentEnv().T++;
			} else {
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry) it.next();
					EmissionUnit em = (EmissionUnit) pairs.getValue();

					EmissionUnit em2 = (EmissionUnit) currentPD.eAgentList
							.get(em.offenderAgent);
					if ((em.choice == 1) && (em2.choice == 1)) {
						em.rewards += 1;
						// em2.rewards+=1;
					} else if ((em.choice == 0) && (em2.choice == 0)) {
						em.rewards += 3;
						// em2.rewards+=3;
					} else if ((em.choice == 0) && (em2.choice == 1)) {
						em.rewards += 0;
						// em2.rewards+=3;
					} else if ((em.choice == 1) && (em2.choice == 0)) {
						em.rewards += 3;
						// em2.rewards+=0;
					}
				}// end else
			}// end while
				// Authorise agent to take the next action
			
		
			
			it = currentPD.eAgentList.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry p = (Map.Entry) it.next();
				((EmissionUnit) p.getValue()).done = false;
			}
		}
	}

	/**
	 * Stop the PD game.
	 */
	public static void StopGame() {
		if (currentPD != null)
			currentPD.started = false;
	}
}