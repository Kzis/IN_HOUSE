package util.test;

import java.io.File;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			FileEncryption secure = new FileEncryption();
			// to encrypt a file
			secure.makeKey();


			File fin = new File("c:/test2.txt");
			File fout = new File("c:/test3.txt");

			secure.encrypt(fin,fout);


//			secure.saveKey(encryptedKeyFile, publicKeyFile);
//			secure.encrypt(fileToEncrypt, encryptedFile);
//
//			// to decrypt it again
//			secure.loadKey(encryptedKeyFile, privateKeyFile);
//			secure.decrypt(encryptedFile, unencryptedFile);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
