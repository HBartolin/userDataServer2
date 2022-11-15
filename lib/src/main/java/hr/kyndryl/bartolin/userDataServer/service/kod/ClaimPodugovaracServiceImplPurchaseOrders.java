package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.db.ClaimPodugovarac;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public class ClaimPodugovaracServiceImplPurchaseOrders extends Kod {
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");
	
	public ClaimPodugovaracServiceImplPurchaseOrders(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
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

	
