package uncrowd.logic.jpa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uncrowd.jpadal.BusinessDao;
import uncrowd.jpadal.LastDayCrowdDao;
import uncrowd.logic.SchedualerService;
import uncrowd.logic.entity.AverageEntity;
import uncrowd.logic.entity.BusinessEntity;
import uncrowd.logic.entity.LastDayCrowdEntity;
import uncrowd.logic.ml.CrowdPredictor;
import uncrowd.logic.ml.Prediction;
import uncrowd.utils.TimeUtils;

@Service
public class JpaSchedulerService implements SchedualerService{

	private BusinessDao businesses;
	private LastDayCrowdDao lastDayCrowd;

	@Autowired
	public JpaSchedulerService(BusinessDao businesses, LastDayCrowdDao lastDayCrowd) {
		this.businesses = businesses;
		this.lastDayCrowd = lastDayCrowd;
	}
	
	@Override
	public void updateBusinessesNewTimePeriod() {
		List<BusinessEntity> allList = new ArrayList<>();
		
		this.businesses.findAll()
			.forEach(allList::add);
		List<BusinessEntity> needsUpdate = allList.stream().filter(bis-> bis.getNeedsExpectedCountUpdate()).collect(Collectors.toList());
		
		for(BusinessEntity bis : needsUpdate) {
			Calendar now = Calendar.getInstance();
			
			int day = now.get(Calendar.DAY_OF_MONTH);
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			if(bis.getIsMLTestBusiness() || bis.getIsFakeBusiness()) {
				int currCrowd = 0, currCrowdTime = 0;
				List<LastDayCrowdEntity> ldcUpdated = new ArrayList<>();
				int currTime = 
						TimeUtils.getCurrTime();
				
				for(LastDayCrowdEntity ldc : bis.getLastDayCrowd()) {
					if(currTime >= ldc.getTimeId() ) {
						ldcUpdated.add(ldc);
						if(ldc.getType() == LastDayCrowdEntity.COSTUMERS_COUNT_TYPE) {
							currCrowd = ldc.getCount();
							currCrowdTime = ldc.getTimeId();
						}
					}
				}
				bis.setCurrCrowdCount(currCrowd);
				bis.setCurrCrowdTime(currCrowdTime);
				bis.setLastDayCrowd(ldcUpdated);		
			}
			
			// Calling the predictor to predict the crowd count:
			Prediction prediction = new CrowdPredictor().predict(day, 
					month, 
					year, 
					bis.getLastDayCrowd(), 
					bis.getCurrCrowdCount(),
					bis.getCurrCrowdTime());
			
			// Updating the business entity
			bis.setExpectedCrowdCount(prediction.getPrediction());
			bis.setExpectedCountTime(prediction.getPredictionTime());

			if(!bis.getIsMLTestBusiness() && !bis.getIsFakeBusiness()) {
				// Indicating there is no need to update the current business
				bis.setNeedsExpectedCountUpdate(false);
			}
			
			updateCrowdLevel(bis);
			
			// Updating the DB with the new details
			businesses.save(bis);
		}
	}

	@Override
	public void updateEndOfDay() {
		List<BusinessEntity> allList = new ArrayList<>();
		 
		this.businesses.findAll()
			.forEach(allList::add);
		
		for(BusinessEntity bis : allList) {
			lastDayCrowd.deleteAll(bis.getLastDayCrowd());
			bis.setLastDayCrowd(null);
			businesses.save(bis);
		}
	}
	
	@VisibleForTesting
	public void updateCrowdLevel(BusinessEntity bis) {
		List<AverageEntity> averages = bis.getAverages();
		int minAverage = -1;
		int maxAverage = -1;
		int crowdLevel = 1;
		
		for (AverageEntity average : averages) {
			if (average.getAverage() > maxAverage) {
				maxAverage = average.getAverage();
			}
			
			if (average.getAverage() < minAverage || minAverage == -1) {
				minAverage = average.getAverage();
			}
		}
		
		int averagesDifference = maxAverage - minAverage;
		
		if (averagesDifference > 0) {
			if (bis.getCurrCrowdCount() >= maxAverage) {
				crowdLevel = 5;
			} else if (bis.getCurrCrowdCount() <= minAverage) {
				crowdLevel = 1;
			} else {
				crowdLevel = (int)Math.ceil((double)(bis.getCurrCrowdCount() - minAverage) / (double)(averagesDifference / 5.0));
			}
		}
		
		bis.setCurrCrowdLevel(crowdLevel);
	}
}
