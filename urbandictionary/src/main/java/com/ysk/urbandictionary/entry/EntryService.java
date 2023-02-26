package com.ysk.urbandictionary.entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EntryService {

    EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public void saveEntry(Entry entry) {
        entry.setTimestamp(new Date());  //db ye kaydetmeden önce şuanın zamanını girdik.
        entryRepository.save(entry);
    }

    public Page<Entry> getEntries(Pageable page) {
        return entryRepository.findAll(page);
    }
}

