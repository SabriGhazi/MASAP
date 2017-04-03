package com.masim.utils;

public class AirQualityUTL {

	/*
	 aq={
    	SO2      NO2     O3      PM10     Indices 	Appreciation
    	0–30     0–45    0–45    0–20     1 		Very Good
    	30–60    45–80   45–80   20–40 	  2 		Good
    	60–125   80–200  80–150  40–100   3 		Average
    	125–250  200–400 150–270 100–200  4 		Bad
    	>250     >400    >270    >200     5 		Very Bad}
	 */
	public static  double getAirQuality(EnvironmentState es){
		
		if((es.getPM10()>250) || 
				(es.getNOx()>400)||
				(es.getO3()>207)||
				(es.getPM10()>200))
			return 5;
		else if(
				((es.getSOx()<250)&&(es.getSOx()>125))||
				((es.getNOx()<400)&&(es.getNOx()>200))||
				((es.getO3()<270)&&(es.getO3()>150))||
				((es.getPM10()<200)&&(es.getO3()>100))) return 4;
		else if(
				((es.getSOx()<125)&&(es.getSOx()>60))||
				((es.getNOx()<200)&&(es.getNOx()>80))||
				((es.getO3()<150)&&(es.getO3()>80))||
				((es.getPM10()<100)&&(es.getO3()>40))) return 3;
		else if(
				((es.getSOx()<60)&&(es.getSOx()>30))||
				((es.getNOx()<80)&&(es.getNOx()>45))||
				((es.getO3()<80)&&(es.getO3()>45))||
				((es.getPM10()<40)&&(es.getO3()>20))) return 2;	
		else if(
				((es.getSOx()<30)&&(es.getSOx()>0))||
				((es.getNOx()<45)&&(es.getNOx()>0))||
				((es.getO3()<45)&&(es.getO3()>0))||
				((es.getPM10()<20)&&(es.getO3()>0))) return 1;
		return 0;
	}
}
