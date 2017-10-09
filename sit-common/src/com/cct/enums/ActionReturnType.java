package com.cct.enums;

/**
 * @Description: Class enum for return result
 */
public enum ActionReturnType {
	MAINPAGE("mainpage")
	, INIT("init")
	, SEARCH("search")
	, SEARCH_DO("searchDo")
	, DOWNLOAD(null)
	, EXPORT("download")
	, UPLOAD("upload")
	, ADD_EDIT("addEdit")
	, ADD_DO("addDo")
	, EDIT_DO("editDo")
	, HOME("home")
	, THIS(null)
	, VIEW("view")
	, SEARCH_AJAX("searchResultAjax")
	, PROFILE("profile")
	, INVALID_REQUEST("invalidRequest")
	, BLOCKING_REQUEST("blockingRequest")
	;

	private String result;

	private ActionReturnType(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}
}
