package hr.bart.userDataServer.service.kod;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjektServiceImplNoviProjekt extends Kod {
	private String claim;
	private String contract;
	private ACommonServis aCommonServis=new ACommonServis();
	
	@Autowired
	private ProjektRepository projektRepository;

	public ProjektServiceImplNoviProjekt info(String claim, String contract) {
		this.claim=claim;
		this.contract=contract;
		
		return this;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {		
		if(claim==null || "".equals(claim.strip())) {
			String msg="Polje Claim nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(contract==null || "".equals(contract.replaceAll("\\s",""))) {
			String msg="Polje Contract nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(!pi.getGreska().isEmpty()) {
			
		} else {
			Projekt projekt=new Projekt();
			projekt.setClaim(aCommonServis.skratiAkoTreba255(claim));
			projekt.setContract(aCommonServis.skratiAkoTreba255(contract));
			projekt.setStatus(DbStatus.A);
			
			projektRepository.save(projekt);
			
			pi.setOk("Projekt je dodan.");
		}		
		
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		Page<List<Projekt>> pageProjektList=projektRepository.findByStatus(DbStatus.A, pageRequest);
		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
		
		return pi;
	}

	@Override
	public String getToString() {
		return new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}
