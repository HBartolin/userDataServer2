package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplProjektDatalji extends Kod {
	private final Long id;
	private final ProjektDetaljiRepository projektDetaljiRepositor;

	public ProjektDetaljiServiceImplProjektDatalji(ProjektDetaljiRepository projektDetaljiRepository, Long id) {
		this.projektDetaljiRepositor=projektDetaljiRepository;
		this.id=id;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		Optional<ProjektDetalji> projektDetalji=projektDetaljiRepositor.findById(id);
		
		if(projektDetalji.isPresent()) {
			pi.setRezultat(projektDetalji.get());
		} else {
			String msg=String.format("Ne postoji podatak za ProjektDetalji=%d.", id);
			pi.addGreskaList(msg);
		}
		
		return pi;
	}

	@Override
	public String getToString() {
		return new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}
