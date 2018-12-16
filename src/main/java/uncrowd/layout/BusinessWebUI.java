package uncrowd.layout;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uncrowd.layout.to.BusinessDataTO;
import uncrowd.layout.to.UpdateFromBusinessTO;
import uncrowd.logic.BusinessData;
import uncrowd.logic.BusinessService;
import uncrowd.logic.MessageNotFoundException;


@RestController
public class BusinessWebUI {
	private BusinessService business;
	
	
	@Autowired
	public void setMessages(BusinessService business) {
		this.business = business;
	}
	
	
	// Local host 
	//http://localhost:8083/BusinessID
	@RequestMapping(
			method=RequestMethod.GET,
			path="/BusinessID",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public BusinessDataTO getBusinessId (HttpServletRequest request) throws MessageNotFoundException {
		
		BusinessData Data = new BusinessData();
		Data.setIp(request.getRemoteAddr());
		Data = this.business.getBusinessData(Data);
		
		return new BusinessDataTO(Data);
	}
	
	// Local host 
	//http://localhost:8083/upateFromBusiness
	@RequestMapping(
			method=RequestMethod.POST,
			path="/updateFromBusiness",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public UpdateFromBusinessTO upateFromBusiness (@RequestBody UpdateFromBusinessTO newUpdate) {
		//HttpServletRequest request
		//System.out.println("\n\n\n upate From Business ************** Remote Address: " + request.getRemoteAddr());
		
		return new UpdateFromBusinessTO(
				this.business.addNewUpdate(newUpdate.toEntity()));
	}
}
