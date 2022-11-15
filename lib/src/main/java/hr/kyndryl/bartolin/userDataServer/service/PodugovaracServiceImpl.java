package hr.kyndryl.bartolin.userDataServer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.ClaimPodugovaracRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ClaimRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimActualRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.kyndryl.bartolin.userDataServer.repository.PodugovaracRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ProjektDetaljiRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.PodugovaracServiceImplPodugovaraci;
import hr.kyndryl.bartolin.userDataServer.service.kod.PodugovaracServiceImplUnesiPodugovarac;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

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
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("idProjektDetalji", idProjektDetalji);
		
		return new PodugovaracServiceImplPodugovaraci(hm, getKodRepository()).izvrsi();
	}

	@Override
	public PojoInterface unesiPodugovarac(Optional<Long> id, Long ts, Long idProjektDetalji, Optional<Long> idPurchaseOrder, LocalDate datumPlanned, LocalDate datumActual, Optional<BigDecimal> cijena, Optional<Long> invoiceNumber) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("id", id);
		hm.put("ts", ts);
		hm.put("idProjektDetalji", idProjektDetalji);
		hm.put("idPurchaseOrder", idPurchaseOrder);
		hm.put("datumPlanned", datumPlanned);
		hm.put("datumActual", datumActual);
		hm.put("cijena", cijena);
		hm.put("invoiceNumber", invoiceNumber);
		
		return new PodugovaracServiceImplUnesiPodugovarac(hm, getKodRepository()).izvrsi();
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
