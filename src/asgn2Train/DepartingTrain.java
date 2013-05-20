package asgn2Train;

import java.util.ArrayList;
import java.util.List;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.FreightCar;
import asgn2RollingStock.Locomotive;
import asgn2RollingStock.PassengerCar;
import asgn2RollingStock.RollingStock;

public class DepartingTrain {
	private List<RollingStock> carriages;

	/**
	 * Constructs a (potential) train object containing no carriages (yet).
	 */
	public DepartingTrain() {
		this.carriages = new ArrayList<RollingStock>();
	}

	/**
	 * Adds a new carriage to the end of the train. However, a new carriage may
	 * be added only if the resulting train configuration is valid, as per the
	 * rules listed above. Furthermore, shunting operations may not be performed
	 * if there are passengers on the train.
	 * 
	 * @param newCarriage
	 *            The new carriage to be added.
	 * @throws TrainException
	 *             If adding the new carriage would produce an invalid train
	 *             configuration, or if there are passengers on the train.
	 */
	public void addCarriage(RollingStock newCarriage) throws TrainException {
		// TODO make a class to check config against carriage?
		if (newCarriage instanceof Locomotive && carriages.size() > 1) {
			throw new TrainException(
					"Locomotive must be added as the first carriage.");
		} else if (newCarriage instanceof FreightCar) {
			carriages.add(newCarriage);
		} else if (newCarriage instanceof PassengerCar) {
			if (carriages.size() > 1
					&& carriages.get(carriages.size() - 1) instanceof FreightCar) {
				throw new TrainException(
						"Passengers must be added after a Locomotive and before FreightCars.");
			}
			carriages.add(newCarriage);
		}
	}

}
