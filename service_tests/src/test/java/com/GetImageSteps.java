package com;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

/**
 * Created by alex.bichovsky on 2/20/2017.
 */
public class GetImageSteps{
    RestTemplate restTemplate = new RestTemplate();
    String imageFromDB;

    @Given("Existing Image in DB")
    public void givenImage(){

    }

    @When("I am getting image with name $name")
    public void gettingImageWithName(@Named("name") String name){
        imageFromDB = restTemplate.getForObject("http://localhost:8080/get-by-name?name={name}",String.class, name);
    }

    @Then("I am seeing image with name $name")
    public void checkingImageForName(@Named("name") String name){
        assertEquals("Expected " + name + " but got "+ imageFromDB, name,imageFromDB);
    }
}
