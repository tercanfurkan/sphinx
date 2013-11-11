package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import models.meter.sensitivity.PipeSensitivityIndex;

import play.data.format.Formats;
import play.db.jpa.JPA;

@Entity
public class Component {

	@Id
	@Column(name = "id")
	@TableGenerator(name = "component_seq", table = "id_sequence", pkColumnName = "id_name", pkColumnValue = "component_seq_nextval", valueColumnName = "id_value", initialValue = 1, allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "component_seq")
	public Long id;

	public Long number;

	public String name;

	public String identifier;

	public String description;

	public Long datasource_code;

	public Boolean deleted;

	public Boolean scope;

	public String owner;
	
	public Integer roadClassification;
	public Float annualOverFlow; //m3
	public boolean inIndustrialRoot = false; //only for pipes at the moment. is true if pipe is in the  industrial waste water root
	public Float ofDistanceToBeach;

	@Transient
	public Boolean selected;

	@Formats.DateTime(pattern = "dd.mm.yyyy HH:mm:ss")
	public Date date;

	@Formats.DateTime(pattern = "dd.mm.yyyy HH:mm:ss")
	public Date import_date;

	@OneToOne(cascade = CascadeType.ALL)
	public ComponentDetail componentDetail = new ComponentDetail();;

	@OneToMany(mappedBy = "parent_component")
	public Set<Component> childComponentSet = new HashSet<Component>(0);

	@ManyToOne(optional = true)
	@JoinTable(name = "JT_COMPONENT_PARENT_CHILD", joinColumns = @JoinColumn(name = "child_id"), inverseJoinColumns = @JoinColumn(name = "parent_id"))
	public Component parent_component;

	@OneToMany(mappedBy = "ownerComponent")
	public Set<Component> ownedComponentSet = new HashSet<Component>(0);

	@ManyToOne(optional = true)
	@JoinTable(name = "JT_COMPONENT_OWNER_COMPONENT", joinColumns = @JoinColumn(name = "component_id"), inverseJoinColumns = @JoinColumn(name = "owner_component_id"))
	public Component ownerComponent;

	@ManyToOne(cascade = CascadeType.MERGE)
	public ComponentType component_type;

	@ManyToOne(cascade = CascadeType.MERGE)
	public Datasource datasource;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	public GroundWaterArea groundWaterArea;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	public BuildingArea buildingArea;
	
	@OneToMany(mappedBy = "component")
	public List<PsScadaHourly> psScadaHourlyList = new ArrayList<PsScadaHourly>();
	
	@OneToMany(mappedBy = "component")
	public List<ComponentProperty> componentPropertyList = new ArrayList<ComponentProperty>();

	public static ComponentDetail getComponentDetail(Long componentId) {
		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c.componentDetail FROM Component c WHERE c.id = :cId");
			query.setParameter("cId", componentId);
			return (ComponentDetail) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Find a company by id.
	 */
	public static Component findById(Long id) {
		return JPA.em().find(Component.class, id);
	}

	public static Component findByName(String name) {
		try {
			return (Component) JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.name LIKE :cName")
					.setParameter("cName", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public static Component findPSComponent(String psComponentName) {
		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.name = :cName and c.component_type.id = 1");
			query.setParameter("cName", psComponentName);
			return (Component) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public static List<Component> findByComponentType(Long componentTypeId) {
		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.component_type.id =:cComponentType order by c.number");
			query.setParameter("cComponentType", componentTypeId);
			return (List<Component>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Component findByNameAndComponentType(String name,
			ComponentType componentType) {
		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.name LIKE :cName and c.component_type =:cComponentType");
			query.setParameter("cName", name);
			query.setParameter("cComponentType", componentType);
			return (Component) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Component findByNameAndComponentType(String name,
			Long componentTypeId) {
		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.name LIKE :cName and c.component_type.id =:cComponentType");
			query.setParameter("cName", name);
			query.setParameter("cComponentType", componentTypeId);
			return (Component) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Component findByNameComponentTypeAndDate(String name,
			ComponentType componentType, Date date) {
		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.name LIKE :cName and c.component_type =:cComponentType and c.date =:cDate");
			query.setParameter("cName", name);
			query.setParameter("cComponentType", componentType);
			query.setParameter("cDate", date);
			return (Component) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Component findByNumberComponentTypeAndDate(Long number,
			ComponentType componentType, Date date) {
		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.number =:cNumber and c.component_type =:cComponentType and c.date =:cDate");
			query.setParameter("cNumber", number);
			query.setParameter("cComponentType", componentType);
			query.setParameter("cDate", date);
			return (Component) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Component findByNumber(Long number) {
		try {
			Query query = JPA.em().createQuery(
					"SELECT c FROM Component c WHERE c.number =:cNumber");
			query.setParameter("cNumber", number);
			return (Component) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static Component findByDatasourceCode(Long datasourceCode) {
		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.datasource_code =:cCode");
			query.setParameter("cCode", datasourceCode);
			return (Component) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION IN findByDatasourceCode");
			System.out.println("STACK TRACE" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Update this computer.
	 */
	public void update(Long id) {
		if (this.component_type.id == null) {
			this.component_type = null;
		} else {
			this.component_type = ComponentType.findById(component_type.id);
		}
		this.id = id;
		JPA.em().merge(this);
	}

	/**
	 * Insert this new computer.
	 */
	public void save() {
		if (this.component_type.id == null) {
			this.component_type = null;
		} else {
			this.component_type = ComponentType.findById(component_type.id);
		}
		JPA.em().persist(this);
	}

	public Component merge() {
		if (this.component_type.id == null) {
			this.component_type = null;
		} else {
			this.component_type = ComponentType.findById(component_type.id);
		}
		return JPA.em().merge(this);
	}

	/**
	 * Delete this computer.
	 */
	public void delete() {
		JPA.em().remove(this);
	}

	public static AreaMeterList areaAndMeterPage(int page, int pageSize,
			String sortBy, String order, String filter) {
		sortBy = "number";
		if (page < 1)
			page = 1;

		Long total = 0L;
		List<Component> data;
		// try {
		Query query = JPA
				.em()
				.createQuery(
						"select count(c) from Component c where lower(c.name) like ? and c.component_type = 1 and c.scope = ?");
		query.setParameter(1, "%" + filter.toLowerCase() + "%");
		query.setParameter(2, Boolean.TRUE);
		total = (Long) query.getSingleResult();

		Query query2 = JPA
				.em()
				.createQuery(
						"from Component c where lower(c.name) like ? and c.component_type = 1 and c.scope = ? order by "
								+ sortBy + " " + order);
		query2.setParameter(1, "%" + filter.toLowerCase() + "%");
		query2.setParameter(2, Boolean.TRUE);
		query2.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
		data = query2.getResultList();

		List<AreaAndMeters> areaAndMetersList = new ArrayList<AreaAndMeters>();
		AreaAndMeters areaAndMeters;
		// Double extraWaterMeter = 0D;
		// Double CCTVconditionMeter = 0D;
		// Double operationalDisturbanceMeter = 0D;
		// Double socialSensitivityMeter = 0D;
		for (Component component : data) {

			// extraWaterMeter = getValueDecimalForPropertyType(component.id,
			// "Extra Water Meter");
			// CCTVconditionMeter = getValueDecimalForPropertyType(component.id,
			// "CCTV Condition Meter");
			// socialSensitivityMeter =
			// getValueDecimalForPropertyType(component.id,
			// "Social Sensitivity Meter");
			areaAndMeters = new AreaAndMeters(component, 0D, 0D, 0D, 0D);
			areaAndMetersList.add(areaAndMeters);
		}
		return new AreaMeterList(areaAndMetersList, total, page, pageSize);
	}

	public static AreaMeterList areaAndMeterPageAdvancedFilter(int page,
			int pageSize, String sortBy, String order, String filter) {
		sortBy = "number";
		if (page < 1)
			page = 1;

		String[] filterList = filter.split(",");
		List<String> strFilterList = new ArrayList<String>();

		for (String str : filterList) {
			str = str.trim();
			System.out.println(str);
			strFilterList.add(str);
		}
		String queryStr = "select count(c) from Component c where c.component_type = 1 and c.scope = ?";
		String queryStrData = "from Component c where c.component_type = 1 and c.scope = ?";
		if (strFilterList.size() > 0) {
			queryStr = queryStr + " and lower(c.name) in ?";
			queryStrData = queryStr + " and lower(c.name) in ?";
		}

		Long total = 0L;
		List<Component> data;
		// try {
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter(1, Boolean.TRUE);
		if (strFilterList.size() > 0)
			query.setParameter(2, strFilterList);

		System.out.println(queryStr);
		System.out.println(query.toString());
		total = (Long) query.getSingleResult();

		Query query2 = JPA.em().createQuery(
				queryStrData + " order by " + sortBy + " " + order);
		query2.setParameter(1, Boolean.TRUE);
		if (strFilterList.size() > 0)
			query.setParameter(2, strFilterList);
		query2.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
		data = query2.getResultList();

		List<AreaAndMeters> areaAndMetersList = new ArrayList<AreaAndMeters>();
		AreaAndMeters areaAndMeters;
		// Double extraWaterMeter = 0D;
		// Double CCTVconditionMeter = 0D;
		// Double operationalDisturbanceMeter = 0D;
		// Double socialSensitivityMeter = 0D;
		for (Component component : data) {

			// extraWaterMeter = getValueDecimalForPropertyType(component.id,
			// "Extra Water Meter");
			// CCTVconditionMeter = getValueDecimalForPropertyType(component.id,
			// "CCTV Condition Meter");
			// socialSensitivityMeter =
			// getValueDecimalForPropertyType(component.id,
			// "Social Sensitivity Meter");
			areaAndMeters = new AreaAndMeters(component, 0D, 0D, 0D, 0D);
			areaAndMetersList.add(areaAndMeters);
		}
		return new AreaMeterList(areaAndMetersList, total, page, pageSize);

		// } catch (Exception e) {
		// System.out.println("ERRORRRRRRR:" +e);
		// return null;
		// }
	}

	/**
	 * Return a page of computer
	 * 
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 * @param sortBy
	 *            Computer property used for sorting
	 * @param order
	 *            Sort order (either or asc or desc)
	 * @param filter
	 *            Filter applied on the name column
	 */
	public static Page page(int page, int pageSize, String sortBy,
			String order, String filter) {
		sortBy = "number";
		if (page < 1)
			page = 1;
		Long total = (Long) JPA
				.em()
				.createQuery(
						"select count(c) from Component c where lower(c.name) like ? and c.component_type = 1")
				.setParameter(1, "%" + filter.toLowerCase() + "%")
				.getSingleResult();
		List<Component> data = JPA
				.em()
				.createQuery(
						"from Component c where lower(c.name) like ? and c.component_type = 1 order by "
								+ sortBy + " " + order)
				.setParameter(1, "%" + filter.toLowerCase() + "%")
				.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize)
				.getResultList();
		return new Page(data, total, page, pageSize);
	}

	public static Page pagePipes(int page, int pageSize, String sortBy,
			String order, String filter) {
		sortBy = "number";
		if (page < 1)
			page = 1;
		Long total = (Long) JPA
				.em()
				.createQuery(
						"select count(c) from Component c where lower(c.name) like ? and c.component_type = 2")
				.setParameter(1, "%" + filter.toLowerCase() + "%")
				.getSingleResult();
		List<Component> data = JPA
				.em()
				.createQuery(
						"from Component c where lower(c.name) like ? and c.component_type = 2 order by "
								+ sortBy + " " + order)
				.setParameter(1, "%" + filter.toLowerCase() + "%")
				.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize)
				.getResultList();
		return new Page(data, total, page, pageSize);
	}

	public static Page pageManholes(int page, int pageSize, String sortBy,
			String order, String filter) {
		sortBy = "number";
		if (page < 1)
			page = 1;
		Long total = (Long) JPA
				.em()
				.createQuery(
						"select count(c) from Component c where lower(c.name) like ? and c.component_type = 4")
				.setParameter(1, "%" + filter.toLowerCase() + "%")
				.getSingleResult();
		List<Component> data = JPA
				.em()
				.createQuery(
						"from Component c where lower(c.name) like ? and c.component_type = 4 order by "
								+ sortBy + " " + order)
				.setParameter(1, "%" + filter.toLowerCase() + "%")
				.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize)
				.getResultList();
		return new Page(data, total, page, pageSize);
	}

	public static ComponentList componentList(Long componentTypeId) {

		try {
			Query query = JPA
					.em()
					.createQuery(
							"SELECT c FROM Component c WHERE c.component_type.id =:ctId and c.scope =:cScope order by number asc");
			query.setParameter("ctId", componentTypeId);
			query.setParameter("cScope", Boolean.TRUE);
			List<Component> comList = query.getResultList();
			return new ComponentList(comList);
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Used to represent a computers page.
	 */
	public static class Page {

		private final int pageSize;
		private final long totalRowCount;
		private final int pageIndex;
		private final List<Component> list;

		public Page(List<Component> data, long total, int page, int pageSize) {
			this.list = data;
			this.totalRowCount = total;
			this.pageIndex = page;
			this.pageSize = pageSize;
		}

		public long getTotalRowCount() {
			return totalRowCount;
		}

		public int getPageIndex() {
			return pageIndex;
		}

		public List<Component> getList() {
			return list;
		}

		public boolean hasPrev() {
			return pageIndex > 1;
		}

		public boolean hasNext() {
			return (totalRowCount / pageSize) >= pageIndex;
		}

		public String getDisplayXtoYofZ() {
			int start = ((pageIndex - 1) * pageSize + 1);
			int end = start + Math.min(pageSize, list.size()) - 1;
			return start + " to " + end + " of " + totalRowCount;
		}

	}

	public static class ComponentList {

		private final List<Component> list;

		public ComponentList(List<Component> data) {
			this.list = data;
		}

		public List<Component> getList() {
			return list;
		}

	}

	public static class AreaMeterList {

		public int wExtraWater;
		public int wCCTVCon;
		public int wOperationalCon;
		public int wSocialSensitivity;
		private final int pageSize;
		private final long totalRowCount;
		private final int pageIndex;
		private List<AreaAndMeters> list;

		public AreaMeterList(List<AreaAndMeters> data, long total, int page,
				int pageSize) {
			this.list = data;
			this.totalRowCount = total;
			this.pageIndex = page;
			this.pageSize = pageSize;
		}

		public long getTotalRowCount() {
			return totalRowCount;
		}

		public int getPageIndex() {
			return pageIndex;
		}

		public boolean hasPrev() {
			return pageIndex > 1;
		}

		public boolean hasNext() {
			return (totalRowCount / pageSize) >= pageIndex;
		}

		public String getDisplayXtoYofZ() {
			int start = ((pageIndex - 1) * pageSize + 1);
			int end = start + Math.min(pageSize, list.size()) - 1;
			return start + " to " + end + " of " + totalRowCount;
		}

		public List<AreaAndMeters> getList() {
			return list;
		}

		public void setList(List<AreaAndMeters> list) {
			this.list = list;
		}

	}

	public static class AreaAndMeters {

		public AreaAndMeters(Component component, Double extraWaterMeter,
				Double conditionCCTVMeter, Double operationalDisturbanceMeter,
				Double socialSensitivityMeter) {
			this.component = component;
			this.extraWaterMeter = extraWaterMeter;
			this.conditionCCTVMeter = conditionCCTVMeter;
			this.operationalDisturbanceMeter = operationalDisturbanceMeter;
			this.socialSensitivityMeter = socialSensitivityMeter;
		}

		public Component component;
		public Double extraWaterMeter;
		public Double conditionCCTVMeter;
		public Double operationalDisturbanceMeter;
		public Double socialSensitivityMeter;
		public Double pi;
	}

	public static Double getTotalLenghtOfPipes(Long componentId,
			String className) {

		Query query = JPA
				.em()
				.createQuery(
						"SELECT SUM(c.componentDetail.length_3d) from Component c where c.ownerComponent.id = ? and c.componentDetail.datasource_class_name like ?");
		query.setParameter(1, componentId);
		query.setParameter(2, className);
		Double retVal = (Double) (query.getSingleResult() != null ? query
				.getSingleResult() : 0D);
		return retVal;
	}

	public static Double getTotalLenghtOfPipes(String className) {

		Query query = JPA
				.em()
				.createQuery(
						"SELECT SUM(c.componentDetail.length_3d) from Component c where c.ownerComponent.component_type.id = 1 and c.ownerComponent.scope = ? and c.componentDetail.datasource_class_name like ?");
		query.setParameter(1, Boolean.TRUE);
		query.setParameter(2, className);
		Double retVal = (Double) (query.getSingleResult() != null ? query
				.getSingleResult() : 0D);
		return retVal;
	}

	public static Double getValueDecimalForPropertyType(Long componentId,
			String propertyTypName) {
		PropertyType pt = PropertyType.findByName(propertyTypName);
		Query query = JPA
				.em()
				.createQuery(
						"SELECT cp.valueDecimal from ComponentProperty cp where cp.propertyType.id =:pType and cp.component.id =:cId");
		query.setParameter("pType", pt.id);
		query.setParameter("cId", componentId);
		Double retVal = (Double) query.getSingleResult();
		return retVal;
	}
	
	/**
	 * I component can be a pressure pipe component is a pipe
	 * and if datasource Class Name is one of the following
	 * "Paineviemäri - Jäte");
	 * "Paineviemäri, digit. - Jäte"
     * "Paineviemäri, epävarma - Jäte"
     * "Paineviemäri, korkeus arvioitu"
	 * 
	 * @param componentId
	 * @return true if component is a pressure pipe
	 */
	public static boolean isMemberOfClassList(Long componentId, String[] classArray) {
		
		List<String> classList = Arrays.asList(classArray);
		String queryStr = "SELECT COUNT(c) from Component c where c.id = :cId and c.componentDetail.datasource_class_name in :cClassList";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cId", componentId);
		query.setParameter("cClassList", classList);
		
		boolean retVal = ((Long) query.getSingleResult()) > 0 ? true: false;
		return retVal;
	}
	
	public static Double getTotalLenghtOfPipesInBuildingArea(Long buildingAreaId) {

		String queryStr = "SELECT SUM(c.componentDetail.length_3d) from Component c where c.component_type.id = 2 and c.buildingArea.id = ?";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter(1, buildingAreaId);
		Double retVal = (Double) (query.getSingleResult() != null ? query.getSingleResult() : 0D);
		return retVal;
	}
	
	public static List<Component> getPipesInPsAreaHavingPsScadaHourly() {
		
		String queryStr = "SELECT c FROM Component c WHERE c.ownerComponent in (SELECT DISTINCT(ps.component) FROM PsScadaHourly ps) ORDER BY c.ownerComponent.number";
		List<Component> retVal = null;
		
		Query query = JPA.em().createQuery(queryStr);
		retVal = (List<Component>) query.getResultList();
		
		return retVal;
	}
	
	public static int getPipeClassAverageDiameter(int classCode, Map<String, Integer> classCodeFilterMap) {
		
		String queryStr = "SELECT AVG(c.componentDetail.diameterValue + 0.0) FROM Component c" +
				" WHERE c.componentDetail.datasource_class_code >= :biggerAndEqualThenValue" +
				" and c.componentDetail.datasource_class_code <= :smallerAndEqualThenValue";
		
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("biggerAndEqualThenValue", classCodeFilterMap.get("biggerAndEqualThenValue"));
		query.setParameter("smallerAndEqualThenValue", classCodeFilterMap.get("smallerAndEqualThenValue"));
		double doubleVal = (Double) query.getSingleResult();
		
		return (int) doubleVal;
	}
	
	public static long getPipeCountDistributionsAccordingToDiameter(int minValue, int maxValue) {
		
		String queryStr = "SELECT COUNT(c.componentDetail.diameterValue) FROM Component c" + 
		" WHERE c.componentDetail.diameterValue >= :cMin" + 
		" and c.componentDetail.diameterValue < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}

	public static long getPipeCountDistributionsAccordingToGroundWaterArea(int minValue, int maxValue) {
		
		//String queryStr = "SELECT COUNT(c.groundWaterArea.classificationValue) FROM Component c" + 
		//" WHERE c.groundWaterArea.classificationValue >= :cMin" + 
		//" and c.groundWaterArea.classificationValue < :cMax";
		String queryStr = "SELECT COUNT(c) FROM Component c" + 
		" WHERE c.groundWaterArea.classificationValue >= :cMin" + 
		" and c.groundWaterArea.classificationValue < :cMax";		
		//String queryStr = "SELECT COUNT(c.componentDetail.sensitivityIndex.groundWaterAreaMeter.valueOfPipe) FROM Component c" + 
		//" WHERE c.componentDetail.sensitivityIndex.groundWaterAreaMeter.valueOfPipe >= :cMin" + 
		//" and c.componentDetail.sensitivityIndex.groundWaterAreaMeter.valueOfPipe < :cMax";		
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}	
	
	public static long getPipeCountDistributionsAccordingToRelativeFloorAreaMeters(float minValue, float maxValue) {
		
		String queryStr = "SELECT COUNT(c.componentDetail.sensitivityIndex.relativeFloorAreaMeter.valueOfPipe) FROM Component c" + 
		" WHERE c.componentDetail.sensitivityIndex.relativeFloorAreaMeter.valueOfPipe >= :cMin" + 
		" and c.componentDetail.sensitivityIndex.relativeFloorAreaMeter.valueOfPipe < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}

	public static long getPipeCountDistributionsAccordingToRoadClassification(int minValue, int maxValue) {
		
		String queryStr = "SELECT COUNT(c.roadClassification) FROM Component c" + 
		" WHERE c.roadClassification >= :cMin" + 
		" and c.roadClassification < :cMax";	
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}	

	public static long getPipeCountDistributionsAccordingToBeachDistance(float minValue, float maxValue) {
		
		String queryStr = "SELECT COUNT(c.ofDistanceToBeach) FROM Component c WHERE c.ofDistanceToBeach >= :cMin" + 
		" and c.ofDistanceToBeach < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}

	public static long getPipeCountDistributionsAccordingToBlockages(float minValue, float maxValue) {
		
		String queryStr = "SELECT COUNT(c.componentDetail.conditionIndex.blockageMeter.valueOfPipe) FROM Component c" + 
		" WHERE c.componentDetail.conditionIndex.blockageMeter.valueOfPipe >= :cMin" + 
		" and c.componentDetail.conditionIndex.blockageMeter.valueOfPipe < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}
	
	public static long getPipeCountDistributionsAccordingToFlushingEvents(float minValue, float maxValue) {
		
		String queryStr = "SELECT COUNT(c.componentDetail.conditionIndex.flushingEventMeter.valueOfPipe) FROM Component c" + 
		" WHERE c.componentDetail.conditionIndex.flushingEventMeter.valueOfPipe >= :cMin" + 
		" and c.componentDetail.conditionIndex.flushingEventMeter.valueOfPipe < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}

	public static long getPipeCountDistributionsAccordingToExtraWaterPercentage(float minValue, float maxValue) {
		
		String queryStr = "SELECT COUNT(c.componentDetail.conditionIndex.extraWaterMeter.valueOfPipe) FROM Component c" + 
		" WHERE c.componentDetail.conditionIndex.extraWaterMeter.valueOfPipe >= :cMin" + 
		" and c.componentDetail.conditionIndex.extraWaterMeter.valueOfPipe < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}

	public static long getPipeCountDistributionsAccordingToCCTVDefects(float minValue, float maxValue) {
		
		String queryStr = "SELECT COUNT(c.componentDetail.conditionIndex.cctvTotalMeter.valueOfPipe) FROM Component c" + 
		" WHERE c.componentDetail.conditionIndex.cctvTotalMeter.valueOfPipe >= :cMin" + 
		" and c.componentDetail.conditionIndex.cctvTotalMeter.valueOfPipe < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}

	public static long getPipeCountDistributionsAccordingToCCTVMajorDefects(float minValue, float maxValue) {
		
		String queryStr = "SELECT COUNT(c.componentDetail.conditionIndex.cctv34Meter.valueOfPipe) FROM Component c" + 
		" WHERE c.componentDetail.conditionIndex.cctv34Meter.valueOfPipe >= :cMin" + 
		" and c.componentDetail.conditionIndex.cctv34Meter.valueOfPipe < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}
	
	public static long getPipeCountDistributionsAccordingAnnualOverFlow(float minValue, float maxValue) {
		
		String queryStr = "SELECT COUNT(c.annualOverFlow) FROM Component c" + 
		" WHERE c.annualOverFlow >= :cMin" + 
		" and c.annualOverFlow < :cMax";
		Query query = JPA.em().createQuery(queryStr);
		query.setParameter("cMin", minValue);
		query.setParameter("cMax", maxValue);
		long retVal = (long) query.getSingleResult();
		
		return retVal;
	}	
}