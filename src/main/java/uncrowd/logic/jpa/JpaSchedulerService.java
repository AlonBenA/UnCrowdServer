package uncrowd.logic.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uncrowd.jpadal.BusinessDao;
import uncrowd.logic.SchedualerService;
import uncrowd.logic.Entity.BusinessEntity;

public class JpaSchedulerService implements SchedualerService{

	private BusinessDao businesses;

	@Autowired
	public JpaSchedulerService(BusinessDao businesses) {
		this.businesses = businesses;
	}
	
	@Override
	public void updateBusinessesNewTimePeriod() {
		// TODO Auto-generated method stub
		List<BusinessEntity> allList = new ArrayList<>();
		
		this.businesses.findAll()
			.forEach(allList::add);
		BusinessEntity[] needsUpdate = (BusinessEntity[]) allList.stream().filter(bis-> bis.getNeedsExpectedCountUpdate()).toArray();
		
		for(BusinessEntity bis : needsUpdate) {
			// TODO: Machine learning for all the "needsUpdate"
			bis.setExpectedCountTime(1700);
			bis.setExpectedCrowdCount(30);
			bis.setNeedsExpectedCountUpdate(false);
			
			// Updating the DB with the new details
			businesses.save(bis);
		}
	}

	@Override
	public void updateEndOfDay() {
		// TODO Move the last day crowd to history and calculate averages
		
	}
}
