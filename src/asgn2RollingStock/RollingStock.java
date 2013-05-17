package asgn2RollingStock;

import asgn2Exceptions.TrainException;

/**
 * Rolling stock are the individual carriages from which a train is constructed.
 * This abstract class defines characteristics which they all share, most
 * notably having a known gross weight, measured here in tonnes. (There are, of
 * course many other important shared characteristics of railway carriages, such
 * as identifying codes, a certain number of wheels, the track gauge they're
 * designed for, etc, but we don't need these for this assignment.)
 * 
 * @author Murray Coggan - 8291951
 * 
 */
public abstract class RollingStock {

	private Integer grossWeight;

	public RollingStock(Integer grossWeight) throws TrainException {
		if (grossWeight < 1) {
			throw new TrainException(
					"The grossWeight must be a positive value, above 0.");
		} else {
			this.grossWeight = grossWeight;
		}
	}

	public Integer getGrossWeight() {
		return this.grossWeight;
	}

	@Override
	public abstract String toString();

}
