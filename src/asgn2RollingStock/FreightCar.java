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

	private boolean isValidGoodsType(String goodsType) {
		return goodsType == "G" || goodsType == "R" || goodsType == "D";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
