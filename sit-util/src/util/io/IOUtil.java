package util.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * สำหรับ Close IO ต่างๆ 
 * @author sittipol.m
 *
 */
public class IOUtil {

	/**
	 * Close BufferedReader
	 * @param bufferedReader
	 */
	public static void close(BufferedReader bufferedReader) {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (Exception e) {

		}
	}
	
	/**
	 * Close InputStreamReader
	 * @param inputStreamReader
	 */
	public static void close(InputStreamReader inputStreamReader) {
		try {
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Close OutputStreamWriter
	 * @param outputStreamWriter
	 */
	public static void close(OutputStreamWriter outputStreamWriter) {
		try {
			if (outputStreamWriter != null) {
				outputStreamWriter.flush();
				outputStreamWriter.close();
			}
		} catch (Exception e) {
			
		}
	}
}
