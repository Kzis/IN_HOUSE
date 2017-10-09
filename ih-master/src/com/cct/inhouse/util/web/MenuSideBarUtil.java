package com.cct.inhouse.util.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.cct.domain.Operator;
import com.cct.inhouse.common.InhouseUser;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.log.DefaultLogUtil;

public class MenuSideBarUtil {
	

	public static final String DELIMITER_FUNCTION = ",";
	public static final String OPERATOR_TYPE_MENU = "M";
	public static final String OPERATOR_TYPE_FUNCTION = "F";
	
	private static final int OPERATOR_LEVEL_1 = 1;
	
//	private static final String DIVIDER = "<li class='divider'></li>";
	
//	private static final String BEGIN_UL = "<ul class='menu-level%s collapse' id='%s'>";
	private static final String BEGIN_UL = "<ul class='%s' id='%s'>";
	private static final String END_UL = "</ul>";
//	private static final String BEGIN_LI = "<li>";
//	private static final String LI_FOR_MENU_ITEM = "	<li id='%s'><a href='javascript:void(0);' onclick=\"loadIframe('%s');\">%s</a></li>";
	private static final String LI_FOR_MENU_ITEM = "	<li id='%s'><div onclick=\"loadIframe('%s',this);\">%s</div></li>";
	private static final String END_LI = "</li>";
	
	private static final String NEW_LINE = "\n";
	
	private Object[] operators;
	private Map<String, Operator> mapMenu;
	private Map<String, Operator> mapMenuLevel = new LinkedHashMap<String, Operator>();
	private InhouseUser user;
	private String context;
	
	// เก็บว่ามีกี่ Tab
	HashMap<String, String> mapTap = new LinkedHashMap<String, String>();
	
	// เก็บว่าแต่ละ Tab มีเมนูอะไรบ้าง
	HashMap<String, String> mapTapMenu = new LinkedHashMap<String, String>();
	
	public MenuSideBarUtil(){
		
	}
	
	public MenuSideBarUtil(String context, Map<String, Operator> mapMenu, InhouseUser user) {
		this.context = context + "/";
		this.mapMenu = mapMenu;
		this.operators = (Object[]) (mapMenu.values().toArray());
		this.user = user;
	}
	
	public String genarateMenuBar() throws Exception {
		String html = "";
		
//		if(user != null){
			genarateItem();
			if (mapMenuLevel.size() > 0) {
				drawHMTLMenu(mapMenuLevel);
				html += displayOperator();
			}
			html += NEW_LINE;
//		}
		return html;
	}
	
	
	public String genarateItem() {
		String html = "";

		if (operators.length > 0) {
			int minLevel = ((Operator) operators[0]).getMinLevel();
			int maxLevel = ((Operator) operators[0]).getMaxLevel();

			for (int currentLevel = minLevel; currentLevel <= maxLevel; currentLevel++) {

				for (int index = 0; index < operators.length; index++) {

					Operator operator = (Operator) operators[index];
					if (operator.getOperatorType().equals(OPERATOR_TYPE_MENU) == false) {
						continue;
					}

					if (operator.getCurrentLevel() == currentLevel) {

						if (operator.getCurrentLevel() == OPERATOR_LEVEL_1) {
							mapMenuLevel.put(operator.getOperatorId(), operator);
						} else {
							String parentOperatorIds = searchParentOperator(operator.getParentId(), operators);
							parentOperatorIds += "," + operator.getOperatorId();
							updateOperator(mapMenu, mapMenuLevel, parentOperatorIds, parentOperatorIds);
						}
					}
				}
			}
		}
		return html;
	}

	public String searchParentOperator(String parentId, Object[] listOperator) {
		String parentIds = "";

		if ((parentId != null) && (parentId.trim().length() > 0)) {

			for (Object operatorObject : listOperator) {

				Operator operator = (Operator) operatorObject;
				if (operator.getOperatorType().equals(OPERATOR_TYPE_MENU) == false) {
					continue;
				}

				if (operator.getOperatorId().equals(parentId)) {
					parentIds = parentIds + ",";
					parentIds = operator.getOperatorId() + parentIds;
					String pppId = searchParentOperator(operator.getParentId(), listOperator);
					if (pppId.length() > 0) {
						parentIds = pppId + "," + parentIds;
					}
				}
			}
			if (parentIds.length() > 0) {
				parentIds = parentIds.substring(0, parentIds.length() - 1);
			}

		}
		return parentIds;
	}

	public void updateOperator(Map<String, Operator> mapMenu, Map<String, Operator> mapMenuLevel, String parentIds, String groupParentIds) {

		if (parentIds.indexOf(",") > -1) {
			String[] operatorIds = parentIds.split(",");
			String parentOperatorId = operatorIds[0];
			String currentOperatorId = operatorIds[1];
			String groupOperatorId = groupParentIds.substring(0, groupParentIds.indexOf(currentOperatorId) + currentOperatorId.length());

			Operator operator = mapMenu.get(currentOperatorId);
			operator.setParentOperatorIds(groupOperatorId);
			if (mapMenuLevel.get(parentOperatorId) != null) {
				mapMenuLevel.get(parentOperatorId).getMapOperator().put(operator.getOperatorId(), operator);
			}

			parentIds = parentIds.substring(parentIds.indexOf(",") + 1, parentIds.length());

			if (mapMenuLevel.get(parentOperatorId) != null) {
				updateOperator(mapMenu, mapMenuLevel.get(parentOperatorId).getMapOperator(), parentIds, groupParentIds);
			}
		}
	}
	
	
	public String displayOperator(){
		String html = "";
		int countTab = 0;
		html += NEW_LINE + "<div id='mainMenuTab' style='border: none;'>";
		html += NEW_LINE + "<div class='ui-widget-header BUTTON_HOME'>";
		html += NEW_LINE + "<a tabindex='-1' href='"+ context +"jsp/security/gotoServiceListMainpage.action'>";
		html += NEW_LINE + "<img src='"+ context +"images/menu/i_home.png'>";
		html += NEW_LINE + "<br/>";
		html += NEW_LINE + "Home";
		html += NEW_LINE + "</a>";
		html += NEW_LINE + "</div>";	
		html += NEW_LINE + "<ul style='padding-left: 5px;'>";
		for (String tabIndex : mapTap.keySet()) {
			String tabName = mapTap.get(tabIndex);
			html += NEW_LINE + "<li><a href='#tabs-" + tabIndex + "'>" + tabName + "</a></li>";
		}
		html += NEW_LINE + "</ul>";
		
		DefaultLogUtil.COMMON.debug("\n\n###################################################################\n");
		for (String tabIndex : mapTap.keySet()) {
			
//			String tabName = mapTap.get(tabIndex);
			String tabMenu = mapTapMenu.get(tabIndex);
			html += NEW_LINE + "<div id='tabs-" + tabIndex + "'>";
			
			html += NEW_LINE + "<div id='div-main-container"+ tabIndex +"' class='div-main-container'>";
			html += NEW_LINE + "<div id='div-left-container-menu"+ tabIndex +"' onclick='checkMenu(" + tabIndex + ")' class='div-left-container-menu'>";
			html += NEW_LINE + "	<img src='" + context + "/images/menu/menu-expand.png'/>";
			html += NEW_LINE + "</div>";
			html += NEW_LINE + "<div id='div-left-container"+ tabIndex +"' class='div-left-container'>";
			html += NEW_LINE + "	<div class='bg-menu'>";
			html += NEW_LINE + "		<div class='header-menu-maldives' onclick='checkMenu()'>";
			html += NEW_LINE + "			<img src='" + context + "/images/menu/menu-expand.png'/>";
			html += NEW_LINE + "		</div>";
			html += NEW_LINE + "		<div id='ui-menu-maldives"+ tabIndex +"' class='ui-menu-maldives'>";
			html += NEW_LINE + "			<ul id='menu"+ tabIndex +"'>";
			html += NEW_LINE + "			" + tabMenu;
			html += NEW_LINE + "			</ul>"; // ui-menu-maldives
			html += NEW_LINE + "		</div>"; // ui-menu-maldives
			html += NEW_LINE + "	</div>"; // bg-menu
			html += NEW_LINE + "</div>"; // div-left-container
			
			html += NEW_LINE + "<div id='div-right-container"+ tabIndex +"' class='div-right-container'>";
			
			if(Integer.parseInt(tabIndex) == 0){
				//html += NEW_LINE + "   <div id='contentDiv'>";
				html += NEW_LINE + "		<iframe id='mainIframe' name='mainIframe' class='iframe' src='' height='100%'>";
				html += NEW_LINE + "			<decorator:body />";
				html += NEW_LINE + "		</iframe>";
				//html += NEW_LINE + "	</div>";
			}
			
			html += NEW_LINE + "</div>"; // end div-right-container 
			
			html += NEW_LINE + "</div>"; // div-main-container
			html += NEW_LINE + "</div>"; // divtab
			
			
			countTab++; //COUNT TAB
		}
		html += NEW_LINE + "</div>";
		html += "<input id='countTab' type='hidden' value='"+countTab+"'>";
		DefaultLogUtil.COMMON.debug("\n\n###################################################################\n");
		return html;
	}
	
	public String drawHMTLMenu(Map<String, Operator> mapMenuLevel) {

		int tabIndex = 0;
		String html = "";
		if (mapMenuLevel.size() > 0) {

			for (String operatorId : mapMenuLevel.keySet()) {

				Operator operator = mapMenuLevel.get(operatorId);
				if (operator.getVisible().equals("N")) {
					continue;
				}

				if(operator.getCurrentLevel() == OPERATOR_LEVEL_1){
					
					if(tabIndex > 0){
						mapTapMenu.put(String.valueOf(tabIndex-1), html);
						html = "";
					}
					
					mapTap.put(String.valueOf(tabIndex), operator.getLabel());
					tabIndex++;
				}
				
				if (operator.getMapOperator().size() > 0) {
					if(operator.getCurrentLevel() == OPERATOR_LEVEL_1){
						html += drawHMTLMenu(operator.getMapOperator());
					}else{
//						html += NEW_LINE + "<li data-toggle='collapse' data-target='#" + operator.getSystemName().replaceAll(" ", "") + "' class='collapsed menu-level" + operator.getCurrentLevel() + "'>";
//						html += NEW_LINE + "	<a href=\"javascript:void(0);\">" + operator.getLabel() + "</a>";
						html += NEW_LINE + "<li>";
						html += NEW_LINE + "	<div href=\"javascript:void(0);\">" + operator.getLabel() + "5</div>";
						
						
						//html += NEW_LINE + BEGIN_LI;
						html += NEW_LINE + String.format(BEGIN_UL, String.valueOf(operator.getCurrentLevel() + 1), operator.getSystemName().replaceAll(" ", ""));
						html += drawHMTLMenu(operator.getMapOperator());
						html += NEW_LINE + END_UL;
						html += NEW_LINE + END_LI;
						
						html += NEW_LINE + "</li>";
						
						
					}
				}else{
					
					String operatorIds = operator.getParentOperatorIds();
					if (operatorIds == null) {
						operatorIds = operator.getOperatorId();
					}
					
					html += NEW_LINE + String.format(LI_FOR_MENU_ITEM, operatorIds, operator.getUrl(), operator.getLabel());
					//html += NEW_LINE + String.format(LI_FOR_MENU_ITEM, operatorIds, "jsp/user/admin/initAdmin.action", operator.getLabel());
				}
			}
		}
		
		mapTapMenu.put(String.valueOf(tabIndex-1), html);
		return html;
	}
	
	
	public static void main(String[] args) {
		try{
			MenuSideBarUtil menu = new MenuSideBarUtil();
			Map<String, Operator> operators = menu.searchDetailOperatorByOperatorId(null, Locale.ENGLISH, null);
			
			menu.mapMenu = operators;
			menu.operators = (Object[]) (operators.values().toArray());
			
			String menuStr = menu.genarateMenuBar();
			
			System.out.println(menuStr);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ค้นหารายละเอียดเมนูและสิทธิ์
	 * @param operatorIds
	 * @param locale
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchDetailOperatorByOperatorId(String operatorIds, Locale locale, Long siteId) throws Exception {

		CCTConnection conn = null;
		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();

		try {
			
			Operator operatorLevel = searchOperatorLevel(conn, operatorIds, locale, siteId);
			mapOperator = searchOperator(conn, operatorIds, locale, siteId);
			for (String key : mapOperator.keySet()) {
				mapOperator.get(key).setMinLevel(operatorLevel.getMinLevel());
				mapOperator.get(key).setMaxLevel(operatorLevel.getMaxLevel());
				break;
			}
		} catch (Exception e) {
			throw e;
		}finally{
			CCTConnectionUtil.close(conn);
		}

		return mapOperator;
	}
	
	/**
	 * หาข้อมูลสิทธิ์_SQL (SEARCH MIN, MAX MENU LEVEL)
	 * @param conn
	 * @param userId
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Operator searchOperatorLevel(CCTConnection conn, String operatorIds,  Locale locale, Long siteId) throws Exception {

		Operator operatorLevel = new Operator();
		operatorLevel.setMinLevel(1);
		operatorLevel.setMaxLevel(4);
		return operatorLevel;
	}
	
	/**
	 * หาข้อมูลสิทธิ์_SQL (SEARCH MENU)
	 * @param conn
	 * @param userId
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchOperator(CCTConnection conn, String operatorIds,  Locale locale, Long siteId) throws Exception {
		
		
		return null;
	}
}
