package com.cct.inhouse.bkonline.thread.checktimeinout;

import org.apache.log4j.Logger;

import com.cct.inhouse.bkonline.core.notification.service.NotificationManager;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class CheckTimeInOutThread extends Thread {

	private Logger logger = LogUtil.CHECK_TIME_IN_OUT;
	
	@Override
	public void run() {
		
		while(true) {
			getLogger().info("Check...[start]");
			
			CCTConnection conn = null;			
			try {
				conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
				
				// ตรวจสอบการ CheckIn
				NotificationManager notificationManager = new NotificationManager(getLogger());
				notificationManager.addNotCheckIn(conn);
			} catch (InterruptedException ie) {
				menageInterrupted(ie);
				break;
			} catch (Exception e) {
				menageException(e);
			} finally {
				CCTConnectionUtil.close(conn);
			}

			
			try {
				Thread.sleep(30000);
			} catch (InterruptedException ie) {
				menageInterrupted(ie);
				break;
			} catch (Exception e) {
				menageException(e);
			}
			
			try {
				conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
				
				// ตรวจสอบการ CheckOut
				NotificationManager notificationManager = new NotificationManager(getLogger());
				notificationManager.addNotCheckOut(conn);
			} catch (InterruptedException ie) {
				menageInterrupted(ie);
				break;
			} catch (Exception e) {
				menageException(e);
			} finally {
				CCTConnectionUtil.close(conn);
			}
			
			try {
				Thread.sleep(30000);
			} catch (InterruptedException ie) {
				menageInterrupted(ie);
				break;
			} catch (Exception e) {
				menageException(e);
			}
		}
		
		getLogger().info("Exit while loop.....");	
	}

	public void menageInterrupted(Exception e) {
		getLogger().info("Checked...[end]");	
	}
	
	public void menageException(Exception e) {
		getLogger().error("Checked...[error], Wait for continue...", e);	
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
