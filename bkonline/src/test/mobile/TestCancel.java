package test.mobile;

import util.gson.GSONUtil;

import com.cct.inhouse.bkonline.core.mobile.domain.MDCaseType;
import com.cct.inhouse.bkonline.core.mobile.domain.MDRoomBookingData;
import com.cct.inhouse.bkonline.core.mobile.domain.MDUser;
import com.cct.inhouse.bkonline.core.mobile.domain.MobileData;

public class TestCancel extends TestMaster {
	
	public static void main(String[] args) {
		
		MobileData mobileData = new MobileData();
		String response = null;
		try {
			
			mobileData.setCaseType(MDCaseType.CANCEL.getFlag());
			
			MDUser md = new MDUser();
			md.setTokenId("a3a5beb45db7e0daef9204e84a18b355");
			mobileData.setMdUser(md);
			
			MDRoomBookingData mdEvent = new MDRoomBookingData();
			mdEvent.setEventIdSelected("45");
			mobileData.setMdEventSelect(mdEvent);
			
			response = request(SERVLET_URL, mobileData);
			
			MobileData result = (MobileData) GSONUtil.transfromJSONStringToObject(response, MobileData.class);
			
			System.out.println(result.getMdStatus().getExceptionFlag() + " > " + result.getMdStatus().getMessage());
			System.out.println(result.getMdStatus().getExceptionDesc());
//			System.out.println("getMdRoom : "+result.getMdRoom().size());
//			System.out.println("getMdEvent : "+result.getMdEvent().size());
//			System.out.println("getCurrentDay : "+result.getMdEvent().get(1).getCurrentDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(response);
	}
}
