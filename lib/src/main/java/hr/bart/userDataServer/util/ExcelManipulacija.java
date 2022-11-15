package hr.bart.userDataServer.util;

public class ExcelManipulacija {
	private static final String D="D";
	private int broj;
	private Long id;

	public ExcelManipulacija(int broj, Long id) {
		this.broj=broj;
		this.id=id;
	}

	public int getBroj() {
		return broj;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getValue() {
		return D + broj;
	}
}
