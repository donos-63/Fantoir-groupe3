/**
* API controller with SpringBoot
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-07
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

import java.io.IOException;
import main.java.Simplon.Fantoir.services.Interfaces.PushFileService;

@RestController
@Api(tags = "Book management")
public class PushFileController {

    private PushFileService pushFileService;

    @Autowired
    public PushFileController(final PushFileService pushFileService){
        this.pushFileService = pushFileService;
    }
    
    @RequestMapping(value = "/PushFile/{nb_file}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get files from server for Talend integration")
	public String search(@PathVariable final int nb_file) throws IOException {
		return this.pushFileService.PushFilesInDatabase(nb_file);
	}
}