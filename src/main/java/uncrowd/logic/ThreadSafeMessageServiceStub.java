package uncrowd.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import uncrowd.logic.Entity.MessageEntity;

//@Service
public class ThreadSafeMessageServiceStub implements MessageService{
	private Map<String, MessageEntity> database;
	
	@PostConstruct
	public void init() {
		this.database = Collections.synchronizedMap(new HashMap<>()); 
//			= new Hashtable<>();// sooo 1996
	}
	
	
	@Override
	public MessageEntity addNewMessage(MessageEntity messageEntity) throws MessageAlreadyExistsException {
		if (this.database.containsKey(messageEntity.getMessage())) {
			throw new MessageAlreadyExistsException ("a message exists for: " + messageEntity.getMessage());
		}
		this.database.put(messageEntity.getMessage(), messageEntity);
		return this.database.get(messageEntity.getMessage());
	}

	@Override
	public MessageEntity getMessage(String name) throws MessageNotFoundException {
		MessageEntity rv =  this.database.get(name);
		if (rv == null) {
			throw new MessageNotFoundException("could not find message by name: " + name);
		}
		return rv;
	}

	@Override
	public List<MessageEntity> getAllMessages(int size, int page) {
		List<MessageEntity> list = null;
		
		synchronized (this.database) {
			list = new ArrayList<>(this.database.values() // collection of entities
			); // ArrayList of entities
		}
		return
			list
			.stream() // stream of entities
			.skip(size*page)
			.limit(size)
			.collect(Collectors.toList());
	}
	
	@Override
	public void cleanup() {
		this.database.clear();		
	}
	
	@Override
	public void updateMessage(String name, MessageEntity update) {
		synchronized (this.database) {
			MessageEntity existing = this.database.get(name);
			boolean dirty = false;
			if (update.getMoreAttributes() != null) {
				existing.setMoreAttributes(update.getMoreAttributes());
				dirty = true;
			}
			if (update.getX() != existing.getX()) {
				existing.setX(update.getX());
				dirty = true;
			}
			if (update.getY() != existing.getY()) {
				existing.setY(update.getY());
				dirty = true;
			}
			if (dirty) {
				this.database.put(name, existing);
			}
		}
	}

	@Override
	public void deleteMessage(String name) throws MessageNotFoundException {
		synchronized (this.database) {
			if (this.database.containsKey(name)) {
				this.database.remove(name);
			} else {
				throw new MessageNotFoundException("no message to delete by message: " + name);
			}
		}
	}
	
	@Override
	public  List<MessageEntity> searchAbcBiggerThan(int value, int size, int page) {
		List<MessageEntity> list = null;
		
		synchronized (this.database) {
			list = new ArrayList<>(this.database.values() // collection of entities
			); // ArrayList of entities
		}
		return
			list
				.stream() // stream of entities
				.filter(ent->ent.getMoreAttributes().get("abc") != null)
				.filter(ent->(int)ent.getMoreAttributes().get("abc") > value)
				.skip(size*page)
				.limit(size)
				.collect(Collectors.toList());
	}

}






