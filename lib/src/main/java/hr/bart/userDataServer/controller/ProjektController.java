package hr.bart.userDataServer.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.bart.userDataServer.service.ClaimPodugovaracService;
import hr.bart.userDataServer.service.ClaimService;
import hr.bart.userDataServer.service.ExcelService;
import hr.bart.userDataServer.service.OsobaClaimActualService;
import hr.bart.userDataServer.service.OsobaClaimPlannedService;
import hr.bart.userDataServer.service.OsobaValutaService;
import hr.bart.userDataServer.service.PodugovaracService;
import hr.bart.userDataServer.service.SifarnikOsobaService;
import hr.bart.userDataServer.service.SifarnikPodugovaracaService;
import hr.bart.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.bart.userDataServer.util.PojoInterface;

@RestController
@RequestMapping(value = ResponseEntityPojo.REQUEST_MAPPING_VALUE)
@CrossOrigin
public class ProjektController extends ResponseEntityPojo {		
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
	
	@GetMapping(value="/sifarniciOsoba")
	public ResponseEntity<PojoInterface> sifarniciOsoba() {
		PojoInterface pi= sifarnikOsobaService.sifarniciOsoba();
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/editirajSifarnikOsoba") @PutMapping(value="/editirajSifarnikOsoba")
	public ResponseEntity<PojoInterface> editirajSifarnikOsoba(@RequestParam("id") Optional<Long> id, @RequestParam("ime") String ime, @RequestParam("prezime") String prezime) {
		PojoInterface pi= sifarnikOsobaService.editirajSifarnikOsoba(id, ime, prezime);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/tablicaSifarnikOsoba")
	public ResponseEntity<PojoInterface> tablicaSifarnikOsoba(@RequestParam("pageNumber") int pageNumber) {
		PojoInterface pi= sifarnikOsobaService.tablicaSifarnikOsoba(pageNumber);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/osobaValuta")
	public ResponseEntity<PojoInterface> osobaValuta(@RequestParam("idSifarnikOsoba") Long idSifarnikOsoba) {
		PojoInterface pi= osobaValutaService.osobaValuta(idSifarnikOsoba);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/unesiOsobaValuta") @PostMapping(value = "/unesiOsobaValuta")
	public ResponseEntity<PojoInterface> unesiOsobaValuta(@RequestParam("id") Optional<Long> id, @RequestParam("ts") Long ts, @RequestParam("idSifarnikOsoba") Long idSifarnikOsoba, @RequestParam("band") String band, @RequestParam("cijena") BigDecimal cijena, @RequestParam("sifarnikDatumaOd") Optional<String> sifarnikDatumaOd, @RequestParam("sifarnikDatumaDo") Optional<String> sifarnikDatumaDo) {
		PojoInterface pi= osobaValutaService.unesiOsobaValuta(id, ts, idSifarnikOsoba, band, cijena, getParseLD(sifarnikDatumaOd), getParseLD(sifarnikDatumaDo));
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/tablicaOsobaValuta")
	public ResponseEntity<PojoInterface> tablicaOsobaValuta(@RequestParam("pageNumber") int pageNumber, @RequestParam("idSifarnikOsoba") Long idSifarnikOsoba) {
		PojoInterface pi= osobaValutaService.tablicaOsobaValuta(pageNumber, idSifarnikOsoba);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/claim")
	public ResponseEntity<PojoInterface> claim(@RequestParam("idProjektDetalji") Long idProjektDetalji) {
		PojoInterface pi= claimService.tablicaOsobaValuta(idProjektDetalji);
		
		return handlePi(pi);
	}
	
	@PutMapping(value="/claimNewActualByDate") @PostMapping(value = "/claimNewActualByDate")
	public ResponseEntity<PojoInterface> claimNewActualByDate(@RequestBody HashMap<String, String> podatci, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("datum") Optional<String> datum) {
		PojoInterface pi= osobaClaimActualService.claimNewActualByDate(idProjektDetalji, getParseLD(datum), podatci);
		
		return handlePi(pi);
	}
	
	@PutMapping(value="/claimUpdatedActualByDate") 
	public ResponseEntity<PojoInterface> claimUpdatedActualByDate(@RequestBody List<ClaimUpdatedActualPlanned> podatci, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("datum") Optional<String> datum) {
		PojoInterface pi= osobaClaimActualService.claimUpdatedActualByDate(idProjektDetalji, getParseLD(datum), podatci);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/uExcel/{id}")
	public ResponseEntity<PojoInterface> uExcel(@PathVariable Long id) {
		PojoInterface pi= excelService.uExcel(id);
		
		return handlePi(pi);
	}
	
	@PutMapping(value="/claimNewPlannedByDate") @PostMapping(value = "/claimNewPlannedByDate")
	public ResponseEntity<PojoInterface> claimNewPlannedByDate(@RequestBody HashMap<String, String> podatci, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("datum") Optional<String> datum) {
		PojoInterface pi= osobaClaimPlannedService.claimNewPlannedByDate(idProjektDetalji, getParseLD(datum), podatci);
		
		return handlePi(pi);
	}
	
	@PutMapping(value="/claimUpdatedPlannedByDate")
	public ResponseEntity<PojoInterface> claimUpdatedPlannedByDate(@RequestBody List<ClaimUpdatedActualPlanned> podatci, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("datum") Optional<String> datum) {
		PojoInterface pi= osobaClaimPlannedService.claimUpdatedPlannedByDate(idProjektDetalji, getParseLD(datum), podatci);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/urediPodugovarace") @PutMapping(value="/urediPodugovarace")
	public ResponseEntity<PojoInterface> urediPodugovarace() {
		PojoInterface pi= sifarnikPodugovaracaService.urediPodugovarace();
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/editirajSifarnikPodugovaraca") @PutMapping(value="/editirajSifarnikPodugovaraca")
	public ResponseEntity<PojoInterface> editirajSifarnikPodugovaraca(@RequestParam("id") Optional<Long> id, @RequestParam("naziv") Optional<String> naziv) {
		PojoInterface pi= sifarnikPodugovaracaService.editirajSifarnikPodugovaraca(id, naziv);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/podugovaraci")
	public ResponseEntity<PojoInterface> podugovaraci(@RequestParam("idProjektDetalji") Long idProjektDetalji) {
		PojoInterface pi= podugovaracService.podugovaraci(idProjektDetalji);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/sifarnikPodugovaracaSelect")
	public ResponseEntity<PojoInterface> sifarnikPodugovaracaSelect() {
		PojoInterface pi= sifarnikPodugovaracaService.urediPodugovarace();
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/purchaseOrders")
	public ResponseEntity<PojoInterface> purchaseOrders(@RequestParam("idProjektDetalji") Long idProjektDetalji) {
		PojoInterface pi= purchaseOrderService.purchaseOrders(idProjektDetalji);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/unesiPO") @PostMapping(value = "/unesiPO")
	public ResponseEntity<PojoInterface> unesiPO(@RequestParam("id") Optional<Long> id, @RequestParam("ts") Optional<Long> ts, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("idSifarnikPodugovaraca") Long idSifarnikPodugovaraca, @RequestParam("po") String po, @RequestParam("total") Optional<BigDecimal> total) {
		PojoInterface pi= purchaseOrderService.unesiPO(id, ts, idProjektDetalji, idSifarnikPodugovaraca, po, total);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/unesiPodugovarac") @PostMapping(value = "/unesiPodugovarac")
	public ResponseEntity<PojoInterface> unesiPodugovarac(@RequestParam("id") Optional<Long> id, @RequestParam("ts") Long ts, @RequestParam("idProjektDetalji") Long idProjektDetalji, @RequestParam("idPurchaseOrder") Optional<Long> idPurchaseOrder, @RequestParam("datumPlanned") Optional<String> datumPlanned, @RequestParam("datumActual") Optional<String> datumActual, @RequestParam("cijena") Optional<BigDecimal> cijena, @RequestParam("invoiceNumber") Optional<Long> invoiceNumber) {
		PojoInterface pi= podugovaracService.unesiPodugovarac(id, ts, idProjektDetalji, idPurchaseOrder, getParseLD(datumPlanned), getParseLD(datumActual), cijena, invoiceNumber);
		
		return handlePi(pi);
	}
	
	@GetMapping(value="/novaOsoba") @PostMapping(value = "/novaOsoba")
	public ResponseEntity<PojoInterface> novaOsoba() {
		PojoInterface pi= claimService.novaOsoba();
		
		return handlePi(pi);
	}
	
	@PutMapping(value="/claimImena")
	public ResponseEntity<PojoInterface> claimImena(@RequestBody Optional<List<Long>> podatci) {
		PojoInterface pi= claimService.claimImena(podatci);
		
		return handlePi(pi);
	}
	
	private LocalDate getParseLD(Optional<String> txtDate) {
		if(txtDate.isPresent()==false || "".equals(txtDate.get())) {
			return null;
		}
			
		return LocalDate.parse(txtDate.get(), dateFormat);
	}
	
}
