package com.cct.inhouse.timeoffset.core.dialog.approve.service;

import java.sql.ResultSet;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.dialog.approve.domain.ApproveDialog;
import com.cct.inhouse.timeoffset.core.dialog.approve.domain.ApproveDialogSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class ApproveDialogDAO extends AbstractDAO<ApproveDialogSearchCriteria, ApproveDialog, ApproveDialog> {

	public ApproveDialogDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	@Override
	protected ApproveDialog createResultSearch(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ApproveDialog createResultSearchById(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		ApproveDialog approveDialog = new ApproveDialog();
		approveDialog.setProjectCode(StringUtil.nullToString(rst.getString("PROJECT_ID")));
		approveDialog.setProjectName(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
		approveDialog.setProjectConditionID(StringUtil.nullToString(rst.getString("PROJ_CON_ID")));
		approveDialog.setProjectConditionDETAIL(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL")));
		approveDialog.setProjectConditionFlag(StringUtil.nullToString(rst.getString("PROJ_CON_FLAG")));
		approveDialog.setHourTot(StringUtil.nullToString(rst.getString("HOUR_TOT")));
		approveDialog.setWorkDetail(StringUtil.nullToString(rst.getString("DETAIL_WORK")));
		approveDialog.setStartDate(StringUtil.nullToString(TOUtil.splitToDate(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))))));
		approveDialog.setStartTime(StringUtil.nullToString(TOUtil.splitToTime(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))))));
		approveDialog.setEndDate(StringUtil.nullToString(TOUtil.splitToDate(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))))));
		approveDialog.setEndTime(StringUtil.nullToString(TOUtil.splitToTime(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))))));
		approveDialog.setWorkDate(StringUtil.nullToString(TOUtil.splitToDate(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))))));
		approveDialog.setDay(StringUtil.nullToString(rst.getString("DAY").split("\\.")[0]));
		approveDialog.setHour(StringUtil.nullToString(rst.getString("HOUR").split("\\.")[0]));
		approveDialog.setMin(StringUtil.nullToString(rst.getString("MINUTE").split("\\.")[0]));
		approveDialog.setRemark(StringUtil.nullToString(rst.getString("REMARK")));
		
		return approveDialog;
	}

	@Override
	protected String createSQLAdd(CCTConnection conn, ApproveDialog obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLCheckDup(CCTConnection conn, ApproveDialog obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLCountData(CCTConnection conn, ApproveDialogSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLDelete(CCTConnection conn, String ids, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLEdit(CCTConnection conn, ApproveDialog obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLSearch(CCTConnection conn, ApproveDialogSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLSearchById(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchApproveDialogById"
				, params);
		
		return sql;
	}

	@Override
	protected String createSQLUpdateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
