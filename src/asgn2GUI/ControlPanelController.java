package asgn2GUI;

import java.awt.Component;
import java.awt.event.*;

import asgn2Exceptions.TrainException;
import asgn2GUI.ControlPanelView.CarriageTypeIndex;

/**
 * Controller class to manage Action Listeners for various components of the
 * Control Panel view.
 * 
 * @author Lochlan Bunn - 8509719
 * @author Murray Coggan - 8291951
 * 
 */
public class ControlPanelController {
	private ControlPanelModel model;
	private ControlPanelView view;

	private final int LOCOMOTIVE_INDEX = CarriageTypeIndex.LOCOMOTIVE
			.getValue();
	private final int FREIGHT_CAR_INDEX = CarriageTypeIndex.FREIGHT_CAR
			.getValue();
	private final int PASSENGER_CAR_INDEX = CarriageTypeIndex.PASSENGER_CAR
			.getValue();

	public ControlPanelController(ControlPanelModel model, ControlPanelView view) {
		this.model = model;
		this.view = view;
		view.addCarriagePropertiesListener(new CarriagePropertiesListener());
		view.addAddCarriageListener(new AddCarriageListener());
		view.addRemoveCarriageListener(new RemoveCarriageListener());
		view.addAddPassengerListener(new AddPassengerListener());
	}

	/**
	 * Updates the train in the model
	 */
	private void updateTrain() {
		view.getTrainCanvas().SetTrain(model.getTrain());
		update();
	}

	/**
	 * Revalidates the GUI
	 */
	private void update() {
		view.revalidate();
	}

	/**
	 * Action listener for the CarriageComboBox. Changes the panel displayed
	 * depending on which carriage is selected
	 */
	class CarriagePropertiesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Component source = (Component) e.getSource();

			if (source == view.getCarriageComboBox()) {
				if (view.getSelectedCarriageType() == view.getCarriageTypes()[LOCOMOTIVE_INDEX]) {
					view.setCarriageTypeOptions(view.getOptionPanelLocomotive());
				} else if (view.getSelectedCarriageType() == view
						.getCarriageTypes()[PASSENGER_CAR_INDEX]) {
					view.setCarriageTypeOptions(view
							.getOptionPanelPassengerCar());
				} else if (view.getSelectedCarriageType() == view
						.getCarriageTypes()[FREIGHT_CAR_INDEX]) {
					view.setCarriageTypeOptions(view.getOptionPanelFreightCar());
				}
				update();
			}
		}
	}

	/**
	 * Action Listener for adding a carriage. Adds a carriage of the specified
	 * type to the train. Throws error messages if shunting operations cannot be
	 * performed, or if the carriage parameters are incorrect
	 */
	class AddCarriageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Component source = (Component) e.getSource();
			if (model.canShunt()) {
				Integer tempWeight = Integer.parseInt(view
						.getCarriageWeightSpinner().getValue().toString());

				if (view.getSelectedCarriageType() == view.getCarriageTypes()[LOCOMOTIVE_INDEX]) {
					String tempClass = (Integer.parseInt(view
							.getLocomotivePowerSpinner().getValue().toString())
							+ "" + view.getLocomotiveEngineSpinner().getValue()
							.toString());
					try {
						model.addLocomotiveToTrain(tempWeight, tempClass);
					} catch (TrainException e1) {
						e1.printStackTrace();
						view.throwWarning(e1.getMessage());
					}
				} else if (view.getSelectedCarriageType() == view
						.getCarriageTypes()[PASSENGER_CAR_INDEX]) {
					Integer tempSeats = Integer.parseInt(view
							.getCarriageSeatsSpinner().getValue().toString());
					try {
						model.addPassengerCarToTrain(tempWeight, tempSeats);
					} catch (TrainException e1) {
						e1.printStackTrace();
						view.throwWarning(e1.getMessage());
					}
				} else if (view.getSelectedCarriageType() == view
						.getCarriageTypes()[FREIGHT_CAR_INDEX]) {
					String tempFreightClass = view.getFreightTypeSpinner()
							.getValue().toString();
					try {
						model.addFreightCarToTrain(tempWeight, tempFreightClass);
					} catch (TrainException e1) {
						e1.printStackTrace();
						view.throwWarning(e1.getMessage());
					}
				} else {
					view.throwWarning("Invalid carriage type");
				}
				updateTrain();
			} else {
				view.throwError("Train cannot be shunted");
			}
		}
	}

	/**
	 * Action Listener for the button to remove a carriage. Attempts to remove
	 * the last carriage on the train. Outputs an error message if the train
	 * currently has no carriages
	 */
	class RemoveCarriageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.removeCarriage();
			} catch (TrainException tE) {
				view.throwError("No carriages to remove");
			}
			updateTrain();
		}

	}

	/**
	 * Action Listener for the button to add passengers. Attempts to add
	 * passengers equal to the value of the AddPassengerSpinner, outputs an
	 * error message if the number of passengers is negative, or if there are
	 * any leftover passengers. The leftover passengers error message also
	 * displays the number of leftover passengers
	 * 
	 */
	class AddPassengerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Component source = (Component) e.getSource();

			if (source == view.getAddPassengerButton()) {
				try {
					Integer leftOverPassengers = 0;
					leftOverPassengers = model.addPassengers(Integer
							.parseInt(view.getAddPassengerSpinner().getValue()
									.toString()));
					updateTrain();
					if (leftOverPassengers > 0) {
						view.throwWarning(leftOverPassengers
								+ "Passengers were unable to board because the carriages are full.");
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
					view.throwWarning(e1.getMessage());
				} catch (TrainException e1) {
					e1.printStackTrace();
					view.throwWarning(e1.getMessage());
				}
			}
		}

	}

}
