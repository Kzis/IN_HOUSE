package com.cct.inhouse.bkonline.core.setting.roomsetting.domain;

import com.cct.inhouse.common.InhouseDomain;

public class RoomPicture extends InhouseDomain {

	private static final long serialVersionUID = 3301738862284653200L;

	private String pictureName;			// ชื่อไฟล์จริง
	private String pictureNameGenerate;	// ชื่อไฟล์ที่ระบบจัดทำให้
	private String picturePath;			// Path ที่เก็บรูป
	private String roomId;				// ไอดีอ้างอิงห้อง

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getPictureNameGenerate() {
		return pictureNameGenerate;
	}

	public void setPictureNameGenerate(String pictureNameGenerate) {
		this.pictureNameGenerate = pictureNameGenerate;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
