package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringExclude;

import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ClaimPodugovaracServiceImplPurchaseOrders extends Kod {
	private final Long idProjektDetalji;
	@ToStringExclude
	private final ClaimPodugovaracRepository claimPodugovaracRepository;
	
	public ClaimPodugovaracServiceImplPurchaseOrders(ClaimPodugovaracRepository claimPodugovaracRepository, Long idProjektDetalji) {
		this.claimPodugovaracRepository=claimPodugovaracRepository;
		this.idProjektDetalji=idProjektDetalji;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) {		
		Optional<List<ClaimPodugovarac>> purchaseOrderListO=claimPodugovaracRepository.findAllByIdProjektDetalji(idProjektDetalji);
		
		if(purchaseOrderListO.isPresent()) {
			pi.setRezultat(purchaseOrderListO.get());
		}
		
		return pi;
	}
	
}

	
