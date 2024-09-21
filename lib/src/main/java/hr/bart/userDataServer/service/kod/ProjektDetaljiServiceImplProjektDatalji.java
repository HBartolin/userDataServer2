package hr.bart.userDataServer.service.kod;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringExclude;

import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplProjektDatalji extends Kod {
	private final Long id;
	@ToStringExclude
	private final ProjektDetaljiRepository projektDetaljiRepositor;

	public ProjektDetaljiServiceImplProjektDatalji(ProjektDetaljiRepository projektDetaljiRepository, Long id) {
		this.projektDetaljiRepositor=projektDetaljiRepository;
		this.id=id;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Map<String, Object> map) throws Throwable {
		Optional<ProjektDetalji> projektDetalji=projektDetaljiRepositor.findById(id);
		
		if(projektDetalji.isPresent()) {
			pi.setRezultat(projektDetalji.get());
		} else {
			String msg=String.format("Ne postoji podatak za ProjektDetalji=%d.", id);
			pi.addGreskaList(msg);
		}
		
		return pi;
	}

}
