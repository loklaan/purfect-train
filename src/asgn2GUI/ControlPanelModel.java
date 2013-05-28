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

	public void addLocomotiveToTrain(Integer grossWeight, String classification)
			throws TrainException {
		locomotive = new Locomotive(grossWeight, classification);
		train.addCarriage(locomotive);
	}

}
