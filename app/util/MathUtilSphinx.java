package util;

import java.text.DecimalFormat;
import java.util.Comparator;

import models.Component.AreaAndMeters;
import models.wrapper.PipeIndexWrapper;

public class MathUtilSphinx {

	public static Double roundTwoDecimals(Double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(d));
	}
	
	public static double truncateDouble(double number, int numDigits) {
	    double result = number;
	    String arg = "" + number;
	    int idx = arg.indexOf('.');
	    if (idx!=-1) {
	        if (arg.length() > idx+numDigits) {
	            arg = arg.substring(0,idx+numDigits+1);
	            result  = Double.parseDouble(arg);
	        }
	    }
	    return result ;
	}
	
	public static Comparator<AreaAndMeters> comparatorExtraWater = new Comparator<AreaAndMeters>(){				    
				        public int compare(AreaAndMeters o1, AreaAndMeters o2)
				        {
				    		if (o1.pi > o2.pi) return -1;
				    		if (o1.pi < o2.pi) return 1;
				            return 0;
				        }
				    };
				    
	public static Comparator<PipeIndexWrapper> comparatorPipeConsequence = new Comparator<PipeIndexWrapper>(){				    
				        public int compare(PipeIndexWrapper o1, PipeIndexWrapper o2)
				        {
				    		if (o1.pipe_consequence_index > o2.pipe_consequence_index) return -1;
				    		if (o1.pipe_consequence_index < o2.pipe_consequence_index) return 1;
				            return 0;
				        }
				    };
				    
	public static Comparator<PipeIndexWrapper> comparatorPipeCondition = new Comparator<PipeIndexWrapper>(){				    
				        public int compare(PipeIndexWrapper o1, PipeIndexWrapper o2)
				        {
				    		if (o1.pipe_condition_index > o2.pipe_condition_index) return -1;
				    		if (o1.pipe_condition_index < o2.pipe_condition_index) return 1;
				            return 0;
				        }
				    };
}
