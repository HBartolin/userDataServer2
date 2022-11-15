package hr.kyndryl.bartolin.userDataServer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.ClaimRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimActualRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaValutaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ProjektDetaljiRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikDatumaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikMjesecaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikOsobaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikValutaRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.OsobaValutaServiceImplOsobaValuta;
import hr.kyndryl.bartolin.userDataServer.service.kod.OsobaValutaServiceImplTablicaOsobaValuta;
import hr.kyndryl.bartolin.userDataServer.service.kod.ProjektDetaljiRepositoryUnesiOsobaValuta;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

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
		HashMap<String, Object> hm=new HashMap<String, Object>();
		hm.put("idSifarnikOsoba", idSifarnikOsoba);
		hm.put("osobaValutaRepository", osobaValutaRepository);
		hm.put("osobaClaimPlannedRepository", osobaClaimPlannedRepository);
		
		return new OsobaValutaServiceImplOsobaValuta(hm, getKodRepository()).izvrsi();
	}

	@Override
	public PojoInterface unesiOsobaValuta(Optional<Long> id, Long ts, Long idSifarnikOsoba, String band, BigDecimal cijena, LocalDate sifarnikDatumaOdLD, LocalDate sifarnikDatumaDoLD) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("id", id);
		hm.put("ts", ts);
		hm.put("idSifarnikOsoba", idSifarnikOsoba);
		hm.put("band", band);
		hm.put("cijena", cijena);
		hm.put("sifarnikDatumaOdLD", sifarnikDatumaOdLD);
		hm.put("sifarnikDatumaDoLD", sifarnikDatumaDoLD);
		
		return new ProjektDetaljiRepositoryUnesiOsobaValuta(hm, getKodRepository()).izvrsi();
	}
	
	@Override
	public PojoInterface tablicaOsobaValuta(int pageNumber, Long idSifarnikOsoba) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("pageNumber", pageNumber);
		hm.put("idSifarnikOsoba", idSifarnikOsoba);
		
		return new OsobaValutaServiceImplTablicaOsobaValuta(hm, getKodRepository()).izvrsi();	
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setSifarnikDatumaRepository(sifarnikDatumaRepository);
		kodRepository.setOsobaValutaRepository(osobaValutaRepository);
		kodRepository.setSifarnikOsobaRepository(sifarnikOsobaRepository);
		kodRepository.setSifarnikValutaRepository(sifarnikValutaRepository);
		kodRepository.setOsobaClaimActualRepository(osobaClaimActualRepository);
		kodRepository.setClaimRepository(claimRepository);
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setSifarnikMjesecaRepository(sifarnikMjesecaRepository);
		kodRepository.setOsobaClaimPlannedRepository(osobaClaimPlannedRepository);
		
		return kodRepository;
	}

}
