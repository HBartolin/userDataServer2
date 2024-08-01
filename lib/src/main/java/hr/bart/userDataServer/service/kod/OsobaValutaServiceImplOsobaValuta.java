package hr.bart.userDataServer.service.kod;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class OsobaValutaServiceImplOsobaValuta extends Kod {
	private final Long idSifarnikOsoba;
	private ACommonServis aCommonServis=new ACommonServis();
	private final OsobaValutaRepository osobaValutaRepository;
	
	public OsobaValutaServiceImplOsobaValuta(OsobaValutaRepository osobaValutaRepository, Long idSifarnikOsoba) {
		this.osobaValutaRepository=osobaValutaRepository;
		this.idSifarnikOsoba=idSifarnikOsoba;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		aCommonServis.findAllBySifarnikOsobaId(osobaValutaRepository, pi, idSifarnikOsoba, pageRequest);
		
		return pi;
	}
	
	@Override
	public String getToString() {
		return new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}
