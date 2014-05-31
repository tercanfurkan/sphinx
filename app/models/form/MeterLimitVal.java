package models.form;

public class MeterLimitVal {

	public MeterLimitVal() {

		this.isPipeDiameterImportant = 0;
		this.isFloorAreaImportant = 0;
		
		this.isGwaImportant = 0;
		
		this.isImportantGwaImportant = false;
		this.isSuitableGwaImportant = false;
		this.isOtherGwaImportant = false;
		
		this.isUndoubledPipeImportant = false;
		
		this.isSewerTypeImportant = 0;
		
		this.isPipeTypeCollectionImportant = false;
		this.isPipeTypeGravityImportant = false;
		this.isPipeTypePressureImportant = false;
		this.isPipeTypeSmallPressureImportant = false;	
		
		this.isRoadTypeImportant = 0;
		
		this.isRoadRegionalMain1Important = false;
		this.isRoadRegionalMain2Important = false;
		this.isRoadLocalMainImportant = false;
		this.isRoadCollectorImportant = false;
		
		this.isBlockageImportant = 0;
		this.isFlushingImportant = 0;	
		this.isInflowAndInfiltrationImportant = 0;
		this.isCCTVDefectsImportant = 0;
		this.isCCTVMajorDefectsImportant = 0;	
	
		// Define min/max values for meters using the slider bar
		this.diameterMin = 400;
		this.diameterMax = 600;
		this.floorAreaMin = 15;  
		this.floorAreaMax = 35;
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
		
		this.undoubledPipeVal = 3;
		
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
		
		this.amountOfConsequenceRed = 0;
		this.amountOfConditionRed = 0;
		this.amountOfConsequenceYellow = 0;
		this.amountOfConditionYellow = 0;		
	}

	// Criteria importance
	public float isPipeDiameterImportant;
	public float isFloorAreaImportant;
	
	public float isGwaImportant;
	
	public boolean isImportantGwaImportant;
	public boolean isSuitableGwaImportant;
	public boolean isOtherGwaImportant;
	
	public boolean isUndoubledPipeImportant;
	
	public float isSewerTypeImportant;
	
	public boolean isPipeTypeCollectionImportant;
	public boolean isPipeTypeGravityImportant;
	public boolean isPipeTypePressureImportant;
	public boolean isPipeTypeSmallPressureImportant;	
	
	public float isRoadTypeImportant;
	
	public boolean isRoadRegionalMain1Important;
	public boolean isRoadRegionalMain2Important;
	public boolean isRoadLocalMainImportant;
	public boolean isRoadCollectorImportant;
	
	public float isBlockageImportant;
	public float isFlushingImportant;	
	public float isInflowAndInfiltrationImportant;
	public float isCCTVDefectsImportant;
	public float isCCTVMajorDefectsImportant;	
	
	// Slider min max values	
	public float diameterMin;
	public float diameterMax;
	public float floorAreaMin;
	public float floorAreaMax;
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
	
	public float undoubledPipeVal;
	
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

	public float amountOfConsequenceRed;
	public float amountOfConditionRed;
	public float amountOfConsequenceYellow;
	public float amountOfConditionYellow;

}