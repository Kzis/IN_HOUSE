package com.cct.common;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cct.enums.Log4jFile;

import util.log.DefaultLoggerName;

public abstract class CommonMain {
	private String processName = initProcessName();
	private File f;
	private FileChannel channel;
	private FileLock lock;
	private Logger logger = Logger.getLogger(DefaultLoggerName.INITIAL.getValue());

	@SuppressWarnings("resource")
	public void start(String[] args) {
		
		// ตั้งค่า Log
		initLogConfig(args);
		
		Calendar startCalendar = Calendar.getInstance(Locale.ENGLISH);
		
		try {
			getLogger().debug("Start >");
			
			f = new File(getProcessName() + ".lock");
			/**
			 * Check if the lock exist
			 * sittipol.m comment if (f.exists()) { // if exist try to delete it f.delete(); }
			 */
			
			// Try to get the lock
			channel = new RandomAccessFile(f, "rw").getChannel();
			lock = channel.tryLock();
			if (lock == null) {
				// File is lock by other application
				channel.close();
				throw new RuntimeException("Only 1 instance of " + getProcessName() + " can run.");
			}
			
			// Add shutdown hook to release lock when application shutdown
			ShutdownHook shutdownHook = new ShutdownHook();
			Runtime.getRuntime().addShutdownHook(shutdownHook);

			/** process ****************************/
			try {
				process(args);
			} catch (Exception e) {
				getLogger().error(null, e);
			}
			/** process ****************************/

		} catch (Exception e) {
			getLogger().error("Could not start process.", e);
			
			getLogger().error(e);
		}
		
		Calendar endCalendar = Calendar.getInstance(Locale.ENGLISH);
		getLogger().info("Total process time: " + convertTime(endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis()));
	}
	
	/**
	 * ต้งค่า Log config
	 * @param args[0] = path config or app path
	 */
	public void initLogConfig(String[] args) {
		if ((args == null) || (args.length == 0)) {
			PropertyConfigurator.configure(Log4jFile.DEFAULT_FILENAME.getValue());
		} else if (args.length > 0) {
			PropertyConfigurator.configure(args[0] + Log4jFile.DEFAULT_FILENAME.getValue());
		}
	}
	
	/**
	 * กำหนดชื่อ Lock file สำหรับตรวจสอบการ run 1 instance เท่านั้น
	 */
	public abstract String initProcessName();
	
	/**
	 * ประมวลผล
	 * @param args[0] = path config or app path
	 */
	public abstract void process(String[] args);

	public String convertTime(long elapsed) {
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		return formatter.format(new Date(elapsed));
	}

	public void unlockFile() {
		// release and delete file lock
		if (lock != null) {
			try {
				lock.release();
			} catch (Exception e) {
			}
		}
		
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception e) {
			}
		}
		
		if (f != null) {
			try {
				f.delete();
			} catch (Exception e) {
			}
		}
	}

	class ShutdownHook extends Thread {
		public void run() {
			unlockFile();
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public String getProcessName() {
		return processName;
	}
}
