package conf;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {

	public static SimpleDateFormat formatBirth = new SimpleDateFormat("dd-MM-yyyy");

	public static SimpleDateFormat formatBirth2 = new SimpleDateFormat("yyyy/MM/dd");

	public static SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
	
	public static SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
	
	public static SimpleDateFormat formatDateAndTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	public static String getDateAsString(long date) {
		return formatBirth2.format(new Date(date));
	}

	public static String getDateAsStringBelgium(long date) {
		return formatBirth.format(new Date(date));
	}
	
	public static String getDateAndTimeAsString(long time){
		return formatDateAndTime.format(new Date(time));
	}
	
	public static String getTimeAsString(long time){
		return formatTime.format(new Date(time));
	}


	// make sure no instance can be made of this class (implicit: static class)
	private DateConverter() {}

	public static long isCorrectDateOfBirth(String date) {
		long parsed = 0;
		try {
			formatBirth.setLenient(false);
			ParsePosition p = new ParsePosition(0);
			parsed = formatBirth.parse(date, p).getTime();
			if (p.getIndex() < 10 || p.getIndex() < date.length()) {
				parsed = -1;
			}
			if (parsed > System.currentTimeMillis()) {
				parsed = -2;
			}
			if(parsed < -2208988800000L){
				parsed = -3;
			}
		} catch (Exception e) {
			parsed = -1;
		}
		return parsed;
	}

	public static long isCorrectYear(String date) {
		long parsed = 0;
		try {
			formatYear.setLenient(false);
			ParsePosition p = new ParsePosition(0);
			parsed = formatYear.parse(date, p).getTime();
			if (p.getIndex() < 4 || p.getIndex() < date.length()) {
				parsed = -1;
			}
		} catch (Exception e) {
			parsed = -1;
		}
		return parsed;
	}

	public static long parseYear(String timeStamp) {
		long date = 0;

		try {
			date = formatYear.parse(timeStamp).getTime();
		} catch (ParseException e) {
			date = -1;
		}

		return date;
	}
	
	public static long parseDate(String timeStamp) {
		long date = 0;

		try {
			date = formatBirth.parse(timeStamp).getTime();
		} catch (ParseException e) {
			date = -1;
		}

		return date;
	}	

	public static String convertIntToHMS(int time) {
		String hours = "0" + (int) (Math.floor(time / 3600));
		String minutes = "0" + (int) (Math.floor(time / 60) % 60);
		String seconds = "0" + (time % 60);
		return hours.substring(hours.length() - 2) + ":"
				+ minutes.substring(minutes.length() - 2) + ":"
				+ seconds.substring(seconds.length() - 2);
	}
	
	public static long getCurrentDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date today = null;
		try {
			today = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return today.getTime();
		
	}
	
	public static long parseTime(String timeStamp){
		long date = 0;

		try {
			date = formatTime.parse(timeStamp).getTime();
		} catch (ParseException e) {
			date = -1;
		}

		return date;
	}
	
	public static long getCurrentDate2(){
		Calendar cal = Calendar.getInstance();
		
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		return cal.getTime().getTime();
		
	}
	
	public static long getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		
		
		cal.clear(Calendar.YEAR);
		cal.clear(Calendar.MONTH);
		cal.clear(Calendar.DAY_OF_MONTH);
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		return cal.getTime().getTime();
		
	}
	
	public static int getAge(long date){
		return Integer.parseInt(formatYear.format(new Date(getCurrentDate()))) - Integer.parseInt(formatYear.format(new Date(date)));
		
	}
}
