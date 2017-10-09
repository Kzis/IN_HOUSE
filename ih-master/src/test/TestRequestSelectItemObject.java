package test;

import java.util.List;
import java.util.Locale;

import com.cct.common.CommonSelectItemObject;
import com.cct.common.CommonUser;
import com.cct.inhouse.core.selectitem.domain.DepartmentSelectItemRequest;
import com.cct.inhouse.core.standard.department.domain.Department;
import com.cct.inhouse.util.IHUtil;
import com.google.gson.Gson;

public class TestRequestSelectItemObject {

	public static void main(String[] args) {
		try {
//			String url = "http://10.100.90.124:8080/ih_central/AllQAUserSelectItemServlet";
//			String url = "http://10.100.10.238:8080/ih_central/DepartmentSelectItemServlet";
			String url = "http://localhost:8080/ih_central/DepartmentSelectItemServlet";
			
			DepartmentSelectItemRequest csiRequest = new DepartmentSelectItemRequest();
			csiRequest.setTerm("C");
			csiRequest.setLimit("10");
			csiRequest.setLocale(Locale.ENGLISH);
			
			CommonUser commonUser = new CommonUser();
			commonUser.setId("123");
			commonUser.setGivenName("สิทธิพล");
			
			csiRequest.setCommonUser(commonUser);
			
			csiRequest.setDepartment(new Department());
			csiRequest.getDepartment().setDepartmentId("DepartmentId");
			csiRequest.getDepartment().setDepartmentName("DepartmentName");
			String response = IHUtil.requestSelectItem(url, csiRequest);
			
			System.out.println(response);
			List<CommonSelectItemObject<Department>> collectionSelectItem = new Gson().fromJson(response, Department.getGenericTypeArrayList());
			for (CommonSelectItemObject<Department> item : collectionSelectItem) {
				System.out.println("item: " + item);
				Department department = (Department)item.getObject();
				System.out.println(department.getDepartmentId());
				System.out.println(department.getDepartmentCode());
				System.out.println(department.getDepartmentName());
				System.out.println(department.getDepartmentDesc());
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
