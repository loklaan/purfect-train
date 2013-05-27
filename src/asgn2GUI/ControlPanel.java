package asgn2GUI;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import javax.swing.border.*;

/**
 * @author Lochlan Bunn - 8509719
 * @author Murray Coggan - 8291951
 * 
 */
public class ControlPanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1139433227864661487L;

	// CONSTANTS
	private final int WIDTH = 700;
	private final int HEIGHT = 500;
	private static final String PROGRAM_TITLE = "Train Control Panel v0.1";
	final String DRIVER_ROLE = "Train Driver";
	final String CONDUCTOR_ROLE = "Train Conductor";

	// FIELDS
	private JScrollPane panelTrain;
	private TrainDraw trainCanvas;
	private JPanel panelControls;
	private JPanel panelConductor;
	private JPanel optionPanelLocomotive;
	private JPanel optionPanelPassengerCar;
	private JPanel optionPanelFreightCar;
	private JPanel panelDriver;
	private final String carriageTypes[] = { "Locomotive", "Passenger Car",
			"Freight Car" };

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public ControlPanel(String title) throws HeadlessException {
		super(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try { // Native look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		createGUI();
	}

	/**
	 * Initialses all GUI components.
	 */
	private void createGUI() {
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		// needs to split center control area in two
		this.panelControls = new JPanel();
		this.getContentPane().add(panelControls, BorderLayout.SOUTH);
		panelControls.setLayout(new GridLayout());

		// TODO fill this
		this.panelTrain = createTrainCanvas();
		this.getContentPane().add(panelTrain, BorderLayout.CENTER);

		// control panels for Driver / Conductor
		this.panelDriver = createDriverControlPanel();
		panelControls.add(panelDriver);
		this.panelConductor = createConductorControlPanel();
		panelControls.add(panelConductor);

		// end of method
		repaint();
	}

	/**
	 * Creates a JScrollPane that contains a canvas to draw train objects.
	 * 
	 * @return JScrollPane containing a TrainCanvas.
	 */
	private JScrollPane createTrainCanvas() {
		this.trainCanvas = new TrainDraw();
		JScrollPane scrollPanel = new JScrollPane(this.trainCanvas);

		scrollPanel.setSize(100, 200);

		return scrollPanel;
	}

	/**
	 * Sets some default values for constraints common to control panels.
	 * 
	 * @param constraints
	 *            GridBagConstraints object
	 * @return GridBagConstraints with some modified default values
	 */
	private GridBagConstraints setDefaultControlPanelConstraints(
			GridBagConstraints constraints) {
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(0, 5, 0, 5);
		return constraints;
	}

	/**
	 * Creates a control panel for the role of a Conductor.
	 * 
	 * @return JPanel and all the components needed for a Conductor.
	 */
	private JPanel createConductorControlPanel() {
		JPanel panel = createDefaultControlPanel(CONDUCTOR_ROLE);
		GridBagConstraints constraints = new GridBagConstraints();
		setDefaultControlPanelConstraints(constraints);

		// Passenger count label
		JLabel passengerCarMetricsLabel = new JLabel("Passengers:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panel, passengerCarMetricsLabel, constraints, 0, 0, 1, 1);

		// Passenger count
		JLabel passengerCarMetrics = new JLabel("PEOPLE/SEATS");
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panel, passengerCarMetrics, constraints, 1, 0, 1, 1);

		// Add passenger label
		JLabel addPassengerLabel = new JLabel("Add passengers:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panel, addPassengerLabel, constraints, 0, 1, 1, 1);

		// Add passenger input text field
		JTextField addPassengerField = new JTextField();
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panel, addPassengerField, constraints, 1, 1, 1, 1);
		addPassengerField.setColumns(4);

		// Add passenger button
		JButton addPassengerButton = new JButton("Add");
		addToPanel(panel, addPassengerButton, constraints, 2, 1, 1, 1);

		return panel;
	}

	/**
	 * Creates a control panel for the role of a Train Driver.
	 * 
	 * @return JPanel and all the components needed for a Train Driver.
	 */
	private JPanel createDriverControlPanel() {
		JPanel panel = createDefaultControlPanel(DRIVER_ROLE);
		GridBagConstraints constraints = new GridBagConstraints();
		setDefaultControlPanelConstraints(constraints);

		// Panel for grouping carriage options
		JPanel panelGroupCarriageOptions = createDefaultControlPanel("Add carriage");
		panelGroupCarriageOptions.setLayout(new GridBagLayout());
		addToPanel(panel, panelGroupCarriageOptions, constraints, 0, 0, 1, 1);

		// Set carriage weight label
		JLabel setCarriageWeightLabel = new JLabel("Carriage weight:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panelGroupCarriageOptions, setCarriageWeightLabel,
				constraints, 0, 0, 1, 1);

		// Set carriage weight field
		JTextField setCarriageWeightField = new JTextField();
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panelGroupCarriageOptions, setCarriageWeightField,
				constraints, 1, 0, 1, 1);
		setCarriageWeightField.setColumns(4);

		// Add carriage type label
		JLabel addCarriageLabel = new JLabel("Carriage type:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panelGroupCarriageOptions, addCarriageLabel, constraints, 0,
				1, 1, 1);

		// Add carriage type combobox with all carriage types
		JComboBox<String> addCarriageComboBox = new JComboBox<String>();
		for (int i = 0; i < carriageTypes.length; i++) {
			addCarriageComboBox.addItem(carriageTypes[i]);
		}
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panelGroupCarriageOptions, addCarriageComboBox, constraints,
				1, 1, 1, 1);

		// Initialises different option panels for carriages. On default, shows
		// the Locomotive options
		optionPanelLocomotive = createLocomotiveOptionsPanel();
		optionPanelPassengerCar = createPassengerCarOptionsPanel();
		optionPanelFreightCar = createFreightCarOptionsPanel();
		constraints.anchor = GridBagConstraints.CENTER;
		addToPanel(panelGroupCarriageOptions, optionPanelLocomotive,
				constraints, 0, 2, 2, 1);

		// Add carriage to train button
		JButton addCarriageToTrainButton = new JButton("Add to train");
		constraints.anchor = GridBagConstraints.CENTER;
		addToPanel(panelGroupCarriageOptions, addCarriageToTrainButton,
				constraints, 0, 3, 2, 1);

		// Remove carriage button
		JButton removeCarriageButton = new JButton("Remove last carriage");
		addToPanel(panel, removeCarriageButton, constraints, 0, 1, 1, 1);

		return panel;
	}

	/**
	 * Creates an option panel to group items for setting up a Locomotive.
	 * 
	 * @return JPanel containing initialised components for a Locomotive,
	 */
	private JPanel createLocomotiveOptionsPanel() {
		JPanel optionsPanel = createDefaultControlPanel("Locomotive");
		optionsPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		setDefaultControlPanelConstraints(constraints);

		// Set locomotive engine label
		JLabel setLocomotiveEngineLabel = new JLabel("Engine Type:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setLocomotiveEngineLabel, constraints, 0, 0,
				1, 1);

		// Set locomotive engine field
		JTextField setLocomotiveEngineField = new JTextField();
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, setLocomotiveEngineField, constraints, 1, 0,
				1, 1);

		setLocomotiveEngineField.setColumns(4);
		// Set locomotive engine label
		JLabel setLocomotivePowerLabel = new JLabel("Power Level:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setLocomotivePowerLabel, constraints, 0, 1, 1,
				1);
		// Set locomotive engine field
		JTextField setLocomotivePowerField = new JTextField();
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, setLocomotivePowerField, constraints, 1, 1, 1,
				1);
		setLocomotivePowerField.setColumns(4);

		return optionsPanel;
	}

	/**
	 * Creates an option panel to group items for setting up a Passenger Car.
	 * 
	 * @return JPanel containing initialised components for a Passenger Car.
	 */
	private JPanel createPassengerCarOptionsPanel() {
		JPanel optionsPanel = createDefaultControlPanel("Passenger Car");
		optionsPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		setDefaultControlPanelConstraints(constraints);

		// Set carriage seats label
		JLabel setCarriageSeatsLabel = new JLabel("Carriage seats:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setCarriageSeatsLabel, constraints, 0, 0, 1, 1);

		// Set carriage seats field
		JTextField setCarriageSeatsField = new JTextField();
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, setCarriageSeatsField, constraints, 1, 0, 1, 1);
		setCarriageSeatsField.setColumns(4);

		return optionsPanel;
	}

	/**
	 * Creates an option panel to group items for setting up a Freight Car.
	 * 
	 * @return JPanel containing initialised components for a Freight Car
	 */
	private JPanel createFreightCarOptionsPanel() {
		JPanel optionsPanel = createDefaultControlPanel("Freight Car");
		optionsPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		setDefaultControlPanelConstraints(constraints);

		// Set freight type label
		JLabel setFreightTypeLabel = new JLabel("Freight type:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setFreightTypeLabel, constraints, 0, 0, 1, 1);

		// Set freight type field
		JTextField setFreightTypeField = new JTextField();
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, setFreightTypeField, constraints, 1, 0, 1, 1);
		setFreightTypeField.setColumns(4);

		return optionsPanel;
	}

	/**
	 * Adds a component to a panel
	 * 
	 * @param panel
	 *            Panel that is being added to.
	 * @param component
	 *            Component to be added.
	 * @param constraints
	 *            GridBagConstraints object
	 * @param x
	 *            gridx constraint
	 * @param y
	 *            gridy constraint
	 * @param w
	 *            gridwidth constraint
	 * @param h
	 *            gridheight constraint
	 */
	private void addToPanel(JPanel panel, Component component,
			GridBagConstraints constraints, int x, int y, int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		panel.add(component, constraints);
	}

	/**
	 * Creates a control panel with a default layout of GridBagLayout and a
	 * TitledBorder. To be used by other methods creating a specific controller
	 * panel.
	 * 
	 * @param title
	 *            Usage / role title for the panel
	 * @return Controller JPanel with default constraints
	 */
	private JPanel createDefaultControlPanel(String title) {
		JPanel panel = new JPanel();
		GridBagLayout grid = new GridBagLayout();

		// Set layout
		panel.setLayout(grid);

		// Create border inside panel with a usage title
		Border border = BorderFactory.createTitledBorder(title);
		panel.setBorder(border);

		return panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ControlPanel trainControlPanel = new ControlPanel(PROGRAM_TITLE);
		trainControlPanel.setVisible(true);
	}

}
