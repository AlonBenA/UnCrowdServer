package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;

import uncrowd.logic.Entity.MessageEntity;

public interface MessageDao extends CrudRepository<MessageEntity, String> {

}
