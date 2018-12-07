package uncrowd.layout;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uncrowd.logic.BusinessService;
import uncrowd.logic.ClientService;
import uncrowd.logic.MessageAlreadyExistsException;
import uncrowd.logic.MessageNotFoundException;
import uncrowd.logic.MessageService;
import uncrowd.logic.Entity.BusinessEntity;
import uncrowd.logic.Entity.BusinessTypeEntity;
import uncrowd.logic.Entity.MessageEntity;

@RestController
public class WebUI {
	private MessageService messages;
	private BusinessService business;
	private ClientService clientService;
	
	
	@Autowired
	public void setMessages(MessageService messages,BusinessService business, ClientService clientService) {
		this.messages = messages;
		this.business = business;
		this.clientService = clientService;
	}
	
	
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }
	
	// Local host 
	//http://localhost:8083/BusinessID
	@RequestMapping(
			method=RequestMethod.GET,
			path="/BusinessID",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public MessageTO getBusinessId (HttpServletRequest request) throws MessageNotFoundException {
		
		System.out.println("\n\n\n************** Remote Address: " + request.getRemoteAddr());
		
		MessageEntity rv = this.messages.getMessage("BusinessID");
		
		return new MessageTO(rv);
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
	
	// Local host 
	//http://localhost:8083/BusinessWithParameters/name/types/100/5/6
	@RequestMapping(
			method=RequestMethod.GET,
			path="/BusinessWithParameters/{name}/{types}/{distance}/{lat}/{lon}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public MessageTO getBusinessWithParameters (@PathVariable("name") String name,
			@PathVariable("types") String types,
			@PathVariable("distance") String distance,
			@PathVariable("lat") double lat,
			@PathVariable("lon") double lon,
			@RequestParam(name="size", required=false, defaultValue="30") int size, 
			@RequestParam(name="page", required=false, defaultValue="0") int page	) throws MessageNotFoundException {
		
		
		MessageEntity rv = this.messages.getMessage(name+types+distance+lat+lon+size);
		
		return new MessageTO(rv);
	}
	
	
	
	// Local host 
	//http://localhost:8083/AllBusinessTypes
	@RequestMapping(
			method=RequestMethod.GET,
			path="/AllBusinessTypes",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TypeTO> getAllBusinessTypes () throws MessageNotFoundException {
		
		List<BusinessTypeEntity> rv = this.clientService.getBusinessesTypes();

		List<TypeTO> typesTO = new ArrayList<>();
		
		rv.forEach(entity-> typesTO.add(new TypeTO(entity)));
		
		return typesTO;
	}
	
	
	// Local host 
	//http://localhost:8083/id
	@RequestMapping(
			method=RequestMethod.GET,
			path="/Businessinfo/{id}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public BusinessTO Businessinfo (@PathVariable("id") Long id) throws MessageNotFoundException {		
		return new BusinessTO(this.clientService.getBusinessUpdate(id));
	}
	
	// Local host 
	//http://localhost:8083/timeToUpdate
	@RequestMapping(
			method=RequestMethod.PUT,
			path="/timeToUpdate",
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public void timeToUpdate (
			@RequestBody MessageTO updatedMessage) throws Exception {
		
		System.out.println("\n\n time to update \n\n");
		
		this.messages.updateMessage("time to update", updatedMessage.toEntity());
	}

	// Local host 
	//http://localhost:8083/AllBusinesses/lat/lon/size/page
	@RequestMapping(
			method=RequestMethod.GET,
			path="/AllBusinesses",
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<BusinessTO> getAllBusinesses (@PathVariable("lat") double latitude,
			@PathVariable("lon") double longitude,
			@RequestParam(name="size", required=false, defaultValue="30") int size, 
			@RequestParam(name="page", required=false, defaultValue="0") int page	) throws Exception {
		
		List<BusinessEntity> entitiesList = this.clientService.getAllBusinesses(longitude, latitude, size, page);
		
		List<BusinessTO> businessTO = new ArrayList<>();
		entitiesList.forEach(entity-> businessTO.add(new BusinessTO(entity)));
		
		return businessTO;
	}
	
	// Local host 
	//http://localhost:8083/deafults
	@RequestMapping(
			method=RequestMethod.PUT,
			path="/deafults")
	public void addDeafultValues () throws Exception {		
		this.clientService.addDeafultValues();
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









