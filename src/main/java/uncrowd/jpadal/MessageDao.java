package uncrowd.jpadal;

import org.springframework.data.repository.CrudRepository;

import uncrowd.logic.entity.MessageEntity;

public interface MessageDao extends CrudRepository<MessageEntity, String> {

}
