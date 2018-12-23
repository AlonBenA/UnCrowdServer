package uncrowd.utils;

import java.time.LocalDateTime;

public class TimeUtils {
	// The interval in minutes between two crowd updates
	public static final int CROWD_UPDATES_INTERVAL_SECONDS = 60;
	public static final int CROWD_UPDATES_INTERVAL_MINUTES = CROWD_UPDATES_INTERVAL_SECONDS / 60;
	
	public static int getNextUpdateTime(int currentCrowdTime) {
		int currCrowdMinute = currentCrowdTime % 100, currCrowdHour = currentCrowdTime / 100;
		
		if(currCrowdMinute + CROWD_UPDATES_INTERVAL_MINUTES <= 59) {
			return currCrowdHour * 100 + currCrowdMinute + CROWD_UPDATES_INTERVAL_MINUTES;
		}else {
			return (currCrowdHour + 1) * 100 + 60 - (currCrowdMinute + CROWD_UPDATES_INTERVAL_MINUTES);
		}
	}
	
	public static int getCurrTime() {
		return LocalDateTime.now().toLocalTime().getHour() * 100 + 
				LocalDateTime.now().toLocalTime().getMinute();
	}
}
