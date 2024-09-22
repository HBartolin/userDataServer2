package hr.bart.userDataServer.service.kod;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikOsobaServiceImplTablicaSifarnikOsoba extends Kod {
	private final int pageNumber;
	@ToStringExclude
	private ACommonServis aCommonServis=new ACommonServis();
	@ToStringExclude
	private final SifarnikOsobaRepository sifarnikOsobaRepository;

	public SifarnikOsobaServiceImplTablicaSifarnikOsoba(SifarnikOsobaRepository sifarnikOsobaRepository, int pageNumber) {
		this.pageNumber=pageNumber;
		this.sifarnikOsobaRepository = sifarnikOsobaRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) throws Throwable {
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		aCommonServis.findAll_sifarnikOsoba(pi, sifarnikOsobaRepository, pageRequest);
		
		return pi;
	}
	
}
