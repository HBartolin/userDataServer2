package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.db.OsobaValuta;
import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.db.SifarnikDatuma;
import hr.bart.userDataServer.db.SifarnikMjeseca;
import hr.bart.userDataServer.db.SifarnikOsoba;
import hr.bart.userDataServer.db.SifarnikPodugovaraca;
import hr.bart.userDataServer.db.SifarnikValuta;
import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.repository.SifarnikDatumaRepository;
import hr.bart.userDataServer.repository.SifarnikMjesecaRepository;
import hr.bart.userDataServer.repository.SifarnikOsobaRepository;
import hr.bart.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.bart.userDataServer.repository.SifarnikValutaRepository;
import hr.bart.userDataServer.util.DbStatus;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RestServiceImplCreateDB extends Kod {
	private final SifarnikValutaRepository sifarnikValutaRepository;
	private final SifarnikOsobaRepository sifarnikOsobaRepository;
	private final SifarnikMjesecaRepository sifarnikMjesecaRepository;
	private final SifarnikDatumaRepository sifarnikDatumaRepository;
	private final OsobaValutaRepository osobaValutaRepository;
	private final SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;
	private final ProjektRepository projektRepository;
	private final ProjektDetaljiRepository projektDetaljiRepository;
	private final ClaimRepository claimRepository;
	private final OsobaClaimActualRepository osobaClaimActualRepository;
	private final OsobaClaimPlannedRepository osobaClaimPlannedRepository;
	private final ClaimPodugovaracRepository claimPodugovaracRepository;
	
	public RestServiceImplCreateDB(
			SifarnikValutaRepository sifarnikValutaRepository,
			SifarnikOsobaRepository sifarnikOsobaRepository,
			SifarnikMjesecaRepository sifarnikMjesecaRepository,
			SifarnikDatumaRepository sifarnikDatumaRepository,
			OsobaValutaRepository osobaValutaRepository,
			SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository,
			ProjektRepository projektRepository,
			ProjektDetaljiRepository projektDetaljiRepository,
			ClaimRepository claimRepository,
			OsobaClaimActualRepository osobaClaimActualRepository,
			OsobaClaimPlannedRepository osobaClaimPlannedRepository,
			ClaimPodugovaracRepository claimPodugovaracRepository
			) {
		this.sifarnikValutaRepository=sifarnikValutaRepository;
		this.sifarnikOsobaRepository=sifarnikOsobaRepository;
		this.sifarnikMjesecaRepository=sifarnikMjesecaRepository;
		this.sifarnikDatumaRepository=sifarnikDatumaRepository;
		this.osobaValutaRepository=osobaValutaRepository;
		this.sifarnikPodugovaracaRepository=sifarnikPodugovaracaRepository;
		this.projektRepository=projektRepository;
		this.projektDetaljiRepository=projektDetaljiRepository;
		this.claimRepository=claimRepository;
		this.osobaClaimActualRepository = osobaClaimActualRepository;
		this.osobaClaimPlannedRepository = osobaClaimPlannedRepository;
		this.claimPodugovaracRepository = claimPodugovaracRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) throws Throwable {
		SifarnikValuta sv=new SifarnikValuta();
		sv.setNaziv(HRK);
		sv=sifarnikValutaRepository.save(sv);
		pi.setOk("SifarnikValuta");
		
		SifarnikOsoba so=new SifarnikOsoba();
		so.setIme("Ime");
		so.setPrezime("Prezime");
		so=sifarnikOsobaRepository.save(so);
		pi.setOk(pi.getOk() + ", SifarnikOsoba");
		
		SifarnikMjeseca smDo=new SifarnikMjeseca();
		smDo.setMjesec(LocalDate.of(9999, 12, 1));
		smDo=sifarnikMjesecaRepository.save(smDo);
		pi.setOk(pi.getOk() + ", SifarnikMjeseca");
		
		SifarnikDatuma sdDo=new SifarnikDatuma();
		sdDo.setDatumNedjelja(LocalDate.of(9999, 12, 26));
		sdDo.setDatumPetak(LocalDate.of(9999, 12, 24));
		sdDo.setMjesec(smDo);
		sdDo=sifarnikDatumaRepository.save(sdDo);
		pi.setOk(pi.getOk() + ", SifarnikDatuma");
		
		SifarnikMjeseca smOd=new SifarnikMjeseca();
		smOd.setMjesec(LocalDate.of(2000, 1, 1));
		smOd=sifarnikMjesecaRepository.save(smOd);
		pi.setOk(pi.getOk() + ", SifarnikMjeseca");
		
		SifarnikDatuma sdOd=new SifarnikDatuma();
		sdOd.setDatumNedjelja(LocalDate.of(2000, 1, 9));
		sdOd.setDatumPetak(LocalDate.of(2000, 1, 7));
		sdOd.setMjesec(smDo);
		sdOd=sifarnikDatumaRepository.save(sdOd);
		pi.setOk(pi.getOk() + ", SifarnikDatuma");
		
		OsobaValuta ov=new OsobaValuta();
		ov.setBand("B7");
		ov.setCijena(new BigDecimal(200));
		ov.setSifarnikDatumaDo(sdDo);
		ov.setSifarnikDatumaOd(sdOd);
		ov.setSifarnikOsoba(so);
		ov.setSifarnikValuta(sv);
		ov.setTs(0l);
		ov=osobaValutaRepository.save(ov);
		pi.setOk(pi.getOk() + ", OsobaValuta");
		
		SifarnikPodugovaraca sp=new SifarnikPodugovaraca();
		sp.setNaziv("Podugovarač");
		sp=sifarnikPodugovaracaRepository.save(sp);
		pi.setOk(pi.getOk() + ", SifarnikPodugovaraca");
		
		Projekt p=new Projekt();
		p.setClaim("Claim");
		p.setContract("Contract");
		p.setStatus(DbStatus.A);
		p.setTs(0l);
		p=projektRepository.save(p);
		pi.setOk(pi.getOk() + ", Projekt");
					
		ProjektDetalji pd=new ProjektDetalji();
		pd.setCostActual(BigDecimal.ZERO);
		pd.setCostPlanned(BigDecimal.ZERO);
		pd.setCostPs(BigDecimal.TEN);
		pd.setProjekt(p);
		pd.setSifarnikValuta(sv);
		pd.setTotalRevenue(new BigDecimal(123456));
		pd=projektDetaljiRepository.save(pd);
		pi.setOk(pi.getOk() + ", ProjektDetalji");
		
		Claim c=new Claim();
		c.setOsobaClaimActual(BigDecimal.ZERO);
		c.setOsobaClaimPlanned(BigDecimal.ZERO);
		c.setProjektDetalji(pd);
		c.setSifarnikOsoba(so);
		c=claimRepository.save(c);
		pi.setOk(pi.getOk() + ", Claim");
		
		OsobaClaimActual oca=new OsobaClaimActual();
		oca.setCijena(BigDecimal.ONE);
		oca.setClaim(c);
		oca.setOsobaValuta(ov);
		oca.setSati(BigDecimal.ONE);
		oca.setSifarnikDatuma(sdOd);
		oca=osobaClaimActualRepository.save(oca);
		pi.setOk(pi.getOk() + ", OsobaClaimActual");
		
		OsobaClaimPlanned ocp=new OsobaClaimPlanned();
		ocp.setClaim(c);
		ocp.setSati(BigDecimal.ONE);
		ocp.setSifarnikMjeseca(smOd);
		ocp=osobaClaimPlannedRepository.save(ocp);
		pi.setOk(pi.getOk() + ", OsobaClaimPlanned");
		
		ClaimPodugovarac cp=new ClaimPodugovarac();
		cp.setActual(BigDecimal.ZERO);
		cp.setPlanned(BigDecimal.ZERO);
		cp.setPo("1234");
		cp.setProjektDetalji(pd);
		cp.setSifarnikPodugovaraca(sp);
		cp.setTotal(BigDecimal.ZERO);
		cp=claimPodugovaracRepository.save(cp);
		pi.setOk(pi.getOk() + ", ClaimPodugovarac");
		
		return pi;
	}

}
