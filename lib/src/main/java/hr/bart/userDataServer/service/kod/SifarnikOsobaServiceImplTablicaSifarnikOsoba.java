package hr.bart.userDataServer.service.kod;

import java.util.HashMap;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikOsobaServiceImplTablicaSifarnikOsoba extends Kod {
	private int pageNumber=(int) hm.get("pageNumber");
	private ACommonServis aCommonServis=new ACommonServis(hm, kodRepository);

	public SifarnikOsobaServiceImplTablicaSifarnikOsoba(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		aCommonServis.findAll_sifarnikOsoba(pi, pageRequest);
		
		return pi;
	}
	
}
