package com.up42.demo;

import com.up42.demo.service.JsonService;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JsonServiceTest {

    @InjectMocks
    private JsonService jsonService;
    String jsonString;
    JSONObject jsonObject;

    @Before
    public void init() throws IOException, ParseException {


    }

    @Test
    public void testLoadJsonToMemory() throws IOException {

        String path = "src/test/resources/source-data-test1.json";
        jsonString = new String (Files.readAllBytes(Paths.get(path)));
        jsonService.loadJsonToMemory(jsonString);
        assertThat(jsonService.getMap().size()).isEqualTo(14);

    }
    @Test
    public void getImage() throws IOException {

        String path = "src/test/resources/source-data-test2.json";
        jsonString = new String (Files.readAllBytes(Paths.get(path)));
        jsonService.loadJsonToMemory(jsonString);

        assertThat(jsonService.getImage("39c2f29e-c0f8-4a39-a98b-deed547d6aea")).isNull();

    }

    @Test
    public void getFeatureById() throws IOException {

        String path = "src/test/resources/source-data-test2.json";
        jsonString = new String (Files.readAllBytes(Paths.get(path)));
        jsonService.loadJsonToMemory(jsonString);

        assertThat(jsonService.getFeatureById("39c2f29e-c0f8-4a39-a98b-deed547d6aea").getMissionName()).isEqualTo("Sentinel-1B");

    }

}
