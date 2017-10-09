package util.encode;
/**
 * Description: EnCodeBase64
 * @Author: Nanthanat.l
 * @Version: 1.0
 * @Create date: 2013-04-11
 * Changes Log :
 * ----------------
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;


public class EnCodeBase64 {

	/** Byte to base64
	 * @param : byte
	 * @return : String encoded
	 *
	 * */
	private static String encodeBase64(byte[] bytes) throws Exception{
		byte[] encoded = null;
		try{

			 //encoding  byte array into base 64
			encoded = Base64.encodeBase64(bytes);

		}catch (Exception e) {
			throw e;
		}

		return new String(encoded);
	}


	/** encodeBase64
	 * @param : filePath คือค่า  เช่น c:\image\passport.jpg
	 * @return : String encoded
	* */

	public static String encodeBase64(String filePath) throws Exception{
		String encoded = "";

		 try{
			 File file = new File(filePath);
			 byte[] bytes = loadFile(file);

			 encoded = encodeBase64(bytes);

		}catch (Exception e) {
			throw e;
		}


		return new String(encoded);
	}


/**
 * String Base64 to byte
 * @param : String Base64
 * @return : byte[]
 *
 **/
	public static byte[] decodeBase64(String data) throws Exception{
		byte[] decoded = null;
		try{
			 byte[] base64 = data.getBytes("UTF-8");

			 //decoding byte array into base64
			decoded = Base64.decodeBase64(base64);


		 }catch(Exception e){
			 throw e;
		 }


		return decoded;
	}

	/** Read file To byte
	 * @param : file
	 * @return : byte[]
	 *
	 * */

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] bytes = null;
		try{
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				throw new IOException("Could not completely read file "+file.getName());
			}

			bytes = new byte[(int)length];
			int offset = 0;
			int numRead = 0;

			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "+file.getName());
			}


		}catch (Exception e) {
			// TODO: handle exception
		}finally {
		    try {
		        if ( is != null )
		        	is.close();
		   } catch ( IOException e) {
		   }
		}


		return bytes;
	}


	public static void main(String[] args) {

		try{

			//Base64x b = new Base64x();
//			byte[] bytes = b.test();
//
//			String x = encodeBase64(bytes);
//
//			byte[] bb =  decodeBase64(x);

			String x = encodeBase64("D:/mykam/pic/kame.jpg");
			byte[] bb =  decodeBase64(x);


			//b.testImage(bb);


		}catch (Exception e) {
			// TODO: handle exception
		}

	}


}
