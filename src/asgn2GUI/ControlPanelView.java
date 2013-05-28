package asgn2GUI;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.border.*;

/**
 * View class for
 * 
 * @author Lochlan Bunn - 8509719
 * @author Murray Coggan - 8291951
 * 
 */
public class ControlPanelView extends JFrame {

	private static final long serialVersionUID = -1139433227864661487L;

	// CONSTANTS
	private final int WIDTH = 700;
	private final int HEIGHT = 500;
	final String DRIVER_ROLE = "Train Driver";
	final String CONDUCTOR_ROLE = "Train Conductor";

	// FIELDS
	private JScrollPane panelTrain;
	private TrainDraw trainCanvas;
	private JPanel panelControls;
	private JPanel panelConductor;
	private JPanel panelDriver;
	// Driver components
	private JPanel optionPanelLocomotive;
	private JPanel optionPanelPassengerCar;
	private JPanel optionPanelFreightCar;
	private JPanel panelCarriageTypeOptions;
	private String[] carriageTypes = { "Locomotive", "Passenger Car",
			"Freight Car" };

	// TODO refactor rename and comment enum
	enum carriageTypeIndex {
		LOCOMOTIVE(0), PASSENGER_CAR(1), FREIGHT_CAR(2);

		private final int value;

		private carriageTypeIndex(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private JSpinner carriageWeightSpinner;
	private JComboBox<String> carriageComboBox;
	private JButton addCarriageToTrainButton;
	private JButton removeCarriageButton;
	private JSpinner locomotiveEngineSpinner;
	private JSpinner locomotivePowerSpinner;
	private JSpinner carriageSeatsSpinner;
	private JSpinner freightTypeSpinner;
	// Conductor components
	private JLabel passengerCarMetrics;
	private JSpinner addPassengerSpinner;
	private JButton addPassengerButton;

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
	}

	/**
	 * Initialses all GUI components.
	 */
	private void createGUI() {
		this.setSize(WIDTH, HEIGHT);
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

		// Passenger count info
		passengerCarMetrics = new JLabel("PEOPLE/SEATS");
		passengerCarMetrics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		constraints.insets = new Insets(0, 5, 30, 5);
		addToPanel(panel, passengerCarMetrics, constraints, 0, 1, 2, 1);

		// Panel for grouping passenger addage
		JPanel panelGroupAddPassenger = createDefaultControlPanel("Add passengers");
		panelGroupAddPassenger.setLayout(new GridBagLayout());
		constraints = defaultControlPanelConstraints(constraints);
		addToPanel(panel, panelGroupAddPassenger, constraints, 0, 2, 1, 1);

		// Add passenger input spinner
		addPassengerSpinner = new JSpinner(new SpinnerNumberModel(9999, null,
				null, 1));
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panelGroupAddPassenger, addPassengerSpinner, constraints, 1,
				2, 1, 1);

		// Add passenger button
		addPassengerButton = new JButton("Add");
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panelGroupAddPassenger, addPassengerButton, constraints, 2,
				2, 1, 1);

		return panel;
	}

	/**
	 * Add a AddPassengerListener to relevant components of the GUI
	 * 
	 * @param listener
	 *            Instance of the action listener to be used (which handles the
	 *            events)
	 */
	protected void addAddPassengerListener(ActionListener listener) {
		this.addPassengerButton.addActionListener(listener);
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
		carriageWeightSpinner = new JSpinner(new SpinnerNumberModel(0, null,
				null, 10));
		((JSpinner.DefaultEditor) carriageWeightSpinner.getEditor())
				.getTextField().setColumns(3);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panelGroupAddCarriageOptions, carriageWeightSpinner,
				constraints, 1, 0, 1, 1);

		// Add carriage type label
		JLabel addCarriageLabel = new JLabel("Carriage type:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panelGroupAddCarriageOptions, addCarriageLabel, constraints,
				0, 1, 1, 1);

		// Add carriage type combobox with all carriage types
		carriageComboBox = new JComboBox<String>(carriageTypes);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panelGroupAddCarriageOptions, carriageComboBox, constraints,
				1, 1, 1, 1);

		// Initialises different option panels for carriages. On default, shows
		// the Locomotive options
		panelCarriageTypeOptions = new JPanel();
		constraints.anchor = GridBagConstraints.CENTER;
		addToPanel(panelGroupAddCarriageOptions, panelCarriageTypeOptions,
				constraints, 0, 2, 2, 1);
		optionPanelLocomotive = createLocomotiveOptionsPanel();
		optionPanelPassengerCar = createPassengerCarOptionsPanel();
		optionPanelFreightCar = createFreightCarOptionsPanel();
		panelCarriageTypeOptions.add(optionPanelLocomotive);

		// Add carriage to train button
		addCarriageToTrainButton = new JButton("Add to train");
		constraints.anchor = GridBagConstraints.CENTER;
		addToPanel(panelGroupAddCarriageOptions, addCarriageToTrainButton,
				constraints, 0, 3, 2, 1);

		// Remove carriage button
		removeCarriageButton = new JButton("Remove last carriage");
		addToPanel(panel, removeCarriageButton, constraints, 0, 1, 1, 1);

		return panel;
	}

	/**
	 * Add a CarriagePropertiesListener to relevant components of the GUI
	 * 
	 * @param listener
	 *            Instance of the action listener to be used (which handles the
	 *            events)
	 */
	protected void addCarriagePropertiesListener(ActionListener listener) {
		this.carriageComboBox.addActionListener(listener);
	}

	/**
	 * Add a AddCarriageListener to relevant components of the GUI
	 * 
	 * @param listener
	 *            Instance of the action listener to be used (which handles the
	 *            events)
	 */
	protected void add(ActionListener listener) {
		this.addCarriageToTrainButton.addActionListener(listener);
	}

	/**
	 * Add a RemoveCarriageListener to relevant components of the GUI
	 * 
	 * @param listener
	 *            Instance of the action listener to be used (which handles the
	 *            events)
	 */
	protected void addRemoveCarriageListener(ActionListener listener) {
		this.removeCarriageButton.addActionListener(listener);
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
		locomotiveEngineSpinner = new JSpinner(new SpinnerListModel(
				new String[] { "E", "S", "D" }));
		((JSpinner.DefaultEditor) locomotiveEngineSpinner.getEditor())
				.getTextField().setColumns(3);
		((JSpinner.DefaultEditor) locomotiveEngineSpinner.getEditor())
				.getTextField().setEditable(false);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, locomotiveEngineSpinner, constraints, 1, 0, 1,
				1);

		// Set locomotive power label
		JLabel setLocomotivePowerLabel = new JLabel("Power Level:");
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(optionsPanel, setLocomotivePowerLabel, constraints, 0, 1, 1,
				1);

		// Set locomotive power spinner
		locomotivePowerSpinner = new JSpinner(
				new SpinnerNumberModel(1, 1, 9, 1));
		((JSpinner.DefaultEditor) locomotivePowerSpinner.getEditor())
				.getTextField().setColumns(3);
		((JSpinner.DefaultEditor) locomotivePowerSpinner.getEditor())
				.getTextField().setEditable(false);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, locomotivePowerSpinner, constraints, 1, 1, 1,
				1);

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
		carriageSeatsSpinner = new JSpinner(new SpinnerNumberModel(0, null,
				null, 1));
		((JSpinner.DefaultEditor) carriageSeatsSpinner.getEditor())
				.getTextField().setColumns(3);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, carriageSeatsSpinner, constraints, 1, 0, 1, 1);

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
		freightTypeSpinner = new JSpinner(new SpinnerListModel(
				new String[] { "G", "R", "D" }));
		((JSpinner.DefaultEditor) freightTypeSpinner.getEditor())
				.getTextField().setColumns(3);
		((JSpinner.DefaultEditor) freightTypeSpinner.getEditor())
				.getTextField().setEditable(false);
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(optionsPanel, freightTypeSpinner, constraints, 1, 0, 1, 1);

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

	/**
	 * Removes the existing options panel from panelCarriageTypeOptions and sets
	 * the new options panel.
	 * 
	 * @param optionsPanel
	 *            the panelCarriageTypeOptions to set
	 */
	protected void setCarriageTypeOptions(JPanel optionsPanel) {
		this.panelCarriageTypeOptions.removeAll();
		this.panelCarriageTypeOptions.add(optionsPanel);
	}

	protected String getSelectedCarriageType() {
		return this.carriageComboBox.getSelectedItem().toString();
	}

	/**
	 * Call to throw message dialog
	 * 
	 * @param message
	 *            Message string to be shown
	 */
	protected void throwWarning(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	// GETTERS FOR FIELDS

	/**
	 * @return the optionPanelLocomotive
	 */
	protected JPanel getOptionPanelLocomotive() {
		return optionPanelLocomotive;
	}

	/**
	 * @return the optionPanelPassengerCar
	 */
	protected JPanel getOptionPanelPassengerCar() {
		return optionPanelPassengerCar;
	}

	/**
	 * @return the optionPanelFreightCar
	 */
	protected JPanel getOptionPanelFreightCar() {
		return optionPanelFreightCar;
	}

	/**
	 * @return the panelGroupCarriageTypeOptions
	 */
	protected JPanel getPanelGroupCarriageTypeOptions() {
		return panelCarriageTypeOptions;
	}

	/**
	 * @return the carriageTypes
	 */
	protected String[] getCarriageTypes() {
		return carriageTypes;
	}

	/**
	 * @return the carriageWeightSpinner
	 */
	protected JSpinner getCarriageWeightSpinner() {
		return carriageWeightSpinner;
	}

	/**
	 * @return the carriageComboBox
	 */
	protected JComboBox<String> getCarriageComboBox() {
		return carriageComboBox;
	}

	/**
	 * @return the addCarriageToTrainButton
	 */
	protected JButton getAddCarriageToTrainButton() {
		return addCarriageToTrainButton;
	}

	/**
	 * @return the removeCarriageButton
	 */
	protected JButton getRemoveCarriageButton() {
		return removeCarriageButton;
	}

	/**
	 * @return the passengerCarMetrics
	 */
	protected JLabel getPassengerCarMetrics() {
		return passengerCarMetrics;
	}

	/**
	 * @return the addPassengerSpinner
	 */
	protected JSpinner getAddPassengerSpinner() {
		return addPassengerSpinner;
	}

	/**
	 * @return the addPassengerButton
	 */
	protected JButton getAddPassengerButton() {
		return addPassengerButton;
	}

	/**
	 * @return the locomotiveEngineSpinner
	 */
	protected JSpinner getLocomotiveEngineSpinner() {
		return locomotiveEngineSpinner;
	}

	/**
	 * @return the locomotivePowerSpinner
	 */
	protected JSpinner getLocomotivePowerSpinner() {
		return locomotivePowerSpinner;
	}

	/**
	 * @return the carriageSeatsSpinner
	 */
	protected JSpinner getCarriageSeatsSpinner() {
		return carriageSeatsSpinner;
	}

	/**
	 * @return the freightTypeSpinner
	 */
	protected JSpinner getFreightTypeSpinner() {
		return freightTypeSpinner;
	}

}
