/**
*
* Entité Commune
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-04
*
**/

package main.java.Simplon.Fantoir.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "Commune")
public class Commune {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code_insee;
	
    @Column(nullable = false)
    private int code_postal;
    
    @Column(nullable = true)
    private String nom_commune;
    
    public Commune() {
    }

    public Commune(int code_insee, int code_postal, String nom_commune) {
        super();
        this.code_insee = code_insee;
		this.code_postal = code_postal;
		this.nom_commune = nom_commune;
    }
    
    public int setCode_insee() {
        return code_insee;
    }
    
    public int getCode_insee() {
        return code_insee;
    }

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int code_postal) {
		this.code_postal = code_postal;
    }
	
	public String getNom_communes() {
        return nom_commune;
    }

    public void setNom_commune(String nom_commune) {
		this.nom_commune = nom_commune;
    }
}
