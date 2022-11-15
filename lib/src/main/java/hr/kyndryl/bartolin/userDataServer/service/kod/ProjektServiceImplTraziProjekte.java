package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import hr.kyndryl.bartolin.userDataServer.db.Projekt;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public class ProjektServiceImplTraziProjekte extends Kod {
	private String trazi=(String) hm.get("trazi");

	public ProjektServiceImplTraziProjekte(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
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
