package com.masim.emissionAgents;

import com.masim.ui.Console;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.*;
/**
 * The tickerbehavior of receiving an request for reducing the emission rate.
 * @author Sabri Ghazi
 */
public class ReceiveASKforERBehaviour extends TickerBehaviour {
   Agent _agnt;
	public ReceiveASKforERBehaviour(Agent a, long period) {
		super(a, period);
		_agnt=a;
	}

	@Override
	protected void onTick() {
		ACLMessage amsg=_agnt.receive();
		if(amsg!=null){
				String msg=amsg.getContent();
				if(msg.equalsIgnoreCase("ASK-FOR-ER")){
					ACLMessage reply= amsg.createReply();
					SourceConfigInfo sourceCFG=SimulationFramework.sourcesInfo.get(_agnt.getLocalName());
					reply.setContent(sourceCFG.getEmisionRate()+"");
	//				Console.logMSG(sourceCFG.getSourceName()+" "+"ASKED to Reduce :"+msg);
		//			_agnt.send(reply);
				}
				else 
					//if(msg.equalsIgnoreCase("ASK-REDUCE-ER"))
					{
					double val=Double.parseDouble(msg);
					((SourceConfigInfo)SimulationFramework.sourcesInfo.get(_agnt.getLocalName())).reduceEmissionBy(val);
				}
		}
	}
}
