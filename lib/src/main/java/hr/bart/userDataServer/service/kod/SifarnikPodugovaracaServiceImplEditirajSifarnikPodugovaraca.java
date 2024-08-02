package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.SifarnikPodugovaraca;
import hr.bart.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca extends Kod {
	private final Optional<Long> idO;
	private final Optional<String> nazivO;
	private final SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;

	public SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca(SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository, Optional<Long> idO, Optional<String> nazivO) {
		this.idO=idO;
		this.nazivO=nazivO;
		this.sifarnikPodugovaracaRepository = sifarnikPodugovaracaRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {		
		if(!nazivO.isPresent()) {
			String msg="Polje Naziv nije upisano.";
			pi.addGreskaList(msg);
		} else {
			String naziv=nazivO.get();
			
			if(naziv==null || naziv.isEmpty()) {
				String msg="Polje Naziv nije upisano.";
				pi.addGreskaList(msg);
			} else {
				SifarnikPodugovaraca sp=new SifarnikPodugovaraca();
				sp.setNaziv(naziv);
				
				if(idO.isPresent()) {
					sp.setId(idO.get());
				}
				
				sifarnikPodugovaracaRepository.save(sp);
			}
		}
		
		if(pi.getGreska().isEmpty()) {
			List<SifarnikPodugovaraca> sifarnikPodugovaracaList=sifarnikPodugovaracaRepository.findAll();
			
			pi.setRezultat(sifarnikPodugovaracaList);
		} else {
			
		}
		
		return pi;
	}

}
