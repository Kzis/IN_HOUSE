package com.cct.abstracts;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 * สำหรับส่งข้อมูล select item กลับไปยัง Client แบบ Object ด้วย Json<br>
 * แต่เวลาใช้งานต้องสร้าง JSON Type ดังนี้<br>
 * exp. new TypeToken<ArrayList<CommonSelectItemObject<MyClass>>>(){}.getType();
 * @author sittipol.m
 *
 */
public abstract class AbstractSelectItemObject implements Serializable {
	
	private static final long serialVersionUID = -6970347057616934528L;

	public AbstractSelectItemObject() {
		
	}
	
	public AbstractSelectItemObject(ResultSet rst) {
		
	}
	
}