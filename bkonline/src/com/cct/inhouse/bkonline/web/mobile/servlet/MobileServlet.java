package com.cct.inhouse.bkonline.web.mobile.servlet;

import com.cct.inhouse.bkonline.core.mobile.domain.MDCaseType;
import com.cct.inhouse.bkonline.core.mobile.domain.MobileData;
import com.cct.inhouse.bkonline.core.mobile.service.MobileManager;

public abstract class MobileServlet extends MobileMasterServlet {

	private static final long serialVersionUID = -7732970393012606432L;

	@Override
	public void processByCase(MobileData mobileData) throws Exception {
		
		MobileManager manager = new MobileManager(getLogger());
		if (mobileData.getCaseType().equals(MDCaseType.NEW_LOGIN.getFlag())) {
			manager.newLogin(mobileData);
		}else{
			manager.pastLogin(mobileData);
			if (mobileData.getCaseType().equals(MDCaseType.TODO.getFlag())) {
				manager.searchNotification(mobileData);
			}else if(mobileData.getCaseType().equals(MDCaseType.CLEAR.getFlag())){
				manager.clearNotification(mobileData);
			}else if(mobileData.getCaseType().equals(MDCaseType.ROOM.getFlag())){
				manager.searchRoomMeeting(mobileData);
			}else if(mobileData.getCaseType().equals(MDCaseType.EVENT.getFlag())){
				manager.searchRoomEvent(mobileData);
			}else if(mobileData.getCaseType().equals(MDCaseType.APPROVE.getFlag())){
				manager.updateApprove(mobileData); 
			}else if(mobileData.getCaseType().equals(MDCaseType.CHECK_OUT.getFlag())){
				manager.updateCheckOut(mobileData); 
			}else if(mobileData.getCaseType().equals(MDCaseType.CHECK_OUT_OVER_LIMIT.getFlag())){
				manager.updateCheckOutOverLimit(mobileData); 
			}else if(mobileData.getCaseType().equals(MDCaseType.CANCEL.getFlag())){
				manager.updateCancel(mobileData); 
			}else{
				
			}
		}
		
	}
}
