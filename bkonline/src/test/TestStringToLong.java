package test;

import util.string.StringUtil;

public class TestStringToLong {

	public static void main(String[] args) {
		String a = null;
		String b = "";
		String c = "1";
		
		try {
			System.out.println(StringUtil.stringToLong(a));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(StringUtil.stringToLong(b));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(StringUtil.stringToLong(c));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
