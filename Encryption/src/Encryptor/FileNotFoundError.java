package Encryptor;

@SuppressWarnings("serial")
public class FileNotFoundError extends Exception {

	public FileNotFoundError() {
	}

	public FileNotFoundError(String message, Throwable throwable) {
		super(message, throwable);
	}
}
