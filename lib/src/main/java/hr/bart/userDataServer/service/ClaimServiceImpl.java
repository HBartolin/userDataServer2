package hr.bart.userDataServer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.service.kod.ClaimServiceImplClaimImena;
import hr.bart.userDataServer.service.kod.ClaimServiceImplNovaOsoba;
import hr.bart.userDataServer.service.kod.ClaimServiceImplTablicaOsobaValuta;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

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
		return new ClaimServiceImplTablicaOsobaValuta(
				claimRepository, 
				osobaClaimActualRepository,
				osobaClaimPlannedRepository,
				idProjektDetalji).izvrsi();
	}
	
	@Override
	public PojoInterface novaOsoba() {		
		return new ClaimServiceImplNovaOsoba().izvrsi();
	} 
	
	@Override
	public PojoInterface claimImena(Optional<List<Long>> podatciO) {	
		return new ClaimServiceImplClaimImena(podatciO).izvrsi();
	}

}
