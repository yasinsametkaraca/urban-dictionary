package com.ysk.urbandictionary.file;


import com.ysk.urbandictionary.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    AppConfiguration appConfiguration;

    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String fileName = generateRandomName();
        File target = new File( appConfiguration.getUploadPath() + "/" + fileName);
        OutputStream outputStream = new FileOutputStream(target);

        byte[] base64Encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64Encoded);
        outputStream.close();
        return fileName;
    }
    public String generateRandomName(){
        return UUID.randomUUID().toString().replaceAll("-","")+".jpg";
    }

    public void deleteFile(String oldImageName) {
        if(oldImageName == null){
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(),oldImageName));  //String filePath = appConfiguration.getUploadPath() + "/" + oldImageName; oraya git sil.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
