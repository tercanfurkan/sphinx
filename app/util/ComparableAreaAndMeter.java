package util;

import java.util.Comparator;

import models.Component.AreaAndMeters;

public class ComparableAreaAndMeter implements Comparator<AreaAndMeters>{

	@Override
	public int compare(AreaAndMeters o1, AreaAndMeters o2) {
		if (o1.pi > o2.pi) return 1;
		if (o1.pi < o2.pi) return -1;
        return 0;
	}

}
