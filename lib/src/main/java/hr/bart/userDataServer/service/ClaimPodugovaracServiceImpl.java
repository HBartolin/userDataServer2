package hr.bart.userDataServer.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.bart.userDataServer.service.kod.ClaimPodugovaracServiceImplPurchaseOrders;
import hr.bart.userDataServer.service.kod.ClaimPodugovaracServiceImplUnesiPO;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClaimPodugovaracServiceImpl extends AService implements ClaimPodugovaracService {
	@Autowired
	private transient ClaimPodugovaracRepository claimPodugovaracRepository;
	@Autowired
	private transient SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;
	@Autowired
	private transient ProjektDetaljiRepository projektDetaljiRepository; 
	
	@Override
	public PojoInterface purchaseOrders(Long idProjektDetalji) {		
		return new ClaimPodugovaracServiceImplPurchaseOrders(getKodRepository(), idProjektDetalji).izvrsi();
	}

	@Override
	public PojoInterface unesiPO(Optional<Long> idO, Optional<Long> tsO, Long idProjektDetalji, Long idSifarnikPodugovaraca, String po, Optional<BigDecimal> totalO) {
		return new ClaimPodugovaracServiceImplUnesiPO(
				getKodRepository(), 
				idO, 
				tsO, 
				idProjektDetalji,
				idSifarnikPodugovaraca,
				po,
				totalO).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setClaimPodugovaracRepository(claimPodugovaracRepository);
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setSifarnikPodugovaracaRepository(sifarnikPodugovaracaRepository);
		
		return kodRepository;
	}

}
