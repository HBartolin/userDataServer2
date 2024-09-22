package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjektServiceImplTraziProjekte extends Kod {
	private String trazi;
	@ToStringExclude
	@Autowired
	private ProjektRepository projektRepository;

//	public ProjektServiceImplTraziProjekte info(String trazi) {
//		this.trazi=trazi;
//		
//		return this;
//	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) throws Throwable {
	    trazi=(String) o[0];
	    
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		StringBuilder sb=new StringBuilder("%").append(trazi.toUpperCase()).append("%");
			
		Page<List<Projekt>> pageProjektList=projektRepository.likeClaimContract(sb.toString(), pageRequest);

		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
		
		return pi;
	}

}
