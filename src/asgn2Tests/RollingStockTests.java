package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.*;

/**
 * Tests for the various <em>Carriage</em> classes in the
 * <strong>RollingStock</strong> package
 * 
 * @author Lochlan Bunn - 8509719
 * 
 */
public class RollingStockTests {
	private RollingStock testRollingStock;
	private FreightCar testFreightCar;
	private Locomotive testLocomotive;
	private PassengerCar testPassengerCar;

	// CONSTANTS
	final Integer DEFAULT_GROSS_WEIGHT = 100;
	final Integer DEFAULT_NEGATIVE_GROSS_WEIGHT = -100;
	final Integer ZERO = 0;
	final String DEFAULT_FREIGHT_TYPE = "D";
	final Integer DEFAULT_LOCO_POWER = 4;
	final String DEFAULT_LOCO_ENGINE = "E";
	final String DEFAULT_LOCO_CLASS = DEFAULT_LOCO_POWER + DEFAULT_LOCO_ENGINE;
	final Integer DEFAULT_SEAT_AMOUNT = 100;

	/*
	 * extends RollingStock shared tests
	 */

	// The cars should not be able to have a negative tonnage.

	@Test(expected = TrainException.class)
	public void testConstructFreightCarWithNegativeGrossWeight()
			throws TrainException {
		testFreightCar = new FreightCar(DEFAULT_NEGATIVE_GROSS_WEIGHT,
				DEFAULT_FREIGHT_TYPE);
	}

	@Test(expected = TrainException.class)
	public void testConstructLocomotiveWithNegativeGrossWeight()
			throws TrainException {
		testLocomotive = new Locomotive(DEFAULT_NEGATIVE_GROSS_WEIGHT,
				DEFAULT_LOCO_CLASS);
	}

	@Test(expected = TrainException.class)
	public void testConstructPassengerCarWithNegativeGrossWeight()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_NEGATIVE_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
	}

	// The cars should have to weigh <em>something</em>.

	@Test(expected = TrainException.class)
	public void testConstructFreightCarWithZeroGrossWeight()
			throws TrainException {
		testFreightCar = new FreightCar(ZERO, DEFAULT_FREIGHT_TYPE);
	}

	@Test(expected = TrainException.class)
	public void testConstructLocomotiveWithZeroGrossWeight()
			throws TrainException {
		testLocomotive = new Locomotive(ZERO, DEFAULT_LOCO_CLASS);
	}

	@Test(expected = TrainException.class)
	public void testConstructPassengerCarWithZeroGrossWeight()
			throws TrainException {
		testPassengerCar = new PassengerCar(ZERO, DEFAULT_SEAT_AMOUNT);
	}

	// Cars should return weight

	@Test
	public void testFreightCarDefaultGetGrossWeight() throws TrainException {
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_FREIGHT_TYPE);
		assertEquals("Default constructed car returns default weight",
				DEFAULT_GROSS_WEIGHT, testFreightCar.getGrossWeight());
	}

	@Test
	public void testLocomotiveDefaultGetGrossWeight() throws TrainException {
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				DEFAULT_LOCO_CLASS);
		assertEquals("Default constructed car returns default weight",
				DEFAULT_GROSS_WEIGHT, testLocomotive.getGrossWeight());
	}

	@Test
	public void testPassengerCarDefaultGetGrossWeight() throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals("Default constructed car returns default weight",
				DEFAULT_GROSS_WEIGHT, testPassengerCar.getGrossWeight());
	}

	/*
	 * Freight Car specific tests
	 */

	/**
	 * The freight can only carry goods of a certain type, and they should be
	 * characterised correcty with specific character representation:<br/>
	 * <br/>
	 * "G" - General goods<br/>
	 * "R" - Refrigerated goods<br/>
	 * "D" - Dangerous materials
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testConstructFreightCarWithValidGoodsType()
			throws TrainException {
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT, "G");
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT, "R");
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT, "D");
	}

	/**
	 * Freight with incorrectly specified good types should throw an Exception.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testConstructFreightCarWithInValidGoodsType()
			throws TrainException {
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT, "Z");
		// TODO make this go through every character, or atleast alot?
	}

	/**
	 * The return of FreightCar's GoodsType() should be the same as the
	 * goodsType first used in Construction.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testFreightCarReturnGoodsType() throws TrainException {
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_FREIGHT_TYPE);
		assertEquals("Returned goodsType is equal to default freight type.",
				DEFAULT_FREIGHT_TYPE, testFreightCar.goodsType());
	}

	/**
	 * The human readable representation of a FreightCar should be like
	 * 'Freight(G)'.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testFreightTrainToString() throws TrainException {
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_FREIGHT_TYPE);
		assertEquals(
				"Representation of FreightTrain should be like 'Freight(x)'.",
				"Freight(" + DEFAULT_FREIGHT_TYPE + ")",
				testFreightCar.toString());
	}

	/*
	 * Locomotive specific tests
	 */

	/**
	 * Invalid power amount in classification should not be allowed.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testLocomotiveWithInvalidClassPower() throws TrainException {
		String invalidPower = "10";
		String electricEngine = "E";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, invalidPower
				+ electricEngine);
	}

	/**
	 * Invalid engine type in classification should not be allowed.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testLocomotiveWithInvalidClassEngine() throws TrainException {
		String power = "5";
		String invalidHydrogenEngine = "H";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, power
				+ invalidHydrogenEngine);
	}

	/**
	 * Valid max power classification.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testLocomotiveWithMaxPowerClass() throws TrainException {
		String validMaxPowerClass = "9E";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				validMaxPowerClass);
	}

	/**
	 * Valid min power calssification.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testLocomotiveWithMinPowerClass() throws TrainException {
		String validMinPowerClass = "1E";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				validMinPowerClass);
	}

	/**
	 * All valid engine types in classification.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testLocomotiveAllValidClassEngineTypes() throws TrainException {
		String power = "5";
		String electric = "E";
		String diesel = "D";
		String steam = "S";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, power + electric);
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, power + diesel);
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, power + steam);
	}

	/**
	 * Returned pulling power should be the power classification * 100
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testLocomotiveReturnValidPower() throws TrainException {
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				DEFAULT_LOCO_CLASS);
		assertEquals("Returned power should be " + (DEFAULT_LOCO_POWER * 100),
				Integer.valueOf(DEFAULT_LOCO_POWER * 100),
				testLocomotive.power());
	}

	/**
	 * Returned pulling power should be the power classification * 100
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testLocomotiveReturnValidMaxPower() throws TrainException {
		Integer validMaxPower = 9;
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, validMaxPower
				+ DEFAULT_LOCO_ENGINE);
		assertEquals(Integer.valueOf(validMaxPower * 100),
				testLocomotive.power());
	}

	/**
	 * Returned pulling power should be the power classification * 100
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testLocomotiveReturnValidMinPower() throws TrainException {
		Integer validMinPower = 1;
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, validMinPower
				+ DEFAULT_LOCO_ENGINE);
		assertEquals(Integer.valueOf(validMinPower * 100),
				testLocomotive.power());
	}

	/**
	 * The human readable representation of a FreightCar should be like
	 * 'Loco(5E)'.
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testLocomotiveToString() throws TrainException {
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				DEFAULT_LOCO_CLASS);
		assertEquals("Representation of Locomotive should be like 'Loco(x)'.",
				"Loco(" + DEFAULT_LOCO_CLASS + ")", testLocomotive.toString());
	}

	/*
	 * Passenger Car specific tests
	 */
	// TODO comment these passengercar tests

	/**
	 * Passenger cars cannot have negative seating amounts
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testPassengerCarWithNegativeSeats() throws TrainException {
		Integer negSeatAmount = -100;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT, negSeatAmount);
	}

	/**
	 * According to the holy spec, zero seats in a Passenger car should be
	 * allowed valid
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarWithZeroSeats() throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT, ZERO);
	}

	/**
	 * You should be able to board a negative amount of new passengers
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testPassengerCarBoardNegativeNewPassengers()
			throws TrainException {
		Integer negNewPassengerAmount = -20;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(negNewPassengerAmount);
	}

	/**
	 * Boarding zero new passengers should work
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarBoardZeroNewPassengers() throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding zero passengers will return a unable-to-board count of zero",
				ZERO, testPassengerCar.board(ZERO));
	}

	/**
	 * Boarding half the seat amount should be fine with no left over passengers
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarBoardNewPassengersHalfOfMaxSeats()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding fifty passengers in a car of 100 seats will return a unable-to-board count of zero",
				ZERO, testPassengerCar.board(50));
	}

	/**
	 * Boarding max amount of passenger to seats should have no left over
	 * passengers
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarBoardNewPassengersMaxSeats()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding one 100 passengers in a car of 100 seats will return a unable-to-board count of zero",
				ZERO, testPassengerCar.board(DEFAULT_SEAT_AMOUNT));
	}

	/**
	 * Attempting to board one to many passengers on a car should have one left
	 * over passenger
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarBoardNewPassengersOneOverMaxSeats()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding 101 passengers in a car of 100 seats will return a unable-to-board count of 1",
				1, testPassengerCar.board(DEFAULT_SEAT_AMOUNT + 1));
	}

	/**
	 * Alighting a negative amount of passengers should not be valid
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testPassengerCarAlightNegativeAmount() throws TrainException {
		Integer negAlightAmount = -50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.alight(negAlightAmount);
	}

	/**
	 * Alighting more passengers then are currently boarded should not be
	 * allowed
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testPassengerCarBoardNewPassengersAlightPassengersExceedingBoarded()
			throws TrainException {
		Integer alightAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(alightAmount);
		testPassengerCar.alight(alightAmount + 1);
	}

	/**
	 * Alighting from a new unboarded train should not be allowed
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testPassengerCarAlightPassengersExceedingZeroBoarded()
			throws TrainException {
		Integer alightAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.alight(alightAmount);
	}

	/**
	 * Alighting all passengers in a car should be allowed
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarBoardNewPassengersAlightAllPassengersBoarded()
			throws TrainException {
		Integer boardAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(boardAmount);
		testPassengerCar.alight(boardAmount);
	}

	/**
	 * Alighting one less then all passengers should be allowed
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarBoardNewPassengersAlightLessPassengersBoarded()
			throws TrainException {
		Integer boardAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(boardAmount);
		testPassengerCar.alight(boardAmount - 1);
	}

	/**
	 * Boarding zero passengers should on return of numberOnBoard, be zero
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarNumberOnBoardWithZeroBoarded()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(ZERO);
		assertEquals("Zero passengers boarded.", ZERO,
				testPassengerCar.numberOnBoard());
	}

	/**
	 * Return of amount of passengers boarded after boarding shoul be equal
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarNumberOnBoardWithSomeBoarded()
			throws TrainException {
		Integer boardAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(boardAmount);
		assertEquals("Fifty passengers boarded.", boardAmount,
				testPassengerCar.numberOnBoard());
	}

	/**
	 * Return of amount of passengers boarded after boarding max amount should
	 * be equal
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarNumberOnBoardWithMaxBoarded()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(DEFAULT_SEAT_AMOUNT);
		assertEquals("100 passengers boarded.", DEFAULT_SEAT_AMOUNT,
				testPassengerCar.numberOnBoard());
	}

	/**
	 * After attempted overboarded, returning numberOnBoard should be max
	 * seats/passengers
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarNumberOnBoardAfterAttemptedOverBoarding()
			throws TrainException {
		Integer boardAmount = DEFAULT_SEAT_AMOUNT + 10;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding 110 with only 100 seats, 10 left over passengers",
				10, testPassengerCar.board(boardAmount));
		assertEquals("110 passengers attempted to board, 100 should be sitted",
				DEFAULT_SEAT_AMOUNT, testPassengerCar.numberOnBoard());
	}

	/**
	 * After boarding and alighting passengers, return of numberOnBoard should
	 * be level
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarNumberOnBoardAfterBoardingAfterAlighting()
			throws TrainException {
		Integer boardAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals("Boarding 50 passengers, none left over", ZERO,
				testPassengerCar.board(boardAmount));
		testPassengerCar.alight(boardAmount - 10);
		assertEquals("50 passengers board, 40 were alighted. 10 should remain",
				boardAmount - 10, testPassengerCar.numberOnBoard());
	}

	/**
	 * Return should be the same as default constructor used
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarNumberOfSeatsDefaultAmount()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals("Default amount of seats allocated at construction.",
				DEFAULT_SEAT_AMOUNT, testPassengerCar.numberOfSeats());
	}

	/**
	 * Zero seats should have numberOfSeats return zero
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarNumberOfSeatsZeroAmount() throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT, ZERO);
		assertEquals("Zero amount of seats allocated at construction.", ZERO,
				testPassengerCar.numberOfSeats());
	}

	/**
	 * Getting toString of PassengerCar with no boarded passengers should look
	 * like 'Passenger(0/100)'
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarToStringZeroBoarded() throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals("Zero passengers on a default constructed car",
				"Passenger(" + ZERO + "/" + DEFAULT_SEAT_AMOUNT + ")",
				testPassengerCar.toString());
	}

	/**
	 * Getting toString of PassengerCar with some boarded passengers should look
	 * like 'Passenger(50/100)'
	 * 
	 * @throws TrainException
	 */
	@Test
	public void testPassengerCarToStringSomeBoarded() throws TrainException {
		Integer boardedAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals("All 50 passengers board.", ZERO,
				testPassengerCar.board(boardedAmount));
		assertEquals("50 passengers on a default constructed car", "Passenger("
				+ boardedAmount + "/" + DEFAULT_SEAT_AMOUNT + ")",
				testPassengerCar.toString());
	}

}
