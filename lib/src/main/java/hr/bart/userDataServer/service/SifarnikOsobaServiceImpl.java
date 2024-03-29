package hr.bart.userDataServer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.service.kod.SifarnikOsobaServiceImplEditirajSifarnikOsoba;
import hr.bart.userDataServer.service.kod.SifarnikOsobaServiceImplSifarniciOsoba;
import hr.bart.userDataServer.service.kod.SifarnikOsobaServiceImplTablicaSifarnikOsoba;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SifarnikOsobaServiceImpl extends AService implements SifarnikOsobaService {
	@Autowired
	private transient SifarnikOsobaRepository sifarnikOsobaRepository;
	
	@Override
	public PojoInterface sifarniciOsoba() {
		return new SifarnikOsobaServiceImplSifarniciOsoba(sifarnikOsobaRepository).izvrsi();
	}

	@Override
	public PojoInterface editirajSifarnikOsoba(Optional<Long> id, String ime, String prezime) {	
		return new SifarnikOsobaServiceImplEditirajSifarnikOsoba(sifarnikOsobaRepository, id, ime, prezime).izvrsi();
	}
	
	@Override
	public PojoInterface tablicaSifarnikOsoba(int pageNumber) {
		return new SifarnikOsobaServiceImplTablicaSifarnikOsoba(sifarnikOsobaRepository, pageNumber).izvrsi();
	}
	
}
