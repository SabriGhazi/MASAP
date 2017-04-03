package com.masim.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.plot.CombinedXYPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.util.Calendar;
import java.util.GregorianCalendar;
import com.masim.utils.DataItem;
import com.masim.utils.SimulationFramework;

/**
 * A demonstration application showing a {@link CombinedXYPlot} with
 * two sub-plots.
 */
public class ChartFactoryUTL{

	public static JFreeChart chart; 
	
    /**
     * Constructs a new JDialog with chart using the data set l.
     *
     * @param title  the frame title.
     */
    public static ChartItem ChartFactory(String title,List<DataItem> l, boolean sameGraph) {
        
        JFreeChart chart;
        TimeSeriesCollection  dataset=null;
        if(!sameGraph){
        	chart= createCombinedChart(l);
        }else{ 
        	 dataset=ConvertToTimeSeriesCollection(l);
        	 chart=createMultipleChart(dataset);
        
        }
        ChartItem chartitm= new ChartItem();
        
        chartitm.chart=chart;
        chartitm.dataset=dataset;
        return chartitm;
    }

    /**
     * Creates an overlaid chart.
     *
     * @return The chart.
     */
    private static JFreeChart createCombinedChart(List<DataItem> ld) {
    	
    	DateAxis domainAxis = new DateAxis("Time");
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.02);
        ValueAxis rangeAxis = new NumberAxis(ld.get(0).getItemUnit());
        CombinedXYPlot cplot = new CombinedXYPlot(domainAxis, rangeAxis);
        
    	for(int i=0;i<ld.size();i++){
        // create plot ... 
        TimeSeries serie = ArrayListToXYseries(ld.get(i));
        IntervalXYDataset data1= new TimeSeriesCollection(serie);
        XYItemRenderer renderer1 = new XYLineAndShapeRenderer(true, false);
        
        renderer1.setBaseToolTipGenerator(
        		new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("d-MMM-yyyy"), 
                new DecimalFormat("0.00")));
        
        renderer1.setSeriesStroke(0, 
        		new BasicStroke(
        				1.0f,
                BasicStroke.CAP_BUTT, 
                BasicStroke.JOIN_BEVEL));
        
        renderer1.setSeriesPaint(0, Color.blue);
      
       
        XYPlot plot1 = new XYPlot(data1, null, rangeAxis, renderer1);
        plot1.setBackgroundPaint(Color.lightGray);
        plot1.setDomainGridlinePaint(Color.white);
        plot1.setRangeGridlinePaint(Color.white);
                
        cplot.add(plot1, 3);
        cplot.setGap(8.0);
        cplot.setDomainGridlinePaint(Color.white);
        cplot.setDomainGridlinesVisible(true);
    	}
  
        // return a new chart containing the overlaid plot...
        JFreeChart chart = new JFreeChart("Air Pollution simulation",
                JFreeChart.DEFAULT_TITLE_FONT, cplot, false);
        chart.setBackgroundPaint(Color.white);
        LegendTitle legend = new LegendTitle(cplot);
        chart.addSubtitle(legend);
        return chart; 
    }
   public static TimeSeriesCollection  ConvertToTimeSeriesCollection(List<DataItem> ld){
    
    TimeSeriesCollection   	dataset = new TimeSeriesCollection();
	
	for(int i=0;i<ld.size();i++){
		dataset.addSeries(ArrayListToXYseries(ld.get(i)));
	}
	return dataset;
    
    }
    /**
     * Creates an overlaid chart.
     *
     * @return The chart.
     */
    private static JFreeChart createMultipleChart(TimeSeriesCollection dataset) {
    	
    	 
        XYItemRenderer renderer1 = new XYLineAndShapeRenderer(true, true);
        renderer1.setBaseToolTipGenerator(
        		new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("d-MMM-yyyy"), 
                new DecimalFormat("0.00")));
        
        renderer1.setSeriesStroke(0, 
        		new BasicStroke(1.0f,
        				BasicStroke.CAP_ROUND, 
        				BasicStroke.JOIN_ROUND));
        
     
        renderer1.setSeriesPaint(0, Color.RED);
       
        // return a new chart containing the overlaid plot...
        JFreeChart chart = 
        	ChartFactory.createTimeSeriesChart(
        		"Predicted and Measured values", // Title
        		"Time", // x-axis Label
        		"micro-gramme / m^3", // y-axis Label
        		dataset, // Dataset
        	//	PlotOrientation.VERTICAL, // Plot Orientation
        		true, // Show Legend
        		true, // Use tooltips
        		false // Configure chart to generate URLs?
        		);
        chart.setAntiAlias(true);
        
        //chart.getXYPlot().
        return chart; 
    }
    /**
     * Create a TimeSeries collections according to dataItem.
     * @param l a DataItem
     * @return TimeSerires
     */
    public static TimeSeries ArrayListToXYseries(DataItem l){
    	  // create dataset 1...
        TimeSeries series1 = new TimeSeries(l.getItemName());
        
        ArrayList<Hour> time=SimulationFramework.getCurrentEnv().time;
        int minzie=(l.getData().size()>time.size())?time.size():l.getData().size();
        for(int i=0;i<minzie;i++){
        	series1.add(
        			time.get(i),l.getData().get(i));
        }
        return  series1;
    }
    
}