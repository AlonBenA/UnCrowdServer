package uncrowd.layout;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class WebUITests {	
	//private RestTemplate restTemplate; 
	
	@LocalServerPort
	private int port;
	
	//private String url;
		
	@PostConstruct
	public void init() {
		//this.restTemplate = new RestTemplate();
		
		//url = "http://localhost:" + port + "/messages";
	}
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testServerIsUp () throws Exception {
		
	}
}
















