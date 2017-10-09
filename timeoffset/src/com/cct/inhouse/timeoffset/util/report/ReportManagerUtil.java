package com.cct.inhouse.timeoffset.util.report;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

public class ReportManagerUtil {

	/**
	 *
	 * Print workbook to servlet
	 */
	public void exportXLS(HSSFWorkbook workbook, String fileName) throws Exception {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			/* response.addHeader("Content-Type", "application/excel"); */
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			OutputStream os = response.getOutputStream();

			workbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			throw e;
		}
	}

}
