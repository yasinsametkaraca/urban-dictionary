package com.ysk.urbandictionary.file;


import com.ysk.urbandictionary.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment,Long> {

    List<FileAttachment> findByDateBeforeAndEntryIsNull(Date date); //entrysi null olanı ve db deki date i bizim vereceğimiz date den önce olanı bulur.

    List<FileAttachment> findByEntryUser(User user); //FileAttachment içerisindeki entrinin içersindeki usera eriş. Verdiğimiz usera ait olan Entrylerin FileAttachment ları döner.

}
