package test;

import java.util.List;
import java.util.Locale;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemRequest;
import com.cct.common.CommonUser;
import com.cct.inhouse.util.IHUtil;

import util.gson.GSONUtil;

public class TestRequestSelectItem {

	public static void main(String[] args) {
		try {
			String url = "http://localhost:8080/ih_central/AllQAUserSelectItemServlet";
//			String url = "http://localhost:8080/ih_central/ProjectsSelectItemServlet";
//			String url = "http://localhost:8080/ih_central/EmployeeFullnameByDepartmentIdServlet";
//			EmployeeFullnameByDepartmentRequest csiRequest = new EmployeeFullnameByDepartmentRequest();
			
//			String url = "http://10.100.10.238:8080/ih_central/DepartmentSelectItemServlet";
//			String url = "http://localhost:8080/ih_central/DepartmentSelectItemServlet";
			
			CommonSelectItemRequest csiRequest = new CommonSelectItemRequest();
//			csiRequest.setTerm("IMM");
//			csiRequest.setDepartmentId("21");
			csiRequest.setLimit("2");
			
			csiRequest.setLocale(Locale.ENGLISH);
			
			CommonUser commonUser = new CommonUser();
			commonUser.setId("123");
			commonUser.setGivenName("สิทธิพล");
			
			csiRequest.setCommonUser(commonUser);
			
			String response = IHUtil.requestSelectItem(url, csiRequest);
			
			System.out.println(response);
			@SuppressWarnings("unchecked")
			List<CommonSelectItem> collectionSelectItem = (List<CommonSelectItem>) GSONUtil.transfromJSONStringToType(response, CommonSelectItem.getGenericTypeArrayList());
			for (CommonSelectItem item : collectionSelectItem) {
				System.out.println("item: " + item);
				System.out.println(item.getKey() + ": " + item.getValue());
				System.out.println("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
