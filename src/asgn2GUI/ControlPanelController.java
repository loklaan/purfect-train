package asgn2GUI;

import java.awt.Component;
import java.awt.event.*;

import asgn2Exceptions.TrainException;
import asgn2GUI.ControlPanelView.carriageTypeIndex;

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

	private final int LOCOMOTIVE_INDEX = carriageTypeIndex.LOCOMOTIVE
			.getValue();
	private final int FREIGHT_CAR_INDEX = carriageTypeIndex.FREIGHT_CAR
			.getValue();
	private final int PASSENGER_CAR_INDEX = carriageTypeIndex.PASSENGER_CAR
			.getValue();

	public ControlPanelController(ControlPanelModel model, ControlPanelView view) {
		this.model = model;
		this.view = view;
		view.addCarriagePropertiesListener(new CarriagePropertiesListener());
		view.addAddCarriageListener(new AddCarriageListener());
		view.addRemoveCarriageListener(new RemoveCarriageListener());
		view.addAddPassengerListener(new AddPassengerListener());
	}
	
	private void updateTrain(){
		view.getTrainCanvas().SetTrain(model.getTrain());
		update();
	}
	
	private void update(){
		view.revalidate();
	}

	/**
	 * When a request is made for a carriage to be added to a train, the
	 * following is read and computed before addage:
	 * <ul>
	 * <li>check if can shunt</li>
	 * <li>weight</li>
	 * <li>car type</li>
	 * <li><em>relevant</em> car properties</li>
	 * <li>update train canvas</li>
	 * </ul>
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
	 * When a request is made for a carriage to be added to a train, the
	 * following is read and computed before addage:
	 * <ul>
	 * <li>check if can shunt</li>
	 * <li>weight</li>
	 * <li>car type</li>
	 * <li><em>relevant</em> car properties</li>
	 * <li>update train canvas</li>
	 * </ul>
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (view.getSelectedCarriageType() == view
						.getCarriageTypes()[PASSENGER_CAR_INDEX]) {
					Integer tempSeats = Integer.parseInt(view
							.getCarriageSeatsSpinner().getValue().toString());
					try {
						model.addPassengerCarToTrain(tempWeight, tempSeats);
					} catch (TrainException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (view.getSelectedCarriageType() == view
						.getCarriageTypes()[FREIGHT_CAR_INDEX]) {
					String tempFreightClass = view.getFreightTypeSpinner()
							.getValue().toString();
					try {
						model.addFreightCarToTrain(tempWeight, tempFreightClass);
					} catch (TrainException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					view.throwWarning("Invalid carriage type");
				}
				updateTrain();
			} else {
				view.throwWarning("Train cannot be shunted");
			}
		}
	}

	/**
	 * When a request is made for a carriage to be removed from a train, the
	 * following is done:
	 * <ul>
	 * <li>check if can shunt</li>
	 * <li>remove last carriage</li>
	 * <li>update train canvas</li>
	 * </ul>
	 */
	class RemoveCarriageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.removeCarriage();
			} catch (TrainException tE) {
				view.throwWarning("No carriages to remove");
			}
			updateTrain();
		}
		
	}

	/**
	 * When a request is made for a number of passengers to be added to a train,
	 * the following is done:
	 * <ul>
	 * <li>does train have passenger cars</li>
	 * <li>is train full</li>
	 * <li>add passengers</li>
	 * <li>update conductor metrics</li>
	 * <li>update train canvas</li>
	 * </ul>
	 */
	class AddPassengerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Component source = (Component) e.getSource();

			if (source == view.getAddPassengerButton()) {
				try {
					model.addPassengers(Integer.parseInt(view
							.getAddPassengerSpinner().getValue().toString()));
					updateTrain();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TrainException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}
	
	

}
