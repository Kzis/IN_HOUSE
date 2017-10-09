package com.cct.common;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONUtil;

import com.cct.enums.CharacterEncoding;
import com.cct.enums.ContentType;
import com.cct.enums.SelectItemParameter;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public abstract class CommonSelectItemBasicServlet extends CommonSelectItemServlet {

	private static final long serialVersionUID = -2018123156904737038L;
	
	/**
	 * init parameter ก่อน search
	 * 
	 * @param request
	 * @param response
	 */
	public void initSelectItem(HttpServletRequest request, HttpServletResponse response) {
		
		CommonUser commonUser = null;
		Locale locale = null;
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();
		
		CCTConnection conn = null;
		try {
			conn = getConection();
			
			commonUser = getCommonUser(request);
			if (invalidateUser(conn, commonUser)) {
				getLogger().debug("No have user is session");
				return;
			}
			
			locale = getLocale(request);

			String term = StringDelimeter.EMPTY.getValue();
			if (SessionUtil.requestParameter(request, SelectItemParameter.TERM.getValue()) != null) {
				term = SessionUtil.requestParameter(request, SelectItemParameter.TERM.getValue());
			}

			String limit = StringDelimeter.EMPTY.getValue();
			if (SessionUtil.requestParameter(request, SelectItemParameter.LIMIT.getValue()) != null) {
				limit = SessionUtil.requestParameter(request, SelectItemParameter.LIMIT.getValue());
			}

			listSelectItem = searchSelectItem(request, conn, commonUser, locale, term, limit);
			if (listSelectItem.size() == 0) {
				// listSelectItem = searchSelectItem(conn, ParameterConfig.getParameter().getApplication().getApplicationLocale(), commonUser, term, limit);
			}
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
			responseToClient(listSelectItem, response, commonUser, locale);
		}
	}

	/**
	 *
	 * @param conn
	 * @param locale
	 * @param commonUser
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public abstract List<CommonSelectItem> searchSelectItem(HttpServletRequest request, CCTConnection conn, CommonUser user, Locale locale, String term, String limit) throws Exception;
	
	/**
	 * ตอบกลับ Client
	 * @param listSelectItem
	 * @param response
	 * @param user
	 * @param locale
	 */
	public void responseToClient(List<CommonSelectItem> listSelectItem, HttpServletResponse response, CommonUser user, Locale locale) {
		try {
			String jsonString = JSONUtil.serialize(listSelectItem, null, null, false, true);
			response.setCharacterEncoding(CharacterEncoding.UTF8.getValue());
			response.setContentType(ContentType.APPLICATION_JSON.getValue());
			
			PrintWriter out = response.getWriter();
			out.print(jsonString);
		} catch (Exception e) {
			getLogger().error(e);
		}
	}
}
