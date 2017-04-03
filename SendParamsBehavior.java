package com.masim.meteoAgents;

import java.io.IOException;

import com.masim.core.MeasureAgent;
import com.masim.core.ParamInfo;
import com.masim.ui.Console;
import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SendParamsBehavior extends TickerBehaviour {

	Agent _agnt;
	double currentVal=0;
	protected SendParamsBehavior(Agent agnt){
		super(agnt,SimulationFramework.SIMSPEED);
		_agnt=agnt;
	}
	
	@Override
	protected void onTick() {
		String name="";
		if(SimulationFramework.getCurrentSimProcessState()== SimProcessState.Started){
			/*ACLMessage runStep=_agnt.receive();
			if(runStep!=null){*/
			MeasureAgent msa=(MeasureAgent)_agnt;
		if( msa.getMeasuredParam().getName().equalsIgnoreCase("Hu")){
			int t=SimulationFramework.getCurrentEnv().T;
			currentVal=SimulationFramework.getCurrentEnv().hu_V.getData().get(t);
			name="Hu";
			                                                    }
		if( msa.getMeasuredParam().getName().equalsIgnoreCase("Temp")){
			int t=SimulationFramework.getCurrentEnv().T;
			currentVal=SimulationFramework.getCurrentEnv().temp_V.getData().get(t);
			name="Temp";
			                                                    }
		if( msa.getMeasuredParam().getName().equalsIgnoreCase("WS")){
			int t=SimulationFramework.getCurrentEnv().T;
			currentVal=SimulationFramework.getCurrentEnv().ws_V.getData().get(t);
			name="ws";
			            }
	
		ParamInfo prmInfo= new ParamInfo();
		prmInfo.setValue(currentVal);
		prmInfo.setName(name);
	
		ACLMessage predictionMsg= new ACLMessage(ACLMessage.INFORM);
		try {
			predictionMsg.setContentObject(prmInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		predictionMsg.addReceiver(new AID("PM10",AID.ISLOCALNAME));
		_agnt.send(predictionMsg);
		ACLMessage reply=_agnt.receive();
		if(reply!=null){
			//Console.logMSG(_agnt.getName()+" : <CONFIRMATION>");
		}
		//}
		}
		
	}

}
