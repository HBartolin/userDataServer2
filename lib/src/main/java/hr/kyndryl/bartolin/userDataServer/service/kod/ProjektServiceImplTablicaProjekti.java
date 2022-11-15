package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.kyndryl.bartolin.userDataServer.util.DbStatus;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class ProjektServiceImplTablicaProjekti extends Kod {
	private int pageNumber=(int) hm.get("pageNumber");
	private Optional<String> status=(Optional<String>) hm.get("status");
	private ACommonServis aCommonServis=new ACommonServis(hm, kodRepository);

	public ProjektServiceImplTablicaProjekti(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		if(status.isPresent()) {
			aCommonServis.findByStatus(pi, DbStatus.valueOf(status.get()), pageRequest);
		} else {
			aCommonServis.findAll_projekt(pi, pageRequest);
		}
		
		return pi;
	}

}
