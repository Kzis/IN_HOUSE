package com.cct.inhouse.core.config.parameter.domain;

import java.io.Serializable;
import java.lang.reflect.Method;

public class MergeParameter implements Serializable {

	private static final long serialVersionUID = 3134547089449265051L;
	
	public void merge(Object update){
		if (update == null) {
			return;
		}
		
		if(!update.getClass().isAssignableFrom(this.getClass())) {
			return;
		}

	    Method[] methods = this.getClass().getMethods();

	    for(Method fromMethod: methods){
	        if(fromMethod.getDeclaringClass().equals(this.getClass())
	                && fromMethod.getName().startsWith("get")){

	            String fromName = fromMethod.getName();
	            String toName = fromName.replace("get", "set");

	            try {
	                Method toMetod = this.getClass().getMethod(toName, fromMethod.getReturnType());
	                Object value = fromMethod.invoke(update, (Object[])null);
	                if(value != null){
	                    toMetod.invoke(this, value);
	                }
	            } catch (Exception e) {
	                // System.err.println("Not found: " + e.getMessage());
	            } 
	        } else if(fromMethod.getDeclaringClass().equals(this.getClass())
	                && fromMethod.getName().startsWith("is")){

	            String fromName = fromMethod.getName();
	            String toName = fromName.replace("is", "set");

	            try {
	                Method toMetod = this.getClass().getMethod(toName, fromMethod.getReturnType());
	                Object value = fromMethod.invoke(update, (Object[])null);
	                if(value != null){
	                    toMetod.invoke(this, value);
	                }
	            } catch (Exception e) {
	            	// System.err.println("Not found: " + e.getMessage());
	            } 
	        }
	    }
	}
}
