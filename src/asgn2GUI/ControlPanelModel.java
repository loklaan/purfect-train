package asgn2GUI;

import asgn2Train.DepartingTrain;
import asgn2Exceptions.TrainException;
import asgn2RollingStock.*;

/**
 * Model class for the data and logic of a Control Panel. Handles the Departing
 * Train object and it's manipulation.
 * 
 * @author Lochlan Bunn - 8509719
 * 
 */
public class ControlPanelModel {

	private static DepartingTrain train;
	private Locomotive locomotive;
	private PassengerCar passengerCar;
	private FreightCar freightCar;

	/**
	 * 
	 */
	public ControlPanelModel() {
		this.train = new DepartingTrain();
	}

	/**
	 * Adds the specified locomotive carriage to the departing train
	 * 
	 * @param grossWeight
	 *            The weight of the carriage
	 * @param classification
	 *            The classification of the carriage, must be in the format
	 *            "POWERCLASS" i.e "4E"
	 * @throws TrainException
	 *             Throws TrainException if grossWeight is negative, or if
	 *             classification is not in the correct format
	 */
	public void addLocomotiveToTrain(Integer grossWeight, String classification)
			throws TrainException {
		locomotive = new Locomotive(grossWeight, classification);
		train.addCarriage(locomotive);
	}

	/**
	 * Adds the specified passenger carriage to the departing train
	 * 
	 * @param grossWeight
	 *            The weight of the carriage
	 * @param numberSeats
	 *            The number of available seats on the carriage
	 * @throws TrainException
	 *             Throws TrainException if either the weight or number of seats
	 *             is negative
	 */
	public void addPassengerCarToTrain(Integer grossWeight, Integer numberSeats)
			throws TrainException {
		passengerCar = new PassengerCar(grossWeight, numberSeats);
		train.addCarriage(passengerCar);
	}

	/**
	 * Adds the specified freight carriage to the departing train
	 * 
	 * @param grossWeight
	 *            The weight of the carriage
	 * @param goodsClass
	 *            The class of goods the carriage holds, must be either "G",
	 *            "R", or "D"
	 * @throws TrainException
	 *             Throws Train exception if the weight is negative or if the
	 *             goods are not within the specified parameters
	 */
	public void addFreightCarToTrain(Integer grossWeight, String goodsClass)
			throws TrainException {
		freightCar = new FreightCar(grossWeight, goodsClass);
		train.addCarriage(freightCar);
	}

	/**
	 * Returns whether the train can be shunted or not
	 * 
	 * @return Returns true if shunting operations can be performed on the
	 *         train, false if they cannot
	 */
	public boolean canShunt() {
		if (train.numberOnBoard() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Boards the specified number of passengers onto the departing train
	 * 
	 * @param numberPassengers
	 *            The number of passengers to board the train
	 * @return Returns any excess passengers who were unable to be boarded
	 * @throws TrainException
	 *             Throws TrainException if the number of passengers to be
	 *             boarded is negative
	 */
	public Integer addPassengers(Integer numberPassengers)
			throws TrainException {
		return train.board(numberPassengers);
	}

	/**
	 * Removes the last carriage from the departing train
	 * 
	 * @throws TrainException
	 *             Throws TrainException if there are no carriages to remove
	 */
	public void removeCarriage() throws TrainException {
		train.removeCarriage();
	}

	/**
	 * Gets the number of passengers on the departing train
	 * 
	 * @return Returns the number of passengers currently on board the departing
	 *         train
	 */
	public Integer getPassengersOnBoard() {
		return train.numberOnBoard();
	}

	/**
	 * Gets the number of seats on the departing train
	 * 
	 * @return Returns the number of seats on the departing train.
	 */
	public Integer getNumberSeats() {
		return train.numberOfSeats();
	}

	/**
	 * Gets the departing train
	 * 
	 * @return returns the departing train
	 */
	public DepartingTrain getTrain() {
		return train;
	}

}
