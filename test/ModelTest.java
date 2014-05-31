import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelWrappers.ScadaHrWrapper;
import modelWrappers.ScadaWrapper;
import models.Component;
import models.ComponentDetail;
import models.ComponentProperty;
import models.ComponentType;
import models.PropertyType;
import models.PsScadaHourly;
import models.PumpScadaHourly;
import models.enums.PropertyDataType;

import org.junit.Test;

import play.db.jpa.JPA;
import service.MeterService;
import util.DateUtilSphinx;
import util.ReadCSV;
import util.ReadDBF;
import util.ReadXLS;

public class ModelTest {

	// @Test
	// public void readNISTest() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	// ReadXLS.readNISTest();
	// }
	// });
	// }
	// });
	// }

	// private String formatted(Date date) {
	// return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
	// }

	// @Test
	// public void findById() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	// Component macintosh = Component.findById(21l);
	// assertThat(macintosh.name).isEqualTo("Macintosh");
	// assertThat(formatted(macintosh.introduced)).isEqualTo("1984-01-24");
	// }
	// });
	// }
	// });
	// }

	// @Test
	// public void addComponentType() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	//
	// ComponentType componentType = new ComponentType();
	// componentType.description = "This is a pumping station component type";
	// componentType.owner = "furkan";
	// componentType.type_name = "Pumping Station";
	// componentType.save();
	//
	// ComponentType componentType2 = new ComponentType();
	// componentType2.description = "This is a pipe component type";
	// componentType2.owner = "furkan";
	// componentType2.type_name = "Pipe";
	// componentType2.save();
	//
	// ComponentType componentType3 = new ComponentType();
	// componentType3.description = "This is a pump component type";
	// componentType3.owner = "furkan";
	// componentType3.type_name = "Pump";
	// componentType3.save();
	//
	//
	// }
	// });
	// }
	// });
	// }

	// @Test
	// public void CalculateOperationalDisturbance() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	//
	// // add operational disturbance meter property type and
	// // component properties for the ps areas having the
	// // scope=true
	//
	// List<Component> comList = Component.componentList(1L)
	// .getList();
	// for (Component comp : comList) {
	// ComponentDetail cd = new ComponentDetail();
	// cd.operationalDisturbance = MeterService
	// .getOperationalDisturbance(comp.id);
	// comp.componentDetail = cd;
	// comp.update(comp.id);
	//
	// System.out
	// .println(" ---comp.name "
	// + comp.name
	// + " ---comp.id "
	// + comp.id
	// + " ---comp.componentDetail.operationalDisturbance "
	// + comp.componentDetail.operationalDisturbance
	// );
	// }
	// }
	// });
	// }
	// });
	// }

	// @Test
	// public void CalculateExtraWater() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	//
	// // add operational disturbance meter property type and
	// // component properties for the ps areas having the
	// // scope=true
	//
	// List<Component> comList = Component.componentList(1L)
	// .getList();
	// for (Component comp : comList) {
	// comp.componentDetail.extraWaterMeter = MeterService.extraWater(comp.id);
	// comp.update(comp.id);
	//
	// System.out
	// .println(" ---comp.name "
	// + comp.name
	// + " ---comp.id "
	// + comp.id
	// + " ---comp.componentDetail.operationalDisturbance "
	// + comp.componentDetail.operationalDisturbance
	// + " ---comp.componentDetail.extraWaterMeter "
	// + comp.componentDetail.extraWaterMeter
	// );
	// }
	// }
	// });
	// }
	// });
	// }

	// @Test
	// public void CalculateCCTVCondition() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	//
	// // add operational disturbance meter property type and
	// // component properties for the ps areas having the
	// // scope=true
	//
	// List<Component> comList = Component.componentList(1L)
	// .getList();
	// for (Component comp : comList) {
	// comp.componentDetail.CCTVConditionMeter =
	// MeterService.getStructuralCondition(comp.id);
	// comp.update(comp.id);
	//
	// System.out
	// .println(" ---comp.name "
	// + comp.name
	// + " ---comp.id "
	// + comp.id
	// + " ---comp.componentDetail.operationalDisturbance "
	// + comp.componentDetail.operationalDisturbance
	// + " ---comp.componentDetail.extraWaterMeter "
	// + comp.componentDetail.extraWaterMeter
	// + " ---comp.componentDetail.CCTVConditionMeter "
	// + comp.componentDetail.CCTVConditionMeter
	// );
	// }
	// }
	// });
	// }
	// });
	// }

	// @Test
	// public void CalculateSocialSensitivity() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	//
	// // add operational disturbance meter property type and
	// // component properties for the ps areas having the
	// // scope=true
	//
	// List<Component> comList = Component.componentList(1L)
	// .getList();
	// for (Component comp : comList) {
	// comp.componentDetail.socialSensitivity =
	// MeterService.getSocialSensitivity(comp.id);
	// comp.update(comp.id);
	//
	// System.out
	// .println(" ---comp.name "
	// + comp.name
	// + " ---comp.id "
	// + comp.id
	// + " ---comp.componentDetail.operationalDisturbance "
	// + comp.componentDetail.operationalDisturbance
	// + " ---comp.componentDetail.extraWaterMeter "
	// + comp.componentDetail.extraWaterMeter
	// + " ---comp.componentDetail.CCTVConditionMeter "
	// + comp.componentDetail.CCTVConditionMeter
	// + " ---comp.componentDetail.socialSensitivity "
	// + comp.componentDetail.socialSensitivity
	// );
	// }
	// }
	// });
	// }
	// });
	// }

	// @Test
	// public void CalculateExtraWater() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	//
	// //add social sensitivity meter property type and component properties for
	// the ps areas having the scope=true
	//
	// // PropertyType pt = new PropertyType();
	// // pt.componentType = ComponentType.findById(1L);
	// // pt.date = DateUtilSphinx.convertStringToDate("2012-12-10 17:00:00");
	// // pt.name = "Social Sensitivity Meter";
	// // pt.propertyDataType = PropertyDataType.NUMERICAL;
	// // pt = pt.save();
	// // List<Component> comList = Component.componentList(1L).getList();
	// // for (Component comp : comList) {
	// // ComponentProperty cp = new ComponentProperty();
	// // cp.component = comp;
	// // cp.propertyType = pt;
	// // cp.import_date =
	// DateUtilSphinx.convertStringToDate("2012-12-10 17:00:00");
	// // cp.valueDecimal = MeterService.getSocialSensitivity(comp.id);
	// // cp.save();
	// // System.out.println("Social Sensitivity Meter for " + comp.name + " : "
	// + MeterService.getSocialSensitivity(comp.id));
	// // }
	//
	// //add extra water meter property type and component properties for the ps
	// areas having the scope=true
	//
	// // PropertyType pt = new PropertyType();
	// // pt.componentType = ComponentType.findById(1L);
	// // pt.date = DateUtilSphinx.convertStringToDate("2012-12-10 16:00:00");
	// // pt.name = "Extra Water Meter";
	// // pt.propertyDataType = PropertyDataType.NUMERICAL;
	// // pt = pt.save();
	// // List<Component> comList = Component.componentList(1L).getList();
	// // for (Component comp : comList) {
	// // Double val = MeterService.getStructuralCondition(comp.id);
	// // ComponentProperty cp = new ComponentProperty();
	// // cp.component = comp;
	// // cp.propertyType = pt;
	// // cp.import_date =
	// DateUtilSphinx.convertStringToDate("2012-12-10 16:05:00");
	// // cp.valueDecimal = MeterService.extraWater(comp.id);
	// // cp.save();
	// // System.out.println("Extra Water Meter for " + comp.name + " : " +
	// MeterService.extraWater(comp.id));
	// // }
	//
	//
	// //add CCTV condition meter property type and component properties for the
	// ps areas having the scope=true
	//
	// // PropertyType pt = new PropertyType();
	// // pt.componentType = ComponentType.findById(1L);
	// // pt.date = DateUtilSphinx.convertStringToDate("2012-12-10 16:00:00");
	// // pt.name = "CCTV Condition";
	// // pt.propertyDataType = PropertyDataType.NUMERICAL;
	// // pt = pt.save();
	// // List<Component> comList = Component.componentList(1L).getList();
	// // for (Component comp : comList) {
	// // Double val = MeterService.getStructuralCondition(comp.id);
	// // ComponentProperty cp = new ComponentProperty();
	// // cp.component = comp;
	// // cp.propertyType = pt;
	// // cp.import_date =
	// DateUtilSphinx.convertStringToDate("2012-12-10 16:00:00");
	// // cp.valueDecimal = MeterService.getStructuralCondition(comp.id);
	// // cp.save();
	// // System.out.println("StructuralCondition for JVP" + comp.id + " : " +
	// MeterService.getStructuralCondition(comp.id));
	// // }
	//
	// }
	// });
	// }
	// });
	// }

	// @Test
	// public void addPSHourlyData() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	//
	// ScadaHrWrapper scadaHrWrapper = ReadCSV
	// .getScadaHourly();
	//
	// for (String key : scadaHrWrapper.pumpSHMap.keySet()) {
	// PumpScadaHourly psh = scadaHrWrapper.pumpSHMap
	// .get(key);
	// psh.save();
	// }
	//
	// for (String key : scadaHrWrapper.psSHMap.keySet()) {
	// PsScadaHourly psh = scadaHrWrapper.psSHMap.get(key);
	// psh.save();
	// }
	// }
	// });
	// }
	// });
	// }

//	 @Test
	public void addComponentList() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				JPA.withTransaction(new play.libs.F.Callback0() {
					public void invoke() {
//						Collection<Component> list = ReadCSV.getComponents();
//						for (Component com : list) {
//							com.save();
//						}
//						ScadaHrWrapper scadaHrWrapper = ReadCSV
//								.getScadaHourly();
//
//						Map<String, ScadaWrapper> wrapperMap = new HashMap<String, ScadaWrapper>();
//
//						for (String key : scadaHrWrapper.pumpSHMap.keySet()) {
//							PumpScadaHourly psh = scadaHrWrapper.pumpSHMap
//									.get(key);
//							psh.save();
//							System.out.println(psh.component.name + " | "
//									+ psh.pumpName + " | " + psh.pumped_flow
//									+ " | " + psh.average_current + " | "
//									+ psh.yield + " | "
//									+ psh.pumping_duration_alone + " | "
//									+ psh.timestamp);
//						}
//
//						for (String key : scadaHrWrapper.psSHMap.keySet()) {
//							PsScadaHourly psh = scadaHrWrapper.psSHMap.get(key);
//							psh.save();
//							System.out.println(psh.component.name + " | "
//									+ psh.timestamp + " | " + psh.FI_01 + " | "
//									+ psh.EQ_01 + " | " + psh.FI_02 + " | "
//									+ psh.YHTK + " | "
//									+ psh.pumpung_duration_2p + " | "
//									+ psh.pumpung_duration_3p + " | "
//									+ psh.pumpung_duration_4p + " | "
//									+ psh.PINT);
//						}
					}
				});
			}
		});
	}

//	@Test
//	public void addNIS() {
//		running(fakeApplication(), new Runnable() {
//			public void run() {
//				JPA.withTransaction(new play.libs.F.Callback0() {
//					public void invoke() {
//
//						try {
//							ReadXLS.main(null);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		});
//	}

	// @Test
	// public void addFlushingEventsNIS() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	//
	// try {
	// ReadXLS.main(null);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// });
	// }
	// });
	// }

//	@Test
//	public void addPipeRoadClassifications() {
//		running(fakeApplication(), new Runnable() {
//			public void run() {
//				JPA.withTransaction(new play.libs.F.Callback0() {
//					public void invoke() {
//
//						try {
//							ReadXLS test = new ReadXLS();
//							test.savePipeClassification(null, 4);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		});
//	}

	// @Test
	// public void addGroundWaterAreas() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	// ReadDBF test = new ReadDBF();
	// test.readGroundWaterAreas();
	// }
	// });
	// }
	// });
	// }

	// @Test
	// public void addBuildingAreas() {
	// running(fakeApplication(), new Runnable() {
	// public void run() {
	// JPA.withTransaction(new play.libs.F.Callback0() {
	// public void invoke() {
	// ReadDBF test = new ReadDBF();
	// test.readBuildingAreas();
	// }
	// });
	// }
	// });
	// }

//	@Test
//	public void addPipeBuildingArea() {
//		running(fakeApplication(), new Runnable() {
//			public void run() {
//				JPA.withTransaction(new play.libs.F.Callback0() {
//					public void invoke() {
//
//						try {
//							ReadXLS test = new ReadXLS();
//							test.savePipeGroundWaterArea(null, 19L);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		});
//	}
	
//	@Test
//	public void readneighbourghoodNoAndBuildingAreas() {
//		running(fakeApplication(), new Runnable() {
//			public void run() {
//				JPA.withTransaction(new play.libs.F.Callback0() {
//					public void invoke() {
//
//						try {
//							ReadXLS test = new ReadXLS();
//							test.readNeighbourghoodNoAndBuildingAreas();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		});
//	}
	
//	@Test
//	public void addSquareMeterOfBuildingAreas() {
//		running(fakeApplication(), new Runnable() {
//			public void run() {
//				JPA.withTransaction(new play.libs.F.Callback0() {
//					public void invoke() {
//
//						try {
//							ReadXLS test = new ReadXLS();
//							test.saveBuildingAreaSquareMeters(test.readNeighbourghoodNoAndBuildingAreas());
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		});
//	}
	
//	@Test
//	public void addBuildingAreaToEachPipe() {
//		running(fakeApplication(), new Runnable() {
//			public void run() {
//				JPA.withTransaction(new play.libs.F.Callback0() {
//					public void invoke() {
//
//						try {
//							ReadXLS test = new ReadXLS();
//							test.addBuildingAreaToEachPipe();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		});
//	}
}
