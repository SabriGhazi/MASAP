package com.masim.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.jfree.data.time.Hour;
import org.jfree.ui.RefineryUtilities;

import weka.classifiers.Classifier;
import weka.classifiers.functions.RBFNetwork;
import weka.core.Instances;

import com.masim.core.MachineLearningUtls;
import com.masim.core.PredictionModel;
import com.masim.ui.ChartFactoryUTL;
import com.masim.ui.Console;

/**
 * This class contains information about the environment. 
 * @author sabri ghazi
 */
public class Environment {

	public DispersionEngine dispersionEngine;
	
	public ArrayList<CBox> listOfBox;
	int numberOfBox;
	int height;
	int width;
	int max_T;
	//the number of hour used for prediction eg. 4 hour ahead prediction.
	public int numberOfStepAhead;
	public int T;
	public DataItem PM10;
	
	public DataItem COx;
	public DataItem SOx;
	public DataItem O3;
	public DataItem NOx;
	
	public DataItem temp;
	public DataItem hu;
	public DataItem ws;
	DataItem rf;
	
	public DataItem PM10_V;
	
	public DataItem COx_V;
	public DataItem SOx_V;
	public DataItem O3_V;
	public DataItem NOx_V;
	
	public DataItem temp_V;
	public DataItem hu_V;
	public DataItem ws_V;
	DataItem rf_V;
	
	public ArrayList<Hour> time;
	public DataItem AirQuality_V;

	public DataItem sim_PM10;
	public DataItem sim_COx;
	public DataItem sim_SOx;
	public DataItem sim_O3;
	public DataItem sim_NOx;
	
	
	public Classifier ClassificationModel;
	public Instances dataSet;
	public Instances dataSetTest;
	
	public PredictionModel pm10MODEL;
	public PredictionModel COxMODEL;
	public PredictionModel SOxMODEL;
	public PredictionModel O3MODEL;
	public PredictionModel NOxMODEL;
	
	public PredictionModel AirQuality;
	
	public String DATA_PATH="";
	
	public void intitEnv(int areaH,int areaL, int P_Max_T, int nbrOfStepAhead){
		this.height=areaH;
		this.width=areaL;
		numberOfStepAhead=nbrOfStepAhead;
		
		 numberOfBox=(areaH*areaL)/100;
		 //max_T=P_Max_T;
		 T=0;
		MachineLearningUtls.NUMBER_OF_STEP_IN_ADVANCE=numberOfStepAhead;
		 listOfBox= new ArrayList<CBox>();
		 AirQuality_V= new DataItem();
		 AirQuality_V.ItemName="Air Quality";
		 AirQuality_V.addData(2);
		 AirQuality_V.addData(1);
		 time=DataLoader.LoadTime(max_T+1);
		 BuildBox();
	}

	/**
	 * Build the Box representing the environment.
	 * 
	 */
	private void BuildBox() {
		for (int j=0;j<height;j++){
			for(int i=0;i<width;i++){
				listOfBox.add(new CBox(j,i));
			}
		}
	}

	

	/**
	 * Load data used for training and simulation.
	 * Data are csv files placed in DATA_PATH.
	 */
	public void loadData() {
		 PM10= new DataItem();
		 PM10.setData(DataLoader.load(DATA_PATH+"PM10_2003.csv"));
		 PM10.setItemMaxVal(DataLoader.maxVal);
		 PM10.setItemMinVal(DataLoader.minVal);
		 PM10.setItemName("PM10");
		 PM10.setItemUnit("µ grm /m ^3 ");
		 
		 PM10_V= new DataItem();
		 PM10_V.setData(DataLoader.load(DATA_PATH+"PM10_2004.csv"));
		 PM10_V.setItemMaxVal(DataLoader.maxVal);
		 PM10_V.setItemMinVal(DataLoader.minVal);
		 PM10_V.setItemName("PM10");
		 PM10_V.setItemUnit("µ grm /m ^3 ");
		 
		 
		 COx=new DataItem();
		 COx.setData(DataLoader.load(DATA_PATH+"CO_2003.csv"));
		 COx.setItemMaxVal(DataLoader.maxVal);
		 COx.setItemMinVal(DataLoader.minVal);
		 COx.setItemName("COx");
		 COx.setItemUnit("µ grm /m ^3 ");
		 
		 SOx=new DataItem();
		 SOx.setData(DataLoader.load(DATA_PATH+"SO2_2003.csv"));
		 SOx.setItemMaxVal(DataLoader.maxVal);
		 SOx.setItemMinVal(DataLoader.minVal);
		 SOx.setItemName("SOx");
		 SOx.setItemUnit("µ grm /m ^3 ");
		 
		 O3=new DataItem();
		 O3.setData(DataLoader.load(DATA_PATH+"O3_2004.csv"));
		 O3.setItemMaxVal(DataLoader.maxVal);
		 O3.setItemMinVal(DataLoader.minVal);
		 O3.setItemName("O3");
		 O3.setItemUnit("µ grm /m ^3 ");
		 
		 NOx=new DataItem();
		 NOx.setData(DataLoader.load(DATA_PATH+"NX_2003.csv"));
		 NOx.setItemMaxVal(DataLoader.maxVal);
		 NOx.setItemMinVal(DataLoader.minVal);
		 NOx.setItemName("NOx");
		 NOx.setItemUnit("µ grm /m ^3 ");
		 
		 NOx_V=new DataItem();
		 NOx_V.setData(DataLoader.load(DATA_PATH+"NX_2004.csv"));
		 NOx_V.setItemMaxVal(DataLoader.maxVal);
		 NOx_V.setItemMinVal(DataLoader.minVal);
		 NOx_V.setItemName("NOx");
		 NOx_V.setItemUnit("µ grm /m ^3 ");
		 
		 temp=new DataItem();
		 temp.setData(DataLoader.load(DATA_PATH+"TEMP_2003.csv"));
		 temp.setItemMaxVal(DataLoader.maxVal);
		 temp.setItemMinVal(DataLoader.minVal);
		 temp.setItemName("Temperature");
		 temp.setItemUnit("°");
		 
		 temp_V=new DataItem();
		 temp_V.setData(DataLoader.load(DATA_PATH+"TEMP_2004.csv"));
		 temp_V.setItemMaxVal(DataLoader.maxVal);
		 temp_V.setItemMinVal(DataLoader.minVal);
		 temp_V.setItemName("Temperature");
		 temp_V.setItemUnit("°");
		 
		 hu= new DataItem();
		 hu.setData(DataLoader.load(DATA_PATH+"HU_2003.csv"));
		 hu.setItemMaxVal(DataLoader.maxVal);
		 hu.setItemMinVal(DataLoader.minVal);
		 hu.setItemName("Humidity");
		 hu.setItemUnit("%");
		 
		 hu_V= new DataItem();
		 hu_V.setData(DataLoader.load(DATA_PATH+"HU_2004.csv"));
		 hu_V.setItemMaxVal(DataLoader.maxVal);
		 hu_V.setItemMinVal(DataLoader.minVal);
		 hu_V.setItemName("Humidity");
		 hu_V.setItemUnit("%");
		 
		 ws=  new DataItem();
		 ws.setData(DataLoader.load(DATA_PATH+"WS_2003.csv"));
		 ws.setItemMaxVal(DataLoader.maxVal);
		 ws.setItemMinVal(DataLoader.minVal);
		 ws.setItemName("Wind Speed");
		 ws.setItemUnit("m/s ");
		 
		 ws_V=  new DataItem();
		 ws_V.setData(DataLoader.load(DATA_PATH+"WS_2004.csv"));
		 ws_V.setItemMaxVal(DataLoader.maxVal);
		 ws_V.setItemMinVal(DataLoader.minVal);
		 ws_V.setItemName("Wind Speed");
		 ws_V.setItemUnit("m/s ");
		 
		 rf=  new DataItem();
		 rf.setItemName("Rainfall");
		 rf.setItemUnit("mm^3");
		 rf.setData(DataLoader.load(DATA_PATH+"RAINFALL-2004.csv"));
		 rf.setItemMaxVal(DataLoader.maxVal);
		 rf.setItemMinVal(DataLoader.minVal);
		 
		 
		  sim_PM10 = new DataItem();
		  sim_PM10.setItemName("Predicted PM10");
		  sim_PM10.setItemUnit("µ gramme/m^3");
			 
		  sim_COx= new DataItem();
		  sim_COx.setItemName("Predicted COx");
		  sim_COx.setItemUnit("µ gramme/m^3");
		  
		  sim_SOx= new DataItem();
		  sim_SOx.setItemName("Predicted sim_SOx");
		  sim_SOx.setItemUnit("µ gramme/m^3");
		  
	      sim_O3= new DataItem();
	      sim_O3.setItemName("Predicted sim_O3");
	      sim_O3.setItemUnit("µ gramme/m^3");
		  
	      sim_NOx= new DataItem();
		  
	      MachineLearningUtls.loadModels();
		 showResume();
	}

	private void showResume() {
		StringBuilder str= new StringBuilder();
		 str.append("Environment initialization started...\n");
		 str.append("Number of box :"+numberOfBox+"\n");
		 str.append("Number of Simulation Step MAX_T ="+max_T+"\n");
		 str.append("Prediction is about ="+numberOfStepAhead+" hours in advance\n");
		 str.append("Pollutant\t|Size\t|Max\t|Min\t|Unit\n");
		 str.append(PM10.toString());
		 str.append(PM10_V.toString());
		 str.append(COx.toString());
		 str.append(SOx.toString());
		 str.append(O3.toString());
		 str.append(NOx.toString());
		 str.append("----------------------------------------\n");
		 str.append("Climatic parameters: \n");
		 str.append("Parameter\t|Size\t|Max\t|Min\t|Unit\n");
		 str.append(temp.toString());
		 str.append(temp_V.toString());
		 str.append(hu.toString());
		 str.append(hu_V.toString());
		 str.append(ws.toString());
		 str.append(ws_V.toString());
		 str.append(rf.toString());
		 str.append("-----------------------------------------\n")
		 .append("Current values of climatic parameters :\n")
		 .append("\t Temperature T    :"+temp.getData().get(0)+" °\n")
		 .append("\t Humidity Hu      :"+hu.getData().get(0)+" %\n")
		 .append("\t Wind Speed WS    :"+ws.getData().get(0)+" m/s\n")
		 .append("Current values of Polluants :\n")
		 .append("\t PM10   :"+PM10.getData().get(0)+"  µ gramme/m^3\n")
		 .append("\t SOx    :"+SOx.getData().get(0)+"   µ gramme/m^3\n")
		 .append("\t COx    :"+COx.getData().get(0)+"   µ gramme/m^3\n")
		 .append("\t O3     :"+O3.getData().get(0)+ "   µ gramme/m^3\n")
		 .append("\t NOx    :"+NOx.getData().get(0)+"   µ gramme/m^3\n");
		 EnvironmentState es= new EnvironmentState();
		 
		 es.PM10=PM10.getData().get(0);
		 es.SOx=SOx.getData().get(0);
		 es.COx=COx.getData().get(0);
		 es.NOx=NOx.getData().get(0);
		 es.O3=O3.getData().get(0);
		 
		 double currentAQINDICES=AirQualityUTL.getAirQuality(es);
		 String currentAQDescription=MachineLearningUtls.getDescription(currentAQINDICES);
		 str.append("\n----------------------------------------------------------------------");
		 str.append("\n Current Air Quality Indices: "+currentAQINDICES+" ("+currentAQDescription+")");
		 str.append("\n----------------------------------------------------------------------");
		 Console.frmLogMSG(str.toString());
		 
	}
	
	public boolean start(){
		double t=0;
		while (t<max_T){
			t=t+1;
		}
		
		return true;
	}

	public ArrayList<CBox> getListOfBox() {
		return listOfBox;
	}

	public void setListOfBox(ArrayList<CBox> listOfBox) {
		this.listOfBox = listOfBox;
	}

	public int getNumberOfBox() {
		return numberOfBox;
	}

	public void setNumberOfBox(int numberOfBox) {
		this.numberOfBox = numberOfBox;
	}

	public int getMax_T() {
		return max_T;
	}

	public void setMax_T(int max_T) {
		this.max_T = max_T;
	}

	public DataItem getPM10() {
		return PM10;
	}

	public void setPM10(DataItem pM10) {
		PM10 = pM10;
	}

	public DataItem getCOx() {
		return COx;
	}

	public void setCOx(DataItem cOx) {
		COx = cOx;
	}

	public DataItem getSOx() {
		return SOx;
	}

	public void setSOx(DataItem sOx) {
		SOx = sOx;
	}

	public DataItem getO3() {
		return O3;
	}

	public void setO3(DataItem o3) {
		O3 = o3;
	}

	public DataItem getNOx() {
		return NOx;
	}

	public void setNOx(DataItem nOx) {
		NOx = nOx;
	}

	public DataItem getTemp() {
		return temp;
	}

	public void setTemp(DataItem temp) {
		this.temp = temp;
	}

	public DataItem getHu() {
		return hu;
	}

	public void setHu(DataItem hu) {
		this.hu = hu;
	}

	public DataItem getWs() {
		return ws;
	}

	public void setWs(DataItem ws) {
		this.ws = ws;
	}

	public DataItem getRf() {
		return rf;
	}

	public void setRf(DataItem rf) {
		this.rf = rf;
	}


	public DataItem getSim_PM10() {
		return sim_PM10;
	}

	public void setSim_PM10(DataItem sim_PM10) {
		this.sim_PM10 = sim_PM10;
	}

	public DataItem getSim_COx() {
		return sim_COx;
	}

	public void setSim_COx(DataItem sim_COx) {
		this.sim_COx = sim_COx;
	}

	public DataItem getSim_SOx() {
		return sim_SOx;
	}

	public void setSim_SOx(DataItem sim_SOx) {
		this.sim_SOx = sim_SOx;
	}

	public DataItem getSim_O3() {
		return sim_O3;
	}

	public void setSim_O3(DataItem sim_O3) {
		this.sim_O3 = sim_O3;
	}

	public DataItem getSim_NOx() {
		return sim_NOx;
	}

	public void setSim_NOx(DataItem sim_NOx) {
		this.sim_NOx = sim_NOx;
	}
}
