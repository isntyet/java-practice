package com.isntyet.java.practice.human.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface HumanRepository extends JpaRepository<Human, Integer> {

    Human findByName(String name);

    List<Human> findAllByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select h from Human h where h.name = :name")
    Human findWithNameForUpdate(@Param("name") String name);
}
