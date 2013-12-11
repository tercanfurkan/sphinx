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
}
