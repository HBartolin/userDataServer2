package hr.bart.userDataServer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikDatumaRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.repository.SifarnikValutaRepository;
import hr.bart.userDataServer.service.kod.OsobaValutaServiceImplOsobaValuta;
import hr.bart.userDataServer.service.kod.OsobaValutaServiceImplTablicaOsobaValuta;
import hr.bart.userDataServer.service.kod.ProjektDetaljiRepositoryUnesiOsobaValuta;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OsobaValutaServiceImpl extends AService implements OsobaValutaService {
	@Autowired
	private transient OsobaValutaRepository osobaValutaRepository;
	@Autowired
	private transient SifarnikDatumaRepository sifarnikDatumaRepository;
	@Autowired
	private transient SifarnikMjesecaRepository sifarnikMjesecaRepository;
	@Autowired
	private transient SifarnikOsobaRepository sifarnikOsobaRepository;
	@Autowired
	private transient SifarnikValutaRepository sifarnikValutaRepository;
	@Autowired
	private transient ClaimRepository claimRepository; 
	@Autowired
	private transient OsobaClaimActualRepository osobaClaimActualRepository;
	@Autowired
	private transient ProjektDetaljiRepository projektDetaljiRepository;
	@Autowired
	private transient OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	
	@Override
	public PojoInterface osobaValuta(Long idSifarnikOsoba) {				
		return new OsobaValutaServiceImplOsobaValuta(osobaValutaRepository, idSifarnikOsoba).izvrsi();
	}

	@Override
	public PojoInterface unesiOsobaValuta(Optional<Long> id, Long ts, Long idSifarnikOsoba, String band, BigDecimal cijena, LocalDate sifarnikDatumaOdLD, LocalDate sifarnikDatumaDoLD) {
		return new ProjektDetaljiRepositoryUnesiOsobaValuta(
				sifarnikDatumaRepository,
				osobaValutaRepository,
				sifarnikOsobaRepository,
				sifarnikValutaRepository,
				osobaClaimActualRepository,
				claimRepository,
				projektDetaljiRepository,
				sifarnikMjesecaRepository,
				osobaClaimPlannedRepository,
				id,
				ts,
				idSifarnikOsoba,
				band,
				cijena,
				sifarnikDatumaOdLD,
				sifarnikDatumaDoLD).izvrsi();
	}
	
	@Override
	public PojoInterface tablicaOsobaValuta(int pageNumber, Long idSifarnikOsoba) {		
		return new OsobaValutaServiceImplTablicaOsobaValuta(osobaValutaRepository, idSifarnikOsoba, pageNumber).izvrsi();	
	}
	
}
