package com.isntyet.java.practice.locker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class LockerService {
    private final LockerRepository lockerRepository;

    @Transactional
    public Locker lock(LocalDate targetDate) {
        Locker locker = lockerRepository.findByTargetDate(targetDate)
                .orElseGet(() -> Locker.of(targetDate));

        if(locker.isRunning()) {
            throw new IllegalArgumentException("이미 진행 중입니다.");
        }
        return lockerRepository.save(locker.run());
    }

    @Transactional
    public void complete(Locker locker) {
        lockerRepository.save(locker.complete());
    }
}
