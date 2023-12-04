package hr.bart.userDataServer.util;

import java.util.ArrayList;
import java.util.List;

public class PojoInterface {
	private List<String> greskaList=new ArrayList<>();
	private String ok="";
	private Object rezultat="";
	private Object rezultatPage="";

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

	public List<String> getGreska() {
		return greskaList;
	}

	public void addGreskaList(String greska) {
		greskaList.add(greska);
	}
	
}
