package util.mq;

import java.util.List;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

public class test {

	/**
	 * @param args
	 */
//	private static String mqHostName="10.100.10.46";
//	private static String mqUserID="Admin";
//	//private static String mqChannelName="SOMAPA.SVR";
//	private static String mqChannelName="SYSTEM.ADMIN.SVRCONN";
//
//	private static String qManagerName="APPMQ";
//	private static String qName = "APP.IN.SIMULATE";

	private static String mqHostName="10.100.70.230";
	private static String mqUserID="Phronphun";
	//private static String mqChannelName="SOMAPA.SVR";
//	private static String mqChannelName="SYSTEM.ADMIN.SVRCONN";
	private static String mqChannelName="SYSTEM.DEF.SVRCONN";

	private static String qManagerName="THAPP1";
	private static String qName = "AO.TEST";

	static MQQueueManager mQMgr;

	public static void main(String[] args) {
		try{
			MQUtil mqUtil = new MQUtil(mqHostName, mqUserID, mqChannelName, qManagerName, qName,2414);
			mQMgr = mqUtil.connectRemote();

			mqUtil.getMessage(mQMgr, qName, "e://pathlog/");

			//mqUtil.deleteMessage(mQMgr,1,1);

//			List<String> lstStr = mqUtil.getAllMessagesFromQueue(mQMgr, qName);
//			for(String msg : lstStr){
//				System.out.println("message : "+msg);
//			}

			System.out.println("pass");



		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(mQMgr != null) {
				try {
					mQMgr.disconnect();
				} catch (MQException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
