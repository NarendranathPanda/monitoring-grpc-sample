package com.naren.grpc.metric.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EpochTime {

	private static final SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");

	public static void main(String[] args) {
		try {
			currentTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static long currentTime() throws ParseException {
		long epochTime;
		Date today = Calendar.getInstance().getTime();
		String currentTime = format.format(today);
		Date date = format.parse(currentTime);
		epochTime = date.getTime();
		return epochTime;
	}

}