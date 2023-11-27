package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektServiceImplProjekti extends Kod {
	private final Optional<String> status;
	private ACommonServis aCommonServis=new ACommonServis(getKodRepository());

	public ProjektServiceImplProjekti(KodRepository kodRepository, Optional<String> status) {
		super(kodRepository);
		this.status=status;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		if(status.isPresent()) {				
			aCommonServis.findByStatus(pi, DbStatus.valueOf(status.get()), pageRequest);
		} else {
			aCommonServis.findAll_projekt(pi, pageRequest);
		}	
		
		return pi;
	}

}
