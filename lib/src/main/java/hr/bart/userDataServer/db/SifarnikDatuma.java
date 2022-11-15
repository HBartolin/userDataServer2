package hr.bart.userDataServer.db;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class SifarnikDatuma implements Serializable {
	private static final long serialVersionUID = -5325593441760406646L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private LocalDate datumPetak;
	private LocalDate datumNedjelja;
	@ManyToOne
	@JoinColumn(name = "id_mjesec", referencedColumnName="id")
	private SifarnikMjeseca mjesec;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getDatumPetak() {
		return datumPetak;
	}
	
	public void setDatumPetak(LocalDate datumPetak) {
		this.datumPetak = datumPetak;
	}
	
	public LocalDate getDatumNedjelja() {
		return datumNedjelja;
	}
	
	public void setDatumNedjelja(LocalDate datumNedjelja) {
		this.datumNedjelja = datumNedjelja;
	}
	
	public SifarnikMjeseca getMjesec() {
		return mjesec;
	}
	
	public void setMjesec(SifarnikMjeseca mjesec) {
		this.mjesec = mjesec;
	}
	
}
