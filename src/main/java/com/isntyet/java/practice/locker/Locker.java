package com.isntyet.java.practice.locker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;

@Slf4j
@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "locker")
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "target_date", unique = true)
    private LocalDate targetDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static Locker of(LocalDate targetDate) {
        return new Locker(targetDate, Status.INIT);
    }

    private Locker(LocalDate targetDate, Status status) {
        this.targetDate = targetDate;
        this.status = status;
    }

    public boolean isRunning() {
        return Status.RUNNING.equals(status);
    }

    public Locker run() {
        this.status = Status.RUNNING;
        return this;
    }

    public Locker complete() {
        this.status = Status.COMPLETE;
        return this;
    }

    public enum Status {
        INIT,
        RUNNING,
        COMPLETE
    }
}
