package test.mobile;

import com.cct.inhouse.bkonline.core.mobile.domain.MDCaseType;
import com.cct.inhouse.bkonline.core.mobile.domain.MDNotification;
import com.cct.inhouse.bkonline.core.mobile.domain.MDUser;
import com.cct.inhouse.bkonline.core.mobile.domain.MobileData;

import util.gson.GSONUtil;

public class TestNotification extends TestMaster {
	
	public static void main(String[] args) {
		
		MobileData mobileData = new MobileData();
		String response = null;
		try {
			mobileData.setCaseType(MDCaseType.TODO.getFlag());
			MDUser md = new MDUser();
			md.setTokenId("a3a5beb45db7e0daef9204e84a18b355");
			
			
			mobileData.setMdUser(md);
			response = request(SERVLET_URL, mobileData);
			
			MobileData result = (MobileData) GSONUtil.transfromJSONStringToObject(response, MobileData.class);
			
			System.out.println(result.getMdStatus().getExceptionFlag() + " > " + result.getMdStatus().getMessage());
			System.out.println(result.getMdStatus().getExceptionDesc());
			System.out.println("TotalActionMessage : "+result.getMdNotification().getTotalActionMessage());
			System.out.println("TotalTodoMessage : "+result.getMdNotification().getTotalTodoMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(response);
	}
}
