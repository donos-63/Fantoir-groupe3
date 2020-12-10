/**
*
* Api controller with SpringBoot to search addresses
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-08
*
**/
package main.java.Simplon.Fantoir.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import main.java.Simplon.Fantoir.models.Adresse;
import main.java.Simplon.Fantoir.services.Interfaces.SearchAdressService;

@RestController
@Api(tags = "Book management")
public class SearchAdressController {

    private SearchAdressService addressService;

    /**
     * Constructor
     * @param addressService auto filled by @Autowired
     */
    @Autowired
    public SearchAdressController(final SearchAdressService addressService){
        this.addressService = addressService;
    }
    
    /**
     * Search addresses by sting
     * @param location : the desired address
     * @return
     */
    @RequestMapping(value = "/SearchAdress/{location}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get address proposals from partial location")
	public List<Adresse> search(@PathVariable final String location) {
		return this.addressService.search(location);
	}
    
    /**
     * Search addresses by postal code
     * @param postal_code
     * @return
     */
    @RequestMapping(value = "/SearchAddressByPostalCode/{postal_code}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get address proposals from partial location")
	public List<Adresse> searchByPostalCode(@PathVariable final int postal_code) {
		return this.addressService.searchByPostalCode(postal_code);
	}
    
    /**
     * Search addresses by postal code
     * @param postal_code
     * @return
     */
    @RequestMapping(value = "/SearchAddressByTopCounts/{nb_top}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get address proposals from partial location")
	public List<Adresse> searchTopAddress(@PathVariable final int nb_top) {
		return this.addressService.searchByTopCounts(nb_top);
	}
}