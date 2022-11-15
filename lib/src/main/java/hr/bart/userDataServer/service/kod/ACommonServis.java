package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
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
	
	public ACommonServis(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
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
		Optional<List<Claim>> claimListOptional=kodRepository.getClaimRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(claimListOptional.isPresent()) {
			for(Claim claim: claimListOptional.get()) {
				Optional<List<OsobaClaimActual>> osobaClaimActualListO=kodRepository.getOsobaClaimActualRepository().findAllByIdClaim(claim.getId());
				Optional<List<OsobaClaimPlanned>> osobaClaimPlannedListO=kodRepository.getOsobaClaimPlannedRepository().findAllByIdClaim(claim.getId());
				
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
		Optional<List<OsobaClaimActual>> findAllByIdClaim=kodRepository.getOsobaClaimActualRepository().findAllByIdClaim(oca.getClaim().getId());
		
		for(OsobaClaimActual osobaCA: findAllByIdClaim.get()) {
			koliko=koliko.add(osobaCA.getCijena());
		}
		
		Optional<Claim> claimO=kodRepository.getClaimRepository().findById(oca.getClaim().getId());
		
		claimO.get().setOsobaClaimActual(koliko);
		kodRepository.getClaimRepository().save(claimO.get());
	}
	
	public void setTabliceClaim1(OsobaClaimPlanned ocp) {
		BigDecimal koliko=new BigDecimal(0);
		koliko.setScale(2, RoundingMode.HALF_EVEN);
		Optional<List<OsobaClaimPlanned>> findAllByIdClaim=kodRepository.getOsobaClaimPlannedRepository().findAllByIdClaim(ocp.getClaim().getId());
		
		for(OsobaClaimPlanned osobaCP: findAllByIdClaim.get()) {
			koliko=koliko.add(osobaCP.getSati());
		}
		
		Optional<Claim> claimO=kodRepository.getClaimRepository().findById(ocp.getClaim().getId());
		
		claimO.get().setOsobaClaimPlanned(koliko);
		kodRepository.getClaimRepository().save(claimO.get());
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
		
		Optional<List<ClaimPodugovarac>> claimPodugovaracListO=kodRepository.getClaimPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(claimPodugovaracListO.isPresent()) {
			for(ClaimPodugovarac claimPodugovarac: claimPodugovaracListO.get()) {
				osobaClaimActualKn=osobaClaimActualKn.add(claimPodugovarac.getActual());
			}
		}
		
		Optional<ProjektDetalji> projektDetaljiO=kodRepository.getProjektDetaljiRepository().findById(idProjektDetalji);
		projektDetaljiO.get().setCostActual(osobaClaimActualKn);

		kodRepository.getProjektDetaljiRepository().save(projektDetaljiO.get());
	}
	
	public OsobaValuta getOsobaValuta(Long idSifarnikOsoba, String imePrezime, LocalDate datum) throws Throwable {		
		List<OsobaValuta> osobaValutaList=kodRepository.getOsobaValutaRepository().findAllBySifarnikOsobaId_datum(idSifarnikOsoba, datum);
		
		if(osobaValutaList.isEmpty()) {
			throw new Exception(String.format("U tablici 'OsobaValuta' nije definirana vrijednost za '%s' (%s) za datum = '%s'.", imePrezime, idSifarnikOsoba, datum));
		}
		
		if(osobaValutaList.size()>1) {
			throw new Exception("Iz tablice 'OsobaValuta' dohvat je vratio više od jednog upita!");
		}
			
		return osobaValutaList.get(0);
	}
	
	public SifarnikMjeseca getSifarnikMjeseca(LocalDate mjesec) {
		greska="";
		
		SifarnikMjeseca sifarnikMjeseca=null;
		List<SifarnikMjeseca> sifarnikMjesecaList=kodRepository.getSifarnikMjesecaRepository().findByMjeseca(mjesec);
		
		if(sifarnikMjesecaList.isEmpty()) {
			SifarnikMjeseca sifarnikMjesecaTmp=new SifarnikMjeseca();
			sifarnikMjesecaTmp.setMjesec(mjesec);
			 
			sifarnikMjeseca=kodRepository.getSifarnikMjesecaRepository().save(sifarnikMjesecaTmp);
		} else if(sifarnikMjesecaList.size()>1){
			if(greska.length()>0) greska+=" <BR> ";
			greska+=String.format("Ima previše polja za %s! KRAJ RADA!", mjesec);
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
		
		Optional<List<ClaimPodugovarac>> claimPodugovaracListO=kodRepository.getClaimPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(claimPodugovaracListO.isPresent()) {
			for(ClaimPodugovarac claimPodugovarac: claimPodugovaracListO.get()) {
				osobaClaimPlannedKn=osobaClaimPlannedKn.add(claimPodugovarac.getPlanned());
			}
		}
		
		Optional<ProjektDetalji> projektDetaljiO=kodRepository.getProjektDetaljiRepository().findById(idProjektDetalji);
		projektDetaljiO.get().setCostPlanned(osobaClaimPlannedKn);

		kodRepository.getProjektDetaljiRepository().save(projektDetaljiO.get());
	}
	
	public void findAllBySifarnikOsobaId(PojoInterface pi, Long idSifarnikOsoba, PageRequest pageRequest) {
		Page<List<OsobaValuta>> pageOsobaValutaList=kodRepository.getOsobaValutaRepository().findAllBySifarnikOsobaId(idSifarnikOsoba, pageRequest);
		
		pi.setRezultat(pageOsobaValutaList.getContent());
		
		setRezultatPage(pi, pageOsobaValutaList);
	}
	
	public void findByStatus(PojoInterface pi, DbStatus dbStatus, PageRequest pageRequest) {
		Page<List<Projekt>> pageProjektList=kodRepository.getProjektRepository().findByStatus(dbStatus, pageRequest);
		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
	}
	
	public void findAll_projekt(PojoInterface pi, PageRequest pageRequest) {
		Page<List<Projekt>> pageProjektList=kodRepository.getProjektRepository().findAll(pageRequest);
		pi.setRezultat(pageProjektList.getContent());
		
		setRezultatPage(pi, pageProjektList);
	}
	
	public void findAll_sifarnikOsoba(PojoInterface pi, PageRequest pageRequest) {
		Page<List<SifarnikOsoba>> pageSifarnikOsobaList=kodRepository.getSifarnikOsobaRepository().findAll(pageRequest);
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
