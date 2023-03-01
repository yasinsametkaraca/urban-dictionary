package com.ysk.urbandictionary.file;

import com.ysk.urbandictionary.entry.Entry;
import com.ysk.urbandictionary.shared.FileType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class FileAttachment { //entrylerimize dosya yüklenebilecek.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String fileType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToOne
    private Entry entry; //bu dosyanın hangi Entry le ilişkisi var o tutulucak. Bidirectional olucak entry entity de de fileattachment kısmı olucak.ö
}

