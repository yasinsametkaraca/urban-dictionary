package com.ysk.urbandictionary.entry;

import com.ysk.urbandictionary.entry.dtos.EntryDto;
import com.ysk.urbandictionary.entry.dtos.EntrySubmitDto;
import com.ysk.urbandictionary.shared.CurrentUser;
import com.ysk.urbandictionary.shared.GenericResponse;
import com.ysk.urbandictionary.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EntryController {

    @Autowired
    EntryService entryService;

    @PostMapping("/api/entries")
    GenericResponse saveEntry(@Valid @RequestBody EntrySubmitDto entry, @CurrentUser User user){                                                     //Entry i kaydetmeden önce o an ki login olan kullanıcının bilgilerini alır.
        entryService.saveEntry(entry,user);
        return new GenericResponse("Entry is saved");
    }

    @GetMapping("/api/entries")
    Page<EntryDto> getEntries(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page){                                //@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) kodunu sıralama yapması için yaptım. Page ile birlikte gelen bir davranış.
        return entryService.getEntries(page).map(EntryDto::new);                                                                            //entry objectlerini tek tek alır ve EntryDto a çevirir. Bunu EntryDto da ki constructoru kullanarak yapar. Sonuç olarak EntryDto objesi dönmüş olur.
    }

    @GetMapping({"/api/entries/{id:[0-9]+}","/api/users/{username}/entries/{id:[0-9]+}"})                                               //İki controllerı ortak tanımladık. Username path inin required false yaptık böylece hem username in olduğu hemde username in olmadığı sorguları koşturabilicez. Birde regex tanımladık çünkü uygulamada id long tipindedir.
    ResponseEntity<?> getEntriesRelative(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
                                                     @PathVariable Long id,
                                                     @PathVariable(required = false) String username,
                                                     @RequestParam(name="count",required = false,defaultValue = "false") boolean count,
                                                     @RequestParam(name = "direction", defaultValue = "before") String direction){                                                      //count kısmı belli id den sonrasını dönücek sayfalarda karışıklık olmaması için. @RequestParam api path de /api/entries?count=true gibi istekler içindir. //direction=after demek de verilen id den sonrakileri getir demektir.
        if(count) {                                                                                                                                         //eğer ?count=true gelirse pathde count sayısını döner.
            long newEntryCount = entryService.getNewEntriesCount(id,username);                                                                              //burda username i yolladık service kısmında null değilse diye kontrol ediyoruz ve ona göre usera göre ve usersiz dönüşler yapıyoruz.
            Map<String,Long> responseCount = new HashMap<>();
            responseCount.put("count",newEntryCount);                                                                                                   //{"count" : 8} gibi json dönmek istiyoruz.
            return ResponseEntity.ok(responseCount);
        }
        if(direction.equals("after")){
            List<EntryDto> newEntries = entryService.getNewEntries(id,username,page.getSort()).stream().map(EntryDto::new).collect(Collectors.toList());                 //page.getSort() istememin sebebi List olarak döndüğümüz için sıralı dönmezdi bu yüzden sıralı dönmesi için yaptım. birde listeyi EntryDto listesine çevirdim.
            return ResponseEntity.ok(newEntries);
        }
        return ResponseEntity.ok(entryService.getOldEntries(id,username, page).map(EntryDto::new));                                                                       //çoklu response var diye ResponseEntity kullandım.
    }

    @GetMapping("/api/users/{username}/entries")
    Page<EntryDto> getEntriesByUsername(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page, @PathVariable String username){
        return entryService.getEntriesByUsername(username,page).map(EntryDto::new);
    }

}

//getEntriesByUsername bize bu şekilde döner. Page kullanmamızın sebebi EntryDto ları page objesi olarak dönsün isteriz. {"content":[{"id":33,"word":"sdfsf","definition":"sdfsfd","sentence":"sdfsdfsf","timestamp":1677450736855,"user":{"username":"yasinsametkaraca","displayName":"yskrca","image":"1bdaffd4d814428bb22ea674795ab25f.jpg"}},{"id":32,"word":"dsfsa","definition":"fsdfsf","sentence":"sdfsdf","timestamp":1677446543688,"user":{"username":"yasinsametkaraca","displayName":"yskrca","image":"1bdaffd4d814428bb22ea674795ab25f.jpg"}},{"id":31,"word":"dgd","definition":"gdfgd","sentence":"fgdfg","timestamp":1677445768008,"user":{"username":"yasinsametkaraca","displayName":"yskrca","image":"1bdaffd4d814428bb22ea674795ab25f.jpg"}}],"pageable":{"sort":{"empty":false,"sorted":true,"unsorted":false},"offset":0,"pageSize":10,"pageNumber":0,"paged":true,"unpaged":false},"last":true,"totalPages":1,"totalElements":3,"size":10,"number":0,"sort":{"empty":false,"sorted":true,"unsorted":false},"first":true,"numberOfElements":3,"empty":false}
/*
    @GetMapping("/api/entries/{id:[0-9]+}") //regex tanımladık çünkü uygulamada id long tipindedir.
    ResponseEntity<?> getEntriesRelative(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
                                     @PathVariable Long id,
                                     @RequestParam(name="count",required = false,defaultValue = "false") boolean count,
                                     @RequestParam(name = "direction", defaultValue = "before") String direction){                                                      //count kısmı belli id den sonrasını dönücek sayfalarda karışıklık olmaması için. @RequestParam api path de /api/entries?count=true gibi istekler içindir. //direction=after demek de verilen id den sonrakileri getir demektir.
        if(count) {                                                                                                                                         //eğer ?count=true gelirse pathde count sayısını döner.
            long newEntryCount = entryService.getNewEntriesCount(id);
            Map<String,Long> responseCount = new HashMap<>();
            responseCount.put("count",newEntryCount); //{"count" : 8} gibi json dönmek istiyoruz.
            return ResponseEntity.ok(responseCount);
        }
        if(direction.equals("after")){
            List<EntryDto> newEntries = entryService.getNewEntries(id,page.getSort()).stream().map(EntryDto::new).collect(Collectors.toList());                 //page.getSort() istememin sebebi List olarak döndüğümüz için sıralı dönmezdi bu yüzden sıralı dönmesi için yaptım. birde listeyi EntryDto listesine çevirdim.
            return ResponseEntity.ok(newEntries);
        }
        return ResponseEntity.ok(entryService.getOldEntries(id,page).map(EntryDto::new));                                                                       //çoklu response var diye ResponseEntity kullandım.
    }

    @GetMapping("/api/users/{username}/entries")
    Page<EntryDto> getEntriesByUsername(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page, @PathVariable String username){
        return entryService.getEntriesByUsername(username,page).map(EntryDto::new);
    }

    @GetMapping("/api/users/{username}/entries/{id:[0-9]+}")
    ResponseEntity<?> getEntriesByUsernameRelative(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
                                                   @PathVariable String username,
                                                   @PathVariable Long id,
                                                   @RequestParam(name="count",required = false,defaultValue = "false") boolean count,
                                                   @RequestParam(name = "direction", defaultValue = "before") String direction){
        if(count) {
            long newEntryCount = entryService.getNewEntriesCountOfUser(id,username);
            Map<String,Long> responseCount = new HashMap<>();
            responseCount.put("count",newEntryCount); //{"count" : 8} gibi json dönmek istiyoruz.
            return ResponseEntity.ok(responseCount);
        }
        if(direction.equals("after")){
            List<EntryDto> newEntries = entryService.getNewEntriesOfUser(id,username, page.getSort()).stream().map(EntryDto::new).collect(Collectors.toList());                 //page.getSort() istememin sebebi List olarak döndüğümüz için sıralı dönmezdi bu yüzden sıralı dönmesi için yaptım. birde listeyi EntryDto listesine çevirdim.
            return ResponseEntity.ok(newEntries);
        }
        return ResponseEntity.ok(entryService.findByIdLessThanAndUser(id,username,page).map(EntryDto::new));
    }
*/
