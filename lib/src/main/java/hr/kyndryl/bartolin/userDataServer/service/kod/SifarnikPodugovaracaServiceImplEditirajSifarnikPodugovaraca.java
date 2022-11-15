package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.db.SifarnikPodugovaraca;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca extends Kod {
	private Optional<Long> idO=(Optional<Long>) hm.get("idO");
	private Optional<String> nazivO=(Optional<String>) hm.get("nazivO");

	public SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		String greska="";
		
		if(!nazivO.isPresent()) {
			if(greska.length()>0) greska=greska + " <BR> ";
			greska=greska + "Polje Naziv nije upisano.";
		} else {
			String naziv=nazivO.get();
			
			if(naziv==null || naziv.isEmpty()) {
				if(greska.length()>0) greska=greska + " <BR> ";
				greska=greska + "Polje Naziv nije upisano.";
			} else {
				SifarnikPodugovaraca sp=new SifarnikPodugovaraca();
				sp.setNaziv(naziv);
				
				if(idO.isPresent()) {
					sp.setId(idO.get());
				}
				
				kodRepository.getSifarnikPodugovaracaRepository().save(sp);
			}
		}
		
		if(greska.isEmpty()) {
			List<SifarnikPodugovaraca> sifarnikPodugovaracaList=kodRepository.getSifarnikPodugovaracaRepository().findAll();
			
			pi.setRezultat(sifarnikPodugovaracaList);
		} else {
			pi.setGreska(greska);
		}
		
		return pi;
	}

}
