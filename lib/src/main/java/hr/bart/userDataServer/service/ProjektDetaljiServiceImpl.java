package hr.bart.userDataServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.repository.SifarnikValutaRepository;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.service.kod.ProjektDetaljiServiceImplProjektDatalji;
import hr.bart.userDataServer.service.kod.ProjektDetaljiServiceImplUrediProjektDatalji;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

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
		return new ProjektDetaljiServiceImplProjektDatalji(projektDetaljiRepository, id).izvrsi();
	}
	
	@Override
	public PojoInterface urediProjektDatalji(Long id, String totalRevenue, String costPs) {
		return new ProjektDetaljiServiceImplUrediProjektDatalji(getKodRepository(), id, totalRevenue, costPs).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setSifarnikValutaRepository(sifarnikValutaRepository);
		kodRepository.setProjektRepository(projektRepository);

		return kodRepository;
	}
	
}
