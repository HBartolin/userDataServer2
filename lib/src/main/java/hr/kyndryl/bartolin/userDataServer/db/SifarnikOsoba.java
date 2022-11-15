package hr.kyndryl.bartolin.userDataServer.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import hr.kyndryl.bartolin.userDataServer.util.Hr2Us;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class SifarnikOsoba implements Serializable {
	private static final long serialVersionUID = -6722477037486496308L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String ime;
	private String prezime;
	
	public Long getId() {
        return id;
    }
	
	public void setId(Long id) {
		this.id=id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@Transient
	public String getImePrezime() {
		return new StringBuffer(ime).append(" ").append(prezime).toString();
	}

	@Transient
	public String getPrezimeIme() {
		return new StringBuffer(prezime).append(", ").append(ime).toString();
	}

	@Transient
	public String getPrezimeImeUs() {
		return new Hr2Us().getNaziv(getPrezimeIme());
	}

}
