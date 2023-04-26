package hr.bart.userDataServer.db;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class SifarnikMjeseca implements Serializable {
	private static final long serialVersionUID = 1482875841526952054L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private LocalDate mjesec;
	
	public Long getId() {
        return id;
    }

	public LocalDate getMjesec() {
		return mjesec;
	}

	public void setMjesec(LocalDate mjesec) {
		this.mjesec = mjesec;
	}
	
}
