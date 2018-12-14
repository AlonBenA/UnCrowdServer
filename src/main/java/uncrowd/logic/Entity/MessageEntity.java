package uncrowd.logic.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "MESSAGES")
public class MessageEntity {
	private String message;
	private Date creationDate;
	private Double x;
	private Double y;
	private Map<String, Object> moreAttributes;
	private String number;

	public MessageEntity() {
		this.creationDate = new Date();
		this.x = 0.0;
		this.y = 0.0;
		this.moreAttributes = new HashMap<>();
		this.moreAttributes.put("creator", "AdvancedMessageGenerator");
		this.moreAttributes.put("length", 5);
		this.moreAttributes.put("validUntil", "forever");
	}

	public MessageEntity(String message) {
		this();
		this.message = message;
	}

	@Id
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	@Transient
	public Map<String, Object> getMoreAttributes() {
		return moreAttributes;
	}

	public void setMoreAttributes(Map<String, Object> moreAttributes) {
		this.moreAttributes = moreAttributes;
	}

	@Lob
	public String getMoreAttributesJson() {
		try {
			return new ObjectMapper().writeValueAsString(this.moreAttributes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setMoreAttributesJson(String json) {
		try {
			this.moreAttributes = new ObjectMapper().readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "MessageEntity [message=" + message + ", creationDate=" + creationDate + ", x=" + x + ", y=" + y
				+ ", moreAttributes=" + moreAttributes + "]";
	}

}
