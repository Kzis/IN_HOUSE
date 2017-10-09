package com.cct.inhouse.timeoffset.core.dialog.approve.domain;

import com.cct.domain.HeaderSorts;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.timeoffset.core.approve.domain.Approve;

public class ApproveDialogSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 1759658429159512237L;
	
	private Approve approve = new Approve();
	
	// กำหนดให้ใช้ header sorts
	public ApproveDialogSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
				new HeaderSorts("CONCAT(emp.NAME, ' ' ,emp.SURNAME)", HeaderSorts.ASC, "0"), 
				new HeaderSorts("sjp.JOB_POSITION_NAME", HeaderSorts.ASC, "0"), 
				new HeaderSorts("emp.START_DATE", HeaderSorts.ASC, "0") 
		};

		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	// กำหนด default header sorts ให้สำหรับการกด search ครั้งแรก
	public void setDefaultHeaderSorts() {
		for (int i = 0; i < getHeaderSortsSize(); i++) {
			getHeaderSorts()[i].setOrder(HeaderSorts.ASC);
			getHeaderSorts()[i].setSeq("0");
		}
		setHeaderSortsSelect("0,1,2");
	}

	public Approve getApprove() {
		return approve;
	}

	public void setApprove(Approve approve) {
		this.approve = approve;
	}
		
	
}
