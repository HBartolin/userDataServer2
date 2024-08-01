package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ClaimPodugovaracServiceImplPurchaseOrders extends Kod {
	private final Long idProjektDetalji;
	private final ClaimPodugovaracRepository claimPodugovaracRepository;
	
	public ClaimPodugovaracServiceImplPurchaseOrders(ClaimPodugovaracRepository claimPodugovaracRepository, Long idProjektDetalji) {
		this.claimPodugovaracRepository=claimPodugovaracRepository;
		this.idProjektDetalji=idProjektDetalji;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {		
		Optional<List<ClaimPodugovarac>> purchaseOrderListO=claimPodugovaracRepository.findAllByIdProjektDetalji(idProjektDetalji);
		
		if(purchaseOrderListO.isPresent()) {
			pi.setRezultat(purchaseOrderListO.get());
		}
		
		return pi;
	}
	
	@Override
	public String getToString(String timerKodaEnd) {
		return getClass().getSimpleName() + " " + timerKodaEnd + " " + new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}

	
