package com.services;

import com.models.Image;
import com.models.ImageDao;
import org.springframework.stereotype.Repository;

/**
 * Created by alex.bichovsky on 2/13/2017.
 */
@Repository
public class ImageService {

    public String takeImage(Image image) {
        return "Done";
    }

}
