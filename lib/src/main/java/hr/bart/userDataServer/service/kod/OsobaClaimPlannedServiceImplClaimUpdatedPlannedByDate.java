package hr.bart.userDataServer.service.kod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.db.SifarnikMjeseca;
import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;

public class OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate extends Kod {
	private final LocalDate datum;
	private final List<ClaimUpdatedActualPlanned> podatci;
	private final Long idProjektDetalji;
	private ACommonServis aCommonServis=new ACommonServis();
	private final OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	private final SifarnikMjesecaRepository sifarnikMjesecaRepository;
	private final ClaimRepository claimRepository;
	private final OsobaClaimActualRepository osobaClaimActualRepository;
	private final ClaimPodugovaracRepository claimPodugovaracRepository;
	private final ProjektDetaljiRepository projektDetaljiRepository;

	public OsobaClaimPlannedServiceImplClaimUpdatedPlannedByDate(
			OsobaClaimPlannedRepository osobaClaimPlannedRepository,
			SifarnikMjesecaRepository sifarnikMjesecaRepository,
			ClaimRepository claimRepository,
			OsobaClaimActualRepository osobaClaimActualRepository,
			ProjektDetaljiRepository projektDetaljiRepository, 
			ClaimPodugovaracRepository claimPodugovaracRepository,
			LocalDate datum, 
			List<ClaimUpdatedActualPlanned> podatci, 
			Long idProjektDetalji) {
		this.osobaClaimPlannedRepository=osobaClaimPlannedRepository;
		this.sifarnikMjesecaRepository=sifarnikMjesecaRepository;
		this.claimRepository=claimRepository;
		this.datum=datum;
		this.podatci=podatci;
		this.idProjektDetalji=idProjektDetalji;
		this.osobaClaimActualRepository = osobaClaimActualRepository;
		this.claimPodugovaracRepository = claimPodugovaracRepository;
		this.projektDetaljiRepository = projektDetaljiRepository;
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
					OsobaClaimPlanned oca=osobaClaimPlannedRepository.save(osobaClaimPlanned);
					
					aCommonServis.setTabliceClaim1(osobaClaimPlannedRepository, claimRepository, oca);
				}
				
				aCommonServis.setTablicePlannedClaim(
						claimRepository,
						osobaClaimActualRepository,
						osobaClaimPlannedRepository,
						claimPodugovaracRepository,
						projektDetaljiRepository,
						idProjektDetalji);
				
				Optional<List<Claim>> claimListOptional=aCommonServis.claimActualPlanned(
						claimRepository,
						osobaClaimActualRepository,
						osobaClaimPlannedRepository,
						idProjektDetalji);
				
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
			pi.addGreskaList(msg);
		} else if(datum.getDayOfMonth()!=JEDAN) {
			String msg="Polje 'Datum' mora biti prvi u mjesecu.";
			pi.addGreskaList(msg);
		} 
	}
	
	private void getOsobaClaimPlanned(PojoInterface pi, ClaimUpdatedActualPlanned cua, List<OsobaClaimPlanned> ocpList, LocalDate datum, Long idProjektDetalji) {
		List<SifarnikMjeseca> sdList=sifarnikMjesecaRepository.findByMjeseca(datum);
		Optional<List<Claim>> claimListOptional=claimRepository.findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, cua.getIdSifarnikOsoba());
		
		if(sdList.size()==JEDAN) {
			if(claimListOptional.isPresent() && claimListOptional.get().size()==JEDAN) {
				OsobaClaimPlanned osobaClaimPlanned=new OsobaClaimPlanned();
				osobaClaimPlanned.setSati(cua.getSati());
				osobaClaimPlanned.setClaim(claimListOptional.get().get(NULA));
				osobaClaimPlanned.setSifarnikMjeseca(sdList.get(NULA));
				
				ocpList.add(osobaClaimPlanned);
			} else {
				String msg=String.format("Iz baze je vraćeno da nema Claim ili ih ima više od jednog (idProjektDetalji=%d, idSifarnikOsoba=%d).", idProjektDetalji, cua.getIdSifarnikOsoba());
				pi.addGreskaList(msg);
			}
		} else {
			String msg=String.format("Iz baze je vraćeno da nema SifarnikDatuma ili ih ima više od jednog (%d).", datum);
			pi.addGreskaList(msg);
		}
	}
	
	private void getOsobaClaimPlanned(PojoInterface pi, ClaimUpdatedActualPlanned cua, List<OsobaClaimPlanned> ocpList) {		
		Optional<OsobaClaimPlanned> ocpOptional=osobaClaimPlannedRepository.findById(cua.getIdClaim());
		
		if(!ocpOptional.isPresent()) {
			String msg=String.format("Iz baze nije ništa vraćeno 'OsobaClaimActual' s id-jem '%d'.", cua.getIdClaim());
			pi.addGreskaList(msg);
		} else {
			OsobaClaimPlanned osobaClaimPlanned=ocpOptional.get();
			
			if(osobaClaimPlanned.getTs().equals(cua.getTs())) {
				osobaClaimPlanned.setSati(cua.getSati());
				osobaClaimPlanned.setTs(osobaClaimPlanned.getTs()+JEDAN);
				
				ocpList.add(osobaClaimPlanned);
			} else {
				String msg=String.format("Iz baze je vraćen zasstario podatak 'OsobaClaimActual' s id-jem '%d' i ts-om '%d' a očekuje ts '%d'.", cua.getIdClaim(), cua.getTs(), osobaClaimPlanned.getTs());
				pi.addGreskaList(msg);
			}
		}
	}

}
