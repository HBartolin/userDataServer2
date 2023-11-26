package hr.bart.userDataServer.service.kod;

import java.util.HashMap;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.PojoInterface;

public class OsobaValutaServiceImplTablicaOsobaValuta extends Kod {
	private int pageNumber=(int) hm.get("pageNumber");
	private Long idSifarnikOsoba=(Long) hm.get("idSifarnikOsoba");
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);

	public OsobaValutaServiceImplTablicaOsobaValuta(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		aCommonServis.findAllBySifarnikOsobaId(pi, idSifarnikOsoba, pageRequest);
		
		return pi;
	}

}
