package hr.bart.userDataServer.util;

public enum DbStatus {
	A("AKTIVAN"), 
	N("NEAKTIVAN"); 

	private final String naziv;

	private DbStatus(String naziv) {
		this.naziv=naziv;
	}

	public String getNaziv() {
		return naziv;
	}

}
