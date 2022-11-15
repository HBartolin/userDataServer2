package hr.kyndryl.bartolin.userDataServer.service;

import java.util.HashMap;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.SifarnikOsobaRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.SifarnikOsobaServiceImplEditirajSifarnikOsoba;
import hr.kyndryl.bartolin.userDataServer.service.kod.SifarnikOsobaServiceImplSifarniciOsoba;
import hr.kyndryl.bartolin.userDataServer.service.kod.SifarnikOsobaServiceImplTablicaSifarnikOsoba;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@Service
@Transactional
public class SifarnikOsobaServiceImpl extends AService implements SifarnikOsobaService {
	@Autowired
	private transient SifarnikOsobaRepository sifarnikOsobaRepository;
	
	@Override
	public PojoInterface sifarniciOsoba() {
		HashMap<String, Object> hm=new HashMap<>();
		
		return new SifarnikOsobaServiceImplSifarniciOsoba(hm, getKodRepository()).izvrsi();
	}

	@Override
	public PojoInterface editirajSifarnikOsoba(Optional<Long> id, String ime, String prezime) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("id", id);
		hm.put("ime", ime);
		hm.put("prezime", prezime);
		
		return new SifarnikOsobaServiceImplEditirajSifarnikOsoba(hm, getKodRepository()).izvrsi();
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
