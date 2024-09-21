package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.SifarnikOsoba;
import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikOsobaServiceImplEditirajSifarnikOsoba extends Kod {
	private final Optional<Long> id;
	private final String ime;
	private final String prezime;
	@ToStringExclude
	private ACommonServis aCommonServis=new ACommonServis();
	@ToStringExclude
	private final SifarnikOsobaRepository sifarnikOsobaRepository;

	public SifarnikOsobaServiceImplEditirajSifarnikOsoba(
			SifarnikOsobaRepository sifarnikOsobaRepository,
			Optional<Long> id, 
			String ime, 
			String prezime) {
		this.id=id;
		this.ime=ime;
		this.prezime=prezime;
		this.sifarnikOsobaRepository = sifarnikOsobaRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) throws Throwable {		
		if(ime==null || "".equals(ime.replaceAll("\\s",""))) {
			String msg="Polje Ime nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(prezime==null || "".equals(prezime.replaceAll("\\s",""))) {
			String msg="Polje Prezime nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(!pi.getGreska().isEmpty()) {
			
		} else {
			SifarnikOsoba sifarnikOsoba=new SifarnikOsoba();
			
			if(id.isPresent()) sifarnikOsoba.setId(id.get());
			sifarnikOsoba.setIme(ime);
			sifarnikOsoba.setPrezime(prezime);
			
			sifarnikOsobaRepository.save(sifarnikOsoba);
			
			pi.setOk("Osoba je dodana.");
		}
		
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		aCommonServis.findAll_sifarnikOsoba(pi, sifarnikOsobaRepository, pageRequest);
		
		return pi;
	}

}
