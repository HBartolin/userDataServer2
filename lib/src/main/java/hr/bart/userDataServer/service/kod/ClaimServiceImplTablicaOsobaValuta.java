package hr.bart.userDataServer.service.kod;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.util.PojoInterface;

public class ClaimServiceImplTablicaOsobaValuta extends Kod {
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");
	private ACommonServis aCommonServis=new ACommonServis(hm, kodRepository);

	public ClaimServiceImplTablicaOsobaValuta(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {		
		Optional<List<Claim>> claimListOptional=aCommonServis.claimActualPlanned(idProjektDetalji);
		
		if(claimListOptional.isPresent()) {
			pi.setRezultat(claimListOptional.get());
		}
		
		return pi;
	}
	
}
