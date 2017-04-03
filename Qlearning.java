package com.masim.qlearning;

import java.util.Random;

import com.masim.emissionAgents.EmissionUnit;

public class Qlearning {

	static double p = -2;
	static double r = 3;
	static double na = -1;

		public static final int Q_SIZE = 5;
	    public static final double GAMMA = 0.8;
	    public static final int ITERATIONS = 30;
	    public static final int INITIAL_STATES[] = new int[] {1, 3, 2, 4, 0};
	
	public static double[][] R = { { p, r, na, na, na }, 
								{ p, p, r, na, na },
								{ na, p, p, r, na }, 
								{ na, na, p, p, r }, 
								{ na, na, na, p, r } };
  
	
	public static void train(EmissionUnit qla)
	    {
	       // initialize();
		 System.out.println(qla.getLocalName()+" :Debut");
	        // Perform training, starting at all initial states.
	        for(int j = 0; j < ITERATIONS; j++)
	        {
	            for(int i = 0; i < Q_SIZE; i++)
	            {
	                episode(INITIAL_STATES[i],qla);
	            } // i
	        } // j
	        System.out.println(qla.getLocalName()+" :Terminer");
	    }
	 private static void episode(final int initialState, EmissionUnit qla)
	    {
		 	qla.currentState = initialState;

	        // Travel from state to state until goal state is reached.
	        do
	        {
	            chooseAnAction(qla);
	        }while(qla.currentState == 4);

	        // When currentState = 5, Run through the set once more for convergence.
	        for(int i = 0; i < Q_SIZE; i++)
	        {
	            chooseAnAction(qla);
	        }
	        return;
	    }
	 private static void chooseAnAction(EmissionUnit qla)
	    {
	        int possibleAction = 0;

	        // Randomly choose a possible action connected to the current state.
	        possibleAction = getRandomAction(Q_SIZE,qla);

	        if(R[qla.currentState][possibleAction] >= 0){
	        	qla.q[qla.currentState][possibleAction] = reward(possibleAction,qla);
	        	qla.currentState = possibleAction;
	        }
	        return;
	    }
	 
	 private static int reward(final int Action, EmissionUnit qla)
	    {
	        return (int)(R[qla.currentState][Action] + (GAMMA * maximum(Action, false,qla)));
	    }
	 
	 private static int maximum(final int State, final boolean ReturnIndexOnly, EmissionUnit qla)
	    {
	        // If ReturnIndexOnly = True, the Q matrix index is returned.
	        // If ReturnIndexOnly = False, the Q matrix value is returned.
	        int winner = 0;
	        boolean foundNewWinner = false;
	        boolean done = false;

	        while(!done)
	        {
	            foundNewWinner = false;
	            for(int i = 0; i < Q_SIZE; i++)
	            {
	                if(i != winner){             // Avoid self-comparison.
	                    if(qla.q[State][i] > qla.q[State][winner]){
	                        winner = i;
	                        foundNewWinner = true;
	                    }
	                }
	            }

	            if(foundNewWinner == false){
	                done = true;
	            }
	        }

	        if(ReturnIndexOnly == true){
	            return winner;
	        }else{
	            return qla.q[State][winner];
	        }
	    }
	 
	  private static int getRandomAction(final int upperBound, EmissionUnit qla)
	    {
	        int action = 0;
	        boolean choiceIsValid = false;

	        // Randomly choose a possible action connected to the current state.
	        while(choiceIsValid == false)
	        {
	            // Get a random value between 0(inclusive) and 6(exclusive).
	            action = new Random().nextInt(upperBound);
	            if(R[qla.currentState][action] > -1){
	                choiceIsValid = true;
	            }
	        }

	        return action;
	    }
}
