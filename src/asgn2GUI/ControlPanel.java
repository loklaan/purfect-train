/**
 * 
 */
package asgn2GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

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
	private JScrollPane panelTrain;
	private JPanel panelConductor;
	private JPanel panelDriver;

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public ControlPanel(String title) throws HeadlessException {
		super(title);

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

//		this.panelTrain = createTrainCanvas();
//		this.getContentPane().add(panelTrain, BorderLayout.NORTH);

		this.panelDriver = createDriverControlPanel();
		this.getContentPane().add(panelDriver, BorderLayout.CENTER);

		this.panelConductor = createConductorControlPanel();
		this.getContentPane().add(panelConductor, BorderLayout.EAST);
		
		// end of method
		repaint();
	}

	private JPanel createConductorControlPanel() {
		JPanel panel = createDefaultControlPanel(CONDUCTOR_ROLE);
		JButton button = new JButton("Great Button");

		panel.add(button);

		return panel;
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
