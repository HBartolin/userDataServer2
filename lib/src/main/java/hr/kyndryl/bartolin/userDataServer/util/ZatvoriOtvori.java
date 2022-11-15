package hr.kyndryl.bartolin.userDataServer.util;

public class ZatvoriOtvori {
	public static ZatvoriOtvori ZATVORI=new ZatvoriOtvori("Z");
	public static ZatvoriOtvori OTVORI=new ZatvoriOtvori("O");
	private DbStatus dbStatusSuprotni;
	private String msg1;
	private String msg2;
	private String msg3;
	
	private ZatvoriOtvori(String ime) {
		if(ime.equals("Z")) {
			dbStatusSuprotni=DbStatus.N;
			msg1="Projekt %s je već u zatvorenom statusu.";
			msg2="U međuvremenu je podatak osvježen. Pa sam ga i ja osvježio. <b>Nije učinjena akcija Zatvori</b>.";
			msg3="Projekt %s je u zatvorenom statusu.";
		} else if(ime.equals("O")) {
			dbStatusSuprotni=DbStatus.A;
			msg1="Projekt %s je već u otvorenom statusu.";
			msg2="U međuvremenu je podatak osvježen. Pa sam ga i ja osvježio. <b>Nije učinjena akcija Otvori</b>.";
			msg3="Projekt %s je u otvorenom statusu.";
		} else {
			throw new RuntimeException(String.format("Nema imana=%s", ime));
		}
	}

	public DbStatus getDbStatusSuprotni() {
		return dbStatusSuprotni;
	}

	public String getMsg1() {
		return msg1;
	}

	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	public String getMsg2() {
		return msg2;
	}

	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}

	public String getMsg3() {
		return msg3;
	}

	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}
}
