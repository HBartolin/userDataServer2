package hr.bart.userDataServer.service;

import java.util.HashMap;
import java.util.Optional;

import jakarta.transaction.Transactional;

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

@Service
@Transactional
public class ProjektServiceImpl extends AService implements ProjektService {	
	@Autowired
	private transient ProjektRepository projektRepository;
	
	@Override
	public PojoInterface projekti(Optional<String> status) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("status", status);
		
		return new ProjektServiceImplProjekti(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface zatvoriOtvoriProjekt(ZatvoriOtvori zo, Long id, Long ts, Optional<String> status) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("zo", zo);
		hm.put("id", id);
		hm.put("ts", ts);
		hm.put("status", status);
		
		return new ProjektServiceImplZatvoriOtvoriProjekt(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface noviProjekt(String claim, String contract) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("claim", claim);
		hm.put("contract", contract);
		
		return new ProjektServiceImplNoviProjekt(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface tablicaProjekti(int pageNumber, Optional<String> status) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("pageNumber", pageNumber);
		hm.put("status", status);
		
		return new ProjektServiceImplTablicaProjekti(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface traziProjekt(String trazi) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("trazi", trazi);
		
		return new ProjektServiceImplTraziProjekte(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setProjektRepository(projektRepository);
		
		return kodRepository;
	}
	
}
