/**
 * 
 */
package asgn2GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Lochlan Bunn - 8509719
 * @author Murray Coggan - 8291951
 *
 */
public class ControlPanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1139433227864661487L;
	
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
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
