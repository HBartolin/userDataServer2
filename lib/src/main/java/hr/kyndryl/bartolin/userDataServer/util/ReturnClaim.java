package hr.kyndryl.bartolin.userDataServer.util;

import org.apache.poi.ss.util.CellAddress;

public class ReturnClaim {
	private CellAddress aktualno;
	private int iRow;
	private CellAddress planirano=null;
	
	public ReturnClaim(CellAddress aktualno, int iRow) {
		this.aktualno=aktualno;
		this.setiRow(iRow);
	}
	
	public CellAddress getAktualno() {
		return aktualno;
	}

	public int getiRow() {
		return iRow;
	}

	public void setiRow(int iRow) {
		this.iRow = iRow;
	}

	public CellAddress getPlanirano() {
		return planirano;
	}

	public void setPlanirano(CellAddress planirano) {
		this.planirano = planirano;
	}
}
