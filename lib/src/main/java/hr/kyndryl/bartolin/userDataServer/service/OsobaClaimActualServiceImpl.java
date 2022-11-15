package hr.kyndryl.bartolin.userDataServer.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.ClaimPodugovaracRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ClaimRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimActualRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaValutaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ProjektDetaljiRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikDatumaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikMjesecaRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.OsobaClaimActualServiceImplClaimNewActualByDate;
import hr.kyndryl.bartolin.userDataServer.service.kod.OsobaClaimActualServiceImplClaimUpdatedActualByDate;
import hr.kyndryl.bartolin.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@Service
@Transactional
public class OsobaClaimActualServiceImpl extends AService implements OsobaClaimActualService {
	@Autowired
	private transient OsobaClaimActualRepository osobaClaimActualRepository;
	@Autowired
	private transient ClaimRepository claimRepository; 
	@Autowired
	private transient SifarnikDatumaRepository sifarnikDatumaRepository;
	@Autowired
	private transient SifarnikMjesecaRepository sifarnikMjesecaRepository;
	@Autowired
	private transient OsobaValutaRepository osobaValutaRepository; 
	@Autowired
	private transient ProjektDetaljiRepository projektDetaljiRepository;
	@Autowired
	private transient ClaimPodugovaracRepository claimPodugovaracRepository;
	@Autowired
	private transient OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	
	@Override
	public PojoInterface claimUpdatedActualByDate(Long idProjektDetalji, LocalDate datum, List<ClaimUpdatedActualPlanned> podatci) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("idProjektDetalji", idProjektDetalji);
		hm.put("datum", datum);
		hm.put("podatci", podatci);
		
		return new OsobaClaimActualServiceImplClaimUpdatedActualByDate(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface claimNewActualByDate(Long idProjektDetalji, LocalDate datum, HashMap<String, String> podatci) {		
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("idProjektDetalji", idProjektDetalji);
		hm.put("datum", datum);
		hm.put("podatci", podatci);
		
		return new OsobaClaimActualServiceImplClaimNewActualByDate(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setOsobaClaimActualRepository(osobaClaimActualRepository);
		kodRepository.setClaimRepository(claimRepository);
		kodRepository.setSifarnikDatumaRepository(sifarnikDatumaRepository);
		kodRepository.setSifarnikMjesecaRepository(sifarnikMjesecaRepository);
		kodRepository.setOsobaValutaRepository(osobaValutaRepository);
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setClaimPodugovaracRepository(claimPodugovaracRepository);
		kodRepository.setOsobaClaimPlannedRepository(osobaClaimPlannedRepository);
		
		return kodRepository;
	}

	
}
