package hr.kyndryl.bartolin.userDataServer.service;

import java.util.HashMap;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca;
import hr.kyndryl.bartolin.userDataServer.service.kod.SifarnikPodugovaracaServiceImplUrediPodugovarace;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@Service
@Transactional
public class SifarnikPodugovaracaServiceImpl extends AService implements SifarnikPodugovaracaService {
	@Autowired
	private transient SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;
	
	@Override
	public PojoInterface editirajSifarnikPodugovaraca(Optional<Long> idO, Optional<String> nazivO) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("idO", idO);
		hm.put("nazivO", nazivO);
		
		return new SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface urediPodugovarace() {
		HashMap<String, Object> hm=new HashMap<>();
		
		return new SifarnikPodugovaracaServiceImplUrediPodugovarace(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setSifarnikPodugovaracaRepository(sifarnikPodugovaracaRepository);

		return kodRepository;
	}
}
