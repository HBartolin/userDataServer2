package hr.kyndryl.bartolin.userDataServer.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.kyndryl.bartolin.userDataServer.repository.ClaimPodugovaracRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ProjektDetaljiRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.kyndryl.bartolin.userDataServer.service.kod.ClaimPodugovaracServiceImplPurchaseOrders;
import hr.kyndryl.bartolin.userDataServer.service.kod.ClaimPodugovaracServiceImplUnesiPO;
import hr.kyndryl.bartolin.userDataServer.service.kod.KodRepository;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

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
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("idProjektDetalji", idProjektDetalji);
		
		return new ClaimPodugovaracServiceImplPurchaseOrders(hm, getKodRepository()).izvrsi();
	}

	@Override
	public PojoInterface unesiPO(Optional<Long> idO, Optional<Long> tsO, Long idProjektDetalji, Long idSifarnikPodugovaraca, String po, Optional<BigDecimal> totalO) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("idO", idO);
		hm.put("tsO", tsO);
		hm.put("idProjektDetalji", idProjektDetalji);
		hm.put("idSifarnikPodugovaraca", idSifarnikPodugovaraca);
		hm.put("po", po);
		hm.put("totalO", totalO);
		
		return new ClaimPodugovaracServiceImplUnesiPO(hm, getKodRepository()).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setClaimPodugovaracRepository(claimPodugovaracRepository);
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setSifarnikPodugovaracaRepository(sifarnikPodugovaracaRepository);
		
		return kodRepository;
	}

}
