/**
 * 
 */
package com.masim.core;

import jade.content.onto.Introspector;
import jade.content.onto.Ontology;

/**
 * @author Sabri Ghazi
 *
 */
@SuppressWarnings("serial")
public class AirPollutionOnto extends Ontology {

	
	public static String ONTOLOGY_NAME="AirPollutionOnto";
	
	
	//Vocabulary
	public static String PREDICTION="Prediction";
	public static String PARAMETER="Parameter";
	
	public static String POLLUTANT="Pollutant";
	public static String POLLUTANT_CONCENTRATION="Concentration";
	public static String POLLUTANT_NAME="NAME";
	public static String POLLUTANT_UNIT="UNIT";
	public static String POLLUTANT_DESTRUCTION="DESTRUCTION";
	
	public static String SOURCE="SOURCE";
	public static String SOURCE_TYPE="TYPE";
	public static String SOURCE_RATE="RATE";
	
	
	/**
	 * @param name
	 * @param base
	 */
	public AirPollutionOnto(String name, Ontology base) {
		super(name, base);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param introspector
	 */
	public AirPollutionOnto(String name, Introspector introspector) {
		super(name, introspector);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param base
	 * @param introspector
	 */
	public AirPollutionOnto(String name, Ontology base,
			Introspector introspector) {
		super(name, base, introspector);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param base
	 * @param introspector
	 */
	public AirPollutionOnto(String name, Ontology[] base,
			Introspector introspector) {
		super(name, base, introspector);
		// TODO Auto-generated constructor stub
	}

}
