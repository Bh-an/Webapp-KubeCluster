package com.csye7125group1.Webapp.Controllers;

import com.csye7125group1.Webapp.Utility.ConnectionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    ConnectionValidator connvalidator;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping(path = "/healthy", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity health() {
//        statsd.incrementCounter("server.get.healthy");
//        logger.info("Healthy endpoint called");
//        Healthzresponse response = new Healthzresponse("Success");
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping(path = "/healthyboi", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity healthyboi() {
//        statsd.incrementCounter("server.get.healthy");
//        logger.info("Healthy endpoint called");
//        Healthzresponse response = new Healthzresponse("Success");
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping(path = "/ready", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity readiness() {
//        statsd.incrementCounter("server.get.healthy");
        logger.info("Ready endpoint called");
//        Healthzresponse response = new Healthzresponse("Success");

        if (connvalidator.checkConnection()){
            return new ResponseEntity(HttpStatus.OK);
        }
        logger.error("Ready endpoint unavailable");
        return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);

    }
}