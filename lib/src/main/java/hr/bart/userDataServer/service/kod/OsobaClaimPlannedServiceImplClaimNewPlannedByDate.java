package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
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
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class OsobaClaimPlannedServiceImplClaimNewPlannedByDate extends Kod {
	private LocalDate datum=(LocalDate) hm.get("datum");
	private HashMap<String, String> podatci=(HashMap<String, String>) hm.get("podatci");
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);
	
	public OsobaClaimPlannedServiceImplClaimNewPlannedByDate(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		greska="";
		
		validirajClaimPlannedByDate(idProjektDetalji, datum);
		
		if(greska.length()>0) {
			pi.setGreska(greska);
		} else {
			Set<Entry<String, String>> set = podatci.entrySet();
		    Iterator<Entry<String, String>> iterator = set.iterator();
		    List<OsobaClaimPlanned> osobaClaimPlannedList=new ArrayList<>();
		    
		    while(iterator.hasNext()) {
		    	Map.Entry<String, String> mentry = iterator.next();
		    	String v=mentry.getValue().trim();
		    	BigDecimal vBD=v.length()>0 ? new BigDecimal(v) : new BigDecimal(0);
		    	long kLong=Long.parseLong(mentry.getKey());
		    	
		    	Optional<List<Claim>> claimListOptional=kodRepository.getClaimRepository().findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, kLong);
		    	
		    	if(claimListOptional.isPresent()) {
		    		List<Claim> claimList=claimListOptional.get();
					
					if(claimList.size()!=JEDAN) {
						if(greska.length()>0) greska+=" <BR> ";
						greska+="Iz baze je tablica 'Claim' vratila različito od 1!";
					} else {
						OsobaClaimPlanned ocp=new OsobaClaimPlanned();
						ocp.setSati(vBD);
						ocp.setClaim(claimList.get(0));
						ocp.setSifarnikMjeseca(aCommonServis.getSifarnikMjeseca(datum));	
						greska+=aCommonServis.getGreska();
						
						ocp=kodRepository.getOsobaClaimPlannedRepository().save(ocp);
						osobaClaimPlannedList.add(ocp);
					}
			    } else {
					if(greska.length()>0) greska+=" <BR> ";
					greska+="Iz baze je tablica 'Claim' vratila null!";
				}
		    }
		    
		    if(greska.length()>0) {
				pi.setGreska(greska);
			} else {
				for(OsobaClaimPlanned osobaCPLokal: osobaClaimPlannedList) {
					aCommonServis.setTabliceClaim1(osobaCPLokal);
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
	
	private void validirajClaimPlannedByDate(Long idProjektDetalji, LocalDate datum) {
		if(idProjektDetalji==null) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje 'idProjektDetalji' nije upisano.";
		} else {		
			if(datum==null) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+="Polje 'Datum' nije upisano.";
			} else if(datum.getDayOfMonth()!=JEDAN) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+="Polje 'Datum' mora biti prvi u mjesecu.";
			} else {
				Optional<List<OsobaClaimPlanned>> ocpListOptional=kodRepository.getOsobaClaimPlannedRepository().findAllByDatum(idProjektDetalji, datum);
				
				if(ocpListOptional.isPresent()) {
					List<OsobaClaimPlanned> ocpList=ocpListOptional.get();
					
					if(!ocpList.isEmpty()) {
						if(greska.length()>0) greska+=" <BR> ";
						greska+=String.format("Datum iz polja 'Datum' već postoji, uredite njega direktno '%s'.", datum);
					}
				}
			}
		}
	}
	
}
