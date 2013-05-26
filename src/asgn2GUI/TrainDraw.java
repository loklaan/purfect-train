package asgn2GUI;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.*;
import asgn2Train.DepartingTrain;

/**
 * @author Lochlan Bunn - 8509719
 * @author Murray Coggan - 8291951
 * 
 */

public class TrainDraw extends JPanel {
	final static int WIDTH = 500;
	final static int HEIGHT = 200;
	final int CARRIAGE_HEIGHT = 50;
	final int CARRIAGE_WIDTH = 100;
	final int DISTANCE = CARRIAGE_WIDTH + 10;
	private int drawPos = 0;
	private static DepartingTrain theTrain;
	private static Graphics graphics;
	private int totalWeight;
	
	
	public TrainDraw() {
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); 
		if (theTrain != null){
			drawTrain(g);
		}
	}
	
	public void SetTrain(DepartingTrain theTrain){
		this.theTrain = theTrain;
		RollingStock tempCar = theTrain.firstCarriage();
		while(tempCar != null){
			totalWeight += tempCar.getGrossWeight();
			tempCar = theTrain.nextCarriage();
		}
		
	}
	
	private void drawTrain(Graphics g){
		RollingStock currentCar = theTrain.firstCarriage();
		while (currentCar != null){
			if (currentCar instanceof Locomotive){
				drawLoco(g, (Locomotive)currentCar);
			} else if (currentCar instanceof PassengerCar){
				drawPassenger(g, (PassengerCar)currentCar);
			}else if (currentCar instanceof FreightCar){
				drawFreight(g, (FreightCar)currentCar);
			}
			drawPos += DISTANCE;
			currentCar = theTrain.nextCarriage();
		}
		drawPos = 0;
	}
	
	
	private void drawLoco(Graphics g, Locomotive theLoco){
		g.setColor(Color.BLUE);
		g.drawRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		g.fillRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		
		int barHeight = 7;
		int weightBarLength = 0;
		int barOffset = 5;
		g.setColor(Color.GREEN);
		g.drawRect(drawPos, CARRIAGE_HEIGHT + barOffset, CARRIAGE_WIDTH, barHeight);
		g.fillRect(drawPos, CARRIAGE_HEIGHT + barOffset, CARRIAGE_WIDTH, barHeight);
		barOffset +=(barOffset + barHeight);
		
		if (theLoco.power() < totalWeight){
			weightBarLength = CARRIAGE_WIDTH;
		} else {
			float tempLength = ((float)totalWeight/(float)theLoco.power()*100);
			weightBarLength = (int)tempLength;
		}
		g.setColor(Color.RED);
		g.drawRect(drawPos, CARRIAGE_HEIGHT + barOffset, weightBarLength, barHeight);
		g.fillRect(drawPos, CARRIAGE_HEIGHT + barOffset, weightBarLength, barHeight);
	}
	
	private void drawPassenger(Graphics g, PassengerCar thePassenger){
		g.setColor(Color.RED);
		g.drawRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		g.fillRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		PassengerCar tempCarriage = thePassenger;
		String tempPassengers = tempCarriage.numberOnBoard() + "/" + tempCarriage.numberOfSeats();
		g.setColor(Color.BLACK);
		// Uses FontMetrics to get half of the width
		// of the passengers string for formatting
		FontMetrics fm = g.getFontMetrics();
		int stringWOffset = (fm.stringWidth(tempPassengers)/2);
		int stringHOffset = 4;
		g.drawString(tempPassengers, drawPos + (CARRIAGE_WIDTH/2) - stringWOffset,
				(CARRIAGE_HEIGHT/2) + stringHOffset);
	}
	
	private void drawFreight(Graphics g, FreightCar theFreight){
		g.setColor(Color.GREEN);
		g.drawRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		g.fillRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
	}
	
	
}
