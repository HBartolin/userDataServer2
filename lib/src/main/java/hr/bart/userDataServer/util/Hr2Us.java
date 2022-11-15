package hr.bart.userDataServer.util;

public class Hr2Us {
	
	public String getNaziv(String naziv) {
		naziv=naziv.replaceAll("Š", "S");
		naziv=naziv.replaceAll("Đ", "D");
		naziv=naziv.replaceAll("Č", "C");
		naziv=naziv.replaceAll("Ć", "C");
		naziv=naziv.replaceAll("Ž", "Z");
		naziv=naziv.replaceAll("š", "s");
		naziv=naziv.replaceAll("đ", "d");
		naziv=naziv.replaceAll("č", "c");
		naziv=naziv.replaceAll("ć", "c");
		naziv=naziv.replaceAll("ž", "z");
		
		return naziv;
	}
}
