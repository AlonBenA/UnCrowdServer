package uncrowd.logic.ml;

public class Prediction {
	private int prediction;
	private int predictionTime;

	public int getPrediction() {
		return prediction;
	}

	public void setPrediction(int prediction) {
		this.prediction = prediction;
	}

	public int getPredictionTime() {
		return predictionTime;
	}

	public void setPredictionTime(int predictionTime) {
		this.predictionTime = predictionTime;
	}

	public Prediction(int prediction, int predictionTime) {
		this.predictionTime = predictionTime;
		this.prediction = prediction;
	}
}
