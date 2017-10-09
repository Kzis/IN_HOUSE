*** ข้อจำกัด ***
- ไม่ Support Array
- กรณี Date ที่รับมาจาก Date Picker นั้นจะมี Locale เท่ากับ Database Locale เสมอ
- การตรวจสอบ Date ทั้งหมด  default Database Locale
- Date มี Default format คือ "DD/MM/YYYY" ทุกตัว
- Time มี Default format คือ "HH:mm" ทุกตัว
- Message ถ้าต้องใช้ Label ที่กำหนดไว้ใน bundle ให้ใส่ # นำหน้า เช่น <message>#bk.startDate</message>
- กรณีตรวจสอบ dateBeforeLimit (เลือกข้อมูลได้ย้อนหลังไม่ได้เกิน x จำนวน) ต้องกำหนด dbLookup, dbFormat : Require ***
- สามารถกำหนด FileName และ Path สำหรับโหลด Config ได้ที่ struts.xml 
	<param name="configFileName">ih-server-validatorx.xml</param>
    <param name="configFilePath">D:/Projects/neon3/inhouseprojectxx/bkonline/src/</param>

*** Validate List ***
* requireInput = ต้องไม่เป็น null และ  ต้องไม่เป็นค่าว่าง
* stringLength = ตรวจสอบจำนวนตัวอักษรที่กรอกต้องอยู่ในช่วงต่างๆ
	+ minLength : default 0
	+ maxLength : default Integer.MAX_VALUE
	
* intValue = ตรวจสอบค่า Integer
* longValue = ตรวจสอบค่า Integer
* dateValue = ตรวจสอบค่า Date
	+ format : default DD/MM/YYYY 

* dayRangeLimit (startDate) = ตรวจสอบจำนวนวันที่เลือก *** ต้องสร้าง validatorWhat ทั้ง startDate และ endDate
	+ endDate : Require
	+ rangeLimit : Default 1 วัน
	+ format : default DD/MM/YYYY 

* dayRangeLimit (endDate) = ตรวจสอบจำนวนวันที่เลือก *** ต้องสร้าง validatorWhat ทั้ง startDate และ endDate
	+ startDate : Require
	+ rangeLimit : Default 1 วัน
	+ format : default DD/MM/YYYY

* timeValue = ตรวจสอบค่า Time
 	+ format : default HH:mm
 	
* emailValue = ตรวจสอบรูปแบบ email

* dateBeforeLimit = ตรวจสอบการเลือกวันที่ ย้อนหลังไม่ได้เกิน x จำนวน
	+ validateTypeCondition : Default M (D=วัน, M=เดือน, Y=ปี) 
	+ rangeLimit : Default 1 (ตามประเภท condition)
	+ dbLookup : Require ***
	+ dbFormat : Require ***

- dateAfterLimit	*****
- dayAndTimeRangeLimit	*****

- timeLength = ตรวจสอบการเลือกจำนวนชั่วโมง
- dateLength = ตรวจสอบการเลือกจำนวนวัน


	