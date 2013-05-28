package asgn2GUI;

import java.awt.event.*;

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

		}

	}

}
