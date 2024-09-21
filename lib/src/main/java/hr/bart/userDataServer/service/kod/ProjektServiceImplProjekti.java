package hr.bart.userDataServer.service.kod;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjektServiceImplProjekti extends Kod {
	private Optional<String> status;
	@ToStringExclude
	private ACommonServis aCommonServis=new ACommonServis();
	
	@Autowired
	@ToStringExclude
	private ProjektRepository projektRepository;	
	
	public ProjektServiceImplProjekti init(Optional<String> status) {
		this.status=status;
		
		return this;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Map<String, Object> map) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		if(status.isPresent()) {				
			aCommonServis.findByStatus(pi, projektRepository, DbStatus.valueOf(status.get()), pageRequest);
		} else {
			aCommonServis.findAll_projekt(pi, projektRepository, pageRequest);
		}	
		
		return pi;
	}

}
