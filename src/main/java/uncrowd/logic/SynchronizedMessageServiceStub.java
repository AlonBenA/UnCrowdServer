package uncrowd.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import uncrowd.logic.entity.MessageEntity;

//@Service
public class SynchronizedMessageServiceStub implements MessageService{
	private Map<String, MessageEntity> database;
	
	@PostConstruct
	public void init() {
		this.database = new HashMap<>();
	}
	
	
	@Override
	public synchronized MessageEntity addNewMessage(MessageEntity messageEntity) throws MessageAlreadyExistsException{
		if (this.database.containsKey(messageEntity.getMessage())) {
			throw new MessageAlreadyExistsException ("a message exists for: " + messageEntity.getMessage());
		}
		this.database.put(messageEntity.getMessage(), messageEntity);
		return this.database.get(messageEntity.getMessage());
	}

	@Override
	public synchronized MessageEntity getMessage(String name) throws MessageNotFoundException {
		MessageEntity rv =  this.database.get(name);
		if (rv == null) {
			throw new MessageNotFoundException("could not find message by name: " + name);
		}
		return rv;
	}

	@Override
	public synchronized List<MessageEntity> getAllMessages(int size, int page) {
		return this.database
			.values() // collection of entities
			.stream() // stream of entities
			.skip(size*page)
			.limit(size)
			.collect(Collectors.toList());
	}
	
	@Override
	public synchronized void cleanup() {
		this.database.clear();		
	}
	
	@Override
	public synchronized void updateMessage(String name, MessageEntity update) {
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
	
	@Override
	public synchronized void deleteMessage(String name) throws MessageNotFoundException {
		if (this.database.containsKey(name)) {
			this.database.remove(name);
		}else {
			throw new MessageNotFoundException("no message to delete by message: " + name);
		}
	}
	
	@Override
	public synchronized List<MessageEntity> searchAbcBiggerThan(int value, int size, int page) {
		return this.database
				.values() // collection of entities
				.stream() // stream of entities
				.filter(ent->ent.getMoreAttributes().get("abc") != null)
				.filter(ent->(int)ent.getMoreAttributes().get("abc") > value)
				.skip(size*page)
				.limit(size)
				.collect(Collectors.toList());
	}
}






