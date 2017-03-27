package com.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.models.ImageClient;
import org.jbehave.core.annotations.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static consts.RequestConsts.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by alex.bichovsky on 2/20/2017.
 */
public class GetImageSteps{
    RestTemplate restTemplate = new RestTemplate();
    String imageJsonObj = null;
    ImageClient image = new ImageClient();
    ObjectMapper mapper = new ObjectMapper();
    HttpHeaders headers = new HttpHeaders();


    // TODO Consider to add Interface for dealing with reusable functions

    @Given("Existing Image in DB with name $name and source url $url")
    public void givenImage(@Named("name") String name, @Named("url") String url) throws IOException {
        imageJsonObj = restTemplate.getForObject(localHost + getByName + nameParameter, String.class, name);
        if(Strings.isNullOrEmpty(imageJsonObj)){
            createNewImage(name, url);
        }else{
            image = mapper.readValue(imageJsonObj, ImageClient.class);
        }
    }

    @When("I am getting image with name $name")
    public void gettingImageWithName(@Named("name") String name) throws IOException {
        imageJsonObj = restTemplate.getForObject(localHost + getByName + nameParameter, String.class, name);
        image =  mapper.readValue(imageJsonObj, ImageClient.class);
    }

    @Then("I am seeing image with name $name")
    public void checkingImageForName(@Named("name") String name){
        assertEquals("Expected " + name + " but got "+ image.getName(), name, image.getName());
    }

    @When("I create a new Image in DB with name $name and source url $url")
    public void createNewImage(@Named("name")String name, @Named("url")String url) throws IOException {
        //TODO add service for creating json document
        image.setName(name);
        image.setUrl(url);
        String jsonInString = mapper.writeValueAsString(image);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
        imageJsonObj = restTemplate.postForObject(localHost + capture, entity, String.class);
        image = mapper.readValue(imageJsonObj, ImageClient.class);
    }

    @When("I delete image with name $name")
    public void deleteImage(@Named("name")String name){
        restTemplate.delete(localHost + delete + nameParameter, name);
        imageJsonObj = restTemplate.getForObject(localHost + getByName + nameParameter, String.class, name);
        assertEquals("Expected " + name + " to be deleted but got "+ imageJsonObj + " existing", null, imageJsonObj);
    }

    @AfterStories
    public void afterStory() {
        deleteImage(image.getName());
    }
}
