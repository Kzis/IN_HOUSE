package com.cct.hrmdata.core.timemoney.service;

import java.util.List;
import java.util.Locale;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractService;
import com.cct.hrmdata.core.config.domain.SQLPath;
import com.cct.hrmdata.core.timemoney.domain.TimeMoney;

public class TimeMoneyService extends AbstractService{


	private TimeMoneyDAO dao = null;
	
	public TimeMoneyService(CCTConnection conn, Locale locale) {
		super(conn, locale);
		setDao(new TimeMoneyDAO());
		getDao().setSqlPath(SQLPath.TIME_MONEY_SQL);
	}
	
	public TimeMoneyDAO getDao() {
		return dao;
	}

	public void setDao(TimeMoneyDAO dao) {
		this.dao = dao;
	}

	
	
	
	
	
	
	
	
	/**
	 * SEARCH EMPLOYYE_ID
	 * @param secUserId
	 * @return
	 * @throws Exception
	 */
	protected String searchEmployeeId(String secUserId) throws Exception{
		return dao.searchEmployeeId(conn, secUserId);
	}
	
	
	
	/**
	 * SEARCH TIME_MONEY
	 * @param startDate
	 * @param endDate
	 * @param secUserIds
	 * @return
	 * @throws Exception
	 */
	protected List<TimeMoney> searchTimeMoney(String startDate, String endDate, String secUserIds) throws Exception{
		return dao.searchTimeMoney(conn, startDate, endDate, secUserIds, "");
	}
	
	
	
	
//	/**
//	 * COUNT SEARCH MAIN
//	 * 		COUNT SEARCH PERSON
//	 * 		COUNT SEARCH DEPARTMENT
//	 * 		COUNT SEARCH RANK PERSON
//	 * 		COUNT SEARCH RANK DEPARTMENT
//	 * 		COUNT SEARCH REPORT
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @return
//	 * @throws Exception
//	 */
//	protected long countSearchTimeMoney(String startDate, String endDate, String secUserIds) throws Exception{
//		return dao.searchCountTimeMoney(conn, startDate, endDate, secUserIds);
//	}
//	
//	
//	
//	/**
//	 * SEARCH MAIN
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @return
//	 * @throws Exception
//	 */
//	protected List<TimeMoney> searchMain(String startDate, String endDate, String secUserIds) throws Exception{
//		return dao.searchTimeMoney(conn, startDate, endDate, secUserIds, "");
//	}
//	
//	
//	
//	/**
//	 * SEARCH PERSON
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @return
//	 * @throws Exception
//	 */
//	protected List<TimeMoney> searchPerson(String startDate, String endDate, String secUserIds, String searchType) throws Exception{
//		List<TimeMoney> listResult = new ArrayList<TimeMoney>();
//		try{
//			
//			//[1] ค้นหาข้อมูลคนที่มาสาย
//			listResult = dao.searchTimeMoney(conn, startDate, endDate, secUserIds, searchType);
//			
////			for(int x=0; x<listResult.size(); x++){
////				LogUtil.TIME_MONEY.debug("#"+listResult.get(x).getFirstName() + " | "+ listResult.get(x).getWorkDate() +" ["+listResult.get(x).getLateTime()+"]");
////			}
//			
//			//[2] คำนวนเวลามาสายทั้งหมด
//			if(searchType.equals("MONTH")){
//				listResult = manageListPersonMonth(listResult);
//			}else if(searchType.equals("CHART")){
//				listResult = manageListPersonMonth(listResult);	//เรียงในรูปแบบเดือนก่อนแสดงเป็น Chart
//				listResult = manageListPersonChart(listResult); //manageListPersonChart
//			}
//			
//		}catch(Exception e){
//			throw e;
//		}
//				
//		return listResult;
//	}
//	
//	
//
//	/**
//	 * SEARCH DEPARTMENT
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @param searchType
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	protected List<TimeMoney> searchDepartment(String startDate, String endDate, String secUserIds, String searchType, String jsonMapDepartment) throws Exception{
//		List<TimeMoney> listResult = new ArrayList<TimeMoney>();
//		HashMap<String, String> mapDepartment = new HashMap<String, String>();
//		mapDepartment = (HashMap<String, String>) new Gson().fromJson(jsonMapDepartment, mapDepartment.getClass());
//		
//		//[1] ค้นหาข้อมูลคนที่มาสาย
//		listResult = dao.searchTimeMoney(conn, startDate, endDate, secUserIds, "");
//		
//		//[2] คำนวนเวลามาสายทั้งหมดของ ทุกคน ทุกแผนก
//		listResult = manageListDepartment(listResult, mapDepartment, searchType);
//		
//		return listResult;
//	}
//	
//	
//	/**
//	 * MANGE LIST DEPARTMENT
//	 * 				
//	 * @param listDepartment
//	 * @param mapDepartment
//	 * @param searchType	MONTH : แสดงผลรูปแบบเดือน
//	 * 						YEAR  : แสดงผลรูปแบบปี
//	 * @return
//	 * @throws Exception
//	 */
//	private List<TimeMoney> manageListDepartment(List<TimeMoney> listDepartment, HashMap<String, String> mapDepartment, String searchType)  throws Exception {
//		List<TimeMoney> listResult = new ArrayList<TimeMoney>();
//		HashMap<String, Long> mapDepartmentCount = new HashMap<String, Long>();
//		TimeMoney obj = null;
//		String departmentName = "";
//		String workDateOld 	= "";
//		String workDateCheck = "";
//		String workDate 	= "";
//		long countLateTime 	= 0;
//		
//		//[1] LOOP DEPARTMENT SEARCH FROM WS
//		if(listDepartment.size() > 0){
//			for(int i=0; i<listDepartment.size(); i++){
//				
//				departmentName = mapDepartment.get(listDepartment.get(i).getPositionId());
////				LogUtil.TIME_MONEY.debug("#DEPARTMENT_NAME : "+departmentName + " | "+listDepartment.get(i).getWorkDate());
//				
//				if(searchType.equals("") || searchType.equals("DAY")){
//					workDateCheck = listDepartment.get(i).getWorkDate();
//				}else{
//					workDateCheck = listDepartment.get(i).getMonths();
//				}
//				
//				
//				//วันที่เดียวกันไหม
//				if(workDateOld.equals(workDateCheck)){
//					//วันเดิม  เดือนเดิม
//					
//					if (mapDepartmentCount.containsKey(departmentName)) {
//						countLateTime = mapDepartmentCount.get(departmentName);
//						countLateTime += listDepartment.get(i).getLateTime();
//						mapDepartmentCount.put(departmentName, countLateTime);
//					}else{
//						mapDepartmentCount.put(departmentName, listDepartment.get(i).getLateTime());
//					}
//					
//				}else{
//					//วันใหม่ เดือนใหม่
//					if(i>0){
//						//[2] ADD LIST RESULT
//						//เอา haspmap มาวนลูปออกหาแต่ละแผนกในเดือนนี้ สายกี่นาที order by ค่าที่ สายมากสุด
//						mapDepartmentCount = sortByComparator(mapDepartmentCount, false); //ASC = true || DESC = false
//						for (Entry<String, Long> entry : mapDepartmentCount.entrySet()){
//							obj = new TimeMoney();
//							obj.setWorkDate(workDate);
//							obj.setDepartmentName(entry.getKey());
//							obj.setFirstName(entry.getKey());
//							obj.setLateTime(entry.getValue());
//							listResult.add(obj);
//						}
//					}
//					
//					mapDepartmentCount = new HashMap<String, Long>();
//					mapDepartmentCount.put(departmentName, listDepartment.get(i).getLateTime());
//				}
//				
//				
//				
//				//SET DATE OLD
//				if(searchType.equals("") || searchType.equals("DAY")){
//					workDateOld = listDepartment.get(i).getWorkDate();
//					workDate 	= listDepartment.get(i).getWorkDate();
//				}else{
//					workDateOld = listDepartment.get(i).getMonths();
//					workDate 	= listDepartment.get(i).getWorkDate();
//				}
//				
//			}//FOR
//		}//IF listDepartment.size() > 0
//		
//		
//		
//		//รอบสุดท้ายมาเซทค่าด้วย
//		if(!workDateOld.equals("")){
//			//[2] ADD LIST RESULT
//			//เอา haspmap มาวนลูปออกหาแต่ละแผนกในเดือนนี้ สายกี่นาที order by ค่าที่ สายมากสุด
//			mapDepartmentCount = sortByComparator(mapDepartmentCount, false); //ASC = true || DESC = false
//		    for (Entry<String, Long> entry : mapDepartmentCount.entrySet()){
//	            obj = new TimeMoney();
//				obj.setWorkDate(workDate);
//				obj.setDepartmentName(entry.getKey());
//				obj.setFirstName(entry.getKey());
//				obj.setLateTime(entry.getValue());
//	            listResult.add(obj);
//	        }
//		}
//		
//		
//		
//		//FIXME LOOP PRINT DISPLAY
////		LogUtil.TIME_MONEY.debug("\n\n\n###############################[ DISPLAY : "+ searchType +" ]#################################\n");
////		for(int x=0; x<listResult.size(); x++){
////			LogUtil.TIME_MONEY.debug("#"+listResult.get(x).getDepartmentName() + " | "+ listResult.get(x).getWorkDate() +" ["+listResult.get(x).getLateTime()+"]");
////		}
////		LogUtil.TIME_MONEY.debug("\n###############################################################################\n\n\n");
//	
//		
//		
//		return listResult;
//	}
//	
//	
//	/**
//	 * SEARCH RANK_PERSON
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @return
//	 * @throws Exception
//	 */
//	protected List<TimeMoney> searchRankPerson(String startDate, String endDate, String secUserIds) throws Exception{
//		HashMap<String, TimeMoney> mapRankPerson = new HashMap<String, TimeMoney>();
//		List<TimeMoney> listResult = new ArrayList<>();
//		TimeMoney obj = null;
//		String firstName = "";
//		long lateTime = 0;
//		long countLateTime = 0;
//		try{
//			
//			//[1] หาคนที่มาสาย
//			listResult = dao.searchTimeMoney(conn, startDate, endDate, secUserIds, "");
//			LogUtil.TIME_MONEY.debug("\n\n\n### TOTAL RANK PERSON ["+listResult.size()+"] \n\n\n");
//			
//			
//			//[2] คำนวนเวลาที่มาสายทั้งหมด
//			for(int i=0; i<listResult.size() ; i++){
////				LogUtil.TIME_MONEY.debug("\n-"+listResult.get(i).getFirstName() +"["+listResult.get(i).getEmployeeId()+"]"
////						+"\n-TIME LATE    : มาสาย "+listResult.get(i).getLateTime()+" นาที"
////						+"\n-------------------------------");
//				
//				firstName = listResult.get(i).getFirstName();
//				lateTime = listResult.get(i).getLateTime();
//				if(mapRankPerson.containsKey(firstName)){
//					obj = mapRankPerson.get(firstName);
//					countLateTime = obj.getLateTime();
//					countLateTime += lateTime;
//					obj.setLateTime(countLateTime);
//					mapRankPerson.put(firstName, obj);
//				}else{
//					mapRankPerson.put(firstName, listResult.get(i));
//				}
//			}
//			
//			
//			//[3] เรียงค่าคนที่มาสายมากทึ่สุด 10 อันดับแรก
//			mapRankPerson = sortByComparatorObject(mapRankPerson, false); //ASC = true || DESC = false
//			listResult = new ArrayList<>();  //CLEAR LIST
//			for (Entry<String, TimeMoney> entry : mapRankPerson.entrySet()){
//				if(listResult.size() < 10){ //TOP 10
//					listResult.add(entry.getValue());
////					LogUtil.TIME_MONEY.debug("##["+entry.getKey()+"] "+ obj.getLateTime() +"นาที\n-------------------------------");
//				}else{
//					break;
//				}
//			}
//			
//		}catch(Exception e){
//			throw e;
//		}
//		return listResult;
//	}
//
//	
//	
//	/**
//	 * SEARCH RANK_DEPARTMENT
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	protected List<TimeMoney> searchRankDepartment(String startDate, String endDate, String secUserIds, String jsonMapDepartment) throws Exception{
//		List<TimeMoney> listResult = new ArrayList<TimeMoney>();
//		TimeMoney obj = null;
//		long countLateTime = 0;
//		try{
//			//JSON TO HASHMAP
//			HashMap<String, String> mapDepartment = new HashMap<String, String>();
//			mapDepartment = (HashMap<String, String>) new Gson().fromJson(jsonMapDepartment, mapDepartment.getClass());
//			
//			
//			//[1] ค้นหาข้อมูลคนที่มาสาย
//			List<TimeMoney> listDepartment = dao.searchTimeMoney(conn, startDate, endDate, secUserIds, "");
//			LogUtil.TIME_MONEY.debug("### TOTAL RANK DEPARTMENT ["+listDepartment.size()+"]");
//			
//			
//			//[2] คำนวนเวลาที่มาสายทั้งหมด
//			String departmentName 	= "";
//			String workDate 		= "";
//			HashMap<String, Long> mapDepartmentCount = new HashMap<String, Long>();
//			for(int i=0 ; i<listDepartment.size(); i++){
//				departmentName = mapDepartment.get(listDepartment.get(i).getPositionId());
//				workDate = listDepartment.get(i).getWorkDate();
////				LogUtil.TIME_MONEY.debug("DE : "+departmentName + " : "+listDepartment.get(i).getWorkDate());
//				
//				//แผนกเดิม
//				if (mapDepartmentCount.containsKey(departmentName)) {
//					countLateTime = mapDepartmentCount.get(departmentName);
//					countLateTime += listDepartment.get(i).getLateTime();
//					mapDepartmentCount.put(departmentName, countLateTime);
//				}else{
//				//แผนกใหม่
//					mapDepartmentCount.put(departmentName, listDepartment.get(i).getLateTime());
//				}
//			}
//			
//			
//			//[3] เรียงค่าแผนกที่มาสายมากทึ่สุด 10 อันดับแรก
//			HashMap<String, Long> sortedMapDesc = sortByComparator(mapDepartmentCount, false); //ASC = true || DESC = false
//			for (Entry<String, Long> entry : sortedMapDesc.entrySet()){
//				if(listResult.size() < 10){
//					obj = new TimeMoney();
//					obj.setWorkDate(workDate);
//					obj.setDepartmentName(entry.getKey());
//					obj.setFirstName(entry.getKey());
//					obj.setLateTime(entry.getValue());
//					listResult.add(obj);
//				}else{
//					break;
//				}
//			}
//			
//		}catch(Exception e){
//			throw e;
//		}
//		
//		return listResult;
//	}
//	
//	
//	
//	/**
//	 * SEARCH REPORT
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	protected List<TimeMoney> searchReport(String startDate, String endDate, String secUserIds, String reportType, String jsonMapDepartment) throws Exception{
//		List<TimeMoney> listResult = new ArrayList<TimeMoney>();
//		try{
//			//JSON TO HASHMAP
//			HashMap<String, String> mapDepartment = new HashMap<String, String>();
//			mapDepartment = (HashMap<String, String>) new Gson().fromJson(jsonMapDepartment, mapDepartment.getClass());
//			
//			
//			//[1] ค้นหาข้อมูลคนที่มาสาย
//			listResult =  dao.searchTimeMoney(conn, startDate, endDate, secUserIds, "REPORT");
//			
//			
//			//[2] เรียงลำดับมาสายมากสุด และ แยกตามแผนก
//			if(reportType.equals("TOPTEN")){
//				listResult = manageListReportTopTen(listResult, mapDepartment);
//			}else{
//				listResult = manageListReport(listResult, mapDepartment);
//			}
//			
//			
//		}catch(Exception e){
//			throw e;
//		}
//		
//		return listResult;
//	}
//	
//	
//
//	/**
//	 * MANAGE LIST PERSON MONTH
//	 * @param listPerson
//	 * @return
//	 * @throws Exception
//	 */
//	private List<TimeMoney> manageListPersonMonth(List<TimeMoney> listPerson)  throws Exception {
//		List<TimeMoney> listResult = new ArrayList<TimeMoney>();
//		TimeMoney temp = null;
//		TimeMoney tempOld = null;
//		String workDateOld 	= "";
//		String workDateCheck = "";
//		String workDate 	= "";
//		String nameOld 		= "";
//		long countLateTime 	= 0;
//		
//		//[1] LOOP PERSON SEARCH FROM WS
//		if(listPerson.size() > 0){
//			for(int i=0; i<listPerson.size(); i++){
//				temp = listPerson.get(i);
//				LogUtil.TIME_MONEY.debug("+"+temp.getFirstName() + " | "+ temp.getWorkDate() +" ["+temp.getLateTime()+"]");
//				workDateCheck = listPerson.get(i).getMonths();
//				//เดือนเดียวกันไหม
//				if(workDateOld.equals(workDateCheck)){
//					//เดือนเดิม
//					//คนเดิมไหม
//					if(nameOld.equals(temp.getFirstName())){
//						countLateTime += listPerson.get(i).getLateTime();
//					}else{
//						addToListResult(listResult, tempOld, tempOld.getWorkDate(), countLateTime); //ADD คนเก่าแล้วบวกใหม่
//						countLateTime = 0;
//						countLateTime += listPerson.get(i).getLateTime();
//					}
//					
//				}else{
//					if(i > 0){
//						addToListResult(listResult, tempOld, tempOld.getWorkDate(), countLateTime);
//					}
//					
//					countLateTime = 0;
//					countLateTime = listPerson.get(i).getLateTime();
//				}
//				
//				tempOld = listPerson.get(i);
//				workDate = listPerson.get(i).getWorkDate();
//				workDateOld = listPerson.get(i).getMonths();
//				nameOld = listPerson.get(i).getFirstName();
//				
//			}
//		}
//		
//		//รอบสุดท้ายมาเซทค่าด้วย
//		if(!workDateOld.equals("")){
//			//[2] ADD LIST RESULT
//			temp = listPerson.get(listPerson.size()-1); //คนสุดท้าย
//			addToListResult(listResult, temp, workDate, countLateTime);
//		}
//		
//		
//		
//		//FIXME LOOP PRINT DISPLAY
////		for(int x=0; x<listResult.size(); x++){
////			LogUtil.TIME_MONEY.debug("#"+listResult.get(x).getFirstName() + " | "+ listResult.get(x).getWorkDate() +" ["+listResult.get(x).getLateTime()+"]");
////		}
////		LogUtil.TIME_MONEY.debug("\n###############################################################################\n\n\n");
//		
//		return listResult;
//	}
//	
//	
//	
//	/**
//	 * MANAGE LIST PERSON CHART
//	 * @param listPerson
//	 * @return
//	 * @throws Exception
//	 */
//	private List<TimeMoney> manageListPersonChart(List<TimeMoney> listPerson)  throws Exception {
//		HashMap<String, List<TimeMoney>> mapPersonChart = new HashMap<String, List<TimeMoney>>();
//		List<TimeMoney> listTemp = null;
//		TimeMoney temp = null;
//		
//		//[1] LOOP PERSON หาคนที่ชื่อเดียวกันเพื่อนำไปวาด CHART
//		if(listPerson.size() > 0){
//			for(int i=0; i<listPerson.size(); i++){
//				temp = listPerson.get(i);
//				if(mapPersonChart.containsKey(temp.getEmployeeId().toString())){
//					listTemp = mapPersonChart.get(temp.getEmployeeId().toString());
//					listTemp.add(temp);
//					mapPersonChart.put(temp.getEmployeeId().toString(), listTemp);
//				}else{
//					listTemp = new ArrayList<TimeMoney>();
//					listTemp.add(temp);
//					mapPersonChart.put(temp.getEmployeeId().toString(), listTemp);
//				}
//			}
//		}
//		
//		
//		
//		listPerson = new ArrayList<TimeMoney>(); // CLEAR FOR RETURN
//		for (Entry<String, List<TimeMoney>> entry : mapPersonChart.entrySet()){
//			listTemp = entry.getValue();
//			for(TimeMoney obj : listTemp){
//				listPerson.add(obj);
//			}
//			
//		}
//		
//		//FIXME LOOP PRINT DISPLAY
////		for(int x=0; x<listPerson.size(); x++){
////			LogUtil.TIME_MONEY.debug("฿"+listPerson.get(x).getFirstName() + " | "+ listPerson.get(x).getWorkDate() +" ["+listPerson.get(x).getLateTime()+"]");
////		}
////		LogUtil.TIME_MONEY.debug("\n###############################################################################\n\n\n");
//		
//		return listPerson;
//	}
//	
//	
//	
//	/**
//	 * MANAGE LIST REPORT
//	 * @param listReport
//	 * @param mapDepartment
//	 * @return
//	 * @throws Exception
//	 */
//	private List<TimeMoney> manageListReport(List<TimeMoney> listReport, HashMap<String, String> mapDepartment)  throws Exception {
//		List<TimeMoney> listResult = new ArrayList<TimeMoney>();
//		List<TimeMoney> listTemp = null;
//		HashMap<String, List<TimeMoney>> mapTemp = new HashMap<String, List<TimeMoney>>();
//		TimeMoney obj = null;
//		String departmentName = "";
//		
//		LogUtil.TIME_MONEY.debug("\n\n\n###############################[ DISPLAY : SEARCH_REPORT ]#################################\n");
//		
//		//
//		//[1] แยกแผนก
//		//
//		if(listReport.size() > 0){
//			for(int x=0; x<listReport.size(); x++){
//				//SET DEPARTMENT NAME
//				obj = listReport.get(x);
//				departmentName = mapDepartment.get(obj.getPositionId());
//				obj.setDepartmentName(departmentName);
//				
//				//เรียงค่าตามแผนกก่อน (ต้อง map positionId = department inhouse)
//				if(mapTemp.containsKey(departmentName)){
//					//แผนกเดิม
//					listTemp = mapTemp.get(departmentName);
//					listTemp.add(obj);
//					mapTemp.put(departmentName, listTemp);
//				}else{
//					//แผนกใหม่
//					listTemp = new ArrayList<TimeMoney>();
//					listTemp.add(obj);
//					mapTemp.put(departmentName, listTemp);
//				}
//			}
//			
//			
//			
//			//
//			//[2] เรียงลำดับคนมาสายมากสุด ในแต่ละแผนก
//			//
//			
//			
//			
//			//
//			//[3] นำค่าใส่ใน LIST FOR DISPLAY
//			//
//			for(HashMap.Entry<String, List<TimeMoney>> pairMap : mapTemp.entrySet()) {
////			    pairMap.getKey();
//			    for(TimeMoney objTimeMoney : pairMap.getValue()) {
////			    	LogUtil.TIME_MONEY.debug("#"+objTimeMoney.getDepartmentName()+ " | "+objTimeMoney.getFirstName() + " | "+ objTimeMoney.getWorkDate() +" ["+objTimeMoney.getLateTime()+"]");
//			    	listResult.add(objTimeMoney);
//			    }
//			}
//		}//SIZE
//		
//		LogUtil.TIME_MONEY.debug("\n###############################################################################\n\n\n");
//		
//		return listResult;
//	}
//	
//	
//	
//	/**
//	 * MANAGE LIST REPORT TOP_TEN
//	 * @param listReport
//	 * @param mapDepartment
//	 * @return
//	 * @throws Exception
//	 */
//	private List<TimeMoney> manageListReportTopTen(List<TimeMoney> listReport, HashMap<String, String> mapDepartment)  throws Exception {
//		List<TimeMoney> listResult = new ArrayList<TimeMoney>();
//		HashMap<String, TimeMoney> mapTemp = new HashMap<String, TimeMoney>();
//		TimeMoney obj = null;
//		TimeMoney objTemp = null;
//		long countLateTime = 0;
//		String departmentName = "";
//		
//		LogUtil.TIME_MONEY.debug("\n\n\n###############################[ DISPLAY : SEARCH_REPORT_TOPTEN ]#################################\n");
//		
//		if(listReport.size() > 0){
//			//
//			//[1] map แผนก และ คำนวนเวลามาสาย
//			//
//			for(int i=0; i<listReport.size(); i++){
//				//SET DEPARTMENT NAME
//				obj = listReport.get(i);
//				departmentName = mapDepartment.get(obj.getPositionId());
//				obj.setDepartmentName(departmentName);
//				
//				if(mapTemp.containsKey(obj.getEmployeeId()+"")){
//					//คนเดิม
//					objTemp = mapTemp.get(obj.getEmployeeId()+"");
//					countLateTime = objTemp.getLateTime();
//					countLateTime += obj.getLateTime();
//					obj.setLateTime(countLateTime);
//					mapTemp.put(obj.getEmployeeId()+"", obj);
//				}else{
//					//คนใหม่
//					mapTemp.put(obj.getEmployeeId()+"", obj);
//				}
//				
//			}
//	
//			
//			//
//			//[2] หาคนที่มาสายมากสุด 10 อันดับแรก
//			//
//			mapTemp = sortByComparatorObject(mapTemp, false); //ASC = true || DESC = false
//		    for (Entry<String, TimeMoney> entry : mapTemp.entrySet()){
//		    	if(listResult.size() < 10){
//		    		listResult.add(entry.getValue());
//		    	}else{
//		    		break;
//		    	}
//	        }
//			
//		    
//		    //PRINT DISPLAY TEST //FIXME
////			for(HashMap.Entry<String, TimeMoney> pairMap : mapTemp.entrySet()) {
////				obj = pairMap.getValue();
////			    LogUtil.TIME_MONEY.debug("#"+obj.getDepartmentName()+ " | "+obj.getFirstName() + " | "+ obj.getWorkDate() +" ["+obj.getLateTime()+"]");
////			}
//		}
//		
////		LogUtil.TIME_MONEY.debug("\n###############################################################################\n\n\n");
//		
//		return listResult;
//	}
//	
//	
//	
//	/**
//	 * ADD TO LIST RESULT (SEARCH_PERSON เรียกใช้)
//	 * @param listResult
//	 * @param temp
//	 * @param workDate
//	 * @param lateTime
//	 * @throws Exception
//	 */
//	private void addToListResult(List<TimeMoney> listResult, TimeMoney temp, String workDate,long lateTime) throws Exception {
//		TimeMoney obj = temp;
//		
//		obj.setWorkDate(workDate);
//		obj.setLateTime(lateTime);
//        listResult.add(obj);
//	}
//	
//	
//	
//	/**
//	 * SORT BY COMPARATOR OBJECT
//	 * 				SORT HASH_MAP => OBJECT
//	 * @param unsortMap
//	 * @param order
//	 * @return
//	 */
//	private HashMap<String, TimeMoney> sortByComparatorObject(HashMap<String, TimeMoney> unsortMap, final boolean order){
//        List<Entry<String, TimeMoney>> list = new LinkedList<Entry<String, TimeMoney>>(unsortMap.entrySet());
//        Collections.sort(list, new Comparator<Entry<String, TimeMoney>>(){
//            public int compare(Entry<String, TimeMoney> o1, Entry<String, TimeMoney> o2){
//                if (order){
//                	TimeMoney tm2 = o2.getValue();
//                	TimeMoney tm1 = o1.getValue();
//                    return tm1.getLateTime().compareTo(tm2.getLateTime());
//                }else{
//                	TimeMoney tm2 = o2.getValue();
//                	TimeMoney tm1 = o1.getValue();
//                    return tm2.getLateTime().compareTo(tm1.getLateTime());
//                }
//            }
//        });
//
//        // Maintaining insertion order with the help of LinkedList
//        HashMap<String, TimeMoney> sortedMap = new LinkedHashMap<String, TimeMoney>();
//        for (Entry<String, TimeMoney> entry : list){
//            sortedMap.put(entry.getKey(), entry.getValue());
//        }
//
//        return sortedMap;
//    }
//	
//	
//	
//	/**
//	 * SORT BY COMPARATOR
//	 * 				SORT HASH_MAP => Long
//	 * @param unsortMap
//	 * @param order
//	 * @return
//	 */
//	private HashMap<String, Long> sortByComparator(HashMap<String, Long> unsortMap, final boolean order){
//        List<Entry<String, Long>> list = new LinkedList<Entry<String, Long>>(unsortMap.entrySet());
//
//        Collections.sort(list, new Comparator<Entry<String, Long>>(){
//            public int compare(Entry<String, Long> o1,
//                    Entry<String, Long> o2){
//                if (order){
//                    return o1.getValue().compareTo(o2.getValue());
//                }else{
//                    return o2.getValue().compareTo(o1.getValue());
//                }
//            }
//        });
//
//        // Maintaining insertion order with the help of LinkedList
//        HashMap<String, Long> sortedMap = new LinkedHashMap<String, Long>();
//        for (Entry<String, Long> entry : list){
//            sortedMap.put(entry.getKey(), entry.getValue());
//        }
//
//        return sortedMap;
//    }
//	
	
	
}
