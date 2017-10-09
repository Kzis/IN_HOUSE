package test;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;

import com.cct.domain.GlobalVariable;
import com.cct.getworkonsite.core.config.parameter.domain.DBLookup;
import com.cct.getworkonsite.core.config.parameter.domain.ParameterConfig;
import com.cct.getworkonsite.core.config.parameter.service.ParameterManager;
import com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServiceProxy;
import com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite;

public class TestSynFuncCallWebService {

	public static void main(String[] args) {

//		String userId = null;
		String userId = "218";
		try {
			ParameterManager parameterManager = new ParameterManager();
			parameterManager.load(GlobalVariable.CONFIG_PARAMETER_FILE);
			
			TestSynFuncCallWebService service = new TestSynFuncCallWebService();
			service.process(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void process(String userId) {

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

//			String processDate = searchLastProcessDateByUser(conn, userId);
//			String processDate = "20170101000000";
			String processDate = "2560-09-14 11:32:59";
			
			// 2. call webservice get work onsite from HRM
			WorkOnsite[] arrWorkOnSite = callWebService(conn, processDate, userId);

			System.out.println("data size: " + arrWorkOnSite.length);
			// 3. check arrWorkOnSite for insert/update to database
			if (arrWorkOnSite != null && arrWorkOnSite.length > 0) {
				updateData(conn, arrWorkOnSite);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CCTConnectionUtil.close(conn);
		}

	}

	private String searchLastProcessDateByUser(CCTConnection conn, String userId) throws Exception {
		String processDate = null;

		String sql = "SELECT MAX(PROCESS_REQ_DATETIME) AS PROCESS_DATE FROM to_last_ws_process WHERE PROCESS_STATUS = 'Y' AND PROCESS_NAME = 'GET_WORK_ONSITE_BY_USER' AND USER_ID = "
				+ userId;

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
				if (rst.getString("PROCESS_DATE") != null) {
					String oldFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
					Locale locale = ParameterConfig.getParameter().getApplication().getApplicationLocale();
					String newFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();

					SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, locale);
					Date d = sdf.parse(rst.getString("PROCESS_DATE"));
					sdf.applyPattern(newFormat);
					processDate = sdf.format(d);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return processDate;
	}

	private WorkOnsite[] callWebService(CCTConnection conn, String processDate, String userId) throws Exception {

		WorkOnsite[] arrWorkOnSite = null;
		int sizeArrWorkOnSite = 0;
		try {
			int lastWsProcessId = insertLog(conn, userId);
			LogUtil.GET_WORK_ONSITE.info("lastWsProcessId: " + lastWsProcessId);

			try {
				GetTimeOffsetWebServiceProxy service = new GetTimeOffsetWebServiceProxy();
				arrWorkOnSite = service.getTimeOffset(processDate, userId);

				if (arrWorkOnSite != null) {
					sizeArrWorkOnSite = arrWorkOnSite.length;
				}

				updateLog(conn, GlobalVariable.PROCESS_STATUS_Y, null, sizeArrWorkOnSite, lastWsProcessId);
			} catch (RemoteException e1) {
				updateLog(conn, GlobalVariable.PROCESS_STATUS_N, e1.getMessage().toString(), sizeArrWorkOnSite, lastWsProcessId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return arrWorkOnSite;
	}

	private int insertLog(CCTConnection conn, String userId) throws Exception {

		int lastWsProcessId = 0;

		String sql = "INSERT INTO to_last_ws_process( PROCESS_NAME, PROCESS_STATUS, USER_ID, CREATE_USER) " + "VALUES ( 'GET_WORK_ONSITE_BY_USER', 'W', "+userId+", 34 )";

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rst = stmt.getGeneratedKeys();
			if (rst.next()) {
				lastWsProcessId = rst.getInt(1);
			}
		} catch (Exception e) {
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return lastWsProcessId;
	}

	protected void updateLog(CCTConnection conn, String processStatus, String msgDesc, int sizeArrWorkOnSite, int lastWsProcessId) throws Exception {

		String sql = "UPDATE to_last_ws_process SET PROCESS_STATUS = '" + processStatus + "', PROCESS_STATUS_DESC = '" + msgDesc + "', TOTAL_RECORD = " + sizeArrWorkOnSite
				+ ", UPDATE_USER = 34 WHERE LAST_WS_PROCESS_ID = " + lastWsProcessId;

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
	}

	private void updateData(CCTConnection conn, WorkOnsite[] workOnsite) throws Exception {

		int exec = 0;
		try {
			int no = 1;
			for (int i = 0; i < workOnsite.length; i++) {
				System.out.println(no + ". " + workOnsite[i].getOnsiteid());
				exec = updateWorkOnSite(conn, workOnsite[i]);

				System.out.println("exec update:  " + workOnsite[i].getOnsiteid());
				if (exec == 0) {
					exec = insertWorkOnSite(conn, workOnsite[i]);
					System.out.println("exec insert:  " + workOnsite[i].getOnsiteid());
				}
				
				no++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected int updateWorkOnSite(CCTConnection conn, WorkOnsite workOnsite) throws Exception {

		int exec = 0;

		String sql = "UPDATE to_workonsite_hrm SET"
				+ " USERID = " + workOnsite.getUserid()
				+ " , EMPLOYEEID = " + workOnsite.getEmployeeid()
				+ " , FIRSTNAMETHA = '"+workOnsite.getFirstnametha()+ "' "
				+ " , LASTNAMETHA = '"+workOnsite.getLastnametha()+"' "
				+ " , NICKNAME = '"+workOnsite.getNickname()+"' "
				+ " , WORKONSITEID = '"+workOnsite.getWorkonsiteid()+"'"
				+ " , ONSITEDATEFROM1 = '"+workOnsite.getOnsitedatefrom1()+"'"
				+ " , ONSITETIMEFROM1 = '"+workOnsite.getOnsitetimefrom1()+"'"
				+ " , ONSITEDATETO2 = '"+workOnsite.getOnsitedateto2()+"'"
			  + " , ONSITETIMETO2 = '"+workOnsite.getOnsitetimeto2()+"'"
			  + " , TOTAL_ONSITEDAY = " + workOnsite.getTotalOnsiteday()
			  + " , TOTAL_ONSITETIME = " + workOnsite.getTotalOnsitetime()
			  + " , SITESERVICE = '"+workOnsite.getSiteservice()+"'"
			  + " , ONSITESTATUS = '"+workOnsite.getOnsitestatus()+"'"
			  + " , APPROVETS = '"+workOnsite.getApprovets()+"'"
			  + " , SITESERVICE_REMARK = '"+workOnsite.getSiteserviceRemark()+"'"
			  + " , ONSITESTATUS_REMARK = '"+workOnsite.getOnsitestatusRemark()+"'"
			  + " , APPROVERID = " + workOnsite.getApproverid()
			  + " , UPDATE_USER = 0"
			  + " , UPDATE_DATE = CURRENT_TIMESTAMP"
			  + " WHERE ONSITEID = " + workOnsite.getOnsiteid()
			  + " AND USERID = " + workOnsite.getUserid();

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			exec = stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}

		return exec;
	}

	protected int insertWorkOnSite(CCTConnection conn, WorkOnsite workOnsite) throws Exception {

		int exec = 0;

		String sql = "INSERT INTO to_workonsite_hrm ("
				+ " ONSITEID"
				+ " , USERID"
				+ " , EMPLOYEEID"
				+ " , FIRSTNAMETHA"
				+ " , LASTNAMETHA"
				+ " , NICKNAME"
				+ " , WORKONSITEID"
				+ " , ONSITEDATEFROM1"
				+ " , ONSITETIMEFROM1"
				+ " , ONSITEDATETO2"
				+ " , ONSITETIMETO2"
				+ " , TOTAL_ONSITEDAY"
				+ " , TOTAL_ONSITETIME"
				+ " , SITESERVICE"
				+ " , ONSITESTATUS"
				+ " , APPROVETS"
				+ " , SITESERVICE_REMARK"
				+ " , ONSITESTATUS_REMARK"
				+ " , APPROVERID"
				+ " , CREATE_USER"
				+ " , CREATE_DATE)" 
				+ " VALUES ("
				+ " '" + workOnsite.getOnsiteid() + "'"
				+ ", '" + workOnsite.getUserid() + "'"
				+ ", '" + workOnsite.getEmployeeid() + "'"
				+ ", '" + workOnsite.getFirstnametha() + "'"
				+ ", '" + workOnsite.getLastnametha() + "'"
				+ ", '" + workOnsite.getNickname() + "'"
				+ ", '" + workOnsite.getWorkonsiteid() + "'"
				+ ", '" + workOnsite.getOnsitedatefrom1() + "'"
				+ ", '" + workOnsite.getOnsitetimefrom1() + "'"
				+ ", '" + workOnsite.getOnsitedateto2() + "'"
				+ ", '" + workOnsite.getOnsitetimeto2() + "'"
				+ ", '" + workOnsite.getTotalOnsiteday() + "'"
				+ ", '" + workOnsite.getTotalOnsitetime() + "'"
				+ ", '" + workOnsite.getSiteservice() + "'"
				+ ", '" + workOnsite.getOnsitestatus() + "'"
				+ ", '" + workOnsite.getApprovets() + "'"
				+ ", '" + workOnsite.getSiteserviceRemark() + "'"
				+ ", '" + workOnsite.getOnsitestatusRemark() + "'"
				+ ", '" + workOnsite.getApproverid() + "'"
				+ ", 0"
				+ ", CURRENT_TIMESTAMP)";

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			exec = stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}

		return exec;
	}

}
