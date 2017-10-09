package com.cct.getworkonsite.bg.control;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.log4j.PropertyConfigurator;

import util.log.LogUtil;

import com.cct.domain.GlobalVariable;
import com.cct.getworkonsite.core.config.parameter.service.ParameterManager;
import com.cct.getworkonsite.core.gettimeoffset.service.GetWorkOnSiteManager;

public class GetWorkOnSiteMain {

	private static File f;
	private static FileChannel channel;
	private static FileLock lock;

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		PropertyConfigurator.configure(GlobalVariable.CONFIG_LOG4J_FILE);
		try {

			f = new File(GetWorkOnSiteMain.class.getName() + ".lock");

			// Try to get the lock
			channel = new RandomAccessFile(f, "rw").getChannel();
			lock = channel.tryLock();
			if (lock == null) {
				// File is lock by other application
				channel.close();
				throw new RuntimeException("Only 1 instance of " + GetWorkOnSiteMain.class.getName() + " can run.");
			}
			// Add shutdown hook to release lock when application shutdown
			ShutdownHook shutdownHook = new ShutdownHook();
			Runtime.getRuntime().addShutdownHook(shutdownHook);

			ParameterManager parameterManager = new ParameterManager();
			parameterManager.load(GlobalVariable.CONFIG_PARAMETER_FILE);

			GetWorkOnSiteManager manager = new GetWorkOnSiteManager();
			manager.process();
		} catch (Exception e) {
			LogUtil.GET_WORK_ONSITE.error("", e);
		}
	}

	public static void unlockFile() {
		// release and delete file lock
		try {
			if (lock != null) {
				lock.release();
				channel.close();
				f.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class ShutdownHook extends Thread {
		public void run() {
			unlockFile();
		}
	}
	
}
