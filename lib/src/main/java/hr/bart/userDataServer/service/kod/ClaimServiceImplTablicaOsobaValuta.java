package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.util.PojoInterface;

public class ClaimServiceImplTablicaOsobaValuta extends Kod {
	private final Long idProjektDetalji;
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);

	public ClaimServiceImplTablicaOsobaValuta(KodRepository kodRepository, Long idProjektDetalji) {
		super(kodRepository);
		this.idProjektDetalji=idProjektDetalji;
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
