package uncrowd.logic.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import uncrowd.jpadal.BusinessDao;
import uncrowd.jpadal.LastDayCrowdDao;
import uncrowd.logic.BusinessData;
import uncrowd.logic.BusinessService;
import uncrowd.logic.UpdateFromBusiness;
import uncrowd.logic.entity.BusinessEntity;
import uncrowd.logic.entity.LastDayCrowdEntity;
import uncrowd.utils.TimeUtils;

@Service
public class JpaBusinessService implements BusinessService {
	private LastDayCrowdDao lastDayCrowdTable;
	private BusinessDao BusinessTable;
	
	@Autowired
	public JpaBusinessService(LastDayCrowdDao lastDayCrowd,BusinessDao BusinessTable) {
		super();
		this.lastDayCrowdTable = lastDayCrowd;
		this.BusinessTable = BusinessTable;
	}
	
	
	@Override
	public UpdateFromBusiness addNewUpdate(UpdateFromBusiness newUpdate) {
		
		Optional<BusinessEntity> BusinessEntityOpt = BusinessTable.findById(newUpdate.getId());
			
		if (BusinessEntityOpt.isPresent())
		{
			BusinessEntity Business = BusinessEntityOpt.get();
			Integer timeId = newUpdate.getDate();
			
			LastDayCrowdEntity enter = new LastDayCrowdEntity(newUpdate.getId(),LastDayCrowdEntity.ENTERING_COSTUMERS_TYPE,
					newUpdate.getNumberOFPeopleThatEnter(),	
					timeId,
					Business);
			
			LastDayCrowdEntity exit = new LastDayCrowdEntity(newUpdate.getId(),LastDayCrowdEntity.EXITING_COSTUMERS_TYPE,
					newUpdate.getNumberOFPeopleThatExit(),	
					timeId,
					Business);
			
			LastDayCrowdEntity numberOfpeople = new LastDayCrowdEntity(newUpdate.getId(),LastDayCrowdEntity.COSTUMERS_COUNT_TYPE,
					newUpdate.getNumberOFPeople(),	
					timeId,
					Business);
			
			Business.setCurrCrowdCount(newUpdate.getNumberOFPeople());
			Business.setCurrCrowdTime(timeId);
			Business.setNeedsExpectedCountUpdate(true);
			
			lastDayCrowdTable.save(numberOfpeople);
			lastDayCrowdTable.save(enter);
			lastDayCrowdTable.save(exit);
			BusinessTable.save(Business);
			
			
			//the Confirmation is set and send back
			newUpdate.setConfirmation("1");
		}
		

		return newUpdate;
	}


	@Override
	public BusinessData getBusinessData(BusinessData businessId) {
        // Searching for the first business that is not fake or for tests
		List<BusinessEntity> realBusinesses = this.BusinessTable.findByIsMLTestBusinessFalseAndIsFakeBusinessFalseOrderById(PageRequest.of(0, 1));
		
		businessId.setId(realBusinesses.get(0).getId());
		
		businessId.setTime(TimeUtils.CROWD_UPDATES_INTERVAL_SECONDS);
		return businessId;
	}

	
	
}
