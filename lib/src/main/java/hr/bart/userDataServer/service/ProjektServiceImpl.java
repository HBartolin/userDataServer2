package hr.bart.userDataServer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.service.kod.ProjektServiceImplNoviProjekt;
import hr.bart.userDataServer.service.kod.ProjektServiceImplProjekti;
import hr.bart.userDataServer.service.kod.ProjektServiceImplTablicaProjekti;
import hr.bart.userDataServer.service.kod.ProjektServiceImplTraziProjekte;
import hr.bart.userDataServer.service.kod.ProjektServiceImplZatvoriOtvoriProjekt;
import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.ZatvoriOtvori;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjektServiceImpl extends AService implements ProjektService {	
	@Autowired
	private transient ProjektRepository projektRepository;
	
	@Override
	public PojoInterface projekti(Optional<String> status) {
		return new ProjektServiceImplProjekti(getKodRepository(), status).izvrsi();
	}
	
	@Override
	public PojoInterface zatvoriOtvoriProjekt(ZatvoriOtvori zo, Long id, Long ts, Optional<String> status) {
		return new ProjektServiceImplZatvoriOtvoriProjekt(getKodRepository(), projektRepository, zo, id, ts, status).izvrsi();
	}
	
	@Override
	public PojoInterface noviProjekt(String claim, String contract) {
		return new ProjektServiceImplNoviProjekt(getKodRepository(), projektRepository, claim, contract).izvrsi();
	}
	
	@Override
	public PojoInterface tablicaProjekti(int pageNumber, Optional<String> status) {
		return new ProjektServiceImplTablicaProjekti(getKodRepository(), status, pageNumber).izvrsi();
	}
	
	@Override
	public PojoInterface traziProjekt(String trazi) {
		return new ProjektServiceImplTraziProjekte(projektRepository, trazi).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setProjektRepository(projektRepository);
		
		return kodRepository;
	}
	
}
