package hr.kyndryl.bartolin.userDataServer.service.kod;

import hr.kyndryl.bartolin.userDataServer.repository.ClaimPodugovaracRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ClaimRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimActualRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.kyndryl.bartolin.userDataServer.repository.OsobaValutaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.PodugovaracRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ProjektDetaljiRepository;
import hr.kyndryl.bartolin.userDataServer.repository.ProjektRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikDatumaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikMjesecaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikOsobaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.kyndryl.bartolin.userDataServer.repository.SifarnikValutaRepository;

public class KodRepository {
	private ClaimPodugovaracRepository claimPodugovaracRepository=null;
	private ProjektDetaljiRepository projektDetaljiRepository=null;
	private SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository=null;
	private ClaimRepository claimRepository=null;
	private OsobaClaimActualRepository osobaClaimActualRepository=null;
	private OsobaClaimPlannedRepository osobaClaimPlannedRepository=null;
	private OsobaValutaRepository osobaValutaRepository=null;
	private PodugovaracRepository podugovaracRepository=null;
	private SifarnikMjesecaRepository sifarnikMjesecaRepository=null;
	private ProjektRepository projektRepository=null;
	private SifarnikOsobaRepository sifarnikOsobaRepository=null;
	private SifarnikDatumaRepository sifarnikDatumaRepository=null;
	private SifarnikValutaRepository sifarnikValutaRepository=null;

	public void setClaimPodugovaracRepository(ClaimPodugovaracRepository claimPodugovaracRepository) {
		this.claimPodugovaracRepository=claimPodugovaracRepository;
	}
	
	public ClaimPodugovaracRepository getClaimPodugovaracRepository() {
		return claimPodugovaracRepository;
	}

	public ProjektDetaljiRepository getProjektDetaljiRepository() {
		return projektDetaljiRepository;
	}

	public void setProjektDetaljiRepository(ProjektDetaljiRepository projektDetaljiRepository) {
		this.projektDetaljiRepository = projektDetaljiRepository;
	}

	public SifarnikPodugovaracaRepository getSifarnikPodugovaracaRepository() {
		return sifarnikPodugovaracaRepository;
	}

	public void setSifarnikPodugovaracaRepository(SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository) {
		this.sifarnikPodugovaracaRepository = sifarnikPodugovaracaRepository;
	}

	public ClaimRepository getClaimRepository() {
		return claimRepository;
	}

	public void setClaimRepository(ClaimRepository claimRepository) {
		this.claimRepository = claimRepository;
	}

	public OsobaClaimActualRepository getOsobaClaimActualRepository() {
		return osobaClaimActualRepository;
	}

	public void setOsobaClaimActualRepository(OsobaClaimActualRepository osobaClaimActualRepository) {
		this.osobaClaimActualRepository = osobaClaimActualRepository;
	}

	public OsobaClaimPlannedRepository getOsobaClaimPlannedRepository() {
		return osobaClaimPlannedRepository;
	}

	public void setOsobaClaimPlannedRepository(OsobaClaimPlannedRepository osobaClaimPlannedRepository) {
		this.osobaClaimPlannedRepository = osobaClaimPlannedRepository;
	}

	public OsobaValutaRepository getOsobaValutaRepository() {
		return osobaValutaRepository;
	}

	public void setOsobaValutaRepository(OsobaValutaRepository osobaValutaRepository) {
		this.osobaValutaRepository = osobaValutaRepository;
	}

	public PodugovaracRepository getPodugovaracRepository() {
		return podugovaracRepository;
	}

	public void setPodugovaracRepository(PodugovaracRepository podugovaracRepository) {
		this.podugovaracRepository = podugovaracRepository;
	}

	public SifarnikMjesecaRepository getSifarnikMjesecaRepository() {
		return sifarnikMjesecaRepository;
	}

	public void setSifarnikMjesecaRepository(SifarnikMjesecaRepository sifarnikMjesecaRepository) {
		this.sifarnikMjesecaRepository = sifarnikMjesecaRepository;
	}

	public ProjektRepository getProjektRepository() {
		return projektRepository;
	}

	public void setProjektRepository(ProjektRepository projektRepository) {
		this.projektRepository = projektRepository;
	}

	public SifarnikOsobaRepository getSifarnikOsobaRepository() {
		return sifarnikOsobaRepository;
	}

	public void setSifarnikOsobaRepository(SifarnikOsobaRepository sifarnikOsobaRepository) {
		this.sifarnikOsobaRepository = sifarnikOsobaRepository;
	}

	public SifarnikDatumaRepository getSifarnikDatumaRepository() {
		return sifarnikDatumaRepository;
	}

	public void setSifarnikDatumaRepository(SifarnikDatumaRepository sifarnikDatumaRepository) {
		this.sifarnikDatumaRepository = sifarnikDatumaRepository;
	}

	public SifarnikValutaRepository getSifarnikValutaRepository() {
		return sifarnikValutaRepository;
	}

	public void setSifarnikValutaRepository(SifarnikValutaRepository sifarnikValutaRepository) {
		this.sifarnikValutaRepository = sifarnikValutaRepository;
	}
}
