package hr.kyndryl.bartolin.userDataServer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.ClaimRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimActualRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.ClaimServiceImplClaimImena;
import hr.kyndryl.bartolin.userDataServer.service.kod.ClaimServiceImplNovaOsoba;
import hr.kyndryl.bartolin.userDataServer.service.kod.ClaimServiceImplTablicaOsobaValuta;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

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
