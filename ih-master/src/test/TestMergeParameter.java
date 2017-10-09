package test;

import com.cct.inhouse.core.config.parameter.domain.Application;
import com.cct.inhouse.core.config.parameter.domain.Parameter;

public class TestMergeParameter {

	public static void main(String[] args) {
		
		Application a1 = new Application();
		a1.setTitle("A1-Title");
		a1.setTheme("A1-Theme");
		a1.setLppString("1,2,3,4");
		Parameter p1 = new Parameter();
		p1.setApplication(a1);
		print(p1);
		
		Application a2 = new Application();
		a2.setTitle("A2-Title");
		a2.setLppString("6,7,8,9");
		// a2.setTheme("A2-Theme");
		
		Parameter p2 = new Parameter();
		p2.setApplication(a2);
		print(p2);
				
		System.out.println();
		p1.getApplication().merge(p2.getApplication());
		System.out.println();
		
		print(p1);
		print(p2);
	}
	
	public static void print(Parameter p) {
		System.out.println(String.format("Title: %s, Theme: %s", p.getApplication().getTitle(), p.getApplication().getTheme()));
		String lppString = "";
		if (p.getApplication().getLpp() == null) {
			
		} else {
			for (String lpp : p.getApplication().getLpp()) {
				lppString += lpp + ",";
			}
		}
		System.out.println(String.format("LPP: %s", lppString));
	
	}
}
