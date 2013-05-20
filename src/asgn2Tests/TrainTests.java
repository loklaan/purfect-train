package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.FreightCar;
import asgn2RollingStock.Locomotive;
import asgn2RollingStock.PassengerCar;
import asgn2RollingStock.RollingStock;
import asgn2Train.DepartingTrain;

/**
 * Tests for DepartingTrain class.
 * @author Murray Coggan - 8291951
 *
 */
public class TrainTests {
	private RollingStock testRollingStock;
	private FreightCar testFreightCar;
	private Locomotive testLocomotive;
	private PassengerCar testPassengerCar;
	private DepartingTrain testDepartingTrain;

	// CONSTANTS
	final Integer DEFAULT_GROSS_WEIGHT = 100;
	final Integer DEFAULT_NEGATIVE_GROSS_WEIGHT = -100;
	final Integer ZERO = 0;
	final String DEFAULT_FREIGHT_TYPE = "D";
	final Integer DEFAULT_LOCO_POWER = 4;
	final String DEFAULT_LOCO_ENGINE = "E";
	final String DEFAULT_LOCO_CLASS = DEFAULT_LOCO_POWER + DEFAULT_LOCO_ENGINE;
	final Integer DEFAULT_SEAT_AMOUNT = 100;
	final Integer DEFAULT_NEGATIVE_PASSENGERS = -1;

	@Test
	public void testAddCarriage() 
	{
		testDepartingTrain = new DepartingTrain();
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, DEFAULT_LOCO_CLASS);
		testDepartingTrain.addCarriage(testLocomotive);
	}
	
	// the first car must be a locomotive
	@Test (expected = TrainException.class)
	void testAddFirstCarriageNotLocomotive()
	{
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testDepartingTrain = new DepartingTrain();
		testDepartingTrain.addCarriage(testPassengerCar);
	}
	
	// first carriage returns null if no carriages
	@Test
	public void testFirstCarriageNull()
	{
		testDepartingTrain = new DepartingTrain();
		assertNull(testDepartingTrain.firstCarriage);
	}
	
	@Test
	public void testFirstCarriageReturnsCorrectCarriage()
	{
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, DEFAULT_LOCO_CLASS);
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testDepartingTrain = new DepartingTrain();
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.firstCarriage, testLocomotive);
	}
	
	// nextCarriage returns the first carriage if called before first carriage
	@Test
	public void testNextCarriageReturnsFirstCarriage()
	{
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, DEFAULT_LOCO_CLASS);
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testDepartingTrain = new DepartingTrain();
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.nextCarriage, testLocomotive);
	}
	
	// successive nextCarriage calls return cars in order
	@Test
	public void testNextCarriageReturnsCorrectCarriage()
	{
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, DEFAULT_LOCO_CLASS);
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testDepartingTrain = new DepartingTrain();
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.nextCarriage, testLocomotive);
		assertEquals(testDepartingTrain.nextCarriage, testPassengerCar);
	}
	
	// number of boarding passengers cannot be negative
	@Test (expected = TrainException.class)
	public void testBoardNegativePassengers()
	{
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, DEFAULT_LOCO_CLASS);
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testDepartingTrain = new DepartingTrain();
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.board(DEFAULT_NEGATIVE_PASSENGERS);
	}

}
