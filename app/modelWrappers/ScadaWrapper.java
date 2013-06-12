package modelWrappers;

import java.util.ArrayList;
import java.util.Collection;

import models.Component;
import models.PsScadaHourly;
import models.PumpScadaHourly;

public class ScadaWrapper {
	
	public Component component = new Component();;
	
	public PsScadaHourly psScadaHourly = new PsScadaHourly();
	
	public Collection<PumpScadaHourly> pumpScadaHourlyList = new ArrayList<PumpScadaHourly>();

}
