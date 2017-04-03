package com.masim.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import com.masim.core.CooperationStrategy;
import com.masim.utils.SimulationFramework;
import com.masim.utils.Source;
import com.masim.utils.DispersionEngine;

public class Console extends JFrame {

	// private JTextArea txt= new JTextArea();
	public static Console current;

	public javax.swing.JTabbedPane tabPan;
	public javax.swing.JPanel gridPanel;
	public javax.swing.JPanel plotsPanel;
	public javax.swing.JTextArea frmlogs;
	public javax.swing.JTable tabshow;
	public DefaultTableModel dtm;
	public JPanel textAreaPanle;
	public JSplitPane controlJsplitPanel;
	// Variables declaration - do not modify
	public javax.swing.JButton jButton1;
	public javax.swing.JButton jButton2;
	public javax.swing.JButton jButton3;
	public javax.swing.JCheckBox jCheckBox1;
	public javax.swing.JCheckBox jCheckBox2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSplitPane jSplitPane1;
	public javax.swing.JTextArea txt;
	private javax.swing.JToolBar jToolBar1;

	public javax.swing.JToggleButton jToggleButton1;
	private javax.swing.JToggleButton jToggleButton2;
	public javax.swing.JButton btn_showEmissionSourcesStates;
	public javax.swing.JCheckBox btn_useCumulatifPenalities;
	// Variables declaration - do not modify
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenu jMenu4;
	private javax.swing.JMenu jMenu5;
	private javax.swing.JMenu modelMenu;
	private javax.swing.JMenu dispersionMenu;
	// private javax.swing.JMenu

	private javax.swing.JMenuBar jMenuBar1;
	public javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem10;
	private javax.swing.JMenuItem jMenuItem11;
	public javax.swing.JMenuItem jMenuItem2;
	public javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	public javax.swing.JMenuItem jMenuItem5;
	public javax.swing.JMenuItem jMenuItem6;
	public javax.swing.JMenuItem addSourceMenuItem;
	private javax.swing.JMenuItem jMenuItem8;
	public javax.swing.JMenuItem jMenuItem9;
	public javax.swing.JMenuItem buildModelMenuItem;

	public javax.swing.JMenuItem buildO3ModelMenuItem;
	public javax.swing.JMenuItem buildSOXModelMenuItem;
	public javax.swing.JMenuItem buildNOXModelMenuItem;
	public javax.swing.JMenuItem buildCOXModelMenuItem;

	public javax.swing.JMenuItem buildAQModelMenuItem;
	public javax.swing.JMenuItem showClimaticParams;
	public JMenuItem showDispersionWindowsMenuItem;
	public JMenuItem showPollutionParams;
	public JMenuItem testModelPM10;
	public JMenuItem configureDATA_PATH;
	private javax.swing.JPopupMenu.Separator jSeparator1;
	public JMenu confguration;
	public JMenuItem confitgurationITM;
	public JMenu aboutJM;
	public JMenuItem aboutMASAP;

	public JMenu cooperationStrategiesJM;
	public JMenuItem defineCooperationStrategiesJMI;
	public JMenuItem computeRMSQFORALLMODEL;
	public JMenuItem selectAirQualityGoal;

	// End of variables declaration
	// End of variables declaration

	public Console() throws HeadlessException {

		this.initComponents();
	}

	private void initComponents() {

		tabPan= new JTabbedPane();
		gridPanel= new JPanel();
		jToolBar1 = new javax.swing.JToolBar();
		jToggleButton1 = new javax.swing.JToggleButton();
		jToggleButton2 = new javax.swing.JToggleButton();
		jSplitPane1 = new javax.swing.JSplitPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		txt = new javax.swing.JTextArea();
		frmlogs = new javax.swing.JTextArea();
		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jCheckBox1 = new javax.swing.JCheckBox();
		jCheckBox2 = new javax.swing.JCheckBox();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem6 = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JPopupMenu.Separator();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenuItem5 = new javax.swing.JMenuItem();
		jMenu4 = new javax.swing.JMenu();
		jMenuItem8 = new javax.swing.JMenuItem();
		jMenuItem9 = new javax.swing.JMenuItem();
		jMenu5 = new javax.swing.JMenu();
		jMenuItem10 = new javax.swing.JMenuItem();
		jMenuItem11 = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		addSourceMenuItem = new javax.swing.JMenuItem();
		modelMenu = new javax.swing.JMenu();
		showPollutionParams = new JMenuItem();
		dispersionMenu = new JMenu();
		showDispersionWindowsMenuItem = new JMenuItem();
		testModelPM10 = new JMenuItem();
		buildModelMenuItem = new javax.swing.JMenuItem();
		aboutJM = new JMenu();
		aboutMASAP = new JMenuItem("About MASAP");
		cooperationStrategiesJM = new JMenu("Cooperation Strategies");
		defineCooperationStrategiesJMI = new JMenuItem(
				"Select a Cooperation Stratedy");
		selectAirQualityGoal = new JMenuItem("Select Air Quality Index");
		cooperationStrategiesJM.add(defineCooperationStrategiesJMI);
		cooperationStrategiesJM.add(selectAirQualityGoal);

		aboutJM.add(aboutMASAP);
		buildO3ModelMenuItem = new javax.swing.JMenuItem(
				"Build O3 Prediction Model");
		buildSOXModelMenuItem = new javax.swing.JMenuItem(
				"Build SOX Prediction Model");
		buildNOXModelMenuItem = new javax.swing.JMenuItem(
				"Build NOX Prediction Model");
		buildCOXModelMenuItem = new javax.swing.JMenuItem(
				"Build COX prediction Model");

		modelMenu.add(buildO3ModelMenuItem);
		modelMenu.add(buildSOXModelMenuItem);
		modelMenu.add(buildNOXModelMenuItem);
		modelMenu.add(buildCOXModelMenuItem);

		buildAQModelMenuItem = new JMenuItem();
		showClimaticParams = new javax.swing.JMenuItem();
		configureDATA_PATH = new JMenuItem("Configure DATA_PATH and load Data");
		confguration = new JMenu("Configuration");
		confitgurationITM = new JMenuItem("Manage Configuration");
		confguration.add(confitgurationITM);
		computeRMSQFORALLMODEL = new JMenuItem(
				"Compute performance of predictions models");
		selectAirQualityGoal = new JMenuItem("Select Air Quality Index Goal");

		tabshow = new JTable();
		dtm = new DefaultTableModel();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		modelMenu.setText("Models");
		buildModelMenuItem.setText("Build PM10 prediction model");
		buildAQModelMenuItem.setText("Build Air Quality prediction model");
		testModelPM10.setText("Run Validation for  prediction Models");

		modelMenu.add(buildModelMenuItem);
		modelMenu.add(buildAQModelMenuItem);
		modelMenu.add(testModelPM10);
		modelMenu.add(computeRMSQFORALLMODEL);
		dispersionMenu.add(showDispersionWindowsMenuItem);

		jMenu1.setText("File");
		dispersionMenu.setText("Dispersion");
		showDispersionWindowsMenuItem.setText("Show Dispersion window");
		jMenuItem1.setText("Save");
		jMenu1.add(jMenuItem1);

		jMenuItem2.setText("Save As...");
		jMenu1.add(jMenuItem2);

		jMenuItem6.setText("Exit");
		jMenu1.add(jMenuItem6);
		jMenu1.add(jSeparator1);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("Simulation");

		jMenuItem3.setText("Start Simulation");
		jMenu2.add(jMenuItem3);

		jMenuItem4.setText("Cancel Simulation");
		jMenu2.add(jMenuItem4);

		jMenuItem5.setText("Reaload System Parameters");
		jMenu2.add(jMenuItem5);

		jMenuBar1.add(jMenu2);

		jMenu4.setText("Data");
		jMenu4.add(configureDATA_PATH);
		jMenuItem9.setText("Reload Environment Data");
		jMenu4.add(jMenuItem9);
		jMenuItem9.setText("Load Environment Data");
		showClimaticParams
				.setText("Plot climatic paramaters (Humidity, temperature and wind speed");
		showPollutionParams
				.setText("Plot pollution parameters (O3,SOx,COx,PM10 and NOx)");
		jMenu4.add(showClimaticParams);
		jMenu4.add(showPollutionParams);

		jMenuItem8.setText("Export Simulation Result");
		jMenu4.add(jMenuItem8);

		jMenuBar1.add(jMenu4);
		jMenuBar1.add(confguration);
		jMenu5.setText("Profiling");

		jMenuItem10.setText("Start Profiling");
		jMenu5.add(jMenuItem10);

		jMenuItem11.setText("Stop Profiling");
		jMenu5.add(jMenuItem11);

		jMenuBar1.add(jMenu5);

		jMenu3.setText("Manage Emission Source");

		addSourceMenuItem.setText("Add Point Source");
		jMenu3.add(addSourceMenuItem);
		jMenuBar1.add(cooperationStrategiesJM);
		jMenuBar1.add(jMenu3);
		jMenuBar1.add(modelMenu);
		jMenuBar1.add(dispersionMenu);
		aboutJM.setText("?");
		jMenuBar1.add(aboutJM);
		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				gridPanel);
		gridPanel.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 279,
				Short.MAX_VALUE));

		jToolBar1.setRollover(true);

		jToggleButton1.setText(" Select Predefined Simulation Scenario");
		jToggleButton1.setFocusable(false);
		jToggleButton1
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jToggleButton1
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jToggleButton1);
		// -------
		btn_showEmissionSourcesStates = new JButton();
		btn_showEmissionSourcesStates.setText("Show Emission Sources Rate");
		btn_showEmissionSourcesStates.setFocusable(false);
		btn_showEmissionSourcesStates
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btn_showEmissionSourcesStates
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(btn_showEmissionSourcesStates);
		// btn_showEmissionSourcesStates
		
		//btn_useCumulatifPenalities
		btn_useCumulatifPenalities = new JCheckBox();
		btn_useCumulatifPenalities.setText("Use cumulative penalty");
		btn_useCumulatifPenalities.setFocusable(false);
		btn_useCumulatifPenalities
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		btn_useCumulatifPenalities
				.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		jToolBar1.add(btn_useCumulatifPenalities);
		//
		jToggleButton2.setText("Clear Message LOG");
		jToggleButton2.setFocusable(false);
		jToggleButton2
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jToggleButton2
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jToggleButton2ActionPerformed(evt);
			}
		});
		jToolBar1.add(jToggleButton2);
		controlJsplitPanel = new JSplitPane();
		controlJsplitPanel.setOrientation(jSplitPane1.VERTICAL_SPLIT);

		jSplitPane1.setDividerLocation(200);
		jSplitPane1.setDividerSize(10);

		txt.setColumns(20);
		txt.setRows(5);

		txt.setBackground(Color.white);
		txt.setForeground(Color.BLACK);
		txt.setAutoscrolls(true);

		frmlogs.setAutoscrolls(true);
		frmlogs.setMaximumSize(new Dimension(200, 200));
		txt.setFont(new Font("Times", 1, 10));
		frmlogs.setFont(new Font("Times", 1, 10));
		jScrollPane1.setViewportView(tabshow);

		jSplitPane1.setRightComponent(jScrollPane1);

		jButton1.setText("Select Air Quality Index Goal.");
		jButton1.setVisible(true);

		jCheckBox1.setLabel("Use Gaussian Plume Dispersion Model");
		jCheckBox1.setSelected(true);

		jCheckBox2.setText("Use Penalities");
		jCheckBox2.setVisible(true);
		jCheckBox2.setSelected(true);

		jButton2.setText("Stop Simulation and save Data");
		jButton3.setText("Suspend Simulation");
		jButton3.setVisible(false);
		// frmlogs.setSize(200, 400);
		frmlogs.setAutoscrolls(true);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jCheckBox2)
																		.addContainerGap())
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jCheckBox1)
																		.addContainerGap(
																				61,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jButton1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				103,
																				Short.MAX_VALUE)
																		.addGap(93,
																				93,
																				93))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jButton2)
																		.addContainerGap(
																				91,
																				Short.MAX_VALUE)))));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jCheckBox1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jCheckBox2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jButton1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton2)
										.addContainerGap(141, Short.MAX_VALUE))

				);

		jSplitPane1.setLeftComponent(controlJsplitPanel);
		JScrollPane panle = new JScrollPane();
		panle.setAutoscrolls(true);
		panle.setViewportView(frmlogs);
		controlJsplitPanel.setBottomComponent(panle);
		controlJsplitPanel.setTopComponent(jPanel1);

		getContentPane().add(tabPan);
		tabPan.addTab("Console", gridPanel);
		tabPan.addTab("Plots", plotsPanel);
		//getContentPane()
		gridPanel.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
						711, Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addComponent(jSplitPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										691, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jToolBar1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										25,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSplitPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										258, Short.MAX_VALUE).addContainerGap()));

		//getContentPane().add(new DrawPanle());
		pack();
	}

	public Console(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public Console(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Console(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Put MSG into the text area of the Console.
	 * 
	 * @param MSG
	 *            the message to be displayed on the console
	 */
	public static void logMSG(String MSG) {
		if (current == null) {
			// current= new Console();
			// current.setVisible(true);
			// current.setTitle("MASAP (Multi-Agent Simulation of Air Pollution) v 0.1 beta");
			// current.setState(MAXIMIZED_BOTH);

		}
		// current.txt.append("\n");
		current.txtLog.append(MSG + "\n");
	}

	public static StringBuffer txtLog = new StringBuffer();
	public static boolean first = true;

	public static void logTab(Object[] obj) {
		if (SimulationFramework.currentStrategy == CooperationStrategy.NEGOTIATION) {

			if (first) {
				Console.current.dtm.setColumnIdentifiers(new String[] {
						"SimStep", "quotionC", "quotionCPM10", "quotionCCOx",
						"quotionCSOx", "quotionCNOx", "PM10 Level",
						"Cox Level", "NOX Level", "SOX level", "O3 level",
						"Air Quality" });
				Console.current.tabshow.setModel(Console.current.dtm);
				first = false;
			} else {
				Console.current.dtm.addRow(obj);
			}
		} else {
			if (first) {
				Console.current.dtm.setColumnIdentifiers(new String[] {
						"SimStep", "PM10", "SOX", "COX", "NOX", "O3", "AIRQ" });
				Console.current.tabshow.setModel(Console.current.dtm);
				first = false;
			} else {
				Console.current.dtm.addRow(obj);
			}
		}
	}

	public static void frmLogMSG(String MSG) {
		if (current == null) {
			current = new Console();
			current.setVisible(true);
			current.setTitle("MASAP (Multi-Agent Simulation of Air Pollution) v 0.1 beta");
			current.setState(MAXIMIZED_BOTH);
		}
		current.frmlogs.append(MSG + "\n");
	}

	private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		this.frmlogs.setText("");
	}
}
