package com.services;

import com.models.Image;
import com.models.ImageDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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

    public boolean takeImage(Image image) throws IOException {
        try{
            if(imageExists(image.getUrl())){
                image.setLocalPath(saveImage(image.getUrl()));
                image.getLocalPath();
            }
            else{
                return false;
            }
        }catch(IOException e){
            throw e;
        }
        return true;
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
}

