package com.controllers;

import com.models.ImageModel;
import com.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * POST /create  --> Create a new user and save it in the database.
     */
    @RequestMapping(value = "/capture", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ImageModel image) throws IOException {

        return imageService.takeImage(image);
    }

    /**
     * GET /get-by-name  --> Return the id for the user having the passed
     * email.
     */
    @RequestMapping("/get-by-name")
    @ResponseBody
    public ResponseEntity<?> getByName(String name) {

        return imageService.getImage(name);
    }

    /**
     * DELETE /delete  --> Delete image by name
     */

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteByName(String name) {

        return imageService.deleteImage(name);
    }

    @Autowired
    private ImageService imageService;
}
