package com.ysk.urbandictionary.entry;

import com.ysk.urbandictionary.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EntryController {

    @Autowired
    EntryService entryService;

    @PostMapping("/api/entries")
    GenericResponse saveEntry(@Valid @RequestBody Entry entry){
        entryService.saveEntry(entry);
        return new GenericResponse("Entry is saved");
    }

    @GetMapping("/api/entries")
    Page<Entry> getEntries(@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable page){  //@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) kodunu sıralama yapması için yaptım. Page ile birlikte gelen bir davranış.
        return entryService.getEntries(page);
    }

}
