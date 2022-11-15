package hr.kyndryl.bartolin.userDataServer.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.kyndryl.bartolin.userDataServer.service.ClaimPodugovaracService;
import hr.kyndryl.bartolin.userDataServer.service.ClaimService;
import hr.kyndryl.bartolin.userDataServer.service.ExcelService;
import hr.kyndryl.bartolin.userDataServer.service.OsobaClaimActualService;
import hr.kyndryl.bartolin.userDataServer.service.OsobaClaimPlannedService;
import hr.kyndryl.bartolin.userDataServer.service.OsobaValutaService;
import hr.kyndryl.bartolin.userDataServer.service.PodugovaracService;
import hr.kyndryl.bartolin.userDataServer.service.ProjektDetaljiService;
import hr.kyndryl.bartolin.userDataServer.service.ProjektService;
import hr.kyndryl.bartolin.userDataServer.service.RestService;
import hr.kyndryl.bartolin.userDataServer.service.SifarnikOsobaService;
import hr.kyndryl.bartolin.userDataServer.service.SifarnikPodugovaracaService;
import hr.kyndryl.bartolin.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;
import hr.kyndryl.bartolin.userDataServer.util.ZatvoriOtvori;

@RestController
@RequestMapping(value ="/api", method= RequestMethod.PUT /*, RequestMethod.GET, RequestMethod.POST}*/)
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*")
public class ProjektController {
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("y-M-d");
	
	@Autowired
	private transient ProjektService projektService;  
	@Autowired
	private transient ProjektDetaljiService projektDetaljiService;
	@Autowired
	private transient SifarnikOsobaService sifarnikOsobaService;
	@Autowired
	private transient OsobaValutaService osobaValutaService;
	@Autowired
	private transient ClaimService claimService;
	@Autowired
	private transient OsobaClaimActualService osobaClaimActualService;
	@Autowired
	private transient OsobaClaimPlannedService osobaClaimPlannedService;
	@Autowired
	private transient ExcelService excelService;
	@Autowired
	private transient SifarnikPodugovaracaService sifarnikPodugovaracaService;
	@Autowired
	private transient PodugovaracService podugovaracService;
	@Autowired
	private transient ClaimPodugovaracService purchaseOrderService;
	@Autowired
	private transient RestService restService;
	
	@GetMapping(value="/createDB") @PostMapping(value = "/createDB")
	public PojoInterface createDB() {		
		return restService.createDB();
	}
	
	@GetMapping(value = "/projekti")
    public PojoInterface projekti(@RequestParam("status") Optional<String> status) {		
		return projektService.projekti(status);
    }	
	
	@GetMapping(value="/zatvoriProjekt/{id}") @PutMapping(value="/zatvoriProjekt/{id}")
	public PojoInterface zatvoriProjekt(@PathVariable Long id, @RequestParam("ts") Long ts, @RequestParam("status") Optional<String> status) {
		return projektService.zatvoriOtvoriProjekt(ZatvoriOtvori.ZATVORI, id, ts, status);
	}
	
	@GetMapping(value="/otvoriProjekt/{id}") @PutMapping(value="/otvoriProjekt/{id}")
	public PojoInterface otvoriProjekt(@PathVariable Long id, @RequestParam("ts") Long ts, @RequestParam("status") Optional<String> status) {
		return projektService.zatvoriOtvoriProjekt(ZatvoriOtvori.OTVORI, id, ts, status);
	}
	
	@GetMapping(value="/noviProjekt") @PostMapping(value = "/noviProjekt")
	public PojoInterface noviProjekt(@RequestParam("claim") String claim, @RequestParam("contract") String contract) {		
		return projektService.noviProjekt(claim, contract);
	}
	
	@GetMapping(value="/projektDatalji/{id}")
	public PojoInterface projektDatalji(@PathVariable Long id) {
		return projektDetaljiService.projektDatalji(id);
	}
	
	@GetMapping(value="/urediProjektDatalji/{id}") @PutMapping(value="/urediProjektDatalji/{id}")
	public PojoInterface urediProjektDatalji(@PathVariable Long id, @RequestParam("totalRevenue") String totalRevenue, @RequestParam("costPs") String costPs) {
		return projektDetaljiService.urediProjektDatalji(id, totalRevenue, costPs);
	}
	
	@GetMapping(value="/tablicaProjekti")
	public PojoInterface tablicaProjekti(@RequestParam("pageNumber") int pageNumber, @RequestParam("status") Optional<String> status) {
		return projektService.tablicaProjekti(pageNumber, status);
	}
	
	@GetMapping(value="/traziProjekt")
	public PojoInterface traziProjekt(@RequestParam("trazi") String trazi) {
		return projektService.traziProjekt(trazi);
	}
	
	@GetMapping(value="/sifarniciOsoba")
	public PojoInterface sifarniciOsoba() {
		return sifarnikOsobaService.sifarniciOsoba();
	}
	
	@GetMapping(value="/editirajSifarnikOsoba") @PutMapping(value="/editirajSifarnikOsoba")
	public PojoInterface editirajSifarnikOsoba(@RequestParam("id") Optional<Long> id, @RequestParam("ime") String ime, @RequestParam("prezime") String prezime) {
		return sifarnikOsobaService.editirajSifarnikOsoba(id, ime, prezime);
	}
	
	@GetMapping(value="/tablicaSifarnikOsoba")
	public PojoInterface tablicaSifarnikOsoba(@RequestParam("pageNumber") int pageNumber) {
		return sifarnikOsobaService.tablicaSifarnikOsoba(pageNumber);
	}
	
	@GetMapping(value="/osobaValuta")
	public PojoInterface osobaValuta(@RequestParam("idSifarnikOsoba") Long idSifarnikOsoba) {
		return osobaValutaService.osobaValuta(idSifarnikOsoba);
	}
	
	@GetMapping(value="/unesiOsobaValuta") @PostMapping(value = "/unesiOsobaValuta")
	public PojoInterface unesiOsobaValuta(@RequestParam("id") Optional<Long> id, @RequestParam("ts") Long ts, @RequestParam("idSifarnikOsoba") Long idSifarnikOsoba, @RequestParam("band") String band, @RequestParam("cijena") BigDecimal cijena, @RequestParam("sifarnikDatumaOd") Optional<String> sifarnikDatumaOd, @RequestParam("sifarnikDatumaDo") Optional<String> sifarnikDatumaDo) {
		return osobaValutaService.unesiOsobaValuta(id, ts, idSifarnikOsoba, band, cijena, getParseLD(sifarnikDatumaOd), getParseLD(sifarnikDatumaDo));
	}
	
	@GetMapping(value="/tablicaOsobaValuta")
	public PojoInterface tablicaOsobaValuta(@RequestParam("pageNumber") int pageNumber, @RequestParam("idSifarnikOsoba") Long idSifarnikOsoba) {
		return osobaValutaService.tablicaOsobaValuta(pageNumber, idSifarnikOsoba);
	}
	
	@GetMapping(value="/claim")
	public PojoInterface claim(@RequestParam("idProjektDetalji") Long idProjektDetalji) {
		return claimService.tablicaOsobaValuta(idProjektDetalji);
	}
	
	@PutMapping(value="/claimNewActualByDate") @PostMapping(value = "/claimNewActualByDate")
	public PojoInterface claimNewActualByDate(@RequestBody HashMap<String, String> podatci, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("datum") Optional<String> datum) {
		return osobaClaimActualService.claimNewActualByDate(idProjektDetalji, getParseLD(datum), podatci);
	}
	
	@PutMapping(value="/claimUpdatedActualByDate") 
	public PojoInterface claimUpdatedActualByDate(@RequestBody List<ClaimUpdatedActualPlanned> podatci, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("datum") Optional<String> datum) {
		return osobaClaimActualService.claimUpdatedActualByDate(idProjektDetalji, getParseLD(datum), podatci);
	}
	
	@GetMapping(value="/uExcel/{id}")
	public PojoInterface uExcel(@PathVariable Long id) {
		return excelService.uExcel(id);
	}
	
	@PutMapping(value="/claimNewPlannedByDate") @PostMapping(value = "/claimNewPlannedByDate")
	public PojoInterface claimNewPlannedByDate(@RequestBody HashMap<String, String> podatci, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("datum") Optional<String> datum) {
		return osobaClaimPlannedService.claimNewPlannedByDate(idProjektDetalji, getParseLD(datum), podatci);
	}
	
	@PutMapping(value="/claimUpdatedPlannedByDate")
	public PojoInterface claimUpdatedPlannedByDate(@RequestBody List<ClaimUpdatedActualPlanned> podatci, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("datum") Optional<String> datum) {
		return osobaClaimPlannedService.claimUpdatedPlannedByDate(idProjektDetalji, getParseLD(datum), podatci);
	}
	
	@GetMapping(value="/urediPodugovarace") @PutMapping(value="/urediPodugovarace")
	public PojoInterface urediPodugovarace() {
		return sifarnikPodugovaracaService.urediPodugovarace();
	}
	
	@GetMapping(value="/editirajSifarnikPodugovaraca") @PutMapping(value="/editirajSifarnikPodugovaraca")
	public PojoInterface editirajSifarnikPodugovaraca(@RequestParam("id") Optional<Long> id, @RequestParam("naziv") Optional<String> naziv) {
		return sifarnikPodugovaracaService.editirajSifarnikPodugovaraca(id, naziv);
	}
	
	@GetMapping(value="/podugovaraci")
	public PojoInterface podugovaraci(@RequestParam("idProjektDetalji") Long idProjektDetalji) {
		return podugovaracService.podugovaraci(idProjektDetalji);
	}
	
	@GetMapping(value="/sifarnikPodugovaracaSelect")
	public PojoInterface sifarnikPodugovaracaSelect() {
		return sifarnikPodugovaracaService.urediPodugovarace();
	}
	
	@GetMapping(value="/purchaseOrders")
	public PojoInterface purchaseOrders(@RequestParam("idProjektDetalji") Long idProjektDetalji) {
		return purchaseOrderService.purchaseOrders(idProjektDetalji);
	}
	
	@GetMapping(value="/unesiPO") @PostMapping(value = "/unesiPO")
	public PojoInterface unesiPO(@RequestParam("id") Optional<Long> id, @RequestParam("ts") Optional<Long> ts, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("idSifarnikPodugovaraca") Long idSifarnikPodugovaraca, @RequestParam("po") String po, @RequestParam("total") Optional<BigDecimal> total) {
		return purchaseOrderService.unesiPO(id, ts, idProjektDetalji, idSifarnikPodugovaraca, po, total);
	}
	
	@GetMapping(value="/unesiPodugovarac") @PostMapping(value = "/unesiPodugovarac")
	public PojoInterface unesiPodugovarac(@RequestParam("id") Optional<Long> id, @RequestParam("ts") Long ts, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("idPurchaseOrder") Optional<Long> idPurchaseOrder, @RequestParam("datumPlanned") Optional<String> datumPlanned, @RequestParam("datumActual") Optional<String> datumActual, @RequestParam("cijena") Optional<BigDecimal> cijena, @RequestParam("invoiceNumber") Optional<Long> invoiceNumber) {
		return podugovaracService.unesiPodugovarac(id, ts, idProjektDetalji, idPurchaseOrder, getParseLD(datumPlanned), getParseLD(datumActual), cijena, invoiceNumber);
	}
	
	@GetMapping(value="/novaOsoba") @PostMapping(value = "/novaOsoba")
	public PojoInterface novaOsoba() {
		return claimService.novaOsoba();
	}
	
	@PutMapping(value="/claimImena")
	public PojoInterface claimImena(@RequestBody Optional<List<Long>> podatci) {
		return claimService.claimImena(podatci);
	}
	
	private LocalDate getParseLD(Optional<String> txtDate) {
		if(txtDate.isPresent()==false || "".equals(txtDate.get())) {
			return null;
		}
			
		return LocalDate.parse(txtDate.get(), dateFormat);
	}
}
