package uncrowd.logic.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uncrowd.jpadal.BusinessDao;
import uncrowd.jpadal.LastDayCrowdDao;
import uncrowd.jpadal.NumbersDao;
import uncrowd.logic.BusinessService;
import uncrowd.logic.UpdateFromBusiness;
import uncrowd.logic.Entity.BusinessEntity;
import uncrowd.logic.Entity.LastDayCrowdEntity;

@Service
public class JpaBusinessService implements BusinessService {
	private LastDayCrowdDao lastDayCrowdTable;
	private BusinessDao BusinessTable;
	private NumbersDao numbers;
	private static final Integer ENTER = 1;
	private static final Integer EXIT = 0;
	
	@Autowired
	public JpaBusinessService(LastDayCrowdDao lastDayCrowd,BusinessDao BusinessTable
			,NumbersDao numbers) {
		super();
		this.lastDayCrowdTable = lastDayCrowd;
		this.BusinessTable = BusinessTable;
		this.numbers = numbers;
	}
	
	
	@Override
	public UpdateFromBusiness addNewUpdate(UpdateFromBusiness newUpdate) {
		
		Optional<BusinessEntity> BusinessEntityOpt = BusinessTable.findById(newUpdate.getId());
		BusinessEntity Business = BusinessEntityOpt.get();
		Integer timeId = newUpdate.getDate();
		
		LastDayCrowdEntity enter = new LastDayCrowdEntity(newUpdate.getId(),ENTER,
				newUpdate.getNumberOFPeopleThatEnter(),	
				timeId,
				Business);
		
		LastDayCrowdEntity exit = new LastDayCrowdEntity(newUpdate.getId(),EXIT,
				newUpdate.getNumberOFPeopleThatExit(),	
				timeId,
				Business);
		
		Business.setCurrCrowdCount(newUpdate.getNumberOFPeople());
		
		
		lastDayCrowdTable.save(enter);
		lastDayCrowdTable.save(exit);
		BusinessTable.save(Business);
		
		
		//the Confirmation is set and send back
		newUpdate.setConfirmation("1");

		return newUpdate;
	}

	
	
}