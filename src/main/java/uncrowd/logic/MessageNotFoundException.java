package uncrowd.logic;

public class MessageNotFoundException extends Exception {
	private static final long serialVersionUID = -4402147946901277231L;

	public MessageNotFoundException() {
		super();
	}

	public MessageNotFoundException(String message) {
		super(message);
	}

	public MessageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageNotFoundException(Throwable cause) {
		super(cause);
	}

}
