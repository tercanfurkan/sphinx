package models.form;

public class MeterLimitVal {

	public MeterLimitVal() {
		// Define min/max values for meters using the slider bar
		this.diameterMin = 333;
		this.diameterMax = 686;
		this.floorAreaMin = 16;
		this.floorAreaMax = 33;
		this.beachDistanceMin = 66;
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
		
		this.gwaImportantVal = 2;
		this.gwaSuitableVal = 1;
		this.gwaOtherVal = 0;
		
		this.pipeTypeLateralVal = 0;
		this.pipeTypeCollectionVal = 0;
		this.pipeTypeGravetyVal = 1; 
		this.pipeTypePressure = 2; 
		//how is small pressure pipe identified for instance: pipeDiameter < x meter ?
		this.pipeTypeSmallPressureVal = 1;
		
		this.roadRegionalMain1Val = 2;
		this.roadRegionalMain2Val = 1;
		this.roadLocalMainVal = 1;
		this.roadCollectorVal = 0;
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
	public float gwaImportantVal;
	public float gwaSuitableVal;
	public float gwaOtherVal;
	
	public float pipeTypeLateralVal;
	public float pipeTypeCollectionVal;
	public float pipeTypeGravetyVal; 
	public float pipeTypePressure; 
	//how is small pressure pipe identified for instance: pipeDiameter < x meter ?
	public float pipeTypeSmallPressureVal;
	
	public float roadRegionalMain1Val;
	public float roadRegionalMain2Val;
	public float roadLocalMainVal;
	public float roadCollectorVal;
}
