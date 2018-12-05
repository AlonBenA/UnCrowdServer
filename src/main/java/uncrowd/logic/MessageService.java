package uncrowd.logic;

import java.util.List;

import uncrowd.logic.Entity.MessageEntity;

public interface MessageService {

	public MessageEntity addNewMessage(MessageEntity messageEntity) throws MessageAlreadyExistsException;

	public MessageEntity getMessage(String name) throws MessageNotFoundException;

	public void cleanup();

	public List<MessageEntity> getAllMessages(int size, int page);

	public void updateMessage(String name, MessageEntity entity) throws MessageNotFoundException;

	public void deleteMessage(String name) throws MessageNotFoundException;

	public List<MessageEntity> searchAbcBiggerThan(int value, int size, int page);

}
