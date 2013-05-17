package asgn2Exceptions;

import java.io.Serializable;

/**
 * A simple class for exceptions thrown by railway shunting and boarding operations.
 * @author Lochlan Bunn - 8509719
 *
 */

public class TrainException extends Exception implements Serializable {

	// Suggested by Eclipse
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new TrainException object.
	 * 
	 * @param message
	 * an informative message describing the cause of the problem
	 */
	public TrainException(String message) {
		super("Train Exception: " + message);
	}
}