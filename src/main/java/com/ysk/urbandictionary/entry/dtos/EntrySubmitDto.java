package com.ysk.urbandictionary.entry.dtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data
public class EntrySubmitDto { //entryleri burdan submit edicez. File için burayı açtık. Request için oluşturdum burayı ve frontend den attachmentId yi göndericez.

    @Size(min = 1,max = 100)
    private String word;

    @Size(min = 1,max = 1000)
    private String definition;

    @Size(min = 1,max = 1000)
    private String sentence;

    private Long attachmentId;  //dosya id.
}
