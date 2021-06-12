package com.up42.demo.controller;

import com.up42.demo.dal.JsonService;
import com.up42.demo.model.FeatureDTO;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class FeatureController {

    @Autowired
    private JsonService jsonService;

    @GetMapping(value = "/features", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List> getFeatures() throws IOException, ParseException {

        return ResponseEntity.ok().body(jsonService.getFeatures());

    }

    @GetMapping(value = "/features/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeatureDTO> getFeatureById(@PathVariable("id") String id) throws IOException, ParseException {

        return ResponseEntity.ok().body(jsonService.getFeatureById(id));

    }

    @GetMapping(value = "/features/{id}/quicklook", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getFeatureImage(@PathVariable("id") String id) throws IOException, ParseException {

        return ResponseEntity.ok().body(jsonService.getImage(id));

    }



}
