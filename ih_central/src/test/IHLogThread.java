package test;

import com.cct.inhouse.central.util.log.LogUtil;

public class IHLogThread extends Thread {

	@Override
	public void run() {
		while (true) {
			try {
				LogUtil.TEST.debug("IHLogThread");
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
