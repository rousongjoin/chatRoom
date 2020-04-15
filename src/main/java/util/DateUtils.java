package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public  static int getAgeFromBirthday(String birthday) throws ParseException {
	
	Calendar cal=Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	int yearNow = cal.get(Calendar.YEAR);
	int monthNow = cal.get(Calendar.MONTH);
	int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
	cal.setTime(sdf.parse(birthday));
	int yearBirth = cal.get(Calendar.YEAR);
	int monthBirth = cal.get(Calendar.MONTH);
	int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
	int age = yearNow - yearBirth;
	if (monthNow <= monthBirth) {
		if (monthNow == monthBirth) {
			if (dayOfMonthNow < dayOfMonthBirth)
				age--;
		} else {
			age--;
		}
	}			 
	return age;
	
}
	public static String getSystemTime() throws ParseException {
		Date date = new Date();//获得系统时间.
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
		String nowTime = sdf.format(date);
		return nowTime;
	}
}
