package hr.bart.userDataServer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.service.kod.ClaimServiceImplClaimImena;
import hr.bart.userDataServer.service.kod.ClaimServiceImplNovaOsoba;
import hr.bart.userDataServer.service.kod.ClaimServiceImplTablicaOsobaValuta;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.util.PojoInterface;

@Service
@Transactional
public class ClaimServiceImpl extends AService implements ClaimService {
	@Autowired
	private transient ClaimRepository claimRepository;
	@Autowired
	private transient OsobaClaimActualRepository osobaClaimActualRepository;
	@Autowired
	private transient OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	
	@Override
	public PojoInterface tablicaOsobaValuta(Long idProjektDetalji) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("idProjektDetalji", idProjektDetalji);
		
		return new ClaimServiceImplTablicaOsobaValuta(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface novaOsoba() {		
		HashMap<String, Object> hm=new HashMap<>();
		
		return new ClaimServiceImplNovaOsoba(hm, getKodRepository()).izvrsi();
	} 
	
	@Override
	public PojoInterface claimImena(Optional<List<Long>> podatciO) {		
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("podatciO", podatciO);
		
		return new ClaimServiceImplClaimImena(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setClaimRepository(claimRepository);
		kodRepository.setOsobaClaimActualRepository(osobaClaimActualRepository);
		kodRepository.setOsobaClaimPlannedRepository(osobaClaimPlannedRepository);
		
		return kodRepository;
	}


}
