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
	// TODO comment these locomotive tests

	@Test(expected = TrainException.class)
	public void testLocomotiveWithInvalidClassPower() throws TrainException {
		String invalidPower = "10";
		String electricEngine = "E";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, invalidPower
				+ electricEngine);
	}

	@Test(expected = TrainException.class)
	public void testLocomotiveWithInvalidClassEngine() throws TrainException {
		String power = "5";
		String invalidHydrogenEngine = "H";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, power
				+ invalidHydrogenEngine);
	}

	@Test
	public void testLocomotiveWithMaxPowerClass() throws TrainException {
		String validMaxPowerClass = "9E";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				validMaxPowerClass);
	}

	@Test
	public void testLocomotiveWithMinPowerClass() throws TrainException {
		String validMinPowerClass = "1E";
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				validMinPowerClass);
	}

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

	@Test
	public void testLocomotiveReturnValidPower() throws TrainException {
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT,
				DEFAULT_LOCO_CLASS);
		assertEquals("Returned power should be " + (DEFAULT_LOCO_POWER * 100),
				Integer.valueOf(DEFAULT_LOCO_POWER * 100),
				testLocomotive.power());
	}

	@Test
	public void testLocomotiveReturnValidMaxPower() throws TrainException {
		Integer validMaxPower = 9;
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, validMaxPower
				+ DEFAULT_LOCO_ENGINE);
		assertEquals(Integer.valueOf(validMaxPower * 100),
				testLocomotive.power());
	}

	@Test
	public void testLocomotiveReturnValidMinPower() throws TrainException {
		Integer validMinPower = 1;
		testLocomotive = new Locomotive(DEFAULT_GROSS_WEIGHT, validMinPower
				+ DEFAULT_LOCO_ENGINE);
		assertEquals(Integer.valueOf(validMinPower * 100),
				testLocomotive.power());
	}

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

	@Test(expected = TrainException.class)
	public void testPassengerCarWithNegativeSeats() throws TrainException {
		Integer negSeatAmount = -100;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT, negSeatAmount);
	}

	@Test
	// shouldn't throw an exception (its in the spec)
	public void testPassengerCarWithZeroSeats() throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT, ZERO);
	}

	@Test(expected = TrainException.class)
	public void testPassengerCarBoardNegativeNewPassengers()
			throws TrainException {
		Integer negNewPassengerAmount = -20;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(negNewPassengerAmount);
	}

	@Test
	public void testPassengerCarBoardZeroNewPassengers() throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding zero passengers will return a unable-to-board count of zero",
				ZERO, testPassengerCar.board(ZERO));
	}

	@Test
	public void testPassengerCarBoardNewPassengersHalfOfMaxSeats()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding fifty passengers in a car of 100 seats will return a unable-to-board count of zero",
				ZERO, testPassengerCar.board(50));
	}

	@Test
	public void testPassengerCarBoardNewPassengersMaxSeats()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding one 100 passengers in a car of 100 seats will return a unable-to-board count of zero",
				ZERO, testPassengerCar.board(DEFAULT_SEAT_AMOUNT));
	}

	@Test
	public void testPassengerCarBoardNewPassengersOneOverMaxSeats()
			throws TrainException {
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		assertEquals(
				"Boarding 101 passengers in a car of 100 seats will return a unable-to-board count of 1",
				1, testPassengerCar.board(DEFAULT_SEAT_AMOUNT + 1));
	}

	@Test(expected = TrainException.class)
	public void testPassengerCarAlightNegativeAmount() throws TrainException {
		negAlightAmount = -50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.alight(negAlightAmount);
	}

	@Test(expected = TrainException.class)
	public void testPassengerCarBoardNewPassengersAlightPassengersExceedingBoarded()
			throws TrainException {
		alightAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(alightAmount);
		testPassengerCar.alight(alightAmount + 1);
	}

	@Test(expected = TrainException.class)
	public void testPassengerCarAlightPassengersExceedingBoarded()
			throws TrainException {
		alightAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.alight(alightAmount);
	}

	@Test
	public void testPassengerCarBoardNewPassengersAlightAllPassengersBoarded()
			throws TrainException {
		boardAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(boardAmount);
		testPassengerCar.alight(boardAmount);
	}
	
	@Test
	public void testPassengerCarBoardNewPassengersAlightLessPassengersBoarded()
			throws TrainException {
		boardAmount = 50;
		testPassengerCar = new PassengerCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_SEAT_AMOUNT);
		testPassengerCar.board(boardAmount);
		testPassengerCar.alight(boardAmount - 1);
	}
	
	// TODO tests for the numberOnBoard method
	// TODO tests for the numberOnSeats method
	// TODO tests for the toString method
	
	// TODO onto tests for DepartingTrain in TrainTests

	// copy of test
	@Test(expected = TrainException.class)
	public void testtest() throws TrainException {

	}
}
