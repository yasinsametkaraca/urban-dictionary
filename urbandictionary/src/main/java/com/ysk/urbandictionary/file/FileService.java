package com.ysk.urbandictionary.file;


import com.ysk.urbandictionary.configuration.AppConfiguration;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class FileService {

    @Autowired
    AppConfiguration appConfiguration;
    Tika tika;  //tika gönderdiğimiz dosyanın img mi png mi pdf mi filan olduğunu anlıcak.
    FileAttachmentRepository fileAttachmentRepository;

    public FileService(AppConfiguration appConfiguration, FileAttachmentRepository fileAttachmentRepository) {
        this.appConfiguration = appConfiguration;
        this.tika = new Tika();
        this.fileAttachmentRepository=fileAttachmentRepository;
    }

    public String writeBase64EncodedStringToFile(String image) throws IOException {

        String fileName = generateRandomName();
        File target = new File( appConfiguration.getProfileStoragePath() + "/" + fileName);
        OutputStream outputStream = new FileOutputStream(target);

        byte[] base64Encoded = Base64.getDecoder().decode(image);
        outputStream.write(base64Encoded);
        outputStream.close();
        return fileName;
    }
    public String generateRandomName(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public void deleteProfileImageFile(String oldImageName) {
        if(oldImageName == null){
            return;
        }
        deleteFile(Paths.get(appConfiguration.getProfileStoragePath(),oldImageName));
    }

    public void deleteAttachmentFile(String oldFileName) {
        if(oldFileName == null){
            return;
        }
        deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(),oldFileName));
    }

    private void deleteFile(Path path){
        try {
            Files.deleteIfExists(path);  //String filePath = appConfiguration.getAttachmentStoragePath() + "/" + oldFileName; oraya git sil.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String detectType(String base64){
        byte[] base64Encoded = Base64.getDecoder().decode(base64); //stringden base64 arraye döndürme
        return detectType(base64Encoded);
    }

    public String detectType(byte[] arr) {
        String fileType = tika.detect(arr);
        return fileType;
    }

    public FileAttachment saveEntryAttachment(MultipartFile multipartFile) {    //Burdan dosyayı klasöre yüklücek. Multipart kullandık çünkü sadece resim değil herşey yüklenebilir.
        String fileName = generateRandomName();
        File target = new File( appConfiguration.getAttachmentStoragePath() + "/" + fileName);
        String fileType = null;
        try {
            byte[] arr = multipartFile.getBytes();
            OutputStream outputStream = new FileOutputStream(target);;
            outputStream.write(arr);  //byte şeklinde getirir.
            outputStream.close();
            fileType = detectType(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setName(fileName);
        fileAttachment.setDate(new Date());
        fileAttachment.setFileType(fileType);
        return fileAttachmentRepository.save(fileAttachment);
    }

    @Scheduled(fixedRate = 12*60*60*1000)  //bu metod 12 saatte bir koşucak. 12 saatten eski olan file ları bulup entry ile ilişkisi olmayanları siler.
    public void cleanUpStorage() {  //bu method entryle ilişkisi olmayan file ları bulup temizlicek. Hem db den hemde karşılık gelen dosyadan silicez. 1 gün boyunca file bir entry ile ilişkilendirilmemiş ise silinir.
        Date twelveHoursAgo = new Date(System.currentTimeMillis() - (12*60*60*1000));  //şuanki zamandan 12 saat çıkarttık.
        List<FileAttachment> filesToBeDeleted =  fileAttachmentRepository.findByDateBeforeAndEntryIsNull(twelveHoursAgo);
        for(FileAttachment file : filesToBeDeleted){
            deleteAttachmentFile(file.getName());  //burda dosyadan siliyoruz.
            fileAttachmentRepository.deleteById(file.getId()); //db den sildim.
        }
    }

}
