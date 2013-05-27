package asgn2GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.*;
import asgn2Train.DepartingTrain;

public class DrawTest extends JFrame implements ActionListener {
	private static final long serialVersionUID = -7031008862559936404L;
	private static TrainDraw theGraphics;
	private static DepartingTrain theTrain;
	private static JPanel btmPanel;
	private static JScrollPane scroller;
	private static int WIDTH = 600;
	private static int HEIGHT = 200;
	
	public DrawTest(){
		createGUI();
	}
	
	
	private void createGUI() {
		setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());

	    theGraphics = new TrainDraw();
	    theGraphics.setBackground(Color.LIGHT_GRAY);
	    scroller = new JScrollPane(theGraphics);
	    add(scroller,BorderLayout.CENTER);

	    btmPanel = new JPanel();
	    btmPanel.setBackground(Color.LIGHT_GRAY);
        btmPanel.setLayout(new FlowLayout());
        
	    JButton loadButton = new JButton("Gen Train");
	    loadButton.setBackground(Color.WHITE);
	    loadButton.addActionListener(this);
	    btmPanel.add(loadButton);
	    this.getContentPane().add(btmPanel, BorderLayout.SOUTH);
	    
	    scroller.setSize(100, HEIGHT);
	    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) throws TrainException {
		DrawTest gui = new DrawTest();
	    gui.setVisible(true);
		theTrain = new DepartingTrain();
		Locomotive testLoco = new Locomotive(200, "5E");
		PassengerCar testPass = new PassengerCar(100, 20);
		PassengerCar testPass2 = new PassengerCar(10, 400);
		FreightCar testFreight = new FreightCar(10, "D");
		theTrain.addCarriage(testLoco);
		theTrain.addCarriage(testPass);
		theTrain.addCarriage(testPass2);
		theTrain.addCarriage(testPass);
		theTrain.addCarriage(testFreight);
		theTrain.addCarriage(testFreight);
		theTrain.addCarriage(testFreight);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonString = e.getActionCommand();

		  if (buttonString.equals("Gen Train")) {
			  theGraphics.SetTrain(theTrain);
			  //theGraphics.repaint();
		  } else {
			  System.err.println("Unexpected Error");
		  }
		
	}

	
}
