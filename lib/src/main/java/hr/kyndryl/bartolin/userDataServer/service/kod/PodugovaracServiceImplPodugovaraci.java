package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.db.Podugovarac;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public class PodugovaracServiceImplPodugovaraci extends Kod {
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");

	public PodugovaracServiceImplPodugovaraci(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		Optional<List<Podugovarac>> podugovaracListO=kodRepository.getPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(podugovaracListO.isPresent()) {
			pi.setRezultat(podugovaracListO.get());
		} 
		
		return pi;
	}

}
