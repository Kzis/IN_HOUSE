package test;

import java.util.Locale;

public class TestMerge {

	public static void main(String[] args) {
		User u1 = new User();
		u1.setName("U1-Name");
		u1.setActive(true);
		u1.setLastName("U1-LastName");
		u1.setAge(1);
		u1.setLocale(Locale.ENGLISH);
		print(u1);
		
		User u2 = new User();
		//u2.setName("U2-Name");
		// u2.setActive(false);
		u2.setLastName("U2-LastName");
		// u2.setAge(2);
		// u2.setLocale(new Locale("th", "TH"));
		print(u2);
		
		System.out.println();
		u1.merge(u1, u2);
		System.out.println();
		
		print(u1);
		print(u2);
	}
	
	public static void print(User u) {
		System.out.println(String.format("Name: %s, LastName: %s, Age: %s, Acitve: %s, Locale: %s", u.getName() == null ? "" : u.getName(), u.getLastName() == null ? "" : u.getLastName(), u.getAge() == null ? "" : u.getAge(), u.isActive() == null ? "" : u.isActive(), u.getLocale() == null ? "" : u.getLocale().getLanguage()));
	}
}
