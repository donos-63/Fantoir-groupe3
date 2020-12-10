package fr.simplon.fantoir.models;

//Importation des librairies
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String numero;
    private String nom_voie;
    private String code_postal;
    private String nom_commune;

    public Client() {
    }

    //Instanciation des 
    public Client(String numero, String nomVoie, String codePostal, String nomCommune) {
        this.numero = numero;
        this.nom_voie = nomVoie;
        this.code_postal = codePostal;
        this.nom_commune = nomCommune;
    }

    public Client(String id, String numero, String nomVoie, String codePostal, String nomCommune) {
        this.id = id;
        this.numero = numero;
        this.nom_voie = nomVoie;
        this.code_postal = codePostal;
        this.nom_commune = nomCommune;
    }

    //Déclaration des getter/setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomVoie() {
        return nom_voie;
    }

    public void setNomVoie(String nomVoie) {
        this.nom_voie = nomVoie;
    }

    public String getCodePostal() {
        return code_postal;
    }

    public void setCodePostal(String codePostal) {
        this.code_postal = codePostal;
    }

    public String getNomCommune() {
        return nom_commune;
    }

    public void setNomCommune(String nomCommune) {
        this.nom_commune = nomCommune;
    }
}
