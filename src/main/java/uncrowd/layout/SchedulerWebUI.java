package uncrowd.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uncrowd.logic.SchedualerService;

@RestController
public class SchedulerWebUI {
	private SchedualerService schedualerService;
	
	@Autowired
	public void setService(SchedualerService schedualerService) {
		this.schedualerService = schedualerService;
	}
	
	// Local host 
	//http://localhost:8083/timeToUpdate
	@RequestMapping(
			method=RequestMethod.POST,
			path="/updatePredictions")
	public void updatePredictions () throws Exception {
		
		System.out.println("\n\n Updating Predictions \n\n");
		this.schedualerService.updateBusinessesNewTimePeriod();
	}
	
	// Local host 
	//http://localhost:8083/updatePredictions
	@RequestMapping(
			method=RequestMethod.POST,
			path="/endOfDay")
	public void endOfDay () throws Exception {
		
		System.out.println("\n\n End of day update \n\n");
		// TODO: Remove from comment after done testing
		//this.schedualerService.updateEndOfDay();
	}
}
