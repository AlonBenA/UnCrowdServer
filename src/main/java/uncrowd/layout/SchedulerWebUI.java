package uncrowd.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uncrowd.logic.MessageAlreadyExistsException;
import uncrowd.logic.MessageNotFoundException;
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
	
	@ExceptionHandler//(MessageNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleException (MessageNotFoundException e) {
		String message = e.getMessage();
		if (message == null) {
			message = "There is no relevant message";
		}
		return new ErrorMessage(message);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleException (MessageAlreadyExistsException e) {
		String message = e.getMessage();
		if (message == null) {
			message = "There is no relevant message";
		}
		return new ErrorMessage(message);
	}
}
