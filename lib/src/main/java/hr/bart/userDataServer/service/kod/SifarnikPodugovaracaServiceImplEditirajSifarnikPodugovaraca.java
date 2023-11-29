package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.SifarnikPodugovaraca;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca extends Kod {
	private final Optional<Long> idO;
	private final Optional<String> nazivO;

	public SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca(KodRepository kodRepository, Optional<Long> idO, Optional<String> nazivO) {
		super(kodRepository);
		this.idO=idO;
		this.nazivO=nazivO;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		String greska="";
		
		if(!nazivO.isPresent()) {
			if(greska.length()>0) greska=greska + " <BR> ";
			String msg="Polje Naziv nije upisano.";
			greska+=msg;
			pi.setGreskaListString(msg);
		} else {
			String naziv=nazivO.get();
			
			if(naziv==null || naziv.isEmpty()) {
				if(greska.length()>0) greska=greska + " <BR> ";
				String msg="Polje Naziv nije upisano.";
				greska+=msg;
				pi.setGreskaListString(msg);
			} else {
				SifarnikPodugovaraca sp=new SifarnikPodugovaraca();
				sp.setNaziv(naziv);
				
				if(idO.isPresent()) {
					sp.setId(idO.get());
				}
				
				getKodRepository().getSifarnikPodugovaracaRepository().save(sp);
			}
		}
		
		if(greska.isEmpty()) {
			List<SifarnikPodugovaraca> sifarnikPodugovaracaList=getKodRepository().getSifarnikPodugovaracaRepository().findAll();
			
			pi.setRezultat(sifarnikPodugovaracaList);
		} else {
			pi.setGreska(greska);
		}
		
		return pi;
	}

}
