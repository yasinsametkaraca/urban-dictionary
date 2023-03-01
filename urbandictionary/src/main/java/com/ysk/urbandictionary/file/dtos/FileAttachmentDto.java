package com.ysk.urbandictionary.file.dtos;

import com.ysk.urbandictionary.file.FileAttachment;
import com.ysk.urbandictionary.shared.FileType;
import lombok.Data;

@Data
public class FileAttachmentDto { //respond içindir.

    private String name;

    private String fileType;
    public FileAttachmentDto(FileAttachment fileAttachment){
        this.setName(fileAttachment.getName());
        this.fileType = fileAttachment.getFileType();
    }
}