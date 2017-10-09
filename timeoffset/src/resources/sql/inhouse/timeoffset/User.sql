searchUserLogin{
	SELECT 
		USER_ID , NAME , SURNAME 
		, DEPARTMENT_ID , POSITION_ID
	FROM sec_user 
	WHERE DELETED = 'N' 
	AND EMAIL = CONCAT(%s, '@somapait.com')
	AND CARD_ID = %s
}