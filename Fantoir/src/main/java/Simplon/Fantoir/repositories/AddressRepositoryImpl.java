/**
*
* Repository Adresse
* Fournit les methodes d'accés à la base
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-07
*
**/
package main.java.Simplon.Fantoir.repositories;

import main.java.Simplon.Fantoir.models.Adresse;
import main.java.Simplon.Fantoir.repositories.Interfaces.AddressRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AddressRepositoryImpl implements AddressRepository{
    @Autowired
    private SessionFactory sessionFactory;
    
    private int maxSearchCount = 20;
    
    /**
     * Constructor
     */
    public AddressRepositoryImpl() {
    }
    
    /**
     * Trouve et popule une adresse par son id
     * @param id
     * @return
     */
    public Adresse getAddressById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Adresse.class, id);   	
    }
    
    /**
     * Trouve et popule toutes les adresses
     */
    public List<Adresse> getAllAddresses() {
    	Session session = this.sessionFactory.openSession();
		List<Adresse> addresses = session.createQuery("from Adresse").list();
		session.close();
		return addresses;
    }
    
    /**
     * Trouve et popule les adresses correspondant à une chaine d'entrée
     */
    public List<Adresse> searchAddress(String location) {
    	String address_clause = '%'+ location.replace(' ', '%') + '%';
    	String[] target = location.toLowerCase().split(" ");
    	
    	Session session = this.sessionFactory.openSession();

    	try{
	    	NativeQuery q = session.createSQLQuery("SELECT Adresse.id  FROM Adresse JOIN Commune on Commune.code_insee = Adresse.code_insee "
	    											+"WHERE IFNULL(numero,'') || '%' || IFNULL(rep,'') || '%'  || nom_voie || '%' || code_postal || '%' || nom_commune LIKE :address_clause "
	    											+"LIMIT :maxSearchCount");
	
	        q.setParameter("address_clause", address_clause);
	        q.setParameter("maxSearchCount", maxSearchCount);
	
	    	List<Integer> ids = q.list();
	    	List<Adresse> addresses = new ArrayList<Adresse>(ids.size());
	    	for(Integer id : ids){
	    		if(id == null)
	    			break;
	    		Adresse address = session.get(Adresse.class, id);
	    		int match = getMatchingRate(target, address);
	    		address.setMatching_rate(match);
	    		
	    		addresses.add(address);
	    	}
			
			return addresses;
    	}
    	finally {
    		session.close();
    	}
    }
    
    /**
     * Trouve le top X des villes avec le plus de référence d'adresse 
     */
	@Override
	public List searchByTopCounts(int nb_top) {
		Session session = this.sessionFactory.openSession();
    	try{
    		Query q = session.createQuery("select new main.java.Simplon.Fantoir.models.CommuneCustom(address.commune, count(address.commune.code_insee) as nb_addresses) "
	    									+"from Adresse as address "
	    									+"group by address.commune order by nb_addresses desc");
	    	q.setMaxResults(nb_top);
	    	return q.list();
    	}
    	finally{
    		session.close();
    	}
	}

	/**
	 * Trouve et popule les adresses correspondant à un code postal
	 */
	@Override
	public List<Adresse> searchByPostalCode(int postal_code) {
		
    	Session session = this.sessionFactory.openSession();
    	try{
	    	Query q = session.createQuery("from Adresse as address where address.commune.code_postal = :postal_code");
	    	 //
	    	q.setParameter("postal_code", postal_code);
	
	    	return q.list();
    	}
    	finally{
    		session.close();
    	}
	}
    
	/**
	 * calcule le taux de matching entre l'adresse demandée et l'adresse trouvée
	 * @param target
	 * @param result
	 * @return
	 */
    private int getMatchingRate(String[] target, Adresse result){
    	List<String> value_to_compare = Arrays.asList(target); 
    	ArrayList<String> value = new ArrayList<String>(); 
    	if(result.getNumero() != null) //lieux-dit
    		value.add(String.valueOf(result.getNumero()));
    	value.add(String.valueOf(result.getCommune().getCode_postal()));
    	if(result.getRep() != null) //lieux-dit
    		value.add(String.valueOf(result.getRep().toLowerCase()));
    	value.addAll(Arrays.asList(result.getNom_voie().toLowerCase().split(" ")));
    	value.addAll(Arrays.asList(result.getCommune().getNom_communes().toLowerCase().split(" ")));
    	value.removeAll(Arrays.asList("", null));
    	
    	int nb_keywords = value.size();
		value.retainAll(value_to_compare);
    	
    	return value.size() *100 / nb_keywords ;
    }  
}
