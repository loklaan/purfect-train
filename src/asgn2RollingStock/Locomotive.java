package asgn2RollingStock;

import asgn2Exceptions.TrainException;

/**
 * A locomotive is a railway carriage with the ability to propel itself and pull
 * (or push) other carriages. Thus the primary distinguishing characteristic of
 * a locomotive is how much weight it can pull.<br/>
 * <br/>
 * 
 * Locomotives are classified by a two-character code:<br/>
 * <li>A numeric "power class" in the range 1 to 9.</li>
 * <li>An alphabetic "engine type" consisting of either "E"
 * for electric, "D" for diesel or "S" for steam.</li>
 * 
 * @author Murray Coggan - 8291951
 * 
 */
public class Locomotive extends RollingStock {
	private Integer power;
	private String engine;
	
	final int POWER_MULTIPLIER = 100;

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

	private String setEngine(String classification) {
		return this.engine = classification.substring(1);
	}

	private Integer setPower(String classification) {
		return this.power = POWER_MULTIPLIER * Integer.valueOf(classification.substring(0, 1));
	}

	private boolean isClassificationFormat(String classification) {
		if (classification.length() == 2) {
			return classification.matches("[1-9][E|S|D]");
		}
		return false;
	}

	public Integer power() {
		return this.power;
	}

	@Override
	public String toString() {
		return "Loco(" + (power / POWER_MULTIPLIER) + engine + ")";
	}

}
