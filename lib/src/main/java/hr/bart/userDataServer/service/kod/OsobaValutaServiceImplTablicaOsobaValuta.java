package hr.bart.userDataServer.service.kod;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class OsobaValutaServiceImplTablicaOsobaValuta extends Kod {
	private final int pageNumber;
	private final Long idSifarnikOsoba;
	@ToStringExclude
	private ACommonServis aCommonServis=new ACommonServis();
	@ToStringExclude
	private final OsobaValutaRepository osobaValutaRepository;

	public OsobaValutaServiceImplTablicaOsobaValuta(OsobaValutaRepository osobaValutaRepository, Long idSifarnikOsoba, int pageNumber) {
		this.osobaValutaRepository=osobaValutaRepository;
		this.idSifarnikOsoba=idSifarnikOsoba;
		this.pageNumber=pageNumber;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		aCommonServis.findAllBySifarnikOsobaId(osobaValutaRepository, pi, idSifarnikOsoba, pageRequest);
		
		return pi;
	}

}
