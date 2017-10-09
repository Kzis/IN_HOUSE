package util.test;

import util.cryptography.EncryptionUtil;
import util.type.CrytographyType.EncType;

public class TestAes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			String pass = EncryptionUtil.doEncrypt("11111", EncType.AES128);
			System.out.println(pass);

		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
