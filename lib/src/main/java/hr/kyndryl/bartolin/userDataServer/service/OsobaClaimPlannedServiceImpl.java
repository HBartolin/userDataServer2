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
import hr.kyndryl.bartolin.userDataServer.repository.ProjektDetaljiRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikMjesecaRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.OsobaClaimPlannedServiceImplClaimNewPlannedByDate;
import hr.kyndryl.bartolin.userDataServer.service.kod.OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate;
import hr.kyndryl.bartolin.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@Service
@Transactional
public class OsobaClaimPlannedServiceImpl extends AService implements OsobaClaimPlannedService {
	@Autowired
	private transient ClaimRepository claimRepository; 
	@Autowired
	private transient OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	@Autowired
	private transient SifarnikMjesecaRepository sifarnikMjesecaRepository;
	@Autowired
	private transient ProjektDetaljiRepository projektDetaljiRepository;
	@Autowired
	private transient ClaimPodugovaracRepository claimPodugovaracRepository;
	@Autowired
	private transient OsobaClaimActualRepository osobaClaimActualRepository;
	
	
	@Override
	public PojoInterface claimUpdatedPlannedByDate(Long idProjektDetalji, LocalDate datum, List<ClaimUpdatedActualPlanned> podatci) {		
		HashMap<String, Object> hm=new HashMap<String, Object>();
		hm.put("idProjektDetalji", idProjektDetalji);
		hm.put("datum", datum);
		hm.put("podatci", podatci);
		
		return new OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface claimNewPlannedByDate(Long idProjektDetalji, LocalDate datum, HashMap<String, String> podatci) {		
		HashMap<String, Object> hm=new HashMap<String, Object>();
		hm.put("idProjektDetalji", idProjektDetalji);
		hm.put("datum", datum);
		hm.put("podatci", podatci);
		
		return new OsobaClaimPlannedServiceImplClaimNewPlannedByDate(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setClaimRepository(claimRepository);
		kodRepository.setOsobaClaimPlannedRepository(osobaClaimPlannedRepository);
		kodRepository.setSifarnikMjesecaRepository(sifarnikMjesecaRepository);
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setClaimPodugovaracRepository(claimPodugovaracRepository);
		kodRepository.setOsobaClaimActualRepository(osobaClaimActualRepository);
		
		return kodRepository;
	}
	
}
