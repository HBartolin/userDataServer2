package hr.bart.userDataServer.service.kod;

import java.util.HashMap;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikOsobaServiceImplSifarniciOsoba extends Kod {
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);

	public SifarnikOsobaServiceImplSifarniciOsoba(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		aCommonServis.findAll_sifarnikOsoba(pi, pageRequest);
		
		return pi;
	}

}
