package com.cct.inhouse.timeoffset.core.approve.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.approve.domain.Approve;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearch;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearchCriteria;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.util.TOUtil;

public class TimeOffsetApproveService extends AbstractService {

	private TimeOffsetApproveDAO dao = null;

	public TimeOffsetApproveService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new TimeOffsetApproveDAO(logger, SQLPath.TO_APPROVE.getSqlPath(), user, locale);
	}

	protected long countData(CCTConnection conn, ApproveSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria);
	}

	protected List<ApproveSearch> search(CCTConnection conn, ApproveSearchCriteria criteria) throws Exception {

		List<ApproveSearch> lstResult = dao.search(conn, criteria);
		List<ApproveSearch> lstResultNew = new ArrayList<ApproveSearch>();

		int day;
		int hour;
		int minute;
		String processStatus;
		String approveStatus = GlobalVariableTO.WAIT_STATUS;

		// Process check process status
		for (int i = 0; i < lstResult.size(); i++) {

			// ครั้งแรกให้ใส่ลงอีก list ได้เลย
			if (lstResultNew.size() == 0) {
				lstResultNew.add(lstResult.get(i));
			} else {

				// เปรียบเทียบ ค่าปัจจุบัน กับค่าใน List
				for (int j = 0; j < lstResultNew.size(); j++) {

					// Check ว่าถ้ามี TIMEOFFSET_ID นี้อยู่ใน list ตัวใหม่แล้วไม่ต้องใส่เข้าไป
					if (lstResultNew.get(j).getId().equals(lstResult.get(i).getId())) {
						day = (int)(Double.parseDouble(lstResult.get(i).getDay()) + Double.parseDouble(lstResultNew.get(j).getDay()));
						hour = (int)(Double.parseDouble(lstResult.get(i).getHour()) + Double.parseDouble(lstResultNew.get(j).getHour()));
						minute = (int)(Double.parseDouble(lstResult.get(i).getMinute()) + Double.parseDouble(lstResultNew.get(j).getMinute()));

						if (lstResultNew.get(j).getProcessStatus().equals(GlobalVariableTO.WAIT_STATUS) || lstResult.get(i).getProcessStatus().equals(GlobalVariableTO.WAIT_STATUS)) {
							processStatus = GlobalVariableTO.WAIT_STATUS;
						} else {
							processStatus = GlobalVariableTO.COMPLETE_STATUS;
						}

						if (lstResultNew.get(j).getApproveUser().length() > lstResult.get(i).getApproveUser().length()) {
							lstResultNew.get(j).setApproveUser(lstResultNew.get(j).getApproveUser());
						} else {
							lstResultNew.get(j).setApproveUser(lstResult.get(i).getApproveUser());
						}

						lstResultNew.get(j).setDay(String.valueOf(day));
						lstResultNew.get(j).setHour(String.valueOf(hour));
						lstResultNew.get(j).setMinute(String.valueOf(minute));
						lstResultNew.get(j).setApproveStatus(approveStatus);
						lstResultNew.get(j).setProcessStatus(processStatus);
						break;
					} else if (j == lstResultNew.size() - 1) {
						lstResultNew.add(lstResult.get(i));
						break;
					}
				}
			}
		}

		return TOUtil.manageTimeToApprove(lstResultNew);
	}


	protected Approve searchById(CCTConnection conn, String id) throws Exception {
		return dao.searchByIdCustom(conn, id);
	}

	protected long add(CCTConnection conn, Approve obj) throws Exception {
		return dao.add(conn, obj);
	}

	protected Approve searchByIdForViewApprove(CCTConnection conn, String id) throws Exception {
		return dao.searchById(conn, id);
	}

	protected long edit(CCTConnection conn, Approve obj, int i) throws Exception {
		return dao.edit(conn, obj, i);
	}

	protected int editMaster(CCTConnection conn, String id, String processStatus) throws Exception {
		return dao.editMaster(conn, id, processStatus);
	}
}
