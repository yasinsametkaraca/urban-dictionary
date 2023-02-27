package com.ysk.urbandictionary.entry;


import com.ysk.urbandictionary.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry,Long> {

    Page<Entry> findByUser(User user, Pageable page);           //verilen usera göre page tipinde entry getiricek.

    Page<Entry> findByIdLessThan(Long id,Pageable page);        //belirttiğimiz id den öncekileri bulucak.

    Page<Entry> findByIdLessThanAndUser(Long id, User user, Pageable page);    //hem vereceğimiz id den küçük olup hemde bu usera eşit olanları getir.

    long countByIdGreaterThan(Long id); //verdiğimiz id den sonrakilerin sayısını döner.

    long countByIdGreaterThanAndUser(Long id, User user);  //verdiğimiz usera ait olan, verilen id den idlerin sayısını döner.

    List<Entry> findByIdGreaterThan(Long id, Sort sort);  //sıralı olarak, verdiğimiz id den daha büyük id li olan entryleri döner.

    List<Entry> findByIdGreaterThanAndUser(Long id, User user, Sort sort);

}
