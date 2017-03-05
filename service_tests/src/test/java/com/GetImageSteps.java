package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Image;
import org.jbehave.core.annotations.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by alex.bichovsky on 2/20/2017.
 */
public class GetImageSteps{
    RestTemplate restTemplate = new RestTemplate();
    String imageJsonObj = null;
    Image image = new Image();
    ObjectMapper mapper = new ObjectMapper();

    // TODO Consider to add Interface for dealing with reusable functions

    @Given("Existing Image in DB with name $name and source url $url")
    public void givenImage(@Named("name") String name, @Named("url") String url) throws IOException {
        // TODO add class for mapping url
        imageJsonObj = restTemplate.getForObject("http://localhost:8080/get-by-name?name={name}",String.class, name);
        if(imageJsonObj.equals("Image not found")){
            createNewImage(name, url);
        }else{
            image = mapper.readValue(imageJsonObj, Image.class);
        }
    }

    @When("I am getting image with name $name")
    public void gettingImageWithName(@Named("name") String name){
        imageJsonObj = restTemplate.getForObject("http://localhost:8080/get-by-name?name={name}",String.class, name);
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonInString,headers);
        imageJsonObj = restTemplate.postForObject("http://localhost:8080/capture", entity, String.class);
        image = mapper.readValue(imageJsonObj, Image.class);
    }

    @When("I delete image with name $name")
    public void deleteImage(@Named("name")String name){
        restTemplate.delete("http://localhost:8080/delete?name={name}", name);
        imageJsonObj = restTemplate.getForObject("http://localhost:8080/get-by-name?name={name}",String.class, name);
        assertEquals("Expected " + name + "to be deleted but got "+ imageJsonObj + " existing", "Image not found", imageJsonObj);
    }

    @AfterStories
    public void afterStory() {
        deleteImage(image.getName());
    }
}
