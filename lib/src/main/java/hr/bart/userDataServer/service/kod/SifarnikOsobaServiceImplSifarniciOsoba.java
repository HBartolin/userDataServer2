package hr.bart.userDataServer.service.kod;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikOsobaServiceImplSifarniciOsoba extends Kod {
	private ACommonServis aCommonServis=new ACommonServis();
	private final SifarnikOsobaRepository sifarnikOsobaRepository;

	public SifarnikOsobaServiceImplSifarniciOsoba(SifarnikOsobaRepository sifarnikOsobaRepository) {
		this.sifarnikOsobaRepository = sifarnikOsobaRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		aCommonServis.findAll_sifarnikOsoba(pi, sifarnikOsobaRepository, pageRequest);
		
		return pi;
	}

}
