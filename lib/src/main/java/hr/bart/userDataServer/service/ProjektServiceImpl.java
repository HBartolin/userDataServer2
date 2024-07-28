package hr.bart.userDataServer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProjektServiceImpl extends AService {	
	@Autowired
	private transient ProjektServiceImplProjekti projektServiceImplProjekti;
	@Autowired
	private transient ProjektServiceImplZatvoriOtvoriProjekt projektServiceImplZatvoriOtvoriProjekt;
	@Autowired
	private transient ProjektServiceImplNoviProjekt projektServiceImplNoviProjekt;
	@Autowired
	private transient ProjektServiceImplTablicaProjekti projektServiceImplTablicaProjekti;
	@Autowired
	private transient ProjektServiceImplTraziProjekte projektServiceImplTraziProjekte;

	public PojoInterface projekti(Optional<String> status) {
		return projektServiceImplProjekti.init(status).izvrsi();
	}
	
	public PojoInterface zatvoriOtvoriProjekt(ZatvoriOtvori zo, Long id, Long ts, Optional<String> status) {
		return projektServiceImplZatvoriOtvoriProjekt.init(zo, id, ts, status).izvrsi();
	}
	

	public PojoInterface noviProjekt(String claim, String contract) {
		return projektServiceImplNoviProjekt.info(claim, contract).izvrsi();
	}
	
	
	public PojoInterface tablicaProjekti(int pageNumber, Optional<String> status) {
		return projektServiceImplTablicaProjekti.info(status, pageNumber).izvrsi();
	}
	
	
	public PojoInterface traziProjekt(String trazi) {
		return projektServiceImplTraziProjekte.info(trazi).izvrsi();
	}
	
}
