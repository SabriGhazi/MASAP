package com.masim.core;

import java.io.File;
import java.util.ArrayList;

import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.encog.util.simple.EncogUtility;
import org.joone.edit.DesiredLayerConnection;
import org.joone.helpers.factory.JooneTools;
import org.joone.net.NeuralNet;
import com.masim.ui.Console;
import com.masim.ui.TrainingProgressDialog;
import com.masim.utils.DataItem;
import com.masim.utils.SimulationFramework;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * 
 * @author Sabri Ghazi email:ghazi_sabri@yahoo.fr
 * 
 */
public class MachineLearningUtls {

	static NeuralNet nnet = null;
	static int dataSetSize;
	static int ValidationDataSetSize;

	public static int NUMBER_OF_STEP_IN_ADVANCE = 0;
	public static double TRAINING_ERROR = 0.001;

	public static double[][] SO2VAL = { { 0, 30 }, { 30, 60 }, { 60, 125 },
			{ 125, 250 }, { 250, 500 } };
	public static double[][] NO2VAL = { { 0, 45 }, { 45, 80 }, { 80, 200 },
			{ 200, 400 }, { 400, 500 } };
	public static double[][] O3 = { { 0, 45 }, { 45, 80 }, { 80, 150 },
			{ 150, 270 }, { 270, 500 } };
	public static double[][] PM10 = { { 0, 20 }, { 20, 40 }, { 40, 100 },
			{ 100, 200 }, { 200, 500 } };

	public static double[][] AQ = { { 0, 0, 0, 0 }, { 30, 45, 45, 20 },
			{ 35, 50, 50, 25 }, { 60, 80, 80, 40 }, { 65, 85, 85, 45 },
			{ 125, 200, 150, 100 }, { 130, 205, 155, 105 },
			{ 250, 400, 270, 200 }, { 255, 405, 275, 205 },
			{ 500, 500, 500, 500 } };

	public static double[][] desiredAQ = { { 1 }, { 1 }, { 2 }, { 2 }, { 3 },
			{ 3 }, { 4 }, { 4 }, { 5 }, { 5 } };

	/**
	 * aq={ SO2 NO2 O3 PM10 Indices Appreciation 0–30 0–45 0–45 0–20 1 Very Good
	 * 30–60 45–80 45–80 20–40 2 Good 60–125 80–200 80–150 40–100 3 Average
	 * 125–250 200–400 150–270 100–200 4 Bad >250 >400 >270 >200 5 Very Bad}
	 */

	/**
	 * @param indices
	 * @return
	 */
	public static String getDescription(double indices) {

		int index = (int) Math.round(indices);
		switch (index) {
		case 1:
			return "VERY GOOD";
		case 2:
			return "GOOD";
		case 3:
			return "Average";
		case 4:
			return "BAD";
		case 5:
			return "VERY BAD";
		default:
			break;
		}
		return "UNKNOW";
	}

	/**
	 * Build WEAKA DATASET
	 * 
	 * @param items
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Instances buildDataSet(ArrayList<DataItem> items, int begin,
			int end) {

		Attribute at1 = new Attribute("Hu");
		Attribute at2 = new Attribute("WS");
		Attribute at3 = new Attribute("Temp");

		Attribute PM10 = new Attribute("PM10");
		FastVector atts = new FastVector();

		atts.addElement(at1);
		atts.addElement(at2);
		atts.addElement(at3);
		atts.addElement(PM10);

		Instances ins = new Instances("Data", atts, 0);
		double[] vals = new double[4];

		ArrayList<Double> hu = items.get(0).getData();
		ArrayList<Double> ws = items.get(1).getData();
		ArrayList<Double> temp = items.get(2).getData();
		ArrayList<Double> pm10 = items.get(3).getData();

		int i = begin;
		Instance a_instance;

		while (i < end) {
			vals = new double[4];
			vals[0] = hu.get(i);
			vals[1] = ws.get(i);
			vals[2] = temp.get(i);
			vals[3] = pm10.get(i);
			a_instance = new Instance(1.0, vals);

			ins.add(a_instance);
			i++;
		}
		ins.setClassIndex(ins.numAttributes() - 1);

		return ins;
	}

	/** Logger for this class. */
	/**
	 * Build JOONE NEURAL NETWORK
	 */
	public synchronized static ArrayList<Double> getJoonNetModel(
			ArrayList<DataItem> items) {
		MachineLearningUtls m = new MachineLearningUtls();

		ArrayList<Double> hu = new ArrayList<Double>();
		hu.addAll(items.get(0).getData());

		ArrayList<Double> ws = new ArrayList<Double>();
		ws.addAll(items.get(1).getData());
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.addAll(items.get(2).getData());
		ArrayList<Double> pm10 = new ArrayList<Double>();
		pm10.addAll(items.get(3).getData());
		// set the inputs
		dataSetSize = 500;// pm10.size();
		double[][] d = new double[dataSetSize][3];
		double[][] desiredOutputArray = new double[dataSetSize][1];
		ArrayList<Double> ret = new ArrayList<Double>();

		NormalizedField huNRM = new NormalizedField(
				NormalizationAction.Normalize, null, items.get(0)
						.getItemMaxVal(), items.get(0).getItemMinVal(), 1, 0.1);

		NormalizedField wsNRM = new NormalizedField(
				NormalizationAction.Normalize, null, items.get(1)
						.getItemMaxVal(), items.get(1).getItemMinVal(), 1, 0.1);

		NormalizedField tempNRM = new NormalizedField(
				NormalizationAction.Normalize, null, items.get(2)
						.getItemMaxVal(), items.get(2).getItemMinVal(), 1, 0.1);
		NormalizedField pm10NRM = new NormalizedField(
				NormalizationAction.Normalize, null, items.get(3)
						.getItemMaxVal(), items.get(3).getItemMinVal(), 1, 0.1);

		for (int i = 0; i < dataSetSize; i++) {
			d[i][0] = huNRM.normalize(hu.get(i).doubleValue());
			d[i][1] = wsNRM.normalize(ws.get(i).doubleValue());
			d[i][2] = tempNRM.normalize(temp.get(i).doubleValue());
			desiredOutputArray[i][0] = pm10NRM.normalize(pm10.get(i)
					.doubleValue());
		}
		try {

			// Create the network: 3 layers with a logistic output layer
			NeuralNet nnet = JooneTools.create_standard(new int[] { 3, 5, 1 },
					JooneTools.LOGISTIC);
			// NeuralNet nnet =
			// JooneTools.load("org/joone/samples/engine/helpers/rxor.snet");
			nnet.getMonitor().setSingleThreadMode(true);

			nnet.setRows(3);
			// Train the network for 5000 epochs, or until rmse reaches 0.01.
			// Outputs the results every 200 epochs on the stardard output
			double rmse = JooneTools.train(nnet, d, desiredOutputArray, 5000,
					0.01, 1000, System.out, false);

			// Interrogate the network and prints the results
			System.out.println("Last RMSE = " + rmse);
			System.out.println("\nResults:" + dataSetSize);
			System.out.println("|Inp 1\t|Inp 2\t|input 3\t|Output");
			double[] output;

			for (int i = 0; i < dataSetSize; ++i) {
				output = JooneTools.interrogate(nnet, d[i]);
				System.out.print(i + " | " + d[i][0] + "\t | " + d[i][1]
						+ "\t |  " + d[i][2]);
				System.out.println("    " + i + "   PM10 :"
						+ pm10NRM.deNormalize(output[0]) + " "
						+ pm10NRM.deNormalize(desiredOutputArray[i][0]));
				ret.add(pm10NRM.deNormalize(output[0]));
			}

			// Test the network and prints the rmse
			double testRMSE = JooneTools.test(nnet, d, desiredOutputArray);
			System.out.println("\nTest error = " + testRMSE);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return ret;
	}

	/**
	 * Build WEAKA RBF
	 * 
	 * @param dataset
	 * @return
	 */
	public static Classifier getRBFNetwork(Instances dataset) {
		// Console.logMSG(dataset.toString());
		weka.classifiers.functions.RBFNetwork rbf = new RBFNetwork();
		weka.classifiers.functions.MultilayerPerceptron mlp = new MultilayerPerceptron();
		// weka.classifiers.functions.LinearRegression lr= new LinearReg
		// ression();
		weka.classifiers.functions.SMO sm = new SMO();
		try {
			String[] options = { "-L", "0.3", "-M", "0.2", "-N", "500", "-V",
					"0", "-S", "0", "-E", "20", "-H", "a" };
			mlp.setOptions(options);
			mlp.buildClassifier(dataset);
			Evaluation eval = new Evaluation(dataset);
			eval.evaluateModel(mlp, dataset);
			Console.logMSG(mlp.toString());
			Console.logMSG(eval.toSummaryString(true));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mlp;
	}

	/**
	 * Build a Prediction model for air pollution indices using a neural
	 * network.
	 * 
	 * @return
	 */
	public static PredictionModel getAirQualityPredictionModel(boolean noTrain) {

		PredictionModel pmodel = new PredictionModel();
		pmodel.setModelName("AIRQUALITY");
		NormalizedField norm = new NormalizedField(
				NormalizationAction.Normalize, null, 0, 500, 1, 0);

		NormalizedField normDesired = new NormalizedField(
				NormalizationAction.Normalize, null, 0, 5, 1, 0);
		pmodel.normalizedUnified = norm;

		pmodel.normalizedPollutant = normDesired;
		for (int i = 0; i < AQ.length; i++) {

			AQ[i][0] = norm.normalize(AQ[i][0]);
			AQ[i][1] = norm.normalize(AQ[i][1]);
			AQ[i][2] = norm.normalize(AQ[i][2]);
			AQ[i][3] = norm.normalize(AQ[i][3]);
			desiredAQ[i][0] = normDesired.normalize(desiredAQ[i][0]);
		}
		MLDataSet trainingDataSet = new BasicMLDataSet(AQ, desiredAQ);
		BasicNetwork network = null;

		if (!noTrain) {
			network = EncogUtility.simpleFeedForward(4, 14, 0, 1, true);
			final Backpropagation train = new Backpropagation(network,
					trainingDataSet);
			int epoch = 1;
			Console.frmLogMSG("Training the prediction model");
			do {
				train.iteration();
				// Console.logMSG("Epoch #" + epoch + " Error:" +
				// train.getError());
				epoch++;
			} while (train.getError() > 0.01);
			EncogDirectoryPersistence.saveObject(new File(pmodel.getModelName()
					+ ".eg"), network);
		} else {
			network = (BasicNetwork) EncogDirectoryPersistence
					.loadObject(new File(pmodel.getModelName() + ".eg"));
		}
		pmodel.setNnModel(network);
		return pmodel;
	}

	/**
	 * Give prediction of AIR QUALITY indices based on input (AIR POLLUTION
	 * CONCENTRATION)
	 * 
	 * @param pmodel
	 * @param input
	 *            array of 4 double values of Air pollutant concentration.
	 * @return the indices of air quality.
	 */
	public static double interrogateAirQualityModel(PredictionModel pmodel,
			double[] input) {

		input[0] = pmodel.normalizedUnified.normalize(input[0]);
		input[1] = pmodel.normalizedUnified.normalize(input[1]);
		input[2] = pmodel.normalizedUnified.normalize(input[2]);
		input[3] = pmodel.normalizedUnified.normalize(input[3]);

		BasicNetwork nnet = pmodel.getNnModel();
		double[] output = new double[1];
		nnet.compute(input, output);
		System.out.println(output + "  "
				+ pmodel.normalizedPollutant.deNormalize(output[0]));
		return pmodel.normalizedPollutant.deNormalize(output[0]);
	}

	/**
	 * Build an air pollution prediction model based MLP artificial neural
	 * network, using the training data set.
	 * 
	 * @param items
	 *            the training data set.
	 * @param validationSet
	 *            the validation data set.
	 * @return PredictionModel
	 */
	public static PredictionModel getEncogModel(ArrayList<DataItem> items,
			ArrayList<DataItem> validationSet, String modelName, boolean noTrain) {

		PredictionModel predictionMDL = new PredictionModel();
		predictionMDL.setModelName(modelName);
		predictionMDL.setPollutantName(modelName);
		// Preparing training dataset.
		ArrayList<Double> hu = new ArrayList<Double>();
		hu.addAll(items.get(0).getData());
		ArrayList<Double> ws = new ArrayList<Double>();
		ws.addAll(items.get(1).getData());
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.addAll(items.get(2).getData());
		ArrayList<Double> pm10 = new ArrayList<Double>();
		pm10.addAll(items.get(3).getData());

		// Preparing validation dataset.
		ArrayList<Double> huV = new ArrayList<Double>();
		huV.addAll(validationSet.get(0).getData());

		ArrayList<Double> wsV = new ArrayList<Double>();
		wsV.addAll(validationSet.get(1).getData());

		ArrayList<Double> tempV = new ArrayList<Double>();
		tempV.addAll(validationSet.get(2).getData());

		ArrayList<Double> pm10V = new ArrayList<Double>();
		pm10V.addAll(validationSet.get(3).getData());

		// set the inputs
		int dataSetSize = pm10.size();
		int ValidationDataSetSiz = 500;
		// e=pm10V.size();

		double[][] d = new double[dataSetSize][4];
		double[][] desiredOutputArray = new double[dataSetSize][1];

		NormalizedField huNRM = new NormalizedField(
				NormalizationAction.Normalize, null, items.get(0)
						.getItemMaxVal(), items.get(0).getItemMinVal(), 1, 0);

		NormalizedField wsNRM = new NormalizedField(
				NormalizationAction.Normalize, null, items.get(1)
						.getItemMaxVal(), items.get(1).getItemMinVal(), 1, 0);

		NormalizedField tempNRM = new NormalizedField(
				NormalizationAction.Normalize, null, items.get(2)
						.getItemMaxVal(), items.get(2).getItemMinVal(), 1, 0);
		NormalizedField pm10NRM = new NormalizedField(
				NormalizationAction.Normalize, null, items.get(3)
						.getItemMaxVal(), items.get(3).getItemMinVal(), 1, 0);
		// Validation normalizers.
		NormalizedField huNRMV = new NormalizedField(
				NormalizationAction.Normalize, null, validationSet.get(0)
						.getItemMaxVal(), validationSet.get(0).getItemMinVal(),
				1, 0);

		NormalizedField wsNRMV = new NormalizedField(
				NormalizationAction.Normalize, null, validationSet.get(1)
						.getItemMaxVal(), validationSet.get(1).getItemMinVal(),
				1, 0);

		NormalizedField tempNRMV = new NormalizedField(
				NormalizationAction.Normalize, null, validationSet.get(2)
						.getItemMaxVal(), validationSet.get(2).getItemMinVal(),
				1, 0);
		NormalizedField pm10NRMV = new NormalizedField(
				NormalizationAction.Normalize, null, validationSet.get(3)
						.getItemMaxVal(), validationSet.get(3).getItemMinVal(),
				1, 0);

		predictionMDL.setNormalizedPollutant(pm10NRMV);
		predictionMDL.setNormalizedHu(huNRMV);
		predictionMDL.setNormalizedTEMP(tempNRMV);
		predictionMDL.setNormalizedWS(wsNRMV);
		BasicNetwork network = null;
		if (!noTrain) {
			for (int i = 0; i < dataSetSize - NUMBER_OF_STEP_IN_ADVANCE; i++) {
				d[i][0] = huNRM.normalize(hu.get(i).doubleValue());
				d[i][1] = wsNRM.normalize(ws.get(i).doubleValue());
				d[i][2] = tempNRM.normalize(temp.get(i).doubleValue());
				d[i][3] = pm10NRM.normalize(pm10.get(i).doubleValue());
				desiredOutputArray[i][0] = pm10NRM.normalize(pm10.get(
						i + NUMBER_OF_STEP_IN_ADVANCE).doubleValue());
			}

			// create a neural network, without using a factory
			network = EncogUtility.simpleFeedForward(4, 14, 0, 1, true);

			MLDataSet ds = new BasicMLDataSet(d, desiredOutputArray);
			// MLDataSet dsV = new BasicMLDataSet(dV, desiredOutputArrayV);

			Backpropagation train = new Backpropagation(network, ds);
			int epoch = 1;
			Console.frmLogMSG("Training the prediction model");
			double error;
			boolean stop = false;
			TrainingProgressDialog.createDialog("Training...");
			do {
				train.iteration();
				error = train.getError();
				stop = TrainingProgressDialog.Update(epoch / 5, epoch, error);
				// Console.logMSG("#");
				epoch++;
			} while ((error > TRAINING_ERROR) && (!stop));

			TrainingProgressDialog.currentDialog.setVisible(false);
			TrainingProgressDialog.currentDialog.dispose();

			Console.frmLogMSG("Number of Epoch :" + epoch + " Error " + error);
			Console.frmLogMSG("..............................................................");
			EncogDirectoryPersistence.saveObject(new File(modelName + ".eg"),
					network);
		} else
			network = (BasicNetwork) EncogDirectoryPersistence
					.loadObject(new File(modelName + ".eg"));

		predictionMDL.setNnModel(network);
		Encog.getInstance().shutdown();
		return predictionMDL;
	}

	public static void loadModels() {

		loadSavedPM10Models();
		loadSOxSavedModel();
		loadNOXsavedModel();
		loadO3savedModel();
		loadCoxSavedModel();
		SimulationFramework.getCurrentEnv().AirQuality = MachineLearningUtls
				.getAirQualityPredictionModel(true);
	}

	public static void loadSavedPM10Models() {

		final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
		trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().PM10);

		final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
		validationDataSet.add(SimulationFramework.getCurrentEnv().temp_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().ws_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().hu_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().PM10_V);
		SimulationFramework.getCurrentEnv().pm10MODEL = MachineLearningUtls
				.getEncogModel(trainingDataSet, validationDataSet, "PM10", true);

	}

	public static void loadSOxSavedModel() {

		final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
		trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);

		final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
		validationDataSet.add(SimulationFramework.getCurrentEnv().temp_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().ws_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().hu_V);

		trainingDataSet.add(SimulationFramework.getCurrentEnv().SOx);
		validationDataSet.add(SimulationFramework.getCurrentEnv().SOx);

		SimulationFramework.getCurrentEnv().SOxMODEL = MachineLearningUtls
				.getEncogModel(trainingDataSet, validationDataSet, "SOX", true);
	}

	public static void loadCoxSavedModel() {
		final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
		trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);

		final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
		validationDataSet.add(SimulationFramework.getCurrentEnv().temp_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().ws_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().hu_V);

		Console.frmLogMSG("Building model for COx prediction");

		trainingDataSet.add(SimulationFramework.getCurrentEnv().COx);
		validationDataSet.add(SimulationFramework.getCurrentEnv().COx);

		SimulationFramework.getCurrentEnv().COxMODEL = MachineLearningUtls
				.getEncogModel(trainingDataSet, validationDataSet, "COX", true);
	}

	public static void loadNOXsavedModel() {

		final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
		trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);

		final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
		validationDataSet.add(SimulationFramework.getCurrentEnv().temp_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().ws_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().hu_V);

		Console.frmLogMSG("Building model for NOx prediction");

		trainingDataSet.add(SimulationFramework.getCurrentEnv().NOx);
		validationDataSet.add(SimulationFramework.getCurrentEnv().NOx);

		SimulationFramework.getCurrentEnv().NOxMODEL = MachineLearningUtls
				.getEncogModel(trainingDataSet, validationDataSet, "NOX", true);
	}

	public static void loadO3savedModel() {

		final ArrayList<DataItem> trainingDataSet = new ArrayList<DataItem>();
		trainingDataSet.add(SimulationFramework.getCurrentEnv().temp);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().ws);
		trainingDataSet.add(SimulationFramework.getCurrentEnv().hu);

		final ArrayList<DataItem> validationDataSet = new ArrayList<DataItem>();
		validationDataSet.add(SimulationFramework.getCurrentEnv().temp_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().ws_V);
		validationDataSet.add(SimulationFramework.getCurrentEnv().hu_V);

		Console.frmLogMSG("Building model for O3 prediction");

		trainingDataSet.add(SimulationFramework.getCurrentEnv().NOx);
		validationDataSet.add(SimulationFramework.getCurrentEnv().O3);

		SimulationFramework.getCurrentEnv().O3MODEL = MachineLearningUtls
				.getEncogModel(trainingDataSet, validationDataSet, "O3", true);
	}

	/**
	 * Give the prediction about air pollution using the pModel.
	 * 
	 * @param pmodel
	 * @param input
	 *            the input for the model.
	 * @return
	 */
	public static double interrogateModel(PredictionModel pmodel, double[] input) {
		if (pmodel == null)
			return 0;
		input[0] = pmodel.normalizedHu.normalize(input[0]);
		input[1] = pmodel.normalizedTEMP.normalize(input[1]);
		input[2] = pmodel.normalizedWS.normalize(input[2]);

		input[3] = pmodel.normalizedPollutant.normalize(input[3]);

		BasicNetwork nnet = pmodel.getNnModel();
		double[] output = new double[1];
		nnet.compute(input, output);
		return pmodel.normalizedPollutant.deNormalize(output[0]);
	}

	/**
	 * Give the output of the model for all data set used for validation.
	 * 
	 * @param pmodel
	 * @param validationSet
	 * @return an ArrayList<Double>.
	 */
	public static ArrayList<Double> interrogateModelForAllValidationValues(
			PredictionModel pmodel, ArrayList<DataItem> validationSet) {

		ArrayList<Double> ret = new ArrayList<Double>();

		// Preparing validation dataset.
		ArrayList<Double> huV = new ArrayList<Double>();
		huV.addAll(validationSet.get(0).getData());

		ArrayList<Double> wsV = new ArrayList<Double>();
		wsV.addAll(validationSet.get(1).getData());

		ArrayList<Double> tempV = new ArrayList<Double>();
		tempV.addAll(validationSet.get(2).getData());

		ArrayList<Double> pollution = new ArrayList<Double>();
		pollution.addAll(validationSet.get(3).getData());
		int minsize = (huV.size() < wsV.size()) ? huV.size() : wsV.size();
		minsize = (minsize > tempV.size()) ? tempV.size() : minsize;
		minsize = (minsize > pollution.size()) ? pollution.size() : minsize;
		ValidationDataSetSize = minsize;

		double[][] dV = new double[ValidationDataSetSize][4];
		double[][] desiredOutputArrayV = new double[ValidationDataSetSize][1];

		for (int i = 0; i < ValidationDataSetSize - NUMBER_OF_STEP_IN_ADVANCE; i++) {
			dV[i][0] = pmodel.getNormalizedHu().normalize(
					huV.get(i).doubleValue());
			dV[i][1] = pmodel.getNormalizedWS().normalize(
					wsV.get(i).doubleValue());
			dV[i][2] = pmodel.getNormalizedTEMP().normalize(
					tempV.get(i).doubleValue());
			dV[i][3] = pmodel.getNormalizedPollutant().normalize(
					pollution.get(i).doubleValue());
			desiredOutputArrayV[i][0] = pmodel.getNormalizedPollutant()
					.normalize(
							pollution.get(i + NUMBER_OF_STEP_IN_ADVANCE)
									.doubleValue());
		}
		MLDataSet dsV = new BasicMLDataSet(dV, desiredOutputArrayV);
		for (MLDataPair pair : dsV) {
			final MLData output = pmodel.getNnModel().compute(pair.getInput());
			ret.add(pmodel.getNormalizedPollutant().deNormalize(
					output.getData(0)));
		}
		return ret;
	}

	/**
	 * compute the RMSE between the target and predicted vectors. Rooted Mean
	 * Squared Error.
	 * 
	 * @param target
	 * @param predicted
	 * @return
	 */
	public static double RMSE(ArrayList<Double> target,
			ArrayList<Double> predicted) {

		int size = (target.size() < predicted.size()) ? target.size()
				: predicted.size();
		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum = Math.pow((target.get(i) - predicted.get(i)), 2) + sum;
		}
		return Math.sqrt((sum / size));
	}
	/*
	 * public static KMe BuildKmeanClassificator( ArrayList<DataItem> items,
	 * ArrayList<DataItem> validationSet, String modelName ){
	 * 
	 * PredictionModel predictionMDL= new PredictionModel();
	 * predictionMDL.PollutantName=modelName; //Preparing training dataset.
	 * ArrayList<Double> hu= new ArrayList<Double>();
	 * hu.addAll(items.get(0).getData()); ArrayList<Double> ws= new
	 * ArrayList<Double>(); ws.addAll(items.get(1).getData()); ArrayList<Double>
	 * temp= new ArrayList<Double>(); temp.addAll(items.get(2).getData());
	 * ArrayList<Double> pm10= new ArrayList<Double>();
	 * pm10.addAll(items.get(3).getData());
	 * 
	 * //Preparing validation dataset. ArrayList<Double> huV= new
	 * ArrayList<Double>(); huV.addAll(validationSet.get(0).getData());
	 * 
	 * ArrayList<Double> wsV= new ArrayList<Double>();
	 * wsV.addAll(validationSet.get(1).getData());
	 * 
	 * ArrayList<Double> tempV= new ArrayList<Double>();
	 * tempV.addAll(validationSet.get(2).getData());
	 * 
	 * ArrayList<Double> pm10V= new ArrayList<Double>();
	 * pm10V.addAll(validationSet.get(3).getData());
	 * 
	 * 
	 * // set the inputs int dataSetSize=pm10.size(); int
	 * ValidationDataSetSiz=500; //e=pm10V.size();
	 * 
	 * 
	 * double[][] d = new double[dataSetSize][4]; double[][] desiredOutputArray=
	 * new double[dataSetSize][1];
	 * 
	 * NormalizedField huNRM= new NormalizedField(
	 * NormalizationAction.Normalize, null, items.get(0).getItemMaxVal(),
	 * items.get(0).getItemMinVal(),1,0);
	 * 
	 * NormalizedField wsNRM= new NormalizedField(
	 * NormalizationAction.Normalize, null, items.get(1).getItemMaxVal(),
	 * items.get(1).getItemMinVal(),1,0);
	 * 
	 * NormalizedField tempNRM= new NormalizedField(
	 * NormalizationAction.Normalize, null, items.get(2).getItemMaxVal(),
	 * items.get(2).getItemMinVal(),1,0); NormalizedField pm10NRM= new
	 * NormalizedField( NormalizationAction.Normalize, null,
	 * items.get(3).getItemMaxVal(), items.get(3).getItemMinVal(),1,0);
	 * //Validation normalizers. NormalizedField huNRMV= new NormalizedField(
	 * NormalizationAction.Normalize, null,
	 * validationSet.get(0).getItemMaxVal(),
	 * validationSet.get(0).getItemMinVal(),1,0);
	 * 
	 * NormalizedField wsNRMV= new NormalizedField(
	 * NormalizationAction.Normalize, null,
	 * validationSet.get(1).getItemMaxVal(),
	 * validationSet.get(1).getItemMinVal(),1,0);
	 * 
	 * NormalizedField tempNRMV= new NormalizedField(
	 * NormalizationAction.Normalize, null,
	 * validationSet.get(2).getItemMaxVal(),
	 * validationSet.get(2).getItemMinVal(),1,0); NormalizedField pm10NRMV= new
	 * NormalizedField( NormalizationAction.Normalize, null,
	 * validationSet.get(3).getItemMaxVal(),
	 * validationSet.get(3).getItemMinVal(),1,0);
	 * 
	 * predictionMDL.setNormalizedPollutant(pm10NRMV);
	 * predictionMDL.setNormalizedHu(huNRMV);
	 * predictionMDL.setNormalizedTEMP(tempNRMV);
	 * predictionMDL.setNormalizedWS(wsNRMV);
	 * 
	 * for(int i=0;i<dataSetSize-NUMBER_OF_STEP_IN_ADVANCE;i++) { d[i][0]=
	 * huNRM.normalize(hu.get(i).doubleValue()); d[i][1]=
	 * wsNRM.normalize(ws.get(i).doubleValue()) ; d[i][2]=
	 * tempNRM.normalize(temp.get(i).doubleValue()); d[i][3]=
	 * pm10NRM.normalize(pm10.get(i).doubleValue());
	 * desiredOutputArray[i][0]=pm10NRM
	 * .normalize(pm10.get(i+NUMBER_OF_STEP_IN_ADVANCE).doubleValue()); }
	 * 
	 * final BasicMLDataSet set = new BasicMLDataSet();
	 * 
	 * final KMeansClustering kmeans = new KMeansClustering(2, set);
	 * 
	 * kmeans.iteration(100); //System.out.println("Final WCSS: " +
	 * kmeans.getWCSS());
	 * 
	 * // Display the cluster int i = 1; for (final MLCluster cluster :
	 * kmeans.getClusters()) { System.out.println("*** Cluster " + (i++) +
	 * " ***"); final MLDataSet ds = cluster.createDataSet(); final MLDataPair
	 * pair = BasicMLDataPair.createPair( ds.getInputSize(), ds.getIdealSize());
	 * for (int j = 0; j < ds.getRecordCount(); j++) { ds.getRecord(j, pair);
	 * System.out.println(Arrays.toString(pair.getInputArray()));
	 * 
	 * } }
	 * 
	 * }
	 */

}
