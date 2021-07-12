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
            throw new IllegalArgumentException("이미 bulk billing 진행 중입니다.");
        }
        lockerRepository.save(locker.run());


        return lockerRepository.findByTargetDate(targetDate)
                .orElseThrow(() -> new IllegalArgumentException("bulk billing lock 실패하였습니다."));
    }

    @Transactional
    public void complete(Locker locker) {
        lockerRepository.save(locker.complete());
    }
}
