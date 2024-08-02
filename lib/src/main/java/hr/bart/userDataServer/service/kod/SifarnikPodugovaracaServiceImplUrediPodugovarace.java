package hr.bart.userDataServer.service.kod;

import java.util.List;

import hr.bart.userDataServer.db.SifarnikPodugovaraca;
import hr.bart.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikPodugovaracaServiceImplUrediPodugovarace extends Kod {
	private final SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;
	
	public SifarnikPodugovaracaServiceImplUrediPodugovarace(SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository) {
		this.sifarnikPodugovaracaRepository = sifarnikPodugovaracaRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		List<SifarnikPodugovaraca> sifarnikPodugovaracaList=sifarnikPodugovaracaRepository.findAll();
		
		pi.setRezultat(sifarnikPodugovaracaList);
		
		return pi;
	}

}
