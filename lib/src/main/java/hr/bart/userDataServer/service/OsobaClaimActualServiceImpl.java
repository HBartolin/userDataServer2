package hr.bart.userDataServer.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

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
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.service.kod.OsobaClaimActualServiceImplClaimNewActualByDate;
import hr.bart.userDataServer.service.kod.OsobaClaimActualServiceImplClaimUpdatedActualByDate;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;

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
