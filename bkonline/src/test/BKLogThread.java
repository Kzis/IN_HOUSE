package test;

import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;

public class BKLogThread extends Thread {
	
	@Override
	public void run() {
		while (true) {
			try {
				LogUtil.BOOKING.debug("Title :- " + ParameterConfig.getParameter().getApplication().getTitle());
				LogUtil.BOOKING.debug("Theme :- " + ParameterConfig.getParameter().getApplication().getTheme());
				LogUtil.BOOKING.debug("GlobalData :- " + ParameterConfig.getMapGlobalData().size());
				LogUtil.BOOKING.debug("ContenType :- " + ParameterConfig.getMapContenType());
			} catch (Exception e) {
				LogUtil.BOOKING.error(e);
			}
			
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				LogUtil.BOOKING.error(e);
			}
		}
	}
}
