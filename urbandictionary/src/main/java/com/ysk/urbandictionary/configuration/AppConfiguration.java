package com.ysk.urbandictionary.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "dictionary")  //dictionary.uploadPath diyerek burdan yönetebiliriz. Projemize özel olduğu çabuk anlaşılır.
public class AppConfiguration {  //properties.yaml da olanlar için burayı kendi yaml dosyamız gibi kullanıcaz. Kullanım kolaylığı için burdan yönetebiliriz.

    private String uploadPath;

    private String profileStorage = "profiles";  //profil image leri için.

    private String attachmentStorage = "attachments";  //attachmentlar için.

    public String getProfileStoragePath() {
        return uploadPath + "/" + profileStorage;
    }  //storage-dev / profiles

    public String getAttachmentStoragePath() {
        return uploadPath + "/" + attachmentStorage;
    }

}
