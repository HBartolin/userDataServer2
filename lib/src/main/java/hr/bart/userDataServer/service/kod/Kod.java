package hr.bart.userDataServer.service.kod;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.RezultatPage;
import hr.bart.userDataServer.util.TimerKoda;

public abstract class Kod {
	private TimerKoda timerKoda=new TimerKoda();
	private final Logger LOGGER=LoggerFactory.getLogger(getClass());
	protected final static int pageRequestSize50=50;
	protected final static int pageRequestSize8=8;
	protected final static LocalDate datumPatakZadnji=LocalDate.of(9999, 12, 24);
	protected final static LocalDate mjesecZadnji=datumPatakZadnji.withDayOfMonth(1);
	protected final static String HRK="€";
	protected final static int NULA=0;
	protected final static int JEDAN=1;
	protected final static int DVA=2;
	protected final static int SEDAM=7;
	protected final static int STO=100;
	
	public abstract PojoInterface izvrsiKod(PojoInterface pi) throws Throwable;
	
	public PojoInterface izvrsi() {
		timerKoda.begin();
		PojoInterface pi=new PojoInterface();
		
		try {
			izvrsiKod(pi);
		} catch(Throwable t) {
			cachException(t, pi);
		} finally {
			timerKoda.end();
			LOGGER.info(getReflectionToStringBuilder());
		}
		
		return pi;
	}
	
	private StandardToStringStyle getStandardToStringStyle() {
		StandardToStringStyle style = new StandardToStringStyle();
		style.setFieldSeparator(", ");
		style.setUseClassName(false);
		style.setUseIdentityHashCode(false);

		return style;
	}
	
	private String getReflectionToStringBuilder() {
		ReflectionToStringBuilder rb=new ReflectionToStringBuilder(this, getStandardToStringStyle());
		rb.setExcludeFieldNames("aCommonServis", "projektRepository", "LOGGER", "timerKoda");
		
		return String.format("%s %s %s", getClass().getSimpleName(), timerKoda.getShowTime(), rb.toString());
	}
	
	protected void cachException(Throwable t, PojoInterface pi) {
		LOGGER.error(t.getMessage(), t);
		
		pi.addGreskaList(t.getMessage());
	}
	
	protected void setRezultatPage(PojoInterface pi, Page<?> page) {
		RezultatPage rezultatPage=new RezultatPage(); 
		rezultatPage.setPageNumber(page.getPageable().getPageNumber());
		rezultatPage.setTotalPages(page.getTotalPages());
		
		pi.setRezultatPage(rezultatPage);
	}
	
}
