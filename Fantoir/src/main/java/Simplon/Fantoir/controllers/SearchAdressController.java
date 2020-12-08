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

    @Autowired
    public SearchAdressController(final SearchAdressService addressService){
        this.addressService = addressService;
    }
    
    @RequestMapping(value = "/SearchAdress/{location}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get address proposals from partial location")
	public List<Adresse> search(@PathVariable final String location) {
		return this.addressService.search(location);
	}
}