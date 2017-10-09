package util.cryptography.SHA1;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

public class FileEncrypter {

	/**
	 * The method is get key index by string data by SHA-1 algorithm
	 */
	public String doEncryptSHA1(String file) throws Exception{

		FileInputStream fis = null;
		String encryptSha1 = "";

		try{
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			fis = new FileInputStream(file);

	        byte[] data = new byte[1024];
	        int read = 0;

	        while ((read = fis.read(data)) != -1) {
	            sha1.update(data, 0, read);
	        }
	        byte[] hashBytes = sha1.digest();

	        StringBuilder sb = new StringBuilder(hashBytes.length * 2);

            Formatter formatter = new Formatter(sb);
            for (byte b : hashBytes) {
                formatter.format("%02x", b);  // format hexadecimal
            }

	        encryptSha1  = sb.toString();

        }catch (Exception e) {
        	throw e;
        }finally {
            try {
                if (fis!=null){
                	fis.close();
                }
	        }catch(Exception e) {
	        	throw new Exception("Unable to close input stream for SHA1 calculation", e);
	        }
        }
		return encryptSha1;
	}
}
