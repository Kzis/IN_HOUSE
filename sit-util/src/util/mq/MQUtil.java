/**
 * การทำงานเกี่ยวกับข้อมูลส่วน MQ
 * @author : SD
 * @version : 1.0
 *
 */
package util.mq;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;



/**
 * Class การทำงานจัดการข้อมูล MQ
 */
public class MQUtil {

	/** The mq host name. */
	private String mqHostName;

	/** The mq user id. */
	private String mqUserID;

	/** The mq channel name. */
	private String mqChannelName;

	/** The q manager name. */
	private String qManagerName;

	/** The q name. */
	private String qName;

	private int qPort;

	/**
	 * Instantiates a new mQ util.
	 * <p>
	 * [Example : MQUtil mq = new MQUtil(hostname,userid,channelname,qmanagername,qname)]
	 *
	 * @param mqHostName : MQ Hostname
	 * @param mqUserID : MQ UserId
	 * @param mqChannelName : MQ MyChannel Name
	 * @param qManagerName : MQ QManager Name
	 * @param qName : QName
	 * @throws MQException
	 * @throws Exception
	 */
	public MQUtil(String mqHostName, String mqUserID, String mqChannelName,String qManagerName,String qName,int qPort) throws MQException, Exception {
		this.mqHostName = mqHostName;
		this.mqUserID = mqUserID;
		this.mqChannelName = mqChannelName;
		this.qManagerName = qManagerName;
		this.qName = qName;
		this.qPort = qPort;
	}


	 /**
 	 * Connect Remote Server.
 	 * <p>
 	 * [Example : MQQueueManager mq = connectRemote()]
 	 *
 	 * @return MQQueueManager
 	 * @throws MQException the mQ exception
 	 * @throws Exception the exception
 	 */
	public MQQueueManager connectRemote() throws MQException, Exception {
		MQEnvironment.hostname = this.mqHostName;
		MQEnvironment.userID = this.mqUserID;
		MQEnvironment.channel = this.mqChannelName;
		MQEnvironment.port = this.qPort;
		MQQueueManager qMgr = new MQQueueManager(this.qManagerName);
		return qMgr;
	}


	 /**
 	 * Connect Local.
 	 * <p>
 	 * [Example : MQQueueManager mq = connectLocal()]
 	 *
 	 * @return MQQueueManager
 	 * @throws MQException the mQ exception
 	 * @throws Exception the exception
 	 */
	public MQQueueManager connectLocal() throws MQException, Exception {
		MQQueueManager qMgr = new MQQueueManager(this.qManagerName);
		return qMgr;
	}

	public void putMessage(MQQueueManager qMgr, String qName, String message)
			throws MQException, Exception {

		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;

		// Now specify the queue that we wish to open,
		// and the open options...
		MQQueue system_default_local_queue = qMgr.accessQueue(qName,
				openOptions);

		// Define a simple WebSphere MQ message, and write some text in UTF
		// format..
		MQMessage mqMessage = new MQMessage();

		//mqMessage.CharacterSet = mqMessage;

		mqMessage.characterSet = 1208 ; //MQCCSI_Q_MGR
		mqMessage.writeString(message);
		//mqMessage.encoding

		// specify the message options...
		MQPutMessageOptions pmo = new MQPutMessageOptions();

		// same as MQPMO_DEFAULT
		// put the message on the queue
		system_default_local_queue.put(mqMessage, pmo);

	}


	/**
	 * อ่านข้อมูล queue ล่าสุดจาก  MQ.
	 * <p>
	 * [Example : String q = getMessages(qMgr)]
	 *
	 * @param qMgr : MQQueueManager
	 * @return messages queue
	 * @throws MQException the mQ exception
	 * @throws Exception the exception
	 */
	public String getMessages(MQQueueManager qMgr)throws MQException, Exception {
		int record = 0;
		MQQueue outputQueue = null;

		String result = "";
		try{
			int openInputOptions = MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;
			outputQueue = qMgr.accessQueue(qName, openInputOptions);
			MQMessage retrievedMessage = new MQMessage();

			MQGetMessageOptions gmo=new MQGetMessageOptions();
			gmo.options=MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
			gmo.matchOptions=MQC.MQMO_NONE;
			gmo.waitInterval=10000;

			while(true) {
				if(record > 0) {
					gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;
				}
				outputQueue.get(retrievedMessage, gmo);

				int strLen = retrievedMessage.getDataLength();
				byte[] strData = new byte[strLen];
				retrievedMessage.readFully(strData,0,strLen);
				String msg = new String(strData,"UTF8");

				result = msg;
				record++;
			}
		}catch(MQException mqe) {
			if(mqe.reasonCode == mqe.MQRC_NO_MSG_AVAILABLE) {
				return result;
			}
			mqe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(outputQueue != null) { outputQueue.close(); }
			}catch (MQException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * อ่านข้อมูล queue จาก Mq ตาม Index.
	 * <p>
	 * [Example : List lstqueue = getMessages(qMgr,0,10)]
	 *
	 * @param qMgr : MQQueueManager
	 * @param begin : index begin
	 * @param end : index end
	 * @return message(List)
	 * @throws MQException
	 * @throws Exception
	 */
	public List<String> getMessages(MQQueueManager qMgr,int begin,int end)throws MQException, Exception {
		int record = 0;
		MQQueue outputQueue = null;
		List<String> result = new ArrayList<String>();
		try{
			int openInputOptions = MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;
			outputQueue = qMgr.accessQueue(qName, openInputOptions);
			MQMessage retrievedMessage = new MQMessage();

			MQGetMessageOptions gmo=new MQGetMessageOptions();
			gmo.options=MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
			gmo.matchOptions=MQC.MQMO_NONE;
			gmo.waitInterval=10000;

			while(true) {
				if(record > 0) {
					gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;
				}
				outputQueue.get(retrievedMessage, gmo);

				int strLen = retrievedMessage.getDataLength();
				byte[] strData = new byte[strLen];
				retrievedMessage.readFully(strData,0,strLen);
				String msg = new String(strData,"UTF8");

				if(record >= begin && record <=end){
					result.add(msg);
				}
				record++;
			}
		}catch(MQException mqe) {
			if(mqe.reasonCode == mqe.MQRC_NO_MSG_AVAILABLE) {
				return result;
			}
			mqe.printStackTrace();

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(outputQueue != null) { outputQueue.close(); }
			}catch (MQException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}


	/**
	 * อ่านข้อมูล queue ทั้งหมดจาก MQ.
	 * <p>
	 * [Example : List lstmsg = getAllMessage(qMqr)]
	 *
	 * @param MQQueueManager
	 * @return message queue
	 * @throws MQException
	 * @throws Exception
	 */
	public List<String> getAllMessages(MQQueueManager qMgr )throws MQException, Exception {
		int record = 0;
		MQQueue outputQueue = null;
		List<String> result = new ArrayList<String>();
		try{
			int openInputOptions = MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;
			outputQueue = qMgr.accessQueue(qName, openInputOptions);
			MQMessage retrievedMessage = new MQMessage();

			MQGetMessageOptions gmo=new MQGetMessageOptions();
			gmo.options=MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
			gmo.matchOptions=MQC.MQMO_NONE;
			gmo.waitInterval=10000;
			while(true) {
				if(record > 0) {
					gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;
				}
				outputQueue.get(retrievedMessage, gmo);
				int strLen = retrievedMessage.getDataLength();
				byte[] strData = new byte[strLen];
				retrievedMessage.readFully(strData,0,strLen);
				String msg = new String(strData,"UTF8");

				result.add(msg);
				record++;
			}
		}catch(MQException mqe) {
			if(mqe.reasonCode == mqe.MQRC_NO_MSG_AVAILABLE) {
				return result;
			}
			mqe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(outputQueue != null) { outputQueue.close(); }
			}catch (MQException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * อ่านข้อมูล queue ทั้งหมดจาก MQ และลบข้อมูล.
	 * <p>
	 * [Example : List lst = getDeleteAllMessage(qMqr) ]
	 *
	 * @param qMgr the q mgr
	 * @return message queue
	 * @throws MQException
	 * @throws Exception
	 */
	public List<String> getDeleteAllMessages(MQQueueManager qMgr) throws MQException, Exception {
		int num = 1;
		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
		MQQueue queue = qMgr.accessQueue(qName, openOptions);
		List<String> messagelist = new ArrayList<String>();
		MQMessage messageBuffer = new MQMessage();
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		try {
			while (true) {
				queue.get(messageBuffer, gmo);

				int strLen = messageBuffer.getDataLength();
				byte[] strData = new byte[strLen];
				messageBuffer.readFully(strData,0,strLen);
				String msg = new String(strData,"UTF8");

				messagelist.add(msg);
				messageBuffer = new MQMessage();
			}
		} catch (MQException e) {
			if (e.reasonCode == 2033) {
				return messagelist;
			}
		} catch (IOException e) {
			System.out.print("IOException:");
			e.printStackTrace();
		}
		return messagelist;
	}

	/**
	 * อ่านข้อมูล queue แรกจาก MQ และลบข้อมูล.
	 * <p>
	 * [Example :  getDeleteMessage(qMqr)]
	 *
	 * @param qMgr the q mgr
	 * @return the delete messages
	 * @throws MQException the mQ exception
	 * @throws Exception the exception
	 */
	public String getDeleteMessages(MQQueueManager qMgr ) throws MQException, Exception {
		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
		MQQueue queue = qMgr.accessQueue(qName, openOptions);
		String msg = "";
		MQMessage messageBuffer = new MQMessage();
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		try {
			queue.get(messageBuffer, gmo);

			int strLen = messageBuffer.getDataLength();
			byte[] strData = new byte[strLen];
			messageBuffer.readFully(strData,0,strLen);
			msg = new String(strData,"UTF8");

			//messagelist.add(msg);
			messageBuffer = new MQMessage();

		} catch (MQException e) {
			if (e.reasonCode == 2033) {
				return msg;
			}
		} catch (IOException e) {
			System.out.print("IOException:");
			e.printStackTrace();
		}
		return msg;
	}


	/*
	 * delete MQ from index to send
	 */
	public void deleteMessage(MQQueueManager qMgr, int indexBeginDel,int indexEndDel){
		MQQueue outputQueue = null;
		String msg = "";
		int c = 1;
		try {
			if(indexBeginDel == 0){
				indexBeginDel = indexEndDel;
			}
			if(indexEndDel == 0){
				indexEndDel = indexBeginDel;
			}

			//mQMgr = new MQQueueManager(remoteQManager);
			int openInputOptions = MQC.MQOO_FAIL_IF_QUIESCING |
			MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;

			outputQueue= qMgr.accessQueue(qName, openInputOptions);
			MQMessage retrievedMessage = new MQMessage();
			MQGetMessageOptions gmo=new MQGetMessageOptions();
			gmo.options=MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
			gmo.matchOptions=MQC.MQMO_NONE;
			gmo.waitInterval=10000;

			while(true) {
				if(c>0) { gmo.options = MQC.MQGMO_WAIT |MQC.MQGMO_BROWSE_NEXT; }
				outputQueue.get(retrievedMessage, gmo);
				msg=retrievedMessage.readString(retrievedMessage.getMessageLength());
				System.out.println("************************ message "+c+"********************** " + retrievedMessage.persistence);
				System.out.println("RETRIEVED MESSAGE: "+msg);
				System.out.println("REMOVING..................................");
				if(indexBeginDel == 0 && indexEndDel == 0){
					if(indexBeginDel <= c && indexEndDel >= c){
						gmo.options = MQC.MQGMO_WAIT |MQC.MQGMO_MSG_UNDER_CURSOR;
						outputQueue.get(retrievedMessage, gmo);
					}
				}else{
					if(indexBeginDel <= c && indexEndDel >= c){
						gmo.options = MQC.MQGMO_WAIT |MQC.MQGMO_MSG_UNDER_CURSOR;
						outputQueue.get(retrievedMessage, gmo);
					}
				}
				c++;
			}
		} catch (MQException mqe) {
			if(mqe.reasonCode == mqe.MQRC_NO_MSG_AVAILABLE) {
				System.out.println("NO MORE MESSAGES AVAILABLE, RETRIEVED"+c);
				return;
			}
			mqe.printStackTrace();
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace(System.out);
			return;
		} finally {
			try {
			System.out.println("CLOSING A QUEUE & MANAGER");
			if(outputQueue != null) { outputQueue.close(); }
			if(qMgr != null) { qMgr.disconnect(); }
			}catch (MQException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void getMessage(MQQueueManager qMgr, String qName,String path)throws Exception{
		int record = 0;
		MQQueue outputQueue = null;

		FileWriter outFile = null;
		PrintWriter out = null;;

		try{
			File f = new File(path);
			if(createFolder(path)){

				String namefile = getDateTime();

				outFile = new FileWriter(path+"/"+namefile+".txt");
				out = new PrintWriter(outFile);

				int openInputOptions = MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;
				outputQueue = qMgr.accessQueue(qName, openInputOptions);
				MQMessage retrievedMessage = new MQMessage();

				MQGetMessageOptions gmo=new MQGetMessageOptions();
				gmo.options=MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
				gmo.matchOptions=MQC.MQMO_NONE;
				gmo.waitInterval=10000;

				while(true) {
					if(record > 0) { gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT; }

					//if(MQC.MQGMO_BROWSE_NEXT == 0){
					outputQueue.get(retrievedMessage, gmo);
					String msg=retrievedMessage.readString(retrievedMessage.getMessageLength());


					//result.add(msg);
					System.out.println("************************ message "+record+"********************** " + retrievedMessage.persistence);
					System.out.println("RETRIEVED MESSAGE: "+msg);

					out.println(msg);
					//gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_MSG_UNDER_CURSOR;
					//outputQueue.get(retrievedMessage, gmo);

					record++;

				}
			}

		}catch(MQException mqe) {
			if(mqe.reasonCode == mqe.MQRC_NO_MSG_AVAILABLE) {
				//System.out.println("NO MORE MESSAGES AVAILABLE, RETRIEVED"+record);
				return;
			}
			mqe.printStackTrace();
		}catch (java.io.IOException ioe) {
			ioe.printStackTrace(System.out);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				out.close();
				//System.out.println("CLOSING A QUEUE & MANAGER");
				if(outputQueue != null) { outputQueue.close(); }
			}catch (MQException ex) {
				ex.printStackTrace();
			}
		}


	}

	private static boolean createFolder(String folderPath) throws Exception{
		if(folderPath == null || folderPath.equals("")) return false;
		File c = new File(folderPath);
		if(!c.exists()){
			//System.out.println("new create");
			c.mkdirs();
		}
		return c.exists();
	}

	private static String getDateTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",new Locale("en","EN"));
		return format.format(new Date());
	}





}
