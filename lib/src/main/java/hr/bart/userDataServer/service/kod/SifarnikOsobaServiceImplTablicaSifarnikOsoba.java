package hr.bart.userDataServer.service.kod;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikOsobaServiceImplTablicaSifarnikOsoba extends Kod {
	private final int pageNumber;
	private ACommonServis aCommonServis=new ACommonServis(getKodRepository());

	public SifarnikOsobaServiceImplTablicaSifarnikOsoba(KodRepository kodRepository, int pageNumber) {
		super(kodRepository);
		this.pageNumber=pageNumber;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		aCommonServis.findAll_sifarnikOsoba(pi, pageRequest);
		
		return pi;
	}
	
}
