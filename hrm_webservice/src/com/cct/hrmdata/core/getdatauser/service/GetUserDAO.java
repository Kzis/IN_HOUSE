package com.cct.hrmdata.core.getdatauser.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.log.LogUtil;
import util.string.StringUtil;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonUser;

public class GetUserDAO extends AbstractDAO{

	
	/**
	 * Search user จาก DB HRM
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonUser> searchUser(CCTConnection conn) throws Exception {
		
		List<CommonUser> lstUser = new ArrayList<>();
		
		/*CommonUser u = new CommonUser();
		u.setUserId("1231");
		u.setFirstName("1231");
		u.setLastName("1231");
		u.setUserEmail("1231");
		u.setNickName("1231");
		u.setPositionId("1231");
		u.setPositionName("1231");
		lstUser.add(u);
		*/
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchUser"
				 );
		LogUtil.GET_USER.debug("SQL searchUser :" +sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try{
			stmt = conn.createStatement();
			LogUtil.GET_USER.debug("SQL searchUser Creacte Complete:");
			
			rst = stmt.executeQuery(sql);
			LogUtil.GET_USER.debug("SQL searchUser Excu Complete:");
			
			CommonUser user = null;
			while (rst.next()) {
				user = new CommonUser();
				
				user.setUserId(rst.getInt("USERID"));
				user.setUserEmail(StringUtil.nullToString(rst.getString("USEREMAIL")));
				user.setTitleName(StringUtil.nullToString(rst.getString("TITLEDESCRTHA")));
				user.setFirstName(StringUtil.nullToString(rst.getString("FIRSTNAMETHA")));
				user.setLastName(StringUtil.nullToString(rst.getString("LASTNAMETHA")));
				user.setNickName(StringUtil.nullToString(rst.getString("NICKNAME")));
				user.setPositionName(StringUtil.nullToString(rst.getString("POSITIONNAME")));
				user.setPositionId(rst.getInt("POSITIONID"));
				user.setCardId(StringUtil.nullToString(rst.getString("CARDID")));
				lstUser.add(user);
				
			}
			
			LogUtil.GET_USER.debug("List searchUser :" +lstUser.size());
			
		}catch (Exception e){
			throw e;
		}
		return lstUser;
		
		
	}

}
