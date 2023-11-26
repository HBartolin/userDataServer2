package hr.bart.userDataServer.service;

import java.util.HashMap;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.service.kod.SifarnikOsobaServiceImplEditirajSifarnikOsoba;
import hr.bart.userDataServer.service.kod.SifarnikOsobaServiceImplSifarniciOsoba;
import hr.bart.userDataServer.service.kod.SifarnikOsobaServiceImplTablicaSifarnikOsoba;
import hr.bart.userDataServer.util.PojoInterface;

@Service
@Transactional
public class SifarnikOsobaServiceImpl extends AService implements SifarnikOsobaService {
	@Autowired
	private transient SifarnikOsobaRepository sifarnikOsobaRepository;
	
	@Override
	public PojoInterface sifarniciOsoba() {
		return new SifarnikOsobaServiceImplSifarniciOsoba(getKodRepository()).izvrsi();
	}

	@Override
	public PojoInterface editirajSifarnikOsoba(Optional<Long> id, String ime, String prezime) {	
		return new SifarnikOsobaServiceImplEditirajSifarnikOsoba(getKodRepository(), id, ime, prezime).izvrsi();
	}
	
	@Override
	public PojoInterface tablicaSifarnikOsoba(int pageNumber) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("pageNumber", pageNumber);
		
		return new SifarnikOsobaServiceImplTablicaSifarnikOsoba(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setSifarnikOsobaRepository(sifarnikOsobaRepository);

		return kodRepository;
	}
	
}
