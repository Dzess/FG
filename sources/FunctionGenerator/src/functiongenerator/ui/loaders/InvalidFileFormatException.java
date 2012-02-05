package functiongenerator.ui.loaders;

import java.io.IOException;

/**
 * Something went wrong with loading the file.
 * 
 * <p>
 * Exception has been rewritten instead of using one from the ini4j library.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
public class InvalidFileFormatException extends IOException {

	private static final long serialVersionUID = -6738274797646324892L;

	public InvalidFileFormatException(String message, Throwable e) {
		super(message, e);
	}

	public InvalidFileFormatException(Throwable e) {
		super(e);
	}

	public InvalidFileFormatException(String message) {
		super(message);
	}

	public InvalidFileFormatException() {
		super();
	}

}