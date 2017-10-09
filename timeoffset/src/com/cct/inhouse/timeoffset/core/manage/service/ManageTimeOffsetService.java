package com.cct.inhouse.timeoffset.core.manage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.exception.DuplicateException;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffset;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearch;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;

public class ManageTimeOffsetService extends AbstractService {

	ManageTimeOffsetDAO dao = null;

	public ManageTimeOffsetService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new ManageTimeOffsetDAO(logger, SQLPath.TO_MANAGE.getSqlPath(), user, locale);
	}

	protected long countData(CCTConnection conn,  ManageTimeOffsetSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria);
	}

	protected List<ManageTimeOffsetSearch> search(CCTConnection conn, ManageTimeOffsetSearchCriteria criteria, String id) throws Exception {

		List<ManageTimeOffsetSearch> lstResult = new ArrayList<ManageTimeOffsetSearch>();

		try {
			
			//ค้นหาข้อมูล
			lstResult = dao.search(conn, criteria);
			
			//นำข้อมูล list ที่ได้ไปจัดการตาม Business
			lstResult = TOUtil.manageTimeTo(lstResult);
			
			//ค้นหาข้อมูล เวลาชดเชยคงเหลือ
			lstResult = dao.searchTO(conn, id , lstResult);
			
			if( !(lstResult.get(0).getTimeOffset().isEmpty()) && lstResult.get(0).getTimeOffset() != null ){
				if(Double.parseDouble(lstResult.get(0).getTimeOffset()) != 0.0){
					lstResult.get(0).setStyleColor(Double.parseDouble(lstResult.get(0).getTimeOffset()) > 0 ? GlobalVariableTO.COLOR_GREEN : GlobalVariableTO.COLOR_RED);
					lstResult.get(0).setTimeOffset(TOUtil.manageSearchTOUser(lstResult.get(0),GlobalVariableTO.TOSHOW));
				}else{
					lstResult.get(0).setTimeOffset(TOUtil.setDefaultTO());
					lstResult.get(0).setStyleColor(GlobalVariableTO.COLOR_BLUE);
				}
			}else{
				lstResult.get(0).setTimeOffset(TOUtil.setDefaultTO());
				lstResult.get(0).setStyleColor(GlobalVariableTO.COLOR_BLUE);
			}
			
		} catch (Exception e) {
			throw e;
		}

		return lstResult;
	}

	protected ManageTimeOffset searchById(CCTConnection conn, String id) throws Exception {
		return dao.searchById(conn, id);
	}

	protected void checkDup(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		dao.checkDup(conn, obj);
	}

	protected long addMaster(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		return dao.addMaster(conn, obj);
	}

	protected long add(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		return dao.add(conn, obj);
	}

	protected int edit(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		return dao.edit(conn, obj);
	}

	protected int editMaster(CCTConnection conn, long id, String processStatus) throws Exception {
		return dao.editMaster(conn, id, processStatus);
	}

	protected void editCheckDup(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		
		boolean dup = dao.editCheckDup(conn, obj, getUser(), getLocale());
		
		//หากข้อมูลซ้ำจะ แสดงข้อความ
		if(dup) {
			throw new DuplicateException();
		}
	}

	protected int delete(CCTConnection conn, String ids) throws Exception {
		return dao.delete(conn, ids);
	}

	protected String searchMasterIsNull(CCTConnection conn) throws Exception {
		return dao.searchMasterIsNull(conn);
	}

	protected Object deleteMaster(CCTConnection conn, String ids) throws Exception {
		return dao.deleteMaster(conn, ids);
	}

	protected List<ManageTimeOffset> searchListById(CCTConnection conn, String id) throws Exception {
		return dao.searchListById(conn, id);
	}

	protected int checkDupMaster(CCTConnection conn, ManageTimeOffset manageTimeOffset) throws Exception {
		return dao.checkDupMaster(conn, manageTimeOffset);
	}

	protected int deleteTODetailInEdit(CCTConnection conn, long i) throws Exception {
		return dao.deleteTODetailInEdit(conn, i);
	}

	protected int deleteTODetailInEdit(CCTConnection conn, String selectedIds) throws Exception {
		return dao.deleteTODetailInEdit(conn, selectedIds);
	}

	protected int addDetailEdit(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		return dao.addDetailEdit(conn, obj);

	}

	protected String countTOT(CCTConnection conn, long id) throws Exception {

		long TOT = dao.countTOT(conn, id);

		if (TOT > 0) {
			return GlobalVariableTO.WAIT_STATUS;
		} else {
			return GlobalVariableTO.COMPLETE_STATUS;
		}

	}
}
