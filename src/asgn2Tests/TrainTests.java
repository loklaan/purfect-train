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
		assertEquals(testLocomotive, testDepartingTrain.firstCarriage());
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
		assertEquals(testLocomotive, testDepartingTrain.nextCarriage());
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
		assertEquals(testLocomotive, testDepartingTrain.nextCarriage());
		assertEquals(testPassengerCar, testDepartingTrain.nextCarriage());
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
		assertEquals(DEFAULT_PASSENGERS, testDepartingTrain.numberOnBoard());
		// TODO: do more tests for numberOnBoard & numberOfSeats
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

	/**
	 * An empty ("degenerate") train is deemed to be able to move. Spec.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testTrainCanMoveEmptyTrain() throws TrainException {
		assertTrue(testDepartingTrain.trainCanMove());
	}

	/**
	 * A train that weighs less than the maximum pulling power is able to move.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testTrainCanMoveValidTrainGrossWeight() throws TrainException {
		// power class 3E == 300 pulling power
		testDepartingTrain.addCarriage(new Locomotive(100, "3E"));
		testDepartingTrain
				.addCarriage(new PassengerCar(50, DEFAULT_SEAT_AMOUNT));
		testDepartingTrain
				.addCarriage(new FreightCar(50, DEFAULT_FREIGHT_TYPE));
		// total train weight == 200
		assertTrue(testDepartingTrain.trainCanMove());
	}

	/**
	 * A train that weighs the same as the maximum pulling power is able to
	 * move.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testTrainCanMoveMaxValidTrainGrossWeight()
			throws TrainException {
		// power class 3E == 300 pulling power
		testDepartingTrain.addCarriage(new Locomotive(100, "3E"));
		testDepartingTrain.addCarriage(new PassengerCar(100,
				DEFAULT_SEAT_AMOUNT));
		testDepartingTrain
				.addCarriage(new FreightCar(100, DEFAULT_FREIGHT_TYPE));
		// total train weight == 300
		assertTrue(testDepartingTrain.trainCanMove());
	}

	/**
	 * A train that weighs more than the maximum pulling power is not able to
	 * move.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testTrainCanMoveInvalidTrainGrossWeight() throws TrainException {
		// power class 3E == 300 pulling power
		testDepartingTrain.addCarriage(new Locomotive(100, "3E"));
		testDepartingTrain.addCarriage(new PassengerCar(100,
				DEFAULT_SEAT_AMOUNT));
		testDepartingTrain
				.addCarriage(new FreightCar(200, DEFAULT_FREIGHT_TYPE));
		// total train weight == 400
		assertFalse(testDepartingTrain.trainCanMove());
	}

	/**
	 * A carriage cannot be removed if the train has none.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testRemoveCarriageEmpty() throws TrainException {
		testDepartingTrain.removeCarriage();
	}

	/**
	 * A carriage cannot be removed if the train has passengers aboard.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testRemoveCarriageWithPassengers() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.board(DEFAULT_PASSENGERS);
		assertEquals(DEFAULT_PASSENGERS, testDepartingTrain.numberOnBoard());
		testDepartingTrain.removeCarriage();
	}

	/**
	 * A train consisting of only a Locomotive can have that Locomotive removed.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testRemoveCarriageLocomotive() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		assertEquals(testLocomotive, testDepartingTrain.firstCarriage());
		assertNull(testDepartingTrain.nextCarriage()); // there is no next car
		testDepartingTrain.removeCarriage();
	}

	/**
	 * A train consisting of a Locomotive and a Passenger Car can have that last
	 * Passenger Car removed.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testRemoveCarriageLocomotivePassengerCar()
			throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		assertEquals(testLocomotive, testDepartingTrain.firstCarriage());
		assertEquals(testPassengerCar, testDepartingTrain.nextCarriage());
		assertNull(testDepartingTrain.nextCarriage()); // there is no next car
		testDepartingTrain.removeCarriage();
	}

	/**
	 * A train consisting of a number of cars can have all those cars removed
	 * (including locomotive).
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testRemoveCarriageAllCarriagesInTrain() throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.addCarriage(testFreightCar);

		testDepartingTrain.removeCarriage();
		testDepartingTrain.removeCarriage();
		testDepartingTrain.removeCarriage();
	}

	/**
	 * firstCarriage should return a Locomotive after it's been added AFTER
	 * removing all carriages in a train.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testRemoveCarriageAllCarriagesInTrainGetFirstAfterReAddingLocomotive()
			throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.addCarriage(testFreightCar);

		testDepartingTrain.removeCarriage();
		testDepartingTrain.removeCarriage();
		testDepartingTrain.removeCarriage();

		testDepartingTrain.addCarriage(testLocomotive);

		assertEquals(
				"Should return Locomotive that was added on an *emptied* train.",
				testLocomotive, testDepartingTrain.firstCarriage());
	}

	/**
	 * nextCarriage after preceeding calls to firstCarriage or nextCarriage
	 * should be null after removing all carriages from the train and readding a
	 * locomotive.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testGetNextCarriageAfterRemovingAllAndAddingLocomotive()
			throws TrainException {
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.addCarriage(testFreightCar);

		assertEquals(testLocomotive, testDepartingTrain.firstCarriage());
		assertEquals(testPassengerCar, testDepartingTrain.nextCarriage());

		testDepartingTrain.removeCarriage();
		testDepartingTrain.removeCarriage();
		testDepartingTrain.removeCarriage();

		testDepartingTrain.addCarriage(testLocomotive);

		assertNull(
				"Should return null as the nextCarriage iterator cannot be followed after removing trains.",
				testDepartingTrain.nextCarriage());
	}

	/**
	 * An empty trains toString returns an empty string.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testToStringEmptyTrain() throws TrainException {
		assertEquals("", testDepartingTrain.toString());
	}

	/**
	 * A 'filled' trains toString, in this case, should be the same as specified
	 * by a combination of constants.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testToStringFilledTrain() throws TrainException {
		Integer boardedPassengers = 50;
		testDepartingTrain.addCarriage(testLocomotive);
		testDepartingTrain.addCarriage(testPassengerCar);
		testDepartingTrain.addCarriage(testFreightCar);
		testDepartingTrain.board(boardedPassengers);
		assertEquals("Loco(" + DEFAULT_LOCO_CLASS + ")-Passenger("
				+ boardedPassengers + "/" + DEFAULT_SEAT_AMOUNT + ")-Freight("
				+ DEFAULT_FREIGHT_TYPE + ")", testDepartingTrain.toString());
	}

}
