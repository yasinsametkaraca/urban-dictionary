package com.ysk.urbandictionary.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent  //jacksona tanımlamak için
public class PageSerializer extends JsonSerializer<Page<?>> {                                                                           //bu class page objectlerini jsona çevirirken işimize yarayacak. bu sayede password gibi bilgilerin page return eden yerlerde password return etmemesini sağlayacağız
    @Override
    public void serialize(Page<?> objects, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {     //usercontrollerda return edilen page li user objecti jsona çevrilmeden önce buraya uğrayacak
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("content");  //page içerisindeki content arrayini söylüyuruz.
        serializerProvider.defaultSerializeValue(objects.getContent(),jsonGenerator);  //content için jsonview i kullansın diyoruz.
        jsonGenerator.writeObjectField("pageable",objects.getPageable()); //pageable fieldini dahil ettik.
        jsonGenerator.writeBooleanField("last",objects.isLast()); //last fieldini ekledik
        jsonGenerator.writeNumberField("totalPages",objects.getTotalPages());
        jsonGenerator.writeNumberField("totalElement",objects.getTotalElements());
        jsonGenerator.writeNumberField("size",objects.getSize());
        jsonGenerator.writeNumberField("number",objects.getNumber());
        jsonGenerator.writeObjectField("sort",objects.getSort());
        jsonGenerator.writeNumberField("numberOfElements",objects.getNumberOfElements());
        jsonGenerator.writeBooleanField("first",objects.isFirst());
        jsonGenerator.writeBooleanField("empty",objects.isEmpty());
        jsonGenerator.writeEndObject(); //json yazma işimiz bitti.
    }
}
//Page dönen cevapları configuration yaptık.
// "content": [
//        {
//            "username": "deneme",
//            "displayName": "fddsf",
//            "image": null
//        },
//        {
//            "username": "deneme2",
//            "displayName": "fddsf",
//            "image": null
//        },
//        {
//            "username": "deneme23",
//            "displayName": "fddsf",
//            "image": null
//        },
//        {
//            "username": "deneme2123",
//            "displayName": "fddsf",
//            "image": null
//        }
//    ],
//    "pageable": {
//        "sort": {
//            "empty": true,
//            "sorted": false,
//            "unsorted": true
//        },
//        "offset": 0,
//        "pageSize": 4,
//        "pageNumber": 0,
//        "unpaged": false,
//        "paged": true
//    },
//    "last": false,
//    "totalPages": 8,
//    "totalElement": 30,
//    "size": 4,
//    "number": 0,
//    "sort": {
//        "empty": true,
//        "sorted": false,
//        "unsorted": true
//    },
//    "numberOfElements": 4,
//    "first": true,
//    "empty": false

