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
import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikDatumaRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.service.kod.OsobaClaimActualServiceImplClaimNewActualByDate;
import hr.bart.userDataServer.service.kod.OsobaClaimActualServiceImplClaimUpdatedActualByDate;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

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
		return new OsobaClaimActualServiceImplClaimUpdatedActualByDate(
				osobaClaimActualRepository,
				sifarnikDatumaRepository,
				claimRepository,
				osobaClaimPlannedRepository,
				projektDetaljiRepository, 
				claimPodugovaracRepository,
				osobaValutaRepository,
				idProjektDetalji, 
				datum, 
				podatci).izvrsi();
	}
	
	@Override
	public PojoInterface claimNewActualByDate(Long idProjektDetalji, LocalDate datum, HashMap<String, String> podatci) {		
		return new OsobaClaimActualServiceImplClaimNewActualByDate(
				claimRepository,
				sifarnikDatumaRepository,
				osobaClaimActualRepository,
				osobaClaimPlannedRepository,
				projektDetaljiRepository, 
				claimPodugovaracRepository,
				osobaValutaRepository,
				sifarnikMjesecaRepository,
				idProjektDetalji, 
				datum, 
				podatci).izvrsi();
	}
	
}
