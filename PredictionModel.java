package com.masim.core;

import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizedField;

public class PredictionModel {

	String modelName;
	BasicNetwork nnModel;
	double perfermanceRMSE;
	double performanceIA;
	
	String PollutantName;
	
	NormalizedField normalizedPollutant;
	NormalizedField normalizedHu;
	NormalizedField normalizedTEMP;
	NormalizedField normalizedWS;
	
	public NormalizedField normalizedUnified;
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public BasicNetwork getNnModel() {
		return nnModel;
	}
	public void setNnModel(BasicNetwork nnModel) {
		this.nnModel = nnModel;
	}
	public double getPerfermanceRMSE() {
		return perfermanceRMSE;
	}
	public void setPerfermanceRMSE(double perfermanceRMSE) {
		this.perfermanceRMSE = perfermanceRMSE;
	}
	public double getPerformanceIA() {
		return performanceIA;
	}
	public void setPerformanceIA(double performanceIA) {
		this.performanceIA = performanceIA;
	}
	public String getPollutantName() {
		return PollutantName;
	}
	public void setPollutantName(String pollutantName) {
		PollutantName = pollutantName;
	}
	public NormalizedField getNormalizedPollutant() {
		return normalizedPollutant;
	}
	public void setNormalizedPollutant(NormalizedField normalizedPollutant) {
		this.normalizedPollutant = normalizedPollutant;
	}
	public NormalizedField getNormalizedHu() {
		return normalizedHu;
	}
	public void setNormalizedHu(NormalizedField normalizedHu) {
		this.normalizedHu = normalizedHu;
	}
	public NormalizedField getNormalizedTEMP() {
		return normalizedTEMP;
	}
	public void setNormalizedTEMP(NormalizedField normalizedTEMP) {
		this.normalizedTEMP = normalizedTEMP;
	}
	public NormalizedField getNormalizedWS() {
		return normalizedWS;
	}
	public void setNormalizedWS(NormalizedField normalizedWS) {
		this.normalizedWS = normalizedWS;
	}
	
	
}
