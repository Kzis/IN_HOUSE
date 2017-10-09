package com.cct.inhouse.timeoffset.core.approve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;
import com.cct.exception.MaxExceedException;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.approve.domain.Approve;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearch;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearchCriteria;
import com.cct.inhouse.timeoffset.core.approve.domain.Detail;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;

public class TimeOffsetApproveManager extends AbstractManager<ApproveSearchCriteria, ApproveSearch, Approve, CommonUser, Locale> {

	private TimeOffsetApproveService service = null;

	public TimeOffsetApproveManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new TimeOffsetApproveService(logger, user, locale);
	}

	@Override
	public long add(CCTConnection conn, Approve obj) throws Exception {
		return service.add(conn, obj);
	}

	@Override
	public int edit(CCTConnection conn, Approve obj) throws Exception {
		try {

			// BEGIN TRANSACTION
			conn.setAutoCommit(false);

			// Count Approve Status = W || กรณีที่ TOT > 0 = ให้ PROCESS_STATUS = W และ  TOT = 0 ให้ PROCESS_STATUS = Y
			String processStatus = countWaitApproveStatus(obj.getListDetail());

			// UPDATE MASTER
			service.editMaster(conn, obj.getId(), processStatus);

			for (int i = 0; i < obj.getListDetail().size(); i++) {
				service.edit(conn, obj, i);
			}

			// COMMIT TRANSACTION
			conn.commit();

		} catch (Exception e) {
			
			// ROLLBACK TRANSACTION
			conn.rollback();

			throw new Exception("30010");
			
		} finally {

			// Set AutoCommit กลับคืนเป็น True
			conn.setAutoCommit(true);
		}

		return 0;
	}

	@Override
	public List<ApproveSearch> search(CCTConnection conn, ApproveSearchCriteria criteria) throws Exception {
		
		List<ApproveSearch> listResult = new ArrayList<ApproveSearch>();
				
		try {
			criteria.setTotalResult(service.countData(conn, criteria));

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

	private String countWaitApproveStatus(List<Detail> listDetail) throws Exception {

		int TOT = 0;
		String processStatus = GlobalVariableTO.WAIT_STATUS;

		try {

			// Loop เพื่อ check ว่าใน list มี approveStatus = W หรือไม่
			for (int i = 0; i < listDetail.size(); i++) {

				// ถ้ามี approveStatus = W แม้แต่ตัวเดียวให้ Set processStatus = W
				if (listDetail.get(i).getApproveStatus().equals(GlobalVariableTO.WAIT_STATUS)) {
					TOT = TOT + 1;
				}

			}

			if (TOT > 0) {
				processStatus = GlobalVariableTO.WAIT_STATUS;
			} else {
				processStatus = GlobalVariableTO.COMPLETE_STATUS;
			}

		} catch (Exception e) {
			throw e;
		}

		return processStatus;
	}

	public Approve searchByIdForViewApprove(CCTConnection conn, String id) throws Exception {
		return service.searchById(conn, id);
	}
	
	@Override
	public Approve searchById(CCTConnection conn, String id) throws Exception {
		return service.searchById(conn, id);
	}

	@Override
	public int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int delete(CCTConnection conn, String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
