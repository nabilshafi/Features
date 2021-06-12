package com.up42.demo.controller;

import com.up42.demo.dal.JsonService;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FeatureController {

    @Autowired
    private JsonService jsonService;

    @GetMapping(value = "/features")
    public ResponseEntity<String> getFeatures() throws IOException, ParseException {


        jsonService.jsonReader();
        return ResponseEntity.ok().body("good");

    }

}
