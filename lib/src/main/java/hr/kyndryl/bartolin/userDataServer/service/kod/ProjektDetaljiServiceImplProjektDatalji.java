package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.util.HashMap;
import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.db.ProjektDetalji;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplProjektDatalji extends Kod {
	private Long id=(Long) hm.get("id");

	public ProjektDetaljiServiceImplProjektDatalji(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
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
