package asgn2Train;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.FreightCar;
import asgn2RollingStock.Locomotive;
import asgn2RollingStock.PassengerCar;
import asgn2RollingStock.RollingStock;

public class DepartingTrain {
	private List<RollingStock> carriages;
	private Integer numberOnBoard;
	private Integer numberOfSeats;

	/**
	 * Constructs a (potential) train object containing no carriages (yet).
	 */
	public DepartingTrain() {
		this.carriages = new ArrayList<RollingStock>();
		this.numberOfSeats = 0;
		this.numberOnBoard = 0;
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
		if (!(newCarriage instanceof Locomotive) && carriages.size() == 0) {
			throw new TrainException("First carriage must be a Locomotive");
		} else if (this.numberOnBoard > 0) {
			throw new TrainException(
					"Cannot add carriages once passengers are aboard the train.");
		}
		if (newCarriage instanceof Locomotive) {
			if (carriages.size() > 1) {
				throw new TrainException(
						"Locomotive must be added as the first carriage.");
			} else {
				carriages.add(newCarriage);
			}
		} else if (newCarriage instanceof FreightCar) {
			carriages.add(newCarriage);
		} else if (newCarriage instanceof PassengerCar) {
			if (carriages.size() > 1 && getLastCarriage() instanceof FreightCar) {
				throw new TrainException(
						"Passengers must be added after a Locomotive and before FreightCars.");
			}
			carriages.add(newCarriage);
			this.numberOfSeats += ((PassengerCar) getLastCarriage())
					.numberOfSeats();
			this.numberOnBoard += ((PassengerCar) getLastCarriage())
					.numberOnBoard();
		}
	}

	/**
	 * Returns the last carriage in the train, unless there is none where it
	 * returns null
	 * 
	 * @return Last carriage or null if one does not exist
	 */
	private RollingStock getLastCarriage() {
		if (carriages.size() != 0) {
			return carriages.get(carriages.size() - 1);
		}
		return null;
	}

	/**
	 * Adds the given number of people to passenger carriages on the train. We
	 * do not specify where the passengers must sit, so they can be allocated to
	 * any vacant seat in any passenger car.
	 * 
	 * @param newPassengers
	 *            The number of people wish to board the train.
	 * @return The number of people who were unable to board the train because
	 *         they couldn't get a seat.
	 * @throws TrainException
	 *             If the number of new passengers is negative.
	 */
	public Integer board(Integer newPassengers) throws TrainException {
		Integer passengers = newPassengers;
		Integer leftOverPassengers;
		if (passengers < 0) {
			throw new TrainException(
					"Amount of new passengers must not be negative");
		} else {
			// Iterate over carriages until a freightcar
			for (RollingStock carriage : carriages) {
				// try to board passengers onto a passengercar, dealing with the
				// left over passengers
				if (carriage instanceof PassengerCar) {
					leftOverPassengers = (Integer) ((PassengerCar) carriage)
							.board(passengers);
					this.numberOnBoard += passengers - leftOverPassengers;
					passengers = leftOverPassengers;
				} else if (carriage instanceof FreightCar) {
					break;
				}
				if (passengers == 0) {
					break;
				}
			}
			return passengers;
		}
	}
}
