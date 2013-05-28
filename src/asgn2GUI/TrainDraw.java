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
	private static final long serialVersionUID = -4580186180750958335L;
	
	final int CARRIAGE_HEIGHT = 50;
	final int CARRIAGE_WIDTH = 100;
	// distance used to iterate drawPos, will make carriages
	// draw 10 pixels apart from each other
	final int DISTANCE = CARRIAGE_WIDTH + 10;
	final int DEFAULT_DRAW_POS = 10;
	private int drawPos = 10;
	private static DepartingTrain theTrain;
	private int totalWeight;
	
	
	public TrainDraw() {
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * Call repaint() to utilise this function
	 * 
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); 
		if (theTrain != null){
			drawTrain(g);
		}
	}
	
	
	/**
	 * @param theTrain
	 * 
	 * Sets the train to be displayed to the passed train
	 * 
	 */
	public void SetTrain(DepartingTrain theTrain){
		this.theTrain = theTrain;
		RollingStock tempCar = theTrain.firstCarriage();
		totalWeight = 0;
		int tempNoCars = 0;
		while(tempCar != null){
			totalWeight += tempCar.getGrossWeight();
			tempCar = theTrain.nextCarriage();
			tempNoCars ++;
		}
		// Sets the width of the panel to the length of the train, plus padding
		this.setSize((tempNoCars*DISTANCE) + DEFAULT_DRAW_POS, 150);
		repaint();
	}
	
	/**
	 * Draws the currently set train as a series of rectangles, with information
	 * relating to each train carriage displayed on the side.
	 *
	 * @param g
	 * 		The Graphics used to draw the train.
	 */
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
		drawPos = DEFAULT_DRAW_POS;
	}
	
	
	/**
	 * Draws the passed Locomotive, along with two small bars representing the power,
	 * and the weight of the train relative to the power. Also draws text representing
	 * the power of the locomotive and the total weight of the train. 
	 * 
	 * @param g
	 * 		The Graphics used to draw the Locomotive
	 * @param theLoco
	 * 		The Locomotive to be drawn
	 */
	private void drawLoco(Graphics g, Locomotive theLoco){
		g.setColor(Color.BLUE);
		g.drawRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		g.fillRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		
		// Draws the small bars representing the power and relative weight of the 
		// Locomotive
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
			// Calculates the length of the lower bar as a percentage of the
			// power of the train
			float tempLength = ((float)totalWeight/(float)theLoco.power()*100);
			weightBarLength = (int)tempLength;
		}
		g.setColor(Color.RED);
		g.drawRect(drawPos, CARRIAGE_HEIGHT + barOffset, weightBarLength, barHeight);
		g.fillRect(drawPos, CARRIAGE_HEIGHT + barOffset, weightBarLength, barHeight);
		
		//draw the weight and power in the format weight/power on the side of the train
		g.setColor(Color.BLACK);
		String tempWeightPower = (totalWeight + "/" + theLoco.power());
		// Uses FontMetrics to get half of the width
		// of the passengers string for formatting
		FontMetrics fm = g.getFontMetrics();
		int stringWOffset = (fm.stringWidth(tempWeightPower)/2);
		int stringHOffset = 4;
		g.drawString(tempWeightPower, drawPos + (CARRIAGE_WIDTH/2) - stringWOffset,
				(CARRIAGE_HEIGHT/2) + stringHOffset);
	}
	
	
	/**
	 * Draws the specified PassengerCar, along with text on the carriage specifying the 
	 * weight of the car, and the number of passengers and the number of available seats 
	 * in the format passengers/seats
	 * 
	 * @param g
	 * 		The Graphics used to draw the PassengerCar
	 * @param thePassenger
	 * 		The PassengerCar to be drawn
	 */
	private void drawPassenger(Graphics g, PassengerCar thePassenger){
		g.setColor(Color.YELLOW);
		g.drawRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		g.fillRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		// Draw the number of passengers and the weight of the car on the side of the carriage
		String tempPassengers = thePassenger.numberOnBoard() + "/" + thePassenger.numberOfSeats();
		g.setColor(Color.BLACK);
		String tempWeight = "" + thePassenger.getGrossWeight();
		FontMetrics fm = g.getFontMetrics();
		int stringWOffset = (fm.stringWidth(tempWeight)/2);
		int stringHOffset = 4;
		g.drawString(tempWeight, drawPos + (CARRIAGE_WIDTH/2) - stringWOffset,
				(CARRIAGE_HEIGHT/2) - fm.getHeight() + stringHOffset*3);
		stringWOffset = (fm.stringWidth(tempPassengers)/2);
		g.drawString(tempPassengers, drawPos + (CARRIAGE_WIDTH/2) - stringWOffset,
				(CARRIAGE_HEIGHT/2) + stringHOffset*2);
	}
	
	
	/**
	 * Draws the specified FreightCar, along with text on the carriage specifying the
	 * type of freight carried and the weight of the car.
	 * @param g
	 * @param theFreight
	 */
	private void drawFreight(Graphics g, FreightCar theFreight){
		g.setColor(Color.GREEN);
		g.drawRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		g.fillRect(drawPos, 0, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		// Draw the goods and weight of the freight car on the side of the carriage
		g.setColor(Color.BLACK);
		String tempGoodsWeight = theFreight.goodsType() + " " + theFreight.getGrossWeight();
		FontMetrics fm = g.getFontMetrics();
		int stringWOffset = (fm.stringWidth(tempGoodsWeight)/2);
		int stringHOffset = 4;
		g.drawString(tempGoodsWeight, drawPos + (CARRIAGE_WIDTH/2) - stringWOffset,
				(CARRIAGE_HEIGHT/2) + stringHOffset);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 * 
	 * Used by the JScrollPane to get the size of this panel, enables scrollbars
	 */
	public Dimension getPreferredSize(){
		return new Dimension(this.getWidth(), this.getHeight());
	}
	
}
