package com.ysk.urbandictionary.entry;

import com.ysk.urbandictionary.user.User;
import com.ysk.urbandictionary.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
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

    public Page<Entry> getOldEntries(Long id, Pageable page) {
        return entryRepository.findByIdLessThan(id,page);
    }

    public Page<Entry> findByIdLessThanAndUser(Long id, String username, Pageable page) {
        User inDatabase = userService.getByUsername(username);
        return entryRepository.findByIdLessThanAndUser(id,inDatabase,page);
    }

    public long getNewEntriesCount(Long id) {
        return entryRepository.countByIdGreaterThan(id);
    }

    public long getNewEntriesCountOfUser(Long id, String username) {
        User inDatabase = userService.getByUsername(username);
        return entryRepository.countByIdGreaterThanAndUser(id,inDatabase);
    }

    public List<Entry> getNewEntries(Long id, Sort sort) {
        return entryRepository.findByIdGreaterThan(id,sort);
    }

    public List<Entry> getNewEntriesOfUser(Long id, String username, Sort sort) {
        User inDatabase = userService.getByUsername(username);
        return entryRepository.findByIdGreaterThanAndUser(id,inDatabase,sort);
    }
}

