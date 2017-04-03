package com.masim.core;

import java.awt.Dialog.ModalityType;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.encog.util.simple.EncogUtility;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.time.Hour;
import org.jfree.ui.RefineryUtilities;

import weka.core.Instance;

import com.masim.emissionAgents.SourceConfigInfo;
import com.masim.ui.AboutDialog;
import com.masim.ui.AddSourceDialog;
import com.masim.ui.ChartFactoryUTL;
import com.masim.ui.ChartItem;
import com.masim.ui.ChartUIDialog;
import com.masim.ui.ChooseAirQualityGoalDialog;
import com.masim.ui.ChooseStrategiesDialog;
import com.masim.ui.Console;
import com.masim.ui.EmissionSourcsDataShowDiag;
import com.masim.ui.SelectPredefinedScenario;
import com.masim.ui.SimulationConfigDilog;
import com.masim.utils.AirQualityItem;
import com.masim.utils.AirQualityUTL;
import com.masim.utils.DataItem;
import com.masim.utils.EnvironmentState;
import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;
import com.masim.utils.Source;
import com.masim.utils.DispersionEngine;

import jade.BootGUI;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.tools.sniffer.ExitAction;

/**
 * This behaviour concern the UIAgent.
 * 
 * @author sabri ghazi
 * 
 */
public class StartUIBehaviour extends OneShotBehaviour {

	@Override
	public void action() {
		try {
			Console.frmLogMSG(StartContainer.currentAgentContainer
					.getContainerName());
			Console.frmLogMSG(StartContainer.currentAgentContainer
					.getPlatformName());
			Console.frmLogMSG(StartContainer.currentAgentContainer.getState()
					.toString());
		} catch (Exception exp) {
			Console.frmLogMSG(exp.getMessage());
		}
		// About menu item.
		Console.current.aboutMASAP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutDialog abd = new AboutDialog(null, true);
				abd.setVisible(true);
			}

		});
		// File menu event.
		Console.current.jMenuItem6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// show dispersion
		Console.current.showDispersionWindowsMenuItem
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						Collection<SourceConfigInfo> l = SimulationFramework.sourcesInfo
								.values();
						SimulationFramework.getCurrentEnv().dispersionEngine.sources
								.addAll(l);
						com.masim.utils.PS p = new com.masim.utils.PS();
						SimulationFramework.getCurrentEnv().dispersionEngine
								.showDispertionEngin();
						Thread t = new Thread(p);
						p.w = SimulationFramework.getCurrentEnv().dispersionEngine;
						t.start();
					}
				});

		// show climatic parameters.
		Console.current.showClimaticParams
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						ArrayList<DataItem> ClimaticDataTraining = new ArrayList<DataItem>();
						ClimaticDataTraining.add(SimulationFramework
								.getCurrentEnv().getTemp());
						ClimaticDataTraining.add(SimulationFramework
								.getCurrentEnv().getWs());
						ClimaticDataTraining.add(SimulationFramework
								.getCurrentEnv().getHu());

						ChartItem climaticCndtionPlot = ChartFactoryUTL
								.ChartFactory("Climatic parameters Training :",
										ClimaticDataTraining, false);
						
						ChartUIDialog cUI = ChartUIDialog
								.ChartUIDialog(climaticCndtionPlot);

						cUI.pack();
						RefineryUtilities.centerFrameOnScreen(cUI);
						cUI.setVisible(true);

						ArrayList<DataItem> climaticDataValidation = new ArrayList<DataItem>();
						climaticDataValidation.add(SimulationFramework
								.getCurrentEnv().temp_V);
						climaticDataValidation.add(SimulationFramework
								.getCurrentEnv().ws_V);
						climaticDataValidation.add(SimulationFramework
								.getCurrentEnv().hu_V);
						ChartItem climaticCndtionPlotVal = ChartFactoryUTL
								.ChartFactory(
										"Climatic parameters validation :",
										climaticDataValidation, false);
						ChartUIDialog cpvCUI = ChartUIDialog
								.ChartUIDialog(climaticCndtionPlotVal);
						cpvCUI.pack();

						RefineryUtilities.centerFrameOnScreen(cpvCUI);
						cpvCUI.setVisible(true);

					}

				});
		// View data used for training.
		Console.current.showPollutionParams
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						ArrayList<DataItem> dataPollutionTraining = new ArrayList<DataItem>();
						dataPollutionTraining.add(SimulationFramework
								.getCurrentEnv().getO3());
						dataPollutionTraining.add(SimulationFramework
								.getCurrentEnv().getPM10());
						dataPollutionTraining.add(SimulationFramework
								.getCurrentEnv().getCOx());
						dataPollutionTraining.add(SimulationFramework
								.getCurrentEnv().getSOx());
						dataPollutionTraining.add(SimulationFramework
								.getCurrentEnv().getNOx());

						ChartItem climaticCndtionPlot = ChartFactoryUTL
								.ChartFactory(
										"Pollution parameters used for training :",
										dataPollutionTraining, false);
						ChartUIDialog pollutionparamDiag = ChartUIDialog
								.ChartUIDialog(climaticCndtionPlot);
						pollutionparamDiag.pack();
						RefineryUtilities
								.centerFrameOnScreen(pollutionparamDiag);
						pollutionparamDiag.setVisible(true);

						ArrayList<DataItem> dataPollutionValidation = new ArrayList<DataItem>();
						// dataPollutionValidation.add(SimulationFramework.getCurrentEnv().O3_V);
						dataPollutionValidation.add(SimulationFramework
								.getCurrentEnv().PM10_V);
						// dataPollutionValidation.add(SimulationFramework.getCurrentEnv().COx_V);
						// dataPollutionValidation.add(SimulationFramework.getCurrentEnv().SOx_V);
						dataPollutionValidation.add(SimulationFramework
								.getCurrentEnv().NOx_V);

						ChartItem climaticCndtionPlotVal = ChartFactoryUTL
								.ChartFactory(
										"Pollution parameters used for validation:",
										dataPollutionValidation, false);
						ChartUIDialog ChartdiagValidationParams = ChartUIDialog
								.ChartUIDialog(climaticCndtionPlotVal);
						ChartdiagValidationParams.pack();

						RefineryUtilities
								.centerFrameOnScreen(ChartdiagValidationParams);
						ChartdiagValidationParams.setVisible(true);
					}
				});
		// Build PM10 model action.
		Console.current.buildModelMenuItem
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						ArrayList<DataItem> ResultsData = new ArrayList<DataItem>();

						final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
						trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().PM10);

						final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().temp_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().ws_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().hu_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().PM10_V);

						Console.frmLogMSG("Building model for PM10 prediction");
						Runnable r = new Runnable() {
							public void run() {
								SimulationFramework.getCurrentEnv().pm10MODEL = MachineLearningUtls
										.getEncogModel(trainingDataSet,
												validationDataSet, "PM10",
												false);
							}
						};
						Thread trainThread = new Thread(r);
						trainThread.start();
					}

				});

		// Build Air Quality prediction model
		Console.current.buildAQModelMenuItem
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						SimulationFramework.getCurrentEnv().AirQuality = MachineLearningUtls
								.getAirQualityPredictionModel(false);
						double[] inputs = new double[4];
						// for(int i=0;i<2000;i++){
						int i = 0;
						inputs[0] = SimulationFramework.getCurrentEnv().SOx
								.getData().get(i);
						inputs[1] = SimulationFramework.getCurrentEnv().NOx
								.getData().get(i);
						inputs[2] = SimulationFramework.getCurrentEnv().O3
								.getData().get(i);
						inputs[3] = SimulationFramework.getCurrentEnv().PM10
								.getData().get(i);

						Console.frmLogMSG("Current Air Quality is ("
								+ inputs[0] + " , " + inputs[1] + " , "
								+ inputs[2] + " , " + inputs[3]
								+ " ) indices : ");
						double currentAQ = MachineLearningUtls.interrogateAirQualityModel(
								SimulationFramework.getCurrentEnv().AirQuality,
								inputs);

						// SimulationFramework.getCurrentEnv().AirQuality.nnModel.
						Console.frmLogMSG(currentAQ + "");
						// }
					}
				});

		// test model.
		Console.current.testModelPM10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
				validationDataSet.add(SimulationFramework.getCurrentEnv().temp_V);
				validationDataSet.add(SimulationFramework.getCurrentEnv().ws_V);
				validationDataSet.add(SimulationFramework.getCurrentEnv().hu_V);
				validationDataSet.add(SimulationFramework.getCurrentEnv().PM10_V);

				ArrayList<DataItem> ResultsData = new ArrayList<DataItem>();
				ResultsData.add(SimulationFramework.getCurrentEnv().PM10_V);

				DataItem predicted = new DataItem();
				predicted.setItemName("Predicted PM10 ");
				predicted.setItemUnit("µ gramme / m^3");
				predicted.setData(MachineLearningUtls
						.interrogateModelForAllValidationValues(
								SimulationFramework.getCurrentEnv().pm10MODEL,
								validationDataSet));
				ResultsData.add(predicted);

				Console.frmLogMSG("Validation error for:"
						+ SimulationFramework.getCurrentEnv().pm10MODEL
								.getPollutantName()
						+ MachineLearningUtls.RMSE(SimulationFramework
								.getCurrentEnv().PM10_V.getData(), predicted
								.getData()) + " ");
				validationDataSet.remove(3);

				validationDataSet.add(SimulationFramework.getCurrentEnv().SOx);
				
				predicted.setData(MachineLearningUtls
						.interrogateModelForAllValidationValues(
								SimulationFramework.getCurrentEnv().SOxMODEL,
								validationDataSet));
				
				Console.frmLogMSG("Validation error for:"
						+ SimulationFramework.getCurrentEnv().SOxMODEL
								.getPollutantName()
						+ MachineLearningUtls.RMSE(SimulationFramework
								.getCurrentEnv().SOx.getData(), predicted
								.getData()) + " ");
				validationDataSet.remove(3);

				validationDataSet.add(SimulationFramework.getCurrentEnv().COx);
				predicted.setData(MachineLearningUtls
						.interrogateModelForAllValidationValues(
								SimulationFramework.getCurrentEnv().COxMODEL,
								validationDataSet));
				
				Console.frmLogMSG("Validation error for:"
						+ SimulationFramework.getCurrentEnv().COxMODEL
								.getPollutantName()
						+ MachineLearningUtls.RMSE(SimulationFramework
								.getCurrentEnv().COx.getData(), predicted
								.getData()) + " ");
				validationDataSet.remove(3);

				validationDataSet.add(SimulationFramework.getCurrentEnv().O3);
				predicted.setData(MachineLearningUtls
						.interrogateModelForAllValidationValues(
								SimulationFramework.getCurrentEnv().O3MODEL,
								validationDataSet));
				
				Console.frmLogMSG("Validation error for:"
						+ SimulationFramework.getCurrentEnv().O3MODEL
								.getPollutantName()
						+ MachineLearningUtls.RMSE(SimulationFramework
								.getCurrentEnv().O3.getData(), predicted
								.getData()) + " ");
				validationDataSet.remove(3);

				validationDataSet.add(SimulationFramework.getCurrentEnv().NOx_V);
				predicted.setData(MachineLearningUtls
						.interrogateModelForAllValidationValues(
								SimulationFramework.getCurrentEnv().NOxMODEL,
								validationDataSet));
				
				Console.frmLogMSG("Validation error for:"
						+ SimulationFramework.getCurrentEnv().NOxMODEL
								.getPollutantName()
						+ MachineLearningUtls.RMSE(SimulationFramework
								.getCurrentEnv().NOx_V.getData(), predicted
								.getData()) + " ");
				validationDataSet.remove(3);
				/*
				 * ChartItem resultsPlot = ChartFactoryUTL.ChartFactory(
				 * "Air Pollution simulation : Plots", ResultsData, true);
				 * ChartUIDialog chartDiag=
				 * ChartUIDialog.ChartUIDialog(resultsPlot); chartDiag.pack();
				 * RefineryUtilities.centerFrameOnScreen(chartDiag);
				 * chartDiag.setVisible(true);
				 */
			}
		});
		// Configure DATA_PATH
		Console.current.configureDATA_PATH
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						SimulationConfigDilog diag = new SimulationConfigDilog(
								Console.current, true);
						diag.setVisible(true);
					}
				});

		// Load environment data..
		Console.current.jMenuItem9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SimulationFramework.loadEnvironmentData();
			}

		});

		Console.current.jMenuItem5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		// Start simulation menuItem action.
		Console.current.jMenuItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SimulationFramework.StartSimulation();
				if(SimulationFramework.currentStrategy==CooperationStrategy.NEGOTIATION){
				try {
					StartContainer.createAgent("GameManager",
							"com.masim.gameTheory.GameManagerAgent");
				} catch (Exception e2) {
					Console.frmLogMSG(e2.getMessage());
				}
			}
				if(SimulationFramework.currentStrategy==CooperationStrategy.Voting){
					try {
						StartContainer.createAgent("VoteManager",
								"com.masim.gameTheory.VoteManager");
					} catch (Exception e2) {
						Console.frmLogMSG(e2.getMessage());
					}
				}

				SimulationFramework.getCurrentEnv().dispersionEngine = new DispersionEngine(
						800, 800, "Dispersion");

				final ArrayList<DataItem> ResultsData = new ArrayList<DataItem>();
				DataItem predicted = SimulationFramework.getCurrentEnv().sim_PM10;
				ResultsData.add(predicted);
				final ChartItem resultsPlot = ChartFactoryUTL.ChartFactory(
						"PM10", ResultsData, true);
				JPanel panel = new ChartPanel(resultsPlot.chart);
				panel.setPreferredSize(new java.awt.Dimension(1000, 800));
				Console.current.tabPan.addTab("PM10", panel);

				ArrayList<DataItem> aqSIM = new ArrayList<DataItem>();
				aqSIM.add(SimulationFramework.getCurrentEnv().sim_O3);
				final ChartItem resultsPlotAQ = ChartFactoryUTL.ChartFactory(
						"O3", aqSIM, true);
				JPanel panelO3 = new ChartPanel(resultsPlotAQ.chart);
				panel.setPreferredSize(new java.awt.Dimension(1000, 800));
				Console.current.tabPan.addTab("O3", panelO3);

				ArrayList<DataItem> SOx = new ArrayList<DataItem>();
				SOx.add(SimulationFramework.getCurrentEnv().sim_SOx);
				final ChartItem SOxChart = ChartFactoryUTL.ChartFactory("SOx",
						SOx, true);
				JPanel panelSOX = new ChartPanel(SOxChart.chart);
				panel.setPreferredSize(new java.awt.Dimension(1000, 800));
				Console.current.tabPan.addTab("SOX", panelSOX);
/*
				ArrayList<DataItem> NOX = new ArrayList<DataItem>();
				NOX.add(SimulationFramework.getCurrentEnv().sim_NOx);
				final ChartItem NOXChart = ChartFactoryUTL.ChartFactory("NOx",
						NOX, true);
				JPanel panelNOX = new ChartPanel(NOXChart.chart);
				panel.setPreferredSize(new java.awt.Dimension(1000, 800));
				Console.current.tabPan.addTab("NOX", panelNOX);
			*/

				Runnable r = new Runnable() {

					@Override
					public void run() {

						while (true) {
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {

									if (SimulationFramework.getCurrentEnv().time
											.size() > SimulationFramework
											.getCurrentEnv().T - 2) {
/*
										resultsPlot.dataset
												.getSeries(0)
												.addOrUpdate(
														SimulationFramework
																.getCurrentEnv().time
																.get(SimulationFramework
																		.getCurrentEnv().T - 1),
														SimulationFramework
																.getCurrentEnv().sim_PM10
																.getData()
																.get(SimulationFramework
																		.getCurrentEnv().T - 1));

										resultsPlotAQ.dataset
												.getSeries(0)
												.addOrUpdate(
														SimulationFramework
																.getCurrentEnv().time
																.get(SimulationFramework
																		.getCurrentEnv().T - 1),
														SimulationFramework
																.getCurrentEnv().sim_O3
																.getData()
																.get(SimulationFramework
																		.getCurrentEnv().T - 1));

								/*		SOxChart.dataset
												.getSeries(0)
												.addOrUpdate(
														SimulationFramework
																.getCurrentEnv().time
																.get(SimulationFramework
																		.getCurrentEnv().T - 1),
														SimulationFramework
																.getCurrentEnv().sim_SOx
																.getData()
																.get(SimulationFramework
																		.getCurrentEnv().T - 1));*/
										/*
										 * NOXChart.dataset .getSeries(0)
										 * .addOrUpdate( SimulationFramework
										 * .getCurrentEnv().time
										 * .get(SimulationFramework
										 * .getCurrentEnv().T - 1),
										 * SimulationFramework
										 * .getCurrentEnv().sim_NOx .getData()
										 * .get(SimulationFramework
										 * .getCurrentEnv().T - 1));
										 */
									}
								}

							});
							try {
								Thread.sleep(SimulationFramework.SIMSPEED);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				};

				Thread t = new Thread(r);
				t.start();

				Collection<SourceConfigInfo> l = SimulationFramework.sourcesInfo
						.values();
				SimulationFramework.getCurrentEnv().dispersionEngine.sources
						.addAll(l);
				com.masim.utils.PS p = new com.masim.utils.PS();
				SimulationFramework.getCurrentEnv().dispersionEngine
						.showDispertionEngin();
				Thread t2 = new Thread(p);
				p.w = SimulationFramework.getCurrentEnv().dispersionEngine;
				t2.start();

			}

		});
		// Choose Air Quality goals
		Console.current.jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ChooseAirQualityGoalDialog cAqGoal = new ChooseAirQualityGoalDialog(
						null, true);
				cAqGoal.show();
			}
		});
		// Stop simulation button action.
		Console.current.jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SimulationFramework
						.setCurrentSimProcessState(SimProcessState.Stoped);
				// Stop the negotiation game.
				SimulationFramework.StopNegociationbetWeenEmissionSources();

				SimulationFramework.save();
				SimulationFramework.getCurrentEnv().T = 0;
				SimulationFramework.getCurrentEnv().AirQuality_V.getData()
						.clear();

				SimulationFramework.getCurrentEnv().sim_COx.getData().clear();
				SimulationFramework.getCurrentEnv().sim_NOx.getData().clear();
				SimulationFramework.getCurrentEnv().sim_SOx.getData().clear();
				SimulationFramework.getCurrentEnv().sim_O3.getData().clear();

				SimulationFramework.sourcesInfo.clear();
				SimulationFramework.KillSourceAgent();
				SimulationFramework.KillGameManagerAgent();
				SimulationFramework.getCurrentEnv().dispersionEngine.sources
						.clear();
				SimulationFramework.getCurrentEnv().dispersionEngine.dispose();
				Console.current.txtLog.delete(0,
						Console.current.txtLog.length());
				Console.current.dtm.setRowCount(0);
			}
		});

		// Save results i a file.

		// Save results.
		Console.current.jMenuItem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SimulationFramework.save();

			}
		});
		// Configuration.

		Console.current.confitgurationITM
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						SimulationConfigDilog sgd = new SimulationConfigDilog(
								null, true);
						sgd.setVisible(true);

					}
				});
		// Build SOX model menu action.
		Console.current.buildSOXModelMenuItem
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
						trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);

						final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().temp_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().ws_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().hu_V);

						Console.frmLogMSG(".......................................................");
						Console.frmLogMSG("Building Model for SOx Prediction...");
						Runnable r = new Runnable() {
							@Override
							public void run() {
								trainingDataSet.add(SimulationFramework
										.getCurrentEnv().SOx);
								validationDataSet.add(SimulationFramework
										.getCurrentEnv().SOx);
								SimulationFramework.getCurrentEnv().SOxMODEL = MachineLearningUtls
										.getEncogModel(trainingDataSet,
												validationDataSet, "SOX", false);
							}
						};
						Thread trainerThread = new Thread(r);
						trainerThread.start();
					}
				});
		// Build COX model menu action.
		Console.current.buildCOXModelMenuItem
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
						trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);

						final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().temp_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().ws_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().hu_V);

						Console.frmLogMSG("Building model for COx prediction");

						trainingDataSet.add(SimulationFramework.getCurrentEnv().COx);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().COx);
						Runnable r = new Runnable() {

							@Override
							public void run() {
								SimulationFramework.getCurrentEnv().COxMODEL = MachineLearningUtls
										.getEncogModel(trainingDataSet,
												validationDataSet, "COX", false);
							}
						};
						Thread trainerThread = new Thread(r);
						trainerThread.start();

					}
				});
		// Build NOX model menu action...
		Console.current.buildNOXModelMenuItem
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
						trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);

						final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().temp_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().ws_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().hu_V);

						Console.frmLogMSG("Building model for NOx prediction");

						trainingDataSet.add(SimulationFramework.getCurrentEnv().NOx);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().NOx);
						Runnable r = new Runnable() {

							@Override
							public void run() {
								SimulationFramework.getCurrentEnv().NOxMODEL = MachineLearningUtls
										.getEncogModel(trainingDataSet,
												validationDataSet, "NOX", false);
							}
						};
						Thread t = new Thread(r);
						t.start();

					}
				});
		// Build O3 model menu action.
		Console.current.buildO3ModelMenuItem
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
						trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
						trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);

						final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().temp_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().ws_V);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().hu_V);

						Console.frmLogMSG("Building model for O3 prediction");

						trainingDataSet.add(SimulationFramework.getCurrentEnv().NOx);
						validationDataSet.add(SimulationFramework
								.getCurrentEnv().O3);

						Runnable r = new Runnable() {
							@Override
							public void run() {
								SimulationFramework.getCurrentEnv().O3MODEL = MachineLearningUtls
										.getEncogModel(trainingDataSet,
												validationDataSet, "O3", false);
							}
						};
						Thread t = new Thread(r);
						t.start();
					}
				});

		// Add Source Menu item event handler.
		Console.current.addSourceMenuItem
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						AddSourceDialog asd = new AddSourceDialog(null, true);
						asd.setVisible(true);
					}
				});
		Console.current.defineCooperationStrategiesJMI
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ChooseStrategiesDialog chousStrategyDiag = new ChooseStrategiesDialog(
								Console.current, true);
						chousStrategyDiag.setVisible(true);
					}
				});
		//

		Console.current.computeRMSQFORALLMODEL
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

					}
				});
		// Select which dispersion model to use.
		Console.current.jCheckBox1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Console.current.jCheckBox1.isSelected()) {
					SimulationFramework.useGausianModel = 1;
				} else
					SimulationFramework.useGausianModel = 0;
			}
		});
		// Select which air quality is fixed as goal.
		Console.current.selectAirQualityGoal
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ChooseAirQualityGoalDialog cAqGoal = new ChooseAirQualityGoalDialog(
								null, true);
						cAqGoal.show();

					}
				});
		// Enable or Disable the use of penalties.
		Console.current.jCheckBox2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Console.current.jCheckBox2.isSelected()) {
					SimulationFramework.withPenalities = true;

				} else
					SimulationFramework.withPenalities = false;
				Console.frmLogMSG("Penalities is :"
						+ SimulationFramework.withPenalities);
			}
		});

		// Select a predefined scenario
		Console.current.jToggleButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SelectPredefinedScenario sPredifinedScenarioDialog = new SelectPredefinedScenario(
						Console.current, true);
				sPredifinedScenarioDialog.setVisible(true);
			}
		});
		// show emission sources
		Console.current.btn_showEmissionSourcesStates
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						EmissionSourcsDataShowDiag emShowDiag = new EmissionSourcsDataShowDiag();
					}
				});
		// is cumulative penality used or not.
		Console.current.btn_useCumulatifPenalities
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (Console.current.btn_useCumulatifPenalities
								.isSelected()) {
							SimulationFramework.cumulatifPenalty = true;
						} else
							SimulationFramework.cumulatifPenalty = false;
					}
				});
	}
}