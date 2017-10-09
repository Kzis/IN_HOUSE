package test;

import com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServiceProxy;
import com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite;

public class TestCallWebService {

	public static void main(String[] args) {

		String processDate = "20100701000000";
		String userId = null; // "415";
		try {
			GetTimeOffsetWebServiceProxy service = new GetTimeOffsetWebServiceProxy();
			WorkOnsite[] arrWorkOnSite = service.getTimeOffset(processDate, userId);
			System.out.println("arrWorkOnSite: " + arrWorkOnSite);
			if (arrWorkOnSite != null && arrWorkOnSite.length > 0) {
				System.out.println("arrWorkOnSite.length: " + arrWorkOnSite.length);
				for (int i = 0; i < arrWorkOnSite.length; i++) {
					System.out.println(arrWorkOnSite[i].getNickname());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
