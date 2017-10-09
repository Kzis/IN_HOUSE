package com.cct.inhouse.timeoffset.core.todo.domain;

import com.cct.domain.HeaderSorts;
import com.cct.domain.SearchCriteria;

public class TodoSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 7503550979538687019L;
	
	public TodoSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
			new HeaderSorts("", HeaderSorts.ASC, "0"), //No
			new HeaderSorts("", HeaderSorts.ASC, "0"), //Approve
			new HeaderSorts("PROJECT_ABBR", HeaderSorts.ASC, "0"), //โครงการ
			new HeaderSorts("PROJ_CON_DETAIL", HeaderSorts.ASC, "0"), //รายละเอียดโครงการ
			new HeaderSorts("USER_FULLNAME", HeaderSorts.ASC, "0"), //ชื่อ-สกุล พนักงาน
			new HeaderSorts("", HeaderSorts.ASC, "0"), //จำนวนวันชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0"), //จำนวนชั่วโมงชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0") //จำนวนนาทีชดเชย
		};

		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	// กำหนด default header sorts ให้สำหรับการกด search ครั้งแรก
	public void setDefaultHeaderSorts() {

		setHeaderSortsSelect("2");
	}
	
}
