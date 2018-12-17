package uncrowd.layout;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class WebUITests {	
	private RestTemplate restTemplate; 
	
	@LocalServerPort
	private int port;
	
	private String url;
		
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		
		url = "http://localhost:" + port + "/messages";
		System.err.println(this.url);
	}
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testServerIsUp () throws Exception {
		
	}
	
	@Test(expected=Exception.class)
	public void testDeleteNonExistingMessage () throws Exception{
		//Given server is up 

		//When I DELETE /messages/hello
		this.restTemplate.delete(this.url + "/{name}", "hello");
		
	}
}
















