package com.redinfo.importcheck.util;

public final class Helper {
	private Helper() {
	}

	private static String BASE_URL = "http://www.salt518.com/index.php/mobile/";
	public static String LOGIN_URL = BASE_URL + "login/";
	public static String REG_URL = BASE_URL + "checkcode/";
	public static String SEARCH_URL = BASE_URL + "tracing/";
	public static String REPORT_URL = BASE_URL + "confirmresult";

	public static int ACCESS_SERVER_ERR = 0x910;
	public static int VERIFICATION_NEGATIVED = 0x00;
	public static int VERIFICATION_PASSED = 0x01;

}