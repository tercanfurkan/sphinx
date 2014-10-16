package models;

import java.util.ArrayList;
import java.util.List;
import models.Component;
import java.lang.String;

public class DataLoader {
	
	public DistributionBar distBar;
	private int distributionFirst;
	private int allowLoading;
	
	public DataLoader() {
		distributionFirst = 0;
		allowLoading = 0;
	}
	
	public void SetAllowLoading() {
		System.out.println("DataLoader SetAllowLoading > ");
		allowLoading = 1;
	}

	public void SetDisAllowLoading() {
		allowLoading = 0;
	}
	
	public void debugdebug(String text) {
		System.out.println(text + " > ");
	}
	
	public List<Double> loadDiameterData() {
		List<Double> diameterLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadDiameterData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadDiameterData > ");
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(0, 101));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(101, 201));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(201, 301));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(301, 401));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(401, 501));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(501, 601));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(601, 701));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(701, 801));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(801, 901));
			diameterLengthArray.add(Component.getPipeLenghtsAccordingToDiameter(901, 1001));
		}
		return diameterLengthArray;
	}
	
	public List<Double> loadAreaLengthData() {
		List<Double> areaLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadAreaLengthData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadAreaLengthData > ");
			areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(0, 11));
			areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(11, 21));
			areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(21, 31));
			areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(31, 41));
			areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(41, 51));
		}
		return areaLengthArray;
	}

	public List<Double> loadGroundWaterLengthData() {	
		List<Double> groundWaterLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadGroundWaterLengthData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadGroundWaterLengthData > ");
			groundWaterLengthArray.add(Component.getPipeLengthsAccordingToGroundWaterArea(0, 1));
			groundWaterLengthArray.add(Component.getPipeLengthsAccordingToGroundWaterArea(1, 2));
			groundWaterLengthArray.add(Component.getPipeLengthsAccordingToGroundWaterArea(2, 3));
			groundWaterLengthArray.add(Component.getPipeLengthsAccordingToGroundWaterArea(3, 4));	
		}		
		return groundWaterLengthArray;
	}

	public List<Double> loadRoadClassLengthData() {
		List<Double> roadclassLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadRoadClassLengthData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadRoadClassLengthData > ");
			roadclassLengthArray.add(Component.getPipeLengthsAccordingToRoadClassification(1, 2));
			roadclassLengthArray.add(Component.getPipeLengthsAccordingToRoadClassification(2, 3));
			roadclassLengthArray.add(Component.getPipeLengthsAccordingToRoadClassification(3, 4));
			roadclassLengthArray.add(Component.getPipeLengthsAccordingToRoadClassification(4, 5));
		}
		return roadclassLengthArray;
	}	

	public List<Double> loadBlockageLengthData() {
		List<Double> blockageLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadBlockageLengthData > ");
		if (allowLoading > 0) {		
			System.out.println("DataLoader IN loadBlockageLengthData > ");
			blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(0, 1));
			blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(1, 2));
			blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(2, 3));
			blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(3, 4));
			blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(4, 5));
		}
		return blockageLengthArray;
	}

	public List<Double> loadFlushingEventLengthData() {	
		List<Double> flushingEventLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadFlushingEventLengthData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadFlushingEventLengthData > ");
			flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(0, 4));
			flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(4, 8));
			flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(8, 12));
			flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(12, 16));
			flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(16, 20));
		}
		return flushingEventLengthArray;
	}	

	public List<Double> loadExtraWaterLengthData() {
		List<Double> extraWaterLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadExtraWaterLengthData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadExtraWaterLengthData > ");		
			extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(0, 4));
			extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(4, 8));
			extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(8, 12));
			extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(12, 16));
			extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(16, 20));
		}
		return extraWaterLengthArray;
	}	

	public List<Double> loadCctvDefectsLengthData() {
		List<Double> cctvDefectsLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadCctvDefectsLengthData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadCctvDefectsLengthData > ");		
			cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(0, 6));
			cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(5, 11));
			cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(11, 16));
			cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(16, 21));
			cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(21, 26));
			cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(26, 31));
			cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(31, 36));
		}
		return cctvDefectsLengthArray;
	}

	public List<Double> loadCctvMajorDefectsLengthData() {	
		List<Double> cctvMajorDefectsLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadCctvMajorDefectsLengthData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadCctvMajorDefectsLengthData > ");		
			cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(0, 6));
			cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(5, 11));
			cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(11, 16));
			cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(16, 21));
			cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(21, 26));
			cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(26, 31));
		}
		return cctvMajorDefectsLengthArray;
	}

	public List<Double> loadUndoubledPipeLengthData() {
		List<Double> undoubledPipeLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadUndoubledPipeLengthData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadUndoubledPipeLengthData calling getMeterDistributions > ");	
			if (distributionFirst == 0) {
				distributionFirst++;
				this.distBar = DistributionBar.getMeterDistributions();
			}
			undoubledPipeLengthArray.add(distBar.undoubled);
			undoubledPipeLengthArray.add(distBar.notUndoubled);
		}
		return undoubledPipeLengthArray;
	}

	public List<Double> loadPipesCloseToWaterworkData() {
		List<Double> array = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadPipesCloseToWaterworkData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadPipesCloseToWaterworkData calling getMeterDistributions > ");	
			if (distributionFirst == 0) {
				distributionFirst++;
				this.distBar = DistributionBar.getMeterDistributions();
			}	
			array.add(distBar.closeToWaterwork);
			array.add(distBar.notCloseToWaterwork);
		}	
		return array;
	}

	public List<Double> loadPipesCloseToNuuksioLakeData() {
		List<Double> array = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadPipesCloseToNuuksioLakeData > ");
		if (allowLoading > 0) {		
			System.out.println("DataLoader IN loadPipesCloseToNuuksioLakeData calling getMeterDistributions > ");
			if (distributionFirst == 0) {
				distributionFirst++;
				this.distBar = DistributionBar.getMeterDistributions();
			}	
			array.add(distBar.closeToNuuksioLake);
			array.add(distBar.notCloseToNuuksioLake);
		}
		return array;
	}

	public List<Double> loadPipesUnderRailwayData() {
		List<Double> array = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadPipesUnderRailwayData > ");
		if (allowLoading > 0) {			
			System.out.println("DataLoader IN loadPipesUnderRailwayData calling getMeterDistributions > ");
			if (distributionFirst == 0) {
				distributionFirst++;
				this.distBar = DistributionBar.getMeterDistributions();
			}	
			array.add(distBar.underRailway);
			array.add(distBar.notUnderRailway);
		}
		return array;
	}

	public List<Double> loadPipesInProtectedAreaData() {
		List<Double> array = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadPipesInProtectedAreaData > ");
		if (allowLoading > 0) {	
			System.out.println("DataLoader IN loadPipesInProtectedAreaData calling getMeterDistributions > ");
			if (distributionFirst == 0) {
				distributionFirst++;
				this.distBar = DistributionBar.getMeterDistributions();
			}	
			array.add(distBar.inProtectedArea);
			array.add(distBar.notInProtectedArea);
		}
		return array;
	}

	public List<Double> loadPipesUnderWaterBodyData() {
		List<Double> array = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadPipesUnderWaterBodyData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadPipesUnderWaterBodyData calling getMeterDistributions > ");
			if (distributionFirst == 0) {
				distributionFirst++;
				this.distBar = DistributionBar.getMeterDistributions();
			}	
			array.add(distBar.underWaterbody);
			array.add(distBar.notUnderWaterbody);
		}
		return array;
	}

	public List<Double> loadPipesUnderProtectedDitchData() {
		List<Double> undoubledPipeLengthArray = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadPipesUnderProtectedDitchData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadPipesUnderProtectedDitchData calling getMeterDistributions > ");
			if (distributionFirst == 0) {
				distributionFirst++;
				this.distBar = DistributionBar.getMeterDistributions();
			}	
			undoubledPipeLengthArray.add(distBar.underProtectedDitch);
			undoubledPipeLengthArray.add(distBar.notUnderProtectedDitch);
		}
		return undoubledPipeLengthArray;
	}

	public List<Double> loadPipesOperationalTypeData() {
		List<Double> array = new ArrayList<Double>();
		System.out.println("DataLoader OUT loadPipesOperationalTypeData > ");
		if (allowLoading > 0) {
			System.out.println("DataLoader IN loadPipesOperationalTypeData calling getMeterDistributions > ");
			if (distributionFirst == 0) {
				distributionFirst++;
				this.distBar = DistributionBar.getMeterDistributions();
			}	
			array.add(distBar.kera);
			array.add(distBar.paavi);
			array.add(distBar.otheroperationaltype);
		}
		return array;
	}
}	