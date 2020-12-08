package fr.simplon.fantoir.repositories;

import org.springframework.data.repository.CrudRepository;

import fr.simplon.fantoir.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
    
}
