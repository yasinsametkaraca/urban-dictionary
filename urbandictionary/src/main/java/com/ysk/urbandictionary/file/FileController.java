package com.ysk.urbandictionary.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/api/entry-attachments")
    FileAttachment saveEntryAttachment(MultipartFile file){    //FormData("file",file) şeklinde frontend in istek atması gerekir.
        return fileService.saveEntryAttachment(file);
    }
}
