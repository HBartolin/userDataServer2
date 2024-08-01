package hr.bart.userDataServer.service.kod;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
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
	private ProjektRepository projektRepository;

	public ProjektServiceImplTraziProjekte info(String trazi) {
		this.trazi=trazi;
		
		return this;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		StringBuilder sb=new StringBuilder("%").append(trazi.toUpperCase()).append("%");
			
		Page<List<Projekt>> pageProjektList=projektRepository.likeClaimContract(sb.toString(), pageRequest);

		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
		
		return pi;
	}

	@Override
	public String getToString() {
		return new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}
