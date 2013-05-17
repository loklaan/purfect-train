package asgn2RollingStock;

import asgn2Exceptions.TrainException;

/**
 * A locomotive is a railway carriage with the ability to propel itself and pull
 * (or push) other carriages. Thus the primary distinguishing characteristic of
 * a locomotive is how much weight it can pull.<br/>
 * <br/>
 * 
 * Locomotives are classified by a two-character code:
 * <ul>
 * <li>A numeric "power class" in the range 1 to 9.</li>
 * <li>An alphabetic "engine type" consisting of either "E" for electric, "D"
 * for diesel or "S" for steam.</li>
 * </ul>
 * 
 * @author Murray Coggan - 8291951
 * 
 */
public class Locomotive extends RollingStock {
	private Integer power;
	private String engine;

	final int POWER_MULTIPLIER = 100;

	/**
	 * Constructs a new locomotive object with a fixed gross weight and
	 * classification code.
	 * 
	 * @param grossWeight
	 *            The freight car's gross weight (fully-laden), in tonnes.
	 * @param classification
	 *            The locomotive's two-character classification code.
	 * @throws TrainException
	 *             If the locomotive's weight is not strictly positive or if its
	 *             classification code is invalid
	 */
	public Locomotive(Integer grossWeight, String classification)
			throws TrainException {
		super(grossWeight);
		if (!isClassificationFormat(classification)) {
			throw new TrainException(
					"Classification must be in format like '4E'.");
		} else {
			setPower(classification);
			setEngine(classification);
		}
	}

	/**
	 * Sets a substring of the classification code as the Engine code.
	 * 
	 * @param classification
	 *            Locomotive classification code.
	 */
	private void setEngine(String classification) {
		this.engine = classification.substring(1);
	}

	/**
	 * Sets a substring of the classification code, cast as an integer,
	 * multiplied with a constant value, as the total pulling power of the
	 * locomotive.
	 * 
	 * @param classification
	 *            Locomotive classification code.
	 */
	private void setPower(String classification) {
		this.power = POWER_MULTIPLIER
				* Integer.valueOf(classification.substring(0, 1));
	}

	/**
	 * Determines if the specified classification string matches the required
	 * scope and format of a Locomotive classification.
	 * 
	 * @param classification
	 * @return True if the classification is a two character string that follows
	 *         the format of:
	 *         <ul>
	 *         <li>A numeric "power class" in the range 1 to 9.</li>
	 *         <li>An alphabetic "engine type" consisting of either "E" for
	 *         electric, "D" for diesel or "S" for steam.</li>
	 *         </ul>
	 */
	private boolean isClassificationFormat(String classification) {
		if (classification.length() == 2) {
			return classification.matches("[1-9][E|S|D]");
		}
		return false;
	}

	/**
	 * Returns how much total weight the locomotive can pull (including itself),
	 * calculated as explained above.
	 * 
	 * @return The locomotive's "pulling power" in tonnes.
	 */
	public Integer power() {
		return this.power;
	}

	/**
	 * Returns a human-readable description of the locomotive. This has the form
	 * "Loco(x)" where x is the locomotive's two-character classification code.
	 * 
	 * @return Loco(xx) <em>where <strong>xx</strong> is a classification code.
	 */
	@Override
	public String toString() {
		return "Loco(" + (power / POWER_MULTIPLIER) + engine + ")";
	}

}
