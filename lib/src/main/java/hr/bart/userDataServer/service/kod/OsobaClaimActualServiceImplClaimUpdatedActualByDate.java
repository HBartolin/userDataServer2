package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.OsobaValuta;
import hr.bart.userDataServer.db.SifarnikDatuma;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;

public class OsobaClaimActualServiceImplClaimUpdatedActualByDate extends Kod {
	private final Long idProjektDetalji;
	private final LocalDate datum;
	private final List<ClaimUpdatedActualPlanned> podatci;	
	private ACommonServis aCommonServis=new ACommonServis(getKodRepository());
	
	public OsobaClaimActualServiceImplClaimUpdatedActualByDate(KodRepository kodRepository, Long idProjektDetalji, LocalDate datum, List<ClaimUpdatedActualPlanned> podatci) {
		super(kodRepository);
		this.idProjektDetalji=idProjektDetalji;
		this.datum=datum;
		this.podatci=podatci;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {		
		List<OsobaClaimActual> ocaList=new ArrayList<>();  
		validirajClaimUpdateActualByDate(pi, datum);
		
		if(!pi.getGreska().isEmpty()) {
			
		} else {
			for(ClaimUpdatedActualPlanned cua: podatci) {		
				OsobaValuta osobaValuta=aCommonServis.getOsobaValuta(cua.getIdSifarnikOsoba(), null, datum);
				
				if(cua.getIdClaim()>0) {
					Optional<OsobaClaimActual> ocaOptional=getKodRepository().getOsobaClaimActualRepository().findById(cua.getIdClaim());
					
					if(!ocaOptional.isPresent()) {
						String msg=String.format("Iz baze nije ništa vraćeno 'OsobaClaimActual' s id-jem '%d'.", cua.getIdClaim());
						pi.setGreskaListString(msg);
					} else {
						OsobaClaimActual osobaClaimActual=ocaOptional.get();
						
						if(osobaClaimActual.getTs().equals(cua.getTs())) {
							osobaClaimActual.setSati(cua.getSati());
							osobaClaimActual.setCijena(cua.getSati().multiply(osobaValuta.getCijena()));
							osobaClaimActual.setTs(osobaClaimActual.getTs()+1);
							
							ocaList.add(osobaClaimActual);
						} else {
							String msg=String.format("Iz baze je vraćen zasstario podatak 'OsobaClaimActual' s id-jem '%d' i ts-om '%d' a očekuje ts '%d'.", cua.getIdClaim(), cua.getTs(), osobaClaimActual.getTs());
							pi.setGreskaListString(msg);
						}
					}
				} else {
					List<SifarnikDatuma> sdList=getKodRepository().getSifarnikDatumaRepository().findByDatumPetak(datum);
					Optional<List<Claim>> claimListOptional=getKodRepository().getClaimRepository().findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, cua.getIdSifarnikOsoba());
					
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
							String msg=String.format("Iz baze je vraćeno da nema Claim ili ih ima više od jednog (idProjektDetalji=%d, idSifarnikOsoba=%d).", idProjektDetalji, cua.getIdSifarnikOsoba());
							pi.setGreskaListString(msg);
						}
					} else {
						String msg=String.format("Iz baze je vraćeno da nema SifarnikDatuma ili ih ima više od jednog (%d).", datum);
						pi.setGreskaListString(msg);
					}
				}
			}
			
			if(!pi.getGreska().isEmpty()) {
				
			} else {					
				for(OsobaClaimActual osobaClaimActual: ocaList) {
					OsobaClaimActual oca=getKodRepository().getOsobaClaimActualRepository().save(osobaClaimActual);
					
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
	
	private void validirajClaimUpdateActualByDate(PojoInterface pi, LocalDate datum) {
		if(datum==null) {
			String msg="Polje 'Datum' nije upisano.";
			pi.setGreskaListString(msg);
		} else if(!DayOfWeek.FRIDAY.equals(datum.getDayOfWeek())) {
			String msg="Polje 'Datum' mora biti PETAK.";
			pi.setGreskaListString(msg);
		} 
	}
	
}
