package com.csye7125group1.Webapp.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Maincontroller {

    private static final Logger logger = LoggerFactory.getLogger(Maincontroller.class);

    @GetMapping(path = "/healthy", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity health() {
//        statsd.incrementCounter("server.get.healthy");
        logger.info("Healthy endpoint called");
//        Healthzresponse response = new Healthzresponse("Success");
        return new ResponseEntity(HttpStatus.OK);

    }
}