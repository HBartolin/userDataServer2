package hr.bart.userDataServer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.RezultatPage;

public abstract class AService {
	@Autowired
	private transient ClaimRepository claimRepository;
	@Autowired
	private transient OsobaClaimActualRepository osobaClaimActualRepository;
	@Autowired
	private transient OsobaClaimPlannedRepository osobaClaimPlannedRepository;
//	private final Logger LOGGER=LoggerFactory.getLogger(getClass());
	protected final static int pageRequestSize50=50;
	protected final static int pageRequestSize8=8;
	protected final static LocalDate datumPatakZadnji=LocalDate.of(9999, 12, 24);
	protected final static LocalDate mjesecZadnji=datumPatakZadnji.withDayOfMonth(1);
	protected final static String HRK="€";
	protected final static int NULA=0;
	protected final static int JEDAN=1;
	protected final static int DVA=2;
	protected final static int SEDAM=7;
	protected final static int STO=100;
	
//	protected void cachException(Throwable t, PojoInterface pi) {
//		LOGGER.error(t.getMessage(), t);
//		
//		pi.setGreska(t.getMessage());
//	}
	
	private String skratiAkoTreba(int duzina, String provjeri) {
		if(provjeri.length()>duzina) {
			provjeri=provjeri.substring(0, duzina);
		}
		
		return provjeri;
	}
	
	protected String skratiAkoTreba255(String provjeri) {
		return skratiAkoTreba(255, provjeri);
	}
	
	protected void setRezultatPage(PojoInterface pi, Page<?> page) {
		RezultatPage rezultatPage=new RezultatPage(); 
		rezultatPage.setPageNumber(page.getPageable().getPageNumber());
		rezultatPage.setTotalPages(page.getTotalPages());
		
		pi.setRezultatPage(rezultatPage);
	}
	
	protected Optional<List<Claim>> claimActualPlanned(Long idProjektDetalji) {
		Optional<List<Claim>> claimListOptional=claimRepository.findAllByIdProjektDetalji(idProjektDetalji);
		
		if(claimListOptional.isPresent()) {
			for(Claim claim: claimListOptional.get()) {
				Optional<List<OsobaClaimActual>> osobaClaimActualListO=osobaClaimActualRepository.findAllByIdClaim(claim.getId());
				Optional<List<OsobaClaimPlanned>> osobaClaimPlannedListO=osobaClaimPlannedRepository.findAllByIdClaim(claim.getId());
				
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
}
