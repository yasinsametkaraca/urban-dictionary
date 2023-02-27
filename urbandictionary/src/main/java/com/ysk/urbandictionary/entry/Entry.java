package com.ysk.urbandictionary.entry;

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

    @ManyToOne      //her entyrinin bir useri vardır.
    @JoinColumn(name = "user_id")  //entry tablosuna user_id kolonu koyar.
    private User user;

}
