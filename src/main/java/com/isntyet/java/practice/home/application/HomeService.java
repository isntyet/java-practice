package com.isntyet.java.practice.home.application;

import com.isntyet.java.practice.home.application.command.CreateHomeCommand;
import com.isntyet.java.practice.home.application.command.HomeFilter;
import com.isntyet.java.practice.home.application.command.SearchHomeQuery;
import com.isntyet.java.practice.home.application.dto.HomeInfo;
import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.home.domain.HomeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeService {
    private final HomeRepository homeRepository;

    @Transactional
    public HomeInfo create(CreateHomeCommand command) {
        Home home = Home.builder()
                .name(command.getName())
                .address(command.getAddress())
                .price(command.getPrice())
                .humans(command.getHumans())
                .build();

        Home result = homeRepository.save(home);
        return HomeInfo.from(result);
    }

    @Transactional(readOnly = true)
    public List<HomeInfo> search(SearchHomeQuery query) {
        List<Home> homes = homeRepository.findAllHomesByFilter(query.getName());
        return homes.stream().map(HomeInfo::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public int currentPrice(String name) {
        Home home = homeRepository.findByName(name);
        return home.getPrice();
    }

    @Transactional
    public int decreasePrice(String name, int price) {
        Home home = homeRepository.findWithNameForUpdate(name);
        home.decreasePrice(price);
        return home.getPrice();
    }

    @Transactional(readOnly = true)
    public void excelDownload(OutputStream os, HomeFilter filter) throws IOException {
        final int FLUSH_SIZE = 100;
        Workbook wb = new Workbook(os, "PracticeApplication", "1.0");

        Worksheet ws = wb.newWorksheet("home");
        ws.value(0, 0, "No.");
        ws.value(0, 1, "이름");
        ws.value(0, 2, "주소");
        ws.value(0, 3, "가격");

        int row = 1;
        int page = 0;

        while (true) {
            var homes = homeRepository.findAllHomesByFilterWithPage(
                    filter,
                    PageRequest.of(page, 10000)
            );

            if (homes.getContent().size() == 0) {
                break;
            }

            for (Home home : homes) {
                ws.value(row, 0, row);
                ws.value(row, 1, home.getName());
                ws.value(row, 2, home.getAddress());
                ws.value(row, 3, home.getPrice());

                if (++row % FLUSH_SIZE == 0) {
                    ws.flush();
                }
            }

            ws.flush();
            ws.finish();

            page++;
        }

        wb.finish();
    }
}
