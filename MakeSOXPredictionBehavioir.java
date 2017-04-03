package com.masim.predictionAgents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import com.masim.core.MachineLearningUtls;
import com.masim.core.ParamInfo;
import com.masim.ui.Console;
import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;

public class MakeSOXPredictionBehavioir extends TickerBehaviour{

	Agent _agnt;
	int nbrOfMsg=0;
	double hu;
	double temp;
	double ws;


	MakeSOXPredictionBehavioir(Agent agnt){
		super(agnt,10);
		_agnt=agnt;
	}
	@Override
	protected void onTick() {
		if(SimulationFramework.getCurrentSimProcessState()== SimProcessState.Started){
			ACLMessage paramsInfoMsg=_agnt.receive();
			if(paramsInfoMsg!=null){
				ACLMessage reply= new ACLMessage(ACLMessage.INFORM);
				reply.setContent("Confirms");
				reply.addReceiver(paramsInfoMsg.getSender());
				_agnt.send(reply);
				String msg;
				try {
					ParamInfo pi= ((ParamInfo) paramsInfoMsg.getContentObject());
					if(pi.getName().equalsIgnoreCase("Hu"))
						hu=pi.getValue();
					else if(pi.getName().equalsIgnoreCase("Temp"))
						temp=pi.getValue();
					else ws=pi.getValue();

					msg = _agnt.getName()+
					": <ParamRecived> :"+
					paramsInfoMsg.getSender().getName()+" :"
					+pi.getValue();
					Console.logMSG(msg);

					nbrOfMsg++;
					//.add(((ParamInfo)paramsInfoMsg.getContentObject()).getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}

				if(nbrOfMsg==3){
					nbrOfMsg=0;
					int t= SimulationFramework.getCurrentEnv().T;

					double currentValOfPM10=SimulationFramework.getCurrentEnv().SOx.getData().get(t);
					double[] input={hu,temp,ws,currentValOfPM10};
					double d=MachineLearningUtls.interrogateModel(
							SimulationFramework.getCurrentEnv().SOxMODEL, 
							input);

					SimulationFramework.getCurrentEnv().T=SimulationFramework.getCurrentEnv().T+1;
					SimulationFramework.getCurrentEnv().sim_PM10.addData(d);
					msg="\n"+_agnt.getName()+" : <SOX Concentration is> : "+d;
					msg=" ["+ msg+hu+" | "+temp+" | "+ws+"]";
				//	Console.logMSG(msg);

				}
			}
		}
	}
}
