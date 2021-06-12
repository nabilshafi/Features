package com.up42.demo.controller;

import com.up42.demo.service.JsonService;
import com.up42.demo.model.FeatureDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import java.util.List;

@RestController
public class FeatureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureController.class);

    @Autowired
    private JsonService jsonService;

    @GetMapping(value = "/features", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List> getFeatures() {

        return ResponseEntity.ok().body(jsonService.getFeatures());

    }

    @GetMapping(value = "/features/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeatureDTO> getFeatureById(@PathVariable("id") String id)  {

        FeatureDTO featureDTO = jsonService.getFeatureById(id);
        if(featureDTO == null){
            LOGGER.warn("Data not found");
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok().body(featureDTO);

    }

    @GetMapping(value = "/features/{id}/quicklook", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getFeatureImage(@PathVariable("id") String id)  {

        byte[] img = jsonService.getImage(id);
        if(img == null){
            LOGGER.warn("Image not found");
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok().body(jsonService.getImage(id));

    }

}
