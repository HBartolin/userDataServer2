package hr.bart.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class OsobaValuta implements Serializable {
	private static final long serialVersionUID = 600435621699148670L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String band;
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
	@JoinColumn(name = "id_sifarnik_osoba", referencedColumnName="id")
	private SifarnikOsoba sifarnikOsoba;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_valuta", referencedColumnName="id")
	private SifarnikValuta sifarnikValuta;
	
	@Override
	public String toString() {
		return new StringBuffer("[id=").append(id).append(", sifarnikDatumaOd=").append(sifarnikDatumaOd.getDatumPetak()).append(", sifarnikDatumaDo=").append(sifarnikDatumaDo.getDatumPetak()).append("]").toString();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getBand() {
		return band;
	}
	
	public void setBand(String band) {
		this.band = band;
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
	
	public SifarnikOsoba getSifarnikOsoba() {
		return sifarnikOsoba;
	}
	
	public void setSifarnikOsoba(SifarnikOsoba sifarnikOsoba) {
		this.sifarnikOsoba = sifarnikOsoba;
	}
	
	public SifarnikValuta getSifarnikValuta() {
		return sifarnikValuta;
	}
	
	public void setSifarnikValuta(SifarnikValuta sifarnikValuta) {
		this.sifarnikValuta = sifarnikValuta;
	}
	
}
