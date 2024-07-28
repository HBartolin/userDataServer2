package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.ZatvoriOtvori;

public class ProjektServiceImplZatvoriOtvoriProjekt extends Kod {
	private ZatvoriOtvori zo;
	private Long id;
	private Long ts;
	private Optional<String> status;
	private ACommonServis aCommonServis=new ACommonServis();
	
	@Autowired
	private ProjektRepository projektRepository;	

	public ProjektServiceImplZatvoriOtvoriProjekt init(ZatvoriOtvori zo, Long id, Long ts, Optional<String> status) {
		this.zo=zo;
		this.id=id;
		this.ts=ts;
		this.status=status;
		
		return this;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		Optional<Projekt> projektOptional=projektRepository.findById(id);
		
		if(zo.getDbStatusSuprotni().equals(projektOptional.get().getStatus())) {
			String msg=String.format(zo.getMsg1(), projektOptional.get().getId());
			pi.addGreskaList(msg);
		} else {
			if(ts!=projektOptional.get().getTs()) {
				String msg=zo.getMsg2();
				pi.addGreskaList(msg);
			} else {
				projektOptional.get().setStatus(zo.getDbStatusSuprotni());
				projektRepository.save(projektOptional.get());
				
				pi.setOk(String.format(zo.getMsg3(), projektOptional.get().getId()));
			}
		}
		
		setProjektStatus(pi, status);
		
		return pi;
	}
	
	private void setProjektStatus(PojoInterface pi, Optional<String> status) {
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		if(status.isPresent()) {
			aCommonServis.findByStatus(pi, projektRepository, DbStatus.valueOf(status.get()), pageRequest);
		} else {
			aCommonServis.findAll_projekt(pi, projektRepository, pageRequest);
		}
	}

}
