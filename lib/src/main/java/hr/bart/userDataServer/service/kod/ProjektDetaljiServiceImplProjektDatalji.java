package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplProjektDatalji extends Kod {
	private Long id;

	public ProjektDetaljiServiceImplProjektDatalji(KodRepository kodRepository, Long id) {
		super(kodRepository);
		this.id=id;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		Optional<ProjektDetalji> projektDetalji=kodRepository.getProjektDetaljiRepository().findById(id);
		
		if(projektDetalji.isPresent()) {
			pi.setRezultat(projektDetalji.get());
		} else {
			pi.setGreska(String.format("Ne postoji podatak za ProjektDetalji=%d.", id));
		}
		
		return pi;
	}

}
