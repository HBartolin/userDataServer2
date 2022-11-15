package hr.bart.userDataServer.util;

import org.apache.poi.ss.util.CellAddress;

public class ReturnPodugovarac {
	private CellAddress planirano=null;
	private CellAddress aktualno=null;
	
	public CellAddress getPlanirano() {
		return planirano;
	}
	
	public void setPlanirano(CellAddress planirano) {
		this.planirano = planirano;
	}

	public CellAddress getAktualno() {
		return aktualno;
	}

	public void setAktualno(CellAddress aktualno) {
		this.aktualno = aktualno;
	}
	
}
