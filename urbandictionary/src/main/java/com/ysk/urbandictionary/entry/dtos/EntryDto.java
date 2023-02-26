package com.ysk.urbandictionary.entry.dtos;

import com.ysk.urbandictionary.entry.Entry;
import com.ysk.urbandictionary.user.dtos.UserDto;
import lombok.Data;

@Data
public class EntryDto {

    private long id;

    private String word;

    private String definition;

    private String sentence;

    private long timestamp; //burda ms cinsinden kullanıcaz.

    private UserDto user;
    public EntryDto(Entry entry) {
        this.setId(entry.getId());
        this.setWord(entry.getWord());
        this.setDefinition(entry.getDefinition());
        this.setSentence(entry.getSentence());
        this.setTimestamp(entry.getTimestamp().getTime());
        this.setUser(new UserDto(entry.getUser())); //UserDto olarak useri respond edicez. Çünkü UserDto da password yoktur.
    }
}
/* //EntryDto ile birlikte backende istek attığımızda respond bu şekilde oluşur.

definition:"sdfsfd",
id:33,
sentence:"sdfsdfsf",
timestamp:1677450736855,
word: "sdfsf",
user:{
    username: "yasinsametkaraca",
    displayName: "yskrca",
    image: "1bdaffd4d814428bb22ea674795ab25f.jpg"
}

*/