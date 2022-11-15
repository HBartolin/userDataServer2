package hr.kyndryl.bartolin.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class ProjektDetalji implements Serializable {
	private static final long serialVersionUID = -933977500671015407L;
	@Id
	private Long id;
	@Version	
	private Long ts;
	private BigDecimal totalRevenue;
	private BigDecimal costPs;
	private BigDecimal costPlanned;	
	private BigDecimal costActual;	
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_valuta", referencedColumnName="id")
	private SifarnikValuta  sifarnikValuta;
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	@JoinColumn(name = "id")
	private Projekt projekt;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public BigDecimal getCostPs() {
		return costPs;
	}

	public void setCostPs(BigDecimal costPs) {
		this.costPs = costPs;
	}

	public BigDecimal getCostActual() {
		return costActual;
	}

	public void setCostActual(BigDecimal costActual) {
		this.costActual = costActual;
	}

	public BigDecimal getCostPlanned() {
		return costPlanned;
	}

	public void setCostPlanned(BigDecimal costPlanned) {
		this.costPlanned = costPlanned;
	}
	
	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}
	
	public SifarnikValuta getSifarnikValuta() {
		return sifarnikValuta;
	}

	public void setSifarnikValuta(SifarnikValuta sifarnikValuta) {
		this.sifarnikValuta = sifarnikValuta;
	}

}
