package hr.bart.userDataServer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.PodugovaracRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.service.kod.PodugovaracServiceImplPodugovaraci;
import hr.bart.userDataServer.service.kod.PodugovaracServiceImplUnesiPodugovarac;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PodugovaracServiceImpl extends AService implements PodugovaracService {
	@Autowired
	private transient PodugovaracRepository podugovaracRepository;
	@Autowired
	private transient ProjektDetaljiRepository projektDetaljiRepository;
	@Autowired
	private transient ClaimPodugovaracRepository claimPodugovaracRepository; 
	@Autowired
	private transient OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	@Autowired
	private transient OsobaClaimActualRepository osobaClaimActualRepository;
	@Autowired
	private transient ClaimRepository claimRepository;
	
	@Override
	public PojoInterface podugovaraci(Long idProjektDetalji) {		
		return new PodugovaracServiceImplPodugovaraci(podugovaracRepository, idProjektDetalji).izvrsi();
	}

	@Override
	public PojoInterface unesiPodugovarac(Optional<Long> id, Long ts, Long idProjektDetalji, Optional<Long> idPurchaseOrder, LocalDate datumPlanned, LocalDate datumActual, Optional<BigDecimal> cijena, Optional<Long> invoiceNumber) {
		return new PodugovaracServiceImplUnesiPodugovarac(
				getKodRepository(),
				id,
				ts,
				idProjektDetalji,
				idPurchaseOrder,
				datumPlanned,
				datumActual,
				cijena,
				invoiceNumber).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setClaimPodugovaracRepository(claimPodugovaracRepository);
		kodRepository.setPodugovaracRepository(podugovaracRepository);
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setOsobaClaimPlannedRepository(osobaClaimPlannedRepository);
		kodRepository.setOsobaClaimActualRepository(osobaClaimActualRepository);
		kodRepository.setClaimRepository(claimRepository);
		
		return kodRepository;
	}

}
