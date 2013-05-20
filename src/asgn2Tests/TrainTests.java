package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.*;
import asgn2Train.DepartingTrain;

/**
 * Tests for DepartingTrain class.
 * 
 * @author Murray Coggan - 8291951
 * 
 */
public class TrainTests {
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
	public void initialisation() throws TrainException {
		testDepartingTrain = new DepartingTrain();
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				DEFAULT_LOCO_CLASS);
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_FREIGHT_TYPE);
	}

	@Test
	public void testAddCarriage() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
	}

	/**
	 * The first car must be a locomotive.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testAddFirstCarriageNotLocomotive() throws TrainException {
		testDepartingTrain.addCarriage(testPassengerCar);
	}

	/**
	 * Cannot add carriages if there are passengers on board.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testAddCarriagePassengersOnTrain() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.board(1);
		testDepartingTrain.addCarriage(testFreightCar);
	}

	/**
	 * Order of carriages must be locomotive - any number of passenger cars -
	 * any number of freight cars.
	 * 
	 * A number of different orderings are being tested.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testInvalidTrainCarOrderOne() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.addCarriage(testPassengerCar);
	}

	@Test(expected = TrainException.class)
	public void testInvalidTrainCarOrderTwo() throws TrainException {
		testDepartingTrain.addCarriage(testFreightCar);
	}

	@Test(expected = TrainException.class)
	public void testInvalidTrainCarOrderThree() throws TrainException {
		testDepartingTrain.addCarriage(testPassengerCar);
	}

	@Test(expected = TrainException.class)
	public void testInvalidTrainCarOrderFour() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.addCarriage(testPassengerCar);
	}

	@Test(expected = TrainException.class)
	public void testInvalidTrainCarOrderFive() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.addCarriage(testLocomotive);
	}

	@Test(expected = TrainException.class)
	public void testInvalidTrainCarOrderSix() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.addCarriage(testLocomotive);
	}

	/**
	 * First carriage returns null if no carriages.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testFirstCarriageNull() throws TrainException {
		assertNull(testDepartingTrain.firstCarriage());
	}

	/**
	 * nextCarriage works if firstCarriage has not been called
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testFirstCarriageReturnsCorrectCarriage() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.firstCarriage(), testLocomotive);
	}

	/**
	 * nextCarriage returns the first carriage if called before first carriage
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testNextCarriageReturnsFirstCarriage() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.nextCarriage(), testLocomotive);
	}

	/**
	 * Successive nextCarriage calls return cars in order.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testNextCarriageReturnsCorrectCarriage() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testDepartingTrain.nextCarriage(), testLocomotive);
		assertEquals(testDepartingTrain.nextCarriage(), testPassengerCar);
	}

	/**
	 * Successive return of null on non-existant next carriage.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testNextCarriageReturnsNullNonexistentCarriage()
			throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.firstCarriage();
		assertNull(testDepartingTrain.nextCarriage());
	}

	/**
	 * Successive return of null on empty train.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testNextCarriageReturnsNullEmptyTrain() throws TrainException {
		assertNull(testDepartingTrain.nextCarriage());
	}

	/**
	 * Check that numberOnBoard returns the correct number.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testNumberOfPassengersBoarded() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.board(DEFAULT_PASSENGERS);
		assertEquals(testDepartingTrain.numberOnBoard(), DEFAULT_PASSENGERS);
	}

	/**
	 * Number of boarding passengers cannot be negative.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testBoardNegativePassengers() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.board(DEFAULT_NEGATIVE_PASSENGERS);
	}

}
