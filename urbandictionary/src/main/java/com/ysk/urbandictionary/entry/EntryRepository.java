package com.ysk.urbandictionary.entry;


import com.ysk.urbandictionary.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry,Long> {

    Page<Entry> findByUser(User user, Pageable page);  //verilen usera göre page tipinde entry getiricek.
}
