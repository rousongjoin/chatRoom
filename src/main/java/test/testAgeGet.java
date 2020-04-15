package test;

import java.text.ParseException;

import util.DateUtils;

public class testAgeGet {
	public static void main(String[] args) throws ParseException {
		int age=DateUtils.getAgeFromBirthday("2010-4-12");
		System.out.println(age);
		System.out.println(DateUtils.getSystemTime());
	}

}
