package asgn2RollingStock;

import asgn2Exceptions.TrainException;

/**
 * A passenger car is designed to carry people and has a fixed seating capacity.
 * We assume that the train is a long-distance one in which all passengers are
 * assigned a seat (unlike your peak-hour, metropolitan commuting experience!).
 * 
 * @author Lochlan Bunn - 8509719
 * 
 */
public class PassengerCar extends RollingStock {

	private Integer numberOfSeats;
	private Integer numberOfPassengers;

	final Integer ZERO = 0;

	/**
	 * Constructs a passenger car with a known weight and a fixed number of
	 * seats. (We allow a passenger car to have zero seats, although it would
	 * not be very useful.)
	 * 
	 * @param grossWeight
	 *            The carriage's gross weight in tonnes (ignoring the weight of
	 *            passengers, which we treat as negligible).
	 * @param numberOfSeats
	 *            How many seats are available in the carriage.
	 * @throws TrainException
	 *             If the gross weight is not positive or if the number of seats
	 *             is negative.
	 */
	public PassengerCar(Integer grossWeight, Integer numberOfSeats)
			throws TrainException {
		super(grossWeight);
		if (numberOfSeats < 0) {
			throw new TrainException(
					"Number of seats in a car must be zero or higher.");
		} else {
			setNumberOfSeats(numberOfSeats);
			setNumberOfPassengers(ZERO);
		}
	}

	/**
	 * Sets the instance variable for numberOfSeats
	 * 
	 * @param numberOfSeats
	 */
	private void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	/**
	 * Sets the instance variable for numberOfPassengers
	 * 
	 * @param numberOfSeats
	 */
	private void setNumberOfPassengers(Integer numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	/**
	 * Adds the given number of new passengers to the number on board the
	 * carriage. If there are too many new passengers for the number of spare
	 * seats the left over people are not boarded.
	 * 
	 * @param newPassengers
	 *            The number of people who wish to board the carriage.
	 * @return The number of people who were unable to board the carriage
	 *         because they couldn't get a seat.
	 * @throws TrainException
	 *             If the number of new passengers is negative.
	 */
	public Integer board(Integer newPassengers) throws TrainException {
		Integer leftOverPassengers = 0;
		if (newPassengers < 0) {
			throw new TrainException(
					"Amount of new passengers to board must be a zero or higher.");
		} else if ((this.numberOfPassengers + newPassengers - numberOfSeats) >= 0) {
			leftOverPassengers = this.numberOfPassengers + newPassengers
					- numberOfSeats;
			setNumberOfPassengers(this.numberOfSeats);
		} else {
			setNumberOfPassengers(this.numberOfPassengers + newPassengers);
		}
		return leftOverPassengers;
	}

	/**
	 * Removes the given number of passengers from this carriage. Attempting to
	 * remove more passengers than are on board is not allowed.
	 * 
	 * @param departingPassengers
	 *            The number of passengers alighting from the carriage.
	 * @throws TrainException
	 *             If the number of departing passengers is negative or if the
	 *             number of departing passengers exceeds the number on board.
	 */
	public void alight(Integer departingPassengers) throws TrainException {
		if (departingPassengers < 0) {
			throw new TrainException(
					"The amount of passengers to alight must be zero or higher");
		} else if (departingPassengers > this.numberOfPassengers) {
			throw new TrainException(
					"The amount of passengers to alight must be less than or equal to the amount of boarded passengers.");
		} else {
			setNumberOfPassengers(this.numberOfPassengers - departingPassengers);
		}
	}

	/**
	 * Returns the number of passengers currently on board this carriage.
	 * 
	 * @return The number of passengers aboard.
	 */
	public Integer numberOnBoard() {
		return this.numberOfPassengers;
	}

	/**
	 * Returns the number of seats installed on this carriage.
	 * 
	 * @return The number of seats on this carriage.
	 */
	public Integer numberOfSeats() {
		return this.numberOfSeats;
	}

	/**
	 * Returns a human-readable description of the passenger car. This has the
	 * form "Passenger(x/y)" where x is the number of passengers currently on
	 * board and y is the number of seats in the carriage.
	 * 
	 * @return A human-readable description of the passenger car.
	 */
	@Override
	public String toString() {
		return "Passenger(" + this.numberOfPassengers + "/"
				+ this.numberOfSeats + ")";
	}

}
