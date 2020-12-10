package main.java.Simplon.Fantoir.models;

public class CommuneCustom {

    private long nb_adresses;
    
    private Commune commune;
    
    public CommuneCustom(){
    }
        
    public CommuneCustom(Commune commune, long nb_adresses )
    {
    	this.commune = commune;
    	this.nb_adresses = nb_adresses;
    }
    
    public void setCommune(Commune commune) {
		this.commune = commune;
    }
    
    public Commune getCommune() {
        return commune;
    }

	public long getNb_adresses() {
        return nb_adresses;
    }

    public void setNb_adresses(long nb_adresses) {
		this.nb_adresses = nb_adresses;
    }
}
