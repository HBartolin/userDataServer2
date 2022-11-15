package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.OsobaValuta;
import hr.bart.userDataServer.db.SifarnikDatuma;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class OsobaClaimActualServiceImplClaimUpdatedActualByDate extends Kod {
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");
	private LocalDate datum=(LocalDate) hm.get("datum");
	private List<ClaimUpdatedActualPlanned> podatci=(List<ClaimUpdatedActualPlanned>) hm.get("podatci");	
	private String greska;
	private ACommonServis aCommonServis=new ACommonServis(hm, kodRepository);
	
	public OsobaClaimActualServiceImplClaimUpdatedActualByDate(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {		
		greska="";
		List<OsobaClaimActual> ocaList=new ArrayList<>();  
		validirajClaimUpdateActualByDate(datum);
		
		if(greska.length()>0) {
			pi.setGreska(greska);
		} else {
			for(ClaimUpdatedActualPlanned cua: podatci) {		
				OsobaValuta osobaValuta=aCommonServis.getOsobaValuta(cua.getIdSifarnikOsoba(), null, datum);
				
				if(cua.getIdClaim()>0) {
					Optional<OsobaClaimActual> ocaOptional=kodRepository.getOsobaClaimActualRepository().findById(cua.getIdClaim());
					
					if(!ocaOptional.isPresent()) {
						if(greska.length()>0) greska+=" <BR> ";
						greska+=String.format("Iz baze nije ništa vraćeno 'OsobaClaimActual' s id-jem '%d'.", cua.getIdClaim());
					} else {
						OsobaClaimActual osobaClaimActual=ocaOptional.get();
						
						if(osobaClaimActual.getTs().equals(cua.getTs())) {
							osobaClaimActual.setSati(cua.getSati());
							osobaClaimActual.setCijena(cua.getSati().multiply(osobaValuta.getCijena()));
							osobaClaimActual.setTs(osobaClaimActual.getTs()+1);
							
							ocaList.add(osobaClaimActual);
						} else {
							if(greska.length()>0) greska+=" <BR> ";
							greska+=String.format("Iz baze je vraćen zasstario podatak 'OsobaClaimActual' s id-jem '%d' i ts-om '%d' a očekuje ts '%d'.", cua.getIdClaim(), cua.getTs(), osobaClaimActual.getTs());
						}
					}
				} else {
					List<SifarnikDatuma> sdList=kodRepository.getSifarnikDatumaRepository().findByDatumPetak(datum);
					Optional<List<Claim>> claimListOptional=kodRepository.getClaimRepository().findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, cua.getIdSifarnikOsoba());
					
					if(sdList.size()==JEDAN) {
						if(claimListOptional.isPresent() && claimListOptional.get().size()==JEDAN) {
							OsobaClaimActual osobaClaimActual=new OsobaClaimActual();
							osobaClaimActual.setSati(cua.getSati());
							osobaClaimActual.setCijenaTecaj(BigDecimal.ZERO);
							osobaClaimActual.setClaim(claimListOptional.get().get(NULA));
							osobaClaimActual.setSifarnikDatuma(sdList.get(NULA));
							osobaClaimActual.setOsobaValuta(osobaValuta);
							osobaClaimActual.setCijena(cua.getSati().multiply(osobaValuta.getCijena()));
							
							ocaList.add(osobaClaimActual);
						} else {
							if(greska.length()>0) greska+=" <BR> ";
							greska+=String.format("Iz baze je vraćeno da nema Claim ili ih ima više od jednog (idProjektDetalji=%d, idSifarnikOsoba=%d).", idProjektDetalji, cua.getIdSifarnikOsoba());
						}
					} else {
						if(greska.length()>0) greska+=" <BR> ";
						greska+=String.format("Iz baze je vraćeno da nema SifarnikDatuma ili ih ima više od jednog (%d).", datum);
					}
				}
			}
			
			if(greska.length()>0) {
				pi.setGreska(greska);
			} else {					
				for(OsobaClaimActual osobaClaimActual: ocaList) {
					OsobaClaimActual oca=kodRepository.getOsobaClaimActualRepository().save(osobaClaimActual);
					
					aCommonServis.setTabliceClaim1(oca);
				}
				
				aCommonServis.setTabliceActualClaim(idProjektDetalji);
				
				Optional<List<Claim>> claimListOptional=aCommonServis.claimActualPlanned(idProjektDetalji);
				
				if(claimListOptional.isPresent()) {
					pi.setRezultat(claimListOptional.get());
				}
			}
		}
		
		return pi;
	}
	
	private void validirajClaimUpdateActualByDate(LocalDate datum) {
		if(datum==null) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje 'Datum' nije upisano.";
		} else if(!DayOfWeek.FRIDAY.equals(datum.getDayOfWeek())) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje 'Datum' mora biti PETAK.";
		} 
	}
	
}
