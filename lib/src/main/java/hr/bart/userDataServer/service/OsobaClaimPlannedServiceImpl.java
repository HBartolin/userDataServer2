package hr.bart.userDataServer.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.service.kod.OsobaClaimPlannedServiceImplClaimNewPlannedByDate;
import hr.bart.userDataServer.service.kod.OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

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
		return new OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate(
				osobaClaimPlannedRepository,
				sifarnikMjesecaRepository,
				claimRepository,
				osobaClaimActualRepository,
				projektDetaljiRepository,
				claimPodugovaracRepository,
				datum, 
				podatci, 
				idProjektDetalji).izvrsi();
	}
	
	@Override
	public PojoInterface claimNewPlannedByDate(Long idProjektDetalji, LocalDate datum, HashMap<String, String> podatci) {		
		return new OsobaClaimPlannedServiceImplClaimNewPlannedByDate(
				claimRepository,
				osobaClaimPlannedRepository,
				osobaClaimActualRepository,
				sifarnikMjesecaRepository,
				projektDetaljiRepository,
				claimPodugovaracRepository,
				datum, 
				podatci, 
				idProjektDetalji).izvrsi();
	}
	
}
