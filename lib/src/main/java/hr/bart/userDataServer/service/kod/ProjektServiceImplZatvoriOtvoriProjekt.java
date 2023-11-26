package hr.bart.userDataServer.service.kod;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.ZatvoriOtvori;

public class ProjektServiceImplZatvoriOtvoriProjekt extends Kod {
	private final ZatvoriOtvori zo;
	private final Long id;
	private final Long ts;
	private final Optional<String> status;
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);

	public ProjektServiceImplZatvoriOtvoriProjekt(
			KodRepository kodRepository,
			ZatvoriOtvori zo,
			Long id,
			Long ts,
			Optional<String> status
			) {
		super(kodRepository);
		this.zo=zo;
		this.id=id;
		this.ts=ts;
		this.status=status;
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
