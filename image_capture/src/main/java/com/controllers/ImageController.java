package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Image;
import com.models.ImageDao;
import com.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by alex.bichovsky on 2/13/2017.
 */

@Controller
public class ImageController {

    ObjectMapper mapper = new ObjectMapper();

    /**
     * POST /create  --> Create a new user and save it in the database.
     */
    @RequestMapping(value = "/capture", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody Image image) throws IOException {
            if(imageService.takeImage(image)){
                imageDao.save(image);
                return new ResponseEntity<Image>(image, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Wrong url for the image was provided!!!", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * GET /get-by-name  --> Return the id for the user having the passed
     * email.
     */
    @RequestMapping("/get-by-name")
    @ResponseBody
    public String getByName(String name) {
        String jsonInString = null;
        try {
            Image image = imageDao.findByName(name);
            if(!image.equals(null)){
                jsonInString = mapper.writeValueAsString(image);
            }
        }
        catch (Exception ex) {
            return "Image not found";
        }
        return jsonInString;
    }

    /**
     * DELETE /delete  --> Delete image by name
     */

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteByName(String name) {
        String imageName = "";
        try {
            Image image = imageDao.findByName(name);
            imageName = image.getName();
            imageDao.delete(image);
        }
        catch (Exception ex) {
            return "Image not found";
        }
        return imageName + " was deleted";
    }

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ImageService imageService;
}
