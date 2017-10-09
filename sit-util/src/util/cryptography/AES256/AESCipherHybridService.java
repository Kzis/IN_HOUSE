package util.cryptography.AES256;

/**
 * @Author: duangthip.k
 * @Version: 1.0
 * @Create date: 2009-11-30
 *
 * Changes Log
 * --------------------------
 * 2009-12-01: v 1.11	Modifier: duangthip.k
 * 		Modifications Crypt Methods use JCE replace Chilkat
 */
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;

public class AESCipherHybridService {

	private String initialVector;
	private String key;

	private byte[] keyBytes;
	private byte[] initialVectorBytes;
	private Key secretKey;
	private IvParameterSpec ivParamSpec;
	private Cipher encryptor;
	private Cipher decryptor;
	private String charset = "UTF-8";

	public AESCipherHybridService(String passKey) throws Exception{
		init(passKey);
	}
	public void init(String passKey ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException {
		// Use IV from Key
		// keyBytes = HexUtils.toBytes(key);
		// initialVectorBytes = HexUtils.toBytes(initialVector);
		//
		// secretKey = new SecretKeySpec(keyBytes, "AES");
		// ivParamSpec = new IvParameterSpec(initialVectorBytes);

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(passKey.getBytes(charset), 0, passKey.length());
		byte[] rawKey = md.digest();

		secretKey = new SecretKeySpec(rawKey, "AES");
		ivParamSpec = new IvParameterSpec(rawKey);

		encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
		encryptor.init(Cipher.ENCRYPT_MODE, secretKey, ivParamSpec);

		decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decryptor.init(Cipher.DECRYPT_MODE, secretKey, ivParamSpec);
	}


	public byte[] encrypt(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
		byte[] output = encryptor.doFinal(input);
		return output;
	}

	public String encrypt(String input) throws IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		byte[] bytes = input.getBytes(charset);
		byte[] output = encrypt(bytes);

		char[] buffer = Base64Coder.encode(output);
		return new String(buffer);
	}

	public byte[] decrypt(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
		byte[] output = decryptor.doFinal(input);
		return output;
	}

	public String decrypt(String input) throws UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException {
		byte[] bytes = Base64Coder.decode(input);
		byte[] output = decrypt(bytes);
		return new String(output, charset);
	}

	public boolean encryptRowData(String inputFile, String outputFile) throws Exception {
		boolean bResult = false;
		// Reader
		File file = null;
		FileReader freader = null;
		LineNumberReader lnreader = null;

		// Writer
		PrintWriter out = null;

		try {
			// Reader
			file = new File(inputFile);
			freader = new FileReader(file);
			lnreader = new LineNumberReader(freader);

			// Writer
			// Create file
			out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));

			String line = "";
			String encrypted = "";
			while ((line = lnreader.readLine()) != null) {
				encrypted = encrypt(line);
				// System.out.println("Line:  " + lnreader.getLineNumber() +
				// ": " + line + " => " + encrypted);
				out.write(encrypted + "\r\n"); // New line
			}
			bResult = true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				// Close the output stream
				freader.close();
				lnreader.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}

		}
		return bResult;

	}

	public boolean decryptRowData(String inputFile, String outputFile) throws Exception {
		boolean bResult = false;
		// Reader
		File file = null;
		FileReader freader = null;
		LineNumberReader lnreader = null;

		// Writer
		PrintWriter out = null;

		try {
			// Reader
			file = new File(inputFile);
			freader = new FileReader(file);
			lnreader = new LineNumberReader(freader);

			// Writer
			// Create file
			out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));

			String line = "";
			String decrypted = "";
			while ((line = lnreader.readLine()) != null) {
				decrypted = decrypt(line);
				// System.out.println("Line:  " + lnreader.getLineNumber() +
				// ": " + line + " => " + decrypted);
				out.write(decrypted + "\r\n"); // New line
			}
			bResult = true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				// Close the output stream
				freader.close();
				lnreader.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return bResult;
	}

	public boolean encryptBinaryFile(String srcFile, String dstFile) throws Exception {
		boolean bResult = false;

		FileOutputStream fos = new FileOutputStream(dstFile);
		CipherOutputStream cos = new CipherOutputStream(fos,encryptor);
		FileInputStream fis = new FileInputStream(srcFile);

		int len = 0;
		try {

			byte[] buf = new byte[4096];
			while((len = fis.read(buf)) != -1) {
				cos.write(buf,0,len);
			}
			cos.flush();

			bResult = true;
			//System.out.println("Encrypt File Complete." + srcFile + " ==> " + dstFile);
		} catch (Exception ex) {
			System.err.println("Error: Encrypt File. => " + srcFile + " ==> " + dstFile + "\n" + ex);
			throw ex;
		}finally{
			try{
				cos.close();
				fis.close();
				fos.close();
			}catch(Exception ex){throw ex;
			}
		}

		return bResult;
	}

	public boolean decryptBinaryFile(String srcFile, String dstFile) throws Exception {
		boolean bResult = false;
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(dstFile);
		CipherInputStream cis = new CipherInputStream(fis,decryptor);
		int len = 0;

		try {
			byte[] buf = new byte[4096];
			while((len = cis.read(buf)) != -1) {
				fos.write(buf,0,len);
			}
			fos.flush();
			bResult = true;
			//System.out.println("Decrypt File Complete." + srcFile + " ==> " + dstFile);
		} catch (Exception ex) {
			System.err.println("Error: Decrypt File. => " + srcFile + " ==> " + dstFile + "\n" + ex);
			throw ex;
		}finally{
			try{
				fis.close();
				fos.close();
				cis.close();
			}catch(Exception ex){
			}
		}

		return bResult;
	}

	public byte[] decryptBinaryFile(String srcFile) throws Exception {
		FileInputStream fis = new FileInputStream(srcFile);
		CipherInputStream cis = new CipherInputStream(fis,decryptor);
		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(cis);
		} catch (Exception ex) {
			throw ex;
		}finally{
			try{
				fis.close();
				cis.close();
			}catch(Exception ex){
			}
		}

		return bytes;
	}

	public void write(byte[] bytes, OutputStream out) throws Exception {
		CipherOutputStream cos = new CipherOutputStream(out, encryptor);
		cos.write(bytes, 0, bytes.length);
		cos.close();

	}

	public void read(byte[] bytes, InputStream in) throws Exception {
		CipherInputStream cis = new CipherInputStream(in, decryptor);
		int pos = 0, intValue;

		while ((intValue = cis.read()) != -1) {
			bytes[pos] = (byte) intValue;
			pos++;
		}
	}

	public static String convertTime(long elapsed) {
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		return formatter.format(new Date(elapsed));
	}

	private String getInitialVector() {
		return initialVector;
	}

	private void setInitialVector(String initialVector) {
		this.initialVector = initialVector;
	}

	private String getKey() {
		return key;
	}

	private void setKey(String key) {
		this.key = key;
	}

	public String getCharset() {
		return charset;
	}

	private void setCharset(String charset) {
		this.charset = charset;
	}

	private Key getSecretKey() {
		return secretKey;
	}

	private Cipher getEncryptor() {
		return encryptor;
	}

	private Cipher getDecryptor() {
		return decryptor;
	}
}
