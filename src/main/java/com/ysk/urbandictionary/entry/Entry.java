package com.ysk.urbandictionary.entry;

import com.ysk.urbandictionary.file.FileAttachment;
import com.ysk.urbandictionary.shared.FileType;
import com.ysk.urbandictionary.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(min = 1,max = 100)
    private String word;

    @Size(min = 1,max = 1000)
    @Column(length = 1000,columnDefinition="text") //255 harften fazlasında hata vermemesi için yazdım.
    private String definition;

    @Size(min = 1,max = 1000)
    @Column(length = 1000,columnDefinition="text")
    private String sentence;

    @Temporal(TemporalType.TIMESTAMP) //hem tarih hem saat bilgisi olsun.
    private Date timestamp;

    @ManyToOne      //her entyrinin bir useri vardır. Unidirectional bir ilişkidir. //burada mappedBy yok yani entry tablosunda user_id diye sütün oluşucak.
    @JoinColumn(name = "user_id")  //entry tablosuna user_id kolonu koyar.
    private User user;

    @OneToOne(mappedBy = "entry",cascade = CascadeType.REMOVE) //burayı ekleyince tekil bir şekilde oluşur ilişki ve entry talosunda fileAttachment kısmı oluşmaz. fileAttachment tablosunda entryId kısmı oluşur. CascadeType.REMOVE un mantığı, entry silinirse fileAttachment objesini de sil.
    private FileAttachment fileAttachment; //bidirectional ilişkiye çevirdim çünkü file a entry objesi üzerinden de erişmek isterim.
    //mappedBy = "entry" in mantığı = FileAttachment tablosundaki entry sütünü Entry instance in foreign key i dir. Yani sadece FileAttachment tablosunda entry_Id diye alan oluşucak.
}
