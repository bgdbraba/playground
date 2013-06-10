package main;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

	public static SimpleDateFormat formatBirth = new SimpleDateFormat("dd/MM/yyyy");

	public static SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");

	public static String getDateAsString(long date) {
		return formatBirth.format(new Date(date));
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
}
