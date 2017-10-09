/*
 * #################################################################################
 * # SQL ORACLE
 * #################################################################################
 */


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : SEARCH EMPLOYEE ID
Description :
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchEmployeeId{
	SELECT EMPLOYEEID FROM SEC_USER 
	WHERE USERID = %s
}





/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : SEARCH TIME MONEY
Description :
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchTimeMoney{
/*——————————————————————————————
— [01] หาคนมาสายทั้งหมด
———————————————————————————————*/ 
	SELECT
		EM.EMPLOYEEID
		,SE.USERID
		,EM.CARDID 
		,EM.POSITIONID 
	 	,EM.FIRSTNAMETHA 
	  	,EM.LASTNAMETHA 
	  	,EM.NICKNAME 
		,EM.FIRSTNAMETHA || '  ' || EM.LASTNAMETHA AS FIRSTNAME
		,CONCAT(TO_CHAR(CA.WORKDATE,'dd/mm/')
		,TO_CHAR(CA.WORKDATE,'yyyy')+543 ) AS  WORKDATE 
		,TO_CHAR(CA.WORKDATE,'dd/mm/yyyy') AS WORKDATES
		,CAST(WT.WORKTIMEFROM AS INT) - CAST(CA.WORKTIMEIN1 AS INT) AS LATE
	  	,TO_CHAR(CA.WORKDATE,'dd') AS DAYS 
	  	,TO_CHAR(CA.WORKDATE,'mm') AS MONTHS 
	  	,TO_CHAR(CA.WORKDATE,'yyyy') AS YEARS 
		,TO_CHAR(TO_DATE(CA.WORKTIMEIN1,'hh24mi'),'hh24mi') AS WORKTIME_IN 			/*เวลาเข้างานปกติ*/
		,TO_CHAR(TO_DATE(CA.WORKTIMEOUT2,'hh24mi'),'hh24mi') AS WORKTIME_OUT 		/*เวลาออกงานปกติ*/
		,TO_CHAR(TO_DATE(WT.WORKTIMEFROM,'hh24mi'),'hh24mi') AS SCANTIME_IN     	/*เวลาสแกนนิ้วเข้างาน*/
		,TO_CHAR(TO_DATE(WT.WORKTIMETO ,'hh24mi'),'hh24mi') AS SCANTIME_OUT     	/*เวลาสแกนนิ้วออกงาน*/
	  	,TO_CHAR(TO_DATE(WT.WORKTIMEFROM,'hh24mi'),'hh24:mi') AS SCANTIME_IN_DISPLAY/*เวลาสแกนนิ้วเข้างาน*/ 
	FROM EMP_MEMBER EM	
		INNER JOIN TAD_WORK_TRANSACTION WT ON EM.EMPLOYEEID = WT.EMPLOYEEID
		INNER JOIN SPC_CALENDARUSER CA ON EM.EMPLOYEEID = CA.EMPLOYEEID
		INNER JOIN SEC_USER SE ON EM.EMPLOYEEID = SE.EMPLOYEEID 
	WHERE 1=1
		AND (CA.WORKDATE = TO_DATE(WT.WORKDATE,'yyyymmdd') AND CA.EMPLOYEEID = WT.EMPLOYEEID)
		AND (CA.WORKDATE BETWEEN TO_DATE(%s,'dd/mm/yyyy')				/* PARAM 1 : DATE_START */
		AND TO_DATE(%s,'dd/mm/yyyy'))                        			/* PARAM 2 : DATE_END */
		AND CAST(WT.WORKTIMEFROM AS INT) > CAST(CA.WORKTIMEIN1 AS INT)	
		--AND SE.USERID IN()         									
		%s 
		/* PARAM 3 : SEC_USERID */

  
MINUS
  
  

/*——————————————————————————————
— [02] เช็คทำงานนอกสถานที่
———————————————————————————————*/ 
	SELECT
		EE.EMPLOYEEID 
		,SE.USERID
		,EE.CARDID 
		,EE.POSITIONID 
		,EE.FIRSTNAMETHA 
		,EE.LASTNAMETHA 
		,EE.NICKNAME 
		,EE.FIRSTNAMETHA || '  ' || EE.LASTNAMETHA AS FIRSTNAME
		,CONCAT(TO_CHAR(C_DATE,'dd/mm/')
		,TO_CHAR(C_DATE,'yyyy')+543 ) AS  WORKDATE 
		,TO_CHAR(C_DATE,'dd/mm/yyyy') AS WORKDATES
		,CAST(WT.WORKTIMEFROM AS INT) - CAST(CA.WORKTIMEIN1 AS INT) AS LATE
		,TO_CHAR(C_DATE,'dd') AS DAYS 
		,TO_CHAR(C_DATE,'mm') AS MONTHS 
		,TO_CHAR(C_DATE,'yyyy') AS YEARS 
		,TO_CHAR(TO_DATE(CA.WORKTIMEIN1,'hh24mi'),'hh24mi') AS WORKTIME_IN			/*เวลาเข้างานปกติ*/
		,TO_CHAR(TO_DATE(CA.WORKTIMEOUT2,'hh24mi'),'hh24mi') AS WORKTIME_OUT		/*เวลาออกงานปกติ*/
		,TO_CHAR(TO_DATE(WT.WORKTIMEFROM,'hh24mi'),'hh24mi') AS SCANTIME_IN 		/*เวลาสแกนนิ้วเข้างาน*/
		,TO_CHAR(TO_DATE(WT.WORKTIMETO ,'hh24mi'),'hh24mi') AS SCANTIME_OUT      	/*เวลาสแกนนิ้วออกงาน*/
	  	,TO_CHAR(TO_DATE(WT.WORKTIMEFROM,'hh24mi'),'hh24:mi') AS SCANTIME_IN_DISPLAY/*เวลาสแกนนิ้วเข้างาน*/ 
	FROM EMP_MEMBER EE	
		INNER JOIN SPC_CALENDARUSER CA ON EE.EMPLOYEEID = CA.EMPLOYEEID
		INNER JOIN SEC_USER SE ON EE.EMPLOYEEID = SE.EMPLOYEEID 
		INNER JOIN (
			WITH CALENDAR AS (
				SELECT TO_DATE(%s, 'dd/mm/yyyy') + ROWNUM - 1 C_DATE		/* PARAM 4 : DATE_START */
				FROM DUAL
				CONNECT BY LEVEL <= TO_DATE(%s, 'dd/mm/yyyy')  				/* PARAM 5 : DATE_END */
				- TO_DATE(%s, 'dd/mm/yyyy') + 1 							/* PARAM 6 : DATE_START */
			)
	
	 		SELECT
	   			A.EMPLOYEEID, A.POSITIONID, C.C_DATE, 
	       	CASE 
	      		WHEN C.C_DATE = A.ONSITEDATEFROM1 THEN A.ONSITETIMEFROM1
	       		--WHEN C.C_DATE > A.ONSITEDATEFROM1 AND C.C_DATE < A.ONSITEDATETO2 THEN '0830'
	       		WHEN C.C_DATE = A.ONSITEDATETO2 THEN A.ONSITETIMEFROM2
	        END ONSITETIMEFROM,
	        CASE 
	        	WHEN C.C_DATE = A.ONSITEDATEFROM1 THEN A.ONSITETIMETO1
	          	--WHEN C.C_DATE > A.ONSITEDATEFROM1 AND C.C_DATE < A.ONSITEDATETO2 THEN '1730'
	        	WHEN C.C_DATE = A.ONSITEDATETO2 THEN A.ONSITETIMETO2
	      	END ONSITETIMETO
	                          
	                  
			FROM CALENDAR C
			-- เช็คทำงานนอกสถานที่
				INNER JOIN
	         		((
		           		SELECT T.EMPLOYEEID, T.POSITIONID, SE.USERID,
		              		T.ONSITEDATEFROM1, T.ONSITETIMEFROM1, T.ONSITETIMETO1,
		             		T.ONSITEDATETO2, T.ONSITETIMEFROM2, T.ONSITETIMETO2 
		                FROM STS_WORKONSITE S ,TAD_WORKONSITE_HISTORY T
	                  	INNER JOIN SEC_USER SE ON T.EMPLOYEEID = SE.EMPLOYEEID 
		                WHERE S.WORKONSITEID = T.WORKONSITEID AND T.ONSITESTATUS <> 'C'
		                --AND T.EMPLOYEEID=3183,2401
	              	) UNION (
	              		SELECT T.EMPLOYEEID, T.POSITIONID, SE.USERID,
	                    T.ONSITEDATEFROM1, T.ONSITETIMEFROM1, T.ONSITETIMETO1,
	                    T.ONSITEDATETO2, T.ONSITETIMEFROM2, T.ONSITETIMETO2 
	                    FROM STS_WORKONSITE S ,TAD_WORKONSITE T
	                    INNER JOIN SEC_USER SE ON T.EMPLOYEEID = SE.EMPLOYEEID 
	                    WHERE S.WORKONSITEID = T.WORKONSITEID AND T.ONSITESTATUS <> 'C'
	                    --AND T.EMPLOYEEID=3183,2401
	              	)) A ON (C.C_DATE >= A.ONSITEDATEFROM1 AND C.C_DATE <= ONSITEDATETO2)
	
	                
	        WHERE A.ONSITEDATEFROM1 >= TO_DATE(%s,'dd/mm/yyyy') 	/* PARAM 7 : DATE_START */
	        AND A.ONSITEDATETO2 <= TO_DATE(%s,'dd/mm/yyyy')			/* PARAM 8 : DATE_END */
	        %s
	        /* PARAM 9 : SEC_USERID */
	        --AND A.EMPLOYEEID IN(3183,2401)
		) WO ON WO.EMPLOYEEID = EE.EMPLOYEEID
	
		INNER JOIN TAD_WORK_TRANSACTION WT ON EE.EMPLOYEEID = WT.EMPLOYEEID
	WHERE 1=1
		AND (CA.WORKDATE = TO_DATE(WT.WORKDATE,'yyyymmdd') AND CA.EMPLOYEEID = WT.EMPLOYEEID)
		AND (CA.WORKDATE BETWEEN TO_DATE(%s,'dd/mm/yyyy')			/* PARAM 10 : DATE_START */
		AND TO_DATE(%s,'dd/mm/yyyy'))                        		/* PARAM 11 : DATE_END */
		AND CAST(WT.WORKTIMEFROM AS INT) > CAST(CA.WORKTIMEIN1 AS INT)
	  	%s
	  	  /* PARAM 3 : SEC_USERID */
	
	   
   
MINUS
  

  
/*——————————————————————————————
— [03] เช็คลาพักผ่อน ลาพักร้อน
———————————————————————————————*/
	SELECT 
		EEE.EMPLOYEEID
		,SE.USERID
	  	,EEE.CARDID 
	  	,EEE.POSITIONID 
	  	,EEE.FIRSTNAMETHA 
	  	,EEE.LASTNAMETHA 
	  	,EEE.NICKNAME 
		,EEE.FIRSTNAMETHA || '  ' || EEE.LASTNAMETHA AS FIRSTNAME
		,CONCAT(TO_CHAR(C_DATE,'dd/mm/')
		,TO_CHAR(C_DATE,'yyyy')+543 ) AS  WORKDATE 
		,TO_CHAR(C_DATE,'dd/mm/yyyy') AS WORKDATES
		,CAST(WTT.WORKTIMEFROM AS INT) - CAST(CAA.WORKTIMEIN1 AS INT) AS LATE
	  	,TO_CHAR(C_DATE,'dd') AS DAYS 
	  	,TO_CHAR(C_DATE,'mm') AS MONTHS 
	  	,TO_CHAR(C_DATE,'yyyy') AS YEARS 
		,TO_CHAR(TO_DATE(CAA.WORKTIMEIN1,'hh24mi'),'hh24mi') AS WORKTIME_IN     	/*เวลาเข้างานปกติ*/
		,TO_CHAR(TO_DATE(CAA.WORKTIMEOUT2,'hh24mi'),'hh24mi') AS WORKTIME_OUT  		/*เวลาออกงานปกติ*/
		,TO_CHAR(TO_DATE(WTT.WORKTIMEFROM,'hh24mi'),'hh24mi') AS SCANTIME_IN    	/*เวลาสแกนนิ้วเข้างาน*/
		,TO_CHAR(TO_DATE(WTT.WORKTIMETO ,'hh24mi'),'hh24mi') AS SCANTIME_OUT    	/*เวลาสแกนนิ้วออกงาน*/
	  	,TO_CHAR(TO_DATE(WTT.WORKTIMEFROM,'hh24mi'),'hh24:mi') AS SCANTIME_IN_DISPLAY/*เวลาสแกนนิ้วเข้างาน*/ 
		
	FROM EMP_MEMBER EEE
		INNER JOIN SPC_CALENDARUSER CAA ON EEE.EMPLOYEEID = CAA.EMPLOYEEID
	  	INNER JOIN SEC_USER SE ON EEE.EMPLOYEEID = SE.EMPLOYEEID 
		INNER JOIN (
	    	WITH CALENDAR AS (
	        	SELECT TO_DATE(%s, 'dd/mm/yyyy') + ROWNUM - 1 C_DATE		/* PARAM 13 : DATE_START */
	          	FROM DUAL
	        	CONNECT BY LEVEL <= TO_DATE(%s, 'dd/mm/yyyy') 				/* PARAM 14 : DATE_END */
	        	- TO_DATE(%s, 'dd/mm/yyyy') + 1	 							/* PARAM 15 : DATE_START */
	        )
	
	        SELECT
	       	B.EMPLOYEEID, B.POSITIONID, C.C_DATE,
	        CASE 
	        	WHEN C.C_DATE = B.LEAVEDATEFROM1 THEN B.LEAVETIMEFROM1
	            --WHEN C.C_DATE > B.LEAVEDATEFROM1 AND C.C_DATE < B.LEAVEDATETO2 THEN '0830'
	            WHEN C.C_DATE = B.LEAVEDATETO2 THEN B.LEAVETIMEFROM2
	        END LEAVETIMEFROM,
	        CASE 
	        	WHEN C.C_DATE = B.LEAVEDATEFROM1 THEN B.LEAVETIMETO1
	            --WHEN C.C_DATE > B.LEAVEDATEFROM1 AND C.C_DATE < B.LEAVEDATETO2 THEN '1730'
	            WHEN C.C_DATE = B.LEAVEDATETO2 THEN B.LEAVETIMETO2
	        END LEAVETIMETO
	                          
	                  
	        FROM CALENDAR C
	        	-- เช็คลาพักผ่อน ลาพักร้อน
	     		INNER JOIN
	            	((
	             		SELECT T.EMPLOYEEID, T.POSITIONID,  SE.USERID,
	                		T.LEAVEDATEFROM1, T.LEAVETIMEFROM1, T.LEAVETIMETO1,
	                   		T.LEAVEDATETO2, T.LEAVETIMEFROM2, T.LEAVETIMETO2 
	                 	FROM STS_LEAVETYPE S ,TAD_LEAVE_HISTORY T
	                  	INNER JOIN SEC_USER SE ON T.EMPLOYEEID = SE.EMPLOYEEID 
	                 	WHERE S.LEAVETYPEID=T.LEAVETYPEID AND T.LEAVESTATUS <> 'C' 
	     				--AND T.EMPLOYEEID=3183,2401
	           		) UNION (
	                	SELECT T.EMPLOYEEID, T.POSITIONID,  SE.USERID,
	               			T.LEAVEDATEFROM1, T.LEAVETIMEFROM1, T.LEAVETIMETO1,
	                    	T.LEAVEDATETO2, T.LEAVETIMEFROM2, T.LEAVETIMETO2 
	                 	FROM STS_LEAVETYPE S ,TAD_LEAVE T
	                  	INNER JOIN SEC_USER SE ON T.EMPLOYEEID = SE.EMPLOYEEID 
	               		WHERE S.LEAVETYPEID=T.LEAVETYPEID AND T.LEAVESTATUS <> 'C'
						--AND T.EMPLOYEEID=3183,2401
	  				)) B ON (C.C_DATE >= B.LEAVEDATEFROM1 AND C.C_DATE <= LEAVEDATETO2)
	
	                
	       	WHERE B.LEAVEDATEFROM1 >= TO_DATE(%s,'dd/mm/yyyy') 			/* PARAM 16 : DATE_START */
	       	AND B.LEAVEDATETO2 <= TO_DATE(%s,'dd/mm/yyyy')				/* PARAM 17 : DATE_END */
	     	%s
	     	/* PARAM 18 : SEC_USERID */
	  		--AND B.EMPLOYEEID IN(3183,2401)
	  	)SI ON SI.EMPLOYEEID = EEE.EMPLOYEEID
	  
		INNER JOIN TAD_WORK_TRANSACTION WTT ON EEE.EMPLOYEEID = WTT.EMPLOYEEID
	WHERE 1=1
		AND (CAA.WORKDATE = TO_DATE(WTT.WORKDATE,'yyyymmdd') AND CAA.EMPLOYEEID = WTT.EMPLOYEEID)
		AND (CAA.WORKDATE BETWEEN TO_DATE(%s,'dd/mm/yyyy')				/* PARAM 19 : DATE_START */
		AND TO_DATE(%s,'dd/mm/yyyy'))                        			/* PARAM 20 : DATE_END */
		AND CAST(WTT.WORKTIMEFROM AS INT) > CAST(CAA.WORKTIMEIN1 AS INT)
		%s
		/* PARAM 21 : SEC_USERID */
	--AND EEE.EMPLOYEEID IN(3183,2401)
	--ORDER BY MONTHS DESC, DAYS DESC 
	%s
	/* PARAM 22 : ORDER BY */

}







/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : COUNT TIME MONEY
Description :
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountTimeMoney{

SELECT COUNT(*) AS TOT FROM(
	/*——————————————————————————————
	— [01] หาคนมาสายทั้งหมด
	———————————————————————————————*/ 
		SELECT
			EM.EMPLOYEEID
			,SE.USERID
			,EM.CARDID 
			,EM.POSITIONID 
		 	,EM.FIRSTNAMETHA 
		  	,EM.LASTNAMETHA 
		  	,EM.NICKNAME 
			,EM.FIRSTNAMETHA || '  ' || EM.LASTNAMETHA AS FIRSTNAME
			,CONCAT(TO_CHAR(CA.WORKDATE,'dd/mm/')
			,TO_CHAR(CA.WORKDATE,'yyyy')+543 ) AS  WORKDATE 
			,TO_CHAR(CA.WORKDATE,'dd/mm/yyyy') AS WORKDATES
			,CAST(WT.WORKTIMEFROM AS INT) - CAST(CA.WORKTIMEIN1 AS INT) AS LATE
		  	,TO_CHAR(CA.WORKDATE,'dd') AS DAYS 
		  	,TO_CHAR(CA.WORKDATE,'mm') AS MONTHS 
		  	,TO_CHAR(CA.WORKDATE,'yyyy') AS YEARS 
			,TO_CHAR(TO_DATE(CA.WORKTIMEIN1,'hh24mi'),'hh24mi') AS WORKTIME_IN 			/*เวลาเข้างานปกติ*/
			,TO_CHAR(TO_DATE(CA.WORKTIMEOUT2,'hh24mi'),'hh24mi') AS WORKTIME_OUT 		/*เวลาออกงานปกติ*/
			,TO_CHAR(TO_DATE(WT.WORKTIMEFROM,'hh24mi'),'hh24mi') AS SCANTIME_IN     	/*เวลาสแกนนิ้วเข้างาน*/
			,TO_CHAR(TO_DATE(WT.WORKTIMETO ,'hh24mi'),'hh24mi') AS SCANTIME_OUT     	/*เวลาสแกนนิ้วออกงาน*/
		  	,TO_CHAR(TO_DATE(WT.WORKTIMEFROM,'hh24mi'),'hh24:mi') AS SCANTIME_IN_DISPLAY/*เวลาสแกนนิ้วเข้างาน*/ 
		FROM EMP_MEMBER EM	
			INNER JOIN TAD_WORK_TRANSACTION WT ON EM.EMPLOYEEID = WT.EMPLOYEEID
			INNER JOIN SPC_CALENDARUSER CA ON EM.EMPLOYEEID = CA.EMPLOYEEID
			INNER JOIN SEC_USER SE ON EM.EMPLOYEEID = SE.EMPLOYEEID 
		WHERE 1=1
			AND (CA.WORKDATE = TO_DATE(WT.WORKDATE,'yyyymmdd') AND CA.EMPLOYEEID = WT.EMPLOYEEID)
			AND (CA.WORKDATE BETWEEN TO_DATE(%s,'dd/mm/yyyy')				/* PARAM 1 : DATE_START */
			AND TO_DATE(%s,'dd/mm/yyyy'))                        			/* PARAM 2 : DATE_END */
			AND CAST(WT.WORKTIMEFROM AS INT) > CAST(CA.WORKTIMEIN1 AS INT)	
			--AND SE.USERID IN()         									
			%s 
			/* PARAM 3 : SEC_USERID */
	
	  
	MINUS
	  
	  
	
	/*——————————————————————————————
	— [02] เช็คทำงานนอกสถานที่
	———————————————————————————————*/ 
		SELECT
			EE.EMPLOYEEID 
			,SE.USERID
			,EE.CARDID 
			,EE.POSITIONID 
			,EE.FIRSTNAMETHA 
			,EE.LASTNAMETHA 
			,EE.NICKNAME 
			,EE.FIRSTNAMETHA || '  ' || EE.LASTNAMETHA AS FIRSTNAME
			,CONCAT(TO_CHAR(C_DATE,'dd/mm/')
			,TO_CHAR(C_DATE,'yyyy')+543 ) AS  WORKDATE 
			,TO_CHAR(C_DATE,'dd/mm/yyyy') AS WORKDATES
			,CAST(WT.WORKTIMEFROM AS INT) - CAST(CA.WORKTIMEIN1 AS INT) AS LATE
			,TO_CHAR(C_DATE,'dd') AS DAYS 
			,TO_CHAR(C_DATE,'mm') AS MONTHS 
			,TO_CHAR(C_DATE,'yyyy') AS YEARS 
			,TO_CHAR(TO_DATE(CA.WORKTIMEIN1,'hh24mi'),'hh24mi') AS WORKTIME_IN			/*เวลาเข้างานปกติ*/
			,TO_CHAR(TO_DATE(CA.WORKTIMEOUT2,'hh24mi'),'hh24mi') AS WORKTIME_OUT		/*เวลาออกงานปกติ*/
			,TO_CHAR(TO_DATE(WT.WORKTIMEFROM,'hh24mi'),'hh24mi') AS SCANTIME_IN 		/*เวลาสแกนนิ้วเข้างาน*/
			,TO_CHAR(TO_DATE(WT.WORKTIMETO ,'hh24mi'),'hh24mi') AS SCANTIME_OUT      	/*เวลาสแกนนิ้วออกงาน*/
		  	,TO_CHAR(TO_DATE(WT.WORKTIMEFROM,'hh24mi'),'hh24:mi') AS SCANTIME_IN_DISPLAY/*เวลาสแกนนิ้วเข้างาน*/ 
		FROM EMP_MEMBER EE	
			INNER JOIN SPC_CALENDARUSER CA ON EE.EMPLOYEEID = CA.EMPLOYEEID
			INNER JOIN SEC_USER SE ON EE.EMPLOYEEID = SE.EMPLOYEEID 
			INNER JOIN (
				WITH CALENDAR AS (
					SELECT TO_DATE(%s, 'dd/mm/yyyy') + ROWNUM - 1 C_DATE		/* PARAM 4 : DATE_START */
					FROM DUAL
					CONNECT BY LEVEL <= TO_DATE(%s, 'dd/mm/yyyy')  				/* PARAM 5 : DATE_END */
					- TO_DATE(%s, 'dd/mm/yyyy') + 1 							/* PARAM 6 : DATE_START */
				)
		
		 		SELECT
		   			A.EMPLOYEEID, A.POSITIONID, C.C_DATE, 
		       	CASE 
		      		WHEN C.C_DATE = A.ONSITEDATEFROM1 THEN A.ONSITETIMEFROM1
		       		--WHEN C.C_DATE > A.ONSITEDATEFROM1 AND C.C_DATE < A.ONSITEDATETO2 THEN '0830'
		       		WHEN C.C_DATE = A.ONSITEDATETO2 THEN A.ONSITETIMEFROM2
		        END ONSITETIMEFROM,
		        CASE 
		        	WHEN C.C_DATE = A.ONSITEDATEFROM1 THEN A.ONSITETIMETO1
		          	--WHEN C.C_DATE > A.ONSITEDATEFROM1 AND C.C_DATE < A.ONSITEDATETO2 THEN '1730'
		        	WHEN C.C_DATE = A.ONSITEDATETO2 THEN A.ONSITETIMETO2
		      	END ONSITETIMETO
		                          
		                  
				FROM CALENDAR C
				-- เช็คทำงานนอกสถานที่
					INNER JOIN
		         		((
			           		SELECT T.EMPLOYEEID, T.POSITIONID, SE.USERID,
			              		T.ONSITEDATEFROM1, T.ONSITETIMEFROM1, T.ONSITETIMETO1,
			             		T.ONSITEDATETO2, T.ONSITETIMEFROM2, T.ONSITETIMETO2 
			                FROM STS_WORKONSITE S ,TAD_WORKONSITE_HISTORY T
		                  	INNER JOIN SEC_USER SE ON T.EMPLOYEEID = SE.EMPLOYEEID 
			                WHERE S.WORKONSITEID = T.WORKONSITEID AND T.ONSITESTATUS <> 'C'
			                --AND T.EMPLOYEEID=3183,2401
		              	) UNION (
		              		SELECT T.EMPLOYEEID, T.POSITIONID, SE.USERID,
		                    T.ONSITEDATEFROM1, T.ONSITETIMEFROM1, T.ONSITETIMETO1,
		                    T.ONSITEDATETO2, T.ONSITETIMEFROM2, T.ONSITETIMETO2 
		                    FROM STS_WORKONSITE S ,TAD_WORKONSITE T
		                    INNER JOIN SEC_USER SE ON T.EMPLOYEEID = SE.EMPLOYEEID 
		                    WHERE S.WORKONSITEID = T.WORKONSITEID AND T.ONSITESTATUS <> 'C'
		                    --AND T.EMPLOYEEID=3183,2401
		              	)) A ON (C.C_DATE >= A.ONSITEDATEFROM1 AND C.C_DATE <= ONSITEDATETO2)
		
		                
		        WHERE A.ONSITEDATEFROM1 >= TO_DATE(%s,'dd/mm/yyyy') 	/* PARAM 7 : DATE_START */
		        AND A.ONSITEDATETO2 <= TO_DATE(%s,'dd/mm/yyyy')			/* PARAM 8 : DATE_END */
		        %s
		        /* PARAM 9 : SEC_USERID */
		        --AND A.EMPLOYEEID IN(3183,2401)
			) WO ON WO.EMPLOYEEID = EE.EMPLOYEEID
		
			INNER JOIN TAD_WORK_TRANSACTION WT ON EE.EMPLOYEEID = WT.EMPLOYEEID
		WHERE 1=1
			AND (CA.WORKDATE = TO_DATE(WT.WORKDATE,'yyyymmdd') AND CA.EMPLOYEEID = WT.EMPLOYEEID)
			AND (CA.WORKDATE BETWEEN TO_DATE(%s,'dd/mm/yyyy')			/* PARAM 10 : DATE_START */
			AND TO_DATE(%s,'dd/mm/yyyy'))                        		/* PARAM 11 : DATE_END */
			AND CAST(WT.WORKTIMEFROM AS INT) > CAST(CA.WORKTIMEIN1 AS INT)
		  	%s
		  	  /* PARAM 3 : SEC_USERID */
		
		   
	   
	MINUS
	  
	
	  
	/*——————————————————————————————
	— [03] เช็คลาพักผ่อน ลาพักร้อน
	———————————————————————————————*/
		SELECT 
			EEE.EMPLOYEEID
			,SE.USERID
		  	,EEE.CARDID 
		  	,EEE.POSITIONID 
		  	,EEE.FIRSTNAMETHA 
		  	,EEE.LASTNAMETHA 
		  	,EEE.NICKNAME 
			,EEE.FIRSTNAMETHA || '  ' || EEE.LASTNAMETHA AS FIRSTNAME
			,CONCAT(TO_CHAR(C_DATE,'dd/mm/')
			,TO_CHAR(C_DATE,'yyyy')+543 ) AS  WORKDATE 
			,TO_CHAR(C_DATE,'dd/mm/yyyy') AS WORKDATES
			,CAST(WTT.WORKTIMEFROM AS INT) - CAST(CAA.WORKTIMEIN1 AS INT) AS LATE
		  	,TO_CHAR(C_DATE,'dd') AS DAYS 
		  	,TO_CHAR(C_DATE,'mm') AS MONTHS 
		  	,TO_CHAR(C_DATE,'yyyy') AS YEARS 
			,TO_CHAR(TO_DATE(CAA.WORKTIMEIN1,'hh24mi'),'hh24mi') AS WORKTIME_IN     	/*เวลาเข้างานปกติ*/
			,TO_CHAR(TO_DATE(CAA.WORKTIMEOUT2,'hh24mi'),'hh24mi') AS WORKTIME_OUT  		/*เวลาออกงานปกติ*/
			,TO_CHAR(TO_DATE(WTT.WORKTIMEFROM,'hh24mi'),'hh24mi') AS SCANTIME_IN    	/*เวลาสแกนนิ้วเข้างาน*/
			,TO_CHAR(TO_DATE(WTT.WORKTIMETO ,'hh24mi'),'hh24mi') AS SCANTIME_OUT    	/*เวลาสแกนนิ้วออกงาน*/
		  	,TO_CHAR(TO_DATE(WTT.WORKTIMEFROM,'hh24mi'),'hh24:mi') AS SCANTIME_IN_DISPLAY/*เวลาสแกนนิ้วเข้างาน*/ 
			
		FROM EMP_MEMBER EEE
			INNER JOIN SPC_CALENDARUSER CAA ON EEE.EMPLOYEEID = CAA.EMPLOYEEID
		  	INNER JOIN SEC_USER SE ON EEE.EMPLOYEEID = SE.EMPLOYEEID 
			INNER JOIN (
		    	WITH CALENDAR AS (
		        	SELECT TO_DATE(%s, 'dd/mm/yyyy') + ROWNUM - 1 C_DATE		/* PARAM 13 : DATE_START */
		          	FROM DUAL
		        	CONNECT BY LEVEL <= TO_DATE(%s, 'dd/mm/yyyy') 				/* PARAM 14 : DATE_END */
		        	- TO_DATE(%s, 'dd/mm/yyyy') + 1	 							/* PARAM 15 : DATE_START */
		        )
		
		        SELECT
		       	B.EMPLOYEEID, B.POSITIONID, C.C_DATE,
		        CASE 
		        	WHEN C.C_DATE = B.LEAVEDATEFROM1 THEN B.LEAVETIMEFROM1
		            --WHEN C.C_DATE > B.LEAVEDATEFROM1 AND C.C_DATE < B.LEAVEDATETO2 THEN '0830'
		            WHEN C.C_DATE = B.LEAVEDATETO2 THEN B.LEAVETIMEFROM2
		        END LEAVETIMEFROM,
		        CASE 
		        	WHEN C.C_DATE = B.LEAVEDATEFROM1 THEN B.LEAVETIMETO1
		            --WHEN C.C_DATE > B.LEAVEDATEFROM1 AND C.C_DATE < B.LEAVEDATETO2 THEN '1730'
		            WHEN C.C_DATE = B.LEAVEDATETO2 THEN B.LEAVETIMETO2
		        END LEAVETIMETO
		                          
		                  
		        FROM CALENDAR C
		        	-- เช็คลาพักผ่อน ลาพักร้อน
		     		INNER JOIN
		            	((
		             		SELECT T.EMPLOYEEID, T.POSITIONID,  SE.USERID,
		                		T.LEAVEDATEFROM1, T.LEAVETIMEFROM1, T.LEAVETIMETO1,
		                   		T.LEAVEDATETO2, T.LEAVETIMEFROM2, T.LEAVETIMETO2 
		                 	FROM STS_LEAVETYPE S ,TAD_LEAVE_HISTORY T
		                  	INNER JOIN SEC_USER SE ON T.EMPLOYEEID = SE.EMPLOYEEID 
		                 	WHERE S.LEAVETYPEID=T.LEAVETYPEID AND T.LEAVESTATUS <> 'C' 
		     				--AND T.EMPLOYEEID=3183,2401
		           		) UNION (
		                	SELECT T.EMPLOYEEID, T.POSITIONID,  SE.USERID,
		               			T.LEAVEDATEFROM1, T.LEAVETIMEFROM1, T.LEAVETIMETO1,
		                    	T.LEAVEDATETO2, T.LEAVETIMEFROM2, T.LEAVETIMETO2 
		                 	FROM STS_LEAVETYPE S ,TAD_LEAVE T
		                  	INNER JOIN SEC_USER SE ON T.EMPLOYEEID = SE.EMPLOYEEID 
		               		WHERE S.LEAVETYPEID=T.LEAVETYPEID AND T.LEAVESTATUS <> 'C'
							--AND T.EMPLOYEEID=3183,2401
		  				)) B ON (C.C_DATE >= B.LEAVEDATEFROM1 AND C.C_DATE <= LEAVEDATETO2)
		
		                
		       	WHERE B.LEAVEDATEFROM1 >= TO_DATE(%s,'dd/mm/yyyy') 			/* PARAM 16 : DATE_START */
		       	AND B.LEAVEDATETO2 <= TO_DATE(%s,'dd/mm/yyyy')				/* PARAM 17 : DATE_END */
		     	%s
		     	/* PARAM 18 : SEC_USERID */
		  		--AND B.EMPLOYEEID IN(3183,2401)
		  	)SI ON SI.EMPLOYEEID = EEE.EMPLOYEEID
		  
			INNER JOIN TAD_WORK_TRANSACTION WTT ON EEE.EMPLOYEEID = WTT.EMPLOYEEID
		WHERE 1=1
			AND (CAA.WORKDATE = TO_DATE(WTT.WORKDATE,'yyyymmdd') AND CAA.EMPLOYEEID = WTT.EMPLOYEEID)
			AND (CAA.WORKDATE BETWEEN TO_DATE(%s,'dd/mm/yyyy')				/* PARAM 19 : DATE_START */
			AND TO_DATE(%s,'dd/mm/yyyy'))                        			/* PARAM 20 : DATE_END */
			AND CAST(WTT.WORKTIMEFROM AS INT) > CAST(CAA.WORKTIMEIN1 AS INT)
			%s
			/* PARAM 21 : SEC_USERID */
	)
}