package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektServiceImplTablicaProjekti extends Kod {
	private final int pageNumber;
	private final Optional<String> status;
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);

	public ProjektServiceImplTablicaProjekti(KodRepository kodRepository, Optional<String> status, int pageNumber) {
		super(kodRepository);
		this.status=status;
		this.pageNumber=pageNumber;
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
