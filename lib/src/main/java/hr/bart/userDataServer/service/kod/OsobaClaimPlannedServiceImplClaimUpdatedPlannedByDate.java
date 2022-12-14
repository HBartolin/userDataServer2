package hr.bart.userDataServer.service.kod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.db.SifarnikMjeseca;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate extends Kod {
	private LocalDate datum=(LocalDate) hm.get("datum");
	private List<ClaimUpdatedActualPlanned> podatci=(List<ClaimUpdatedActualPlanned>) hm.get("podatci");
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");
	private ACommonServis aCommonServis=new ACommonServis(hm, kodRepository);

	public OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		greska="";
		List<OsobaClaimPlanned> ocpList=new ArrayList<>();  
		validirajClaimUpdatePlannedByDate(datum);
		
		if(greska.length()>0) {
			pi.setGreska(greska);
		} else {
			for(ClaimUpdatedActualPlanned cua: podatci) {					
				if(cua.getIdClaim()>0) {
					getOsobaClaimPlanned(cua, ocpList);
				} else {
					getOsobaClaimPlanned(cua, ocpList, datum, idProjektDetalji);
				}
			}
			
			if(greska.length()>0) {
				pi.setGreska(greska);
			} else {					
				for(OsobaClaimPlanned osobaClaimPlanned: ocpList) {
					OsobaClaimPlanned oca=kodRepository.getOsobaClaimPlannedRepository().save(osobaClaimPlanned);
					
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
	
	private void validirajClaimUpdatePlannedByDate(LocalDate datum) {
		if(datum==null) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje 'Datum' nije upisano.";
		} else if(datum.getDayOfMonth()!=JEDAN) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje 'Datum' mora biti prvi u mjesecu.";
		} 
	}
	
	private void getOsobaClaimPlanned(ClaimUpdatedActualPlanned cua, List<OsobaClaimPlanned> ocpList, LocalDate datum, Long idProjektDetalji) {
		List<SifarnikMjeseca> sdList=kodRepository.getSifarnikMjesecaRepository().findByMjeseca(datum);
		Optional<List<Claim>> claimListOptional=kodRepository.getClaimRepository().findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, cua.getIdSifarnikOsoba());
		
		if(sdList.size()==JEDAN) {
			if(claimListOptional.isPresent() && claimListOptional.get().size()==JEDAN) {
				OsobaClaimPlanned osobaClaimPlanned=new OsobaClaimPlanned();
				osobaClaimPlanned.setSati(cua.getSati());
				osobaClaimPlanned.setClaim(claimListOptional.get().get(NULA));
				osobaClaimPlanned.setSifarnikMjeseca(sdList.get(NULA));
				
				ocpList.add(osobaClaimPlanned);
			} else {
				if(greska.length()>0) greska+=" <BR> ";
				greska+=String.format("Iz baze je vra??eno da nema Claim ili ih ima vi??e od jednog (idProjektDetalji=%d, idSifarnikOsoba=%d).", idProjektDetalji, cua.getIdSifarnikOsoba());
			}
		} else {
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Iz baze je vra??eno da nema SifarnikDatuma ili ih ima vi??e od jednog (%d).", datum);
		}
	}
	
	private void getOsobaClaimPlanned(ClaimUpdatedActualPlanned cua, List<OsobaClaimPlanned> ocpList) {		
		Optional<OsobaClaimPlanned> ocpOptional=kodRepository.getOsobaClaimPlannedRepository().findById(cua.getIdClaim());
		
		if(!ocpOptional.isPresent()) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Iz baze nije ni??ta vra??eno 'OsobaClaimActual' s id-jem '%d'.", cua.getIdClaim());
		} else {
			OsobaClaimPlanned osobaClaimPlanned=ocpOptional.get();
			
			if(osobaClaimPlanned.getTs().equals(cua.getTs())) {
				osobaClaimPlanned.setSati(cua.getSati());
				osobaClaimPlanned.setTs(osobaClaimPlanned.getTs()+JEDAN);
				
				ocpList.add(osobaClaimPlanned);
			} else {
				if(greska.length()>0) greska+=" <BR> ";
				greska+=String.format("Iz baze je vra??en zasstario podatak 'OsobaClaimActual' s id-jem '%d' i ts-om '%d' a o??ekuje ts '%d'.", cua.getIdClaim(), cua.getTs(), osobaClaimPlanned.getTs());
			}
		}
	}

}
