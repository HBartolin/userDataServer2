package hr.kyndryl.bartolin.userDataServer.service;

import java.util.HashMap;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.ProjektRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.ProjektServiceImplNoviProjekt;
import hr.kyndryl.bartolin.userDataServer.service.kod.ProjektServiceImplProjekti;
import hr.kyndryl.bartolin.userDataServer.service.kod.ProjektServiceImplTablicaProjekti;
import hr.kyndryl.bartolin.userDataServer.service.kod.ProjektServiceImplTraziProjekte;
import hr.kyndryl.bartolin.userDataServer.service.kod.ProjektServiceImplZatvoriOtvoriProjekt;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;
import hr.kyndryl.bartolin.userDataServer.util.ZatvoriOtvori;

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
