package uncrowd.layout;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uncrowd.layout.to.BusinessIdTO;
import uncrowd.layout.to.UpdateFromBusinessTO;
import uncrowd.logic.BusinessId;
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
	public BusinessIdTO getBusinessId (HttpServletRequest request) throws MessageNotFoundException {
		
		BusinessId businessId = new BusinessId();
		businessId.setIp(request.getRemoteAddr());
		businessId = this.business.getBusinessId(businessId);
		
		return new BusinessIdTO(businessId);
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
