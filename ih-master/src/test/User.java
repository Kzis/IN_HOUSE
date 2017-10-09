package test;

import java.lang.reflect.Method;
import java.util.Locale;

public class User {
	
	private String name;
	private String lastName;
	private Boolean active;
	private Integer age;
	private Locale locale;
	
	
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Boolean isActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	public void merge(Object obj, Object update){
	    if(!obj.getClass().isAssignableFrom(update.getClass())){
	        return;
	    }

	    Method[] methods = obj.getClass().getMethods();

	    for(Method fromMethod: methods){
	        if(fromMethod.getDeclaringClass().equals(obj.getClass())
	                && fromMethod.getName().startsWith("get")){

	            String fromName = fromMethod.getName();
	            String toName = fromName.replace("get", "set");

	            try {
	                Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
	                Object value = fromMethod.invoke(update, (Object[])null);
	                if(value != null){
	                    toMetod.invoke(obj, value);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            } 
	        } else if(fromMethod.getDeclaringClass().equals(obj.getClass())
	                && fromMethod.getName().startsWith("is")){

	            String fromName = fromMethod.getName();
	            String toName = fromName.replace("is", "set");

	            try {
	                Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
	                Object value = fromMethod.invoke(update, (Object[])null);
	                if(value != null){
	                    toMetod.invoke(obj, value);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            } 
	        }
	    }
	}
}
