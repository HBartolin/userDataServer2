package hr.bart.userDataServer.util;

public class PojoInterface {
	private String greska="";
	private String ok="";
	private Object rezultat="";
	private Object rezultatPage="";
	
	public String getGreska() {
		return greska;
	}

	public void setGreska(String greska) {
		this.greska = greska;
	}

	public Object getRezultat() {
		return rezultat;
	}

	public void setRezultat(Object rezultat) {
		this.rezultat = rezultat;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public Object getRezultatPage() {
		return rezultatPage;
	}

	public void setRezultatPage(Object rezultatPage) {
		this.rezultatPage = rezultatPage;
	}
	
}
