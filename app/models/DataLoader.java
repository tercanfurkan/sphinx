package models;

import java.util.ArrayList;
import java.util.List;
import models.Component;


public class DataLoader {

	//@Transactional(readOnly = true)
	public static List<Double> loadDiameterData() {
		List<Double> diameterLengthArray = new ArrayList<Double>();
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
		return diameterLengthArray;
	}
	
	public static List<Double> loadAreaLengthData() {
		List<Double> areaLengthArray = new ArrayList<Double>();
		areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(0, 11));
		areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(11, 21));
		areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(21, 31));
		areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(31, 41));
		areaLengthArray.add(Component.getPipeLenghtsAccordingToRelativeFloorAreaMeters(41, 51));
		return areaLengthArray;
	}

	public static List<Double> loadGroundWaterLengthData() {	
		List<Double> groundWaterLengthArray = new ArrayList<Double>();
		groundWaterLengthArray.add(Component.getPipeLengthsAccordingToGroundWaterArea(0, 1));
		groundWaterLengthArray.add(Component.getPipeLengthsAccordingToGroundWaterArea(1, 2));
		groundWaterLengthArray.add(Component.getPipeLengthsAccordingToGroundWaterArea(2, 3));
		groundWaterLengthArray.add(Component.getPipeLengthsAccordingToGroundWaterArea(3, 4));		
		return groundWaterLengthArray;
	}

	public static List<Double> loadRoadClassLengthData() {
		List<Double> roadclassLengthArray = new ArrayList<Double>();
		roadclassLengthArray.add(Component.getPipeLengthsAccordingToRoadClassification(1, 2));
		roadclassLengthArray.add(Component.getPipeLengthsAccordingToRoadClassification(2, 3));
		roadclassLengthArray.add(Component.getPipeLengthsAccordingToRoadClassification(3, 4));
		roadclassLengthArray.add(Component.getPipeLengthsAccordingToRoadClassification(4, 5));
		return roadclassLengthArray;
	}	

	public static List<Double> loadBlockageLengthData() {	
		List<Double> blockageLengthArray = new ArrayList<Double>();
		blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(0, 1));
		blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(1, 2));
		blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(2, 3));
		blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(3, 4));
		blockageLengthArray.add(Component.getPipeLengthsAccordingToBlockages(4, 5));
		return blockageLengthArray;
	}

	public static List<Double> loadFlushingEventLengthData() {	
		List<Double> flushingEventLengthArray = new ArrayList<Double>();
		flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(0, 4));
		flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(4, 8));
		flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(8, 12));
		flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(12, 16));
		flushingEventLengthArray.add(Component.getPipeLengthsAccordingToFlushingEvents(16, 20));	
		return flushingEventLengthArray;
	}	

	public static List<Double> loadExtraWaterLengthData() {	
		List<Double> extraWaterLengthArray = new ArrayList<Double>();
		extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(0, 4));
		extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(4, 8));
		extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(8, 12));
		extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(12, 16));
		extraWaterLengthArray.add(Component.getPipeLengthsAccordingToExtraWaterPercentage(16, 20));
		return extraWaterLengthArray;
	}	

	public static List<Double> loadCctvDefectsLengthData() {	
		List<Double> cctvDefectsLengthArray = new ArrayList<Double>();
		cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(0, 6));
		cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(5, 11));
		cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(11, 16));
		cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(16, 21));
		cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(21, 26));
		cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(26, 31));
		cctvDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVDefects(31, 36));
		return cctvDefectsLengthArray;
	}

	public static List<Double> loadCctvMajorDefectsLengthData() {	
		List<Double> cctvMajorDefectsLengthArray = new ArrayList<Double>();
		cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(0, 6));
		cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(5, 11));
		cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(11, 16));
		cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(16, 21));
		cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(21, 26));
		cctvMajorDefectsLengthArray.add(Component.getPipeLengthsAccordingToCCTVMajorDefects(26, 31));		
		return cctvMajorDefectsLengthArray;
	}

	public List<Double> loadUndoubledPipeLengthData() {
		DistributionBar dist = DistributionBar.getMeterDistributions();
		List<Double> undoubledPipeLengthArray = new ArrayList<Double>();
		System.out.println(dist.undoubled);
		System.out.println(dist.notUndoubled);
		undoubledPipeLengthArray.add(dist.undoubled);
		undoubledPipeLengthArray.add(dist.notUndoubled);
		return undoubledPipeLengthArray;
	}
}	