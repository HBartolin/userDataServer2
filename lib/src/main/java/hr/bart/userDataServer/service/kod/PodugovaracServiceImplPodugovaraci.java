package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Podugovarac;
import hr.bart.userDataServer.util.PojoInterface;

public class PodugovaracServiceImplPodugovaraci extends Kod {
	private final Long idProjektDetalji;

	public PodugovaracServiceImplPodugovaraci(KodRepository kodRepository, Long idProjektDetalji) {
		super(kodRepository);
		this.idProjektDetalji=idProjektDetalji;
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
