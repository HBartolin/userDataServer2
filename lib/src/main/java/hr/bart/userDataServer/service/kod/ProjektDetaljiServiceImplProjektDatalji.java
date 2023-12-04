package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplProjektDatalji extends Kod {
	private final Long id;

	public ProjektDetaljiServiceImplProjektDatalji(KodRepository kodRepository, Long id) {
		super(kodRepository);
		this.id=id;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		Optional<ProjektDetalji> projektDetalji=getKodRepository().getProjektDetaljiRepository().findById(id);
		
		if(projektDetalji.isPresent()) {
			pi.setRezultat(projektDetalji.get());
		} else {
			String msg=String.format("Ne postoji podatak za ProjektDetalji=%d.", id);
			pi.addGreskaList(msg);
		}
		
		return pi;
	}

}
