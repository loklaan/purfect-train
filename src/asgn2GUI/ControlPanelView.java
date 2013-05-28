package asgn2GUI;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;

import javax.swing.border.*;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.*;
import asgn2Train.DepartingTrain;

/**
 * @author Lochlan Bunn - 8509719
 * @author Murray Coggan - 8291951
 * 
 */
public class ControlPanelView extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1139433227864661487L;

	// CONSTANTS
	private final int WIDTH = 700;
	private final int HEIGHT = 500;
	private static final String PROGRAM_TITLE = "Train Control Panel v0.1";
	final String DRIVER_ROLE = "Train Driver";
	final String CONDUCTOR_ROLE = "Train Conductor";

	// FIELDS
	private DepartingTrain train;
	private JScrollPane panelTrain;
	private TrainDraw trainCanvas;
	private JPanel panelControls;
	private JPanel panelConductor;
	private JPanel optionPanelLocomotive;
	private JPanel optionPanelPassengerCar;
	private JPanel optionPanelFreightCar;
	private JPanel panelDriver;
	private JPanel panelGroupCarriageTypeOptions;
	private final String[] carriageTypes = { "Locomotive", "Passenger Car",
			"Freight Car" };

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public ControlPanelView(String title) throws HeadlessException {
		super(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try { // Native look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		createGUI();
		try {
			createTrain();
		} catch (TrainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createTrain() throws TrainException {
		train = new DepartingTrain();
	}

	private void updateTrainFake() throws TrainException {
		Locomotive testLoco = new Locomotive(200, "5E");
		PassengerCar testPass = new PassengerCar(100, 20);
		PassengerCar testPass2 = new PassengerCar(10, 400);
		FreightCar testFreight = new FreightCar(10, "D");
		train.addCarriage(testLoco);
		train.addCarriage(testPass);
		train.addCarriage(testPass2);
		train.addCarriage(testPass);
		train.addCarriage(testFreight);
		train.addCarriage(testFreight);
		train.addCarriage(testFreight);
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
		scrollPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		return scrollPanel;
	}

	/**
	 * Sets some default values for constraints common to control panels.
	 * 
	 * @param constraints
	 *            GridBagConstraints object
	 * @return GridBagConstraints with some modified default values
	 */
	private GridBagConstraints defaultControlPanelConstraints(
			GridBagConstraints constraints) {
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(0, 5, 3, 5);
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
		constraints = defaultControlPanelConstraints(constraints);

		// Passenger count label
		JLabel passengerCarMetricsLabel = new JLabel("Passenger Metrics");
		constraints.anchor = GridBagConstraints.CENTER;
		addToPanel(panel, passengerCarMetricsLabel, constraints, 0, 0, 2, 1);

		// Passenger count
		JLabel passengerCarMetrics = new JLabel("PEOPLE/SEATS");
		passengerCarMetrics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		constraints.insets = new Insets(0, 5, 30, 5);
		addToPanel(panel, passengerCarMetrics, constraints, 0, 1, 2, 1);

		// Panel for grouping passenger addage
		JPanel panelGroupAddPassenger = createDefaultControlPanel("Add passengers");
		panelGroupAddPassenger.setLayout(new GridBagLayout());
		constraints = defaultControlPanelConstraints(constraints);
		addToPanel(panel, panelGroupAddPassenger, constraints, 0, 2, 1, 1);

		// Add passenger input spinner
		JSpinner addPassengerSpinner = new JSpinner(new SpinnerNumberModel(
				9999, null, null, 1));
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panelGroupAddPassenger, addPassengerSpinner, constraints, 1,
				2, 1, 1);

		// Add passenger button
		JButton addPassengerButton = new JButton("Add");
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panelGroupAddPassenger, addPassengerButton, constraints, 2,
				2, 1, 1);

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
		constraints = defaultControlPanelConstraints(constraints);

		// Panel for grouping carriage options
		JPanel panelGroupAddCarriageOptions = createDefaultControlPanel("Add carriage");
		panelGroupAddCarriageOptions.setLayout(new GridBagLayout());
		addToPanel(panel, panelGroupAddCarriageOptions, constraints, 0, 0, 1, 1);

		// Set carriage weight label
		JLabel setCarriageWeightLabel = new JLabel("Carriage weight:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panelGroupAddCarriageOptions, setCarriageWeightLabel,
				constraints, 0, 0, 1, 1);

		// Set carriage weight spinner
		JSpinner setCarriageWeightSpinner = new JSpinner(
				new SpinnerNumberModel(0, null, null, 10));
		((JSpinner.DefaultEditor) setCarriageWeightSpinner.getEditor())
				.getTextField().setColumns(3);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panelGroupAddCarriageOptions, setCarriageWeightSpinner,
				constraints, 1, 0, 1, 1);

		// Add carriage type label
		JLabel addCarriageLabel = new JLabel("Carriage type:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panelGroupAddCarriageOptions, addCarriageLabel, constraints,
				0, 1, 1, 1);

		// Add carriage type combobox with all carriage types
		JComboBox<String> addCarriageComboBox = new JComboBox<String>(
				carriageTypes);
		addCarriageComboBox.addActionListener(this);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panelGroupAddCarriageOptions, addCarriageComboBox,
				constraints, 1, 1, 1, 1);

		// Initialises different option panels for carriages. On default, shows
		// the Locomotive options
		panelGroupCarriageTypeOptions = new JPanel();
		constraints.anchor = GridBagConstraints.CENTER;
		addToPanel(panelGroupAddCarriageOptions, panelGroupCarriageTypeOptions,
				constraints, 0, 2, 2, 1);
		optionPanelLocomotive = createLocomotiveOptionsPanel();
		optionPanelPassengerCar = createPassengerCarOptionsPanel();
		optionPanelFreightCar = createFreightCarOptionsPanel();
		panelGroupCarriageTypeOptions.add(optionPanelLocomotive);
		// addToPanel(panelGroupCarriageTypeOptions, optionPanelLocomotive,
		// constraints, 0, 0, 1, 1);

		// Add carriage to train button
		JButton addCarriageToTrainButton = new JButton("Add to train");
		addCarriageToTrainButton.addActionListener(this);
		constraints.anchor = GridBagConstraints.CENTER;
		addToPanel(panelGroupAddCarriageOptions, addCarriageToTrainButton,
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
		constraints = defaultControlPanelConstraints(constraints);

		// Set locomotive engine label
		JLabel setLocomotiveEngineLabel = new JLabel("Engine Type:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setLocomotiveEngineLabel, constraints, 0, 0,
				1, 1);

		// Set locomotive engine spinner
		JSpinner setLocomotiveEngineSpinner = new JSpinner(
				new SpinnerListModel(new String[] { "E", "S", "D" }));
		((JSpinner.DefaultEditor) setLocomotiveEngineSpinner.getEditor())
				.getTextField().setColumns(3);
		((JSpinner.DefaultEditor) setLocomotiveEngineSpinner.getEditor())
				.getTextField().setEditable(false);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, setLocomotiveEngineSpinner, constraints, 1, 0,
				1, 1);

		// Set locomotive power label
		JLabel setLocomotivePowerLabel = new JLabel("Power Level:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setLocomotivePowerLabel, constraints, 0, 1, 1,
				1);

		// Set locomotive power spinner
		JSpinner setLocomotivePowerSpinner = new JSpinner(
				new SpinnerNumberModel(1, 1, 9, 1));
		((JSpinner.DefaultEditor) setLocomotivePowerSpinner.getEditor())
				.getTextField().setColumns(3);
		((JSpinner.DefaultEditor) setLocomotivePowerSpinner.getEditor())
				.getTextField().setEditable(false);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, setLocomotivePowerSpinner, constraints, 1, 1,
				1, 1);

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
		constraints = defaultControlPanelConstraints(constraints);

		// Set carriage seats label
		JLabel setCarriageSeatsLabel = new JLabel("Carriage seats:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setCarriageSeatsLabel, constraints, 0, 0, 1, 1);

		// Set carriage seats spinner
		JSpinner setCarriageSeatsSpinner = new JSpinner(new SpinnerNumberModel(
				0, null, null, 1));
		((JSpinner.DefaultEditor) setCarriageSeatsSpinner.getEditor())
				.getTextField().setColumns(3);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, setCarriageSeatsSpinner, constraints, 1, 0, 1,
				1);

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
		constraints = defaultControlPanelConstraints(constraints);

		// Set freight type label
		JLabel setFreightTypeLabel = new JLabel("Freight type:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setFreightTypeLabel, constraints, 0, 0, 1, 1);

		// Set freight type spinner
		JSpinner setFreightTypeSpinner = new JSpinner(new SpinnerListModel(
				new String[] { "G", "R", "D" }));
		((JSpinner.DefaultEditor) setFreightTypeSpinner.getEditor())
				.getTextField().setColumns(3);
		((JSpinner.DefaultEditor) setFreightTypeSpinner.getEditor())
				.getTextField().setEditable(false);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, setFreightTypeSpinner, constraints, 1, 0, 1, 1);

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
		if (e.getSource() instanceof JComboBox) {
			if (((JComboBox) e.getSource()).getSelectedItem() == carriageTypes[0]) {
				panelGroupCarriageTypeOptions.removeAll();
				panelGroupCarriageTypeOptions.add(optionPanelLocomotive);
			} else if (((JComboBox) e.getSource()).getSelectedItem() == carriageTypes[1]) {
				panelGroupCarriageTypeOptions.removeAll();
				panelGroupCarriageTypeOptions.add(optionPanelPassengerCar);
			} else if (((JComboBox) e.getSource()).getSelectedItem() == carriageTypes[2]) {
				panelGroupCarriageTypeOptions.removeAll();
				panelGroupCarriageTypeOptions.add(optionPanelFreightCar);
			}
		}
		if (e.getSource() instanceof JButton) {
			try {
				updateTrainFake();
				trainCanvas.SetTrain(train);
			} catch (TrainException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		revalidate();
	}

}
