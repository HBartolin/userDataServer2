package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.SifarnikDatuma;
import hr.bart.userDataServer.db.SifarnikMjeseca;
import hr.bart.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class OsobaClaimActualServiceImplClaimNewActualByDate extends Kod {
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");
	private LocalDate datum=(LocalDate) hm.get("datum");
	private HashMap<String, String> podatci=(HashMap<String, String>) hm.get("podatci");
	private ACommonServis aCommonServis=new ACommonServis(hm, kodRepository);
	
	public OsobaClaimActualServiceImplClaimNewActualByDate(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		greska="";
		
		validirajClaimActualByDate(idProjektDetalji, datum);
		
		if(greska.length()>0) {
			pi.setGreska(greska);
		} else {
			Set<Entry<String, String>> set = podatci.entrySet();
		    Iterator<Entry<String, String>> iterator = set.iterator();
		    List<OsobaClaimActual> osobaClaimActualList=new ArrayList<>();
		      
		    while(iterator.hasNext()) {
		    	Map.Entry<String, String> mentry = iterator.next();
		    	String v=mentry.getValue().trim();
		    	BigDecimal vBD=v.length()>0 ? new BigDecimal(v) : new BigDecimal(0);
		    	long kLong=Long.parseLong(mentry.getKey());
		    	
		    	Optional<List<Claim>> claimListOptional=kodRepository.getClaimRepository().findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, kLong);
				
				if(claimListOptional.isPresent()) {
					List<Claim> claimList=claimListOptional.get();
					
					if(claimList.size()!=1) {
						if(greska.length()>0) greska+=" <BR> ";
						greska+="Iz baze je tablica 'Claim' vratila razli??ito od 1!";
					} else {							
						List<SifarnikDatuma> sifarnikDatumaList=kodRepository.getSifarnikDatumaRepository().findByDatumPetak(datum);
						
						if(sifarnikDatumaList.size()>1) {
							if(greska.length()>0) greska+=" <BR> ";
							greska+="Iz baze je tablica 'Claim' vratila razli??ito od 1!";
						} else {
							OsobaClaimActual oca=new OsobaClaimActual();
							
							Optional<Claim> claimO=kodRepository.getClaimRepository().findById(claimList.get(0).getId()/*oca.getClaimId()*/);
							
							oca.setSati(vBD);
							oca.setCijenaTecaj(BigDecimal.ZERO);
							oca.setClaim(claimList.get(0));
							oca.setSifarnikDatuma(getSifarnikDatuma(sifarnikDatumaList, datum));			
							oca.setOsobaValuta(aCommonServis.getOsobaValuta(kLong,claimO.get().getSifarnikOsoba().getImePrezime(), datum));
							oca.setCijena(vBD.multiply(oca.getOsobaValuta().getCijena()));
															
							oca=kodRepository.getOsobaClaimActualRepository().save(oca);
							osobaClaimActualList.add(oca);
						}
					}
				} else {
					if(greska.length()>0) greska+=" <BR> ";
					greska+="Iz baze je tablica 'Claim' vratila null!";
				}
		    }
			
			if(greska.length()>0) {
				pi.setGreska(greska);
			} else {
				for(OsobaClaimActual osobaCALokal: osobaClaimActualList) {
					aCommonServis.setTabliceClaim1(osobaCALokal);
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

	private SifarnikDatuma getSifarnikDatuma(List<SifarnikDatuma> sifarnikDatumaList, LocalDate datum) throws Exception {		
		SifarnikDatuma sd;
		
		if(sifarnikDatumaList.isEmpty()) {
			LocalDate ned=datum.plusDays(DVA);
			LocalDate mj=ned.withDayOfMonth(JEDAN);
			SifarnikMjeseca sifarnikMjeseca = aCommonServis.getSifarnikMjeseca(mj);
			greska+=aCommonServis.getGreska();
			
			SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
			sifarnikDatuma.setDatumPetak(datum);
			sifarnikDatuma.setDatumNedjelja(ned);
			sifarnikDatuma.setMjesec(sifarnikMjeseca);
			
			sd=kodRepository.getSifarnikDatumaRepository().save(sifarnikDatuma);
		} else if(sifarnikDatumaList.size()!=JEDAN) {
			throw new Exception(String.format("Iz tablice 'SifarnikDatuma' dohvat je vratio ni??ta ili vi??e od jednog upita (%d)!", datum));
		} else {
			sd=sifarnikDatumaList.get(0);
		}
		
		return sd;
	}

	private void validirajClaimActualByDate(Long idProjektDetalji, LocalDate datum) {		
		if(idProjektDetalji==null) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje 'idProjektDetalji' nije upisano.";
		} else {		
			if(datum==null) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+="Polje 'Datum' nije upisano.";
			} else if(!DayOfWeek.FRIDAY.equals(datum.getDayOfWeek())) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+="Polje 'Datum' mora biti PETAK.";
			} else {
				Optional<List<OsobaClaimActual>> ocaListOptional=kodRepository.getOsobaClaimActualRepository().findAllByDatum(idProjektDetalji, datum);
				
				if(ocaListOptional.isPresent()) {
					List<OsobaClaimActual> ocaList=ocaListOptional.get();
					
					if(!ocaList.isEmpty()) {
						if(greska.length()>0) greska+=" <BR> ";
						greska+=String.format("Datum iz polja 'Datum' ve?? postoji, uredite njega direktno '%s'.", datum);
					}
				}
			}
		}
	}
	
}
