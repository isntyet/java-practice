package com.isntyet.java.practice.locker;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.Optional;

public interface LockerRepository extends JpaRepository<Locker, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Locker> findByTargetDate(LocalDate runningDate);
}
