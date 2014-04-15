package models.form;

public class MeterLimitVal {

	public MeterLimitVal() {
		// Define min/max values for meters using the slider bar
		this.agree = false;
		this.diameterMin = 400;
		this.diameterMax = 600;
		this.floorAreaMin = 15;  
		this.floorAreaMax = 35;
		this.beachDistanceMin = 100;
		this.beachDistanceMax = 200;
		this.blockageMin = 1;
		this.blockageMax = 3;
		this.flushingMin = 4;
		this.flushingMax = 8;
		this.extraWaterMin = 8;
		this.extraWaterMax = 12;
		this.cctvMin = 8;
		this.cctvMax = 11;
		this.cctv34Min = 6;
		this.cctv34Max = 9;
		
		this.gwaImportantVal = 2;
		this.gwaSuitableVal = 2;
		this.gwaOtherVal = 2;
		
		this.pipeTypeLateralVal = 1;
		this.pipeTypeCollectionVal = 1;
		this.pipeTypeGravetyVal = 2; 
		this.pipeTypePressure = 2; 
		//how is small pressure pipe identified for instance: pipeDiameter < x meter ?
		this.pipeTypeSmallPressureVal = 1;
		
		this.roadRegionalMain1Val = 2;
		this.roadRegionalMain2Val = 1;
		this.roadLocalMainVal = 1;
		this.roadCollectorVal = 1;
	}

	// Slider min max values
	public boolean agree;
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