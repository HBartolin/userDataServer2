package hr.kyndryl.bartolin.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class ClaimPodugovarac implements Serializable {
	private static final long serialVersionUID = 4687697201452573954L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Version
	private Long ts;
	private String po;
	private BigDecimal total;
	private BigDecimal planned;
	private BigDecimal actual;
	@ManyToOne
	@JoinColumn(name = "id_projekt_detalji", referencedColumnName="id")
	private ProjektDetalji projektDetalji;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_podugovaraca", referencedColumnName="id")
	private SifarnikPodugovaraca sifarnikPodugovaraca;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public ProjektDetalji getProjektDetalji() {
		return projektDetalji;
	}

	public void setProjektDetalji(ProjektDetalji projektDetalji) {
		this.projektDetalji = projektDetalji;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public SifarnikPodugovaraca getSifarnikPodugovaraca() {
		return sifarnikPodugovaraca;
	}

	public void setSifarnikPodugovaraca(SifarnikPodugovaraca sifarnikPodugovaraca) {
		this.sifarnikPodugovaraca = sifarnikPodugovaraca;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getPlanned() {
		return planned;
	}

	public void setPlanned(BigDecimal planned) {
		this.planned = planned;
	}

	public BigDecimal getActual() {
		return actual;
	}

	public void setActual(BigDecimal actual) {
		this.actual = actual;
	}
	
}
