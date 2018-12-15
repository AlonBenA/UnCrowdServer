package uncrowd.logic.ml;

import java.util.ArrayList;
import java.util.List;

import uncrowd.logic.entity.LastDayCrowdEntity;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.timeseries.WekaForecaster;

public class CrowdPredictor {
	
	// The number of minutes to add to the current count in order to get the prediction time
	private static final int PREDICTION_INTERVAL_MIN = 30;
	
	public Prediction predict(int day, int month, int year, List<LastDayCrowdEntity> training, int currCrowdCount, int currCrowdCountTime) {
		Prediction prediction = null;
		
		if(training != null && training.size() > 0) {
			
			// Define the feature and label attributes
			ArrayList<Attribute> attributes = new ArrayList<>();
			Attribute dateAttribute = new Attribute("date", "MM/dd/yy,HH:mm:ss");
			attributes.add(dateAttribute);
			Attribute countAttribute = new Attribute("count");
			attributes.add(countAttribute);

			Instances trainingIncomingDataset = new Instances("trainDataIncoming" + day, attributes, 100);
			trainingIncomingDataset.setClassIndex(trainingIncomingDataset.numAttributes() - 1);
			
			Instances trainingOutgoingDataset = new Instances("trainDataOutgoing" + day, attributes, 100);
			trainingIncomingDataset.setClassIndex(trainingIncomingDataset.numAttributes() - 1);
			
			try {
				// Going over the training set and dividing it into incoming and outgoing datasets
				for(LastDayCrowdEntity currEntity : training){
					
					// Assembling the time of the current record
					String date = String.format("%02d/%02d/%02d,%02d:%02d:%02d", month, day, year, currEntity.getTimeId() / 100, currEntity.getTimeId() % 100, 0);
					
					Instance instance = new DenseInstance(2);
					instance.setValue(dateAttribute, dateAttribute.parseDate(date));
					instance.setValue(countAttribute, currEntity.getCount());

					if(currEntity.getType() == LastDayCrowdEntity.ENTERING_COSTUMERS_TYPE){
						trainingIncomingDataset.add(instance);
					}else if(currEntity.getType() == LastDayCrowdEntity.EXITING_COSTUMERS_TYPE){
						trainingOutgoingDataset.add(instance);
					}
				}
				
				// Predicting the number of costumers entering and exiting
				double predictedEntering = predictFromTraining(trainingIncomingDataset);
				double predictedExiting  = predictFromTraining(trainingOutgoingDataset);
				
				// Calculating the difference between the predicted entering
				// (the change predicted from the current count)
				double predictedDiffFromCurrCount = (predictedEntering - predictedExiting);
				
				// Adding the difference with the current crowd count and rounding the result
				// (if the count results in a negative number, returning 0)
				int predictionCount = (int)Math.round(Math.max(0, currCrowdCount + predictedDiffFromCurrCount));
				// Calculating the time for the next prediction 
				// (by adding PREDICTION_INTERVAL_MIN to the last crowd update time)
				int predictionTime = getPredictionTime(currCrowdCountTime); 
				
				prediction = new Prediction(predictionCount, 
						predictionTime);
			}catch(Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		return prediction;
	}
	
	private int getPredictionTime(int currentCrowdTime) {
		int currCrowdMinute = currentCrowdTime % 100, currCrowdHour = currentCrowdTime / 100;
		
		if(currCrowdMinute + PREDICTION_INTERVAL_MIN <= 59) {
			return currCrowdHour * 100 + currCrowdMinute + PREDICTION_INTERVAL_MIN;
		}else {
			return (currCrowdHour + 1) * 100 + 60 - (currCrowdMinute + PREDICTION_INTERVAL_MIN);
		}
	}
	
	/**
	 * This method predicts the next value from the given training set
	 * @param training Instances, The training set used to train the machine that will produce the next value
	 * @return double, The prediction of entering or exiting costumers in the next time unit
	 */
	private double predictFromTraining(Instances training) {
		double predictedValue = -1;
		try{
			// new forecaster
	        WekaForecaster forecaster = new WekaForecaster();

	        // Set the targets we want to forecast
	        forecaster.setFieldsToForecast("count");
	        
	        // Default underlying classifier is SMOreg (SVM) - we'll use
	        // gaussian processes for regression instead
	        forecaster.setBaseForecaster(new GaussianProcesses());
	        forecaster.getTSLagMaker().setTimeStampField("date"); // date time stamp
	        
	        //System.out.println(dateFormat.format(new Date()) + " buildForecaster");
	        
	        // build the model
	        forecaster.buildForecaster(training, System.out);

	        //System.out.println(dateFormat.format(new Date()) + " primeForecaster");
	        
	        forecaster.primeForecaster(training);
	        
	        //System.out.println(dateFormat.format(new Date()) + " forecaster.forecast");
	        
	        // forecast for 1 unit beyond the end of the
	        // training data
	        List<List<NumericPrediction>> forecast = forecaster.forecast(1, System.out);
	        
	        //System.out.println(dateFormat.format(new Date()) + " forecast.get");
	        
	        // Getting the prediction:
	        List<NumericPrediction> predsAtStep = forecast.get(0);
	        NumericPrediction predForTarget = predsAtStep.get(0);
          	//System.out.print("" + predForTarget.predicted() + " ");
          	predictedValue = predForTarget.predicted();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return predictedValue;
	}
}
