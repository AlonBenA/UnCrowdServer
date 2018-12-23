package uncrowd.logic.fakes;

import java.util.ArrayList;
import java.util.List;

import uncrowd.logic.entity.LastDayCrowdEntity;
import uncrowd.utils.TimeUtils;

public class LastDayCrowdFakeCreator {
	private int[][] crowdCounts = { 
			{2, 3, 4, 2, 1, 1, 5, 12, 20, 23, 22, 21, 25, 30, 20, 17, 20, 10, 7, 7, 5, 2, 4, 1, 5, 10, 12, 13, 12, 12, 12, 10, 7, 8, 5, 2, 1, 1, 1, 1},
			{0, 0, 0, 0, 1, 2, 3, 1, 2, 1, 3, 4, 2, 1, 5, 2, 2, 3, 4, 5, 7, 8, 9, 5, 3, 1, 1, 2, 0, 0, 0, 0, 2, 3, 4, 5, 4, 3, 2, 1},
			{0, 1, 2, 1, 2, 1, 1, 3, 2, 3, 4, 2, 3, 3, 2, 1, 0, 0, 0, 0, 0, 1, 2, 3, 3, 2, 1, 2, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
			{0, 2, 3, 10, 14, 30, 29, 34, 20, 12, 10, 13, 10, 7, 3, 0, 0, 0, 0, 0, 0, 3, 4, 2, 1, 3, 4, 2, 0, 3, 0, 1, 1, 2, 2, 2, 2, 2, 1, 1}
	};
	
	private int[][] crowdEntering = {
			{3, 1, 1, 1, 0, 5, 9, 8, 3, 2, 2, 4, 10, 0, 1, 3, 2, 2, 1, 0, 0, 2, 0, 4, 5, 2, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1},
			{0, 0, 0, 1, 1, 1, 0, 2, 0, 2, 1, 0, 0, 4, 0, 0, 1, 1, 1, 2, 1, 2, 0, 0, 0, 0, 1, 0, 1, 1, 1, 2, 1, 1, 1, 0, 0, 1, 0, 0},
			{1, 1, 0, 1, 0, 0, 2, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 2, 1, 0, 0, 0, 0}, 
			{2, 1, 7, 4, 17, 2, 5, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 0, 0, 2, 1, 0, 1, 4, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0}
	};
	
	private int[][] crowdExiting = {
			{2, 0, 3, 2, 0, 1, 2, 0, 0, 3, 1, 0, 5, 10, 4, 0, 12, 5, 1, 2, 3, 0, 3, 0, 0, 0, 0, 2, 1, 1, 1, 2, 3, 0, 3, 3, 1, 0, 1, 1}, 
			{0, 0, 0, 0, 0, 0, 2, 1, 1, 0, 0, 2, 1, 0, 3, 0, 0, 0, 0, 0, 0, 1, 4, 2, 2, 1, 0, 2, 1, 1, 1, 0, 0, 0, 0, 1, 1, 2, 1, 0},
			{0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 2, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 3, 0, 14, 8, 2, 0, 3, 3, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 2, 3, 1, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0}
	};
	
	List<List<LastDayCrowdEntity>> fakeLastDayCrowdEntities;
	
	private int nextFakeBusiness = 0;
	
	public LastDayCrowdFakeCreator() {
		fakeLastDayCrowdEntities = new ArrayList<List<LastDayCrowdEntity>>(crowdCounts.length);
		
		for(int i = 0 ; i < crowdCounts.length; i ++) {
			List<LastDayCrowdEntity> currList = new ArrayList<>();
			for(int j = 0; j < crowdCounts[i].length; j ++) {
				LastDayCrowdEntity ldc = new LastDayCrowdEntity();
				ldc.setCount(crowdExiting[i][j]);
				ldc.setType(LastDayCrowdEntity.EXITING_COSTUMERS_TYPE);
				currList.add(ldc);
				
				ldc = new LastDayCrowdEntity();
				ldc.setCount(crowdEntering[i][j]);
				ldc.setType(LastDayCrowdEntity.ENTERING_COSTUMERS_TYPE);
				currList.add(ldc);
				
				ldc = new LastDayCrowdEntity();
				ldc.setCount(crowdCounts[i][j]);
				ldc.setType(LastDayCrowdEntity.COSTUMERS_COUNT_TYPE);
				currList.add(ldc);	
			}
			fakeLastDayCrowdEntities.add(currList);
		}
	}
	
	List<LastDayCrowdEntity> getRandomLastDayCrowd(){
		int randomListIndex = nextFakeBusiness++;

		if(nextFakeBusiness == crowdCounts.length) {
			nextFakeBusiness = 0;
		}
		
		ArrayList<LastDayCrowdEntity> originalLastDayCrowd = new ArrayList<>(fakeLastDayCrowdEntities.get(randomListIndex));
		ArrayList<LastDayCrowdEntity> fullDayLastDayCrowd = new ArrayList<>();

		int currTime = TimeUtils.getCurrTime();
		int originalListIndex = 0;
		
		// Filling a list with all of this day's remaining time slots with duplicates of the random list
		for(; currTime < 2359; currTime = TimeUtils.getNextUpdateTime(currTime)) {
			for(int i = 0; i < 3; i ++) {
				// Creating a copy of the original value and assigning it the next time slot
				LastDayCrowdEntity currEntityCopy = new LastDayCrowdEntity(originalLastDayCrowd.get(originalListIndex));
				currEntityCopy.setTimeId(currTime);
				fullDayLastDayCrowd.add(currEntityCopy);
				
				// Checking if the list is finished
				if(++originalListIndex >= originalLastDayCrowd.size()) {
					// Cycling to the beginning all over again
					originalListIndex = 0;
				}
			}
		}
		
		return fullDayLastDayCrowd;
	}

}
