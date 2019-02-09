package uncrowd.layout;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uncrowd.layout.to.BusinessTO;
import uncrowd.layout.to.TypeTO;
import uncrowd.logic.ClientService;
import uncrowd.logic.entity.BusinessEntity;
import uncrowd.logic.entity.BusinessTypeEntity;

@RestController
public class ClientsWebUI {
	private ClientService clientService;
	
	
	@Autowired
	public void setService(ClientService clientService) {
		this.clientService = clientService;
	}
	
	@PostConstruct
	public void initializeBusinesses() {
		this.clientService.addDeafultValues();
	}
	
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }
	
	// Local host 
	//http://localhost:8083/BusinessWithParameters/name/types/100/5/6
	@RequestMapping(
			method=RequestMethod.GET,
			path="/BusinessWithParameters/{name}/{distance}/{lat}/{lon}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<BusinessTO> getBusinessWithParameters (@PathVariable("name") String name,
			@PathVariable("distance") Integer distance,
			@PathVariable("lat") double lat,
			@PathVariable("lon") double lon,
			@RequestParam(name="types", required=false, defaultValue="") String types,
			@RequestParam(name="size", required=false, defaultValue="30") int size, 
			@RequestParam(name="page", required=false, defaultValue="0") int page	) {
		
		List<Long> typesIds = new ArrayList<>();
		
		// Checking there are types to filter
		if(!types.equals("")) {
			String[] typesIdsString = types.split(",");
			for(String id : typesIdsString) {
				try {
					typesIds.add(Long.parseLong(id));
				}catch(Exception ex) {
					System.err.println("RECEIVED WRONG TYPE ID, NOT A LONG!");
				}
			}
		}
		
		return convertBusinessEntityToTO(clientService.getBusinessesFiltered(lon, lat, name, typesIds, distance, size, page));
	}
	
	// Local host 
	//http://localhost:8083/AllBusinessTypes
	@RequestMapping(
			method=RequestMethod.GET,
			path="/AllBusinessTypes",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TypeTO> getAllBusinessTypes () {
		
		// Getting the types from the client service
		List<BusinessTypeEntity> rv = this.clientService.getBusinessesTypes();

		// Converting to TO objects:
		List<TypeTO> typesTO = new ArrayList<>();		
		rv.forEach(entity-> typesTO.add(new TypeTO(entity)));
		
		return typesTO;
	}
	
	// Local host 
	//http://localhost:8083/Alternatives/{id}
	@RequestMapping(
			method=RequestMethod.GET,
			path="/Alternatives/{id}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<BusinessTO> getAlternatives(@PathVariable("id") Long id) {
		
		return convertBusinessEntityToTO(clientService.getAlternatives(id));		
	}	
	
	// Local host 
	//http://localhost:8083/Businessinfo/id
	@RequestMapping(
			method=RequestMethod.GET,
			path="/Businessinfo/{id}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public BusinessTO Businessinfo (@PathVariable("id") Long id) {		
		return new BusinessTO(this.clientService.getBusinessUpdate(id));
	}

	// Local host 
	//http://localhost:8083/AllBusinesses/lat/lon?size=5&page=0
	@RequestMapping(
			method=RequestMethod.GET,
			path="/AllBusinesses/{lat}/{lon}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<BusinessTO> getAllBusinesses (@PathVariable("lat") double latitude,
			@PathVariable("lon") double longitude,
			@RequestParam(name="size", required=false, defaultValue="30") int size, 
			@RequestParam(name="page", required=false, defaultValue="0") int page	) throws Exception {
		System.out.println("getAllBusinesses received : lat = " + latitude + " lon = " + longitude);
		return convertBusinessEntityToTO(this.clientService.getAllBusinesses(longitude, latitude, size, page));
	}
	
	private List<BusinessTO> convertBusinessEntityToTO(List<BusinessEntity> entitiesList){
		List<BusinessTO> businessTO = new ArrayList<>();
		entitiesList.forEach(entity-> businessTO.add(new BusinessTO(entity)));
		
		return businessTO;
	}
}









