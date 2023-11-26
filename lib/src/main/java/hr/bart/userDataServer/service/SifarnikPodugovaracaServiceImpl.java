package hr.bart.userDataServer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.service.kod.SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca;
import hr.bart.userDataServer.service.kod.SifarnikPodugovaracaServiceImplUrediPodugovarace;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SifarnikPodugovaracaServiceImpl extends AService implements SifarnikPodugovaracaService {
	@Autowired
	private transient SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;
	
	@Override
	public PojoInterface editirajSifarnikPodugovaraca(Optional<Long> idO, Optional<String> nazivO) {
		return new SifarnikPodugovaracaServiceImplEditirajSifarnikPodugovaraca(getKodRepository(), idO, nazivO).izvrsi();
	}
	
	@Override
	public PojoInterface urediPodugovarace() {
		return new SifarnikPodugovaracaServiceImplUrediPodugovarace(getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setSifarnikPodugovaracaRepository(sifarnikPodugovaracaRepository);

		return kodRepository;
	}
}
