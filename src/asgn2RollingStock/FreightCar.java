package asgn2RollingStock;

import asgn2Exceptions.TrainException;

/**
 * Freight cars are designed to handle a variety of goods. For the purposes of
 * this assignment we assume there are three freight car types of interest,
 * characterised by the kinds of goods they are designed to carry:<br/>
 * <br/>
 * "G" - General goods<br/>
 * "R" - Refrigerated goods<br/>
 * "D" - Dangerous materials
 * 
 * @author Murray Coggan - 8291951
 * 
 */
public class FreightCar extends RollingStock {
	private String goodsType;

	/**
	 * 
	 * @param grossWeight
	 *            The freight car's gross weight (fully-laden), in tonnes.
	 * @param goodsType
	 *            The type of goods the car is designed to carry (either "G",
	 *            "R" or "D").
	 * @throws TrainException
	 *             If the gross weight is not positive or if the goods' type is
	 *             invalid.
	 */
	public FreightCar(Integer grossWeight, String goodsType)
			throws TrainException {
		super(grossWeight);
		if (!isValidGoodsType(goodsType)) {
			throw new TrainException("The goodsType must be valid");
		}
		this.goodsType = goodsType;
	}

	/**
	 * Returns the type of goods this carriage was designed to carry.
	 * 
	 * @return the goodsType (G", "R" or "D")
	 */
	public String goodsType() {
		return this.goodsType;
	}

	/**
	 * Determines if the specified goodsType string matches the scope of
	 * acceptable Freight goods.
	 * 
	 * @param goodsType
	 *            A single character string representing the type of goods.
	 * @return True if the goodsType was either G, R or D
	 */
	private boolean isValidGoodsType(String goodsType) {
		return goodsType.matches("G|R|D");
	}

	/**
	 * Returns a human-readable description of the freight car. This has the
	 * form "Freight(x)" where x is a character ("G", "R" or "D") indicating the
	 * type of goods the car is designed to carry.
	 * 
	 * @return Freight(x)
	 *         <em>where <strong>x</strong> is a character representation of the goodsType.
	 */
	@Override
	public String toString() {
		return "Freight(" + this.goodsType + ")";
	}

}
