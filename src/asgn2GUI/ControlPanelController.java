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
 * 
 */
public class ControlPanelController {
	private ControlPanelModel model;
	private ControlPanelView view;

	public ControlPanelController(ControlPanelModel model, ControlPanelView view) {
		this.model = model;
		this.view = view;
		view.addCarriagePropertiesListener(new CarriagePropertiesListener());
		view.addAddCarriageListener(new AddCarriageListener());
		view.addRemoveCarriageListener(new RemoveCarriageListener());
		view.addAddPassengerListener(new AddPassengerListener());
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
				} else if (view.getSelectedCarriageType() == view.getCarriageTypes()[PASSENGER_CAR_INDEX]) {
					view.setCarriageTypeOptions(view.getOptionPanelPassengerCar());
				} else if (view.getSelectedCarriageType() == view.getCarriageTypes()[FREIGHT_CAR_INDEX]) {
					view.setCarriageTypeOptions(view.getOptionPanelFreightCar());
				}
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
			if(){
				model.addLocomotiveToTrain(grossWeight, classification);
			}else if(){
				model.addPassengerCarToTrain(grossWeight, numberSeats);
			}else if(){
				model.addFreightCarToTrain(grossWeight, goodClass);
			}else{
				view.throwWarning("Invalid carriage type");
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
			try{
				model.removeCarriage();
			} catch (TrainException tE){
				view.throwWarning("No carriages to remove");
			}
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
					model.addPassengers(Integer.parseInt(view.getAddPassengerSpinner().getValue().toString()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TrainException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}//Listeners for what?
	

}
