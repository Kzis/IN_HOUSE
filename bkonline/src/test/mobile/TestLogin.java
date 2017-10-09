package test.mobile;

import com.cct.inhouse.bkonline.core.mobile.domain.MDCaseType;
import com.cct.inhouse.bkonline.core.mobile.domain.MDNotification;
import com.cct.inhouse.bkonline.core.mobile.domain.MDUser;
import com.cct.inhouse.bkonline.core.mobile.domain.MobileData;

import util.gson.GSONUtil;

public class TestLogin extends TestMaster {
	
	public static void main(String[] args) {
		
		MobileData mobileData = new MobileData();
		String response = null;
		try {
			mobileData.setCaseType(MDCaseType.CLEAR.getFlag());
			MDUser md = new MDUser();
			md.setDeviceId("11");
			md.setUserName("kittaphart.c");
			md.setPlatform("AND");
			md.setTokenId("235793f19d9718182272293f1ace001c");
			
			MDNotification noti = new MDNotification();
			noti.setSelectNotificationId("1");
			
			mobileData.setMdUser(md);
			mobileData.setMdNotification(noti);
			response = request(SERVLET_URL, mobileData);
			
			MobileData result = (MobileData) GSONUtil.transfromJSONStringToObject(response, MobileData.class);
			
			System.out.println(result.getMdStatus().getExceptionFlag() + " > " + result.getMdStatus().getMessage());
			System.out.println(result.getMdStatus().getExceptionDesc());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(response);
	}
}
