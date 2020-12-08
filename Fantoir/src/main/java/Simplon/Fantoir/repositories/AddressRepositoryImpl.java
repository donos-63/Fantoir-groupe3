package main.java.Simplon.Fantoir.repositories;

import main.java.Simplon.Fantoir.models.Adresse;
import main.java.Simplon.Fantoir.repositories.Interfaces.AddressRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AddressRepositoryImpl implements AddressRepository{
    @Autowired
    private SessionFactory sessionFactory;
    
    public AddressRepositoryImpl() {
    }
    
    public Adresse getAddressById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Adresse.class, id);   	
    }
    
    
    public List<Adresse> getAllAddresses() {
    	Session session = this.sessionFactory.openSession();
		List<Adresse> addresses = session.createQuery("from Adresse").list();
		session.close();
		return addresses;
    }
    
    public List<Adresse> searchAddress(String location) {
    	String address_clause = '%'+ location.replace(' ', '%') + '%';
    	String[] target = location.toLowerCase().split(" ");
    	
    	Session session = this.sessionFactory.openSession();
    	NativeQuery q = session.createSQLQuery("SELECT Adresse.id  FROM Adresse JOIN Commune on Commune.code_insee = Adresse.code_insee "
    											+"WHERE numero || '%' || rep || '%'  || nom_voie || '%' || code_postal || '%' || nom_commune LIKE '"+address_clause+"' "
    											+"GROUP BY Adresse.code_insee, Adresse.nom_voie LIMIT 10");

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
		session.close();
		
		return addresses;
    }
    
    private int getMatchingRate(String[] target, Adresse result){
    	List<String> value_to_compare = Arrays.asList(target); 
    	ArrayList<String> value = new ArrayList<String>(); 
    	value.add(String.valueOf(result.getNumero()));
    	value.add(String.valueOf(result.getCommune().getCode_postal()));
    	value.add(String.valueOf(result.getRep().toLowerCase()));
    	value.addAll(Arrays.asList(result.getNom_voie().toLowerCase().split(" ")));
    	value.addAll(Arrays.asList(result.getCommune().getNom_communes().toLowerCase().split(" ")));
		
    	int nb_keywords = value.size();
		value.retainAll(value_to_compare);
    	
    	return value.size() *100 / nb_keywords ;
    }
    
    
    
}
