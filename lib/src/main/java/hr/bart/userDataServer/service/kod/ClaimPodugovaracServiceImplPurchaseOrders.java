package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.util.PojoInterface;

public class ClaimPodugovaracServiceImplPurchaseOrders extends Kod {
	private final Long idProjektDetalji;
	
	public ClaimPodugovaracServiceImplPurchaseOrders(KodRepository kodRepository, Long idProjektDetalji) {
		super(kodRepository);
		this.idProjektDetalji=idProjektDetalji;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {		
		Optional<List<ClaimPodugovarac>> purchaseOrderListO=kodRepository.getClaimPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(purchaseOrderListO.isPresent()) {
			pi.setRezultat(purchaseOrderListO.get());
		}
		
		return pi;
	}
}

	
