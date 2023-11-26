package hr.bart.userDataServer.service.kod;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.ZatvoriOtvori;

@SuppressWarnings("unchecked")
public class ProjektServiceImplZatvoriOtvoriProjekt extends Kod {
	private ZatvoriOtvori zo=(ZatvoriOtvori) hm.get("zo");
	private Long id=(Long) hm.get("id");
	private Long ts=(Long) hm.get("ts");
	private Optional<String> status=(Optional<String>) hm.get("status");
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);

	public ProjektServiceImplZatvoriOtvoriProjekt(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		Optional<Projekt> projektOptional=kodRepository.getProjektRepository().findById(id);
		
		if(zo.getDbStatusSuprotni().equals(projektOptional.get().getStatus())) {
			pi.setGreska(String.format(zo.getMsg1(), projektOptional.get().getId()));
		} else {
			if(ts!=projektOptional.get().getTs()) {
				pi.setGreska(zo.getMsg2());
			} else {
				projektOptional.get().setStatus(zo.getDbStatusSuprotni());
				kodRepository.getProjektRepository().save(projektOptional.get());
				
				pi.setOk(String.format(zo.getMsg3(), projektOptional.get().getId()));
			}
		}
		
		setProjektStatus(pi, status);
		
		return pi;
	}
	
	private void setProjektStatus(PojoInterface pi, Optional<String> status) {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		if(status.isPresent()) {
			aCommonServis.findByStatus(pi, DbStatus.valueOf(status.get()), pageRequest);
		} else {
			aCommonServis.findAll_projekt(pi, pageRequest);
		}
	}

}
