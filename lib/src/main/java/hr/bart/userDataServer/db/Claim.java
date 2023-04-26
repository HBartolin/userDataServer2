package hr.bart.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
//@JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@claimId", scope = Claim.class )
public class Claim implements Serializable {
	private static final long serialVersionUID = -1497167250229820179L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private BigDecimal osobaClaimPlanned;
	private BigDecimal osobaClaimActual;
	@Version
	private Long ts;
	@ManyToOne
	@JoinColumn(name = "id_projekt_detalji", referencedColumnName="id")
	private ProjektDetalji projektDetalji;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_osoba", referencedColumnName="id")
	private SifarnikOsoba sifarnikOsoba;
//	@OneToMany(mappedBy="claim")
	@Transient
	@JsonIgnoreProperties("claim")
//	@LazyCollection
	private List<OsobaClaimActual> osobaClaimActualList;
//	@OneToMany(mappedBy="claim")
	@Transient
	@JsonIgnoreProperties("claim")
	private List<OsobaClaimPlanned> osobaClaimPlannedList;
	
	public List<OsobaClaimPlanned> getOsobaClaimPlannedList() {
		return osobaClaimPlannedList;
	}

	public void setOsobaClaimPlannedList(List<OsobaClaimPlanned> osobaClaimPlannedList) {
		this.osobaClaimPlannedList = osobaClaimPlannedList;
	}

	public List<OsobaClaimActual> getOsobaClaimActualList() {
		return osobaClaimActualList;
	}

	public void setOsobaClaimActualList(List<OsobaClaimActual> osobaClaimActualList) {
		this.osobaClaimActualList = osobaClaimActualList;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getOsobaClaimPlanned() {
		return osobaClaimPlanned;
	}
	
	public void setOsobaClaimPlanned(BigDecimal osobaClaimPlanned) {
		this.osobaClaimPlanned = osobaClaimPlanned;
	}
	
	public BigDecimal getOsobaClaimActual() {
		return osobaClaimActual;
	}
	
	public void setOsobaClaimActual(BigDecimal osobaClaimActual) {
		this.osobaClaimActual = osobaClaimActual;
	}
	
	public Long getTs() {
		return ts;
	}
	
	public void setTs(Long ts) {
		this.ts = ts;
	}
	
	public ProjektDetalji getProjektDetalji() {
		return projektDetalji;
	}
	
	public void setProjektDetalji(ProjektDetalji projektDetalji) {
		this.projektDetalji = projektDetalji;
	}
	
	public SifarnikOsoba getSifarnikOsoba() {
		return sifarnikOsoba;
	}

	public void setSifarnikOsoba(SifarnikOsoba sifarnikOsoba) {
		this.sifarnikOsoba = sifarnikOsoba;
	}

}
