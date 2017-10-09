/**
 * สำหรับ  connect remote Server
 * @author : SD
 * @version : 1.0
 *
 */
package util.scp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * Class การทำงานส่วน connect Remote Server
 */
public class SCPUtil {
	private Connection connection = null;

	/**
	 * Detail : The command use for find tomcat process id.
	 */
	public final String findTomcatProcess = "ps -ef |grep tomcat | grep -v grep | awk '{print $2}'";

	/**
	 * Class การทำงาน Connect Server.
	 * <p>
	 * [Example : scp = new SCPUtil("root","1111","22")]
	 *
	 * @param host
	 * @param username
	 * @param password
	 * @param port
	 * @throws Exception
	 */
	public SCPUtil(String host,String username, String password,int port)throws Exception{

		try{
			connection = new Connection(host, port);
			connection.connect();
			connection.authenticateWithPassword(username, password);
			System.out.println("Get connection success..");

		}catch (Exception e) {
			System.out.println("Authentication failed.");
			throw e;
		}
	}

	/**
	 * Connect Server (ด้วย Key File).
	 * <p>
	 * [Example : scp = new SCPUtil("root","1111","22",file,"filepass")]
	 *
	 * @param host
	 * @param username
	 * @param password
	 * @param port
	 * @param keyfile
	 * @param keyfilePass
	 * @throws Exception
	 */
	public SCPUtil(String host,String username, String password,int port,File keyfile,String keyfilePass)throws Exception{
		try{
			connection = new Connection(host, port);
			connection.connect();

			boolean isAuthenticated = connection.authenticateWithPublicKey(username, keyfile, keyfilePass);

			if (isAuthenticated == false){
				System.out.println("Authentication failed.");
				throw new IOException("Authentication failed.");
			}else{
				System.out.println("Get connection success..");
			}


		}catch (Exception e) {
			System.out.println("Authentication failed.");
			throw e;
		}

	}

	/**
	 * สั่งทำงาน run command
	 * <p>
	 * [Example : excuteCommand("ls /opt/tomcat/logs/catalina.out")]
	 *
	 * @param connection
	 * @param cmd
	 * @return boolean
	 * @throws Exception
	 */
	public boolean excuteCommand(String cmd)throws Exception{
		if(connection==null){
			return false;
		}

		boolean success = true;
		Session session = null;

		if(null == cmd || cmd.equals("")){
			return success;
		}
		try {
			System.out.println("Command : "+cmd);
			session = connection.openSession();
			session.execCommand(cmd);
		} catch (Exception e) {
			success = false;
			throw e;
		} finally {
			/* Close this session */
			session.close();
			return success;
		}
	}

	/**
	 * สั่งทำงาน  run command return String command .
	 * <p>
	 * [Example : excuteCommandConsole("ls /opt/tomcat/logs/catalina.out")]
	 *
	 * @param connection
	 * @param cmd
	 * @return String
	 * @throws Exception
	 */
	public String excuteCommandConsole(String cmd)throws Exception{
		if(connection==null){
			return "Authentication failed.";
		}
		String console = "";
		Session session = null;
		if(null == cmd || cmd.equals("")){
			return console;
		}
		try {
			System.out.println("Command : "+cmd);
			session = connection.openSession();
			session.execCommand(cmd);
			InputStream stdout = new StreamGobbler(session.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				console += line;
			}


		} catch (Exception e) {
			throw e;
		} finally {
			/* Close this session */
			session.close();
			return console;
		}
	}

	/**
	 * Upload file ขึ้น Server
	 * <p>
	 * [Example : uploadFile("/opt/upload/")]
	 *
	 *
	 * @param connection
	 * @param localFile
	 * @param remoteTargetDirectory
	 * @return
	 * @throws Exception
	 */
	public boolean uploadFile(String localFile,String remoteTargetDirectory)throws Exception{
		if(connection==null){
			return false;
		}
		boolean success = false;
		try{
			SCPClient client = connection.createSCPClient();
			client.put(localFile,remoteTargetDirectory);
			success = true;
		}catch (Exception e) {
			throw e;
		}
		return success;
	}

	/**
	 * Upload file ขึ้น Server พร้อม Set Mode
	 * <p>
	 * [Example : uploadFile("/opt/upload/file.txt","c:/upload","0755")]
	 *
	 * @param localFile
	 * @param remoteTargetDirectory
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean uploadFile(String localFile,String remoteTargetDirectory,String mode)throws Exception{
		if(connection==null){
			return false;
		}
		boolean success = false;
		try{
			SCPClient client = connection.createSCPClient();
			client.put(localFile, remoteTargetDirectory,mode);
			success = true;
		}catch (Exception e) {
			throw e;
		}
		return success;
	}

	/**
	 * Copy File Server.
	 * <p>
	 * [Example : copyFile("opt/download/cfile.txt","/opt/upload/yfile.txt")]
	 *
	 * @param dirtory
	 * @param destination
	 * @return
	 * @throws Exception
	 */
	public boolean copyFile(String dirtory, String destination)throws Exception{

		if(connection==null){
			return false;
		}
		boolean success = true;
		Session session = null;
		try{
			//cp -r /home/hope/files/* /home/hope/backup
			String cmd = "cp -r "+dirtory+" "+destination;
			System.out.println("Command : "+cmd);
			session = connection.openSession();
			session.execCommand(cmd);

		}catch (Exception e) {
			success = false;
			throw e;
		}
		return success;
	}

	/**
	 * Delete file Server.
	 * <p>
	 * [Example : removeFile("opt/download/yfile.txt"]
	 *
	 *
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean removeFile(String fileName)throws Exception{

		if(connection==null){
			return false;
		}
		boolean success = true;
		Session session = null;
		try{
			//rm combined.txt
			String cmd = "rm "+fileName;
			System.out.println("Command : "+cmd);
			session = connection.openSession();
			session.execCommand(cmd);

		}catch (Exception e) {
			success = false;
			throw e;
		}
		return success;
	}

	/**
	 * Detail : Delete Folder.
	 * <p>
	 *
	 *
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean removeDirectories(String directoryName)throws Exception{

		if(connection==null){
			return false;
		}
		boolean success = true;
		Session session = null;
		try{
			//rm combined.txt
			String cmd = "rmdir "+directoryName;
			System.out.println("Command : "+cmd);
			session = connection.openSession();
			session.execCommand(cmd);

		}catch (Exception e) {
			success = false;
			throw e;
		}
		return success;
	}

	public boolean downloadFile(String localFile,String remoteTargetDirectory)throws Exception{
		if(connection==null){
			return false;
		}
		boolean success = false;
		try{
			SCPClient client = connection.createSCPClient();
			client.get(remoteTargetDirectory,localFile);
			success = true;
		}catch (Exception e) {
			throw e;
		}
		return success;
	}

	/**
	 * Detail : Disconnect
	 */
	public void disConnect(){
		if(connection!=null){
			connection.close();
			System.out.println("disconnect");
		}
	}

}
