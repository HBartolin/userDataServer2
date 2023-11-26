package hr.bart.userDataServer.service.kod;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.PojoInterface;

public class OsobaValutaServiceImplTablicaOsobaValuta extends Kod {
	private final int pageNumber;
	private final Long idSifarnikOsoba;
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);

	public OsobaValutaServiceImplTablicaOsobaValuta(KodRepository kodRepository, Long idSifarnikOsoba, int pageNumber) {
		super(kodRepository);
		this.idSifarnikOsoba=idSifarnikOsoba;
		this.pageNumber=pageNumber;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		aCommonServis.findAllBySifarnikOsobaId(pi, idSifarnikOsoba, pageRequest);
		
		return pi;
	}

}
