package asgn2GUI;

/**
 * Main program for the Control Panel. This control panel allows Train Drivers
 * and Conductors to create and manage a train that is soon to depart.
 * 
 * @author Lochlan Bunn - 8509719
 * 
 */
public class ControlPanel {

	private static final String PROGRAM_TITLE = "Train Control Panel";

	public static void main(String[] args) {

		ControlPanelModel model = new ControlPanelModel();
		ControlPanelView view = new ControlPanelView(PROGRAM_TITLE);
		ControlPanelController controller = new ControlPanelController(model,
				view);

		view.setVisible(true);

	}

}
