package util.cryptography.AES128;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES {
	private String initialVector;
	private String key;
	
	private byte[] keyBytes;
	private byte[] initialVectorBytes;
	private Key secretKey;
	private IvParameterSpec ivParamSpec;
	private Cipher encryptor;
	private Cipher decryptor;
	private String charset = "UTF8";
	
	public void init() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		keyBytes = HexUtils.toBytes(key);
		initialVectorBytes = HexUtils.toBytes(initialVector);
		
		secretKey = new SecretKeySpec(keyBytes, "AES");
		ivParamSpec = new IvParameterSpec(initialVectorBytes);
		
		encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
		encryptor.init(Cipher.ENCRYPT_MODE, secretKey, ivParamSpec);
		
		decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decryptor.init(Cipher.DECRYPT_MODE, secretKey, ivParamSpec);
	}
	
	public byte[] encrypt(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
		byte[] output = encryptor.doFinal(input);
		return output;
	}
	
	public String encrypt(String input) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] bytes = input.getBytes(charset);
		byte[] output = encrypt(bytes);
		
		char[] buffer = Base64Coder.encode(output);
		return new String(buffer);
	}
	
	public byte[] decrypt(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
		byte[] output = decryptor.doFinal(input);
		return output;
	}
	
	public String decrypt(String input) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		byte[] bytes = Base64Coder.decode(input);
		byte[] output = decrypt(bytes);
		return new String(output, charset);
	}

	public String getInitialVector() {
		return initialVector;
	}

	public void setInitialVector(String initialVector) {
		this.initialVector = initialVector;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Key getSecretKey() {
		return secretKey;
	}

	public Cipher getEncryptor() {
		return encryptor;
	}

	public Cipher getDecryptor() {
		return decryptor;
	}
}
