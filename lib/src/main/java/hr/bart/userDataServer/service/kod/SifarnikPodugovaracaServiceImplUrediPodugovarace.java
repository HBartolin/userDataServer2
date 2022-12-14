package hr.bart.userDataServer.service.kod;

import java.util.HashMap;
import java.util.List;

import hr.bart.userDataServer.db.SifarnikPodugovaraca;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikPodugovaracaServiceImplUrediPodugovarace extends Kod {
	
	public SifarnikPodugovaracaServiceImplUrediPodugovarace(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		List<SifarnikPodugovaraca> sifarnikPodugovaracaList=kodRepository.getSifarnikPodugovaracaRepository().findAll();
		
		pi.setRezultat(sifarnikPodugovaracaList);
		
		return pi;
	}

}
