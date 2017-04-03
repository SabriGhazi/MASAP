package com.masim.predictionAgents;

import java.util.ArrayList;
import java.util.List;

import com.masim.core.CooperationStrategy;
import com.masim.core.MachineLearningUtls;
import com.masim.core.ParamInfo;
import com.masim.gameTheory.PrisonnerDelimmaUtl;
import com.masim.gameTheory.VoteStrategyUtl;
import com.masim.ui.Console;
import com.masim.utils.AirQualityUTL;
import com.masim.utils.DispersionEngine;
import com.masim.utils.EnvironmentState;
import com.masim.utils.PollutantType;
import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/**
 *
 *     
 * @author user
 *
 */
public class MakePredictionBehaviour extends TickerBehaviour {

	PM10 _agnt;
	static int lastTime=-1;

	MakePredictionBehaviour(Agent agnt,long period){
		super(agnt,SimulationFramework.SIMSPEED);
		_agnt=(PM10)agnt;
	}
	@Override
	public void onTick() {
		if(SimulationFramework.getCurrentSimProcessState()== SimProcessState.Started){
			/*
		ACLMessage paramsInfoMsg=_agnt.receive();
		if(paramsInfoMsg!=null){
			ACLMessage reply= new ACLMessage(ACLMessage.INFORM);
			reply.setContent("<CONFIRM>");
			reply.addReceiver(paramsInfoMsg.getSender());
			_agnt.send(reply);
			String msg;
			try {
				ParamInfo pi= ((ParamInfo) paramsInfoMsg.getContentObject());
				if(pi.getName().equalsIgnoreCase("Hu"))
					_agnt.hu=pi.getValue();
				else if(pi.getName().equalsIgnoreCase("Temp"))
					_agnt.temp=pi.getValue();
				else _agnt.ws=pi.getValue();

				msg = _agnt.getName()+
				": <ParamRecived> :"+
				paramsInfoMsg.getSender().getName()+" :"
				+pi.getValue();

				_agnt.setNbrOfMsgParam(_agnt.getNbrOfMsgParam()+1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			 */
			//if(_agnt.getNbrOfMsgParam()==3){
			//_agnt.setNbrOfMsgParam(0);

			double currentValOfPM10;
			currentValOfPM10=(SimulationFramework.useGausianModel==1)?SimulationFramework.getCurrentEnv().dispersionEngine.pm10lvelGassian:
				((SimulationFramework.getCurrentEnv().dispersionEngine.pm10nbr * 10000)/(DispersionEngine.nHeight*DispersionEngine.nWidth))/2;
			//+
			//SimulationFramework.getCurrentEnv().PM10_V.getData().get(t);
			double currentValOfSOX;

			currentValOfSOX=(SimulationFramework.useGausianModel==1)?SimulationFramework.getCurrentEnv().dispersionEngine.soxlvelGassian:
				((SimulationFramework.getCurrentEnv().dispersionEngine.soxnbr * 10000)/(DispersionEngine.nHeight*DispersionEngine.nWidth))/2;
			//SimulationFramework.getCurrentEnv().SOx.getData().get(t);
			double currentValOfCOX;

			currentValOfCOX=(SimulationFramework.useGausianModel==1)?SimulationFramework.getCurrentEnv().dispersionEngine.coxlvelGassian:
				((SimulationFramework.getCurrentEnv().dispersionEngine.cox * 10000)/(DispersionEngine.nHeight*DispersionEngine.nWidth))/2;
			//SimulationFramework.getCurrentEnv().COx.getData().get(t);
			double currentValOfNOX;

			currentValOfNOX=(SimulationFramework.useGausianModel==1)?SimulationFramework.getCurrentEnv().dispersionEngine.noxlvelGassian:
				((SimulationFramework.getCurrentEnv().dispersionEngine.nox * 10000)/(DispersionEngine.nHeight*DispersionEngine.nWidth))/2;
			//SimulationFramework.getCurrentEnv().NOx_V.getData().get(t);
			double currentValOfO3=currentValOfNOX;
			//SimulationFramework.getCurrentEnv().O3.getData().get(t);

			double hu=SimulationFramework.getCurrentEnv().hu_V.getData().get(SimulationFramework.getCurrentEnv().T);
			double temp=SimulationFramework.getCurrentEnv().temp_V.getData().get(SimulationFramework.getCurrentEnv().T);
			double ws=SimulationFramework.getCurrentEnv().ws_V.getData().get(SimulationFramework.getCurrentEnv().T);

			double[] input={hu,temp,ws,currentValOfPM10};

			//System.out.println(hu+" tmp:"+temp+" ws "+ws+" pm10 "+currentValOfPM10+" t"+SimulationFramework.getCurrentEnv().T);

			double PM10_Prediected=MachineLearningUtls.interrogateModel(
					SimulationFramework.getCurrentEnv().pm10MODEL, 
					input);
			PM10_Prediected=(PM10_Prediected<0)?PM10_Prediected*-1:PM10_Prediected;


			input[3]=currentValOfSOX;
			double SOX_Predicted=MachineLearningUtls.interrogateModel(
					SimulationFramework.getCurrentEnv().SOxMODEL, 
					input);

			input[3]=currentValOfCOX;
			double COX_Predicted=MachineLearningUtls.interrogateModel(
					SimulationFramework.getCurrentEnv().COxMODEL, 
					input);

			input[3]=currentValOfO3;
			double O3_Predicted=MachineLearningUtls.interrogateModel(
					SimulationFramework.getCurrentEnv().O3MODEL, 
					input);

			input[3]=currentValOfNOX;
			double NOX_Predicted=MachineLearningUtls.interrogateModel(
					SimulationFramework.getCurrentEnv().NOxMODEL, 
					input);

			//SimulationFramework.getCurrentEnv().T=SimulationFramework.getCurrentEnv().T+1;
			SimulationFramework.getCurrentEnv().sim_PM10.addData(PM10_Prediected);
			SimulationFramework.getCurrentEnv().sim_O3.addData(O3_Predicted);
			SimulationFramework.getCurrentEnv().sim_NOx.addData(NOX_Predicted);
			SimulationFramework.getCurrentEnv().sim_SOx.addData(SOX_Predicted);
			SimulationFramework.getCurrentEnv().sim_COx.addData(COX_Predicted);

			EnvironmentState es = new EnvironmentState();

			es.setPM10(PM10_Prediected);
			es.setO3(O3_Predicted);
			es.setCOx(COX_Predicted);
			es.setNOx(NOX_Predicted);
			es.setSOx(SOX_Predicted);

			double currentAQ=AirQualityUTL.getAirQuality(es);

			SimulationFramework.getCurrentEnv().AirQuality_V.addData(currentAQ);
			if(SimulationFramework.currentStrategy== CooperationStrategy.GREEDY)
				ReportProgressCentralized();
			else
			if(SimulationFramework.currentStrategy== CooperationStrategy.CENTRALIZED)
			{
				ReportProgressCentralized();
			}
			else if(SimulationFramework.currentStrategy== CooperationStrategy.NEGOTIATION){
				ReportProgress();
			}

			else if(SimulationFramework.currentStrategy== CooperationStrategy.Voting){
				/*if(VoteStrategyUtl.showHead){
							VoteStrategyUtl.showHead=false;

							Console.logMSG("SimStep"+ ";"
									+ "PM10 Level"+ ";" 
									+ "Cox Level" + ";"
									+ "NOX Level" + ";"
									+ "SOX level"+";"
									+"O3 level"+";"
									+"Air Quality");
						}
						if(VoteStrategyUtl.started){
							Console.logTab(new String[] {
									SimulationFramework.getCurrentEnv().T +"" ,
									 VoteStrategyUtl.currentVote.currentPM10Level+ "", 
									 VoteStrategyUtl.currentVote.currentCoxLevel + "",
									 VoteStrategyUtl.currentVote.currentNOXLevel + "",
									 VoteStrategyUtl.currentVote.currentSOXlevel+"",
									 VoteStrategyUtl.currentVote.currentO3Level+"",
									 VoteStrategyUtl.currentVote.currentAQ+""
							});

						Console.logMSG(SimulationFramework.getCurrentEnv().T + ";"
								+ PrisonnerDelimmaUtl.currentPD.currentPM10Level+ ";" 
								+ PrisonnerDelimmaUtl.currentPD.currentCoxLevel + ";"
								+ PrisonnerDelimmaUtl.currentPD.currentNOXLevel + ";"
								+ PrisonnerDelimmaUtl.currentPD.currentSOXlevel+";"
								+PrisonnerDelimmaUtl.currentPD.currentO3Level+";"+
								PrisonnerDelimmaUtl.currentPD.currentAQ);

						int t= SimulationFramework.getCurrentEnv().T;
						if(t<SimulationFramework.getCurrentEnv().getMax_T()){
							SimulationFramework.getCurrentEnv().T=t+1;
						}
						}*/
			}
		}
	}

	private void ReportProgressCentralized() {
		if(SimulationFramework.first==0){
			Console.logMSG(
			"T;PM10;SOX;COX;NOX;O3;AIRQ");
			SimulationFramework.first=1;
		}
		else {
			Console.logMSG(
					SimulationFramework.getCurrentEnv().T+
					";"+
					SimulationFramework.getCurrentEnv().sim_PM10.getData().get(SimulationFramework.getCurrentEnv().T)+
					";" +
					SimulationFramework.getCurrentEnv().sim_SOx.getData().get(SimulationFramework.getCurrentEnv().T) +
					";" +
					SimulationFramework.getCurrentEnv().sim_COx.getData().get(SimulationFramework.getCurrentEnv().T) +
					";" +
					SimulationFramework.getCurrentEnv().sim_NOx.getData().get(SimulationFramework.getCurrentEnv().T) +
					";" +
					SimulationFramework.getCurrentEnv().sim_O3.getData().get(SimulationFramework.getCurrentEnv().T)+
					";"+
					SimulationFramework.getCurrentEnv().AirQuality_V.getData().get(SimulationFramework.getCurrentEnv().T));

			Console.logTab(new String[]{
					SimulationFramework.getCurrentEnv().T+"",

					SimulationFramework.getCurrentEnv().sim_PM10.getData().get(SimulationFramework.getCurrentEnv().T)+"",
					SimulationFramework.getCurrentEnv().sim_SOx.getData().get(SimulationFramework.getCurrentEnv().T)+"",
					SimulationFramework.getCurrentEnv().sim_COx.getData().get(SimulationFramework.getCurrentEnv().T)+"",
					SimulationFramework.getCurrentEnv().sim_NOx.getData().get(SimulationFramework.getCurrentEnv().T)+"",
					SimulationFramework.getCurrentEnv().sim_O3.getData().get(SimulationFramework.getCurrentEnv().T)+"",
					SimulationFramework.getCurrentEnv().AirQuality_V.getData().get(SimulationFramework.getCurrentEnv().T)+""});
		}
		int t= SimulationFramework.getCurrentEnv().T;
		if(t<SimulationFramework.getCurrentEnv().getMax_T()){
			SimulationFramework.getCurrentEnv().T=t+1;
		}
	}
	private  void ReportProgress() {

		if(PrisonnerDelimmaUtl.showHead){
			PrisonnerDelimmaUtl.showHead=false;
			lastTime= SimulationFramework.getCurrentEnv().T;
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
			//if(lastTime!=SimulationFramework.getCurrentEnv().T){
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
			lastTime=SimulationFramework.getCurrentEnv().T;
	
			//}
		}

	}
	}