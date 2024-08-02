package hr.bart.userDataServer.util;

public class TimerKoda {
	private long pocetak=0;
	private String showTime;
	
	public void begin() {
		pocetak=System.currentTimeMillis();
	}
	
	public String end() {
		long kraj=System.currentTimeMillis();
		long koliko=kraj - pocetak;
		String jedinica="ms";
		
		if(koliko>1000) {
			koliko=koliko / 1000;
			jedinica="s";
		}
		
		showTime=koliko + " " + jedinica;
		
		return showTime;
	}
	
	public String getShowTime() {
		return showTime;
	}
	
}
