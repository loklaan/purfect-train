package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

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
	final Integer DEFAULT_PASSENGERS = 5;
	
	@Before
	public void initial() throws TrainException
	{
		testDepartingTrain = new DepartingTrain();
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, DEFAULT_LOCO_CLASS);
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT, DEFAULT_FREIGHT_TYPE);
	}
	
	@Test
	public void testAddCarriage() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
	}
	
	// the first car must be a locomotive
	@Test (expected = TrainException.class)
	public void testAddFirstCarriageNotLocomotive() throws TrainException
	{
		testDepartingTrain.addCarriage(testPassengerCar);
	}
	
	// cannot add carriages if there are passengers on board
	@Test (expected = TrainException.class)
	public void testAddCarriagePassengersOnTrain() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.board(1);
		testDepartingTrain.addCarriage(testFreightCar);
	}
	
	// order of carriages must be locomotive - any number of passenger
	// cars - any number of freight cars
	@Test (expected = TrainException.class)
	public void testInvalidTrainCarOrderOne() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.addCarriage(testPassengerCar);
	}
	@Test (expected = TrainException.class)
	public void testInvalidTrainCarOrderTwo() throws TrainException
	{
		testDepartingTrain.addCarriage(testFreightCar);
	}
	@Test (expected = TrainException.class)
	public void testInvalidTrainCarOrderThree() throws TrainException
	{
		testDepartingTrain.addCarriage(testPassengerCar);
	}
	@Test (expected = TrainException.class)
	public void testInvalidTrainCarOrderFour() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.addCarriage(testPassengerCar);
	}
	@Test (expected = TrainException.class)
	public void testInvalidTrainCarOrderFive() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.addCarriage(testLocomotive);
	}
	@Test (expected = TrainException.class)
	public void testInvalidTrainCarOrderSix() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.addCarriage(testLocomotive);
	}
	
	// first carriage returns null if no carriages
	@Test
	public void testFirstCarriageNull() throws TrainException
	{
		assertNull(testDepartingTrain.firstCarriage());
	}
	
	@Test
	public void testFirstCarriageReturnsCorrectCarriage() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.firstCarriage(), testLocomotive);
	}
	
	// nextCarriage returns the first carriage if called before first carriage
	@Test
	public void testNextCarriageReturnsFirstCarriage() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.nextCarriage(), testLocomotive);
	}
	
	// successive nextCarriage calls return cars in order
	@Test
	public void testNextCarriageReturnsCorrectCarriage() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.nextCarriage(), testLocomotive);
		assertEquals(testDepartingTrain.nextCarriage(), testPassengerCar);
	}
	
	// successive return of null on non-existant next carriage
	@Test
	public void testNextCarriageReturnsNullNonexistentCarriage() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.firstCarriage();
		assertNull(testDepartingTrain.nextCarriage());
	}
	
	// successive return of null on empty train
	@Test
	public void testNextCarriageReturnsNullEmptyTrain() throws TrainException
	{
		assertNull(testDepartingTrain.nextCarriage());
	}
	
	// check that numberOnBoard returns the correct number
	@Test
	public void testNumberOfPassengersBoarded() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.board(DEFAULT_PASSENGERS);
		assertEquals(testDepartingTrain.numberOnBoard(), DEFAULT_PASSENGERS);
	}
	
	// number of boarding passengers cannot be negative
	@Test (expected = TrainException.class)
	public void testBoardNegativePassengers() throws TrainException
	{
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.board(DEFAULT_NEGATIVE_PASSENGERS);
	}

}
