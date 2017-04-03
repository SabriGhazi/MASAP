package com.masim.emissionAgents;

import java.util.ArrayList;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.masim.core.SourceTYpe;
import com.masim.gameTheory.PrisonnerDelimmaUtl;
import com.masim.ui.Console;
import com.masim.utils.CBox;
import com.masim.utils.Location;
import com.masim.utils.Pollutant;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.util.RWLock;

public class EmissionUnit extends Agent {

	double emisionRate;
	double production;

	// Game theory Used attribute.
	public double rewards;
	public String offenderAgent;
	public int personnality;
	public int lastChoice;
	public int otherLastChoice;
	public int choice;
	public boolean done = false;
	public boolean affected = false;
	public double participation;
	public int k = 4;
	public double ecoImpact = 0;
	// Pavlovian region.
	// the last N choice.
	public int[] lastChoices = new int[k];
	// the learning rate
	public double alpha_LR;
	// P probability for Cooperating, Q probability for defecting.
	public double P, Q;
	// P last probability for Cooperating, Q last probability for defecting.
	public double PP, QQ;

	// the weight for each reward.
	public double w1, w2, w3;
	public double[] wi = new double[k];
	// three step reward
	public double mc1, mc2, mc3;
	// end.
	public double[] mci = new double[k];
	// production function from neighbours.
	public double[] pf = new double[k];
	// production function average.
	public double pf_AVG;
	// weighted reward.
	public double RPwt;

	// weighted reward.
	public void RPwt() {

		double temp = 0;
		for (int i = 0; i < k; i++)
			temp = temp + wi[i] * mci[i];
		RPwt = temp;
	}

	public int computeDistanceInlastActions() {
		int i = 0;
		for (int j = 0; j < k - 1; j++) {
			if (lastChoices[j] != lastChoices[j + 1])
				i++;
		}
		return i;
	}

	// update the learning rate.
	public void updateAlpha() {
		if ((alpha_LR > 0.9) || (alpha_LR < 0.1))
			return;
		int diffrence = computeDistanceInlastActions();
		if (diffrence == 0) {
			if (alpha_LR + 0.015 < 1)
				alpha_LR = alpha_LR + 0.015;
		} else if (diffrence < k - 1) {
			if (alpha_LR + 0.010 < 1)
				alpha_LR = alpha_LR + 0.010;
		} else if (diffrence == k - 1) {
			if (alpha_LR - 0.010 < 1)
				alpha_LR = alpha_LR - 0.010;
		}
	}

	/*
	 * if ((lastChoices[0] == lastChoices[1]) && (lastChoices[1] ==
	 * lastChoices[2])) if (alpha_LR + 0.015 < 1) alpha_LR = alpha_LR + 0.015;
	 * else if ((lastChoices[0] == lastChoices[1]) && (lastChoices[1] !=
	 * lastChoices[2])) if (alpha_LR + 0.010 < 1) alpha_LR = alpha_LR + 0.010;
	 * else if (alpha_LR - 0.015 < 1) alpha_LR = alpha_LR - 0.010;
	 */
	// }

	// update probability for being cooperator

	public void updateEcoImpact() {
		ecoImpact = (SimulationFramework.cumulatifPenalty) ? ecoImpact
				+ (1 - (1 / Math.exp(participation))) : (1 - (1 / Math
				.exp(participation)));
	}

	public void updatePP() {
		double tmp = PrisonnerDelimmaUtl.r.nextDouble();

		if ((lastChoices[0] == 0) && (RPwt > 0)) {
			tmp = PP + (1 - PP) * (alpha_LR);
		} else if ((lastChoices[0] == 0) && (RPwt <= 0)) {
			tmp = (1 - alpha_LR) * PP;
		}
		// else if (participation != 0)
		// tmp = (1 - (1 / Math.exp(participation)));
		// if(P==1)P=P-ecoImpact;
		PP = P;
		P = tmp;
	}// end function.

	// update probability for being defeactor.
	public void updateQQ() {
		double tmp = PrisonnerDelimmaUtl.r.nextDouble();
		if ((lastChoices[0] == 1) && (RPwt > 0)) {
			tmp = QQ + (1 - QQ) * (alpha_LR);
		} else if ((lastChoices[0] == 1) && (RPwt <= 0)) {
			tmp = (1 - alpha_LR) * QQ;
		}
		// else if (participation != 0)
		// tmp = 1 - P;
		// if(Q==1)Q=Q-(1-ecoImpact);
		QQ = Q;
		Q = tmp;
	}

	public void initPavlovian(int cpt) {

		int i = 0;
		double sumPF = 0;
		for (i = 0; i < k; i++) {
			if (i == 0)
				wi[i] = 0.45;
			else
				wi[i] = wi[i - 1] - (0.10 / i);
			mci[i] = PrisonnerDelimmaUtl.r.nextDouble();
			pf[i] = PrisonnerDelimmaUtl.r.nextDouble() * 10;
			sumPF = sumPF + pf[i];
			if (i != 0)
				lastChoices[i] = PrisonnerDelimmaUtl.r.nextInt(2);
		}

		mc1 = PrisonnerDelimmaUtl.r.nextDouble();
		mc2 = PrisonnerDelimmaUtl.r.nextDouble();
		mc3 = PrisonnerDelimmaUtl.r.nextDouble();

		if (cpt < PrisonnerDelimmaUtl.getInstance().quotionC
				* SimulationFramework.numberOfSource) {
			lastChoices[0] = 0;
			cpt++;
		} else
			lastChoices[0] = 1;

		PP = PrisonnerDelimmaUtl.r.nextDouble();
		P = PrisonnerDelimmaUtl.r.nextDouble();

		QQ = 1 - PP;
		Q = 1 - P;

		pf_AVG = sumPF / k;
		alpha_LR = 0.1;

		/*
		 * Console.logMSG(getLocalName() + "w123: " + w1 + " " + w2 + " " + w3 +
		 * " mc123 " + mc1 + " " + mc2 + " " + mc3 + " " + lastChoices[0] + " "
		 * + lastChoices[1] + " " + lastChoices[2]); Console.logMSG("P PP" + P +
		 * " " + PP); Console.logMSG("Q QQ" + Q + " " + QQ);
		 * Console.logMSG("Pf_avg" + pf_AVG); Console.logMSG("ALpha" +
		 * alpha_LR);
		 */

	}

	/**
	 * Pavlovian agent.
	 */
	public void pavlovian() {
		int tmp = 0;

		/*
		 * Console.logMSG("-----------------------------------------------------\n"
		 * + " Alpha=" + alpha_LR+" \n"+ " P and PP=" + P + " " + PP+" \n"+
		 * " Q and QQ=" + Q + " " + QQ+" \n"+ " mcs " + mc1 + " " + mc2 + " " +
		 * mc3+" \n"+ " RPwt " + RPwt+" \n"+ " pf_avg " + pf_AVG + " pf123 " +
		 * pf[0] + " " + pf[1] + " " + pf[2]+" \n"+
		 * "-----------------------------------------------------");
		 */
		// Make action according to the parameters.
		double Ru = PrisonnerDelimmaUtl.r.nextDouble();
		lastChoices[2] = lastChoices[1];
		lastChoices[1] = lastChoices[0];

		if (lastChoices[0] == 0) {// if C
			if ((RPwt < pf_AVG) && (P < Q) && (Q > Ru))// &&
														// (PrisonnerDelimmaUtl.getInstance().currentPM10Level<50))
			{
				lastChoices[0] = 1;// D
				((SourceConfigInfo) SimulationFramework.sourcesInfo.get(this
						.getLocalName())).resumeEmission();
			} else {
				lastChoices[0] = 0;// keep cooperating.
				((SourceConfigInfo) SimulationFramework.sourcesInfo.get(this
						.getLocalName())).reduceEmission();
			}
		}// end if.
		else if (lastChoices[1] == 1) {
			if ((RPwt < pf_AVG) && (Q < P) && (P > Ru))// &&(PrisonnerDelimmaUtl.getInstance().currentPM10Level>50))
			{
				lastChoices[0] = 0;// C
				((SourceConfigInfo) SimulationFramework.sourcesInfo.get(this
						.getLocalName())).reduceEmission();
			} else {
				lastChoices[0] = 1;// D keep defecting.
				((SourceConfigInfo) SimulationFramework.sourcesInfo.get(this
						.getLocalName())).resumeEmission();
			}
		}// end if.

		// Update Parameters.
		RPwt();
		updateAlpha();
		updatePP();
		updateQQ();
	}

	// end of Pavlovian region.

	Location geoLocation;
	CBox isLocatedOnCbox;
	Pollutant p;
	SourceTYpe type;

	SourceConfigInfo sources;

	public SourceConfigInfo getSources() {
		return sources;
	}

	public void setSources(SourceConfigInfo sources) {
		this.sources = sources;
	}

	public double getEmisionRate() {
		return emisionRate;
	}

	public void setEmisionRate(double emisionRate) {
		this.emisionRate = emisionRate;
	}

	public double getProduction() {
		return production;
	}

	public void setProduction(double production) {
		this.production = production;
	}

	public Location getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(Location geoLocation) {
		this.geoLocation = geoLocation;
	}

	public CBox getIsLocatedOnCbox() {
		return isLocatedOnCbox;
	}

	public void setIsLocatedOnCbox(CBox isLocatedOnCbox) {
		this.isLocatedOnCbox = isLocatedOnCbox;
	}

	public Pollutant getP() {
		return p;
	}

	public void setP(Pollutant p) {
		this.p = p;
	}

	public SourceTYpe getType() {
		return type;
	}

	public void setType(SourceTYpe type) {
		this.type = type;
	}

	/**
	 * Cooperate of defect.
	 */
	public void cooperateOrDefect() {
		if (!done) {
			otherLastChoice = 0;
			// PrisonnerDelimmaUtl.getInstance().eAgentList.get(offenderAgent).lastChoice;
			switch (personnality) {
			case 0: // Allways cooperate.
			{
				lastChoice = choice;
				choice = 0;
				((SourceConfigInfo) SimulationFramework.sourcesInfo.get(this
						.getLocalName())).reduceEmission();
				break;
			}
			case 1: // Always defect
			{
				lastChoice = choice;
				choice = 1;
				((SourceConfigInfo) SimulationFramework.sourcesInfo.get(this
						.getLocalName())).resumeEmission();
				break;
			}
			case 2: // TIT-FOR-TAT.
			{
				if (otherLastChoice == 0) {
					lastChoice = choice;
					choice = 0;
					((SourceConfigInfo) SimulationFramework.sourcesInfo
							.get(this.getLocalName())).reduceEmission();
				} else if (otherLastChoice == 1) {
					lastChoice = choice;
					choice = 1;
					((SourceConfigInfo) SimulationFramework.sourcesInfo
							.get(this.getLocalName())).resumeEmission();
				}
				break;
			}

			case 3: { // Alternate.
				if (lastChoice == 1) {
					choice = 0;
					((SourceConfigInfo) SimulationFramework.sourcesInfo
							.get(this.getLocalName())).reduceEmission();
				} else if (lastChoice == 0) {
					choice = 1;
					((SourceConfigInfo) SimulationFramework.sourcesInfo
							.get(this.getLocalName())).resumeEmission();
				}
				break;
			}
			case 4: { // random
				choice = PrisonnerDelimmaUtl.r.nextInt(2);
				if (choice == 0)
					((SourceConfigInfo) SimulationFramework.sourcesInfo
							.get(this.getLocalName())).reduceEmission();
				else
					((SourceConfigInfo) SimulationFramework.sourcesInfo
							.get(this.getLocalName())).resumeEmission();
				break;
			}
			case 5: {
				if (PrisonnerDelimmaUtl.getInstance().quotionC > 0.5) {
					choice = 0;
					((SourceConfigInfo) SimulationFramework.sourcesInfo
							.get(this.getLocalName())).reduceEmission();
				} else {
					choice = 1;
					((SourceConfigInfo) SimulationFramework.sourcesInfo
							.get(this.getLocalName())).resumeEmission();
				}
				break;
			}
			case 6: {
				pavlovian();
				break;
			}
			case 7: {
				MakeVote();
				break;
			}

			}

			// PrisonnerDelimmaUtl.getInstance().flags.put(this.getLocalName(),1);
			done = true;
		}
	}// end of the function.

	// Q-Learning region.
	public int q[][] = new int[4][4];
	public int currentState;
	public boolean Trainnig = true;

	public void Qlearning() {
		System.out.println("I'm started :)");
	}
	
	public void MakeVote(){
		System.out.println("I'm voting :)");	
	}
	// end of Q-learning region.
}
