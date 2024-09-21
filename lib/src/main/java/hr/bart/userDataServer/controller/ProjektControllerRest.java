package hr.bart.userDataServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.bart.userDataServer.service.kod.RestServiceImplCreateDB;
import hr.bart.userDataServer.util.PojoInterface;

@RestController
@RequestMapping(value = ResponseEntityPojo.REQUEST_MAPPING_VALUE)
@CrossOrigin
public class ProjektControllerRest extends ResponseEntityPojo {
    @Autowired
    private transient RestServiceImplCreateDB restServiceImplCreateDB; 
    
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/createDB")
    public ResponseEntity<PojoInterface> createDB() {       
        PojoInterface pi=restServiceImplCreateDB.izvrsi();
        
        return handlePi(pi);
    }
}
