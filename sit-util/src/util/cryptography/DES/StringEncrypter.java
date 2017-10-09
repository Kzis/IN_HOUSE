// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 12/28/2010 2:47:02 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   StringEncrypter.java

package util.cryptography.DES;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringEncrypter
{
    /* member class not found */
    class EncryptionException {}


    public StringEncrypter(String encryptionScheme, String encryptionKey) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,Exception
    {
        if(encryptionKey == null)
            throw new IllegalArgumentException("encryption key was null");
        if(encryptionKey.trim().length() < 24)
            throw new IllegalArgumentException("encryption key was less than 24 characters");
        byte keyAsBytes[] = encryptionKey.getBytes("UTF8");
        if(encryptionScheme.equals("DESede"))
            keySpec = new DESedeKeySpec(keyAsBytes);
        else
        if(encryptionScheme.equals("DES"))
            keySpec = new DESKeySpec(keyAsBytes);
        else
            throw new IllegalArgumentException("Encryption scheme not supported: " + encryptionScheme);
        keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
        cipher = Cipher.getInstance(encryptionScheme);

    }

    public StringEncrypter(String encryptionScheme) throws Exception {
        this(encryptionScheme, "This is a fairly long phrase used to encrypt");
    }

    private static String bytes2String(byte bytes[]) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < bytes.length; i++)
            stringBuffer.append((char)bytes[i]);

        return stringBuffer.toString();
    }

    public String decrypt(String encryptedString) throws Exception{
        try{
	    	 if(encryptedString == null || encryptedString.trim().length() <= 0)
	             throw new IllegalArgumentException("encrypted string was null or empty");
	         byte ciphertext[];
	         javax.crypto.SecretKey key = keyFactory.generateSecret(keySpec);
	         cipher.init(2, key);
	         BASE64Decoder base64decoder = new BASE64Decoder();
	         byte cleartext[] = base64decoder.decodeBuffer(encryptedString);
	         ciphertext = cipher.doFinal(cleartext);
	         return bytes2String(ciphertext);
        }catch (Exception e) {
			throw e;
		}
    }

    public String encrypt(String unencryptedString) throws Exception{
    	try{
    		 if(unencryptedString == null || unencryptedString.trim().length() == 0)
    	            throw new IllegalArgumentException("unencrypted string was null or empty");
    	        byte ciphertext[];
    	        BASE64Encoder base64encoder;
    	        javax.crypto.SecretKey key = keyFactory.generateSecret(keySpec);
    	        cipher.init(1, key);
    	        byte cleartext[] = unencryptedString.getBytes("UTF8");
    	        ciphertext = cipher.doFinal(cleartext);
    	        base64encoder = new BASE64Encoder();
    	        return base64encoder.encode(ciphertext);
    	}catch (Exception e) {
			throw e;
		}
    }

    public static final String DEFAULT_ENCRYPTION_KEY = "This is a fairly long phrase used to encrypt";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    public static final String DES_ENCRYPTION_SCHEME = "DES";
    public static final String UNICODE_FORMAT = "UTF8";
    private Cipher cipher;
    private SecretKeyFactory keyFactory;
    private KeySpec keySpec;
}