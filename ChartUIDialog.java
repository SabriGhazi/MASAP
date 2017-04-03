package com.masim.ui;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

public class ChartUIDialog extends JDialog {

	public static ChartUIDialog ChartUIDialog(ChartItem citem){
		
		ChartUIDialog cuidiag= new ChartUIDialog();
        JPanel panel = new ChartPanel(citem.chart);
        panel.setPreferredSize(new java.awt.Dimension(1000, 800));
        cuidiag.setContentPane(panel);
        return cuidiag; 
	}
	
}
