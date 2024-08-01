package hr.bart.userDataServer.service.kod;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import hr.bart.userDataServer.util.PojoInterface;

public class ClaimServiceImplNovaOsoba extends Kod {

	public ClaimServiceImplNovaOsoba() {
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToString(String timerKodaEnd) {
		return getClass().getSimpleName() + " " + timerKodaEnd + " " + new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}
