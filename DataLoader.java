package com.masim.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jfree.data.time.Hour;

import com.masim.ui.Console;

/**
 * This class is used to load data from CSV files,
 * after loading a file, maxVal and minVal contain, respectively, the maximum and minimum values.
 * @author Sabri Ghazi
 */
public  class DataLoader {

	public static double maxVal=0;
	public static double minVal=0;

	public static ArrayList<Double> load(String fileName){

		ArrayList<Double> data=new ArrayList<Double>();

		FileInputStream f;
		maxVal=Double.MIN_VALUE;
		minVal=Double.MAX_VALUE;
		try {
			f = new FileInputStream(fileName);
			double d;

			DataInputStream dis= new DataInputStream(f);
			while (dis.available()!=0){
				d=Double.parseDouble(dis.readLine());
				if(fileName.contains(("PM10"))){
					d=d*600;
					data.add(d);
				}
				else data.add(d);
				if(d>maxVal)maxVal=d;
				if(d<minVal)minVal=d;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Console.frmLogMSG(e.getMessage());
		}
		return data;
	}

	/**
	 * Create an ArrayList of MAX_T Hour.
	 * @param MAX_T
	 * @return ArrayList<Hour>
	 */
	public synchronized static  ArrayList<Hour>LoadTime(int MAX_T){
		ArrayList<Hour> dt= new ArrayList<Hour>(MAX_T);
		int h=0;
		int d=1;
		int m=1;
		int y=2004;
		int i;
		for( i=0;i<MAX_T+1;i++){
			dt.add(	new Hour(h,d,m,2004));
			
			if(h<23)h++;
			else { 
				h=0;
				if(d<29)d++;
				else {
					d=1;
					if(m<11)
						m++;
					else    {m=1;
					y++;}
				}
			}
		}
		return dt;
	}
}
