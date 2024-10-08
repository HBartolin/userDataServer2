package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Optional;
import java.util.Set;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class OsobaClaimPlannedServiceImplClaimNewPlannedByDate extends Kod {
	private final LocalDate datum;
	private final HashMap<String, String> podatci;
	private final Long idProjektDetalji;
	@ToStringExclude
	private ACommonServis aCommonServis=new ACommonServis();
	@ToStringExclude
	private final ClaimRepository claimRepository;
	@ToStringExclude
	private final OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	@ToStringExclude
	private final OsobaClaimActualRepository osobaClaimActualRepository;
	@ToStringExclude
	private final SifarnikMjesecaRepository sifarnikMjesecaRepository;
	@ToStringExclude
	private final ClaimPodugovaracRepository claimPodugovaracRepository;
	@ToStringExclude
	private final ProjektDetaljiRepository projektDetaljiRepository;
	
	public OsobaClaimPlannedServiceImplClaimNewPlannedByDate(
			ClaimRepository claimRepository,
			OsobaClaimPlannedRepository osobaClaimPlannedRepository,
			OsobaClaimActualRepository osobaClaimActualRepository,
			SifarnikMjesecaRepository sifarnikMjesecaRepository,
			ProjektDetaljiRepository projektDetaljiRepository, 
			ClaimPodugovaracRepository claimPodugovaracRepository,
			LocalDate datum, 
			HashMap<String, String> podatci, 
			Long idProjektDetalji) {
		this.claimRepository=claimRepository;
		this.osobaClaimPlannedRepository=osobaClaimPlannedRepository;
		this.datum=datum;
		this.podatci=podatci;
		this.idProjektDetalji=idProjektDetalji;
		this.osobaClaimActualRepository = osobaClaimActualRepository;
		this.sifarnikMjesecaRepository = sifarnikMjesecaRepository;
		this.claimPodugovaracRepository = claimPodugovaracRepository;
		this.projektDetaljiRepository = projektDetaljiRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) throws Throwable {		
		validirajClaimPlannedByDate(pi, idProjektDetalji, datum);
		
		if(!pi.getGreska().isEmpty()) {
			
		} else {
			Set<Entry<String, String>> set = podatci.entrySet();
		    Iterator<Entry<String, String>> iterator = set.iterator();
		    List<OsobaClaimPlanned> osobaClaimPlannedList=new ArrayList<>();
		    
		    while(iterator.hasNext()) {
		    	Map.Entry<String, String> mentry = iterator.next();
		    	String v=mentry.getValue().trim();
		    	BigDecimal vBD=v.length()>0 ? new BigDecimal(v) : new BigDecimal(0);
		    	long kLong=Long.parseLong(mentry.getKey());
		    	
		    	Optional<List<Claim>> claimListOptional=claimRepository.findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, kLong);
		    	
		    	if(claimListOptional.isPresent()) {
		    		List<Claim> claimList=claimListOptional.get();
					
					if(claimList.size()!=JEDAN) {
						String msg="Iz baze je tablica 'Claim' vratila različito od 1!";
						pi.addGreskaList(msg);
					} else {
						OsobaClaimPlanned ocp=new OsobaClaimPlanned();
						ocp.setSati(vBD);
						ocp.setClaim(claimList.get(0));
						ocp.setSifarnikMjeseca(aCommonServis.getSifarnikMjeseca(sifarnikMjesecaRepository, pi, datum));
						
						ocp=osobaClaimPlannedRepository.save(ocp);
						osobaClaimPlannedList.add(ocp);
					}
			    } else {
					String msg="Iz baze je tablica 'Claim' vratila null!";
					pi.addGreskaList(msg);
				}
		    }
		    
		    if(!pi.getGreska().isEmpty()) {
				
			} else {
				for(OsobaClaimPlanned osobaCPLokal: osobaClaimPlannedList) {
					aCommonServis.setTabliceClaim1(osobaClaimPlannedRepository, claimRepository, osobaCPLokal);
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
	
	private void validirajClaimPlannedByDate(PojoInterface pi, Long idProjektDetalji, LocalDate datum) {
		if(idProjektDetalji==null) {
			String msg="Polje 'idProjektDetalji' nije upisano.";
			pi.addGreskaList(msg);
		} else {		
			if(datum==null) {
				String msg="Polje 'Datum' nije upisano.";
				pi.addGreskaList(msg);
			} else if(datum.getDayOfMonth()!=JEDAN) {
				String msg="Polje 'Datum' mora biti prvi u mjesecu.";
				pi.addGreskaList(msg);
			} else {
				Optional<List<OsobaClaimPlanned>> ocpListOptional=osobaClaimPlannedRepository.findAllByDatum(idProjektDetalji, datum);
				
				if(ocpListOptional.isPresent()) {
					List<OsobaClaimPlanned> ocpList=ocpListOptional.get();
					
					if(!ocpList.isEmpty()) {
						String msg=String.format("Datum iz polja 'Datum' već postoji, uredite njega direktno '%s'.", datum);
						pi.addGreskaList(msg);
					}
				}
			}
		}
	}

}
