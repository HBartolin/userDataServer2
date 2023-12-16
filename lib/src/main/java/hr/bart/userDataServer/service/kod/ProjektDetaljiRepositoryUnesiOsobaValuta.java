package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.OsobaValuta;
import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.db.SifarnikDatuma;
import hr.bart.userDataServer.db.SifarnikMjeseca;
import hr.bart.userDataServer.db.SifarnikOsoba;
import hr.bart.userDataServer.db.SifarnikValuta;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikDatumaRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.repository.SifarnikValutaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiRepositoryUnesiOsobaValuta extends Kod {
	private final Optional<Long> id;
	private final Long ts;
	private final Long idSifarnikOsoba;
	private final String band;
	private final BigDecimal cijena;
	private final LocalDate sifarnikDatumaOdLD;
	private final LocalDate sifarnikDatumaDoLD;
	private String sifarnikDatumaOdValue="Datum od";
	private String sifarnikDatumaDoValue="Datum do";
	private ACommonServis aCommonServis=new ACommonServis(getKodRepository());
	private final SifarnikDatumaRepository sifarnikDatumaRepository;
	private final OsobaValutaRepository osobaValutaRepository;
	private final SifarnikOsobaRepository sifarnikOsobaRepository;
	private final SifarnikValutaRepository sifarnikValutaRepository;
	private final OsobaClaimActualRepository osobaClaimActualRepository;
	private final ClaimRepository claimRepository;
	private final ProjektDetaljiRepository projektDetaljiRepository;
	private final SifarnikMjesecaRepository sifarnikMjesecaRepository;
	private final OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	
	public ProjektDetaljiRepositoryUnesiOsobaValuta(
			KodRepository kodRepository,
			SifarnikDatumaRepository sifarnikDatumaRepository,
			OsobaValutaRepository osobaValutaRepository,
			SifarnikOsobaRepository sifarnikOsobaRepository,
			SifarnikValutaRepository sifarnikValutaRepository,
			OsobaClaimActualRepository osobaClaimActualRepository,
			ClaimRepository claimRepository,
			ProjektDetaljiRepository projektDetaljiRepository,
			SifarnikMjesecaRepository sifarnikMjesecaRepository,
			OsobaClaimPlannedRepository osobaClaimPlannedRepository,
			Optional<Long> id,
			Long ts,
			Long idSifarnikOsoba,
			String band,
			BigDecimal cijena,
			LocalDate sifarnikDatumaOdLD,
			LocalDate sifarnikDatumaDoLD
			) {
		super(kodRepository);
		this.sifarnikDatumaRepository=sifarnikDatumaRepository;
		this.osobaValutaRepository=osobaValutaRepository;
		this.sifarnikOsobaRepository=sifarnikOsobaRepository;
		this.sifarnikValutaRepository=sifarnikValutaRepository;
		this.osobaClaimActualRepository=osobaClaimActualRepository;
		this.claimRepository=claimRepository;
		this.projektDetaljiRepository=projektDetaljiRepository;
		this.sifarnikMjesecaRepository=sifarnikMjesecaRepository;
		this.id=id;
		this.ts=ts;
		this.idSifarnikOsoba=idSifarnikOsoba;
		this.band=band;
		this.cijena=cijena;
		this.sifarnikDatumaDoLD=sifarnikDatumaDoLD;
		this.sifarnikDatumaOdLD=sifarnikDatumaOdLD;
		this.osobaClaimPlannedRepository = osobaClaimPlannedRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		SifarnikDatuma sifarnikDatumaDo=null;
		SifarnikDatuma sifarnikDatumaOd=null;
		
		if("".equals(band)) {
			String msg="Polje Band nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(cijena==null) {
			String msg="Polje Cijena nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(sifarnikDatumaOdLD==null) {
			String msg=String.format("Polje '%s' nije upisano.", sifarnikDatumaOdValue);
			pi.addGreskaList(msg);
		} else {
			sifarnikDatumaOd=getSifarnikDatumaOd(pi, sifarnikDatumaOdLD);
		}
					
		if(sifarnikDatumaDoLD==null) {
			sifarnikDatumaDo=getSifarnikDatumaDo(pi);
		} else {
			sifarnikDatumaDo=getSifarnikDatumaDoExist(pi, sifarnikDatumaDoLD);
		}
		
		if(pi.getGreska().isEmpty() && sifarnikDatumaOd!=null && sifarnikDatumaDo!=null) {
			if(sifarnikDatumaOd.getDatumPetak().isEqual(sifarnikDatumaDo.getDatumPetak()) || sifarnikDatumaOd.getDatumPetak().isAfter(sifarnikDatumaDo.getDatumPetak())) {
				String msg=String.format("'%s' je nakon ili jednak '%s'.", sifarnikDatumaOdValue, sifarnikDatumaDoValue);
				pi.addGreskaList(msg);
			} else {
				napraviSifarnikeDatuma(pi, id, ts, band, cijena, idSifarnikOsoba, sifarnikDatumaOd, sifarnikDatumaDo);
			} 
		}
			
		if(!pi.getGreska().isEmpty()) {
			
		} 
		
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		aCommonServis.findAllBySifarnikOsobaId(pi, idSifarnikOsoba, pageRequest);
		
		return pi;
	}
	
	private SifarnikDatuma getSifarnikDatumaOd(PojoInterface pi, LocalDate sifarnikDatumaOdLD) {
		DayOfWeek dayOfWeek=sifarnikDatumaOdLD.getDayOfWeek();
		SifarnikDatuma sifarnikDatumaOd=null;
		
		if(!DayOfWeek.FRIDAY.equals(dayOfWeek)) {
			String msg=String.format("Polje '%s' mota biti PETAK.", sifarnikDatumaOdValue);
			pi.addGreskaList(msg);
		} else {
			LocalDate ned=sifarnikDatumaOdLD.plusDays(DVA);
			LocalDate mj=ned.withDayOfMonth(JEDAN);
			SifarnikMjeseca sifarnikMjeseca=getSifarnikMjeseca(pi, sifarnikDatumaOdValue, mj);
			
			if(sifarnikMjeseca!=null) {			
				List<SifarnikDatuma> sifarnikDatumaList=sifarnikDatumaRepository.findByDatumPetak(sifarnikDatumaOdLD);
				
				if(sifarnikDatumaList.isEmpty()) {
					SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
					sifarnikDatuma.setDatumPetak(sifarnikDatumaOdLD);
					sifarnikDatuma.setDatumNedjelja(ned);
					sifarnikDatuma.setMjesec(sifarnikMjeseca);
					
					sifarnikDatumaOd=sifarnikDatumaRepository.save(sifarnikDatuma);
				} else if(sifarnikDatumaList.size()>1) {
					String msg=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaDoValue, sifarnikDatumaOdLD);
					pi.addGreskaList(msg);
				} else {
					sifarnikDatumaOd=sifarnikDatumaList.get(0);
				}		
			}
		}
		
		return sifarnikDatumaOd;
	}
	
	private SifarnikDatuma getSifarnikDatumaDoExist(PojoInterface pi, LocalDate sifarnikDatumaDoLD) {
		SifarnikDatuma sifarnikDatumaDo=null;
		DayOfWeek dayOfWeek=sifarnikDatumaDoLD.getDayOfWeek();
		
		if(!DayOfWeek.FRIDAY.equals(dayOfWeek)) {
			String msg=String.format("Polje '%s' mota biti PETAK.", sifarnikDatumaDoValue);
			pi.addGreskaList(msg);
		} else {
			LocalDate ned=sifarnikDatumaDoLD.plusDays(DVA);
			LocalDate mj=ned.withDayOfMonth(JEDAN);
			SifarnikMjeseca sifarnikMjeseca=getSifarnikMjeseca(pi, sifarnikDatumaDoValue, mj);

			if(sifarnikMjeseca!=null) {
				List<SifarnikDatuma> sifarnikDatumaList=sifarnikDatumaRepository.findByDatumPetak(sifarnikDatumaDoLD);
				
				if(sifarnikDatumaList.isEmpty()) {
					SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
					sifarnikDatuma.setDatumPetak(sifarnikDatumaDoLD);
					sifarnikDatuma.setDatumNedjelja(ned);
					sifarnikDatuma.setMjesec(sifarnikMjeseca);
					
					sifarnikDatumaDo=sifarnikDatumaRepository.save(sifarnikDatuma);
				} else if(sifarnikDatumaList.size()>1) {
					String msg=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaDoValue, sifarnikDatumaDoLD);
					pi.addGreskaList(msg);
				} else {
					sifarnikDatumaDo=sifarnikDatumaList.get(0);
				}					
			}
		}
		
		return sifarnikDatumaDo;
	}

	private SifarnikDatuma getSifarnikDatumaDo(PojoInterface pi) {
		SifarnikDatuma sifarnikDatumaDo=null;
		List<SifarnikDatuma> sifarnikDatumaList=sifarnikDatumaRepository.findByDatumPetak(datumPatakZadnji);
		
		if(sifarnikDatumaList.isEmpty()) {
			SifarnikMjeseca sifarnikMjeseca=getSifarnikMjeseca(pi, sifarnikDatumaDoValue, mjesecZadnji);
			
			if(sifarnikMjeseca!=null) {
				SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
				sifarnikDatuma.setDatumPetak(datumPatakZadnji);
				sifarnikDatuma.setDatumNedjelja(datumPatakZadnji.plusDays(DVA));
				sifarnikDatuma.setMjesec(sifarnikMjeseca);
				
				sifarnikDatumaDo=sifarnikDatumaRepository.save(sifarnikDatuma);
			}
		} else if(sifarnikDatumaList.size()>1){
			String msg=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaDoValue, datumPatakZadnji);
			pi.addGreskaList(msg);
		} else {
			sifarnikDatumaDo=sifarnikDatumaList.get(0);
		}
		
		return sifarnikDatumaDo;
	}
	
	private void napraviSifarnikeDatuma(PojoInterface pi, Optional<Long> id, Long ts, String band, BigDecimal cijena, Long idSifarnikOsoba, SifarnikDatuma sifarnikDatumaOd, SifarnikDatuma sifarnikDatumaDo) {
		final List<OsobaValuta> osobaValutaOdList;
		final List<OsobaValuta> osobaValutaDoList;
		
		if(id.isPresent()) {
			osobaValutaOdList=osobaValutaRepository.findAllByIdSifarnikDatumaOd(id.get(), idSifarnikOsoba, sifarnikDatumaOd.getDatumPetak());
			osobaValutaDoList=osobaValutaRepository.findAllByIdSifarnikDatumaDo(id.get(), idSifarnikOsoba, sifarnikDatumaDo.getDatumPetak());
		} else {
			osobaValutaOdList=osobaValutaRepository.findAllBySifarnikDatumaOd(idSifarnikOsoba, sifarnikDatumaOd.getDatumPetak());
			osobaValutaDoList=osobaValutaRepository.findAllBySifarnikDatumaDo(idSifarnikOsoba, sifarnikDatumaDo.getDatumPetak());
		}
		
		List<OsobaValuta> ovList=getOvList(id, idSifarnikOsoba, sifarnikDatumaOd, sifarnikDatumaDo);	
		
		if(ovList.isEmpty()==false) {
			String g="";
			
			for(OsobaValuta ov: ovList) {
				if(g.length()>0) g+=", ";
				g+=ov.getId();
			}
			
			String msg=String.format("Prevelik raspon datuma, prekriva polje: %s.", g);
			pi.addGreskaList(msg);
		} else if(osobaValutaOdList.size()>1 || osobaValutaDoList.size()>1) {
			String msg="Nešto se čudno desilo, ajde u kod.";
			pi.addGreskaList(msg);
		} else {
			setOsobaValutaOdList(pi, osobaValutaOdList, sifarnikDatumaOd);
			setOsobaValutaDoList(pi, osobaValutaDoList, sifarnikDatumaDo);
			Optional<SifarnikOsoba> sifarnikOsoba=sifarnikOsobaRepository.findById(idSifarnikOsoba);
			Optional<SifarnikValuta> sifarnikValuta=sifarnikValutaRepository.findAllByNaziv(HRK);
			
			OsobaValuta osobaValuta=new OsobaValuta();			
			osobaValuta.setBand(band);
			osobaValuta.setCijena(cijena);
			osobaValuta.setSifarnikDatumaOd(sifarnikDatumaOd);
			osobaValuta.setSifarnikDatumaDo(sifarnikDatumaDo);
			osobaValuta.setSifarnikOsoba(sifarnikOsoba.get());
			osobaValuta.setSifarnikValuta(sifarnikValuta.get());
			osobaValuta.setTs(ts);
			if(id.isPresent()) osobaValuta.setId(id.get());
			
			osobaValuta=osobaValutaRepository.save(osobaValuta);
			
			LocalDate datumOd=sifarnikDatumaOd.getDatumPetak();
			LocalDate datumDo=sifarnikDatumaDo.getDatumPetak();			
			Optional<List<OsobaClaimActual>> ocaListO=osobaClaimActualRepository.findAllBySifarnikOsoba_datumi(sifarnikOsoba.get(), datumOd, datumDo);
			HashSet<Long> idProjektDetaljiList=new HashSet<>();
			
			if(ocaListO.isPresent()) {
				List<OsobaClaimActual> ocaList=ocaListO.get();
				HashSet<Long> idClaimList=new HashSet<>();
				BigDecimal koliko=new BigDecimal(0);
				koliko.setScale(2, RoundingMode.HALF_EVEN);
				
				for(OsobaClaimActual osobaClaimActual: ocaList) {
					osobaClaimActual.setOsobaValuta(osobaValuta);
					osobaClaimActual.setCijena(osobaClaimActual.getSati().multiply(osobaValuta.getCijena()));
					
					osobaClaimActualRepository.save(osobaClaimActual);
					
					Optional<Claim> claimO=claimRepository.findById(osobaClaimActual.getClaim().getId());
					
					idProjektDetaljiList.add(claimO.get().getProjektDetalji().getId());
					idClaimList.add(osobaClaimActual.getClaim().getId());
				}	
				
				for(Long idC: idClaimList) {
					Optional<List<OsobaClaimActual>> findAllByIdClaim=osobaClaimActualRepository.findAllByIdClaim(idC);
					
					for(OsobaClaimActual osobaCA: findAllByIdClaim.get()) {
						koliko=koliko.add(osobaCA.getCijena());
					}
					
					Optional<Claim> claimO=claimRepository.findById(idC);
			
					claimO.get().setOsobaClaimActual(koliko);
					claimRepository.save(claimO.get());
				}				
			}	
			
			for(Long idPD: idProjektDetaljiList) {		
				BigDecimal osobaClaimActualKn=new BigDecimal(0);
				osobaClaimActualKn.setScale(2, RoundingMode.HALF_EVEN);	
				Optional<List<Claim>> claimListO=aCommonServis.claimActualPlanned(
						claimRepository,
						osobaClaimActualRepository,
						osobaClaimPlannedRepository,
						idPD);
				
				for(Claim c: claimListO.get()) {
					osobaClaimActualKn=osobaClaimActualKn.add(c.getOsobaClaimActual());
				}
				
				Optional<ProjektDetalji> projektDetaljiO=projektDetaljiRepository.findById(idPD);
				projektDetaljiO.get().setCostActual(osobaClaimActualKn);
				
				projektDetaljiRepository.save(projektDetaljiO.get()); 
			}
		}
	}
	
	private List<OsobaValuta> getOvList(Optional<Long> id, Long idSifarnikOsoba, SifarnikDatuma sifarnikDatumaOd, SifarnikDatuma sifarnikDatumaDo) {
		List<OsobaValuta> ovList=new LinkedList<>();
		List<OsobaValuta> osobaValutaPrethodnoList=new LinkedList<>();
		List<OsobaValuta> osobaValutaSlijedeceList=new LinkedList<>();
		
		List<OsobaValuta> osobaValutaList=osobaValutaRepository.findAllBySifarnikOsobaId(idSifarnikOsoba);			
		
		for(OsobaValuta ov: osobaValutaList) {
			if(id.isPresent() && id.get().equals(ov.getId())) {
				continue;
			}
			
			if(sifarnikDatumaOd.getDatumPetak().isBefore(ov.getSifarnikDatumaOd().getDatumPetak()) || sifarnikDatumaOd.getDatumPetak().isEqual(ov.getSifarnikDatumaOd().getDatumPetak())) {
				osobaValutaPrethodnoList.add(ov);
			}
			
			if(sifarnikDatumaDo.getDatumPetak().isAfter(ov.getSifarnikDatumaDo().getDatumPetak()) || sifarnikDatumaDo.getDatumPetak().isEqual(ov.getSifarnikDatumaDo().getDatumPetak())) {
				osobaValutaSlijedeceList.add(ov);
			}
		}
		
		for(OsobaValuta ovP: osobaValutaPrethodnoList) {
			for(OsobaValuta ovS: osobaValutaSlijedeceList) {
				if(ovP.getId().equals(ovS.getId())) {
					ovList.add(ovP);
				}
			}
		}
		
		return ovList;
	}

	private void setOsobaValutaDoList(PojoInterface pi, List<OsobaValuta> osobaValutaDoList, SifarnikDatuma sifarnikDatumaDo) {
		if(osobaValutaDoList.isEmpty()==false) {
			OsobaValuta ovDoSljedece=osobaValutaDoList.get(0);
			
			if(ovDoSljedece.getSifarnikDatumaDo().getDatumPetak().isBefore(sifarnikDatumaDo.getDatumPetak())) {
				String msg=String.format("'%s' je zapravo prije '%s': %s.", sifarnikDatumaOdValue, sifarnikDatumaDoValue, ovDoSljedece.getId());
				pi.addGreskaList(msg);
			} else {
				LocalDate sdOdPomak=sifarnikDatumaDo.getDatumPetak().plusDays(SEDAM);
				LocalDate ned=sdOdPomak.plusDays(DVA);
				LocalDate mj=ned.withDayOfMonth(JEDAN);
				SifarnikMjeseca sifarnikMjeseca=getSifarnikMjeseca(pi, sifarnikDatumaDoValue, mj);
				
				setSifarnikDatuma(pi, true, ovDoSljedece, sifarnikMjeseca, sdOdPomak);
			}
		}
	}
	
	private void setOsobaValutaOdList(PojoInterface pi, List<OsobaValuta> osobaValutaOdList, SifarnikDatuma sifarnikDatumaOd) {
		if(osobaValutaOdList.isEmpty()==false) {
			OsobaValuta ovOdPrethodno=osobaValutaOdList.get(0);
			
			if(ovOdPrethodno.getSifarnikDatumaOd().getDatumPetak().isAfter(sifarnikDatumaOd.getDatumPetak())) {
				String msg=String.format("'%s' je zapravo prije '%s': %s.", sifarnikDatumaDoValue, sifarnikDatumaOdValue, ovOdPrethodno.getId());
				pi.addGreskaList(msg);
			} else {
				LocalDate sdDoPomak=sifarnikDatumaOd.getDatumPetak().minusDays(SEDAM);
				LocalDate ned=sdDoPomak.plusDays(DVA);
				LocalDate mj=ned.withDayOfMonth(JEDAN);
				SifarnikMjeseca sifarnikMjeseca = getSifarnikMjeseca(pi, sifarnikDatumaOdValue, mj);
				
				setSifarnikDatuma(pi, false, ovOdPrethodno, sifarnikMjeseca, sdDoPomak);
			}
		}
	}

	private SifarnikMjeseca getSifarnikMjeseca(PojoInterface pi, String sifarnikDatumaValue, LocalDate mjesec) {
		SifarnikMjeseca sifarnikMjeseca=null;
		List<SifarnikMjeseca> sifarnikMjesecaList=sifarnikMjesecaRepository.findByMjeseca(mjesec);
		
		if(sifarnikMjesecaList.isEmpty()) {
			SifarnikMjeseca sifarnikMjesecaTmp=new SifarnikMjeseca();
			sifarnikMjesecaTmp.setMjesec(mjesec);
			 
			sifarnikMjeseca=sifarnikMjesecaRepository.save(sifarnikMjesecaTmp);
		} else if(sifarnikMjesecaList.size()>1){
			String msg=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaValue, mjesec);
			pi.addGreskaList(msg);
		} else {
			sifarnikMjeseca=sifarnikMjesecaList.get(0);
		}
		
		return sifarnikMjeseca;
	}
	
	private void setSifarnikDatuma(PojoInterface pi, boolean datumOdDo, OsobaValuta ovDoSljedece, SifarnikMjeseca sifarnikMjeseca, LocalDate sdOdPomak) {
		LocalDate ned=sdOdPomak.plusDays(DVA);
		
		if(sifarnikMjeseca!=null) {
			List<SifarnikDatuma> sifarnikDatumaList=sifarnikDatumaRepository.findByDatumPetak(sdOdPomak);
			SifarnikDatuma sdPomak=null;
			
			if(sifarnikDatumaList.isEmpty()) {
				SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
				sifarnikDatuma.setDatumPetak(sdOdPomak);
				sifarnikDatuma.setDatumNedjelja(ned);
				sifarnikDatuma.setMjesec(sifarnikMjeseca);
				
				sdPomak=sifarnikDatumaRepository.save(sifarnikDatuma);
			} else if(sifarnikDatumaList.size()>1) {
				String msg=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaDoValue, sdOdPomak);
				pi.addGreskaList(msg);
			} else {
				sdPomak=sifarnikDatumaList.get(0);
			}			
			
			if(datumOdDo) {
				ovDoSljedece.setSifarnikDatumaOd(sdPomak);
			} else {
				ovDoSljedece.setSifarnikDatumaDo(sdPomak);
			}
			
			osobaValutaRepository.save(ovDoSljedece);
		}
	}
	
}
