package hr.bart.userDataServer.service.kod;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.PojoInterface;

public class OsobaValutaServiceImplOsobaValuta extends Kod {
	private final Long idSifarnikOsoba;
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);
	
	public OsobaValutaServiceImplOsobaValuta(KodRepository kodRepository, Long idSifarnikOsoba) {
		super(kodRepository);
		this.idSifarnikOsoba=idSifarnikOsoba;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		aCommonServis.findAllBySifarnikOsobaId(pi, idSifarnikOsoba, pageRequest);
		
		return pi;
	}
	
}
