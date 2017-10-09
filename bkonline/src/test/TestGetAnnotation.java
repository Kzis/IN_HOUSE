package test;

import javax.servlet.annotation.WebServlet;

import com.cct.inhouse.bkonline.web.selectitem.servlet.AllQAUserSelectItemServlet;

import util.string.StringDelimeter;

public class TestGetAnnotation {

	public static void main(String[] args) { 
//		AllQAUserSelectItemServlet
		System.out.println(getAnnotationName(AllQAUserSelectItemServlet.class));
		System.out.println(getAnnotationURLPatterns(AllQAUserSelectItemServlet.class));
	}
	
	public static String getAnnotationName(Class<?> classz) {
		String annotationName = StringDelimeter.EMPTY.getValue();
		if ((classz.getAnnotations() != null) && (classz.getAnnotations().length > 0)) {
			annotationName = ((WebServlet) classz.getAnnotations()[0]).name();
		}
		return annotationName;
	}
	
	public static String getAnnotationURLPatterns(Class<?> classz) {
		String annotationName = StringDelimeter.EMPTY.getValue();
		if ((classz.getAnnotations() != null) && (classz.getAnnotations().length > 0)) {
			annotationName = ((WebServlet) classz.getAnnotations()[0]).urlPatterns()[0];
		}
		return annotationName;
	}
}
