package uncrowd.logic;

public class MessageAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = -402044789717888327L;

	public MessageAlreadyExistsException() {
	}

	public MessageAlreadyExistsException(String message) {
		super(message);
	}

	public MessageAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public MessageAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
