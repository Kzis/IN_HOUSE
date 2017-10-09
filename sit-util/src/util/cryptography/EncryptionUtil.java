/**
 * Encrypt Data
 * @author : SD
 * @version : 1.0
 *
 */
package util.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import util.cryptography.AES128.AES;
import util.cryptography.AES256.AESCipherHybridService;
import util.cryptography.DES.KeyEncryptDecrypt;
import util.cryptography.DES.StringEncrypter;
import util.cryptography.SHA1.FileEncrypter;
import util.type.CrytographyType.EncFileType;
import util.type.CrytographyType.EncType;

/**
 *
 * Class ส่วน encrypt ข้อมูล
 *
 */
public class EncryptionUtil {

	/**
	 * Encrypt Data.
	 * <p>
	 * [Example : String str = EncryptionUtil.doEncrypt("123",EncryptionUtil.EncType.AES128)];
	 *
	 * @param stringtoencrypt : ข้อมูลที่ต้องการ encrypt
	 * @param type : รูปแบบ encrypt(DES,AES128,SHA256,MD5)
	 * @return : ข้อมูลที่ encrypt
	 *
	 */
	public static String doEncrypt(String stringtoencrypt,EncType type) throws Exception{
		String result = "";
		try{
			if(type == null) return "";
			if(type.equals(EncType.DES)){
				result = doEncryptDES(stringtoencrypt);
			}else if(type.equals(EncType.AES128)){
				result = doEncryptAES128(stringtoencrypt);
			}else if(type.equals(EncType.SHA256)){
				result = doEncryptSHA256(stringtoencrypt);
			}else if(type.equals(EncType.MD5) ){
				result = doEncryptMD5(stringtoencrypt);
			}else if(type.equals(EncType.AES256) ){
				result = doEncryptAES256(stringtoencrypt,"Isb5Plse$%!dsl0[sd{sd,xpW>ztF.;e");
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	public static String doEncrypt(String stringtoencrypt,String key, EncType type) throws Exception{
		String result = "";
		try{
			if(type == null) return "";
			if(type.equals(EncType.DES)){
				result = doEncryptDES(stringtoencrypt);
			}else if(type.equals(EncType.AES128)){
				result = doEncryptAES128(stringtoencrypt);
			}else if(type.equals(EncType.SHA256)){
				result = doEncryptSHA256(stringtoencrypt);
			}else if(type.equals(EncType.MD5) ){
				result = doEncryptMD5(stringtoencrypt);
			}else if(type.equals(EncType.AES256) ){
				result = doEncryptAES256(stringtoencrypt,key);
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * Encrypt Data File.
	 * <p>
	 * [Example : String data = EncryptionUtil.doEncryptFile("c:/deployout.txt", EncFileType.SHA1)]
	 *
	 * @param file : path file ที่ต้องการ encrypt
	 * @param type : รูปแบบ Encrypt (SHA1)
	 * @return : ข้อมูลที่ encrypt
	 *
	 */
	public static String doEncryptFile(String file,EncFileType type) throws Exception{
		String result = "";
		try{
			if(type == null) return "";
			if(type.equals(EncFileType.SHA1)){
				FileEncrypter encrypter = new FileEncrypter();
				result = encrypter.doEncryptSHA1(file);
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * Encrypt Data โดยใช้  DES.
	 *
	 * @param stringtoencrypt : ข้อมูลที่ต้องการ encrypt
	 * @return : ข้อมูลที่ encrypt
	 */
    private static String doEncryptDES(String stringtoencrypt) throws Exception{
    	try{
    		String encryptionKey;
	        String encryptionScheme;
	        encryptionKey = KeyEncryptDecrypt.getkey();
	        encryptionScheme = "DES";
	        String encryptedString;
	        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
	        encryptedString = encrypter.encrypt(stringtoencrypt);
	        return encryptedString;
    	}catch (Exception e) {
			throw e;
		}
    }

	/**
	 * Encrypt Data โดยใช้  AES128.
	 *
	 * @param plain : ข้อมูลที่ต้องการ encrypt
	 * @return : ข้อมูลที่ encrypt
	 */
	private static String doEncryptAES128(String plain) throws Exception{
		String stringtoencrypt = "";
		try {
			AES aes = new AES();
			aes.setInitialVector("QEU#%$1587IOPBN1");
			aes.setKey("12345$@AB789QWEZ");
			aes.init();
			stringtoencrypt = aes.encrypt(plain);
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
		return stringtoencrypt;
	}

	/**
	 * Encrypt Data โดยใช้  SHA256.
	 *
	 * @param stringtoencrypt : ข้อมูลที่ต้องการ encrypt
	 * @return : ข้อมูลที่ encrypt
	 */
    private static String doEncryptSHA256(String stringtoencrypt) throws Exception{
    	MessageDigest sha=null;
    	try{
    		//sha = MessageDigest.getInstance("SHA1");
    		sha = MessageDigest.getInstance("SHA-256");
    		return asHex(sha.digest(stringtoencrypt.getBytes("UTF8")));
    	}catch(Exception e){
    		throw e;
    	}
    }

    private static String asHex(byte buf[]) throws Exception{
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
			strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}


	/**
	 * Encrypt Data โดยใช้  MD5.
	 *
	 * @param stringtoencrypt : ข้อมูลที่ต้องการ encrypt
	 * @return : ข้อมูลที่ encrypt
	 */
    private static String doEncryptMD5(String stringtoencrypt) throws Exception{
    	try{
    		MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(stringtoencrypt.getBytes("UTF8"));
            //m.update(stringtotoencrypt.getBytes("iso-8859-1"));
            byte s[] = m.digest();

            String result = "";
            for (int i = 0; i < s.length; i++) {
            	result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
            }
            return result;
    	}catch(Exception e){
    		throw e;
    	}
    }


    private static String doEncryptAES256(String stringtoencrypt,String key) throws Exception{
    	try{
			AESCipherHybridService aes = new AESCipherHybridService(key);
			return aes.encrypt(stringtoencrypt);
    	}catch(Exception e){
    		throw e;
    	}
    }




}
