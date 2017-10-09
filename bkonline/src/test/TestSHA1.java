package test;

import util.cryptography.EncryptionUtil;
import util.type.CrytographyType.EncType;

public class TestSHA1 {

	public static void main(String[] args) {
		try {
			String x = EncryptionUtil.doEncrypt("123456", EncType.MD5);
			System.out.println(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
