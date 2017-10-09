/**
 * การ descrypt ข้อมูล
 * @author : SD
 * @version : 1.0
 *
 */
package util.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import util.cryptography.AES128.AES;
import util.cryptography.AES256.AESCipherHybridService;
import util.cryptography.DES.KeyEncryptDecrypt;
import util.cryptography.DES.StringEncrypter;
import util.type.CrytographyType.DecType;


/**
 * Class ส่วน decrypt ข้อมูล
 */
public class DecryptionUtil {


	/**
	 * Decrypt ข้อมูล.
	 * <p>
	 * [Example : String str = DecryptionUtil.doDecrypt(str, DecryptionUtil.DecType.AES128);]
	 *
	 * @param stringtoencrypt : ข้อมูล encrypt
	 * @param type : รูปแบบที่ต้องการใช้ decrypt(DES,AES128)
	 * @return : ข้อมูลที่  decrypt
	 */
	public static String doDecrypt(String stringtoencrypt,DecType type) throws Exception{
		String result = "";
		try{
			if(type == null) return "";
			if(type.equals(DecType.DES)){
				result = doDecryptDES(stringtoencrypt);
			}else if(type.equals(DecType.AES128)){
				result = doDecryptAES128(stringtoencrypt);
			}
			return result;
		}catch(Exception e){
			throw e;
		}
	}

	public static String doDecrypt(String stringtoencrypt, String key, DecType type) throws Exception{
		String result = "";
		try{
			if(type == null) return "";
			if(type.equals(DecType.DES)){
				result = doDecryptDES(stringtoencrypt);
			}else if(type.equals(DecType.AES128)){
				result = doDecryptAES128(stringtoencrypt);
			}else if(type.equals(DecType.AES256)){
				result = doDecryptAES256(stringtoencrypt, key);
			}
			return result;
		}catch(Exception e){
			throw e;
		}
	}



	/**
	 * Decrypt Data โดยใช้  DES :
	 * @param stringtoencrypt : ข้อมูลที่ encrypt
	 * @return : ข้อมูลที่ decrypt
	 */
    private static String doDecryptDES(String stringtoencrypt) throws Exception{
    	try{
    		String encryptionKey;
	        String encryptionScheme;
	        encryptionKey = KeyEncryptDecrypt.getkey();
	        encryptionScheme = "DES";
	        String decryptedString;
	        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
	        decryptedString = encrypter.decrypt(stringtoencrypt);
	        return decryptedString;
    	}catch (Exception e) {
			throw e;
		}
    }


	/**
	 * Decrypt Data โดยใช้  AES128
	 * @param base64 : ข้อมูลที่ encrypt
	 * @return : ข้อมูลที่ decrypt
	 */
	private static String doDecryptAES128(String base64) throws Exception {
		String stringtodecrypt = "";
		try {
			AES aes = new AES();
			aes.setInitialVector("QEU#%$1587IOPBN1");
			aes.setKey("12345$@AB789QWEZ");
			aes.init();
			stringtodecrypt = aes.decrypt(base64);
		} catch (InvalidKeyException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (NoSuchPaddingException e) {
			throw e;
		} catch (InvalidAlgorithmParameterException e) {
			throw e;
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (BadPaddingException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		return stringtodecrypt;
	}

    private static String doDecryptAES256(String stringtodecyypt,String key) throws Exception{
    	try{
			AESCipherHybridService aes = new AESCipherHybridService(key);
			return aes.decrypt(stringtodecyypt);
    	}catch(Exception e){
    		throw e;
    	}
    }

}
