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
	private final int WIDTH = 900;
	private final int HEIGHT = 600;
	private static final String PROGRAM_TITLE = "Train Control Panel v0.1";
	final String DRIVER_ROLE = "Train Driver";
	final String CONDUCTOR_ROLE = "Train Conductor";

	// FIELDS
	private JPanel panelControls;
	private JPanel panelConductor;
	private JPanel panelDriver;
	private JScrollPane panelTrain;
	private TrainDraw trainCanvas;

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

	private JScrollPane createTrainCanvas() {
		this.trainCanvas = new TrainDraw();
		JScrollPane scrollPanel = new JScrollPane(this.trainCanvas);
		
		scrollPanel.setSize(100, 200);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		return scrollPanel;
	}

	private JPanel createConductorControlPanel() {
		JPanel panel = createDefaultControlPanel(CONDUCTOR_ROLE);
		GridBagConstraints constraints = new GridBagConstraints();

		// Default constraints
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(0, 5, 0, 5);

		// Passenger count label
		JLabel passengerCarMetricsLabel = new JLabel("Passengers:");
		passengerCarMetricsLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panel, passengerCarMetricsLabel, constraints, 0, 0, 1, 1);
		
		// Passenger count
		JLabel passengerCarMetrics = new JLabel("PEOPLE/SEATS");
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panel, passengerCarMetrics, constraints, 1, 0, 1, 1);
		
		// Add passenger label
		JLabel addPassengerLabel = new JLabel("Add passengers:");
		addPassengerLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
		constraints.anchor = GridBagConstraints.EAST;
		addToPanel(panel, addPassengerLabel, constraints, 0, 2, 1, 1);
		
		// Add passenger input text field
		JTextField addPassengerField = new JTextField();
		constraints.anchor = GridBagConstraints.WEST;
		addToPanel(panel, addPassengerField, constraints, 1, 2, 1, 1);
		addPassengerField.setColumns(4);
		
		// Add passenger button
		JButton addPassengerButton = new JButton("Add");
		addToPanel(panel, addPassengerButton, constraints, 2, 2, 1, 1);

		return panel;
	}

	private void addToPanel(JPanel panel, Component component,
			GridBagConstraints constraints, int x, int y, int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		panel.add(component, constraints);
	}

	private JPanel createDriverControlPanel() {
		JPanel panel = createDefaultControlPanel(DRIVER_ROLE);

		return panel;
	}

	/**
	 * Default controller panel to be used by other methods creating a specific
	 * controller panel.
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
