package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtilSphinx {

	public static Date convertStringToDate(String str) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date formatDate(String sDate, String originalPattern)  {
        DateFormat oldFormatter = new SimpleDateFormat(originalPattern);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldDate = null;
		try {
			oldDate = (Date)oldFormatter.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oldDate = (Date)formatter.parse(formatter.format(oldDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println(oldDate);
        return oldDate;
	}
}
