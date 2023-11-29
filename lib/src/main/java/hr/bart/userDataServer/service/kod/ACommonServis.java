package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.db.OsobaValuta;
import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.db.SifarnikMjeseca;
import hr.bart.userDataServer.db.SifarnikOsoba;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;

public class ACommonServis extends Kod {
	
	public ACommonServis(KodRepository kodRepository) {
		super(kodRepository);
	}
	
	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		throw new RuntimeException("Ne koristi se!");
	}
	
	@Override
	public PojoInterface izvrsi() {
		throw new RuntimeException("Ne koristi se!");
	}

	public Optional<List<Claim>> claimActualPlanned(Long idProjektDetalji) {
		Optional<List<Claim>> claimListOptional=getKodRepository().getClaimRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(claimListOptional.isPresent()) {
			for(Claim claim: claimListOptional.get()) {
				Optional<List<OsobaClaimActual>> osobaClaimActualListO=getKodRepository().getOsobaClaimActualRepository().findAllByIdClaim(claim.getId());
				Optional<List<OsobaClaimPlanned>> osobaClaimPlannedListO=getKodRepository().getOsobaClaimPlannedRepository().findAllByIdClaim(claim.getId());
				
				if(osobaClaimActualListO.isPresent()) {
//					for(OsobaClaimActual osa: osobaClaimActualListO.get()) {
//						osa.setClaim(null);
//					}
					
					claim.setOsobaClaimActualList(osobaClaimActualListO.get());
				}
				
				if(osobaClaimPlannedListO.isPresent()) {
//					for(OsobaClaimPlanned osp: osobaClaimPlannedListO.get()) {
//						osp.setClaim(null);
//					}
					
					claim.setOsobaClaimPlannedList(osobaClaimPlannedListO.get());
				}
			}
		}
		
		return claimListOptional;
	}
	
	public void setTabliceClaim1(OsobaClaimActual oca) {		
		BigDecimal koliko=new BigDecimal(0);
		koliko.setScale(2, RoundingMode.HALF_EVEN);
		Optional<List<OsobaClaimActual>> findAllByIdClaim=getKodRepository().getOsobaClaimActualRepository().findAllByIdClaim(oca.getClaim().getId());
		
		for(OsobaClaimActual osobaCA: findAllByIdClaim.get()) {
			koliko=koliko.add(osobaCA.getCijena());
		}
		
		Optional<Claim> claimO=getKodRepository().getClaimRepository().findById(oca.getClaim().getId());
		
		claimO.get().setOsobaClaimActual(koliko);
		getKodRepository().getClaimRepository().save(claimO.get());
	}
	
	public void setTabliceClaim1(OsobaClaimPlanned ocp) {
		BigDecimal koliko=new BigDecimal(0);
		koliko.setScale(2, RoundingMode.HALF_EVEN);
		Optional<List<OsobaClaimPlanned>> findAllByIdClaim=getKodRepository().getOsobaClaimPlannedRepository().findAllByIdClaim(ocp.getClaim().getId());
		
		for(OsobaClaimPlanned osobaCP: findAllByIdClaim.get()) {
			koliko=koliko.add(osobaCP.getSati());
		}
		
		Optional<Claim> claimO=getKodRepository().getClaimRepository().findById(ocp.getClaim().getId());
		
		claimO.get().setOsobaClaimPlanned(koliko);
		getKodRepository().getClaimRepository().save(claimO.get());
	}

	public void setTabliceActualClaim(Long idProjektDetalji) {		
		BigDecimal osobaClaimActualKn=new BigDecimal(0);
		osobaClaimActualKn.setScale(2, RoundingMode.HALF_EVEN);					
		Optional<List<Claim>> claimListO=claimActualPlanned(idProjektDetalji);
		
		if(claimListO.isPresent()) {
			for(Claim c: claimListO.get()) {
				osobaClaimActualKn=osobaClaimActualKn.add(c.getOsobaClaimActual());
			}
		}
		
		Optional<List<ClaimPodugovarac>> claimPodugovaracListO=getKodRepository().getClaimPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(claimPodugovaracListO.isPresent()) {
			for(ClaimPodugovarac claimPodugovarac: claimPodugovaracListO.get()) {
				osobaClaimActualKn=osobaClaimActualKn.add(claimPodugovarac.getActual());
			}
		}
		
		Optional<ProjektDetalji> projektDetaljiO=getKodRepository().getProjektDetaljiRepository().findById(idProjektDetalji);
		projektDetaljiO.get().setCostActual(osobaClaimActualKn);

		getKodRepository().getProjektDetaljiRepository().save(projektDetaljiO.get());
	}
	
	public OsobaValuta getOsobaValuta(Long idSifarnikOsoba, String imePrezime, LocalDate datum) throws Throwable {		
		List<OsobaValuta> osobaValutaList=getKodRepository().getOsobaValutaRepository().findAllBySifarnikOsobaId_datum(idSifarnikOsoba, datum);
		
		if(osobaValutaList.isEmpty()) {
			throw new Exception(String.format("U tablici 'OsobaValuta' nije definirana vrijednost za '%s' (%s) za datum = '%s'.", imePrezime, idSifarnikOsoba, datum));
		}
		
		if(osobaValutaList.size()>1) {
			throw new Exception("Iz tablice 'OsobaValuta' dohvat je vratio više od jednog upita!");
		}
			
		return osobaValutaList.get(0);
	}
	
	public SifarnikMjeseca getSifarnikMjeseca(PojoInterface pi, LocalDate mjesec) {		
		SifarnikMjeseca sifarnikMjeseca=null;
		List<SifarnikMjeseca> sifarnikMjesecaList=getKodRepository().getSifarnikMjesecaRepository().findByMjeseca(mjesec);
		
		if(sifarnikMjesecaList.isEmpty()) {
			SifarnikMjeseca sifarnikMjesecaTmp=new SifarnikMjeseca();
			sifarnikMjesecaTmp.setMjesec(mjesec);
			 
			sifarnikMjeseca=getKodRepository().getSifarnikMjesecaRepository().save(sifarnikMjesecaTmp);
		} else if(sifarnikMjesecaList.size()>1){
			String msg=String.format("Ima previše polja za %s! KRAJ RADA!", mjesec);
			pi.setGreskaListString(msg);
		} else {
			sifarnikMjeseca=sifarnikMjesecaList.get(0);
		}
		
		return sifarnikMjeseca;
	}
	
	public void setTablicePlannedClaim(Long idProjektDetalji) {
		BigDecimal osobaClaimPlannedKn=new BigDecimal(0);
		osobaClaimPlannedKn.setScale(2, RoundingMode.HALF_EVEN);					
		Optional<List<Claim>> claimListO=claimActualPlanned(idProjektDetalji);
		
		for(Claim c: claimListO.get()) {
			osobaClaimPlannedKn=osobaClaimPlannedKn.add(c.getOsobaClaimPlanned());
		}
		
		Optional<List<ClaimPodugovarac>> claimPodugovaracListO=getKodRepository().getClaimPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(claimPodugovaracListO.isPresent()) {
			for(ClaimPodugovarac claimPodugovarac: claimPodugovaracListO.get()) {
				osobaClaimPlannedKn=osobaClaimPlannedKn.add(claimPodugovarac.getPlanned());
			}
		}
		
		Optional<ProjektDetalji> projektDetaljiO=getKodRepository().getProjektDetaljiRepository().findById(idProjektDetalji);
		projektDetaljiO.get().setCostPlanned(osobaClaimPlannedKn);

		getKodRepository().getProjektDetaljiRepository().save(projektDetaljiO.get());
	}
	
	public void findAllBySifarnikOsobaId(PojoInterface pi, Long idSifarnikOsoba, PageRequest pageRequest) {
		Page<List<OsobaValuta>> pageOsobaValutaList=getKodRepository().getOsobaValutaRepository().findAllBySifarnikOsobaId(idSifarnikOsoba, pageRequest);
		
		pi.setRezultat(pageOsobaValutaList.getContent());
		
		setRezultatPage(pi, pageOsobaValutaList);
	}
	
	public void findByStatus(PojoInterface pi, DbStatus dbStatus, PageRequest pageRequest) {
		Page<List<Projekt>> pageProjektList=getKodRepository().getProjektRepository().findByStatus(dbStatus, pageRequest);
		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
	}
	
	public void findAll_projekt(PojoInterface pi, PageRequest pageRequest) {
		Page<List<Projekt>> pageProjektList=getKodRepository().getProjektRepository().findAll(pageRequest);
		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
	}
	
	public void findAll_sifarnikOsoba(PojoInterface pi, PageRequest pageRequest) {
		Page<List<SifarnikOsoba>> pageSifarnikOsobaList=getKodRepository().getSifarnikOsobaRepository().findAll(pageRequest);
		pi.setRezultat(pageSifarnikOsobaList.getContent());
		
		setRezultatPage(pi, pageSifarnikOsobaList);
	}
	
	private String skratiAkoTreba(int duzina, String provjeri) {
		if(provjeri.length()>duzina) {
			provjeri=provjeri.substring(0, duzina);
		}
		
		return provjeri;
	}
	
	public String skratiAkoTreba255(String provjeri) {
		return skratiAkoTreba(255, provjeri);
	}
}
