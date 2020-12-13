/**
*
* Entité Adresse
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(appliesTo = "Adresse")
public class Adresse {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
	
    @Column(nullable = true)
    private Integer numero;

    @Column(nullable = true)
    private String rep;

    @Column(nullable = false)
    private String nom_voie;
    
    @OneToOne
    @JoinColumn(name = "code_insee")
    private Commune commune;
    
    @Transient
    private int matching_rate;

    public Adresse() {
    }

    public Adresse(String code_insee, Integer numero, String rep, String nom_voie) {
        super();
		this.numero = numero;
		this.rep = rep;
		this.nom_voie = nom_voie;
    }
    
    public long getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
		this.numero = numero;
    }
	
	public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
		this.rep = rep;
    }
	
	public String getNom_voie() {
        return nom_voie;
    }

    public void setNom_voie(String nom_voie) {
		this.nom_voie = nom_voie;
    }
    
    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }
   
    public int getMatching_rate() {
        return matching_rate;
    }

    public void setMatching_rate(int matching_rate) {
		this.matching_rate = matching_rate;
    }
}