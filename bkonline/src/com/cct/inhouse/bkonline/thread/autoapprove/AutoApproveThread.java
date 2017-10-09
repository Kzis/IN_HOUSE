package com.cct.inhouse.bkonline.thread.autoapprove;

import org.apache.log4j.Logger;

import com.cct.inhouse.bkonline.core.autoapprove.service.AutoApproveManager;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class AutoApproveThread extends Thread {

	private Logger logger = LogUtil.AUTO_APPROVE;
	
	@Override
	public void run() {
		
		while(true) {
			getLogger().info("Auto...[start]");
			
			CCTConnection conn = null;			
			try {
				conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
				
				AutoApproveManager manager = new AutoApproveManager(getLogger());
				manager.updateBookingStatusApproveByAuto(conn);
				
			} catch (InterruptedException ie) {
				menageInterrupted(ie);
				break;
			} catch (Exception e) {
				menageException(e);
			} finally {
				CCTConnectionUtil.close(conn);
			}

			
			try {
				Thread.sleep(60000);
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
		getLogger().info("Auto...[end]");	
	}
	
	public void menageException(Exception e) {
		getLogger().error("Auto...[error], Wait for continue...", e);	
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
