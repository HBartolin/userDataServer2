package hr.kyndryl.bartolin.userDataServer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerKoda {
	private final Logger LOGGER=LoggerFactory.getLogger(getClass());
	private long pocetak=0;
	private final String clazzSN;
	
	public TimerKoda(Class<?> clazz) {
		clazzSN=clazz.getSimpleName();
	}
	
	public void begin() {
		pocetak=System.currentTimeMillis();
	}
	
	public void end() {
		long kraj=System.currentTimeMillis();
		long koliko=kraj - pocetak;
		String jedinica="ms";
		String prefiks="";
		
		if(koliko>1000) {
			koliko=koliko / 1000;
			jedinica="s";
			prefiks="**** ";
		}
		
		LOGGER.info("{}Total time: {} {} [{}]", prefiks, koliko, jedinica, clazzSN);
	}
	
}
