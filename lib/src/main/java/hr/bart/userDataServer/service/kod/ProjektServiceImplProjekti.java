package hr.bart.userDataServer.service.kod;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class ProjektServiceImplProjekti extends Kod {
	private Optional<String> status=(Optional<String>) hm.get("status");
	private ACommonServis aCommonServis=new ACommonServis(hm, kodRepository);

	public ProjektServiceImplProjekti(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
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
