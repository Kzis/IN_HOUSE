package test.line;

import com.cct.linebot.web.linebot.ws.LineBotWebServiceProxy;

public class TestSend {

	public static void main(String[] args) {
		new TestSend().sentMessageWait();
//		new TestSend().sentMessageApprove();
//		new TestSend().sentMessageCheckIn();
//		new TestSend().sentMessageCheckOut();
//		new TestSend().sentMessageCancel();
//		new TestSend().sentMessageNotCheckIn();
//		new TestSend().sentMessageNotCheckOut();
		
		
	}

	public void sentMessageTest() {
		try {
			String message = "jtr.APPS2015-1157";
			String lineUserId = "Ud16e9b45d6a705bf45770e7eff4faabf";
			String channelAccessToken = "7+2jy6NLlZXCCv54z7ABiWxAlLAECOPe1hTHzvzcE6BehgvbmTy3ucxJmkA1Ovng8cvMgTn9HlgBFkYq2wzXpiHs8A8fnY7KbHjQajIDoCkKzlTqXymoz7Fnw+2oPQcKUvx5LdYXR3aaw9mGfQupvQdB04t89/1O/w1cDnyilFU=";

			/*
			 * Linebot JtracDeliveryWebServiceProxy s = new JtracDeliveryWebServiceProxy("http://localhost:8080/jtracdelivery_webservice/JtracDeliveryWebService?wsdl"); s.recivceMessage(lineUserId,
			 * message, channelAccessToken);
			 */

			// Send line message
			byte[] bytes = message.getBytes("UTF-8");
			LineBotWebServiceProxy service = new LineBotWebServiceProxy("http://10.100.70.68/linebot_webservice_dev/LineBotWebService?wsdl");
			boolean result = service.sendByteMessage(channelAccessToken, lineUserId, bytes);

			System.out.println("RESULT: " + (result ? "SUCCESS" : "NOT SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sentMessageWait() {

		try {
			StringBuilder messageBuild = new StringBuilder();
			messageBuild.append("📅	แจ้งขอใช้งาน ห้องประชุม 2   ผู้แจ้ง: สิทธิพล (เต๋า)\n");
			messageBuild.append("หัวข้อ: เตรียมงาน\n");
			messageBuild.append("วันที่: 14/08/2017   เวลา: 15:00 - 17:00\n");
			messageBuild.append("วันที่แจ้ง: 14/08/2017 15:11\n");

			String lineUserId = "Ud16e9b45d6a705bf45770e7eff4faabf";
			String channelAccessToken = "7+2jy6NLlZXCCv54z7ABiWxAlLAECOPe1hTHzvzcE6BehgvbmTy3ucxJmkA1Ovng8cvMgTn9HlgBFkYq2wzXpiHs8A8fnY7KbHjQajIDoCkKzlTqXymoz7Fnw+2oPQcKUvx5LdYXR3aaw9mGfQupvQdB04t89/1O/w1cDnyilFU=";

			/*
			 * Linebot JtracDeliveryWebServiceProxy s = new JtracDeliveryWebServiceProxy("http://localhost:8080/jtracdelivery_webservice/JtracDeliveryWebService?wsdl"); s.recivceMessage(lineUserId,
			 * message, channelAccessToken);
			 */

			// Send line message
			byte[] bytes = messageBuild.toString().getBytes("UTF-8");
			LineBotWebServiceProxy service = new LineBotWebServiceProxy("http://10.100.70.68/linebot_webservice_dev/LineBotWebService?wsdl");
			boolean result = service.sendByteMessage(channelAccessToken, lineUserId, bytes);

			System.out.println("RESULT: " + (result ? "SUCCESS" : "NOT SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sentMessageApprove() {
		try {
			StringBuilder messageBuild = new StringBuilder();
			messageBuild.append("✅	แจ้งอนุมัติใช้งาน ห้องประชุม 1   ผู้แจ้ง: พีรพล (โอ๋)\n");
			messageBuild.append("หัวข้อ: เตรียมงาน\n");
			messageBuild.append("วันที่: 14/08/2017   เวลา: 15:00 - 16:00\n");
			messageBuild.append("วันที่แจ้ง: 14/08/2017 14:35\n");

			String lineUserId = "Ud16e9b45d6a705bf45770e7eff4faabf";
			String channelAccessToken = "7+2jy6NLlZXCCv54z7ABiWxAlLAECOPe1hTHzvzcE6BehgvbmTy3ucxJmkA1Ovng8cvMgTn9HlgBFkYq2wzXpiHs8A8fnY7KbHjQajIDoCkKzlTqXymoz7Fnw+2oPQcKUvx5LdYXR3aaw9mGfQupvQdB04t89/1O/w1cDnyilFU=";

			/*
			 * Linebot JtracDeliveryWebServiceProxy s = new JtracDeliveryWebServiceProxy("http://localhost:8080/jtracdelivery_webservice/JtracDeliveryWebService?wsdl"); s.recivceMessage(lineUserId,
			 * message, channelAccessToken);
			 */

			// Send line message
			byte[] bytes = messageBuild.toString().getBytes("UTF-8");
			LineBotWebServiceProxy service = new LineBotWebServiceProxy("http://10.100.70.68/linebot_webservice_dev/LineBotWebService?wsdl");
			boolean result = service.sendByteMessage(channelAccessToken, lineUserId, bytes);

			System.out.println("RESULT: " + (result ? "SUCCESS" : "NOT SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sentMessageCheckIn() {
		try {
			StringBuilder messageBuild = new StringBuilder();
			messageBuild.append("📥	แจ้งเข้าใช้งาน ห้องประชุม 1   ผู้แจ้ง: สิทธิพล (เต๋า)\n");
			messageBuild.append("หัวข้อ: เตรียมงาน\n");
			messageBuild.append("วันที่: 14/08/2017   เวลา: 15:00 - 16:00\n");
			messageBuild.append("วันที่แจ้ง: 14/08/2017 15:15\n");

			String lineUserId = "Ud16e9b45d6a705bf45770e7eff4faabf";
			String channelAccessToken = "7+2jy6NLlZXCCv54z7ABiWxAlLAECOPe1hTHzvzcE6BehgvbmTy3ucxJmkA1Ovng8cvMgTn9HlgBFkYq2wzXpiHs8A8fnY7KbHjQajIDoCkKzlTqXymoz7Fnw+2oPQcKUvx5LdYXR3aaw9mGfQupvQdB04t89/1O/w1cDnyilFU=";

			/*
			 * Linebot JtracDeliveryWebServiceProxy s = new JtracDeliveryWebServiceProxy("http://localhost:8080/jtracdelivery_webservice/JtracDeliveryWebService?wsdl"); s.recivceMessage(lineUserId,
			 * message, channelAccessToken);
			 */

			// Send line message
			byte[] bytes = messageBuild.toString().getBytes("UTF-8");
			LineBotWebServiceProxy service = new LineBotWebServiceProxy("http://10.100.70.68/linebot_webservice_dev/LineBotWebService?wsdl");
			boolean result = service.sendByteMessage(channelAccessToken, lineUserId, bytes);

			System.out.println("RESULT: " + (result ? "SUCCESS" : "NOT SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sentMessageCheckOut() {
		try {
			StringBuilder messageBuild = new StringBuilder();
			messageBuild.append("📤	แจ้งคืนห้อง ห้องประชุม 2   ผู้แจ้ง: สิทธิพล (เต๋า)\n");
			messageBuild.append("หัวข้อ: เตรียมงาน\n");
			messageBuild.append("วันที่: 14/08/2017   เวลา: 15:00 - 15:18\n");
			messageBuild.append("วันที่แจ้ง: 14/08/2017 15:18\n");

			String lineUserId = "Ud16e9b45d6a705bf45770e7eff4faabf";
			String channelAccessToken = "7+2jy6NLlZXCCv54z7ABiWxAlLAECOPe1hTHzvzcE6BehgvbmTy3ucxJmkA1Ovng8cvMgTn9HlgBFkYq2wzXpiHs8A8fnY7KbHjQajIDoCkKzlTqXymoz7Fnw+2oPQcKUvx5LdYXR3aaw9mGfQupvQdB04t89/1O/w1cDnyilFU=";

			/*
			 * Linebot JtracDeliveryWebServiceProxy s = new JtracDeliveryWebServiceProxy("http://localhost:8080/jtracdelivery_webservice/JtracDeliveryWebService?wsdl"); s.recivceMessage(lineUserId,
			 * message, channelAccessToken);
			 */

			// Send line message
			byte[] bytes = messageBuild.toString().getBytes("UTF-8");
			LineBotWebServiceProxy service = new LineBotWebServiceProxy("http://10.100.70.68/linebot_webservice_dev/LineBotWebService?wsdl");
			boolean result = service.sendByteMessage(channelAccessToken, lineUserId, bytes);

			System.out.println("RESULT: " + (result ? "SUCCESS" : "NOT SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sentMessageCancel() {
		try {
			StringBuilder messageBuild = new StringBuilder();
			
			messageBuild.append("❎	แจ้งยกเลิก ห้องประชุม 1   ผู้แจ้ง: สิทธิพล (เต๋า)\n");
			messageBuild.append("หัวข้อ: เตรียมงาน\n");
			messageBuild.append("วันที่: 14/08/2017   เวลา: 15:30 - 17:00\n");
			messageBuild.append("วันที่แจ้ง: 14/08/2017 15:40\n");

			String lineUserId = "Ud16e9b45d6a705bf45770e7eff4faabf";
			String channelAccessToken = "7+2jy6NLlZXCCv54z7ABiWxAlLAECOPe1hTHzvzcE6BehgvbmTy3ucxJmkA1Ovng8cvMgTn9HlgBFkYq2wzXpiHs8A8fnY7KbHjQajIDoCkKzlTqXymoz7Fnw+2oPQcKUvx5LdYXR3aaw9mGfQupvQdB04t89/1O/w1cDnyilFU=";

			/*
			 * Linebot JtracDeliveryWebServiceProxy s = new JtracDeliveryWebServiceProxy("http://localhost:8080/jtracdelivery_webservice/JtracDeliveryWebService?wsdl"); s.recivceMessage(lineUserId,
			 * message, channelAccessToken);
			 */

			// Send line message
			byte[] bytes = messageBuild.toString().getBytes("UTF-8");
			LineBotWebServiceProxy service = new LineBotWebServiceProxy("http://10.100.70.68/linebot_webservice_dev/LineBotWebService?wsdl");
			boolean result = service.sendByteMessage(channelAccessToken, lineUserId, bytes);

			System.out.println("RESULT: " + (result ? "SUCCESS" : "NOT SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sentMessageNotCheckIn() {
		try {
			StringBuilder messageBuild = new StringBuilder();
			messageBuild.append("🕗	แจ้งไม่มีการใช้งาน ห้องประชุม 1   ผู้แจ้ง: อัตโนมัติ\n");
			messageBuild.append("หัวข้อ: เตรียมงาน\n");
			messageBuild.append("วันที่: 14/08/2017   เวลา: 15:00 - 16:00\n");
			messageBuild.append("วันที่แจ้ง: 14/08/2017 15:15\n");

			String lineUserId = "Ud16e9b45d6a705bf45770e7eff4faabf";
			String channelAccessToken = "7+2jy6NLlZXCCv54z7ABiWxAlLAECOPe1hTHzvzcE6BehgvbmTy3ucxJmkA1Ovng8cvMgTn9HlgBFkYq2wzXpiHs8A8fnY7KbHjQajIDoCkKzlTqXymoz7Fnw+2oPQcKUvx5LdYXR3aaw9mGfQupvQdB04t89/1O/w1cDnyilFU=";

			/*
			 * Linebot JtracDeliveryWebServiceProxy s = new JtracDeliveryWebServiceProxy("http://localhost:8080/jtracdelivery_webservice/JtracDeliveryWebService?wsdl"); s.recivceMessage(lineUserId,
			 * message, channelAccessToken);
			 */

			// Send line message
			byte[] bytes = messageBuild.toString().getBytes("UTF-8");
			LineBotWebServiceProxy service = new LineBotWebServiceProxy("http://10.100.70.68/linebot_webservice_dev/LineBotWebService?wsdl");
			boolean result = service.sendByteMessage(channelAccessToken, lineUserId, bytes);

			System.out.println("RESULT: " + (result ? "SUCCESS" : "NOT SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sentMessageNotCheckOut() {
		try {
			StringBuilder messageBuild = new StringBuilder();
			messageBuild.append("🕓	แจ้งไม่มีการคืนห้อง ห้องประชุม 2   ผู้แจ้ง: อัตโนมัติ\n");
			messageBuild.append("หัวข้อ: เตรียมงาน\n");
			messageBuild.append("วันที่: 14/08/2017   เวลา: 15:00 - 15:18\n");
			messageBuild.append("วันที่แจ้ง: 14/08/2017 15:18\n");

			String lineUserId = "Ud16e9b45d6a705bf45770e7eff4faabf";
			String channelAccessToken = "7+2jy6NLlZXCCv54z7ABiWxAlLAECOPe1hTHzvzcE6BehgvbmTy3ucxJmkA1Ovng8cvMgTn9HlgBFkYq2wzXpiHs8A8fnY7KbHjQajIDoCkKzlTqXymoz7Fnw+2oPQcKUvx5LdYXR3aaw9mGfQupvQdB04t89/1O/w1cDnyilFU=";

			/*
			 * Linebot JtracDeliveryWebServiceProxy s = new JtracDeliveryWebServiceProxy("http://localhost:8080/jtracdelivery_webservice/JtracDeliveryWebService?wsdl"); s.recivceMessage(lineUserId,
			 * message, channelAccessToken);
			 */

			// Send line message
			byte[] bytes = messageBuild.toString().getBytes("UTF-8");
			LineBotWebServiceProxy service = new LineBotWebServiceProxy("http://10.100.70.68/linebot_webservice_dev/LineBotWebService?wsdl");
			boolean result = service.sendByteMessage(channelAccessToken, lineUserId, bytes);

			System.out.println("RESULT: " + (result ? "SUCCESS" : "NOT SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
