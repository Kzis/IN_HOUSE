searchOperatorLevel {
	SELECT 
		MIN(so.LEVEL) as MIN_LEVEL, MAX(so.LEVEL) as MAX_LEVEL
	FROM [SCHEMAS].sec_operator so
	WHERE so.ACTIVE = 'Y'
		AND so.SYSTEM_CODE = %s
}

searchOperator {
	SELECT 
		so.OPERATOR_ID, so.PARENT_ID, so.TYPE, so.LEVEL , so.SYSTEM_CODE,
		so.LABEL_TH, so.LABEL_EN, so.URL, so.ACTIVE
	FROM [SCHEMAS].sec_operator so
	WHERE so.ACTIVE = 'Y'
		AND so.SYSTEM_CODE = %s	
}

searchOperatorByUserId {
	SELECT DISTINCT
    	o.OPERATOR_ID, o.PARENT_ID, o.TYPE, o.LEVEL , o.SYSTEM_CODE,
		o.LABEL_TH, o.LABEL_EN, o.URL, o.ACTIVE, o.SEQUENCE_NO
	FROM 
		(
			SELECT 
	    		so.OPERATOR_ID, so.PARENT_ID, so.TYPE, so.LEVEL ,so.SYSTEM_CODE,
			    so.LABEL_TH, so.LABEL_EN, so.URL, so.ACTIVE, so.SEQUENCE_NO, su.USER_ID
			FROM [SCHEMAS].sec_operator so
				INNER JOIN [SCHEMAS].sec_map_user_operator smuo ON (smuo.OPERATOR_ID = so.OPERATOR_ID)
				INNER JOIN [SCHEMAS].sec_user su ON (su.USER_ID = smuo.USER_ID)
			WHERE so.ACTIVE = 'Y' AND su.DELETED = 'N'
			UNION
			SELECT 
	    		so.OPERATOR_ID, so.PARENT_ID, so.TYPE, so.LEVEL ,so.SYSTEM_CODE,
			    so.LABEL_TH, so.LABEL_EN, so.URL, so.ACTIVE, so.SEQUENCE_NO, su.USER_ID
			FROM [SCHEMAS].sec_operator so
	    		INNER JOIN [SCHEMAS].sec_map_group_operator smgp ON (smgp.OPERATOR_ID = so.OPERATOR_ID)
    			INNER JOIN [SCHEMAS].sec_group sg ON (sg.GROUP_ID = smgp.GROUP_ID)
    			INNER JOIN [SCHEMAS].sec_map_user_group smug ON (smug.GROUP_ID = sg.GROUP_ID)
    			INNER JOIN [SCHEMAS].sec_user su ON (su.USER_ID = smug.USER_ID)
      		WHERE so.ACTIVE = 'Y' AND sg.ACTIVE = 'Y' AND su.DELETED = 'N'
    	) o
	WHERE 1 = 1
    AND o.SYSTEM_CODE = %s
		AND o.USER_ID = %s
	ORDER BY o.LEVEL ASC, o.SEQUENCE_NO ASC
}