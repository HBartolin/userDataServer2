package hr.bart.userDataServer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.bart.userDataServer.service.ProjektDetaljiService;
import hr.bart.userDataServer.service.kod.ProjektDetaljiServiceImplProjektDatalji;
import hr.bart.userDataServer.service.kod.ProjektServiceImplNoviProjekt;
import hr.bart.userDataServer.service.kod.ProjektServiceImplProjekti;
import hr.bart.userDataServer.service.kod.ProjektServiceImplTablicaProjekti;
import hr.bart.userDataServer.service.kod.ProjektServiceImplTraziProjekte;
import hr.bart.userDataServer.service.kod.ProjektServiceImplZatvoriOtvoriProjekt;
import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.ZatvoriOtvori;

@RestController
@RequestMapping(value = ResponseEntityPojo.REQUEST_MAPPING_VALUE)
@CrossOrigin
public class ProjektControllerProjekt extends ResponseEntityPojo {
	@Autowired
	private transient ProjektServiceImplProjekti projektServiceImplProjekti;
	@Autowired
	private transient ProjektServiceImplZatvoriOtvoriProjekt projektServiceImplZatvoriOtvoriProjekt;
	@Autowired
	private transient ProjektServiceImplNoviProjekt projektServiceImplNoviProjekt;
	@Autowired
	private transient ProjektServiceImplTablicaProjekti projektServiceImplTablicaProjekti;
	@Autowired
	private transient ProjektServiceImplTraziProjekte projektServiceImplTraziProjekte;
	@Autowired
	private transient ProjektDetaljiService projektDetaljiService;
	@Autowired
	private transient ProjektDetaljiServiceImplProjektDatalji projektDetaljiServiceImplProjektDatalji; 
	
	@GetMapping(value = "/projekti")
    public ResponseEntity<PojoInterface> projekti(@RequestParam("status") Optional<String> status) {		
		//PojoInterface pi=projektService.projekti(status);
		PojoInterface pi=projektServiceImplProjekti.izvrsi(status);
		
		return handlePi(pi);
    }	
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/zatvoriProjekt/{id}")
	public ResponseEntity<PojoInterface> zatvoriProjekt(@PathVariable Long id, @RequestParam("ts") Long ts, @RequestParam("status") Optional<String> status) {
		//PojoInterface pi= projektService.zatvoriOtvoriProjekt(ZatvoriOtvori.ZATVORI, id, ts, status);
		PojoInterface pi=projektServiceImplZatvoriOtvoriProjekt.izvrsi(ZatvoriOtvori.ZATVORI, id, ts, status);
		
		return handlePi(pi);
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/otvoriProjekt/{id}")
	public ResponseEntity<PojoInterface> otvoriProjekt(@PathVariable Long id, @RequestParam("ts") Long ts, @RequestParam("status") Optional<String> status) {
		// PojoInterface pi= projektService.zatvoriOtvoriProjekt(ZatvoriOtvori.OTVORI, id, ts, status);
		PojoInterface pi=projektServiceImplZatvoriOtvoriProjekt.izvrsi(ZatvoriOtvori.OTVORI, id, ts, status);
		
		return handlePi(pi);
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/noviProjekt")
	public ResponseEntity<PojoInterface> noviProjekt(@RequestParam("claim") String claim, @RequestParam("contract") String contract) {		
		//PojoInterface pi= projektService.noviProjekt(claim, contract);
		 PojoInterface pi=projektServiceImplNoviProjekt.izvrsi(claim, contract);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/projektDatalji/{id}")
	public ResponseEntity<PojoInterface> projektDatalji(@PathVariable Long id) {
		//PojoInterface pi= projektDetaljiService.projektDatalji(id);
	    PojoInterface pi=projektDetaljiServiceImplProjektDatalji.izvrsi(id);
		
		return handlePi(pi);
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/urediProjektDatalji/{id}")
	public ResponseEntity<PojoInterface> urediProjektDatalji(@PathVariable Long id, @RequestParam("totalRevenue") String totalRevenue, @RequestParam("costPs") String costPs) {
		PojoInterface pi= projektDetaljiService.urediProjektDatalji(id, totalRevenue, costPs);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/tablicaProjekti")
	public ResponseEntity<PojoInterface> tablicaProjekti(@RequestParam("pageNumber") int pageNumber, @RequestParam("status") Optional<String> status) {
		// PojoInterface pi= projektService.tablicaProjekti(pageNumber, status);
		PojoInterface pi=projektServiceImplTablicaProjekti.izvrsi(status, pageNumber);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/traziProjekt")
	public ResponseEntity<PojoInterface> traziProjekt(@RequestParam("trazi") String trazi) {
		//PojoInterface pi= projektService.traziProjekt(trazi);
		PojoInterface pi=projektServiceImplTraziProjekte.izvrsi(trazi);
		
		return handlePi(pi);
	}

}
