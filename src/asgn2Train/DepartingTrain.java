package asgn2Train;

import java.util.ArrayList;
import java.util.List;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.FreightCar;
import asgn2RollingStock.Locomotive;
import asgn2RollingStock.PassengerCar;
import asgn2RollingStock.RollingStock;

/**
 * <p>
 * A train is a sequence of carriages. This class defines various operations
 * that can be performed to prepare a long-distance train for departure.
 * </p>
 * 
 * <p>
 * We assume that a train can be assembled from any available rolling stock,
 * including locomotives, passenger cars and freight cars. However, they may be
 * configured in only a certain sequence:
 * </p>
 * 
 * <ol>
 * <li>The first carriage must be a locomotive (and there can be only one
 * locomotive per train).</li>
 * <li>This may be followed by zero or more passenger cars.</li>
 * <li>These may be followed by zero or more freight cars.
 * </ol>
 * <p>
 * Any other configurations of rolling stock are disallowed.
 * </p>
 * 
 * <p>
 * The process of preparing the train for departure occurs in two stages:
 * </p>
 * 
 * <ol>
 * <li>The train is assembled from individual carriages. New carriages may be
 * added to the rear of the train only. (Similarly, carriages may be removed
 * from the rear of the train only.)</li>
 * <li>Passengers board the train. For safety reasons, no carriage shunting
 * operations may be performed when any passengers are on board the train.</li>
 * </ol>
 * 
 * @author Lochlan Bunn - 8509719
 * 
 */
public class DepartingTrain {
	private List<RollingStock> carriages;
	private Integer numberOnBoard;
	private Integer numberOfSeats;
	private Integer carriagesIterator;

	/**
	 * Constructs a (potential) train object containing no carriages (yet).
	 */
	public DepartingTrain() {
		this.carriages = new ArrayList<RollingStock>();
		this.numberOfSeats = 0;
		this.numberOnBoard = 0;
		this.carriagesIterator = -1;
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
	 * Returns the first carriage on the train (which must be a locomotive).
	 * Special value null is returned if there are no carriages on the train at
	 * all.<br/>
	 * <br/>
	 * 
	 * NB: When combined with method nextCarriage, this method gives us a simple
	 * ability to iteratively examine each of the train's carriages.
	 * 
	 * @return The first carriage in the train, or null if there are no
	 *         carriages.
	 */
	public RollingStock firstCarriage() {
		if (isTrainEmpty()) {
			return null;
		} else {
			this.carriagesIterator = 0;
			return carriages.get(this.carriagesIterator);
		}
	}

	/**
	 * Returns the next carriage in the train after the one returned by the
	 * immediately preceding call to either this method or method firstCarriage.
	 * Special value null is returned if there is no such carriage. If there has
	 * been no preceding call to either firstCarriage or nextCarriage, this
	 * method behaves like firstCarriage, i.e., it returns the first carriage in
	 * the train, if any.<br/>
	 * <br/>
	 * 
	 * NB: When combined with method firstCarriage, this method gives us a
	 * simple ability to iteratively examine each of the train's carriages.
	 * 
	 * @return The train's next carriage after the one returned by the
	 *         immediately preceding call to either firstCarriage or
	 *         nextCarriage, or null if there is no such carriage.
	 */
	public RollingStock nextCarriage() {
		this.carriagesIterator++;
		try {
			// TODO: acts just like firstCarriage, after a ++ on -1 (could
			// refactor?)
			return carriages.get(carriagesIterator);
		} catch (IndexOutOfBoundsException e) { // 'next' element does not exist
			return null;
		}
	}

	/**
	 * Returns the last carriage in the train, unless there is none where it
	 * returns null
	 * 
	 * @return Last carriage or null if one does not exist
	 */
	private RollingStock getLastCarriage() {
		if (isTrainEmpty()) {
			return null;
		}
		return carriages.get(carriages.size() - 1);
	}

	/**
	 * Returns if the train has no carriages.
	 * 
	 * @return If the train is empty of carriages.
	 */
	private boolean isTrainEmpty() {
		return carriages.isEmpty(); // or carriages.size() - 1 ?
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

	public Integer numberOnBoard() {
		return this.numberOnBoard;
	}

}
