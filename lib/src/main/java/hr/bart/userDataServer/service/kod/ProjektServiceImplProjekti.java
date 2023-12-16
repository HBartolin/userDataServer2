package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektServiceImplProjekti extends Kod {
	private final Optional<String> status;
	private ACommonServis aCommonServis=new ACommonServis();
	private final ProjektRepository projektRepository;

	public ProjektServiceImplProjekti(ProjektRepository projektRepository, Optional<String> status) {
		this.status=status;
		this.projektRepository = projektRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		if(status.isPresent()) {				
			aCommonServis.findByStatus(pi, projektRepository, DbStatus.valueOf(status.get()), pageRequest);
		} else {
			aCommonServis.findAll_projekt(pi, pageRequest);
		}	
		
		return pi;
	}

}
