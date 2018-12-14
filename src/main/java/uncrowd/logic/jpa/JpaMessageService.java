package uncrowd.logic.jpa;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uncrowd.jpadal.GeneratedNumber;
import uncrowd.jpadal.MessageDao;
import uncrowd.jpadal.NumbersDao;
import uncrowd.logic.MessageAlreadyExistsException;
import uncrowd.logic.MessageNotFoundException;
import uncrowd.logic.MessageService;
import uncrowd.logic.entity.MessageEntity;

@Service
public class JpaMessageService implements MessageService{
	private MessageDao messages;
	private NumbersDao numbers;
	
	@Autowired
	public JpaMessageService(MessageDao messages, NumbersDao numbers) {
		super();
		this.messages = messages;
		this.numbers = numbers;
	}

	@Override
	@Transactional
	public MessageEntity addNewMessage(MessageEntity messageEntity) throws MessageAlreadyExistsException {
		if (!this.messages.existsById(messageEntity.getMessage())) {
			long number = this.numbers.save(new GeneratedNumber()).getNextValue();
			this.numbers.deleteById(number);
			messageEntity.setNumber("" + number);
			return this.messages.save(messageEntity);
		}else {
			throw new MessageAlreadyExistsException("message exisits with: " + messageEntity.getMessage()); 
		}
	}

	@Override
	@Transactional(readOnly=true)
	public MessageEntity getMessage(String name) throws MessageNotFoundException {
//		Optional<MessageEntity> opt = this.messages.findById(name);
//		if (opt.isPresent()) {
//			return opt.get();
//		}else {
//			throw new MessageNotFoundException("no message for: " + name);
//		}
		
		return 
			this.messages.findById(name)
			.orElseThrow(()->new MessageNotFoundException("no message for: " + name));
	}

	@Override
	@Transactional
	public void cleanup() {
		this.messages.deleteAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<MessageEntity> getAllMessages(int size, int page) {
		List<MessageEntity> allList = new ArrayList<>();
		
		this.messages.findAll()
			.forEach(allList::add);
		
		return allList
				.stream() // stream of entities
				.skip(size*page)
				.limit(size)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateMessage(String name, MessageEntity update) throws MessageNotFoundException {
		MessageEntity existing = getMessage(name);
		
		if (update.getMoreAttributes() != null) {
			existing.setMoreAttributes(update.getMoreAttributes());
		}
		
		if (update.getX() != existing.getX()) {
			existing.setX(update.getX());
		}

		if (update.getY() != existing.getY()) {
			existing.setY(update.getY());
		}
		
		this.messages.save(existing);
	}

	@Override
	@Transactional
	public void deleteMessage(String name) throws MessageNotFoundException {
		this.messages.deleteById(name);		
	}

	@Override
	@Transactional(readOnly=true)
	public List<MessageEntity> searchAbcBiggerThan(int value, int size, int page) {
		List<MessageEntity> allList = new ArrayList<>();
		
		this.messages.findAll()
			.forEach(allList::add);
		
		return allList
				.stream() // stream of entities
				.filter(ent->ent.getMoreAttributes().get("abc") != null)
				.filter(ent->(int)ent.getMoreAttributes().get("abc") > value)
				.skip(size*page)
				.limit(size)
				.collect(Collectors.toList());
	}

}
