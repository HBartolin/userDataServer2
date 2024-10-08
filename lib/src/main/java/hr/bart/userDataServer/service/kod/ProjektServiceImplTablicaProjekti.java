package hr.bart.userDataServer.service.kod;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringExclude;
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
	@ToStringExclude
	private ACommonServis aCommonServis=new ACommonServis();
	@ToStringExclude
	private ProjektRepository projektRepository;

//	public ProjektServiceImplTablicaProjekti info(Optional<String> status, int pageNumber) {
//		this.status=status;
//		this.pageNumber=pageNumber;
//		
//		return this;
//	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) throws Throwable {
	    status=(Optional<String>) o[0];
	    pageNumber=(Integer) o[1];
	    
		PageRequest pageRequest=PageRequest.of(pageNumber, pageRequestSize50);
		
		if(status.isPresent()) {
			aCommonServis.findByStatus(pi, projektRepository, DbStatus.valueOf(status.get()), pageRequest);
		} else {
			aCommonServis.findAll_projekt(pi, projektRepository, pageRequest);
		}
		
		return pi;
	}

}
