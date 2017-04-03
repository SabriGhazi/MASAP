package com.masim.utils;

public class CBox {

	double PM10_concentration;
	double O3_concentration;
	double NO_concentration;
	double CO_concentration;
	double SOx_concentration;
	
	int j;
	int i;
	
	public double getSOx_concentration() {
		return SOx_concentration;
	}
	public void setSOx_concentration(double sOx_concentration) {
		SOx_concentration = sOx_concentration;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	/**
	 * @return the pM10_concentration
	 */
	public double getPM10_concentration() {
		return PM10_concentration;
	}
	/**
	 * @param pM10_concentration the pM10_concentration to set
	 */
	public void setPM10_concentration(double pM10_concentration) {
		PM10_concentration = pM10_concentration;
	}
	/**
	 * @return the o3_concentration
	 */
	public double getO3_concentration() {
		return O3_concentration;
	}
	/**
	 * @param o3_concentration the o3_concentration to set
	 */
	public void setO3_concentration(double o3_concentration) {
		O3_concentration = o3_concentration;
	}
	/**
	 * @return the nO_concentration
	 */
	public double getNO_concentration() {
		return NO_concentration;
	}
	/**
	 * @param nO_concentration the nO_concentration to set
	 */
	public void setNO_concentration(double nO_concentration) {
		NO_concentration = nO_concentration;
	}
	/**
	 * @return the cO_concentration
	 */
	public double getCO_concentration() {
		return CO_concentration;
	}
	/**
	 * @param cO_concentration the cO_concentration to set
	 */
	public void setCO_concentration(double cO_concentration) {
		CO_concentration = cO_concentration;
	}

	public void polluate(double i) {
		PM10_concentration=PM10_concentration+i;
	}
	/**
	 * @param j
	 * @param i
	 */
	public CBox(int j, int i) {
		super();
		this.j = j;
		this.i = i;
	}
}
