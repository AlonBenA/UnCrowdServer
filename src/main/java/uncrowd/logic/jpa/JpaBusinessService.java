package uncrowd.logic.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uncrowd.jpadal.LastDayCrowdDao;
import uncrowd.jpadal.MessageDao;
import uncrowd.jpadal.NumbersDao;
import uncrowd.logic.BusinessService;
import uncrowd.logic.Entity.UpdateFromBusinessEntity;

@Service
public class JpaBusinessService implements BusinessService {
	private LastDayCrowdDao lastDayCrowd;
	
	@Autowired
	public JpaBusinessService(LastDayCrowdDao lastDayCrowd) {
		super();
		this.lastDayCrowd = lastDayCrowd;
	}
	
	
	@Override
	public UpdateFromBusinessEntity addNewUpdate(UpdateFromBusinessEntity newUpdate) {
		
		
		
		//the Confirmation is set and send back
		newUpdate.setConfirmation("1");

		return newUpdate;
	}

}
