package com.ysk.urbandictionary.entry;

import com.ysk.urbandictionary.user.User;
import com.ysk.urbandictionary.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.DoubleStream;

@Service
public class EntryService {

    EntryRepository entryRepository;

    UserService userService;

    public EntryService(EntryRepository entryRepository, UserService userService) {
        this.entryRepository = entryRepository;
        this.userService = userService;
    }

    public void saveEntry(Entry entry, User user) {
        entry.setTimestamp(new Date());  //db ye kaydetmeden önce şuanın zamanını girdik.
        entry.setUser(user);
        entryRepository.save(entry);
    }

    public Page<Entry> getEntries(Pageable page) {
        return entryRepository.findAll(page);
    }

    public Page<Entry> getEntriesByUsername(String username, Pageable page) {
        User inDatabase = userService.getByUsername(username);
        return entryRepository.findByUser(inDatabase,page);
    }
}

