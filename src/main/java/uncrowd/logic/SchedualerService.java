package uncrowd.logic;

public interface SchedualerService{

	/**
	 * A method invoking an update to all businesses of the expected crowd count on the next time unit
	 * using machine learning algorithm and calculating the crowd level.
	 */
	void updateBusinessesNewTimePeriod();
	
	/**
	 * A method called every end of day,
	 * calculating new averages, clearing last day data that is not needed anymore.
	 */
	void updateEndOfDay();
}
