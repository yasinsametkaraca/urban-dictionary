package com.ysk.urbandictionary.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration  // Configuration anotasyonunu spring in default davranışlarını ezmek için veya spring configuration larda kullanırız.
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    AppConfiguration appConfiguration;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**") // "/images/**"  demek http://localhost:8080/images/profile.png dediğimizde bu satır devreye giricek. Bu satırla birlikte http://localhost:8080/images/profile.png adrese gittiğimizde görüntüye ulaşırız.
        .addResourceLocations("file:./" + appConfiguration.getUploadPath() + "/")  //profile.png in olduğu  dosyayı bulmaya çalışır, olduğu dosyayı bulması için location verdik.
        .setCacheControl(CacheControl.maxAge(120, TimeUnit.DAYS));  //resim dosyasını cachledik bu dosya clientta 120 gün saklanıcak.
    }

    @Bean //başlangıçta direk klasör oluşturması için.
    CommandLineRunner createStorageDirectories() {
        return (args) -> {
            createFolder(appConfiguration.getUploadPath()); //storage-dev
            createFolder(appConfiguration.getProfileStoragePath()); //profiles
            createFolder(appConfiguration.getAttachmentStoragePath()); //attachments
        };
    }

    private void createFolder(String path) {
        File folder = new File(path);
        boolean folderExist = folder.exists() && folder.isDirectory();
        if(!folderExist) {
            folder.mkdir();
        }
    }


}
