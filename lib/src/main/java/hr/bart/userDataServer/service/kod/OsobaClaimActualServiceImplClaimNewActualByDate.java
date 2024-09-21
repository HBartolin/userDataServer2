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

import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Optional;
import java.util.Set;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.SifarnikDatuma;
import hr.bart.userDataServer.db.SifarnikMjeseca;
import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikDatumaRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class OsobaClaimActualServiceImplClaimNewActualByDate extends Kod {
	private final Long idProjektDetalji;
	private final LocalDate datum;
	private final HashMap<String, String> podatci;
	@ToStringExclude
	private ACommonServis aCommonServis=new ACommonServis();
	@ToStringExclude
	private final ClaimRepository claimRepository;
	@ToStringExclude
	private final SifarnikDatumaRepository sifarnikDatumaRepository;
	@ToStringExclude
	private final OsobaClaimActualRepository osobaClaimActualRepository;
	@ToStringExclude
	private final OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	@ToStringExclude
	private final ClaimPodugovaracRepository claimPodugovaracRepository;
	@ToStringExclude
	private final ProjektDetaljiRepository projektDetaljiRepository;
	@ToStringExclude
	private final OsobaValutaRepository osobaValutaRepository;
	@ToStringExclude
	private final SifarnikMjesecaRepository sifarnikMjesecaRepository;
	
	public OsobaClaimActualServiceImplClaimNewActualByDate(
			ClaimRepository claimRepository,
			SifarnikDatumaRepository sifarnikDatumaRepository,
			OsobaClaimActualRepository osobaClaimActualRepository,
			OsobaClaimPlannedRepository osobaClaimPlannedRepository,
			ProjektDetaljiRepository projektDetaljiRepository, 
			ClaimPodugovaracRepository claimPodugovaracRepository,
			OsobaValutaRepository osobaValutaRepository,
			SifarnikMjesecaRepository sifarnikMjesecaRepository,
			Long idProjektDetalji, 
			LocalDate datum, 
			HashMap<String, String> podatci) {
		this.claimRepository=claimRepository;
		this.sifarnikDatumaRepository=sifarnikDatumaRepository;
		this.idProjektDetalji=idProjektDetalji;
		this.osobaClaimActualRepository=osobaClaimActualRepository;
		this.datum=datum;
		this.podatci=podatci;
		this.osobaClaimPlannedRepository = osobaClaimPlannedRepository;
		this.claimPodugovaracRepository = claimPodugovaracRepository;
		this.projektDetaljiRepository = projektDetaljiRepository;
		this.osobaValutaRepository = osobaValutaRepository;
		this.sifarnikMjesecaRepository = sifarnikMjesecaRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Map<String, Object> o) throws Throwable {		
		validirajClaimActualByDate(pi, idProjektDetalji, datum);
		
		if(!pi.getGreska().isEmpty()) {
			
		} else {
			Set<Entry<String, String>> set = podatci.entrySet();
		    Iterator<Entry<String, String>> iterator = set.iterator();
		    List<OsobaClaimActual> osobaClaimActualList=new ArrayList<>();
		      
		    while(iterator.hasNext()) {
		    	Map.Entry<String, String> mentry = iterator.next();
		    	String v=mentry.getValue().trim();
		    	BigDecimal vBD=v.length()>0 ? new BigDecimal(v) : new BigDecimal(0);
		    	long kLong=Long.parseLong(mentry.getKey());
		    	
		    	Optional<List<Claim>> claimListOptional=claimRepository.findAllByIdProjektDetalji_idSifarnikOsoba(idProjektDetalji, kLong);
				
				if(claimListOptional.isPresent()) {
					List<Claim> claimList=claimListOptional.get();
					
					if(claimList.size()!=1) {
						String msg="Iz baze je tablica 'Claim' vratila različito od 1!";
						pi.addGreskaList(msg);
					} else {							
						List<SifarnikDatuma> sifarnikDatumaList=sifarnikDatumaRepository.findByDatumPetak(datum);
						
						if(sifarnikDatumaList.size()>1) {
							String msg="Iz baze je tablica 'Claim' vratila različito od 1!";
							pi.addGreskaList(msg);
						} else {
							OsobaClaimActual oca=new OsobaClaimActual();
							
							Optional<Claim> claimO=claimRepository.findById(claimList.get(0).getId()/*oca.getClaimId()*/);
							
							oca.setSati(vBD);
							oca.setCijenaTecaj(BigDecimal.ZERO);
							oca.setClaim(claimList.get(0));
							oca.setSifarnikDatuma(getSifarnikDatuma(pi, sifarnikDatumaList, datum));			
							oca.setOsobaValuta(aCommonServis.getOsobaValuta(osobaValutaRepository, kLong, claimO.get().getSifarnikOsoba().getImePrezime(), datum));
							oca.setCijena(vBD.multiply(oca.getOsobaValuta().getCijena()));
															
							oca=osobaClaimActualRepository.save(oca);
							osobaClaimActualList.add(oca);
						}
					}
				} else {
					String msg="Iz baze je tablica 'Claim' vratila null!";
					pi.addGreskaList(msg);
				}
		    }
			
			if(!pi.getGreska().isEmpty()) {
				
			} else {
				for(OsobaClaimActual osobaCALokal: osobaClaimActualList) {
					aCommonServis.setTabliceClaim1(
							osobaClaimActualRepository,
							claimRepository,
							osobaCALokal);
				}
				
				aCommonServis.setTabliceActualClaim(
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

	private SifarnikDatuma getSifarnikDatuma(PojoInterface pi, List<SifarnikDatuma> sifarnikDatumaList, LocalDate datum) throws Exception {		
		SifarnikDatuma sd;
		
		if(sifarnikDatumaList.isEmpty()) {
			LocalDate ned=datum.plusDays(DVA);
			LocalDate mj=ned.withDayOfMonth(JEDAN);
			SifarnikMjeseca sifarnikMjeseca = aCommonServis.getSifarnikMjeseca(sifarnikMjesecaRepository, pi, mj);
					
			SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
			sifarnikDatuma.setDatumPetak(datum);
			sifarnikDatuma.setDatumNedjelja(ned);
			sifarnikDatuma.setMjesec(sifarnikMjeseca);
			
			sd=sifarnikDatumaRepository.save(sifarnikDatuma);
		} else if(sifarnikDatumaList.size()!=JEDAN) {
			throw new Exception(String.format("Iz tablice 'SifarnikDatuma' dohvat je vratio ništa ili više od jednog upita (%d)!", datum));
		} else {
			sd=sifarnikDatumaList.get(0);
		}
		
		return sd;
	}

	private void validirajClaimActualByDate(PojoInterface pi, Long idProjektDetalji, LocalDate datum) {		
		if(idProjektDetalji==null) {
			String msg="Polje 'idProjektDetalji' nije upisano.";
			pi.addGreskaList(msg);
		} else {		
			if(datum==null) {
				String msg="Polje 'Datum' nije upisano.";
				pi.addGreskaList(msg);
			} else if(!DayOfWeek.FRIDAY.equals(datum.getDayOfWeek())) {
				String msg="Polje 'Datum' mora biti PETAK.";
				pi.addGreskaList(msg);
			} else {
				Optional<List<OsobaClaimActual>> ocaListOptional=osobaClaimActualRepository.findAllByDatum(idProjektDetalji, datum);
				
				if(ocaListOptional.isPresent()) {
					List<OsobaClaimActual> ocaList=ocaListOptional.get();
					
					if(!ocaList.isEmpty()) {
						String msg=String.format("Datum iz polja 'Datum' već postoji, uredite njega direktno '%s'.", datum);
						pi.addGreskaList(msg);
					}
				}
			}
		}
	}

}
