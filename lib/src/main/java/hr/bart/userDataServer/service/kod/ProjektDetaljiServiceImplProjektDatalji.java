package hr.bart.userDataServer.service.kod;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjektDetaljiServiceImplProjektDatalji extends Kod {
	private Long id;
	@Autowired
    @ToStringExclude
	private ProjektDetaljiRepository projektDetaljiRepositor;

//	public ProjektDetaljiServiceImplProjektDatalji(ProjektDetaljiRepository projektDetaljiRepository, Long id) {
//		this.projektDetaljiRepositor=projektDetaljiRepository;
//		this.id=id;
//	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) throws Throwable {
	    id=(Long) o[0];
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
