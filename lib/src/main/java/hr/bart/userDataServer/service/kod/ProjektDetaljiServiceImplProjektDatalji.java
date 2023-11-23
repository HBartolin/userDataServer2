package hr.bart.userDataServer.service.kod;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplProjektDatalji extends Kod {
	private Long id=(Long) hm.get("id");
	@Autowired
	private ProjektDetaljiRepository projektDetaljiRepository;

	public ProjektDetaljiServiceImplProjektDatalji(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}
	
	public ProjektDetaljiServiceImplProjektDatalji(HashMap<String, Object> hm) {
		super(hm);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		System.out.println("!!!! " + projektDetaljiRepository);
		Optional<ProjektDetalji> projektDetalji=projektDetaljiRepository.findById(id);
		
		if(projektDetalji.isPresent()) {
			pi.setRezultat(projektDetalji.get());
		} else {
			pi.setGreska(String.format("Ne postoji podatak za ProjektDetalji=%d.", id));
		}
		
		
		
		return pi;
	}

}
