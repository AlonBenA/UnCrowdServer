package uncrowd.logic.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uncrowd.jpadal.BusinessDao;
import uncrowd.jpadal.LastDayCrowdDao;
import uncrowd.jpadal.MessageDao;
import uncrowd.jpadal.NumbersDao;
import uncrowd.logic.BusinessService;
import uncrowd.logic.UpdateFromBusiness;

@Service
public class JpaBusinessService implements BusinessService {
	private LastDayCrowdDao lastDayCrowdtable;
	private BusinessDao BusinessTable;
	
	@Autowired
	public JpaBusinessService(LastDayCrowdDao lastDayCrowd,BusinessDao BusinessTable) {
		super();
		this.lastDayCrowdtable = lastDayCrowd;
		this.BusinessTable = BusinessTable;
	}
	
	
	@Override
	public UpdateFromBusiness addNewUpdate(UpdateFromBusiness newUpdate) {
		
		
		
		
		
		
		//the Confirmation is set and send back
		newUpdate.setConfirmation("1");

		return newUpdate;
	}

	
	
}
