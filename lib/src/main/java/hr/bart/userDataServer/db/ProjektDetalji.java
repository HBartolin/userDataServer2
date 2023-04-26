package hr.bart.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

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
