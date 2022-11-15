package hr.kyndryl.bartolin.userDataServer.service;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.ClaimPodugovaracRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ClaimRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimActualRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaValutaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ProjektDetaljiRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ProjektRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikDatumaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikMjesecaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikOsobaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikValutaRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.RestServiceImplCreateDB;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@Service
@Transactional
public class RestServiceImpl extends AService implements RestService {
	@Autowired
	private transient SifarnikOsobaRepository sifarnikOsobaRepository;
	@Autowired
	private transient SifarnikValutaRepository sifarnikValutaRepository;
	@Autowired
	private transient SifarnikDatumaRepository sifarnikDatumaRepository;
	@Autowired
	private transient SifarnikMjesecaRepository sifarnikMjesecaRepository;
	@Autowired
	private transient OsobaValutaRepository osobaValutaRepository;
	@Autowired
	private transient ProjektRepository projektRepository;
	@Autowired
	private transient ProjektDetaljiRepository projektDetaljiRepository;
	@Autowired
	private transient SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;
	@Autowired
	private transient ClaimRepository claimRepository;
	@Autowired
	private transient ClaimPodugovaracRepository claimPodugovaracRepository;
	@Autowired
	private transient OsobaClaimActualRepository osobaClaimActualRepository;
	@Autowired
	private transient OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	
	@Override
	public PojoInterface createDB() {
		HashMap<String, Object> hm=new HashMap<>();
		
		return new RestServiceImplCreateDB(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setSifarnikOsobaRepository(sifarnikOsobaRepository);
		kodRepository.setSifarnikValutaRepository(sifarnikValutaRepository);
		kodRepository.setSifarnikDatumaRepository(sifarnikDatumaRepository);
		kodRepository.setSifarnikMjesecaRepository(sifarnikMjesecaRepository);
		kodRepository.setOsobaValutaRepository(osobaValutaRepository);
		kodRepository.setProjektRepository(projektRepository);
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setSifarnikPodugovaracaRepository(sifarnikPodugovaracaRepository);
		kodRepository.setClaimRepository(claimRepository);
		kodRepository.setClaimPodugovaracRepository(claimPodugovaracRepository);
		kodRepository.setOsobaClaimActualRepository(osobaClaimActualRepository);
		kodRepository.setOsobaClaimPlannedRepository(osobaClaimPlannedRepository);
		
		return kodRepository;
	}
}
