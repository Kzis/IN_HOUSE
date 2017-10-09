searchUserByUsernameAndPassword{
	select US.*
	from [OC].SEC_USER US 
	WHERE US.STATUS <> 'I'
		AND LOGINNAME = %s
		AND PASSWORD = hrmencrypt(%s)
}