package test.mobile;

import com.cct.inhouse.bkonline.core.mobile.domain.MDCaseType;
import com.cct.inhouse.bkonline.core.mobile.domain.MDNotification;
import com.cct.inhouse.bkonline.core.mobile.domain.MDRoomBookingData;
import com.cct.inhouse.bkonline.core.mobile.domain.MDUser;
import com.cct.inhouse.bkonline.core.mobile.domain.MobileData;
import com.cct.inhouse.bkonline.domain.BKOnlineVariable;

import util.gson.GSONUtil;

public class TestEvent extends TestMaster {
	
	public static void main(String[] args) {
		
		MobileData mobileData = new MobileData();
		String response = null;
		try {
			mobileData.setCaseType(MDCaseType.EVENT.getFlag());
			MDUser md = new MDUser();
			md.setTokenId("a3a5beb45db7e0daef9204e84a18b355");
			MDRoomBookingData roomSelect = new MDRoomBookingData();
			
			//case 1 Have Mode & Step
//			roomSelect.setCurrentMode(BKOnlineVariable.Mode.DAY.getValue());
//			roomSelect.setCurrentStep("");
			
			//case 2 Have Mode & Haven't Step
//			roomSelect.setCurrentMode(BKOnlineVariable.Mode.DAY.getValue());
			
			//case 3 Haven't Mode & Haven't Step
			
			//case 4 Haven't Mode & Step not Empty & Haven't Day
//			roomSelect.setCurrentStep("previous");
//			roomSelect.setCurrentStep("next");
			
			//case 5 Have Mode & Step not Empty & Have Day
//			roomSelect.setCurrentMode(BKOnlineVariable.Mode.MONTH.getValue());
//			roomSelect.setCurrentDay("01/10/2017");
//			roomSelect.setCurrentStep("next");
			
			//case final MdRoomSelect null
			mobileData.setMdRoomSelect(roomSelect);
			
			mobileData.setMdUser(md);
			response = request(SERVLET_URL, mobileData);
			
			MobileData result = (MobileData) GSONUtil.transfromJSONStringToObject(response, MobileData.class);
			
			System.out.println(result.getMdStatus().getExceptionFlag() + " > " + result.getMdStatus().getMessage());
			System.out.println(result.getMdStatus().getExceptionDesc());
			for (int i = 0; i < result.getMdEvent().size(); i++) {
				System.out.println(i+": isDataAdmin : "+result.getMdEvent().get(i).isDataAdmin());
				System.out.println(i+": getCurrentTime : "+result.getMdEvent().get(i).getCurrentTime());
			}
			System.out.println("getMdRoom : "+result.getMdRoom().size());
			System.out.println("getMdEvent : "+result.getMdEvent().size());
			System.out.println("getCurrentDay : "+result.getMdEvent().get(1).getCurrentDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(response);
	}
}
