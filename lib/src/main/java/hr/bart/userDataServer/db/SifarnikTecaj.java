package hr.bart.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class SifarnikTecaj implements Serializable {
	private static final long serialVersionUID = -5985722146143047077L;

	@Id
	private Long id;
	private BigDecimal cijena;
	@Version
	private Long ts;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_datuma_od", referencedColumnName="id")
	private SifarnikDatuma sifarnikDatumaOd;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_datuma_do", referencedColumnName="id")
	private SifarnikDatuma sifarnikDatumaDo;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_valuta_iz", referencedColumnName="id")
	private SifarnikValuta sifarnikValutaIz;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_valuta_u", referencedColumnName="id")
	private SifarnikValuta sifarnikValutaU;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getCijena() {
		return cijena;
	}
	
	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}
	
	public Long getTs() {
		return ts;
	}
	
	public void setTs(Long ts) {
		this.ts = ts;
	}
	
	public SifarnikDatuma getSifarnikDatumaOd() {
		return sifarnikDatumaOd;
	}
	
	public void setSifarnikDatumaOd(SifarnikDatuma sifarnikDatumaOd) {
		this.sifarnikDatumaOd = sifarnikDatumaOd;
	}
	
	public SifarnikDatuma getSifarnikDatumaDo() {
		return sifarnikDatumaDo;
	}
	
	public void setSifarnikDatumaDo(SifarnikDatuma sifarnikDatumaDo) {
		this.sifarnikDatumaDo = sifarnikDatumaDo;
	}
	
	public SifarnikValuta getSifarnikValutaIz() {
		return sifarnikValutaIz;
	}
	
	public void setSifarnikValutaIz(SifarnikValuta sifarnikValutaIz) {
		this.sifarnikValutaIz = sifarnikValutaIz;
	}
	
	public SifarnikValuta getSifarnikValutaU() {
		return sifarnikValutaU;
	}
	
	public void setSifarnikValutaU(SifarnikValuta sifarnikValutaU) {
		this.sifarnikValutaU = sifarnikValutaU;
	}
	
}
