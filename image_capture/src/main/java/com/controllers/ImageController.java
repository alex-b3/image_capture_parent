package com.controllers;

import com.models.Image;
import com.models.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by alex.bichovsky on 2/13/2017.
 */

@Controller
public class ImageController {

    /**
     * POST /create  --> Create a new user and save it in the database.
     */
    @RequestMapping(value = "/capture", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Image> create(@RequestBody Image image){
            imageDao.save(image);
            return new ResponseEntity<Image>(image, HttpStatus.OK);
    }

    /**
     * GET /get-by-name  --> Return the id for the user having the passed
     * email.
     */
    @RequestMapping("/get-by-name")
    @ResponseBody
    public String getByName(String name) {
        String imageName = "";
        try {
            Image image = imageDao.findByName(name);
            imageName = String.valueOf(image.getName());
        }
        catch (Exception ex) {
            return "User not found";
        }
        return imageName;
    }

    @Autowired
    private ImageDao imageDao;
}
