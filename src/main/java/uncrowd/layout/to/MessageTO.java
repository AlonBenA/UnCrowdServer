package uncrowd.layout.to;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import uncrowd.logic.Location;
import uncrowd.logic.entity.MessageEntity;

public class MessageTO {
	private String message;
	private Date creationDate;
	private Location location;
	private Map<String, Object> moreAttributes;

	public MessageTO() {
		this.creationDate = new Date();
		this.location = new Location(0, 0);
		this.moreAttributes = new HashMap<>();
		this.moreAttributes.put("creator", "AdvancedMessageGenerator");
		this.moreAttributes.put("length", 5);
		this.moreAttributes.put("validUntil", "forever");
	}

	public MessageTO(String message) {
		this();
		this.message = message;
	}

	public MessageTO(MessageEntity entity) {
		this();
		if (entity != null) {
			this.creationDate = entity.getCreationDate();
			this.location = new Location(entity.getX(), entity.getY());
			this.message = entity.getMessage();
			this.moreAttributes = entity.getMoreAttributes();
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Map<String, Object> getMoreAttributes() {
		return moreAttributes;
	}

	public void setMoreAttributes(Map<String, Object> moreAttributes) {
		this.moreAttributes = moreAttributes;
	}

	public MessageEntity toEntity () {
		MessageEntity rv = new MessageEntity();
		rv.setCreationDate(this.creationDate);
		rv.setMessage(this.message);
		rv.setMoreAttributes(this.moreAttributes);
		rv.setX(this.location.getX());
		rv.setY(this.location.getY());
		return rv;
	}
	
	@Override
	public String toString() {
		return message;
	}

}
