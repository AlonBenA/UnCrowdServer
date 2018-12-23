package uncrowd.logic.jpa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uncrowd.jpadal.BusinessDao;
import uncrowd.jpadal.LastDayCrowdDao;
import uncrowd.logic.SchedualerService;
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
				// TODO: Remove this, this is only for testing
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

			// TODO: Get this back when working with a real system
			// Indicating there is no need to update the current business
			// bis.setNeedsExpectedCountUpdate(false);
			
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
			// TODO: Calculate averages
			lastDayCrowd.deleteAll(bis.getLastDayCrowd());
			bis.setLastDayCrowd(null);
			businesses.save(bis);
		}
	}
}
