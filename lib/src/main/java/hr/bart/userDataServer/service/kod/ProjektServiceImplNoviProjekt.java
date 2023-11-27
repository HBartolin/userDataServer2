package hr.bart.userDataServer.service.kod;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektServiceImplNoviProjekt extends Kod {
	private final String claim;
	private final String contract;
	private ACommonServis aCommonServis=new ACommonServis(getKodRepository());

	public ProjektServiceImplNoviProjekt(KodRepository kodRepository, String claim, String contract) {
		super(kodRepository);
		this.claim=claim;
		this.contract=contract;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		String greska="";
		
		if(claim==null || "".equals(claim.replaceAll("\\s",""))) {
			greska="Polje Claim nije upisano.";
		}
		
		if(contract==null || "".equals(contract.replaceAll("\\s",""))) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje Contract nije upisano.";
		}
		
		if(greska.length()>0) {
			pi.setGreska(greska);
		} else {
			Projekt projekt=new Projekt();
			projekt.setClaim(aCommonServis.skratiAkoTreba255(claim));
			projekt.setContract(aCommonServis.skratiAkoTreba255(contract));
			projekt.setStatus(DbStatus.A);
			
			getKodRepository().getProjektRepository().save(projekt);
			
			pi.setOk("Projekt je dodan.");
		}		
		
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		Page<List<Projekt>> pageProjektList=getKodRepository().getProjektRepository().findByStatus(DbStatus.A, pageRequest);
		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
		
		return pi;
	}

}
