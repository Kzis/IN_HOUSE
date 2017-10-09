package test.mobile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.struts2.json.JSONUtil;

import com.cct.enums.CharacterEncoding;
import com.cct.enums.SelectItemParameter;
import com.cct.inhouse.bkonline.core.mobile.domain.MobileData;

import util.io.IOUtil;
import util.string.StringDelimeter;

public class TestMaster {

	public static final String SERVLET_URL = "http://127.0.0.1:8080/bkonline/AndroidServlet";
	public static final String REQUEST_METHOD = "POST";
	public static final int CONNECTION_TIMEOUT = 5000;
	public static final int READING_TIMEOUT = 5000;
	
	public static String request(String selectItemUrl, MobileData mobileData) throws Exception {
		
		StringBuilder responseBuilder = new StringBuilder();
		
		StringBuilder parameterBuilder = new StringBuilder();
		parameterBuilder.append(SelectItemParameter.JSON_REQUEST.getValue() + StringDelimeter.EQUAL.getValue());
		parameterBuilder.append(JSONUtil.serialize(mobileData, null, null, false, true, true));
		
		URL url = new URL(selectItemUrl);
		HttpURLConnection httpURLConn = (HttpURLConnection) url.openConnection();
		httpURLConn.setRequestMethod(REQUEST_METHOD);
		httpURLConn.setConnectTimeout(CONNECTION_TIMEOUT);
		httpURLConn.setReadTimeout(READING_TIMEOUT);
		httpURLConn.setRequestProperty("Accept-Language", CharacterEncoding.UTF8.getValue());
		httpURLConn.setDoOutput(true);
		
		
		OutputStreamWriter outputStreamWriter = null;
		try {
			System.out.println(parameterBuilder.toString());
			outputStreamWriter = new OutputStreamWriter(httpURLConn.getOutputStream());
			outputStreamWriter.write(parameterBuilder.toString());
			outputStreamWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(outputStreamWriter);
		}


		int responseCode = 0;
		try {
			responseCode = httpURLConn.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("responseCode: " + responseCode);
		if (responseCode == 200) {
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			
			try {
			
				inputStreamReader = new InputStreamReader(httpURLConn.getInputStream(), CharacterEncoding.UTF8.getValue());
				bufferedReader = new BufferedReader(inputStreamReader);

				String inputLine;
				while ((inputLine = bufferedReader.readLine()) != null) {
					responseBuilder.append(inputLine);
				}
				
				if (responseBuilder.toString().equals("[]")) {
					responseBuilder.delete(0, responseBuilder.length());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtil.close(bufferedReader);
				IOUtil.close(inputStreamReader);
			}
		}
		
		return responseBuilder.toString();
	}
}
