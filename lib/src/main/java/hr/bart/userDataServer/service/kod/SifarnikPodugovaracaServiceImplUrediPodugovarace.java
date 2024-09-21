package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringExclude;

import hr.bart.userDataServer.db.SifarnikPodugovaraca;
import hr.bart.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class SifarnikPodugovaracaServiceImplUrediPodugovarace extends Kod {
	@ToStringExclude
	private final SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;
	
	public SifarnikPodugovaracaServiceImplUrediPodugovarace(SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository) {
		this.sifarnikPodugovaracaRepository = sifarnikPodugovaracaRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Map<String, Object> map) throws Throwable {
		List<SifarnikPodugovaraca> sifarnikPodugovaracaList=sifarnikPodugovaracaRepository.findAll();
		
		pi.setRezultat(sifarnikPodugovaracaList);
		
		return pi;
	}

}
