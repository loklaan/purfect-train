package asgn2GUI;

/**
 * Main program for the Control Panel
 * 
 * @author Lochlan Bunn - 8509719
 * 
 */
public class ControlPanel {

	private static final String PROGRAM_TITLE = "Train Control Panel";

	/**
	 * Comments.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		ControlPanelModel model = new ControlPanelModel();
		ControlPanelView view = new ControlPanelView(PROGRAM_TITLE);
		ControlPanelController controller = new ControlPanelController(model,
				view);

		view.setVisible(true);

	}

}
