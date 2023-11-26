package hr.bart.userDataServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.repository.SifarnikDatumaRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.bart.userDataServer.repository.SifarnikValutaRepository;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.service.kod.RestServiceImplCreateDB;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

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
		return new RestServiceImplCreateDB(getKodRepository()).izvrsi();
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
