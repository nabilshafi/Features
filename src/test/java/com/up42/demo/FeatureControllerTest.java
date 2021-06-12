package com.up42.demo;

import com.up42.demo.service.JsonService;
import com.up42.demo.model.FeatureDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = FeaturesApplication.class)
public class FeatureControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    private JsonService jsonService;

    FeatureDTO featureDTO;
    List<FeatureDTO> featureList;
    byte[] img;

    @Before
    public void init()  {

    }

    @Test
    public void testGetFeatures() {


        Mockito.when(jsonService.getFeatures()).thenReturn(featureList);
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/features");
        ResponseEntity<List> result = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(headers), List.class);
        assertThat(result.getBody()).isEqualTo(featureList);

    }

    @Test
    public void testGetFeatureById() {


        Mockito.when(jsonService.getFeatureById(Mockito.anyString())).thenReturn(featureDTO);
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/features/3");
        ResponseEntity<FeatureDTO> result = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(headers), FeatureDTO.class);
        assertThat(result.getBody()).isEqualTo(featureDTO);

    }

    @Test
    public void testGetFeatureImage() {


        Mockito.when(jsonService.getImage(Mockito.anyString())).thenReturn(img);
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/features/3/quicklook");
        ResponseEntity<byte[]> result = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
        assertThat(result.getBody()).isEqualTo(img);

    }


}
