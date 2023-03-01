package com.ysk.urbandictionary.file;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment,Long> {

    List<FileAttachment> findByDateBeforeAndEntryIsNull(Date date); //entrysi null olanı ve db deki date i bizim vereceğimiz date den önce olanı bulur.
}
