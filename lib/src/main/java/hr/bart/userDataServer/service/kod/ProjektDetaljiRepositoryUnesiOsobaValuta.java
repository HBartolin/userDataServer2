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
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiRepositoryUnesiOsobaValuta extends Kod {
	private final Optional<Long> id;
	private final Long ts;
	private final Long idSifarnikOsoba;
	private final String band;
	private final BigDecimal cijena;
	private final LocalDate sifarnikDatumaOdLD;
	private final LocalDate sifarnikDatumaDoLD;
	private String greska="";
	private String sifarnikDatumaOdValue="Datum od";
	private String sifarnikDatumaDoValue="Datum do";
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);
	
	public ProjektDetaljiRepositoryUnesiOsobaValuta(
			KodRepository kodRepository,
			Optional<Long> id,
			Long ts,
			Long idSifarnikOsoba,
			String band,
			BigDecimal cijena,
			LocalDate sifarnikDatumaOdLD,
			LocalDate sifarnikDatumaDoLD
			) {
		super(kodRepository);
		this.id=id;
		this.ts=ts;
		this.idSifarnikOsoba=idSifarnikOsoba;
		this.band=band;
		this.cijena=cijena;
		this.sifarnikDatumaDoLD=sifarnikDatumaDoLD;
		this.sifarnikDatumaOdLD=sifarnikDatumaOdLD;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		greska="";
		SifarnikDatuma sifarnikDatumaDo=null;
		SifarnikDatuma sifarnikDatumaOd=null;
		
		if("".equals(band)) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje Band nije upisano.";
		}
		
		if(cijena==null) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Polje Cijena nije upisano.";
		}
		
		if(sifarnikDatumaOdLD==null) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Polje '%s' nije upisano.", sifarnikDatumaOdValue);
		} else {
			sifarnikDatumaOd=getSifarnikDatumaOd(sifarnikDatumaOdLD);
		}
					
		if(sifarnikDatumaDoLD==null) {
			sifarnikDatumaDo=getSifarnikDatumaDo();
		} else {
			sifarnikDatumaDo=getSifarnikDatumaDoExist(sifarnikDatumaDoLD);
		}
		
		if(greska.length()==0 && sifarnikDatumaOd!=null && sifarnikDatumaDo!=null) {
			if(sifarnikDatumaOd.getDatumPetak().isEqual(sifarnikDatumaDo.getDatumPetak()) || sifarnikDatumaOd.getDatumPetak().isAfter(sifarnikDatumaDo.getDatumPetak())) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+=String.format("'%s' je nakon ili jednak '%s'.", sifarnikDatumaOdValue, sifarnikDatumaDoValue);
			} else {
				napraviSifarnikeDatuma(id, ts, band, cijena, idSifarnikOsoba, sifarnikDatumaOd, sifarnikDatumaDo);
			} 
		}
			
		if(greska.length()>0) {
			pi.setGreska(greska);
		} 
		
		PageRequest pageRequest=PageRequest.of(NULA, pageRequestSize50);
		
		aCommonServis.findAllBySifarnikOsobaId(pi, idSifarnikOsoba, pageRequest);
		
		return pi;
	}
	
	private SifarnikDatuma getSifarnikDatumaOd(LocalDate sifarnikDatumaOdLD) {
		DayOfWeek dayOfWeek=sifarnikDatumaOdLD.getDayOfWeek();
		SifarnikDatuma sifarnikDatumaOd=null;
		
		if(!DayOfWeek.FRIDAY.equals(dayOfWeek)) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Polje '%s' mota biti PETAK.", sifarnikDatumaOdValue);
		} else {
			LocalDate ned=sifarnikDatumaOdLD.plusDays(DVA);
			LocalDate mj=ned.withDayOfMonth(JEDAN);
			SifarnikMjeseca sifarnikMjeseca=getSifarnikMjeseca(sifarnikDatumaOdValue, mj);
			
			if(sifarnikMjeseca!=null) {			
				List<SifarnikDatuma> sifarnikDatumaList=kodRepository.getSifarnikDatumaRepository().findByDatumPetak(sifarnikDatumaOdLD);
				
				if(sifarnikDatumaList.isEmpty()) {
					SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
					sifarnikDatuma.setDatumPetak(sifarnikDatumaOdLD);
					sifarnikDatuma.setDatumNedjelja(ned);
					sifarnikDatuma.setMjesec(sifarnikMjeseca);
					
					sifarnikDatumaOd=kodRepository.getSifarnikDatumaRepository().save(sifarnikDatuma);
				} else if(sifarnikDatumaList.size()>1) {
					if(greska.length()>0) greska+=" <BR> ";
					greska+=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaDoValue, sifarnikDatumaOdLD);
				} else {
					sifarnikDatumaOd=sifarnikDatumaList.get(0);
				}		
			}
		}
		
		return sifarnikDatumaOd;
	}
	
	private SifarnikDatuma getSifarnikDatumaDoExist(LocalDate sifarnikDatumaDoLD) {
		SifarnikDatuma sifarnikDatumaDo=null;
		DayOfWeek dayOfWeek=sifarnikDatumaDoLD.getDayOfWeek();
		
		if(!DayOfWeek.FRIDAY.equals(dayOfWeek)) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Polje '%s' mota biti PETAK.", sifarnikDatumaDoValue);
		} else {
			LocalDate ned=sifarnikDatumaDoLD.plusDays(DVA);
			LocalDate mj=ned.withDayOfMonth(JEDAN);
			SifarnikMjeseca sifarnikMjeseca=getSifarnikMjeseca(sifarnikDatumaDoValue, mj);

			if(sifarnikMjeseca!=null) {
				List<SifarnikDatuma> sifarnikDatumaList=kodRepository.getSifarnikDatumaRepository().findByDatumPetak(sifarnikDatumaDoLD);
				
				if(sifarnikDatumaList.isEmpty()) {
					SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
					sifarnikDatuma.setDatumPetak(sifarnikDatumaDoLD);
					sifarnikDatuma.setDatumNedjelja(ned);
					sifarnikDatuma.setMjesec(sifarnikMjeseca);
					
					sifarnikDatumaDo=kodRepository.getSifarnikDatumaRepository().save(sifarnikDatuma);
				} else if(sifarnikDatumaList.size()>1) {
					if(greska.length()>0) greska+=" <BR> ";
					greska+=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaDoValue, sifarnikDatumaDoLD);
				} else {
					sifarnikDatumaDo=sifarnikDatumaList.get(0);
				}					
			}
		}
		
		return sifarnikDatumaDo;
	}

	private SifarnikDatuma getSifarnikDatumaDo() {
		SifarnikDatuma sifarnikDatumaDo=null;
		List<SifarnikDatuma> sifarnikDatumaList=kodRepository.getSifarnikDatumaRepository().findByDatumPetak(datumPatakZadnji);
		
		if(sifarnikDatumaList.isEmpty()) {
			SifarnikMjeseca sifarnikMjeseca=getSifarnikMjeseca(sifarnikDatumaDoValue, mjesecZadnji);
			
			if(sifarnikMjeseca!=null) {
				SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
				sifarnikDatuma.setDatumPetak(datumPatakZadnji);
				sifarnikDatuma.setDatumNedjelja(datumPatakZadnji.plusDays(DVA));
				sifarnikDatuma.setMjesec(sifarnikMjeseca);
				
				sifarnikDatumaDo=kodRepository.getSifarnikDatumaRepository().save(sifarnikDatuma);
			}
		} else if(sifarnikDatumaList.size()>1){
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaDoValue, datumPatakZadnji);
		} else {
			sifarnikDatumaDo=sifarnikDatumaList.get(0);
		}
		
		return sifarnikDatumaDo;
	}
	
	private void napraviSifarnikeDatuma(Optional<Long> id, Long ts, String band, BigDecimal cijena, Long idSifarnikOsoba, SifarnikDatuma sifarnikDatumaOd, SifarnikDatuma sifarnikDatumaDo) {
		final List<OsobaValuta> osobaValutaOdList;
		final List<OsobaValuta> osobaValutaDoList;
		
		if(id.isPresent()) {
			osobaValutaOdList=kodRepository.getOsobaValutaRepository().findAllByIdSifarnikDatumaOd(id.get(), idSifarnikOsoba, sifarnikDatumaOd.getDatumPetak());
			osobaValutaDoList=kodRepository.getOsobaValutaRepository().findAllByIdSifarnikDatumaDo(id.get(), idSifarnikOsoba, sifarnikDatumaDo.getDatumPetak());
		} else {
			osobaValutaOdList=kodRepository.getOsobaValutaRepository().findAllBySifarnikDatumaOd(idSifarnikOsoba, sifarnikDatumaOd.getDatumPetak());
			osobaValutaDoList=kodRepository.getOsobaValutaRepository().findAllBySifarnikDatumaDo(idSifarnikOsoba, sifarnikDatumaDo.getDatumPetak());
		}
		
		List<OsobaValuta> ovList=getOvList(id, idSifarnikOsoba, sifarnikDatumaOd, sifarnikDatumaDo);	
		
		if(ovList.isEmpty()==false) {
			String g="";
			
			for(OsobaValuta ov: ovList) {
				if(g.length()>0) g+=", ";
				g+=ov.getId();
			}
			
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Prevelik raspon datuma, prekriva polje: %s.", g);
		} else if(osobaValutaOdList.size()>1 || osobaValutaDoList.size()>1) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Nešto se čudno desilo, ajde u kod.";
		} else {
			setOsobaValutaOdList(osobaValutaOdList, sifarnikDatumaOd);
			setOsobaValutaDoList(osobaValutaDoList, sifarnikDatumaDo);
			Optional<SifarnikOsoba> sifarnikOsoba=kodRepository.getSifarnikOsobaRepository().findById(idSifarnikOsoba);
			Optional<SifarnikValuta> sifarnikValuta=kodRepository.getSifarnikValutaRepository().findAllByNaziv(HRK);
			
			OsobaValuta osobaValuta=new OsobaValuta();			
			osobaValuta.setBand(band);
			osobaValuta.setCijena(cijena);
			osobaValuta.setSifarnikDatumaOd(sifarnikDatumaOd);
			osobaValuta.setSifarnikDatumaDo(sifarnikDatumaDo);
			osobaValuta.setSifarnikOsoba(sifarnikOsoba.get());
			osobaValuta.setSifarnikValuta(sifarnikValuta.get());
			osobaValuta.setTs(ts);
			if(id.isPresent()) osobaValuta.setId(id.get());
			
			osobaValuta=kodRepository.getOsobaValutaRepository().save(osobaValuta);
			
			LocalDate datumOd=sifarnikDatumaOd.getDatumPetak();
			LocalDate datumDo=sifarnikDatumaDo.getDatumPetak();			
			Optional<List<OsobaClaimActual>> ocaListO=kodRepository.getOsobaClaimActualRepository().findAllBySifarnikOsoba_datumi(sifarnikOsoba.get(), datumOd, datumDo);
			HashSet<Long> idProjektDetaljiList=new HashSet<>();
			
			if(ocaListO.isPresent()) {
				List<OsobaClaimActual> ocaList=ocaListO.get();
				HashSet<Long> idClaimList=new HashSet<>();
				BigDecimal koliko=new BigDecimal(0);
				koliko.setScale(2, RoundingMode.HALF_EVEN);
				
				for(OsobaClaimActual osobaClaimActual: ocaList) {
					osobaClaimActual.setOsobaValuta(osobaValuta);
					osobaClaimActual.setCijena(osobaClaimActual.getSati().multiply(osobaValuta.getCijena()));
					
					kodRepository.getOsobaClaimActualRepository().save(osobaClaimActual);
					
					Optional<Claim> claimO=kodRepository.getClaimRepository().findById(osobaClaimActual.getClaim().getId());
					
					idProjektDetaljiList.add(claimO.get().getProjektDetalji().getId());
					idClaimList.add(osobaClaimActual.getClaim().getId());
				}	
				
				for(Long idC: idClaimList) {
					Optional<List<OsobaClaimActual>> findAllByIdClaim=kodRepository.getOsobaClaimActualRepository().findAllByIdClaim(idC);
					
					for(OsobaClaimActual osobaCA: findAllByIdClaim.get()) {
						koliko=koliko.add(osobaCA.getCijena());
					}
					
					Optional<Claim> claimO=kodRepository.getClaimRepository().findById(idC);
					
					claimO.get().setOsobaClaimActual(koliko);
					kodRepository.getClaimRepository().save(claimO.get());
				}				
			}	
			
			for(Long idPD: idProjektDetaljiList) {		
				BigDecimal osobaClaimActualKn=new BigDecimal(0);
				osobaClaimActualKn.setScale(2, RoundingMode.HALF_EVEN);	
				Optional<List<Claim>> claimListO=aCommonServis.claimActualPlanned(idPD);
				
				for(Claim c: claimListO.get()) {
					osobaClaimActualKn=osobaClaimActualKn.add(c.getOsobaClaimActual());
				}
				
				Optional<ProjektDetalji> projektDetaljiO=kodRepository.getProjektDetaljiRepository().findById(idPD);
				projektDetaljiO.get().setCostActual(osobaClaimActualKn);
				
				kodRepository.getProjektDetaljiRepository().save(projektDetaljiO.get()); 
			}
		}
	}
	
	private List<OsobaValuta> getOvList(Optional<Long> id, Long idSifarnikOsoba, SifarnikDatuma sifarnikDatumaOd, SifarnikDatuma sifarnikDatumaDo) {
		List<OsobaValuta> ovList=new LinkedList<>();
		List<OsobaValuta> osobaValutaPrethodnoList=new LinkedList<>();
		List<OsobaValuta> osobaValutaSlijedeceList=new LinkedList<>();
		
		List<OsobaValuta> osobaValutaList=kodRepository.getOsobaValutaRepository().findAllBySifarnikOsobaId(idSifarnikOsoba);			
		
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

	private void setOsobaValutaDoList(List<OsobaValuta> osobaValutaDoList, SifarnikDatuma sifarnikDatumaDo) {
		if(osobaValutaDoList.isEmpty()==false) {
			OsobaValuta ovDoSljedece=osobaValutaDoList.get(0);
			
			if(ovDoSljedece.getSifarnikDatumaDo().getDatumPetak().isBefore(sifarnikDatumaDo.getDatumPetak())) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+=String.format("'%s' je zapravo prije '%s': %s.", sifarnikDatumaOdValue, sifarnikDatumaDoValue, ovDoSljedece.getId());
			} else {
				LocalDate sdOdPomak=sifarnikDatumaDo.getDatumPetak().plusDays(SEDAM);
				LocalDate ned=sdOdPomak.plusDays(DVA);
				LocalDate mj=ned.withDayOfMonth(JEDAN);
				SifarnikMjeseca sifarnikMjeseca=getSifarnikMjeseca(sifarnikDatumaDoValue, mj);
				
				setSifarnikDatuma(true, ovDoSljedece, sifarnikMjeseca, sdOdPomak);
			}
		}
	}
	
	private void setOsobaValutaOdList(List<OsobaValuta> osobaValutaOdList, SifarnikDatuma sifarnikDatumaOd) {
		if(osobaValutaOdList.isEmpty()==false) {
			OsobaValuta ovOdPrethodno=osobaValutaOdList.get(0);
			
			if(ovOdPrethodno.getSifarnikDatumaOd().getDatumPetak().isAfter(sifarnikDatumaOd.getDatumPetak())) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+=String.format("'%s' je zapravo prije '%s': %s.", sifarnikDatumaDoValue, sifarnikDatumaOdValue, ovOdPrethodno.getId());
			} else {
				LocalDate sdDoPomak=sifarnikDatumaOd.getDatumPetak().minusDays(SEDAM);
				LocalDate ned=sdDoPomak.plusDays(DVA);
				LocalDate mj=ned.withDayOfMonth(JEDAN);
				SifarnikMjeseca sifarnikMjeseca = getSifarnikMjeseca(sifarnikDatumaOdValue, mj);
				
				setSifarnikDatuma(false, ovOdPrethodno, sifarnikMjeseca, sdDoPomak);
			}
		}
	}

	private SifarnikMjeseca getSifarnikMjeseca(String sifarnikDatumaValue, LocalDate mjesec) {
		SifarnikMjeseca sifarnikMjeseca=null;
		List<SifarnikMjeseca> sifarnikMjesecaList=kodRepository.getSifarnikMjesecaRepository().findByMjeseca(mjesec);
		
		if(sifarnikMjesecaList.isEmpty()) {
			SifarnikMjeseca sifarnikMjesecaTmp=new SifarnikMjeseca();
			sifarnikMjesecaTmp.setMjesec(mjesec);
			 
			sifarnikMjeseca=kodRepository.getSifarnikMjesecaRepository().save(sifarnikMjesecaTmp);
		} else if(sifarnikMjesecaList.size()>1){
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaValue, mjesec);
		} else {
			sifarnikMjeseca=sifarnikMjesecaList.get(0);
		}
		
		return sifarnikMjeseca;
	}
	
	private void setSifarnikDatuma(boolean datumOdDo, OsobaValuta ovDoSljedece, SifarnikMjeseca sifarnikMjeseca, LocalDate sdOdPomak) {
		LocalDate ned=sdOdPomak.plusDays(DVA);
		
		if(sifarnikMjeseca!=null) {
			List<SifarnikDatuma> sifarnikDatumaList=kodRepository.getSifarnikDatumaRepository().findByDatumPetak(sdOdPomak);
			SifarnikDatuma sdPomak=null;
			
			if(sifarnikDatumaList.isEmpty()) {
				SifarnikDatuma sifarnikDatuma=new SifarnikDatuma();
				sifarnikDatuma.setDatumPetak(sdOdPomak);
				sifarnikDatuma.setDatumNedjelja(ned);
				sifarnikDatuma.setMjesec(sifarnikMjeseca);
				
				sdPomak=kodRepository.getSifarnikDatumaRepository().save(sifarnikDatuma);
			} else if(sifarnikDatumaList.size()>1) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+=String.format("Ima previše polja '%s' = %s! KRAJ RADA!", sifarnikDatumaDoValue, sdOdPomak);
			} else {
				sdPomak=sifarnikDatumaList.get(0);
			}			
			
			if(datumOdDo) {
				ovDoSljedece.setSifarnikDatumaOd(sdPomak);
			} else {
				ovDoSljedece.setSifarnikDatumaDo(sdPomak);
			}
			
			kodRepository.getOsobaValutaRepository().save(ovDoSljedece);
		}
	}
	
}
