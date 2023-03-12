package com.isntyet.java.practice.home.controller;

import com.isntyet.java.practice.common.CommonResponse;
import com.isntyet.java.practice.home.application.HomeService;
import com.isntyet.java.practice.home.application.command.CreateHomeCommand;
import com.isntyet.java.practice.home.application.command.HomeFilter;
import com.isntyet.java.practice.home.application.command.SearchHomeQuery;
import com.isntyet.java.practice.home.application.dto.HomeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/home")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:63342"})
public class HomeController {
    private final HomeService homeService;

    @PostMapping
    public CommonResponse<HomeInfo> create(@RequestBody CreateHomeCommand command) {
        HomeInfo home = homeService.create(command);

        return CommonResponse.ok(home);
    }

    @PostMapping("/search")
    public CommonResponse<List<HomeInfo>> search(@RequestBody SearchHomeQuery query) {
        List<HomeInfo> homes = homeService.search(query);

        return CommonResponse.ok(homes);
    }

    @GetMapping("/decrease")
    public String decreasePrice(@RequestParam(value = "name") String name, @RequestParam(value = "price") int price) {
        String result;
        try {
            homeService.decreasePrice(name, price);
            result = "현재 가격 : " + homeService.currentPrice(name);
        }
//        catch (ObjectOptimisticLockingFailureException oe) {
//            log.info("재시도");
//            return decreaseMoney(name, money);
//        }
        catch (Exception e) {
            result = e.getMessage();
        }
        log.info(result);
        return result;
    }

    @GetMapping("/download")
    public void downloadGet(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileNameUtf8 = URLEncoder.encode("FAST_EXCEL", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileNameUtf8 + ".xlsx");

        try (OutputStream os = response.getOutputStream()) {
            Workbook wb = new Workbook(os, "PracticeApplication", "1.0");

            Worksheet ws = wb.newWorksheet("home");
            for (int i = 0; i < 500000; i++) {
                ws.value(i, 0, "No.");
                ws.value(i, 1, "첫번 째 칼럼 " + i);
                ws.value(i, 2, "두번 째 칼럼");
                ws.value(i, 3, i + "세번 째 칼럼");

                if (i % 100 == 0) {
                    System.out.println("flush 중 " + i);
                    ws.flush();
                }
            }

            ws.flush();
            ws.finish();

            wb.finish();
        } catch (IOException e) {
            log.error("[fastexcel] ERROR {}", e.getMessage());
        }
    }

    @PostMapping("/download")
    public void downloadPost(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileNameUtf8 = URLEncoder.encode("FAST_EXCEL", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileNameUtf8 + ".xlsx");
        try (OutputStream os = response.getOutputStream()) {
            Workbook wb = new Workbook(os, "PracticeApplication", "1.0");

            Worksheet ws = wb.newWorksheet("home");
            for (int i = 0; i < 500000; i++) {
                ws.value(i, 0, "No.");
                ws.value(i, 1, "첫번 째 칼럼 " + i);
                ws.value(i, 2, "두번 째 칼럼");
                ws.value(i, 3, i + "세번 째 칼럼");

                if (i % 100 == 0) {
                    System.out.println("flush 중 " + i);
                    ws.flush();
                }
            }

            ws.flush();
            ws.finish();

            wb.finish();
        } catch (IOException e) {
            log.error("[fastexcel] ERROR {}", e.getMessage());
        }
    }

    @PostMapping(value = "/download-2")
    public void download2(
            HttpServletResponse response,
            HomeFilter filter
    ) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileNameUtf8 = URLEncoder.encode("FAST_EXCEL", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileNameUtf8 + ".xlsx");

        try (OutputStream os = response.getOutputStream()) {
            this.homeService.excelDownload(os, filter);
        } catch (IOException e) {
            log.error("error {}", e.getMessage());
        }
    }
}
