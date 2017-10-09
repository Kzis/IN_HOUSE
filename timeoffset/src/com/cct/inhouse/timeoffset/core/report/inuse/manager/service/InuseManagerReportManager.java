package com.cct.inhouse.timeoffset.core.report.inuse.manager.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractManager;
import com.cct.exception.MaxExceedException;
import com.cct.exception.MaxExceedReportException;
import com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServiceProxy;
import com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReport;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportModel;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearch;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearchCriteria;

public class InuseManagerReportManager extends AbstractManager<InuseManagerReportSearchCriteria, InuseManagerReportSearch, InuseManagerReport, InhouseUser, Locale> {

	private InuseManagerReportService service = null;

	public InuseManagerReportManager(Logger logger, InhouseUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new InuseManagerReportService(logger, user, locale);
	}


	public XSSFWorkbook export(CCTConnection conn, InuseManagerReportSearchCriteria criteria) throws Exception {

		List<InuseManagerReportSearch> listResult = new ArrayList<InuseManagerReportSearch>();

		try {

			criteria.setTotalResult(service.countData(conn,criteria));

			if (criteria.getTotalResult() == 0) {
				return null;
			} else if ((criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceedReport())) {
				throw new MaxExceedReportException();
			} else {

				// ค้นหาข้อมูล
				listResult = service.searchReport(conn,criteria);

				if (listResult != null) {
					return service.exportReport(criteria, listResult);
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return null;
	}

	WorkOnsite[] arrWorkOnSite = null;

	public void synData(CCTConnection conn,InuseManagerReportModel model) throws Exception {
		try{
	
			// processDate = ค้นหาข้อมูลเวลาประมวลผลล่าสุด ของ User นั้นๆ ที่สถานะประมวลผลสำเร็จ
			String processDateUser = service.searchProcessReqDateTimeUser(conn,model.getReport().getId());
	
			// processDateAll = ค้นหาข้อมูลเวลาประมวลผลล่าสุด ของ Background ที่สถานะประมวลผลสำเร็จ
			String processDateAll = service.searchProcessReqDateTimeAll(conn);
			
			// processDate = เวลาที่ประมวลผลล่าสุดที่เปรียบเทียบระหว่าง USER กับ ALL เรียบร้อยแล้ว
			String processDateLast = manageProcessDate(processDateUser,processDateAll);
			
			String processDate = null;
			
			if (processDateLast != null && !(processDateLast.isEmpty())) {
				DateFormat sdfDate = new SimpleDateFormat(GlobalVariableTO.FORMATYYYYMMDDHHMMSS, Locale.ENGLISH);
				DateFormat df = new SimpleDateFormat(GlobalVariableTO.FORMAT_DD_MM_YYYY_HHMMSS, Locale.ENGLISH);
				Date now = new Date();
				now = df.parse(processDateLast);
				processDate = sdfDate.format(now);
			} else {
				processDate = null;
			}
			
			// lastWsProcessId = บันทึก Log การ Call web service
			int lastWsProcessId = service.insertLogCallWS(conn,model.getReport().getId());
			getLogger().info("lastWsProcessId: " + lastWsProcessId);
			
			// Call Web service
			arrWorkOnSite = callWebService(conn,processDate, model.getReport().getId(), lastWsProcessId);
	
			// Update ข้อมูลการลงเวลาชดเชย
			if (arrWorkOnSite != null && arrWorkOnSite.length > 0) {
				updateDate(conn,arrWorkOnSite);
			}
	
		}catch(Exception e){
			throw e;
		}
	}
	

	private String manageProcessDate(String processDateUser, String processDateAll) throws Exception {
					
		return compareProcessDate(processDateUser,processDateAll);
	}


	private String compareProcessDate(String processDateUser,String processDateAll) throws Exception {

		if ( (processDateUser != null && !(processDateUser.isEmpty() ) )
				&& (processDateAll != null && !(processDateAll.isEmpty() ) ) ) {
			
			SimpleDateFormat format = new SimpleDateFormat(GlobalVariableTO.FORMAT_DD_MM_YYYY_HHMMSS_S);
			
			Date dateUser = format.parse(processDateUser);
			Date dateAll = format.parse(processDateAll);
			
			
			if (dateAll.compareTo(dateUser) <= 0) {
				
				//dateAll <= dateUser = dateUser
				return processDateUser;
				 
			}else{
				
				//dateAll > dateUser = dateAll
				return processDateAll;
				
			}
			
		}else{
			
			if(processDateUser == null && processDateAll != null){
				return processDateAll;
			}
			
			return null;
		}
		
	}


	private void updateDate(CCTConnection conn, WorkOnsite[] arrWorkOnSite) throws Exception {
		
		int exec = 0;
		
		try{
					
			for (int i = 0; i < arrWorkOnSite.length; i++) {
				exec = service.updateWorkOnSite(conn,arrWorkOnSite[i]);

				if (exec == 0) {
					exec = service.insertWorkOnSite(conn,arrWorkOnSite[i]);
				}
			}
		}catch (Exception e){
			throw e;
		}
		
	}

	private WorkOnsite[] callWebService(CCTConnection conn, String processDate, String id, int lastWsProcessId) throws Exception {

		WorkOnsite[] arrWorkOnSite = null;
		int sizeArrWorkOnSite = 0;

		try {
			
			if(processDate != null && id != null){
				// Call webservice
				GetTimeOffsetWebServiceProxy serviceWs = new GetTimeOffsetWebServiceProxy();
				arrWorkOnSite = serviceWs.getTimeOffset(processDate, id);

				if (arrWorkOnSite != null) {
					sizeArrWorkOnSite = arrWorkOnSite.length;
				}

				// Update Log การ Call web service สำเร็จ
				service.updateLogCallWS(conn,GlobalVariableTO.CALL_WS_SUCCESS, null, sizeArrWorkOnSite, lastWsProcessId);

			}else{
				// Update Log การ Call web service ไม่สำเร็จ
				service.updateLogCallWS(conn,GlobalVariableTO.CALL_WS_UNSUCCESS, "ProcessDate or User_Id is null", sizeArrWorkOnSite, lastWsProcessId);
			}
			
		} catch (Exception e2) {
			// Update Log การ Call web service ไม่สำเร็จ
			service.updateLogCallWS(conn,GlobalVariableTO.CALL_WS_UNSUCCESS, e2.getMessage().toString(), sizeArrWorkOnSite, lastWsProcessId);
		}

		return arrWorkOnSite;
	}

	@Override
	public List<InuseManagerReportSearch> search(CCTConnection conn,InuseManagerReportSearchCriteria criteria) throws Exception {
		
		List<InuseManagerReportSearch> listResult = new ArrayList<InuseManagerReportSearch>();

		try {

			criteria.setTotalResult(service.countData(conn,criteria));

			if (criteria.getTotalResult() == 0) {

			} else if ((criteria.isCheckMaxExceed()) && (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceed())) {
				throw new MaxExceedException();
			} else {
				// ค้นหาข้อมูล
				listResult = service.search(conn, criteria);
			}

		} catch (Exception e) {
			throw e;
		}

		return listResult;
	}

	@Override
	public InuseManagerReport searchById(CCTConnection conn, String id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long add(CCTConnection conn, InuseManagerReport obj)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(CCTConnection conn, InuseManagerReport obj)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(CCTConnection conn, String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateActive(CCTConnection conn, String ids, String activeFlag)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
