package com.ysk.urbandictionary.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    Page<User> findByUsernameNot(String username, Pageable page); //bu username i olmayanları getir.
}


  /*@Query(value="Select u from User u")  //jpql şeklinde yazacağız. (Java Persistence Query Language) . Bütün userları getir ama UserProjection şeklinde.
    Page<UserProjection> getAllUsersProjection(Pageable page);*/