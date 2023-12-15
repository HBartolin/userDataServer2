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
		return new ClaimPodugovaracServiceImplPurchaseOrders(claimPodugovaracRepository, idProjektDetalji).izvrsi();
	}

	@Override
	public PojoInterface unesiPO(Optional<Long> idO, Optional<Long> tsO, Long idProjektDetalji, Long idSifarnikPodugovaraca, String po, Optional<BigDecimal> totalO) {
		return new ClaimPodugovaracServiceImplUnesiPO(
				claimPodugovaracRepository, 
				projektDetaljiRepository,
				sifarnikPodugovaracaRepository, 
				idO, 
				tsO, 
				idProjektDetalji,
				idSifarnikPodugovaraca,
				po,
				totalO).izvrsi();
	}

}
