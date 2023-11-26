package hr.bart.userDataServer.service.kod;

import java.util.List;

import hr.bart.userDataServer.db.SifarnikPodugovaraca;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikPodugovaracaServiceImplUrediPodugovarace extends Kod {
	
	public SifarnikPodugovaracaServiceImplUrediPodugovarace(KodRepository kodRepository) {
		super(kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		List<SifarnikPodugovaraca> sifarnikPodugovaracaList=kodRepository.getSifarnikPodugovaracaRepository().findAll();
		
		pi.setRezultat(sifarnikPodugovaracaList);
		
		return pi;
	}

}
