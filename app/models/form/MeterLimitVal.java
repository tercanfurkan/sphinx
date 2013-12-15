package models.form;

public class MeterLimitVal {

	public MeterLimitVal() {
		// Slider min max default values
		this.diameterMin = 333;
		this.diameterMax = 686;
		this.floorAreaMin = 16;
		this.floorAreaMax = 33;
		this.beachDistanceMin = 66;
		this.beachDistanceMax = 133;
		this.blockageMin = 1;
		this.blockageMax = 3;
		this.flushingMin = 5;
		this.flushingMax = 10;
		this.extraWaterMin = 6;
		this.extraWaterMax = 12;
		this.cctvMin = 11;
		this.cctvMax = 22;
		this.cctv34Min = 8;
		this.cctv34Max = 17;
		
		// Radio button min max default values
		this.groundWaterAreaMin = 1;
		this.groundWaterAreaMax = 2;
		this.roadClassMin = 2;
		this.roadClassMax = 3;
		
		// Sewer types? Lateral gravity sewers, Collection sewers, Gravity sewer mains, Pressure sewers, Small pressure sewers?????
	}

	// Slider min max values
	public float diameterMin;
	public float diameterMax;
	public float floorAreaMin;
	public float floorAreaMax;
	public float beachDistanceMin;
	public float beachDistanceMax;
	public float blockageMin;
	public float blockageMax;
	public float flushingMin;
	public float flushingMax;
	public float extraWaterMin;
	public float extraWaterMax;
	public float cctvMin;
	public float cctvMax;
	public float cctv34Min;
	public float cctv34Max;
	
	// Radio button min max values
	public float groundWaterAreaMin;
	public float groundWaterAreaMax;
	public float roadClassMin;
	public float roadClassMax;
	
	//public float wasteWaterLimit; -> not in use yet (12.12.2013)
	//public float pipeTypeLimit; <-> ??
	//public boolean isInspected;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(diameterMin);
		result = prime * result + Float.floatToIntBits(diameterMax);
		result = prime * result + Float.floatToIntBits(floorAreaMin);
		result = prime * result + Float.floatToIntBits(floorAreaMax);
		result = prime * result + Float.floatToIntBits(beachDistanceMin);
		result = prime * result + Float.floatToIntBits(beachDistanceMax);
		result = prime * result + Float.floatToIntBits(blockageMin);
		result = prime * result + Float.floatToIntBits(blockageMax);
		result = prime * result + Float.floatToIntBits(flushingMin);
		result = prime * result + Float.floatToIntBits(flushingMax);
		result = prime * result + Float.floatToIntBits(extraWaterMin);
		result = prime * result + Float.floatToIntBits(extraWaterMax);
		result = prime * result + Float.floatToIntBits(cctvMin);
		result = prime * result + Float.floatToIntBits(cctvMax);
		result = prime * result + Float.floatToIntBits(cctv34Min);
		result = prime * result + Float.floatToIntBits(cctv34Max);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeterLimitVal other = (MeterLimitVal) obj;
		if (Float.floatToIntBits(diameterMin) != Float
				.floatToIntBits(other.diameterMin))
			return false;
		if (Float.floatToIntBits(diameterMax) != Float
				.floatToIntBits(other.diameterMax))
			return false;
		if (Float.floatToIntBits(floorAreaMin) != Float
				.floatToIntBits(other.floorAreaMin))
			return false;
		if (Float.floatToIntBits(floorAreaMax) != Float
				.floatToIntBits(other.floorAreaMax))
			return false;		
		if (Float.floatToIntBits(beachDistanceMin) != Float
				.floatToIntBits(other.beachDistanceMin))
			return false;
		if (Float.floatToIntBits(beachDistanceMax) != Float
				.floatToIntBits(other.beachDistanceMax))
			return false;
		if (Float.floatToIntBits(blockageMin) != Float
				.floatToIntBits(other.blockageMin))
			return false;
		if (Float.floatToIntBits(blockageMax) != Float
				.floatToIntBits(other.blockageMax))
			return false;
		if (Float.floatToIntBits(flushingMin) != Float
				.floatToIntBits(other.flushingMin))
			return false;
		if (Float.floatToIntBits(flushingMax) != Float
				.floatToIntBits(other.flushingMax))
			return false;
		if (Float.floatToIntBits(extraWaterMin) != Float
				.floatToIntBits(other.extraWaterMin))
			return false;
		if (Float.floatToIntBits(extraWaterMax) != Float
				.floatToIntBits(other.extraWaterMax))
			return false;
		if (Float.floatToIntBits(cctvMin) != Float
				.floatToIntBits(other.cctvMin))
			return false;
		if (Float.floatToIntBits(cctvMax) != Float
				.floatToIntBits(other.cctvMax))
			return false;
		if (Float.floatToIntBits(cctv34Min) != Float
				.floatToIntBits(other.cctv34Min))
			return false;
		if (Float.floatToIntBits(cctv34Max) != Float
				.floatToIntBits(other.cctv34Max))
			return false;			
		return true;
	}	
	
}
