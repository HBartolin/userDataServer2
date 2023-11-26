package hr.bart.userDataServer.service.kod;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektServiceImplTraziProjekte extends Kod {
	private final String trazi;

	public ProjektServiceImplTraziProjekte(KodRepository kodRepository, String trazi) {
		super(kodRepository);
		this.trazi=trazi;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		StringBuilder sb=new StringBuilder("%").append(trazi.toUpperCase()).append("%");
			
		Page<List<Projekt>> pageProjektList=kodRepository.getProjektRepository().likeClaimContract(sb.toString(), pageRequest);

		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
		
		return pi;
	}

}
