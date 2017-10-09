package com.cct.inhouse.timeoffset.core.manage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;
import com.cct.exception.DuplicateException;
import com.cct.exception.MaxExceedException;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffset;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetModel;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearch;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearchCriteria;

public class ManageTimeOffsetManager extends AbstractManager<ManageTimeOffsetSearchCriteria, ManageTimeOffsetSearch, ManageTimeOffset, InhouseUser, Locale> {

	ManageTimeOffsetService service = null;

	public ManageTimeOffsetManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new ManageTimeOffsetService(logger, user, locale);
	}

	@Override
	public long add(CCTConnection conn, ManageTimeOffset obj) throws Exception {

		try {
			
			String processStatus;
			
			// BEGIN TRANSACTION
			conn.setAutoCommit(false);

			for (int i = 0; i <= obj.getListNewProject().size() - 1; i++) {
				// CHECK MASTER : ดึง ID ข้อมูลเวลาชดเชย_SQL
				long timeOffsetId = service.checkDupMaster(conn, obj.getListNewProject().get(i));

				if (timeOffsetId == 0) {
					// ADD MASTER : บันทึกข้อมูลเวลาชดเชย_SQL
					timeOffsetId = service.addMaster(conn, obj.getListNewProject().get(i));
				} else {
					// UPDATE MASTER : อัพเดทข้อมูลเวลาชดเชย_SQL
					processStatus = GlobalVariableTO.WAIT_STATUS;
					service.editMaster(conn, timeOffsetId, processStatus);
				}

				obj.getListNewProject().get(i).setTimeOffsetId(timeOffsetId);

				// CHECK DETAIL : ตรวจสอบการเพิ่มข้อมูลเวลาชดเชย_SQL
				service.checkDup(conn, obj.getListNewProject().get(i));

				// ADD DETAIL : บันทึกข้อมูลรายละเอียดเวลาชดเชย_SQL
				service.add(conn, obj.getListNewProject().get(i));
			}

			// COMMIT TRANSACTION
			conn.commit();
			
		} catch (DuplicateException e) {
			throw new DuplicateException("10003");
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
	public int delete(CCTConnection conn, String ids) throws Exception {

		try {
			// BEGIN TRANSACTION
			conn.setAutoCommit(false);

			service.delete(conn, ids);

			String id = service.searchMasterIsNull(conn);
			
			if (id != "") {
				service.deleteMaster(conn, id);
			}

			// COMMIT TRANSACTION
			conn.commit();
			
		} catch (Exception e) {
			
			// ROLLBACK TRANSACTION
			conn.rollback();
			
			throw new Exception("30012");
			
		} finally {
			
			// Set AutoCommit กลับคืนเป็น True
			conn.setAutoCommit(true);
			
		}

		return 0;
	}

	@Override
	public int edit(CCTConnection conn, ManageTimeOffset obj) throws Exception {

		try {
			
			String processStatus;

			// BEGIN TRANSACTION
			conn.setAutoCommit(false);

			// DELETE DETAIL : ลบรายละเอียดการทำงาน_SQL
			service.deleteTODetailInEdit(conn, obj.getTimeOffsetId());

			// INSERT NEW DETAIL(EDIT) : Loop
			
			// บันทึกข้อมูลรายละเอียดเวลาชดเชย_SQL
			for (int i = 0; i <= obj.getListNewProject().size() - 1; i++) {
				if (obj.getListNewProject().get(i) != null) {
					
					//Delete != Y
					if( !(obj.getListNewProject().get(i).getDeleteFlag().equals("Y")) ){
						
						// ถ้า ProjectCondition เป็นแบบกำหนดชั่วโมงชดเชย
						if (obj.getListNewProject().get(i).getProjectConditionFlag().equals("Y")) {
							obj.getListNewProject().get(i).setStartDate(obj.getListNewProject().get(i).getWorkDate());
							obj.getListNewProject().get(i).setEndDate(obj.getListNewProject().get(i).getWorkDate());
							obj.getListNewProject().get(i).setStartTime(GlobalVariableTO.START_TIME);
							obj.getListNewProject().get(i).setEndTime(GlobalVariableTO.END_TIME);
						}

						// CHECK_DUP_EDIT : ตรวจสอบการเพิ่มข้อมูลเวลาชดเชย_SQL
						service.editCheckDup(conn, obj.getListNewProject().get(i));

						// ADD DETAIL
						service.addDetailEdit(conn, obj.getListNewProject().get(i));
					}
					
				}
			}

			// COUNT TOT : ตรวจสอบรายการรออนุมัติ_SQL : กรณีที่ TOT > 0 = ให้  PROCESS_STATUS = W และ TOT = 0 ให้ PROCESS_STATUS = Y
			processStatus = service.countTOT(conn, obj.getTimeOffsetId());

			// EDIT_MASTER : บันทึกแก้ไขข้อมูลเวลาชดเชย_SQL
			service.editMaster(conn, obj.getTimeOffsetId(), processStatus);

			// COMMIT TRANSACTION
			conn.commit();
			
		} catch (DuplicateException e) {
			
			throw new DuplicateException();
			
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

	public List<ManageTimeOffsetSearch> search(CCTConnection conn, ManageTimeOffsetSearchCriteria criteria, ManageTimeOffsetModel model) throws Exception {
		
		List<ManageTimeOffsetSearch> listResult = new ArrayList<ManageTimeOffsetSearch>();

		try {
			criteria.setTotalResult(service.countData(conn, criteria));

			if (criteria.getTotalResult() == 0) {

			} else if ((criteria.isCheckMaxExceed()) && (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceed())) {
				throw new MaxExceedException();
			} else {
				// ค้นหาข้อมูล
				listResult = service.search(conn, criteria , getUser().getId());
			}

		} catch (Exception e) {
			throw e;
		}

		return listResult;
	}

	@Override
	public ManageTimeOffset searchById(CCTConnection conn, String id) throws Exception {
		return service.searchById(conn, id);
	}

	public int deleteMasterSearchPage(CCTConnection conn, String ids) throws Exception {

		try {
			service.deleteMaster(conn, ids);
		} catch (Exception e) {
			throw new Exception("30012");
		} finally {
			//
		}

		return 0;
	}

	public List<ManageTimeOffset> searchListById(CCTConnection conn, String id) throws Exception {
		return service.searchListById(conn, id);
	}

	public int deleteTODetailInEdit(CCTConnection conn, ManageTimeOffset obj, String ids) throws Exception {

		try {
			// DELETE DETAIL : ลบรายละเอียดการทำงาน_SQL
			service.deleteTODetailInEdit(conn, ids);

			// COUNT TOT : ตรวจสอบรายการรออนุมัติ_SQL : กรณีที่ TOT > 0 = ให้  PROCESS_STATUS = W และ TOT = 0 ให้ PROCESS_STATUS = C
			String processStatus = service.countTOT(conn, obj.getListNewProject().get(0).getTimeOffsetId());

			// EDIT_MASTER : บันทึกแก้ไขข้อมูลเวลาชดเชย_SQL
			service.editMaster(conn, obj.getListNewProject().get(0).getTimeOffsetId(), processStatus);

		} catch (Exception e) {
			throw e;
		}

		return 0;
	}

	@Override
	public List<ManageTimeOffsetSearch> search(CCTConnection conn,ManageTimeOffsetSearchCriteria criteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}


