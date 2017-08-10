package com.services;

import com.models.ImageModel;
import com.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by alex.bichovsky on 2/13/2017.
 */
@Component
public class ImageService {

    public ResponseEntity<?> takeImage(ImageModel image) throws IOException {
        try{
            if(imageExists(image.getUrl())){
                image.setLocalPath(saveImage(image.getUrl()));
            }
            else{
                return new ResponseEntity<String>("Wrong url for the image was provided!!!", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch(IOException e){
            throw e;
        }

        imageRepository.save(image);
        return new ResponseEntity<ImageModel>(image, HttpStatus.OK);
    }

    private boolean imageExists(String URLName) throws IOException {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (ProtocolException e) {
            throw e;
        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    private String saveImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        String path = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        OutputStream os = new FileOutputStream(path);
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
        return path;
    }

    public ResponseEntity<?> getImage(String name) {
        ImageModel image;
        try {
            image = imageRepository.findByName(name);
        }
        catch (Exception ex) {
            return new ResponseEntity<String>("No Image with this name was found!!!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ImageModel>(image, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteImage(String name) {

        //TODO map ImageModel object to Image object before deleting
        ImageModel image;
        try {
            image = imageRepository.findByName(name);
            imageRepository.delete(image);
        }
        catch (Exception ex) {
            return new ResponseEntity<String>("Image was not found!!!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ImageModel>(image, HttpStatus.OK);
    }

    @Autowired
    private ImageRepository imageRepository;
}

