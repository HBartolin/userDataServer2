package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.SifarnikOsoba;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikOsobaServiceImplEditirajSifarnikOsoba extends Kod {
	private final Optional<Long> id;
	private final String ime;
	private final String prezime;
	private ACommonServis aCommonServis=new ACommonServis(getKodRepository());

	public SifarnikOsobaServiceImplEditirajSifarnikOsoba(KodRepository kodRepository, Optional<Long> id, String ime, String prezime) {
		super(kodRepository);
		this.id=id;
		this.ime=ime;
		this.prezime=prezime;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {		
		if(ime==null || "".equals(ime.replaceAll("\\s",""))) {
			String msg="Polje Ime nije upisano.";
			pi.setGreskaListString(msg);
		}
		
		if(prezime==null || "".equals(prezime.replaceAll("\\s",""))) {
			String msg="Polje Prezime nije upisano.";
			pi.setGreskaListString(msg);
		}
		
		if(!pi.getGreska().isEmpty()) {
			
		} else {
			SifarnikOsoba sifarnikOsoba=new SifarnikOsoba();
			
			if(id.isPresent()) sifarnikOsoba.setId(id.get());
			sifarnikOsoba.setIme(ime);
			sifarnikOsoba.setPrezime(prezime);
			
			getKodRepository().getSifarnikOsobaRepository().save(sifarnikOsoba);
			
			pi.setOk("Osoba je dodana.");
		}
		
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		aCommonServis.findAll_sifarnikOsoba(pi, pageRequest);
		
		return pi;
	}

}
