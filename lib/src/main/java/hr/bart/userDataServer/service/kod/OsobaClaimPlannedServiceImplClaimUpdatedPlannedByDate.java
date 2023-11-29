package hr.bart.userDataServer.service.kod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.db.SifarnikMjeseca;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;

public class OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate extends Kod {
	private final LocalDate datum;
	private final List<ClaimUpdatedActualPlanned> podatci;
	private final Long idProjektDetalji;
	private ACommonServis aCommonServis=new ACommonServis(getKodRepository());

	public OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate(KodRepository kodRepository, LocalDate datum, List<ClaimUpdatedActualPlanned> podatci, Long idProjektDetalji) {
		super(kodRepository);
		this.datum=datum;
		this.podatci=podatci;
		this.idProjektDetalji=idProjektDetalji;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		List<OsobaClaimPlanned> ocpList=new ArrayList<>();  
		validirajClaimUpdatePlannedByDate(pi, datum);
		
		if(!pi.getGreska().isEmpty()) {
			
		} else {
			for(ClaimUpdatedActualPlanned cua: podatci) {					
				if(cua.getIdClaim()>0) {
					getOsobaClaimPlanned(pi, cua, ocpList);
				} else {
					getOsobaClaimPlanned(pi, cua, ocpList, datum, idProjektDetalji);
				}
			}
			
			if(!pi.getGreska().isEmpty()) {
				
			} else {					
				for(OsobaClaimPlanned osobaClaimPlanned: ocpList) {
					OsobaClaimPlanned oca=getKodRepository().getOsobaClaimPlannedRepository().save(osobaClaimPlanned);
					
					aCommonServis.setTabliceClaim1(oca);
				}
				
				aCommonServis.setTablicePlannedClaim(idProjektDetalji);
				
				Optional<List<Claim>> claimListOptional=aCommonServis.claimActualPlanned(idProjektDetalji);
				
				if(claimListOptional.isPresent()) {
					pi.setRezultat(claimListOptional.get());
				}
			}
		}
		
		return pi;
	}
	
	private void validirajClaimUpdatePlannedByDate(PojoInterface pi, LocalDate datum) {
		if(datum==null) {
			String msg="Polje 'Datum' nije upisano.";
			pi.setGreskaListString(msg);
		} else if(datum.getDayOfMonth()!=JEDAN) {
			String msg="Polje 'Datum' mora biti prvi u mjesecu.";
			pi.setGreskaListString(msg);
		} 
	}
	
	private void getOsobaClaimPlanned(PojoInterface pi, ClaimUpdatedActualPlanned cua, List<OsobaClaimPlanned> ocpList, LocalDate datum, Long idProjektDetalji) {
		List<SifarnikMjeseca> sdList=getKodRepository().getSifarnikMjesecaRepository().findByMjeseca(datum);
		Optional<List<Claim>> claimListOptional=getKodRepository().getClaimRepository().findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, cua.getIdSifarnikOsoba());
		
		if(sdList.size()==JEDAN) {
			if(claimListOptional.isPresent() && claimListOptional.get().size()==JEDAN) {
				OsobaClaimPlanned osobaClaimPlanned=new OsobaClaimPlanned();
				osobaClaimPlanned.setSati(cua.getSati());
				osobaClaimPlanned.setClaim(claimListOptional.get().get(NULA));
				osobaClaimPlanned.setSifarnikMjeseca(sdList.get(NULA));
				
				ocpList.add(osobaClaimPlanned);
			} else {
				String msg=String.format("Iz baze je vraćeno da nema Claim ili ih ima više od jednog (idProjektDetalji=%d, idSifarnikOsoba=%d).", idProjektDetalji, cua.getIdSifarnikOsoba());
				pi.setGreskaListString(msg);
			}
		} else {
			String msg=String.format("Iz baze je vraćeno da nema SifarnikDatuma ili ih ima više od jednog (%d).", datum);
			pi.setGreskaListString(msg);
		}
	}
	
	private void getOsobaClaimPlanned(PojoInterface pi, ClaimUpdatedActualPlanned cua, List<OsobaClaimPlanned> ocpList) {		
		Optional<OsobaClaimPlanned> ocpOptional=getKodRepository().getOsobaClaimPlannedRepository().findById(cua.getIdClaim());
		
		if(!ocpOptional.isPresent()) {
			String msg=String.format("Iz baze nije ništa vraćeno 'OsobaClaimActual' s id-jem '%d'.", cua.getIdClaim());
			pi.setGreskaListString(msg);
		} else {
			OsobaClaimPlanned osobaClaimPlanned=ocpOptional.get();
			
			if(osobaClaimPlanned.getTs().equals(cua.getTs())) {
				osobaClaimPlanned.setSati(cua.getSati());
				osobaClaimPlanned.setTs(osobaClaimPlanned.getTs()+JEDAN);
				
				ocpList.add(osobaClaimPlanned);
			} else {
				String msg=String.format("Iz baze je vraćen zasstario podatak 'OsobaClaimActual' s id-jem '%d' i ts-om '%d' a očekuje ts '%d'.", cua.getIdClaim(), cua.getTs(), osobaClaimPlanned.getTs());
				pi.setGreskaListString(msg);
			}
		}
	}

}
