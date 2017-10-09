package test;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.cct.common.CommonSelectItem;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;

public class TestThead extends Thread {
	@Override
	public void run() {
		while (true) {
			try {
				LogUtil.INITIAL.debug("Title :- " + ParameterConfig.getParameter().getApplication().getTitle());
				LogUtil.INITIAL.debug("AttachFile :- " + ParameterConfig.getParameter().getAttachFile().getSystemPath());
				LogUtil.INITIAL.debug("Report :- " + ParameterConfig.getParameter().getReport().getSystemPath());
				LogUtil.INITIAL.debug("Support Locale :- " + ParameterConfig.getParameter().getApplication().getSupportLocaleString());
				LogUtil.INITIAL.debug("Application Locale :- " + ParameterConfig.getParameter().getApplication().getApplicationLocale());
				
				LogUtil.INITIAL.debug("ContextCentral :- " + ParameterConfig.getParameter().getContextConfig().getURLContextCentral());
				LogUtil.INITIAL.debug("ContextQAQC :- " + ParameterConfig.getParameter().getContextConfig().getURLContextQAQC());
				LogUtil.INITIAL.debug("ContextRM :- " + ParameterConfig.getParameter().getContextConfig().getURLContextRM());
				LogUtil.INITIAL.debug("ContextTO :- " + ParameterConfig.getParameter().getContextConfig().getURLContextTO());
				LogUtil.INITIAL.debug("ContextBK :- " + ParameterConfig.getParameter().getContextConfig().getURLContextBK());
				
				Map<Locale, Map<String, List<CommonSelectItem>>> mapCentralGlobalData = ParameterConfig.getMapGlobalData();
				for (Locale locale : mapCentralGlobalData.keySet()) {
					Map<String, List<CommonSelectItem>> mapList = mapCentralGlobalData.get(locale);
					for (String globalType : mapList.keySet()) {
						List<CommonSelectItem> listComm = mapList.get(globalType);
						LogUtil.INITIAL.debug("globalType: " + globalType);
						for (CommonSelectItem item : listComm) {
							LogUtil.INITIAL.debug("item: " + item.getKey() + ": " + item.getValue());
						}
						LogUtil.INITIAL.debug("");
					}
				}
				
				LogUtil.INITIAL.debug("");
				LogUtil.INITIAL.debug("");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
