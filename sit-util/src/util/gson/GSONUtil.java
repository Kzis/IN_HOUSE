package util.gson;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class GSONUtil {

	public static Object transfromJSONStringToObject(String jsonString, Class<?> classz) throws Exception {
		return new Gson().fromJson(jsonString, classz);
	}
	
	public static Object transfromJSONStringToType(String jsonString, Type typeOf) throws Exception {
		return new Gson().fromJson(jsonString, typeOf);
	}
	
}
