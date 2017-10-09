package com.cct.inhouse.util.web;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cct.domain.Operator;

import util.string.StringDelimeter;

public class InHousePushMenuUtil {
	private static final String BEGIN_LI = "<li>";
	private static final String END_LI = "</li>";
	// private static final String BEGIN_UL = "<ul>";
	private static final String END_UL = "</ul>";
	private static final String BEGIN_DIV_FOR_SUBMENU = "<li class='icon icon-arrow-left'>\n<a id='%s' tabindex='-1' class='icon %s' href='javascript:void(0);'>%s</a>\n<div class='mp-level'>\n<h2 class='icon %s'>%s</h2>\n<ul>";
	private static final String END_DIV = "</div>";
	// private static final String BEGIN_HREF = "<a id='%s' tabindex='-1' href='javascript:void(0);'>"; 
	private static final String BEGIN_HREF_FOR_LINK = "<a id='%s' tabindex='-1' href='%s' class='icon %s' target='_self'>";
	private static final String END_HREF = "</a>";
	private static final String JAVASCRIPT_VOID_0 = "javascript:void(0);";

	public static final String DELIMITER_FUNCTION = "_";
	public static final String OPERATOR_TYPE_MENU = "M";
	public static final String OPERATOR_TYPE_FUNCTION = "F";

	private static final int OPERATOR_LEVEL_1 = 2;// TODO: ไม่นับ root บนสุด เนื่องจากใช้เป็น Title ของเมนูแล้ว
	private String menuTitle;
	private String context;
	private Object[] operators;
	private Map<String, Operator> mapMenu;
	private Map<String, Operator> mapMenuLevel = new LinkedHashMap<String, Operator>();
	private int totalMenuInProcess = 0; 

	public InHousePushMenuUtil() {

	}
	
//	<p><a href="#" id="trigger" class="menu-trigger">Open/Close Menu</a></p>
//	<!-- mp-menu -->
//	<nav id="mp-menu" class="mp-menu">
//		<div class="mp-level">
//			<h2 class="icon icon-world">Border Crossing Control</h2>
//			<ul>
//				<li class="icon icon-arrow-left">
//					<a class="icon icon-display" href="javascript:void(0);">Crossing Control</a>
//					<div class="mp-level">
//						<h2 class="icon icon-display">Crossing Control</h2>
//						<ul>
//							<li><a class="icon icon-photo" href="<s:url value='/jsp/bcc/border/initArrival.action'/>">Arrival</a></li>
//							<li><a class="icon icon-wallet" href="<s:url value='jsp/bcc/border/initDeparture.action'/>">Departure</a></li>
//						</ul>
//					</div>
//				</li>
//				<li><a class="icon icon-photo" href="<s:url value='/jsp/bcc/crossingrejection/initMonitoring.action'/>">Crossing Rejection Alert</a></li>
//				<li><a class="icon icon-wallet" href="<s:url value='/jsp/bcc/offload/initFlight.action'/>">Offload/Transport Rollback</a></li>
//				<li><a class="icon icon-wallet" href="<s:url value='/jsp/bcc/inadmissible/initInadmissiblePassenger.action'/>">Inadmissible Passenger</a></li>
//			</ul>
//		</div>
//	</nav>
	
// 1. ถ้า Menu ไม่มีลูกสามารถใส่ item ได้เลย <li>item</li>
// 2. ถ้า Menu มีลูกต้องใส่ค่า
//		<li class="icon icon-arrow-left">
//			<a class="icon icon-display" href="javascript:void(0);">Crossing Control</a>
//			<div class="mp-level">
//				<h2 class="icon icon-display">Crossing Control</h2>
//				<ul>
//					???
//				</ul>
//			</div>
//		</li>
	

	public InHousePushMenuUtil(String context, Map<String, Operator> mapMenu) {
		this.context = context;
		this.mapMenu = mapMenu;
		this.operators = (Object[]) (mapMenu.values().toArray());
//		this.menuTitle = ((Operator) operators[0]).getLabel();
//		if (operators.length > 1) {
//			((Operator) operators[1]).setMinLevel(((Operator) operators[0]).getMinLevel());
//			((Operator) operators[1]).setMaxLevel(((Operator) operators[0]).getMaxLevel());
//			System.arraycopy(operators, 1, operators, 0, operators.length -1);
//		}
	}

	public String genarateMenuBar() {
		genarateItem();
		StringBuilder htmlBuilder = new StringBuilder();
		// TODO: ไม่นับ root บนสุด เนื่องจากใช้เป็น Title ของเมนูแล้ว
//		htmlBuilder.append("<div class='mp-level'>");
//		htmlBuilder.append("<h2>" + menuTitle + "</h2>");
		htmlBuilder.append("<ul>");
		if (mapMenuLevel.size() > 0) {
			String htmlTmp = displayOperator(mapMenuLevel);
			htmlBuilder.append(htmlTmp.substring(0, htmlTmp.length()));
		}
		htmlBuilder.append(END_UL);
//		htmlBuilder.append("</div>");
		return htmlBuilder.toString();
	}

	public String genarateItem() {
		String html = "";

		if (operators.length > 0) {
			int minLevel = ((Operator) operators[0]).getMinLevel();
			int maxLevel = ((Operator) operators[0]).getMaxLevel();

			for (int currentLevel = minLevel; currentLevel <= maxLevel; currentLevel++) {

				for (int index = operators.length - 1; index >= 0; index--) {

					Operator operator = (Operator) operators[index];
					// System.out.println(currentLevel + " > " + operator.getCurrentLevel() + " " + operator.getOperatorId() + " " + operator.getOperatorType());
					if (operator.getOperatorType().equals(OPERATOR_TYPE_MENU) == false) {
						continue;
					}

					if (operator.getCurrentLevel() == currentLevel) {

						if (operator.getCurrentLevel() == OPERATOR_LEVEL_1) {
							mapMenuLevel.put(operator.getOperatorId(), operator);
						} else {
							String parentOperatorIds = searchParentOperator(operator.getParentId(), operators);
							parentOperatorIds += DELIMITER_FUNCTION + operator.getOperatorId();
							// System.out.println(currentLevel + " > " + parentOperatorIds);
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
					parentIds = parentIds + DELIMITER_FUNCTION;
					parentIds = operator.getOperatorId() + parentIds;
					String pppId = searchParentOperator(operator.getParentId(), listOperator);
					if (pppId.length() > 0) {
						parentIds = pppId + DELIMITER_FUNCTION + parentIds;
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

		if (parentIds.indexOf(DELIMITER_FUNCTION) > -1) {
			String[] operatorIds = parentIds.split(DELIMITER_FUNCTION);
			String parentOperatorId = operatorIds[0];
			String currentOperatorId = operatorIds[1];
			String groupOperatorId = groupParentIds.substring(0, groupParentIds.indexOf(currentOperatorId) + currentOperatorId.length());

			Operator operator = mapMenu.get(currentOperatorId);
			operator.setParentOperatorIds(groupOperatorId);
			if (mapMenuLevel.get(parentOperatorId) != null) {
				mapMenuLevel.get(parentOperatorId).getMapOperator().put(operator.getOperatorId(), operator);
			}

			parentIds = parentIds.substring(parentIds.indexOf(DELIMITER_FUNCTION) + 1, parentIds.length());

			if (mapMenuLevel.get(parentOperatorId) != null) {
				updateOperator(mapMenu, mapMenuLevel.get(parentOperatorId).getMapOperator(), parentIds, groupParentIds);
			}
		}
	}

	public String displayOperator(Map<String, Operator> mapMenuLevel) {

		String html = StringDelimeter.EMPTY.getValue();
		if (mapMenuLevel.size() > 0) {

			for (String operatorId : mapMenuLevel.keySet()) {

				Operator operator = mapMenuLevel.get(operatorId);

//				if (operator.getCurrentLevel() == 1) {
//					continue; // TODO : ข้ามหนึ่ง เพราะใช้เป็น title
//				}
				
				if (operator.getVisible().equals("N")) {
					continue;
				}

				// System.out.println(operator.getOperatorId() + " " + operator.getLabel() + " " + operator.getCurrentLevel());
				if (operator.getMapOperator().size() > 0) {
					// System.out.println("h1: [\n" + html + "]\n");
					html = displayOperator(operator.getMapOperator()) + html;
					// System.out.println("h2: [\n" + html + "]\n");
					if (html.length() == 0) {
						// html = "<li>" + operator.getOperatorId() + " " + operator.getLabel() + " " + operator.getCurrentLevel() + "</li>" + "\n" + html;
						html = genarateMenuItem(operator) + "\n" + html;
					} else {
						// html = "<li>" + operator.getOperatorId() + " " + operator.getLabel() + " " + operator.getCurrentLevel() + "\n<ul>" + "\n" + html;
						html = genarateBeginMenuItem(operator) + "\n" + html;
					}
				} else {
					// html = "<li>" + operator.getOperatorId() + " " + operator.getLabel() + " " + operator.getCurrentLevel() + "</li>" + "\n" + html;
					html = genarateMenuItem(operator) + "\n" + html;
				}
			}
		}
		
		// System.out.println("kk: \n" + totalMenuInProcess + " >> " + mapMenu.size());
		if (totalMenuInProcess == mapMenu.size() - 1) {
			
		} else {
			html = html + genarateEndMenuItem();
		}
//		System.out.println("kk: \n" + html);
		return html;
	}

	public String genarateMenuItem(Operator operator) {
		totalMenuInProcess++;
//		<li><a class="icon icon-wallet" href="<s:url value='/jsp/bcc/inadmissible/initInadmissiblePassenger.action'/>">Inadmissible Passenger</a></li>
		String operatorIds = operator.getParentOperatorIds();
		if (operatorIds == null) {
			operatorIds = operator.getOperatorId();
		}

		String url = JAVASCRIPT_VOID_0;
		if ((operator.getUrl() != null) && (!operator.getUrl().isEmpty())) {
			url = context + "/" + operator.getUrl();
		}
//		String link = String.format(BEGIN_HREF_FOR_LINK, operatorIds, url, "icon-wallet");
		String link = String.format(BEGIN_HREF_FOR_LINK, operatorIds, url, "");
		String html = "";
		html += BEGIN_LI;
		html += link + operator.getLabel() + END_HREF;
		html += END_LI;
		return html;
	}

	public String genarateBeginMenuItem(Operator operator) {
		totalMenuInProcess++;
		String groupOperatorId = operator.getParentOperatorIds();
		if (groupOperatorId == null) {
			groupOperatorId = operator.getOperatorId();
		}
//		BEGIN_DIV_FOR_SUBMENU = "<li class='icon icon-arrow-left'>\n<a id='%s' tabindex='-1' class='icon %s' href='javascript:void(0);'>%s</a>\n<div class='mp-level'>\n<h2 class='icon %s'>%s</h2>\n<ul>";
//		String html = String.format(BEGIN_DIV_FOR_SUBMENU, groupOperatorId,  "icon-display", operator.getLabel(), "icon-display", operator.getLabel());
		String html = String.format(BEGIN_DIV_FOR_SUBMENU, groupOperatorId,  "", operator.getLabel(), "", operator.getLabel());
		return html;
	}

	public String genarateEndMenuItem() {
//			</ul>
//		</div>
//	</li>
		String html = END_UL + "\n" + END_DIV + "\n" + END_LI;
		return html;
	}

	public static String searchLabel(Map<String, Operator> mapMenu, String functionCode) {
		String labelHeader = "";

		if (mapMenu.get(functionCode) == null) {
			return labelHeader;
		}

		while (mapMenu.get(functionCode).getParentId() != null && !mapMenu.get(functionCode).getParentId().equals("")) {
			Operator operator = mapMenu.get(functionCode);

			if (operator != null) {
				if (labelHeader.length() > 0) {
					labelHeader = "&nbsp;&rsaquo;&nbsp;" + labelHeader;
				}
				labelHeader = operator.getLabel() + labelHeader;
				functionCode = operator.getParentId();
			}
		}

//		labelHeader = "&nbsp;&rsaquo;&nbsp;" + labelHeader;
//		labelHeader = mapMenu.get(functionCode).getLabel() + labelHeader;

		return labelHeader;
	}

	public static String searchParentOperatorIds(Map<String, Operator> mapMenu, String programCode) {
		String searchParentOperatorIds = StringDelimeter.EMPTY.getValue();

		if (programCode == null) {

		} else if (mapMenu.get(programCode) == null) {

		} else {
			searchParentOperatorIds = programCode;
			while (mapMenu.get(programCode).getParentId() != null) {
				Operator operator = mapMenu.get(programCode);

				if (operator != null) {
					searchParentOperatorIds = operator.getParentId() + DELIMITER_FUNCTION + searchParentOperatorIds;
					programCode = operator.getParentId();
				}
			}
		}
		return searchParentOperatorIds;
	}
	
	public static void main(String[] args) {
		try {
//			System.out.println(LoginDAO.searchOperatorx());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}