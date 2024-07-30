package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjektServiceImplTablicaProjekti extends Kod {
	private int pageNumber;
	private Optional<String> status;
	private ACommonServis aCommonServis=new ACommonServis();
	private ProjektRepository projektRepository;

	public ProjektServiceImplTablicaProjekti info(Optional<String> status, int pageNumber) {
		this.status=status;
		this.pageNumber=pageNumber;
		
		return this;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		if(status.isPresent()) {
			aCommonServis.findByStatus(pi, projektRepository, DbStatus.valueOf(status.get()), pageRequest);
		} else {
			aCommonServis.findAll_projekt(pi, projektRepository, pageRequest);
		}
		
		return pi;
	}

}
