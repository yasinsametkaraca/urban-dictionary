package com.ysk.urbandictionary.entry;

import com.ysk.urbandictionary.entry.dtos.EntryDto;
import com.ysk.urbandictionary.shared.CurrentUser;
import com.ysk.urbandictionary.shared.GenericResponse;
import com.ysk.urbandictionary.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EntryController {

    @Autowired
    EntryService entryService;

    @PostMapping("/api/entries")
    GenericResponse saveEntry(@Valid @RequestBody Entry entry, @CurrentUser User user){  //Entry i kaydetmeden önce o an ki login olan kullanıcının bilgilerini alır.
        entryService.saveEntry(entry,user);
        return new GenericResponse("Entry is saved");
    }

    @GetMapping("/api/entries")
    Page<EntryDto> getEntries(@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable page){  //@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) kodunu sıralama yapması için yaptım. Page ile birlikte gelen bir davranış.
        return entryService.getEntries(page).map(EntryDto::new);  //entry objectlerini tek tek alır ve EntryDto a çevirir. Bunu EntryDto da ki constructoru kullanarak yapar. Sonuç olarak EntryDto objesi dönmüş olur.
    }

    @GetMapping("/api/users/{username}/entries")
    Page<EntryDto> getEntriesByUsername(@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable page, @PathVariable String username){
        return entryService.getEntriesByUsername(username,page).map(EntryDto::new);
    }

}

//getEntriesByUsername bize bu şekilde döner. Page kullanmamızın sebebi EntryDto ları page objesi olarak dönsün isteriz. {"content":[{"id":33,"word":"sdfsf","definition":"sdfsfd","sentence":"sdfsdfsf","timestamp":1677450736855,"user":{"username":"yasinsametkaraca","displayName":"yskrca","image":"1bdaffd4d814428bb22ea674795ab25f.jpg"}},{"id":32,"word":"dsfsa","definition":"fsdfsf","sentence":"sdfsdf","timestamp":1677446543688,"user":{"username":"yasinsametkaraca","displayName":"yskrca","image":"1bdaffd4d814428bb22ea674795ab25f.jpg"}},{"id":31,"word":"dgd","definition":"gdfgd","sentence":"fgdfg","timestamp":1677445768008,"user":{"username":"yasinsametkaraca","displayName":"yskrca","image":"1bdaffd4d814428bb22ea674795ab25f.jpg"}}],"pageable":{"sort":{"empty":false,"sorted":true,"unsorted":false},"offset":0,"pageSize":10,"pageNumber":0,"paged":true,"unpaged":false},"last":true,"totalPages":1,"totalElements":3,"size":10,"number":0,"sort":{"empty":false,"sorted":true,"unsorted":false},"first":true,"numberOfElements":3,"empty":false}
