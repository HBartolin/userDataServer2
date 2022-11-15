package hr.bart.userDataServer.service;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.repository.SifarnikValutaRepository;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.service.kod.ProjektDetaljiServiceImplProjektDatalji;
import hr.bart.userDataServer.service.kod.ProjektDetaljiServiceImplUrediProjektDatalji;
import hr.bart.userDataServer.util.PojoInterface;

@Service
@Transactional
public class ProjektDetaljiServiceImpl extends AService implements ProjektDetaljiService {
	@Autowired
	private transient ProjektDetaljiRepository projektDetaljiRepository;
	@Autowired
	private transient SifarnikValutaRepository sifarnikValutaRepository;
	@Autowired
	private transient ProjektRepository projektRepository;
	
	@Override
	public PojoInterface projektDatalji(Long id) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("id", id);
		
		return new ProjektDetaljiServiceImplProjektDatalji(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface urediProjektDatalji(Long id, String totalRevenue, String costPs) {

		
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("id", id);
		hm.put("totalRevenue", totalRevenue);
		hm.put("costPs", costPs);
		
		return new ProjektDetaljiServiceImplUrediProjektDatalji(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setSifarnikValutaRepository(sifarnikValutaRepository);
		kodRepository.setProjektRepository(projektRepository);

		return kodRepository;
	}
	
}
