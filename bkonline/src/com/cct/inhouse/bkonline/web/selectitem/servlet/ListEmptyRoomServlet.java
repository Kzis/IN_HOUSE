package com.cct.inhouse.bkonline.web.selectitem.servlet;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONUtil;

import com.cct.common.CommonSelectItemServlet;
import com.cct.common.CommonUser;
import com.cct.enums.CharacterEncoding;
import com.cct.enums.ContentType;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingResponse;
import com.cct.inhouse.bkonline.core.booking.roombooking.service.RoomBookingManager;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;

import util.bundle.BundleUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

@WebServlet(
		name=SelectItemServletURL.ListEmptyRoomServlet,
	    description="For search empty room",
	    urlPatterns={SelectItemServletURL.ListEmptyRoomServlet}
)
public class ListEmptyRoomServlet extends CommonSelectItemServlet {

	private static final long serialVersionUID = 3729490736198418668L;

	@Override
	public CCTConnection getConection() throws Exception {
		return new CCTConnectionProvider().getConnection(null, DBLookup.MYSQL_INHOUSE.getLookup());
	}

	@Override
	public boolean invalidateUser(CCTConnection conn, CommonUser commonUser) throws Exception {
		return (commonUser == null);
	}


	@Override
	public void initSelectItem(HttpServletRequest request, HttpServletResponse response) {

		RoomBookingResponse roomBookingResponse = new RoomBookingResponse();
		
		CommonUser commonUser = null;
		Locale locale = null;
		CCTConnection conn = null;
		try {
			conn = getConection();
			
			commonUser = getCommonUser(request);
			if (invalidateUser(conn, commonUser)) {
				getLogger().debug("No have user is session");
				return;
			}
			
			locale = getLocale(request);
			
			String startDate = StringDelimeter.EMPTY.getValue();
			if (SessionUtil.requestParameter("startDate") != null) {
				startDate = SessionUtil.requestParameter("startDate");
			}
			
			String startTime = StringDelimeter.EMPTY.getValue();
			if (SessionUtil.requestParameter("startTime") != null) {
				startTime = SessionUtil.requestParameter("startTime");
			}
			
			String endDate = StringDelimeter.EMPTY.getValue();
			if (SessionUtil.requestParameter("endDate") != null) {
				endDate = SessionUtil.requestParameter("endDate");
			}
			
			String endTime = StringDelimeter.EMPTY.getValue();
			if (SessionUtil.requestParameter("endTime") != null) {
				endTime = SessionUtil.requestParameter("endTime");
			}
	
			if (!startDate.isEmpty() && !startTime.isEmpty() && !endDate.isEmpty() && !endTime.isEmpty()) {
				RoomBookingManager manager = new RoomBookingManager(getLogger(), getCommonUser(request), getLocale(request));
				RoomBooking data = new RoomBooking();
				data.setStartDate(startDate);
				data.setStartTime(startTime);
				data.setEndDate(endDate);
				data.setEndTime(endTime);
				roomBookingResponse.setListEmptyRoom(manager.searchListEmptyRoom(conn, startDate, startTime, endDate, endTime));
			}
			
			if (roomBookingResponse.getListEmptyRoom().size() == 0) {
				roomBookingResponse.setError(true);
				roomBookingResponse.setErrorMessage(getErrorMessage("bk.noHaveEmptyRoom", locale));	
			}
		} catch (Exception e) {
			roomBookingResponse.setError(true);
			roomBookingResponse.setErrorMessage(e.getMessage());
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
			responseToClient(roomBookingResponse, response, commonUser, locale);
		}
	}
	
	/**
	 * ตอบกลับ Client
	 * @param listSelectItem
	 * @param response
	 * @param user
	 * @param locale
	 */
	public void responseToClient(RoomBookingResponse roomBookingResponse, HttpServletResponse response, CommonUser user, Locale locale) {
		try {
			String jsonString = JSONUtil.serialize(roomBookingResponse, null, null, false, true);
			response.setCharacterEncoding(CharacterEncoding.UTF8.getValue());
			response.setContentType(ContentType.APPLICATION_JSON.getValue());
			
			PrintWriter out = response.getWriter();
			out.print(jsonString);
		} catch (Exception e) {
			getLogger().error(e);
		}
	}
	
	private String getErrorMessage(String key, Locale locale) {
		String message = StringDelimeter.EMPTY.getValue();
		try {
			ResourceBundle bundle = BundleUtil.getBundle("resources/bundle/inhouse/bkonline/MessageBK"
                , locale);
			message = bundle.getString(key);
		} catch (Exception e) {
			
		}
		return message;
	}
}
