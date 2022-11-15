package hr.bart.userDataServer.service.kod;

import java.time.LocalDate;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.RezultatPage;
import hr.bart.userDataServer.util.TimerKoda;

public abstract class Kod {
	private TimerKoda timerKoda=new TimerKoda(getClass());
	protected HashMap<String, Object> hm;
	protected KodRepository kodRepository;
	private final Logger LOGGER=LoggerFactory.getLogger(getClass());
	protected final static int pageRequestSize50=50;
	protected final static int pageRequestSize8=8;
	protected final static LocalDate datumPatakZadnji=LocalDate.of(9999, 12, 24);
	protected final static LocalDate mjesecZadnji=datumPatakZadnji.withDayOfMonth(1);
	protected final static String HRK="HRK";
	protected final static int NULA=0;
	protected final static int JEDAN=1;
	protected final static int DVA=2;
	protected final static int SEDAM=7;
	protected final static int STO=100;
	protected String greska="";
	
	public Kod(HashMap<String, Object> hm, KodRepository kodRepository) {
		this.hm=hm;
		this.kodRepository=kodRepository;
	}
	
	public abstract PojoInterface izvrsiKod(PojoInterface pi) throws Throwable;
	
	public PojoInterface izvrsi() {
		LOGGER.info(getClass().getSimpleName() + " - POCETAK");
		timerKoda.begin();
		PojoInterface pi=new PojoInterface();
		
		try {
			izvrsiKod(pi);
		} catch(Throwable t) {
			cachException(t, pi);
		} finally {
			timerKoda.end();
		}
		
		return pi;
	}
	
	public String getGreska() {
		return greska;
	}
	
	protected void cachException(Throwable t, PojoInterface pi) {
		LOGGER.error(t.getMessage(), t);
		
		pi.setGreska(t.getMessage());
	}
	
	protected void setRezultatPage(PojoInterface pi, Page<?> page) {
		RezultatPage rezultatPage=new RezultatPage(); 
		rezultatPage.setPageNumber(page.getPageable().getPageNumber());
		rezultatPage.setTotalPages(page.getTotalPages());
		
		pi.setRezultatPage(rezultatPage);
	}
	
}
