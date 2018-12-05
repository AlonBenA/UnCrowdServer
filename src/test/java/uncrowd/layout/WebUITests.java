package uncrowd.layout;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import uncrowd.layout.MessageTO;
import uncrowd.logic.Location;
import uncrowd.logic.MessageService;
import uncrowd.logic.Entity.MessageEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class WebUITests {
	@Autowired
	private MessageService messageService;
	
	private RestTemplate restTemplate; 
	
	@LocalServerPort
	private int port;
	
	private String url;
	
	private ObjectMapper jackson;
	
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		
		url = "http://localhost:" + port + "/messages";
		System.err.println(this.url);
		
		this.jackson = new ObjectMapper();
	}
	
	@Before
	public void setup() {
		
	}
	
	@After
	public void teardown() {
		// cleanup database
		this.messageService.cleanup();
	}
	
	@Test
	public void testServerIsUp () throws Exception {
		
	}
	
	@Test
	public void testGetAMessageSuccessfully() throws Exception{
		String name = "bobo";
		
//		Given The database contains {"message":"bobo"}
		this.messageService.addNewMessage (new MessageEntity(name));
		
		// When I GET /messages/bobo with headers Accept:application/json
		MessageTO actualMessage = this.restTemplate.getForObject(
				this.url + "/{name}", 
				MessageTO.class, 
				name);

		// then
		assertThat(actualMessage)
			.isNotNull()
			.extracting("message")
			.containsExactly(name);
	}

	@Test(expected=Exception.class)
	public void testGetAMessageWithInvalidName () throws Exception{
		// GET /messages/bobo
		this.restTemplate.getForObject(
				this.url + "/{name}", 
				MessageTO.class, 
				"bobo");
	}
	
	@Test
	public void testGetAllMessagesUsingPaginationWithDefaultSizeOfFirstPageSuccessfully () throws Exception{
		// given
		Stream.of("m1", "m2", "m99") // String stream
			.map(MessageEntity::new) // MessageEntity stream
			.forEach(msg->{
				try {
					this.messageService.addNewMessage(msg);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		
		// when
		MessageTO[] actualMessages = this.restTemplate.getForObject(
				this.url, 
				MessageTO[].class);
		
		// then
		assertThat(actualMessages)
			.isNotNull()
			.hasSize(3);
		
	}
	
	@Test
	public void testGetSomeMessagesUsingPaginationSuccessfully() throws Exception {
		int size = 3;
		
		// given
		IntStream.range(0, 10) // int stream
			.mapToObj(i->"message #" + i) // String stream
			.map(MessageEntity::new) // MessageEntity stream
			.forEach(this.messageService::addNewMessage);
		
		// when
		MessageTO[] actualMessages = this.restTemplate.getForObject(
				this.url + "?size={size}", 
				MessageTO[].class,
				size);
		
		// then
		assertThat(actualMessages)
			.isNotNull()
			.hasSize(size);
		
	}
	
	@Test
	public void testGetAllMessagesUsingPaginationOfSecondPageSuccessfully() {
		int size = 6;
		int page = 1;
		
		// given
		IntStream.range(0, 20) // int stream
			.mapToObj(i->"message #" + i) // String stream
			.map(MessageEntity::new) // MessageEntity stream
			.forEach(this.messageService::addNewMessage);
		
		// when
		MessageTO[] actualMessages = this.restTemplate.getForObject(
				this.url + "?size={size}&page={page}", 
				MessageTO[].class,
				size, page);
				
		// then
		assertThat(actualMessages)
			.isNotNull()
			.hasSize(size);
	}
	
	@Test(expected=Exception.class)
	public void testGetAllMessagesWithIinvalidPageSize () {
		// when
		this.restTemplate.getForObject(
				this.url + "?size={size}&page={page}", 
				MessageTO[].class,
				-6, 1);
	}
	
	@Test
	public void testSuccessfulCreationOfMessage () throws Exception{
		String name = "hello";
		double x = 1.0;
		double y = 1.0;
		
		//Given server is up
		
		//When I POST /messages 
		//	with headers 
		//	Accept:application/json 
		//	Content-Type: application/json
		//	And with body {"message":"hello", "location":{"x":1.0, "y":1.0}}
		MessageTO newMessage = new MessageTO(name);
		newMessage.setLocation(new Location(x, y));
		this.restTemplate.postForObject(
				this.url, 
				newMessage, 
				MessageTO.class);
		
		//Then the response status is 200 
		//And the database contains for name: "hello" 
		// the object {"message":"hello", "x":1.0, "y":1.0}
		MessageEntity actualValue = this.messageService.getMessage(name);
		assertThat(actualValue)
//			.isNotNull()
			.extracting("message", "x", "y")
			.containsExactly(name, x, y);
		

	}
	
	@Test(expected=Exception.class)
	public void testCreationOfMessageWithDuplicateKey() throws Exception{
		String name = "hello";
		double x = 1.0;
		double y = 1.0;
		
		//Given server is up
		//And database already contains a message with "message":"hello"
		this.messageService.addNewMessage(new MessageEntity(name));
		
		//When I POST /messages 
		//	with headers 
		//	Accept:application/json 
		//	Content-Type: application/json
		//	And with body {"message":"hello", "location":{"x":1.0, "y":1.0}}
		MessageTO newMessage = new MessageTO(name);
		newMessage.setLocation(new Location(x, y));
		try {
			this.restTemplate.postForObject(
					this.url, 
					newMessage, 
					MessageTO.class);
			
			//Then the response status <> 2xx 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testSuccessfulMessageUpdate() throws Exception{
		String name = "hello";
		
		//Given server is up 
		//And the database contains {"message":"hello", "x":1.0, "y":1.0}
		this.messageService.addNewMessage(
				this.jackson.readValue("{\"message\":\"hello\", \"x\":1.0, \"y\":1.0}", MessageEntity.class));
		
		// When I PUT /messages/hello
		// with headers
		// 	Content-Type: application/json
		// And with body {"message":"hello", "location":{"x":2.0, "y":2.5}, "moreAttributes":{"abc":123}}
		
//		MessageTO updatedMessage = new MessageTO(name);
//		updatedMessage.setLocation(new Location(2.0, 2.5));
//		Map<String, Object> moreAttributes = new HashMap<>();
//		moreAttributes.put("abc", 123);
//		updatedMessage.setMoreAttributes(moreAttributes);
		String updatedMessageJson = "{\"message\":\"hello\", \"location\":{\"x\":2.0, \"y\":2.5}, \"moreAttributes\":{\"abc\":123}}";
		MessageTO updatedMessage = this.jackson.readValue(updatedMessageJson, MessageTO.class);
		
		this.restTemplate.put(
				this.url + "/{name}", 
				updatedMessage, 
				name);
		
		// Then the response status is 200 
		//And the database contains for name: "hello" the object {"message":"hello", "x":2.0, "y":2.5, "moreAttributes":{"abc":123}}
		
		MessageEntity actualMessage = this.messageService.getMessage(name);
		actualMessage.setCreationDate(null);
		actualMessage.setNumber(null);
		String actualMessageJson = this.jackson.writeValueAsString(actualMessage);
		
		
		MessageEntity expectedMessage = this.jackson.readValue("{\"message\":\"hello\", \"x\":2.0, \"y\":2.5, \"moreAttributes\":{\"abc\":123}}", MessageEntity.class);
		expectedMessage.setCreationDate(null);
		String expectedMessageJson = this.jackson.writeValueAsString(expectedMessage);
		assertThat(actualMessageJson)
			.isEqualTo(expectedMessageJson);
	}
	
	
	@Test(expected=Exception.class)
	public void testUpdateNonExistingMessage() throws Exception{
		String name = "hello";
		
		//Given server is up 

		// When I PUT /messages/hello
		// with headers
		// 	Content-Type: application/json
		// And with body {"message":"hello", "location":{"x":2.0, "y":2.5}, "moreAttributes":{"abc":123}}
		String updatedMessageJson = "{\"message\":\"hello\", \"location\":{\"x\":2.0, \"y\":2.5}, \"moreAttributes\":{\"abc\":123}}";
		MessageTO updatedMessage = this.jackson.readValue(updatedMessageJson, MessageTO.class);
		
		this.restTemplate.put(
				this.url + "/{name}", 
				updatedMessage, 
				name);
		
		// Then the response status <> 2xx 
	}
	
	@Test
	public void testSuccessfulMessageDelete () throws Exception{
		String name = "hello";
		
		//Given server is up 
		//And the database contains {"message":"hello", "x":1.0, "y":1.0}
		this.messageService.addNewMessage(
				this.jackson.readValue("{\"message\":\"hello\", \"x\":1.0, \"y\":1.0}", MessageEntity.class));
		
		//When I DELETE /messages/hello
		this.restTemplate.delete(this.url + "/{name}", name);
		
		
		//Then the response status is 200 
		//And the database contains nothing for name: "hello"
		boolean success = false;
		try {
			this.messageService.getMessage(name);
			
		} catch (Exception e) {
			 success = true;
		}

		assertThat(success).isTrue();
	}
	
	@Test(expected=Exception.class)
	public void testDeleteNonExistingMessage () throws Exception{
		//Given server is up 

		//When I DELETE /messages/hello
		this.restTemplate.delete(this.url + "/{name}", "hello");
		
	}
	
	@Test
	public void testSuccessfulGetMessageThatMoreAttributes_abc_GreaterThan100() throws Exception{
		//Given server is up 
		//And the database contains 3 messages for which moreAttributes.abc > 100
		//And 3 more message that doesn't
		IntStream.range(0, 6)
			.mapToObj(i->(i <3)?50-i:200+i)
			.map(value->{
				Map<String, Object> moreAttributes = new HashMap<>();
				moreAttributes.put("abc", value);
				return moreAttributes;
			})
			.map(moreAttributes->{
				MessageEntity entity = new MessageEntity ("Message#" + moreAttributes.get("abc"));
				entity.setMoreAttributes(moreAttributes);
				return entity;
			})
			.forEach(this.messageService::addNewMessage);
		
		
		//When I GET /messages/searchAbcBiggerThan/100
		MessageTO[] actualValues = this.restTemplate.getForObject(
				this.url + "/searchAbcBiggerThan/{value}?page={page}&size={size}", 
				MessageTO[].class, 
				100, 0, 600);
		
		//Then the response status is 200 
		//And the response body contains an array of 3 messages
		assertThat(actualValues)
			.isNotNull()
			.hasSize(3);

	}
}
















