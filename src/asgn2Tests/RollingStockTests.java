package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.xml.internal.bind.v2.TODO;

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

	// CONSTANTS
	final Integer DEFAULT_GROSS_WEIGHT = 100;
	final String DEFAULT_FREIGHT_TYPE = "D";

	/*
	 * extends RollingStock shared tests
	 */

	/**
	 * The car should not be able to have a negative tonnage.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testConstructFreightCarWithNegativeGrossWeight()
			throws TrainException {
		Integer negGrossWeight = DEFAULT_GROSS_WEIGHT * -1;
		testFreightCar = new FreightCar(negGrossWeight, DEFAULT_FREIGHT_TYPE);
	}

	/**
	 * The car should have to weigh <em>something</em>.
	 * 
	 * @throws TrainException
	 */
	@Test(expected = TrainException.class)
	public void testConstructFreightCarWithZeroGrossWeight()
			throws TrainException {
		Integer zeroGrossWeight = 0;
		testFreightCar = new FreightCar(zeroGrossWeight, DEFAULT_FREIGHT_TYPE);
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

	@Test
	public void testFreightCarReturnValidGoodsType() throws TrainException {
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_FREIGHT_TYPE);
		assertEquals("Return of valid goods type", DEFAULT_FREIGHT_TYPE,
				testFreightCar.goodsType());
	}

	@Test
	public void testFreightCarReturnValidGoodsTypeAgainstInvalidGoodsType()
			throws TrainException {
		testFreightCar = new FreightCar(DEFAULT_GROSS_WEIGHT,
				DEFAULT_FREIGHT_TYPE);
		assertFalse(testFreightCar.goodsType() == "O");
	}

	// copy of test
	@Test
	public void testtest() throws TrainException {

	}
}
