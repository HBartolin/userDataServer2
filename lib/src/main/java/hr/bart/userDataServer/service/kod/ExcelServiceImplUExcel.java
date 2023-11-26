package hr.bart.userDataServer.service.kod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.OsobaClaimActual;
import hr.bart.userDataServer.db.OsobaClaimPlanned;
import hr.bart.userDataServer.db.OsobaValuta;
import hr.bart.userDataServer.db.Podugovarac;
import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.util.ExcelManipulacija;
import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.ReturnClaim;
import hr.bart.userDataServer.util.ReturnPodugovarac;

public class ExcelServiceImplUExcel extends Kod {
	private final Long id;
	private static final String S_VALUTE="valuta";
	private static final String S_POSTO="posto";
	private static final String S_CENTRIRANO="centrirano";
	private static final String S_OKOMITO="okomito";
	private static final String S_VALUTA_CRVENO="valuta_crveno";
	private static final String TOTAL_REVENUE="Total revenue";
	private static final String CLAIM = "Claim";
	private static final String PODUGOVARACI = "Podugovarači";
	private static final String USER_COST = "User cost";
	private HashMap<String, CellStyle> hmCellStyle=new HashMap<>();
	private SortedSet<LocalDate> datumiSortedSet;
	private Optional<List<Claim>> claimListO;
	private ACommonServis aCommonServis=new ACommonServis(kodRepository);

	public ExcelServiceImplUExcel(KodRepository kodRepository, Long id) {
		super(kodRepository);
		this.id=id;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {		
		ByteArrayOutputStream baos=getByteExcelWriteClose(id);
	    
		String encodedBytes = Base64.getEncoder().encodeToString(baos.toByteArray());

		pi.setRezultat(encodedBytes);
		
		return pi;
	}
	
	private ByteArrayOutputStream getByteExcelWriteClose(Long id) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		Workbook wb=getExcel(id);
		wb.write(baos);
		
	    wb.close();
	    baos.close();
	    
	    return baos;
	}

	private Workbook getExcel(Long id) {
		Workbook wb = new XSSFWorkbook();
		
		setHmCellStyle(wb);
		setTotalRevenue(id, wb);
		ReturnClaim returnClaim=setClaim(id, wb);
		ReturnPodugovarac returnPodugovarac=setPodugovaraci(id, wb);
		setTotalRevenueClaim(wb, returnClaim, returnPodugovarac);
		setUserCost(wb, id);
		
		return wb;
	}

	private ReturnPodugovarac setPodugovaraci(Long idProjektDetalji, Workbook wb) {		
		ReturnPodugovarac returnPodugovarac=new ReturnPodugovarac();
		Sheet sheetPodugovaraci=wb.createSheet(PODUGOVARACI);
		int iRow=0;
		Optional<List<Podugovarac>> podugovaracListO=kodRepository.getPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
		
		if(podugovaracListO.isPresent()) {
			Row row = sheetPodugovaraci.createRow(iRow++);
			
			Cell cell_0=row.createCell(0);
			cell_0.setCellValue("Podugovarač");
			
			Cell cell_1=row.createCell(1);
			cell_1.setCellValue("Datum planirani");
			
			Cell cell_2=row.createCell(2);
			cell_2.setCellValue("Datum aktualni");
			
			Cell cell_3=row.createCell(3);
			cell_3.setCellValue("Cijena [kn]");
			
			Cell cell_4=row.createCell(4);
			cell_4.setCellValue("Invoice number");
			
			Cell cell_5=row.createCell(5);
			cell_5.setCellValue("PO");
			
			CellAddress caPlanirani0=null;
			CellAddress caPlanirani1=null;
			CellAddress caAktualni0=null;
			CellAddress caAktualni1=null;
			CellAddress caCijena0=null;
			CellAddress caCijena1=null;
			
			for(Podugovarac podugovarac: podugovaracListO.get()) {
				row = sheetPodugovaraci.createRow(iRow++);
				
				cell_0=row.createCell(0);
//				cell_0.setCellValue(podugovarac.getPurchaseOrder().getSifarnikPodugovaraca().getNaziv());
				
				cell_1=row.createCell(1);
				cell_1.setCellValue(podugovarac.getDatumPlanned().toString());
//				cell_1.setCellStyle(hmCellStyle.get(S_CENTRIRANO));
				if(caPlanirani0==null) caPlanirani0=cell_1.getAddress();
				caPlanirani1=cell_1.getAddress();
				
				cell_2=row.createCell(2);
				cell_2.setCellValue(podugovarac.getDatumActual()==null ? "" : podugovarac.getDatumActual().toString());
//				cell_2.setCellStyle(hmCellStyle.get(S_CENTRIRANO));
				if(caAktualni0==null) caAktualni0=cell_2.getAddress();
				caAktualni1=cell_2.getAddress();
				
				cell_3=row.createCell(3);
				cell_3.setCellValue(podugovarac.getCijena().doubleValue());
				if(caCijena0==null) caCijena0=cell_3.getAddress();
				caCijena1=cell_3.getAddress();
				
				cell_4=row.createCell(4);
				cell_4.setCellValue(podugovarac.getInvoiceNumber());
				
				cell_5=row.createCell(5);
				cell_5.setCellValue(podugovarac.getClaimPodugovarac().getPo());
			}
			
			row = sheetPodugovaraci.createRow(iRow++);
			
			cell_0=row.createCell(0);
			cell_0.setCellValue("Ukupno:");
			
			cell_1=row.createCell(1);
			cell_1.setCellFormula("SUMIF(" + caPlanirani0 + ":" + caPlanirani1 + ", \"*-*\", " + caCijena0 + ":" + caCijena1 + ")");
			returnPodugovarac.setPlanirano(cell_1.getAddress());
			
			cell_2=row.createCell(2);
			cell_2.setCellFormula("SUMIF(" + caAktualni0 + ":" + caAktualni1 + ", \"*-*\", " + caCijena0 + ":" + caCijena1 + ")");
			returnPodugovarac.setAktualno(cell_2.getAddress());
		}
		
		return returnPodugovarac;
	}

	private void setTotalRevenueClaim(Workbook wb, ReturnClaim returnClaim, ReturnPodugovarac returnPodugovarac) {
		Sheet sheetTotalRevenue=wb.getSheet(TOTAL_REVENUE);
		CellAddress aktualnoCA=returnClaim.getAktualno();
		CellAddress pAktualno=returnPodugovarac.getAktualno();
		
		if(aktualnoCA!=null || pAktualno!=null) {
			Row row1=sheetTotalRevenue.getRow(1);
			
			Cell cell1_7=row1.createCell(7);
			cell1_7.setCellStyle(hmCellStyle.get(S_VALUTE));
			
			if(aktualnoCA!=null && pAktualno!=null) {
				cell1_7.setCellFormula("'" + CLAIM + "'!" + aktualnoCA + " + '" + PODUGOVARACI + "'!" + pAktualno);
			} else if(aktualnoCA!=null) {			
				cell1_7.setCellFormula("'" + CLAIM + "'!" + aktualnoCA);
			} else {
				cell1_7.setCellFormula("'" + PODUGOVARACI + "'!" + pAktualno);
			}
		}
		
		
		CellAddress planiranoCA=returnClaim.getPlanirano();
		CellAddress pPlanirano=returnPodugovarac.getPlanirano();
		
		if(planiranoCA!=null || pPlanirano!=null) {
			Row row1=sheetTotalRevenue.getRow(1);
			
			Cell cell1_6=row1.createCell(6);
			cell1_6.setCellStyle(hmCellStyle.get(S_VALUTE));
			
			if(planiranoCA!=null && pPlanirano!=null) {
				cell1_6.setCellFormula("'" + CLAIM + "'!" + planiranoCA + " + '" + PODUGOVARACI + "'!" + pPlanirano);
			} else if(planiranoCA!=null) {
				cell1_6.setCellFormula("'" + CLAIM + "'!" + planiranoCA);
			} else {
				cell1_6.setCellFormula("'" + PODUGOVARACI + "'!" + pPlanirano);
			}
		}
	}

	private ReturnClaim setClaim(Long id, Workbook wb) {
		Sheet sheetClaim = wb.createSheet(CLAIM);
		
		claimListO=aCommonServis.claimActualPlanned(id);
		
		if(!claimListO.isPresent()) { 
			return null;
		}
		
		List<Claim> claimList=claimListO.get();
		HashMap<LocalDate, LocalDate> datumiHashMap=new HashMap<>();
		
		for(Claim c: claimList) {
			for(OsobaClaimActual oca: c.getOsobaClaimActualList()) {
				datumiHashMap.put(oca.getSifarnikDatuma().getDatumPetak(), oca.getSifarnikDatuma().getMjesec().getMjesec());
			}
		}
		
		datumiSortedSet = new TreeSet<LocalDate>(datumiHashMap.keySet());		
		SortedSet<LocalDate> mjesecSortedSet = new TreeSet<LocalDate>(datumiHashMap.values());	
		HashMap<String, List<CellAddress>> sumarniMjeseciHashMap=new HashMap<>();
		 
		for(Claim c: claimList) {
			for(LocalDate ld: mjesecSortedSet) {
				sumarniMjeseciHashMap.put(c.getId().toString() + ld, new ArrayList<CellAddress>());
			}
		}
		
		HashMap<LocalDate, LocalDate> datumiPlaniraniHashMap=new HashMap<>();
		
		for(Claim c: claimList) {
			for(OsobaClaimPlanned ocp: c.getOsobaClaimPlannedList()) {
				datumiPlaniraniHashMap.put(ocp.getSifarnikMjeseca().getMjesec(), ocp.getSifarnikMjeseca().getMjesec());
			}
		}
		
		SortedSet<LocalDate> datumiPlaniraniSortedSet = new TreeSet<LocalDate>(datumiPlaniraniHashMap.keySet());
		
		int iRow = getClaimActuall1(sheetClaim, 0, claimList, sumarniMjeseciHashMap);
		ReturnClaim returnClaim = getClaimActuall2(sheetClaim, iRow, mjesecSortedSet, claimList, sumarniMjeseciHashMap);	
		returnClaim = getClaimPlanned(sheetClaim, datumiPlaniraniSortedSet, returnClaim, claimList);
		
		return returnClaim;
	}
	
	private ReturnClaim getClaimPlanned(Sheet sheetClaim, SortedSet<LocalDate> datumiPlaniraniSortedSet, ReturnClaim returnClaim, List<Claim> claimList) {
		int iRow=returnClaim.getiRow();
		iRow++;
		int iCell2=1;
		
		Row rowZ = sheetClaim.createRow(iRow++);
		Cell cellZ_0=rowZ.createCell(iCell2++);
		cellZ_0.setCellValue("Osoba");
		
		for(LocalDate ld: datumiPlaniraniSortedSet) {
			Cell cellZ_n=rowZ.createCell(iCell2++);
			cellZ_n.setCellValue(ld.toString());
			cellZ_n.setCellStyle(hmCellStyle.get(S_CENTRIRANO));
			sheetClaim.setColumnWidth(iCell2-1, 3000);
		}
		
		cellZ_0=rowZ.createCell(iCell2++);
		cellZ_0.setCellValue("Sum [kn]");
		
		int startRow=iRow-1;
		List<CellAddress> skupnoPlannedList=new ArrayList<>();
		CellAddress maxCA=null;
		
		for(Claim c: claimList) {
			Row row0 = sheetClaim.createRow(iRow++);
			int iCell=1;
			CellAddress minCA=null;
			
			Cell cell0_0=row0.createCell(iCell++);
			cell0_0.setCellValue(c.getSifarnikOsoba().getPrezimeIme());
			
			for(LocalDate ld: datumiPlaniraniSortedSet) {
				boolean ima=false;
				
				for(OsobaClaimPlanned ocp: c.getOsobaClaimPlannedList()) {
					if(ocp.getSifarnikMjeseca().getMjesec().isEqual(ld)) {
						ima=true;
						
						Cell cell0_n=row0.createCell(iCell++);
						cell0_n.setCellValue(ocp.getSati().doubleValue());
						
						if(minCA==null) minCA=cell0_n.getAddress();
						maxCA=cell0_n.getAddress();
					}
				}
				
				if(!ima) {
					iCell++;
				}
			}
			
			cell0_0=row0.createCell(iCell++);
			cell0_0.setCellFormula("SUM(" + minCA + ":" + maxCA + ")");
			cell0_0.setCellStyle(hmCellStyle.get(S_VALUTE));
			sheetClaim.setColumnWidth(iCell-1, 4000);
			skupnoPlannedList.add(cell0_0.getAddress());
		}
		
		rowZ = sheetClaim.createRow(iRow++);
		cellZ_0=rowZ.createCell(maxCA.getColumn());
		cellZ_0.setCellValue("Ukupno");
		
		String sum="";
		
		for(CellAddress ca: skupnoPlannedList) {
			if(!sum.isEmpty()) sum += " + ";
			sum += ca;
		}
		
		cellZ_0=rowZ.createCell(maxCA.getColumn()+1);
		cellZ_0.setCellFormula(sum);
		cellZ_0.setCellStyle(hmCellStyle.get(S_VALUTE));
		CellAddress plannedCA=cellZ_0.getAddress();
		
		sheetClaim.addMergedRegion(new CellRangeAddress(startRow, iRow-2, 0, 0));
		rowZ = sheetClaim.getRow(startRow);
		cellZ_0=rowZ.createCell(0);
		cellZ_0.setCellValue("PLANNED");
		cellZ_0.setCellStyle(hmCellStyle.get(S_OKOMITO));
		sheetClaim.setColumnWidth(0, 1000);
		
		returnClaim.setiRow(iRow);
		returnClaim.setPlanirano(plannedCA);
		
		return returnClaim;
	}
	
	private ReturnClaim getClaimActuall2(Sheet sheetClaim, int iRow, SortedSet<LocalDate> mjesecSortedSet, List<Claim> claimList, HashMap<String, List<CellAddress>> sumarniMjeseciHashMap) {
		iRow++;
		int iCell2=1;
		
		Row rowZ = sheetClaim.createRow(iRow++);
		Cell cellZ_0=rowZ.createCell(iCell2++);
		cellZ_0.setCellValue("Osoba");
		
		for(LocalDate ld: mjesecSortedSet) {
			Cell cellZ_n=rowZ.createCell(iCell2++);
			cellZ_n.setCellValue(ld.toString());
			sheetClaim.setColumnWidth(iCell2-1, 3000);
		}
		
		cellZ_0=rowZ.createCell(iCell2++);
		cellZ_0.setCellValue("Sum [kn]");
		
		int startRow=iRow-1;
		CellAddress maxCA=null;
		List<CellAddress> skupnoActualList=new ArrayList<>();
		
		for(Claim c: claimList) {
			Row row0 = sheetClaim.createRow(iRow++);
			int iCell=1;
			CellAddress minCA=null;
			
			Cell cell0_0=row0.createCell(iCell++);
			cell0_0.setCellValue(c.getSifarnikOsoba().getPrezimeIme());
			
			for(LocalDate ld: mjesecSortedSet) {
				for(String i: sumarniMjeseciHashMap.keySet()) {
					if(i.equals(c.getId().toString() + ld)) {
						List<CellAddress> caList=sumarniMjeseciHashMap.get(i);
						String sum="";
						
						for(CellAddress ca: caList) {
							if(!sum.isEmpty()) sum += " + ";
							sum += ca;
						}
						
						cell0_0=row0.createCell(iCell++);
						cell0_0.setCellFormula(sum);
						cell0_0.setCellStyle(hmCellStyle.get(S_VALUTE));
						
						if(minCA==null) minCA=cell0_0.getAddress();
						maxCA=cell0_0.getAddress();
					}
				}
			}
			
			cell0_0=row0.createCell(iCell++);
			cell0_0.setCellFormula("SUM(" + minCA + ":" + maxCA + ")");
			cell0_0.setCellStyle(hmCellStyle.get(S_VALUTE));
			sheetClaim.setColumnWidth(iCell-1, 4000);
			skupnoActualList.add(cell0_0.getAddress());
		}
		
		rowZ = sheetClaim.createRow(iRow++);
		cellZ_0=rowZ.createCell(maxCA.getColumn());
		cellZ_0.setCellValue("Ukupno");
		
		String sum="";
		
		for(CellAddress ca: skupnoActualList) {
			if(!sum.isEmpty()) sum += " + ";
			sum += ca;
		}
		
		cellZ_0=rowZ.createCell(maxCA.getColumn()+1);
		cellZ_0.setCellFormula(sum);
		cellZ_0.setCellStyle(hmCellStyle.get(S_VALUTE));
		CellAddress aktualnoCA=cellZ_0.getAddress();
		
		sheetClaim.addMergedRegion(new CellRangeAddress(startRow, iRow-2, 0, 0));
		rowZ = sheetClaim.getRow(startRow);
		cellZ_0=rowZ.createCell(0);
		cellZ_0.setCellValue("ACTUAL");
		cellZ_0.setCellStyle(hmCellStyle.get(S_OKOMITO));
		sheetClaim.setColumnWidth(0, 1000);
		
		return new ReturnClaim(aktualnoCA, iRow);
	}

	private int getClaimActuall1(Sheet sheetClaim, int iRow, List<Claim> claimList, HashMap<String, List<CellAddress>> sumarniMjeseciHashMap) {
		int iCell2=1;
		Row rowZ = sheetClaim.createRow(iRow++);
		Cell cellZ_0=rowZ.createCell(iCell2++);
		cellZ_0.setCellValue("Osoba");
		
		for(LocalDate ld: datumiSortedSet) {
			Cell cellZ_n=rowZ.createCell(iCell2++);
			cellZ_n.setCellValue(ld.toString());
			cellZ_n.setCellStyle(hmCellStyle.get(S_CENTRIRANO));
			
			sheetClaim.addMergedRegion(new CellRangeAddress(iRow-1, iRow-1, iCell2-1, iCell2));
			iCell2++;
		}
		
		int startRow=iRow-1;
		
		for(Claim c: claimList) {
			Row row0 = sheetClaim.createRow(iRow++);
			int iCell=1;
			
			Cell cell0_0=row0.createCell(iCell++);
			cell0_0.setCellValue(c.getSifarnikOsoba().getPrezimeIme());
			sheetClaim.setColumnWidth(iCell-1, 4000);
			
			for(LocalDate ld: datumiSortedSet) {
				boolean ima=false;
				
				for(OsobaClaimActual oca: c.getOsobaClaimActualList()) {
					if(oca.getSifarnikDatuma().getDatumPetak().isEqual(ld)) {
						ima=true;
						
						Cell cell0_n=row0.createCell(iCell++);
						cell0_n.setCellValue(oca.getSati().doubleValue());
						
						cell0_n=row0.createCell(iCell++);
						cell0_n.setCellValue(oca.getCijena().doubleValue());
						cell0_n.setCellStyle(hmCellStyle.get(S_VALUTE));
						
						for(String i: sumarniMjeseciHashMap.keySet()) {
							if(i.equals(c.getId().toString() + oca.getSifarnikDatuma().getMjesec().getMjesec())) {
								sumarniMjeseciHashMap.get(i).add(cell0_n.getAddress());
							}
						}
					}
				}
				
				if(!ima) {
					iCell++;
					iCell++;
				}
			}
		}
		
		sheetClaim.addMergedRegion(new CellRangeAddress(startRow, iRow-1, 0, 0));
		rowZ = sheetClaim.getRow(startRow);
		cellZ_0=rowZ.createCell(0);
		cellZ_0.setCellValue("ACTUAL");
		cellZ_0.setCellStyle(hmCellStyle.get(S_OKOMITO));
		sheetClaim.setColumnWidth(0, 1000);
		
		return iRow;
	}

	private void setUserCost(Workbook wb, Long id) { 		
		List<Long> idSifarnikOsobaList=new ArrayList<>();
		Sheet sheetUserCost = wb.createSheet(USER_COST);
		Long prethodnoIme=null;
		List<ExcelManipulacija> excelManipulacijaList=new ArrayList<>();
		HashMap<Long, CellAddress> idCijenaHashMap=new HashMap<>();
		
		if(!claimListO.isPresent()) {
			return;
		}
		
		List<Claim> claimList=claimListO.get();
		
		for(Claim c: claimList) {
		 	idSifarnikOsobaList.add(c.getSifarnikOsoba().getId());
		}
			
		if(idSifarnikOsobaList.size()==0) {
			return;
		}
		
		Optional<List<OsobaValuta>> osobaValutaListO=kodRepository.getOsobaValutaRepository().findAllByIdSifarnikOsobaList(idSifarnikOsobaList);
		
		if(!osobaValutaListO.isPresent()) {
			return;
		}
		
		List<OsobaValuta> osobaValutaList=osobaValutaListO.get();
		int iRow=0;
		Row rowZ = sheetUserCost.createRow(iRow++);
		Cell cellZ_0=rowZ.createCell(0);
		cellZ_0.setCellValue("Osoba");
		
		Cell cellZ_1=rowZ.createCell(1);
		cellZ_1.setCellValue("Datum od");
		
		Cell cellZ_2=rowZ.createCell(2);
		cellZ_2.setCellValue("Datum do");
		
		Cell cellZ_3=rowZ.createCell(3);
		cellZ_3.setCellValue("Cijena [kn]");
		
		for(OsobaValuta ov: osobaValutaList) {
			Row row0 = sheetUserCost.createRow(iRow); 
			
			if(prethodnoIme==null || !ov.getSifarnikOsoba().getId().equals(prethodnoIme)) {
				if(prethodnoIme!=null) {
					row0 = sheetUserCost.createRow(++iRow);
				}
				
				Cell cell0_0=row0.createCell(0);
				cell0_0.setCellValue(ov.getSifarnikOsoba().getPrezimeIme());
				sheetUserCost.setColumnWidth(0, 4000);
			}
			
			Cell cell0_1=row0.createCell(1);
			cell0_1.setCellValue(ov.getSifarnikDatumaOd().getDatumPetak().toString());
			sheetUserCost.setColumnWidth(1, 3000);
			
			Cell cell0_2=row0.createCell(2);
			cell0_2.setCellValue(ov.getSifarnikDatumaDo().getDatumPetak().toString());
			sheetUserCost.setColumnWidth(2, 3000);
			
			Cell cell0_3=row0.createCell(3);
			cell0_3.setCellValue(ov.getCijena().doubleValue());
			cell0_3.setCellStyle(hmCellStyle.get(S_VALUTE));
			sheetUserCost.setColumnWidth(3, 3000);
			idCijenaHashMap.put(ov.getId(), cell0_3.getAddress());
			
			excelManipulacijaList.add(new ExcelManipulacija(iRow+1, ov.getId()));
			
			prethodnoIme=ov.getSifarnikOsoba().getId();
			iRow++;
		}
		
		iRow++;
		int iCell2=1;
		
		rowZ = sheetUserCost.createRow(iRow++);
		cellZ_0=rowZ.createCell(iCell2++);
		cellZ_0.setCellValue("Osoba");
		
		for(LocalDate ld: datumiSortedSet) {
			Cell cellZ_n=rowZ.createCell(iCell2++);
			cellZ_n.setCellValue(ld.toString());
			cellZ_n.setCellStyle(hmCellStyle.get(S_CENTRIRANO));
			
			sheetUserCost.addMergedRegion(new CellRangeAddress(iRow-1, iRow-1, iCell2-1, iCell2));
			iCell2++;
		}
		
		int startRow=iRow-1;
		
		for(Claim c: claimList) {
			Row row0 = sheetUserCost.createRow(iRow++);
			int iCell=1;
			
			Cell cell0_0=row0.createCell(iCell++);
			cell0_0.setCellValue(c.getSifarnikOsoba().getPrezimeIme());
			sheetUserCost.setColumnWidth(iCell-1, 4000);
			
			for(LocalDate ld: datumiSortedSet) {
				boolean ima=false;
				
				for(OsobaClaimActual oca: c.getOsobaClaimActualList()) {
					if(oca.getSifarnikDatuma().getDatumPetak().isEqual(ld)) {
						ima=true;
						
						Cell cell0_n=row0.createCell(iCell++);
						cell0_n.setCellValue(oca.getCijena().doubleValue());
						cell0_n.setCellStyle(hmCellStyle.get(S_VALUTE));
						
						for(Long idCijene: idCijenaHashMap.keySet()) {
							if(idCijene.equals(oca.getOsobaValuta().getId())) {
								Cell cell0_m=row0.createCell(iCell++);
								cell0_m.setCellFormula(oca.getSati() + " * " + idCijenaHashMap.get(idCijene));
								cell0_m.setCellStyle(hmCellStyle.get(S_VALUTE));
								
								if(oca.getCijena().equals(oca.getSati().multiply(oca.getOsobaValuta().getCijena()))) {
									cell0_n.setCellStyle(hmCellStyle.get(S_VALUTA_CRVENO));
									cell0_m.setCellStyle(hmCellStyle.get(S_VALUTA_CRVENO));
								} 
							}
						}
					}
				}
				
				if(!ima) {
					iCell++;
					iCell++;
				}
			}
		}
		
		sheetUserCost.addMergedRegion(new CellRangeAddress(startRow, iRow-1, 0, 0));
		rowZ = sheetUserCost.getRow(startRow);
		cellZ_0=rowZ.createCell(0);
		cellZ_0.setCellValue("ACTUAL");
		cellZ_0.setCellStyle(hmCellStyle.get(S_OKOMITO));
	}

	private void setHmCellStyle(Workbook wb) {
		DataFormat format = wb.createDataFormat();
		Font font = wb.createFont();
		
		CellStyle styleValuta = wb.createCellStyle();
	    styleValuta.setDataFormat(format.getFormat("#,##0.00"));
	    hmCellStyle.put(S_VALUTE, styleValuta);
	    
	    CellStyle stylePosto=wb.createCellStyle();
	    stylePosto.setDataFormat(format.getFormat("0.00%"));
	    hmCellStyle.put(S_POSTO, stylePosto);
	    
	    CellStyle styleCentrirano = wb.createCellStyle();
	    styleCentrirano.setAlignment(HorizontalAlignment.CENTER);
	    hmCellStyle.put(S_CENTRIRANO, styleCentrirano);
	    
	    CellStyle styleOkomito = wb.createCellStyle();
	    styleOkomito.setRotation((short) 90);
	    styleOkomito.setVerticalAlignment(VerticalAlignment.CENTER);
	    hmCellStyle.put(S_OKOMITO, styleOkomito);
	    
	    CellStyle styleCrveno = wb.createCellStyle();
	    styleCrveno.setDataFormat(format.getFormat("#,##0.00"));
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        styleCrveno.setFont(font);
        hmCellStyle.put(S_VALUTA_CRVENO, styleCrveno);
	}

	private void setTotalRevenue(Long id, Workbook wb) {		
		Sheet sheetTotalRevenue = wb.createSheet(TOTAL_REVENUE);
		
		Optional<ProjektDetalji> projektDetaljiO=kodRepository.getProjektDetaljiRepository().findById(id);
		
		if(!projektDetaljiO.isPresent()) {
			return;
		}
		
		ProjektDetalji projektDetalji=projektDetaljiO.get();
		
		Row row0 = sheetTotalRevenue.createRow(0); 
		Cell cell0_0=row0.createCell(0);
	    cell0_0.setCellValue("Claim");
	    Cell cell0_1=row0.createCell(1);
	    cell0_1.setCellValue(projektDetalji.getProjekt().getClaim());
	    Cell cell0_5=row0.createCell(5);
	    cell0_5.setCellValue("Pricing sheet");
	    Cell cell0_6=row0.createCell(6);
	    cell0_6.setCellValue("Planned");
	    Cell cell0_7=row0.createCell(7);
	    cell0_7.setCellValue("Actual");
	    
	    Row row1 = sheetTotalRevenue.createRow(1);
	    Cell cell1_0=row1.createCell(0);
	    cell1_0.setCellValue("Contract");
	    Cell cell1_1=row1.createCell(1);
	    cell1_1.setCellValue(projektDetalji.getProjekt().getContract());
	    Cell cell1_4=row1.createCell(4);
	    cell1_4.setCellValue("Cost");
	    Cell cell1_5=row1.createCell(5);
	    cell1_5.setCellValue(projektDetalji.getCostPs().doubleValue());
	    cell1_5.setCellStyle(hmCellStyle.get(S_VALUTE));
	    
	    Row row2 = sheetTotalRevenue.createRow(2);
	    Cell cell2_4=row2.createCell(4);
	    cell2_4.setCellValue("GP");
	    Cell cell2_5=row2.createCell(5);
	    cell2_5.setCellFormula("$B$4-F2");
	    cell2_5.setCellStyle(hmCellStyle.get(S_VALUTE));
	    Cell cell2_6=row2.createCell(6);
	    cell2_6.setCellFormula("$B$4-G2");
	    cell2_6.setCellStyle(hmCellStyle.get(S_VALUTE));
	    Cell cell2_7=row2.createCell(7);
	    cell2_7.setCellFormula("$B$4-H2");
	    cell2_7.setCellStyle(hmCellStyle.get(S_VALUTE));
	    
	    Row row3 = sheetTotalRevenue.createRow(3);
	    Cell cell3_0=row3.createCell(0);
	    cell3_0.setCellValue("Total revenue");
	    Cell cell3_1=row3.createCell(1);
	    cell3_1.setCellValue(projektDetalji.getTotalRevenue().doubleValue());   
	    cell3_1.setCellStyle(hmCellStyle.get(S_VALUTE));
	    Cell cell3_4=row3.createCell(4);
	    cell3_4.setCellValue("GP %");
	    Cell cell3_5=row3.createCell(5);
	    cell3_5.setCellFormula("F3/$B$4");
	    cell3_5.setCellStyle(hmCellStyle.get(S_POSTO));
	    Cell cell3_6=row3.createCell(6);
	    cell3_6.setCellFormula("G3/$B$4");
	    cell3_6.setCellStyle(hmCellStyle.get(S_POSTO));
	    Cell cell3_7=row3.createCell(7);
	    cell3_7.setCellFormula("H3/$B$4");
	    cell3_7.setCellStyle(hmCellStyle.get(S_POSTO));
	    	    
	    sheetTotalRevenue.setColumnWidth(0, 3500);
	    sheetTotalRevenue.setColumnWidth(1, 3500);
	    sheetTotalRevenue.setColumnWidth(5, 3500);
	    sheetTotalRevenue.setColumnWidth(6, 3000);
	    sheetTotalRevenue.setColumnWidth(7, 3000);
	}

}
