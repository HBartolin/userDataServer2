package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import hr.bart.userDataServer.db.Podugovarac;
import hr.bart.userDataServer.repository.PodugovaracRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class PodugovaracServiceImplPodugovaraci extends Kod {
	private final Long idProjektDetalji;
	private final PodugovaracRepository podugovaracRepository;

	public PodugovaracServiceImplPodugovaraci(PodugovaracRepository podugovaracRepository, Long idProjektDetalji) {
		this.podugovaracRepository=podugovaracRepository;
		this.idProjektDetalji=idProjektDetalji;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		Optional<List<Podugovarac>> podugovaracListO=podugovaracRepository.findAllByIdProjektDetalji(idProjektDetalji);
		
		if(podugovaracListO.isPresent()) {
			pi.setRezultat(podugovaracListO.get());
		} 
		
		return pi;
	}

	@Override
	public String getToString() {
		return new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}
